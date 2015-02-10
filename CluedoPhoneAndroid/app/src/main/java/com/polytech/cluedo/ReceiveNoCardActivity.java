package com.polytech.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ReceiveNoCardActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_no_card);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : "+Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_no_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void endTurn(View v) {
        if(!Remote.accusation_turn) {
            Remote.turn_moved = false;
            Remote.valeur_de = 0;

            Remote.perso_for_supposition = new ArrayList<String>();
            Remote.arme_for_supposition = new ArrayList<String>();
            Remote.historique_arme = new ArrayList<String>();
            Remote.historique_perso = new ArrayList<String>();
            Remote.historique_piece = new ArrayList<String>();
            Remote.my_accused = new ArrayList<String>();
            Remote.card_send ="";
            Remote.pseudo_card_send="";
            Remote.cases_shortcut = new ArrayList<String>();

            Remote.emit_tour_termine();

            Intent intent = new Intent(Remote.context, ProfilActivity.class);
            Remote.context.startActivity(intent);
        } else {
            Remote.emit_end_game();
        }
    }
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
