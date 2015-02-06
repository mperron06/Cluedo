package com.polytech.utils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {

	public static long extractLong(String key, String json) {
		JSONParser parser = new JSONParser();
		try {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(json);
			return (Long) jsonObject.get(key);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public static boolean extractBoolean(String key, String json) {
		JSONParser parser = new JSONParser();
		try {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(json);
			return (Boolean) jsonObject.get(key);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static String extractString(String key, String json) {
		JSONParser parser = new JSONParser();
		try {
			org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(json);
			return (String) jsonObject.get(key);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    public static ArrayCarte extractCards(String key, String json) {
        JSONParser parser = new JSONParser();
        try {
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(json);
            return (ArrayCarte) jsonObject.get(key);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
