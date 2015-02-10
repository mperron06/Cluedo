package com.polytech.cluedo;

import android.content.Context;
import android.content.Intent;

import com.polytech.utils.ArrayCase;
import com.polytech.utils.JSONUtils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class Remote {

    private static volatile Remote instance;
    private static SocketIO socket;

    /* ------ RECUP INFOS ------ */
    private static final String MY_ID = "myId";
    private static final String CASES = "cases";
    private static final String MY_CARDS_PERSO = "myCardsPerso";
    private static final String MY_CARDS_ARME = "myCardsArme";
    private static final String MY_CARDS_PIECE = "myCardsPiece";
    private static final String CHOICE_CARTE_SUPPO = "choixTextSupposition";
    private static final String CASES_SHORTCUT = "caseRac";


    /* ------ CHANGEMENT DE SCREEN ------ */
    private static final String START_NEW_GAME = "startNewGame";
    private static final String PLAYER_READY = "joueurReady";
    private static final String WAITING_INIT = "joueursPrets";
    private static final String BEGIN_GAME ="debutPartie";
    private static final String WAIT_TURN = "waitTurn";
    private static final String MOVED_TURN = "tourChange";
    private static final String SHORTCUT_TURN = "tourRaccourci";
    private static final String DICE_TURN = "tourLancerDe";
    private static final String SUPPOSITION_TURN = "tourSupposition";
    private static final String ADD_PERSO_SUPPOSITION = "addPersoSupposition";
    private static final String ADD_ARME_SUPPOSITION = "addArmeSupposition";
    private static final String ACCUSATION_TURN = "tourAccusation";
    private static final String WAITING_MOVE = "attenteDeplacement";
    private static final String WAITING_SUPPOSITION = "attenteSupposition";
    private static final String WAITING_ACCUSATION = "attenteAccusation";
    private static final String ACCUSED = "choixCarteSupposition";
    private static final String REICEVE_CARD = "receptionCarteAccusation";
    private static final String END_GAME = "partieTerminee";

    public static ArrayCase les_cases;
    public static String[] ma_supposition;
    public static String[] accusations;
    public static ArrayList<String> my_accused;
    public static ArrayList<String> my_cards_all;
    public static ArrayList<String> my_cards_perso;
    public static ArrayList<String> my_cards_arme;
    public static ArrayList<String> my_cards_piece;
    public static ArrayList<String> perso_for_supposition;
    public static ArrayList<String> arme_for_supposition;
    public static ArrayList<String> historique_perso;
    public static ArrayList<String> historique_arme;
    public static ArrayList<String> historique_piece;
    public static ArrayList<String> cases_shortcut;

    // EMIT
    private static final String ADD_PLAYER = "addPlayer";
    private static final String DICE_ROLL = "lanceDe";
    private static final String EMIT_SUPPOSITION = "supposition";
    private static final String EMIT_NOT_SUPPOSE = "notSuppose";
    private static final String CHOIX_CARTE = "tourReceptionCarte";
    private static final String END_TURN = "tourTermine";
    private static final String EMIT_NEW_GAME="newGame";
    private static final String EMIT_END_GAME = "tourFinJeu";

    // FIELDS
    private static final String FIELD_ID_JOUEUR = "idJoueur";
    private static final String FIELD_ID_CASE = "idCase";
    private static final String FIELD_CARDS = "cartes";


    public static String url;
    public static Context context;
    public static boolean already_connect;

    public static long mon_id;
    public static String mon_perso;
    public static String mon_pseudo;
    public static String card_send;
    public static String pseudo_card_send;
    public static long id_joueur_actuel;
    public static boolean turn_moved;

    public static boolean mon_tour;
    public static int valeur_de;
    public static boolean accusation_turn;


    final IOCallback ioCallback = new IOCallback() {

        @Override
        public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
            			/* ------ RECUP INFOS ------ */
            if (event.equals(MY_ID)) { // ID
                mon_id = Long.parseLong(objects[0].toString());
                System.out.println("my_id "+mon_id);
            }
            if (event.equals(CASES)) { // CASES
                les_cases = new ArrayCase(objects[0].toString());
                System.out.println(les_cases);
            }
            if (event.equals(MY_CARDS_PERSO)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    my_cards_all.add(JSONUtils.extractString("cartes", objects[0].toString()));
                    my_cards_perso.add(JSONUtils.extractString("cartes", objects[0].toString()));
                }
            }
            if (event.equals(MY_CARDS_ARME)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    my_cards_all.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    my_cards_arme.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                }
            }
            if (event.equals(MY_CARDS_PIECE)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    my_cards_all.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    my_cards_piece.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                }
            }
            if( event.equals(CHOICE_CARTE_SUPPO)) {
                System.out.println("carte reçu :"+objects[0].toString());
                my_accused.add(objects[0].toString());
                Intent intent = new Intent(context, AccusedActivity.class);
                context.startActivity(intent);
            }
            if( event.equals(CASES_SHORTCUT)) {
                String temp = objects[0].toString().toLowerCase();
                temp.replace(" ", "");
                cases_shortcut.add(temp);
            }
            /* ------ CHANGEMENT DE SCREEN ------ */
            if(event.equals(START_NEW_GAME)){
                initAttributes();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(PLAYER_READY)){
                initAttributes();
                Intent intent = new Intent(context, WaitingLogActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_INIT)) { // MISE EN PLACE DES PIONS
                Intent intent = new Intent(context, WaitingPionsActivity.class);
                context.startActivity(intent);
            }
            if(event.equals(BEGIN_GAME)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());

                if (mon_id == id_joueur_actuel) {
                    String case_actu = JSONUtils.extractString(FIELD_ID_CASE, objects[0].toString());
                    System.out.println(case_actu);
                    System.out.println(my_cards_all);
                    Intent intent = new Intent(context, DiceActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    context.startActivity(intent);
                }
            }
            if(event.equals(WAIT_TURN)){
                Intent intent = new Intent(context, ProfilActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(MOVED_TURN)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    ma_supposition = new String[3];
                    ma_supposition[2] = JSONUtils.extractString("idCase",objects[0].toString());
                    Intent intent = new Intent(context, MovedActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(SHORTCUT_TURN)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    ma_supposition = new String[3];
                    Intent intent = new Intent(context, ShortcutActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(DICE_TURN)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    Intent intent = new Intent(context, DiceActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(SUPPOSITION_TURN)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = mon_id;
                ma_supposition = new String[3];
                ma_supposition[2] = objects[0].toString().toLowerCase().replace(" ",""); //Long.parseLong(objects[0].toString());

                Intent intent = new Intent(context, SuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(ADD_PERSO_SUPPOSITION)) { // on recupere les perso de la piece
                System.out.println("new perso "+objects[0].toString());
                if(!perso_for_supposition.contains(objects[0].toString())) {
                    perso_for_supposition.add(objects[0].toString());
                    Intent intent = new Intent(context, SuppositionActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(ADD_ARME_SUPPOSITION)) { // on recupere les armes de la piece
                System.out.println("new arme "+objects[0].toString());
                String arme_temp = objects[0].toString().toLowerCase().replace(" ","");
                if(!arme_for_supposition.contains(arme_temp)) {
                    arme_for_supposition.add(arme_temp);
                    Intent intent = new Intent(context, SuppositionActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(ACCUSATION_TURN)) { // DEBUT DE LA PARTIE
                accusation_turn = true;
                Intent intent = new Intent(context, SuppositionActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_MOVE)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, WaitingDiceActivity.class);
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
            if (event.equals(ACCUSED)) { // DEBUT DE LA PARTIE
                System.out.println("ACCUSED");
                //Intent intent = new Intent(context, AccusedActivity.class);
                //context.startActivity(intent);
            }
            if (event.equals(REICEVE_CARD)) { // DEBUT DE LA PARTIE
                card_send = JSONUtils.extractString(FIELD_CARDS, objects[0].toString());
                pseudo_card_send = JSONUtils.extractString("pseudo", objects[0].toString());
                System.out.println("carte recu "+card_send+" et pseudo envoyé "+pseudo_card_send);
                if(pseudo_card_send != ""){
                    System.out.println("Ici");
                    Intent intent = new Intent(context, ReceiveCardActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ReceiveNoCardActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(END_GAME)) { // FIN DE LA PARTIE
                //socket.disconnect();
                accusations = new String[4];
                accusations[0] = JSONUtils.extractString("pseudo", objects[0].toString());
                accusations[1] = JSONUtils.extractString("perso", objects[0].toString());
                accusations[2] = JSONUtils.extractString("arme", objects[0].toString());
                accusations[3] = JSONUtils.extractString("lieu", objects[0].toString());

                Intent intent = new Intent(context, EndGameActivity.class);
                context.startActivity(intent);
            }
        }

        @Override
        public void onConnect() {
            if (already_connect) {
                socket.emit("reconnect", mon_id);
            } else {
                socket.emit(ADD_PLAYER, mon_pseudo, mon_perso);
                already_connect = true;
            }
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
        this.connectSocket();
    }

    private void connectSocket() {
        try {
            socket = new SocketIO(url);
        } catch (MalformedURLException e) {
            // URL pourrie
            e.printStackTrace();
        }
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
    public static void emit_lance_de() { socket.emit(DICE_ROLL, valeur_de); }
    public static void emit_not_suppose(){socket.emit(EMIT_NOT_SUPPOSE);}
    public static void emit_supposition() {
        socket.emit(EMIT_SUPPOSITION, ma_supposition[0], ma_supposition[1], ma_supposition[2]);
    }
    public static void emit_choix_carte() {
        socket.emit(CHOIX_CARTE, mon_pseudo, card_send);
    }
    public static void emit_tour_termine() {
        socket.emit(END_TURN);
    }
    public static void emit_end_game(){
        socket.emit(EMIT_END_GAME, ma_supposition[0], ma_supposition[1], ma_supposition[2]);
    };
    public static void emit_new_game(){
        socket.emit(EMIT_NEW_GAME);
    }


    private void initAttributes() {
        accusation_turn = false;
        mon_tour = (mon_id == id_joueur_actuel);
        turn_moved = false;
        valeur_de = 0;
        my_cards_perso = new ArrayList<String>();
        my_cards_arme = new ArrayList<String>();
        my_cards_piece = new ArrayList<String>();
        my_cards_all = new ArrayList<String>();
        perso_for_supposition = new ArrayList<String>();
        arme_for_supposition = new ArrayList<String>();
        historique_arme = new ArrayList<String>();
        historique_perso = new ArrayList<String>();
        historique_piece = new ArrayList<String>();
        my_accused = new ArrayList<String>();
        card_send ="";
        pseudo_card_send="";
        cases_shortcut = new ArrayList<String>();
    }
}
