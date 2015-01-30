package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


public class AccusedActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private RadioButton persoRadio;
    private ImageView persoView;
    private RadioButton armeoRadio;
    private ImageView armeView;
    private RadioButton pieceoRadio;
    private ImageView pieceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accused);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        persoRadio = (RadioButton) findViewById(R.id.persoRadio);
        persoView = (ImageView) findViewById(R.id.persoView);
        armeoRadio = (RadioButton) findViewById(R.id.armeRadio);
        armeView = (ImageView) findViewById(R.id.armeView);
        pieceoRadio = (RadioButton) findViewById(R.id.pieceRadio);
        pieceView = (ImageView) findViewById(R.id.pieceView);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : " + Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier(Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accused, menu);
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
}
