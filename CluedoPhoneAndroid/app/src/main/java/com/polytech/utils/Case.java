package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Case {
    private static final long serialVersionUID = 4180424525164441191L;

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "nom";
    private static final String NUM_KEY = "num";
    private static final String TYPE_KEY = "type";

    private long id;
    private String nom;
    private String num;
    private String type;

    public Case(String json) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(json);
        } catch (ParseException e) {
            Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
            e.printStackTrace();
        }

        this.createCase(jsonObject);
    }

    /**
     * Constructeur
     * @param jsonObject Objet JSON re&ccedil;u.
     */
    public Case(JSONObject jsonObject) {
        this.createCase(jsonObject);
    }

    private void createCase(JSONObject jsonObject) {
        this.id = (Long) jsonObject.get(ID_KEY);
        this.nom = (String) jsonObject.get(NAME_KEY);
        this.num = (String) jsonObject.get(NUM_KEY);
        this.type = (String) jsonObject.get(TYPE_KEY);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNum() {
        return num;
    }
    public String getTyoe() {
        return type;
    }
}
