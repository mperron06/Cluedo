package com.polytech.cluedo;

/**
 * Created by Alexia on 09/01/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.MalformedURLException;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class Remote {

    private static volatile Remote instance;
    private static SocketIO socket;
    public static String url;
    public static String pseudo;
    public static String perso;
    public static Context context;
    public static boolean already_connect;
    public static long mon_id;
    public static String mon_perso;


    private static final String MY_ID = "myId";
    private static final String MY_PERSO = "myPerso";
    private static final String PLAYER_READY = "joueursPrets";

    private static final String ADD_PLAYER = "addPlayer";

    private static final String PERVENCHE = "Madame Pervenche";


    final IOCallback ioCallback = new IOCallback() {

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
            Intent intent = new Intent(context, WaitingActivity.class);
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
        public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
            			/* ------ RECUP INFOS ------ */
            if (event.equals(MY_ID)) { // ID
                mon_id = Long.parseLong(objects[0].toString());
            }
            /*if (event.equals(MY_PERSO)){

            }*/
            /*if (event.equals(PLAYER_READY)){
                System.out.println("Ready");
                Intent intent = new Intent(context, WelcomeActivity.class);
                if (perso.equals("Pervenche")){
                    System.out.println("Miss Pervenche");
                    intent.putExtra("MESSAGE", R.string.madamePervenche);
                }
                context.startActivity(intent);
            }*/
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
}
