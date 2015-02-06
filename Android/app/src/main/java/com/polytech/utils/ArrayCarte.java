package com.polytech.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.polytech.utils.Carte;

public class ArrayCarte {
    private Carte[] cards;

    public ArrayCarte(String json) {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(json);
        } catch (ParseException e) {
            Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
            e.printStackTrace();
        }

        this.cards = new Carte[jsonArray.size()];
        for (int i=0; i<this.cards.length; i++) {
            this.cards[i] = new Carte((JSONObject)jsonArray.get(i));
        }
    }

    public int size() {
        return this.cards.length;
    }

    public Carte[] getCartes() {
        return cards;
    }

    public String getNom(long id_case) {
        return cards[(int) id_case].getNom();
    }
}
