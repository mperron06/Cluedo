package com.polytech.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EndGameActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private TextView pseudo_gagnant;
    //private TextView persoAccuseText;
    private ImageView persoView;
    //private TextView armeAccuseText;
    private ImageView armeView;
    //private TextView pieceAccuseText;
    private ImageView pieceView;

    private Button end_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);
        pseudo_gagnant = (TextView) findViewById(R.id.pseudo_gagnant);
        //persoAccuseText = (TextView) findViewById(R.id.persoAccuseText);
        persoView = (ImageView) findViewById(R.id.persoView);
        //armeAccuseText = (TextView) findViewById(R.id.armeAccuseText);
        armeView = (ImageView) findViewById(R.id.armeView);
        //pieceAccuseText = (TextView) findViewById(R.id.pieceAccuseText);
        pieceView = (ImageView) findViewById(R.id.pieceView);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : "+Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        pseudo_gagnant.setText(Remote.accusations[0]);
        //persoAccuseText.setText(Remote.accusations[1]);
        //armeAccuseText.setText(Remote.accusations[2]);
        //pieceAccuseText.setText(Remote.accusations[3]);

        persoView.setImageResource((getResources().getIdentifier(Remote.accusations[1].toLowerCase().replace(" ",""), "drawable", getPackageName())));
        armeView.setImageResource((getResources().getIdentifier(Remote.accusations[2].toLowerCase().replace(" ",""), "drawable", getPackageName())));
        pieceView.setImageResource((getResources().getIdentifier(Remote.accusations[3].toLowerCase().replace(" ",""), "drawable", getPackageName())));

        end_button = (Button) findViewById(R.id.validerButton);
        // Listener
        end_button.setOnClickListener(onClick);

        LinearLayout layoutActivity = (LinearLayout) findViewById(R.id.headActivity);
        String perso_temp = Remote.mon_perso.toLowerCase();
        if (perso_temp.equals("leblanc")){
            layoutActivity.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else if (perso_temp.equals("moutarde")){
            layoutActivity.setBackgroundColor(Color.parseColor("#FFFF00"));
        } else if (perso_temp.equals("olive")){
            layoutActivity.setBackgroundColor(Color.parseColor("#00FF00"));
        } else if (perso_temp.equals("pervenche")){
            layoutActivity.setBackgroundColor(Color.parseColor("#0000FF"));
        } else if (perso_temp.equals("rose")){
            layoutActivity.setBackgroundColor(Color.parseColor("#FF00FF"));
        } else { //violet
            layoutActivity.setBackgroundColor(Color.parseColor("#7F00FF"));
        }
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

    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.validerButton: //fin de partie
                    Remote.emit_new_game();
                    Intent intent = new Intent(EndGameActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
