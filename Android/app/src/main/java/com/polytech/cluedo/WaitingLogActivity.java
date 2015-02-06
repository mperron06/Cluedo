package com.polytech.cluedo;

/**
 * Created by Alexia on 15/01/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WaitingLogActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;
    public IdentifyCard ident;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_log);

        ident = new IdentifyCard();

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imagePerso);
        linear = (LinearLayout) findViewById(R.id.waitingLogLinear);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText(Remote.perso);
        profil_picture.setImageResource(ident.findImage(Remote.perso));
        linear.setBackgroundColor(ident.findColor(Remote.perso));


    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
