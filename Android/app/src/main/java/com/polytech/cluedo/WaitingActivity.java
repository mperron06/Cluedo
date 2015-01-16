package com.polytech.cluedo;

/**
 * Created by Alexia on 15/01/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class WaitingActivity extends Activity {
    private TextView info_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Remote.context = this;

        Intent intent = getIntent();
        //String bienv = intent.getStringExtra("BIENVENUE");
        //String perso = intent.getStringExtra("PERSO");
        //String mess = intent.getStringExtra("MESSAGE");
        int message = intent.getIntExtra("MESSAGE", -1);
        int verifPer = intent.getIntExtra("PERVENCHE", -1);
        int verifMout = intent.getIntExtra("MOUTARDE", -1);
        int verifOliv = intent.getIntExtra("OLIVE", -1);
        int verifRose = intent.getIntExtra("ROSE", -1);
        int verifLeblanc = intent.getIntExtra("LEBLANC", -1);
        int verifViolet = intent.getIntExtra("VIOLET", -1);
        System.out.println(message);


        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        info_textView = new TextView(this);
        if (verifPer == 1){
                info_textView.setText(R.string.madamePervenche);
            }
        if (verifMout == 1){
            info_textView.setText(R.string.colonelMoutarde);
        }
        if (verifRose == 1){
            info_textView.setText(R.string.mlleRose);
        }
        if (verifLeblanc == 1){
            info_textView.setText(R.string.madameLeblanc);
        }
        if (verifOliv == 1){
            info_textView.setText(R.string.docteurOlive);
        }
        if (verifViolet == 1){
            info_textView.setText(R.string.profViolet);
        }
        //info_textView.setText((message == -1) ? R.string.madamePervenche : message);
        info_textView.setGravity(Gravity.CENTER);
        info_textView.setTextSize(12);

        this.addContentView(info_textView, params);
    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
