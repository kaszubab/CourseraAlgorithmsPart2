/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class BaseballElimination {

    private final int [][] gamesMatrix;
    private  HashMap<String, int []>  teamDict;
    private HashMap<Integer, String> reverseDict;

    public BaseballElimination(String filename)  {
        In file = new In(filename);
        int len = Integer.parseInt(file.readLine());
        gamesMatrix = new int[len][len];
        teamDict = new HashMap<>(len);
        reverseDict = new HashMap<>(len);
        int i = 0;
        while (file.hasNextLine()) {
            String teamName = file.readString();
            // array containing index wins, loses and games remaining
            int [] teamInfo = new int[4];
            teamInfo[0] = i;
            teamInfo[1] = file.readInt();
            teamInfo[2] = file.readInt();
            teamInfo[3] = file.readInt();
            teamDict.put(teamName, teamInfo);
            reverseDict.put(i, teamName);
            // filling games matrix
            for (int j = 0; j < len; j++) {
                int gamesVersusJTeam = file.readInt();
                gamesMatrix[i][j] = gamesVersusJTeam;
            }
            i++;
            file.readLine();
        }
    }                  // create a baseball division from given filename in format specified below

    private FlowNetwork makeFlowNetwork(String team, int maxPossibleWins) {
        FlowNetwork teamFlowNetwork = new FlowNetwork(((teamDict.size()-1) * (teamDict.size()-2))/2 + teamDict.size()+1);
        int  curTeamIndex = teamDict.get(team)[0];
        int gamesV = 1;
        for (int  i = 0; i < gamesMatrix.length; i++) {
            if (i != curTeamIndex) {
                for (int j = i+1; j < gamesMatrix.length; j++) {
                    if (j != curTeamIndex) {
                        FlowEdge sourceToTeamGames = new FlowEdge(0, gamesV, gamesMatrix[i][j]);
                        gamesV++;
                        teamFlowNetwork.addEdge(sourceToTeamGames);
                    }
                }
            }
        }
        int teamV = gamesV;
        int howManyGamesWithOtherTeamsWillTeamPlay = teamDict.size()-2;
        int currConfrontation = 1;
        while (howManyGamesWithOtherTeamsWillTeamPlay > 0) {
            int otherTeamQuantity = 1;
            int currTeamInBack = teamV + 1;
            while (otherTeamQuantity <= howManyGamesWithOtherTeamsWillTeamPlay) {
                FlowEdge newEdge = new FlowEdge(currConfrontation, teamV, Double.POSITIVE_INFINITY);
                teamFlowNetwork.addEdge(newEdge);
                newEdge = new FlowEdge(currConfrontation, currTeamInBack, Double.POSITIVE_INFINITY);
                teamFlowNetwork.addEdge(newEdge);
                currConfrontation++;
                otherTeamQuantity++;
                currTeamInBack++;
            }
            teamV++;
            howManyGamesWithOtherTeamsWillTeamPlay--;
        }
        int i = gamesV;
        for (int j = 0; j < reverseDict.size(); j++) {
            if (!(reverseDict.get(j).equals(team))) {
                FlowEdge newEdge = new FlowEdge(i, teamV+1, maxPossibleWins - teamDict.get(reverseDict.get(j))[1]);
                teamFlowNetwork.addEdge(newEdge);
                i++;
            }
        }
        return teamFlowNetwork;
    }

    private void validateEntry (String team) {
        if (team == null)throw new IllegalArgumentException();
        if (!(teamDict.containsKey(team)) throw  new IllegalArgumentException();
    }


    public int numberOfTeams() {
        return teamDict.size();
    }                        // number of teams
    public Iterable<String> teams() {
        return teamDict.keySet();
    }                                // all teams
    public int wins(String team) {
        return teamDict.get(team)[1];
    }                    // number of wins for given team
    public int losses(String team) {
        return teamDict.get(team)[2];
    }                    // number of losses for given team
    public int remaining(String team) {
        return teamDict.get(team)[3];
    }                // number of remaining games for given team
    public int against(String team1, String team2) {
        return gamesMatrix[teamDict.get(team1)[0]][teamDict.get(team2)[0]];
    }    // number of remaining games between team1 and team2

    public boolean isEliminated(String team) {
        int maxPossibleWins = teamDict.get(team)[1] + teamDict.get(team)[3];
        // solution for trivial elimination
        for (int[] x : teamDict.values()) {
            if (maxPossibleWins < x[1]) return false;
        }
        FlowNetwork teamFlowNetwork = makeFlowNetwork(team, maxPossibleWins);
        FordFulkerson checkingMaxFlow = new FordFulkerson(teamFlowNetwork, 0, (((teamDict.size()-1) * (teamDict.size()-2))/2) + teamDict.size());
        for (int i = ((teamDict.size()-2) * teamDict.size()-1)/2 + 1; i < ((teamDict.size()-2) * teamDict.size()-1)/2 + teamDict.size(); i++) {
            if (checkingMaxFlow.inCut(i)) return true;
        }
        return false;
    }              // is given team eliminated?


    public Iterable<String> certificateOfElimination(String team) {
        Stack<String> newStack = new Stack<>();
        int maxPossibleWins = teamDict.get(team)[1] + teamDict.get(team)[3];
        // solution for trivial elimination
        boolean nonTrivialEliminated = false;
        for (String x : teamDict.keySet()) {
            if (maxPossibleWins < teamDict.get(x)[1]) {
                newStack.push(x);
                nonTrivialEliminated = true;
            }
        }
        if (nonTrivialEliminated) return newStack;
        FlowNetwork teamFlowNetwork = makeFlowNetwork(team, maxPossibleWins);
        FordFulkerson checkingMaxFlow = new FordFulkerson(teamFlowNetwork, 0, (((teamDict.size()-1) * (teamDict.size()-2))/2) + teamDict.size());
        int currTeamIndex = 0;
        for (int i = ((teamDict.size()-2) * teamDict.size()-1)/2 + 1; i < ((teamDict.size()-2) * teamDict.size()-1)/2 + teamDict.size(); i++) {
            if (checkingMaxFlow.inCut(i)) {
                newStack.push(reverseDict.get(currTeamIndex));
            }
            currTeamIndex++;
            if (currTeamIndex < reverseDict.size() && reverseDict.get(currTeamIndex).equals(team))currTeamIndex++;
        }
        return newStack;
    }  // subset R of teams that eliminates given team; null if not eliminated



    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
