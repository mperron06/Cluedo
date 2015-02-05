package com.polytech.cluedo;

import android.content.Context;
import android.content.Intent;

import com.polytech.utils.ArrayCarte;
import com.polytech.utils.ArrayCase;
import com.polytech.utils.ArrayPlayer;
import com.polytech.utils.Case;
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
    private static final String MY_CARDS = "myCards";
    private static final String MY_CARDS_PERSO = "myCardsPerso";
    private static final String MY_CARDS_ARME = "myCardsArme";
    private static final String MY_CARDS_PIECE = "myCardsPiece";
    private static final String CHOICE_PERSO = "addPersoSupposition";
    private static final String CHOICE_ARME = "addArmeSupposition";


    /* ------ CHANGEMENT DE SCREEN ------ */
    private static final String NEW_TAG = "nouveauTag";
    private static final String PLAYER_READY = "joueurReady";
    private static final String WAITING_INIT = "joueursPrets";
    private static final String BEGIN_GAME ="debutPartie";
    private static final String WAIT_TURN = "waitTurn";
    private static final String MOVED_TURN = "tourChange";
    private static final String SHORTCUT_TURN = "tourRaccourci";
    private static final String DICE_TURN = "tourLancerDe";
    private static final String SUPPOSITION_TURN = "tourSupposition";
    private static final String ACCUSATION_TURN = "tourAccusation";
    private static final String WAITING_MOVE = "attenteDeplacement";
    private static final String WAITING_SUPPOSITION = "attenteSupposition";
    private static final String WAITING_ACCUSATION = "attenteAccusation";
    private static final String ACCUSED = "choixCarteAccusation";
    private static final String REICEVE_CARD = "receptionCarteAccusation";
    private static final String END_GAME = "partieTerminee";
    private static final String NEXT_PLAYER = "prochainJoueur";

    // ACTIONS CASES
    private static final String SUPPOSITION = "supposition";
    private static final String ACCUSATION = "accusation";


    public static ArrayCase les_cases;
    public static ArrayPlayer les_joueurs;
    public static ArrayCarte my_cards;
    public static ArrayCarte my_suppose;
    public static String[] ma_supposition;
    public static String[] mes_supposition;
    public static ArrayCarte my_accused;
    public static ArrayCarte my_historic;
    public static ArrayList<String> my_cards_all;
    public static ArrayList<String> my_cards_perso;
    public static ArrayList<String> my_cards_arme;
    public static ArrayList<String> my_cards_piece;
    public static ArrayList<String> perso_for_supposition;
    public static ArrayList<String> arme_for_supposition;

    // CASES
    private static final String CASE_BEGIN = "caseDepart";

    // EMIT
    private static final String ADD_PLAYER = "addPlayer";
    private static final String DICE_ROLL = "lanceDe";
    private static final String EMIT_ACCUSATION = "accusation";
    private static final String EMIT_SUPPOSITION = "supposition";
    private static final String EMIT_NOT_SUPPOSE = "notSuppose";
    private static final String CHOIX_CARTE = "choixCarte";
    private static final String END_TURN = "tourTermine";
    private static final String EMIT_RECEPTION="tourReceptionCarte";

    // FIELDS
    private static final String FIELD_ID_JOUEUR = "idJoueur";
    private static final String FIELD_TAG = "tag";
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
    //public static ArrayCase les_cases;
    //public static ArrayPlayer les_joueurs;

    public static boolean mon_tour;
    public static boolean de_lance;
    public static boolean de_lance_exam;
    public static int valeur_de;
    public static Case case_actuel;


    private static final String PERVENCHE = "Madame Pervenche";


    final IOCallback ioCallback = new IOCallback() {

        @Override
        public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
            			/* ------ RECUP INFOS ------ */
            if (event.equals(MY_ID)) { // ID
                System.out.println("my_id");
                mon_id = Long.parseLong(objects[0].toString());
                System.out.println(mon_id);
            }
            if (event.equals(CASES)) { // CASES
                les_cases = new ArrayCase(objects[0].toString());
                System.out.println(les_cases);
            }
            if (event.equals(MY_CARDS)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("Ici");
                    /*my_cards = new ArrayCarte(objects[0].toString());
                    System.out.println(my_cards);
                    System.out.println("Première carte");/*
                    System.out.println(Remote.my_cards.getCartes()[0]);*/
                    //my_cards = JSONUtils.extractCards(FIELD_CARDS, objects[0].toString());
                    System.out.println(my_cards);
                    System.out.println("Première carte");/*
                    System.out.println(Remote.my_cards.getCartes()[0]);*/
                    //my_cards = JSONUtils.extractCards(FIELD_CARDS, objects[0].toString());
                }
            }
            if (event.equals(MY_CARDS_PERSO)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("CartePerso");
                    my_cards_all.add(JSONUtils.extractString("cartes", objects[0].toString()));
                    my_cards_perso.add(JSONUtils.extractString("cartes", objects[0].toString()));
                    System.out.println(my_cards_perso);
                }
            }
            if (event.equals(MY_CARDS_ARME)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("CarteArme");
                    my_cards_all.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    my_cards_arme.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    System.out.println(my_cards_arme);
                }
            }
            if (event.equals(MY_CARDS_PIECE)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("CartePiece");
                    my_cards_all.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    my_cards_piece.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                    System.out.println(my_cards_piece);
                }
            }
            if (event.equals(CHOICE_PERSO)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("Perso");
                    perso_for_supposition.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                }
            }
            if (event.equals(CHOICE_ARME)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                System.out.println(mon_id);
                System.out.println(id_joueur_actuel);
                if (mon_id == id_joueur_actuel) {
                    System.out.println("Arme");
                    arme_for_supposition.add(JSONUtils.extractString(FIELD_CARDS, objects[0].toString()));
                }
            }
            /* ------ CHANGEMENT DE SCREEN ------ */
            if (event.equals(PLAYER_READY)){
                initAttributes();
                System.out.println("Ready");
                Intent intent = new Intent(context, WaitingLogActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(WAITING_INIT)) { // MISE EN PLACE DES PIONS
                Intent intent = new Intent(context, WaitingPionsActivity.class);
                context.startActivity(intent);
            }
            if(event.equals(BEGIN_GAME)){
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                //System.out.println("id reçu "+id_joueur_actuel+" mon id "+id_joueur_actuel);
                //case_actuel = (int) JSONUtils.extractLong(FIELD_ID_CASE, objects[0].toString());

                //Intent intent = new Intent(context, DiceActivity.class);
                //context.startActivity(intent);

                if (mon_id == id_joueur_actuel) {
                    //les_cases = JSONUtils.extractCase(FIELD_ID_CASE, objects[0].toString());
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
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(MOVED_TURN)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    les_cases = JSONUtils.extractCase(FIELD_ID_CASE, objects[0].toString());
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
                    les_cases = JSONUtils.extractCase(FIELD_ID_CASE, objects[0].toString());
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
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    ma_supposition = new String[3];
                    ma_supposition[2] = Remote.les_cases.getCases()[0].getNom();
                    Intent intent = new Intent(context, SuppositionActivity.class);
                    context.startActivity(intent);
                }
            }
            if (event.equals(ACCUSATION_TURN)) { // DEBUT DE LA PARTIE
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
                Intent intent = new Intent(context, AccusedActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(REICEVE_CARD)) { // DEBUT DE LA PARTIE
                id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, objects[0].toString());
                if (mon_id == id_joueur_actuel) {
                    card_send = JSONUtils.extractString(FIELD_CARDS, objects[0].toString());
                    pseudo_card_send = JSONUtils.extractString("pseudo", objects[0].toString());
                    if(pseudo_card_send == ""){
                        Intent intent = new Intent(context, ReceiveNoCardActivity.class);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ReceiveCardActivity.class);
                        context.startActivity(intent);
                    }
                }
            }
            if (event.equals(END_GAME)) { // FIN DE LA PARTIE
                socket.disconnect();
                Intent intent = new Intent(context, EndGameActivity.class);
                context.startActivity(intent);
            }
        }

        @Override
        public void onConnect() {
            //System.out.println("Am i here ?");
            if (already_connect) {
                socket.emit("reconnect", mon_id);
            } else {
                //System.out.println("Where am i ?");
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
    public static void emit_lance_de() { socket.emit(DICE_ROLL, valeur_de); }
    public static void emit_not_suppose(){socket.emit(EMIT_NOT_SUPPOSE);}
    public static void emit_accusation() {
        socket.emit(EMIT_ACCUSATION);
    }
    public static void emit_supposition() {
        socket.emit(EMIT_SUPPOSITION, ma_supposition[0], ma_supposition[1], ma_supposition[2]);
    }
    public static void emit_choix_carte() {
        socket.emit(CHOIX_CARTE, mon_id, card_send);
    }
    public static void emit_tour_termine() {
        socket.emit(END_TURN);
    }


    private void initAttributes() {
        mon_tour = (mon_id == id_joueur_actuel);
        turn_moved = false;
        valeur_de = 0;
        my_cards_perso = new ArrayList<String>();
        my_cards_arme = new ArrayList<String>();
        my_cards_piece = new ArrayList<String>();
        my_cards_all = new ArrayList<String>();
        perso_for_supposition = new ArrayList<String>();
        arme_for_supposition = new ArrayList<String>();
    }
}
