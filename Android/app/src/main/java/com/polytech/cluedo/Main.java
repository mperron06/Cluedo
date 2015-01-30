package com.polytech.cluedo;

/**
 * Created by Fujitsu on 29/01/2015.
 */
public class Main {

    private static volatile Main instance;

    private Main() {
        /*Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);*/
        this.initializeMain();
    }

    public static synchronized Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public void initializeMain(){

    }

}
