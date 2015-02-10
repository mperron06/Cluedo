package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ArrayCarte {

	private Carte[] cartes;
	
	public ArrayCarte(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) jsonParser.parse(json);
		} catch (ParseException e) {
			Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
			e.printStackTrace();
		}
		
		this.cartes = new Carte[jsonArray.size()];
		for (int i=0; i<this.cartes.length; i++) {
			this.cartes[i] = new Carte((JSONObject)jsonArray.get(i));
		}
	}
	
	public int size() {
		return this.cartes.length;
	}
    public Carte[] getCartes() {
        return cartes;
    }

    public String getNom(long id_carte) {
        return cartes[(int) id_carte].getNom();
    }
}
