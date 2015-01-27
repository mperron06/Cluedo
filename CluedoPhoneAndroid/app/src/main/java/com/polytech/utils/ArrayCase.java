package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ArrayCase {
private Case[] cases;
	
	public ArrayCase(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) jsonParser.parse(json);
		} catch (ParseException e) {
			Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
			e.printStackTrace();
		}
		
		this.cases = new Case[jsonArray.size()];
		for (int i=0; i<this.cases.length; i++) {
			this.cases[i] = new Case((JSONObject)jsonArray.get(i));
		}
	}
	
	public int size() {
		return this.cases.length;
	}

	public Case[] getCases() {
		return cases;
	}
	
	public String getNom(long id_case) {
		return cases[(int) id_case].getNom();
	}
}
