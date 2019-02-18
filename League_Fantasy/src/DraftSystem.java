import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Constants;
import utils.F_League_Players;
import java.io.*;

public class DraftSystem {
    // This array list contains all of the pro-players available to draft from
    static ArrayList<String> draft;

    // This array list contains all of the fantasy players in this league
    static ArrayList<F_League_Players> fantasy_players;

    // This array list helps maintain how many pro-players are on each team.
    static ArrayList<Integer> team_counter;

    // For input purposes
    static Scanner drafter = new Scanner(System.in);
    // static F_League_Players temp;
    static int num_f_players = 0;

    public void run_draft() throws IOException {
        System.out.println("How many fantasy players are there?");
        initialize_Number_Of_Fantasy_Players();
        initialize_Draft_Pro_Players();
        start_Draft();
        System.out.println("Draft Complete!");
        print_Fantasy_Teams();
        System.out.println("Leftoever Pro-Players: ");
        print_Draftable_Players();
    }

    /**
     * This method will print all the teams from each of the fantasy players
     * @throws IOException 
     */
    public void print_Fantasy_Teams() throws IOException {
        FileWriter file = null;
        JSONArray total_league = new JSONArray();
        for (F_League_Players current_player : fantasy_players) {
            JSONObject stored_league = new JSONObject();
            JSONArray drafted_team = new JSONArray();
            System.out.println("Player: " + current_player.get_Player_ID());
            stored_league.put("Player", "Player " + current_player.get_Player_ID());
            System.out.println("Player " + current_player.get_Player_ID() + "'s team is: ");
            for (String player : current_player.fantasy_team) {
                drafted_team.put(player);
                System.out.println(player);
            }
            stored_league.put("Team: ", drafted_team);
            total_league.put(stored_league);
            
        }
        file = new FileWriter("/Users/abhishekjohri/FantasyLOl/test.txt");
        file.write(total_league.toString());
        System.out.println("JSON Object: " + total_league);
        file.close();
    }

    /**
     * This initializes all of the fantasy players in a given league
     */
    private static void initialize_Number_Of_Fantasy_Players() {
        num_f_players = drafter.nextInt();
        fantasy_players = new ArrayList<F_League_Players>();

        System.out.println("Number of Fantasy Players: " + num_f_players);
        for (int i = 0; i < num_f_players; i++) {
            fantasy_players.add(new F_League_Players(i + 1));
        }

    }

    /**
     * This converts the constant string of pro players into an array list for the
     * draft system
     */
    private static void initialize_Draft_Pro_Players() {
        draft = new ArrayList<String>();
        for (String player : Constants.pro_players) {
            draft.add(player);
        }
    }

    /**
     * Main drafting logic is here
     */
    private static void start_Draft() {
        // Initializes the counter kept for each of the players
        team_counter = new ArrayList<Integer>();
        for (F_League_Players flp : fantasy_players) {
            team_counter.add(new Integer(0));
        }
        System.out.println("Team_Counter_Size: " + team_counter.size());
        int up_down_draft_flag = 0;
        int draft_flag = 0;

        for (int i : team_counter) {
            i = 0;
        }

        // Draft loop
        while (draft_flag == 0) {
            // Can probably make this it's own method as it is relatively independent of the
            // draft below
            int temp = 0;
            for (int i : team_counter) {
                if (i == Constants.NUMBER_OF_PLAYERS_ON_STARTING_TEAM) {
                    temp++;
                }
                if (temp == num_f_players) {
                    draft_flag = 1;
                    break;
                }
            }

            // This will be the snake draft logic here.
            if (up_down_draft_flag == 0) {
                // First person starts then it will just go from here on
                for (int i = 1; i <= num_f_players; i++) {
                    System.out.println("List of draftable players: ");
                    print_Draftable_Players();

                    System.out.println("Player " + i + " type in the name of a player you want to draft:");
                    String desired_player = drafter.nextLine();
                    if (draft.contains(desired_player)) {
                        draft_Player_To_Team(desired_player, i - 1);
                    } else {
                        // Desired player is either not there or you misspelled their name
                        System.out.println("Desired player is not available or you misspelled their name. Try again");
                        i--;
                    }
                }
                up_down_draft_flag = 1;
            } // end of if
            else {
                // Last person starts then it will just go from here on
                for (int i = num_f_players; i > 0; i--) {
                    System.out.println("List of draftable players: ");
                    print_Draftable_Players();

                    System.out.println("Player " + i + " type in the name of a player you want to draft:");
                    String desired_player = drafter.nextLine();

                    if (draft.contains(desired_player)) {
                        draft_Player_To_Team(desired_player, i - 1);
                    } else {
                        // Desired player is either not there or you misspelled their name
                        System.out.println("Desired player is not available or you misspelled their name. Try again");
                        i++;
                    }
                }
                up_down_draft_flag = 0;
            } // end of else

        } // End of game loop (while loop)

    }

    /**
     * Helper function to print the available pro-players for remaining drafts
     */
    private static void print_Draftable_Players() {
        for (String player : draft) {
            System.out.println(player);
        }
    }

    /**
     * This will take a pro-player from the draft and place it into a fantasy
     * player's league
     * 
     * @param desired_Player
     *            This is the name of the pro-player that a fantasy player wants to
     *            draft
     * @param fantasy_player_ID
     *            This is the fantasy player ID so that the draft affects the
     *            correct team
     */
    private static void draft_Player_To_Team(String desired_Player, int fantasy_player_ID) {
        // Desired player is available
        int temp_index = draft.indexOf(desired_Player);
        String temp_player = draft.get(temp_index);

        // Desired player gets added to the fantasy team
        fantasy_players.get(fantasy_player_ID).add_To_Fantasy_Team(temp_player);

        // Increments the counter for number of players on this participants team
        int current_count = team_counter.get(fantasy_player_ID);
        current_count++;
        team_counter.set(fantasy_player_ID, current_count);

        // Desired player gets removed from the list of draftable players
        draft.remove(temp_index);
    }
}
