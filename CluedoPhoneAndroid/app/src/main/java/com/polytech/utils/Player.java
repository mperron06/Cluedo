package com.polytech.utils;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 4869474518101624147L;
	
	private static final String ID_KEY = "id";
	private static final String NAME_KEY = "name";
    private static final String TAG = "tag";
	private static final String ID_ACTUAL_CASE_KEY = "numCase";
	private static final String SUPPOSITION_KEY = "supposition";
	private static final String CARDS_KEY = "cartes";
	private static final String INLINE_KEY = "inline";
	private static final String MOVED_KEY = "moved";
	
	private long id;
	private String nom;
	private Case[] id_case_actuelle;
	private Carte[] cartes;
    private Carte[] supposition;
	private boolean inline;
	private boolean moved;
    private String couleur;
	
	/**
	 * Constructeur
	 * @param json La chaine JSON re&ccedil;ue.
	 */
	public Player(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(json);
		} catch (ParseException e) {
			Log.e("ERREUR PARSER JSON", "Chaine JSON foireuse");
			e.printStackTrace();
		}
		
		this.createPlayer(jsonObject);
	}
	
	public Player(JSONObject jsonObject) {
		this.createPlayer(jsonObject);
	}
	
	private void createPlayer(JSONObject jsonObject) {
		this.id = (Long) jsonObject.get(ID_KEY);
		this.nom = (String) jsonObject.get(NAME_KEY);
		this.inline = ((Boolean) jsonObject.get(INLINE_KEY));
		this.moved = ((Boolean) jsonObject.get(MOVED_KEY));
		this.couleur = (String) jsonObject.get(TAG);

        JSONArray jsonArrayCases = (JSONArray) jsonObject.get(ID_ACTUAL_CASE_KEY);
        if (jsonArrayCases != null) {
            this.id_case_actuelle = new Case[jsonArrayCases.size()];
            for (int i=0; i<this.id_case_actuelle.length; i++) {
                JSONObject jsonCase = (JSONObject) jsonArrayCases.get(i);
                this.id_case_actuelle[i] = new Case(jsonCase);
            }
        }

		JSONArray jsonArrayCartes = (JSONArray) jsonObject.get(CARDS_KEY);
		if (jsonArrayCartes != null) {
			this.cartes = new Carte[jsonArrayCartes.size()];
			for (int i=0; i<this.cartes.length; i++) {
				JSONObject jsonCarte = (JSONObject) jsonArrayCartes.get(i);
				this.cartes[i] = new Carte(jsonCarte);
			}
		}
        JSONArray jsonArraySupposition = (JSONArray) jsonObject.get(SUPPOSITION_KEY);
        if (jsonArraySupposition != null) {
            this.supposition = new Carte[jsonArrayCartes.size()];
            for (int i=0; i<3; i++) {
                JSONObject jsonCarte = (JSONObject) jsonArrayCartes.get(i);
                this.supposition[i] = new Carte(jsonCarte);
            }
        }
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

	public Case[] getId_case_actuelle() {
		return id_case_actuelle;
	}

	public void setId_case_actuelle(Case[] id_case_actuelle) {
		this.id_case_actuelle = id_case_actuelle;
	}

	public Carte[] getCartes() {
		return cartes;
	}

	public void setCartes(Carte[] cartes) {
		this.cartes = cartes;
	}

    public Carte[] getSupposition() {
        return cartes;
    }

    public void setSupposition(Carte[] cartes) {
        this.cartes = cartes;
    }

	public boolean isInline() {
		return inline;
	}

	public boolean isMoved() {
		return moved;
	}

	public String getCouleur() {
		return couleur;
	}
	
}
