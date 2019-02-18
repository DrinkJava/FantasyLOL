import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

public class EndlessGame {
    static Scanner moderator = new Scanner(System.in);
    static DraftSystem drafting = new DraftSystem();
    static JSONObject stored_data = new JSONObject();
    
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Fantasy League of Legends!");
        while (true) {
            System.out.println("Pick one of the options below: ");
            System.out.println("(1) Initial Draft");
            System.out.println("(2) Trade Players");
            System.out.println("(3) Trade from Pool");
            System.out.println("(4) Print all Fantasy Teams");
            System.out.println("(5) Exit");
            int choice = moderator.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("This will start the initial draft");
                    drafting.run_draft();
                    break;
                case 2:
                    System.out.println("This will allow two players to trade");
                    
                    break;
                case 3:
                    System.out.println("This will allow a player to trade from the pool");
                    break;
                case 4:
                    System.out.println("All of the current fantasy teams: ");
                    drafting.print_Fantasy_Teams();
                    break;
                case 5: 
                    System.out.println("Goodbye");
                    return;
            }
                    
        }
    }
}
