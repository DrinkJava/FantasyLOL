package utils;

import java.util.ArrayList;

public class F_League_Players {
	public ArrayList<String> fantasy_team = new ArrayList<String>();
	private int Player_ID;
	
	public F_League_Players(int ID) {
		Player_ID = ID;
	}
	
	public int get_Player_ID() {
		return Player_ID;
	}
	
	public void add_To_Fantasy_Team(String pro_player) {
		fantasy_team.add(pro_player);
	}
	
	public void remove_From_Fantasy_Team(String pro_player) {
		if(fantasy_team.contains(pro_player)) {
			int remove = fantasy_team.indexOf(pro_player);
			fantasy_team.remove(remove);
		}
		
	}
}
