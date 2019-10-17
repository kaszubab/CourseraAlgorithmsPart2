/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class BaseballElimination {

    private final int [][] gamesMatrix;
    private  HashMap<String, int []>  teamDict;

    public BaseballElimination(String filename)  {
        In file = new In(filename);
        int len = Integer.parseInt(file.readLine());
        gamesMatrix = new int[len][len];
        teamDict = new HashMap<>(len);
        int i = 0;
        while (file.hasNextLine()) {
            String teamName = file.readString();
            // array containing index wins, loses and games remaining
            int [] teamInfo = new int[4];
            teamInfo[0] = i;
            teamInfo[1] = file.readInt();
            teamInfo[2] = file.readInt();
            teamInfo[3] = file.readInt();
            teamDict.put(teamName,teamInfo);
            // filling games matrix
            for (int j = 0; j < len; j++) {
                int gamesVersusJTeam = file.readInt();
                gamesMatrix[i][j] = gamesVersusJTeam;
            }
            i++;
            file.readLine();
        }
    }                  // create a baseball division from given filename in format specified below
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
        //solution for trivial elimination
        for (int[] x : teamDict.values()) {
            if (maxPossibleWins < x[1]) return false;
        }
        System.out.println(teamDict.size());
        FlowNetwork teamFlowNetwork = new FlowNetwork((teamDict.size() * (teamDict.size()-1))/2 + teamDict.size()+1);
        int  curTeamIndex = teamDict.get(team)[0];
        int gamesV = 1;
        for (int  i = 0; i < gamesMatrix.length; i++) {
            if (i != curTeamIndex) {
                for (int j = i+1; j <gamesMatrix.length; j++) {
                    FlowEdge sourceToTeamGames = new FlowEdge(0, gamesV, gamesMatrix[i][j]);
                    gamesV++;
                    teamFlowNetwork.addEdge(sourceToTeamGames);
                }
            }
        }
        int teamV = gamesV;
        int howManyGamesWithOtherTeamsWillTeamPlay = teamDict.size()-1;
        while ( howManyGamesWithOtherTeamsWillTeamPlay > 0) {
            int currConfrontation = 1;
            int otherTeamQuantity = 1;
            int currTeamInBack = teamV + 1;
            while (otherTeamQuantity < howManyGamesWithOtherTeamsWillTeamPlay) {
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
        System.out.println(teamFlowNetwork);
        return true;
    }              // is given team eliminated?


    public Iterable<String> certificateOfElimination(String team){
        Stack<String> newStack = new Stack<>();
        newStack.push("5");
        return newStack;
    }  // subset R of teams that eliminates given team; null if not eliminated



    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        division.isEliminated("Atlanta");
        /*for (String team : division.teams()) {
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
        }*/
    }
}
