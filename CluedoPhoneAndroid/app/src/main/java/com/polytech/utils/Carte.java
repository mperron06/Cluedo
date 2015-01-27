package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

enum typeCarte {Perso, Arme, Piece, None};

public class Carte implements Serializable{

	private static final long serialVersionUID = 690169787746724574L;
	
	private static final String ID_KEY = "id";
	private static final String NAME_KEY = "nom";
	private static final String TYPE_KEY = "type";
	private static final String VALUE_TAG = "tag";

    private static final String TYPECARTE_PERSO = "perso";
    private static final String TYPECARTE_ARME = "arme";
    private static final String TYPECARTE_PIECE = "piece";
	
	private long id;
	private String nom;
	private typeCarte type;
	private long tag;

	public Carte(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(json);
		} catch (ParseException e) {
			Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
			e.printStackTrace();
		}
		
		this.createCarte(jsonObject);
	}
	
	public Carte(JSONObject jsonObject) {
		this.createCarte(jsonObject);
	}
	
	private void createCarte(JSONObject jsonObject) {
		this.id = (Long) jsonObject.get(ID_KEY);
		this.nom = (String) jsonObject.get(NAME_KEY);
		this.type = getTypeCarte((String) jsonObject.get(TYPE_KEY));
		Long value = (Long) jsonObject.get(VALUE_TAG);
		this.tag = (value == null) ? -1 : value;
	}

	/**
	 * Retourne le type de la carte
	 */
	private static typeCarte getTypeCarte(String type) {
		if (type.equals(TYPECARTE_PERSO))
			return typeCarte.Perso;
		else if (type.equals(TYPECARTE_ARME))
			return typeCarte.Arme;
        else if (type.equals(TYPECARTE_PIECE))
            return typeCarte.Piece;
		else
			return typeCarte.None;
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

	public typeCarte getType() {
		return type;
	}

	public void setType(typeCarte type) {
		this.type = type;
	}

	public long getTag() {
		return tag;
	}

	public void setTag(long valeur) {
		this.tag = valeur;
	}
}