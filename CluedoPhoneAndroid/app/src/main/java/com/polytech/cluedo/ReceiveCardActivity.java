package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ReceiveCardActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private TextView carte_pseudo;
    private ImageView carte_image;
    private TextView carte_text;

    private ImageView end_turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_card);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);
        carte_pseudo = (TextView) findViewById(R.id.textView1);
        carte_image = (ImageView) findViewById(R.id.imageView2);
        carte_text = (TextView) findViewById(R.id.textView2);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : " + Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        carte_pseudo.setText(Remote.pseudo_card_send);
        carte_text.setText(Remote.card_send);
        String temp = Remote.card_send.replace(" ", "").toLowerCase();
        System.out.println(temp);
        carte_image.setImageResource((getResources().getIdentifier(temp, "drawable", getPackageName())));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_card, menu);
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

        //Intent intent = new Intent(Remote.context, ProfilActivity.class);
        //Remote.context.startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
