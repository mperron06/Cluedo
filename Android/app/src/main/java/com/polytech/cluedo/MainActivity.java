package com.polytech.cluedo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polytech.utils.Carte;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    final Context context = this;
    private Button retour;
    private ImageView card1View;
    IdentifyCard ident = new IdentifyCard();
    private static final String TAG_NOM = "nom";
    GridLayout grd;
    RelativeLayout relativeLayout;
    RelativeLayout rowCards2;
    LinearLayout rowCards1;
    LinearLayout rowCards2Linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retour = (Button) findViewById(R.id.retour_button);

        retour.setOnClickListener(onClick);
        instantiateBandeau();
        relativeLayout = (RelativeLayout) findViewById(R.id.relatLayGlobal);
        rowCards2 = (RelativeLayout) findViewById(R.id.rowCardsBonus);
        rowCards1 = (LinearLayout) findViewById(R.id.rowCards);
        rowCards2Linear = (LinearLayout) findViewById(R.id.rowCardsLayout2);
        //grd = (GridLayout) findViewById(R.id.gridLayout);
        //card1View = (ImageView) findViewById(R.id.card1);
        try {
            drawCards();
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.retour_button:
                    onBackPressed();
                    break;
                default:
                    break;
            }
        }
    };

    public void instantiateBandeau(){
        TextView hello = (TextView) findViewById(R.id.Hello);
        TextView persoName = (TextView) findViewById(R.id.persoName);

        ImageView profil_picture = (ImageView) findViewById(R.id.imageView1);
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String pseud = sharedPref.getString(getString(R.string.keyPseudo),"b");
        String persName = sharedPref.getString(getString(R.string.keyPersoName), "c");
        hello.setText(pseud);
        persoName.setText(persName);
        //profil_picture.setImageResource((getResources().getIdentifier( Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));
    }

    public void drawCards() throws JSONException {
        String nom;
        int id;
        int length = Remote.cards.length();
        if (length <= 5){
            ViewGroup viewgroup = (ViewGroup) rowCards2.getParent();
            viewgroup.removeView(rowCards2);
        }
        //for(int i = 0; i < length; i++){
        for(int i = 0; i < 5; i++){
            JSONObject jsonObj = Remote.cards.getJSONObject(i);
            nom = jsonObj.getString(TAG_NOM);
            id = ident.findImage(nom);
            ImageView card = new ImageView(this);
            card.setImageResource(id);
            RelativeLayout rl = new RelativeLayout(this);
            rl.addView(card);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f);
            rowCards1.addView(rl,lp);
        }
        if (length > 5){
            for(int i = 5; i < length; i++){
                JSONObject jsonObj = Remote.cards.getJSONObject(i);
                nom = jsonObj.getString(TAG_NOM);
                id = ident.findImage(nom);
                ImageView card = new ImageView(this);
                card.setImageResource(id);
                RelativeLayout rl = new RelativeLayout(this);
                rl.addView(card);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f);
                rowCards2Linear.addView(rl,lp);
            }
        }


    }
}
