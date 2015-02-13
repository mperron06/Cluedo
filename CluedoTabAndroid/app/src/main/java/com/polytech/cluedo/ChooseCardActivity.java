package com.polytech.cluedo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class ChooseCardActivity extends Activity {

    final Context context = this;
    private Button retour;
    ImageView background;
    IdentifyCard ident = new IdentifyCard();
    RelativeLayout relativeLayout;
    LinearLayout rowCards1;
    ImageButton option1;
    ImageButton option2;
    ImageButton option3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);
        //retour = (Button) findViewById(R.id.retour_button);

        //retour.setOnClickListener(onClick);
        instantiateBandeau();
        relativeLayout = (RelativeLayout) findViewById(R.id.relatLayGlobal);
        rowCards1 = (LinearLayout) findViewById(R.id.rowCards);
        option1 = (ImageButton) findViewById(R.id.chooseOption1);
        option2 = (ImageButton) findViewById(R.id.chooseOption2);
        option3 = (ImageButton) findViewById(R.id.chooseOption3);


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
                case R.id.ok_button:
                    //onBackPressed();
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

        hello.setText(Remote.mon_pseudo);
        persoName.setText(Remote.perso);
        profil_picture.setImageResource(ident.findImage(Remote.perso));
        LinearLayout linear = (LinearLayout) findViewById(R.id.bandeauLinear);
        linear.setBackgroundColor(ident.findColor(Remote.perso));
    }

    public void drawCards() throws JSONException {
        String nom;
        int id;
        int length = Remote.cardSupp.length();
        JSONObject jsonObj = Remote.cardSupp.getJSONObject(0);
        System.out.println(length);
        id = ident.findImage(jsonObj.toString());
        option1.setImageResource(id);
        option2.setImageResource(R.drawable.corde);
        if (length > 0){
            option3.setVisibility(View.INVISIBLE);
            JSONObject jsonObj2 = Remote.cardSupp.getJSONObject(1);
            //id = ident.findImage(jsonObj2.toString());
            //option2.setImageResource(id);
        }
        if (length > 1){
            JSONObject jsonObj3 = Remote.cardSupp.getJSONObject(2);
            //id = ident.findImage(jsonObj3.toString());
            //option3.setImageResource(id);
        }

        /*for(int i = 0; i < length; i++){
            JSONObject jsonObj = Remote.cardSupp.getJSONObject(i);
            id = ident.findImage(jsonObj.toString());
            ImageButton card = new ImageButton(this);
            card.setImageResource(id);
            RelativeLayout rl = new RelativeLayout(this);
            rl.addView(card);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f);
            rowCards1.addView(rl,lp);
        }*/

    }


}
