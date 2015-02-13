package com.polytech.cluedo;

/**
 * Created by Alexia on 09/01/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.polytech.utils.ArrayCarte;
import com.polytech.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import static com.polytech.cluedo.R.string.regex;

public class Remote {

    private static volatile Remote instance;
    private static SocketIO socket;

    /* ------ RECUP INFOS ------ */
    private static final String MY_ID = "myId";
    private static final String CASES = "cases";
    private static final String MY_PERSO = "myPerso";

    /* ------ CHANGEMENT DE SCREEN ------ */
    private static final String NEW_TAG = "nouveauTag";
    private static final String PLAYER_READY = "joueurReady";
    private static final String WAITING_INIT = "joueursPrets";
    private static final String BEGIN_GAME = "debutPartie";
    private static final String MOVED_TURN = "tourChange";
    private static final String SHORTCUT_TURN = "tourRaccourci";
    private static final String DICE_TURN = "tourLancerDeTab";
    private static final String SUPPOSITION_TURN = "tourSupposition";
    private static final String ACCUSATION_TURN = "tourAccusation";
    private static final String WAITING_MOVE = "attenteDeplacement";
    private static final String WAITING_SUPPOSITION = "attenteSupposition";
    private static final String WAITING_ACCUSATION = "attenteAccusation";
    private static final String CHOOSE_CARD = "choixCarteSupposition";
    private static final String REICEVE_CARD = "receptionCarteAccusation";
    private static final String END_GAME = "partieTerminee";
    private static final String NEXT_PLAYER = "prochainJoueur";
    private static final String WAIT_TURN = "waitTurnTab";


    private static final String SUPPOSITION = "supposition";
    private static final String ACCUSATION = "accusation";


    // EMIT
    private static final String ADD_PLAYER = "addPlayer";
    private static final String DICE_ROLL = "lanceDe";
    private static final String EMIT_ACCUSATION = "accusation";
    private static final String EMIT_SUPPOSITION = "supposition";
    private static final String CHOIX_CARTE = "choixCarte";
    private static final String END_TURN = "tourTermine";

    // FIELDS
    private static final String FIELD_ID_JOUEUR = "idJoueur";
    private static final String FIELD_TAG = "tag";
    private static final String FIELD_ID_CASE = "idCase";
    //	private static final String FIELD_ID_CARTE = "carteId";

    private static final String FIELD_CARDS = "cartes";



    private static final String MY_CARDS = "myCardsTab";


    public static String url;
    //public static String pseudo;
    public static String mon_pseudo;
    public static String perso;
    public static Context context;
    public static boolean already_connect;

    public static long mon_id;
    public static String mon_perso;
    public static long id_joueur_actuel;

    public static boolean turn_moved;

    public static int case_actuel;
    //public static ArrayCase les_cases;
    //public static ArrayPlayer les_joueurs;

    public static boolean mon_tour;
    public static boolean de_lance;
    public static boolean de_lance_exam;
    public static int valeur_de;

	
	public static boolean myTurn;
    public static boolean displayRaccourci;
    public static boolean displayPassage;
    public static boolean displayDice;
    public static boolean displaySupposition;

    public static String persoHypo;
    public static String armeHypo;
    public static String lieuHypo;


    private static final String PERVENCHE = "Madame Pervenche";

    public static String test;

    //public static ArrayCarte cards;

    private static final String TAG_CARTES = "cartes";
    private static final String TAG_ID = "id";
    private static final String TAG_NOM = "nom";
    private static final String TAG_TYPE = "type";
    private static final String TAG_TAG = "tag";

    static JSONArray cards = null;
    //static JSONObject cards = null;
    static JSONArray cardSupp = null;

    public static String myRoom = "HALL";


    final IOCallback ioCallback = new IOCallback() {

        @Override
        public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
            			/* ------ RECUP INFOS ------ */
            if (event.equals(MY_ID)) { // ID
                mon_id = Long.parseLong(objects[0].toString());
                System.out.println(mon_id);
                /*
                Intent intent = new Intent(context, WaitingActivity.class);
                intent.putExtra("MESSAGE", R.string.putThePiece);
                context.startActivity(intent);
                */
            }
            /*if (event.equals(MY_PERSO)){

            }*/
            /* ------ CHANGEMENT DE SCREEN ------ */
            if (event.equals(PLAYER_READY)){
                System.out.println("Ready");
                Intent intent = new Intent(context, WaitingLogActivity.class);
                context.startActivity(intent);
                System.out.println("Waiting");

            }

            if (event.equals(WAITING_INIT)) { // MISE EN PLACE DES PIONS
                System.out.println("Waiting");
                Intent intent = new Intent(context, WaitingPionsActivity.class);
                context.startActivity(intent);
            }

            if (event.equals(BEGIN_GAME)) { // DEBUT DE LA PARTIE


                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                /*System.out.println("id recu "+id_joueur_actuel+" mon id "+id_joueur_actuel);
                case_actuel = (int) JSONUtils.extractLong(FIELD_ID_CASE, objects[0].toString());
                initAttributes();*/

                if (mon_id == id_joueur_actuel) {
					myTurn = true;
                    displayDice = true;
                    Intent intent = new Intent(context, GlobalActivity.class);
                    context.startActivity(intent);
                } else {
					myTurn = false;
                    Intent intent = new Intent(context, GlobalActivity.class);
                    context.startActivity(intent);
                }

            }

            if (event.equals(MY_CARDS)) {
                    //cards = new ArrayCarte(objects[0].toString());
                    try{
                System.out.println(objects[0].toString());
                //JSONArray jsonArr = new JSONArray(objects[0].toString());
                        //JSONObject jsonObj = jsonArr.getJSONObject(0);

                        cards = new JSONArray(objects[0].toString());
                        JSONObject jsonObj = cards.getJSONObject(0);
                        //String nom = (String) jsonObj.get(TAG_NOM);
                        //System.out.println(nom);

                    //JSONObject jsonObj = new JSONObject(objects[0].toString());
                    //cards = jsonObj.getJSONArray(TAG_CARTES);
                    //cards = jsonObj.getJSONObject(TAG_CARTES);

                    /*for(int i = 0; i < cards.length(); i++){

                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            if (event.equals(MOVED_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, MovedActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(SHORTCUT_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, ShortcutActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(DICE_TURN)) {
                System.out.println("C'est mon tour");
                myTurn = true;
                displayDice = true;
                myRoom = objects[0].toString();
                Intent intent = new Intent(context, GlobalActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(SUPPOSITION_TURN)) {
                System.out.println(objects[0].toString());
                myRoom = objects[0].toString();
                myTurn = true;
                displaySupposition = true;
                Intent intent = new Intent(context, SuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(ACCUSATION_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, SuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_MOVE)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, WaitingMoveActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAIT_TURN)) { // DEBUT DE LA PARTIE
                myTurn = false;
                myRoom = objects[0].toString();
                Intent intent = new Intent(context, GlobalActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_SUPPOSITION)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, WaitingSuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_ACCUSATION)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, WaitingSuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(CHOOSE_CARD)) { // DEBUT DE LA PARTIE
                try{
                    cardSupp = new JSONArray(objects[0].toString());
                }
                catch(JSONException e){}

                Intent intent = new Intent(context, ChooseCardActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(REICEVE_CARD)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, ReceiveCardActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(END_GAME)) { // FIN DE LA PARTIE
                socket.disconnect();
                Intent intent = new Intent(context, EndGameActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(NEXT_PLAYER)){
                System.out.println("Entering here");
                //System.out.println(objects[0]);
                /*String temp = objects[0].toString();
                System.out.println(temp);
                id_joueur_actuel = Long.parseLong(objects[0].toString());
                System.out.println(id_joueur_actuel);
                if (id_joueur_actuel == mon_id){*/
                    /*Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);*/
                /*}
                else {
                    Intent intent = new Intent(context, WaitingActivity.class);
                    context.startActivity(intent);
                }*/
            }
        }

        @Override
        public void onConnect() {
            //System.out.println("Am i here ?");
            if (already_connect) {
                socket.emit("reconnect", mon_id);
            } else {
                //System.out.println("Where am i ?");
                socket.emit(ADD_PLAYER, mon_pseudo, perso);
                already_connect = true;
            }
            // Login to Waiting
            Intent intent = new Intent(context, WaitingLogActivity.class);
            context.startActivity(intent);
        }

        @Override
        public void onMessage(String s, IOAcknowledge ioAcknowledge) {
            // TODO Auto-generated method stub
            System.out.println("Server Message : " + s);

        }

        @Override
        public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {
            // TODO Auto-generated method stub
            System.out.println("Server JSON Message : " + jsonObject);

        }

        @Override
        public void onError(SocketIOException e) {
            // TODO Auto-generated method stub
            e.printStackTrace();
            connectSocket();
        }

        @Override
        public void onDisconnect() {
            // TODO Auto-generated method stub
            System.out.println("disconnected");
        }

    };

    private Remote() {
        /*Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);*/
        this.connectSocket();
    }

    private void connectSocket() {
        try {
            socket = new SocketIO(url);
        } catch (MalformedURLException e) {
            // URL pourrie
            e.printStackTrace();
        }
        //System.out.println("Conneting");
        socket.connect(this.ioCallback);
    }
    public static synchronized Remote getInstance() {
        if (instance == null) {
            instance = new Remote();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
        socket = null;
        already_connect = false;
    }

    // EMIT
    public static void emit_lance_de() { socket.emit(DICE_ROLL, valeur_de);
    System.out.println("Youpi");}


    public static void emit_supposition() {
        socket.emit(EMIT_SUPPOSITION, persoHypo, armeHypo, lieuHypo);
    }

    public static void emit_accusation() {
        socket.emit(EMIT_ACCUSATION);
    }

    public static void emit_choix_carte() {
        socket.emit(CHOIX_CARTE);
    }
    public static void emit_tour_termine() {
        socket.emit(END_TURN);
    }
	
	    private void initAttributes() {
        mon_tour = (mon_id == id_joueur_actuel);
        turn_moved = false;
    }
}
