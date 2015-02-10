package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class EndGameActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private TextView pseudo_gagnant;
    private TextView persoAccuseText;
    private ImageView persoView;
    private TextView armeAccuseText;
    private ImageView armeView;
    private TextView pieceAccuseText;
    private ImageView pieceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);
        pseudo_gagnant = (TextView) findViewById(R.id.pseudo_gagnant);
        persoAccuseText = (TextView) findViewById(R.id.persoAccuseText);
        persoView = (ImageView) findViewById(R.id.persoView);
        armeAccuseText = (TextView) findViewById(R.id.armeAccuseText);
        armeView = (ImageView) findViewById(R.id.armeView);
        pieceAccuseText = (TextView) findViewById(R.id.pieceAccuseText);
        pieceView = (ImageView) findViewById(R.id.pieceView);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : "+Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        pseudo_gagnant.setText(Remote.accusations[0]);
        persoAccuseText.setText(Remote.accusations[1]);
        armeAccuseText.setText(Remote.accusations[2]);
        pieceAccuseText.setText(Remote.accusations[3]);

        persoView.setImageResource((getResources().getIdentifier(Remote.accusations[1].toLowerCase().replace(" ",""), "drawable", getPackageName())));
        armeView.setImageResource((getResources().getIdentifier(Remote.accusations[2].toLowerCase().replace(" ",""), "drawable", getPackageName())));
        pieceView.setImageResource((getResources().getIdentifier(Remote.accusations[3].toLowerCase().replace(" ",""), "drawable", getPackageName())));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_game, menu);
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
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
    // Fin de la partie
    public void fermer(){
        Remote.emit_new_game();;
    }
}
