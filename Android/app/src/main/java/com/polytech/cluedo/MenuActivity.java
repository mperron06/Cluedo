package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MenuActivity extends Activity {


    private Button rollDice_button;
    private String myPseudo = Remote.pseudo;
    private String myPerso = Remote.perso;
    private TextView pseudo;
    private TextView perso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        rollDice_button = (Button) findViewById(R.id.rollDice_button);
        rollDice_button.setOnClickListener(onClick);
        pseudo = (TextView) findViewById(R.id.pseudoText);
        perso = (TextView) findViewById(R.id.persoText);
        pseudo.setText(myPseudo);
        perso.setText(myPerso);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.rollDice_button:
                    int valeur = lancerDes();
                    //System.out.println("Bravo");
                    Remote.valeur_de = valeur;
                    Remote.emit_lance_de();
                    break;
                default:
                    break;
            }
        }
    };

    public int lancerDes (){
        int result = 0;
        Random r = new Random();
        int min = 2;
        int max = 12;
        result = r.nextInt((max - min + 1) + min);
        System.out.println(result);
        return result;
    }
}
