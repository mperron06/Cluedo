package com.polytech.cluedo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class EnqueteActivity extends Activity {

    final Context context = this;
    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquete);
        retour = (Button) findViewById(R.id.retour_button);
        retour.setOnClickListener(onClick);
        instantiateBandeau();
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
        //profil_picture.setImageResource((getResources().getIdentifier( Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));
        persoName.setText(persName);
    }



}
