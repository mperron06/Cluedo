package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ArrayPlayer {
	private Player[] players;
	
	public ArrayPlayer(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) jsonParser.parse(json);
		} catch (ParseException e) {
			Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
			e.printStackTrace();
		}
		
		this.players = new Player[jsonArray.size()];
		for (int i=0; i<this.players.length; i++) {
			this.players[i] = new Player((JSONObject)jsonArray.get(i));
		}
	}
	
	public void addProperty(long id_joueur, Case[] id_case) {
		players[(int) id_joueur].setId_case_actuelle( id_case);
	}
	
	public int size() {
		return this.players.length;
	}

	public Player[] getPlayers() {
		return players;
	}
	
	public void setIdCaseActuelle(long id_joueur, Case[] id_case) {
		players[(int) id_joueur].setId_case_actuelle(id_case);
	}
}
