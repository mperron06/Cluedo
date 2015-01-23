package com.polytech.cluedo;

/**
 * Created by Alexia on 09/01/2015.
 */
import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import java.net.MalformedURLException;

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
    private static final String MY_PERSO = "myPerso";

    /* ------ CHANGEMENT DE SCREEN ------ */
    private static final String NEW_TAG = "nouveauTag";
    private static final String PLAYER_READY = "joueurReady";
    private static final String BEGIN_GAME = "joueursPrets";
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

    // CASES
    private static final String CASE_BEGIN = "caseDepart";

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
    private static final String FIELD_IN_EXAMS = "enExams";
    private static final String FIELD_MONEY = "argent";
    private static final String FIELD_TURN_IN_EXAMS = "nbToursExams";
    //	private static final String FIELD_NUMBER_OF_PROPERTIES = "nbProprietes";
    private static final String FIELD_SUM = "somme";
    private static final String FIELD_PASS_YOUR_TURN = "passeTour";
    private static final String FIELD_CARD_DESCRIPTION = "description";



    public static String url;
    public static String pseudo;
    public static String perso;
    public static Context context;
    public static boolean already_connect;

    public static long mon_id;
    public static String mon_perso;
    public static long id_joueur_actuel;
    //public static ArrayCase les_cases;
    //public static ArrayPlayer les_joueurs;

    public static boolean mon_tour;
    public static boolean de_lance;
    public static boolean de_lance_exam;
    public static int valeur_de;

    public static boolean proposition_achat;
    public static boolean achat_impossible;
    public static boolean proposition_vente;

    public static boolean carte_chance;
    public static boolean carte_chance_tiree;
    public static boolean secher_cours;

    public static boolean rentree;
    public static boolean revisions;
    public static boolean session_exams;
    public static boolean nuit_info;
    public static boolean bourse_merite;
    //	public static boolean en_exam;
    public static boolean tour_en_exam;
    public static boolean frais_scolarite;

    public static boolean paiement;
    public static boolean nouvelle_propriete;
    public static String texte;


    private static final String PERVENCHE = "Madame Pervenche";


    final IOCallback ioCallback = new IOCallback() {

        @Override
        public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
            			/* ------ RECUP INFOS ------ */
            if (event.equals(MY_ID)) { // ID
                mon_id = Long.parseLong(objects[0].toString());
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
                if (perso.equals("Pervenche")){
                intent.putExtra("PERVENCHE", 1);
                //intent.putExtra("MESSAGE", R.string.madamePervenche);
            }
            if (perso.equals("Moutarde")){
                intent.putExtra("MOUTARDE", 1);
            }
            if (perso.equals("Leblanc")){
                intent.putExtra("LEBLANC", 1);
            }
            if (perso.equals("Rose")){
                intent.putExtra("ROSE", 1);
            }

            if (perso.equals("Olive")){
                intent.putExtra("OLIVE", 1);
            }

            if (perso.equals("Violet")){
                intent.putExtra("VIOLET", 1);
            }
                context.startActivity(intent);

            }
            if (event.equals(BEGIN_GAME)) { // DEBUT DE LA PARTIE
                /*id_joueur_actuel = JSONUtils.extractLong(FIELD_ID_JOUEUR, args[0].toString());
                initAttributes();*/

                Intent intent = new Intent(context, ProfilActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(MOVED_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, MovedActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(SHORTCUT_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, ShortcutActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(DICE_TURN)) { // DEBUT DE LA PARTIE
                Intent intent = new Intent(context, DiceActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(SUPPOSITION_TURN)) { // DEBUT DE LA PARTIE
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
                Intent intent = new Intent(context, ReceiveCardActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(END_GAME)) { // FIN DE LA PARTIE
                socket.disconnect();
                Intent intent = new Intent(context, EndGameActivity.class);
                context.startActivity(intent);
            }
            if (event.equals(NEXT_PLAYER)){
                id_joueur_actuel = Long.parseLong(objects[0].toString());
                if (id_joueur_actuel == mon_id){
                    Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, WaitingActivity.class);
                    context.startActivity(intent);
                }
            }
        }

        @Override
        public void onConnect() {
            //System.out.println("Am i here ?");
            if (already_connect) {
                socket.emit("reconnect", mon_id);
            } else {
                //System.out.println("Where am i ?");
                socket.emit(ADD_PLAYER, pseudo, perso);
                already_connect = true;
            }
            // Login to Waiting
            Intent intent = new Intent(context, WaitingLogActivity.class);
            if (perso.equals("Pervenche")){
                intent.putExtra("PERVENCHE", 1);
            }
            if (perso.equals("Moutarde")){
                intent.putExtra("MOUTARDE", 1);
            }
            if (perso.equals("Leblanc")){
                intent.putExtra("LEBLANC", 1);
            }
            if (perso.equals("Rose")){
                intent.putExtra("ROSE", 1);
            }

            if (perso.equals("Olive")){
                intent.putExtra("OLIVE", 1);
            }

            if (perso.equals("Violet")){
                intent.putExtra("VIOLET", 1);
            }
            //intent.putExtra("MESSAGE", R.string.waitingForPlayers);
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
    public static void emit_accusation() {
        socket.emit(EMIT_ACCUSATION);
    }
    public static void emit_supposition() {
        socket.emit(EMIT_SUPPOSITION);
    }
    public static void emit_choix_carte() {
        socket.emit(CHOIX_CARTE);
    }
    public static void emit_tour_termine() {
        socket.emit(END_TURN);
    }
}
