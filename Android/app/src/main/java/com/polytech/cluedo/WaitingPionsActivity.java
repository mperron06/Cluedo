package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WaitingPionsActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;
    public IdentifyCard ident;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_pions);
        ident = new IdentifyCard();

        // FindView
        /*pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);*/
        profil_picture = (ImageView) findViewById(R.id.imagePerso);
        linear = (LinearLayout) findViewById(R.id.waitingPionsLinear);

        /*pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText(Remote.perso);*/
        profil_picture.setImageResource(ident.findImage(Remote.perso));
        linear.setBackgroundColor(ident.findColor(Remote.perso));
    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }

}
