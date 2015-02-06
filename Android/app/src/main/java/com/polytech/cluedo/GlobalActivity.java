package com.polytech.cluedo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class GlobalActivity extends Activity {
    private Button main;
    private Button not;
    private Button dice;
    private Button shortcut;
    private Button aside;
    private Button suppose;
    private TextView hello;
    private EditText debug;
    RelativeLayout rlDice;
    RelativeLayout rlshortcut;
    RelativeLayout rlaside;
    RelativeLayout rlsuppose;
    final Context context = this;
    ImageView background;
    IdentifyCard ident;
    RelativeLayout rlGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_global);

            main = (Button) findViewById(R.id.hand_button);
        not = (Button) findViewById(R.id.note_button);
        dice = (Button) findViewById(R.id.dice_button);
        shortcut = (Button) findViewById(R.id.shortcut_button);
        aside = (Button) findViewById(R.id.aside_button);
        suppose = (Button) findViewById(R.id.suppose_button);

        ident = new IdentifyCard();

        dice.setOnClickListener(onClick);
        shortcut.setOnClickListener(onClick);
        aside.setOnClickListener(onClick);
        suppose.setOnClickListener(onClick);

        rlDice = (RelativeLayout) findViewById(R.id.diceButtonLayout);
        rlshortcut = (RelativeLayout) findViewById(R.id.shortcutLayout);
        rlaside = (RelativeLayout) findViewById(R.id.asideLayout);
        rlsuppose = (RelativeLayout) findViewById(R.id.supposeLayout);


            debug = (EditText) findViewById(R.id.debug_editText);
        debug.setText(Long.toString(Remote.mon_id));

            if (Remote.myTurn == false){
                rlDice.setVisibility(View.INVISIBLE);
                rlshortcut.setVisibility(View.INVISIBLE);
                rlaside.setVisibility(View.INVISIBLE);
                rlsuppose.setVisibility(View.INVISIBLE);
            }
        rlsuppose.setVisibility(View.VISIBLE);


            instantiateBandeau();

            main.setOnClickListener(onClick);
        not.setOnClickListener(onClick);

        background = (ImageView) findViewById(R.id.imageViewBackground);

        background.setImageResource(ident.findImage(Remote.myRoom));

        rlGlob = (RelativeLayout) findViewById(R.id.relatLayGlobal);
        rlGlob.setBackgroundColor(ident.findColor(Remote.perso));


    }

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


    @Override
    public void onBackPressed() {

    }

    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.hand_button:
                    showHandView();
                    break;
                case R.id.note_button:
                    Intent intent = new Intent(context,EnqueteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.dice_button:
                    int valeur = lancerDes();
                    //System.out.println(valeur);
                    //debug.setText(valeur);
                    Remote.valeur_de = valeur;
                    Remote.emit_lance_de();
                    rlDice.setVisibility(View.INVISIBLE);
                    break;
                case R.id.shortcut_button:
                    rlDice.setVisibility(View.INVISIBLE);
                    rlshortcut.setVisibility(View.INVISIBLE);
                    rlaside.setVisibility(View.INVISIBLE);
                    break;
                case R.id.aside_button:
                    rlDice.setVisibility(View.INVISIBLE);
                    rlshortcut.setVisibility(View.INVISIBLE);
                    rlaside.setVisibility(View.INVISIBLE);
                    break;
                case R.id.suppose_button:
                    Intent intent2 = new Intent(context,SuppositionActivity.class);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
        }
    };

    public void showHandView(){
        /*RelativeLayout mainLay = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLay.setVisibility(View.INVISIBLE);*/
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Bundle viewHierarchy = savedInstanceState.getBundle("android:viewHierarchyState");
            if (viewHierarchy != null) {
                SparseArray views = viewHierarchy.getSparseParcelableArray("android:views");
}
        super.onRestoreInstanceState(savedInstanceState);
    }

    public int lancerDes (){
        int result = 0;
        Random r = new Random();
        int min = 2;
        int max = 12;
        result = r.nextInt((max - min + 1) + min);
        System.out.println(result);
        return result;
    }
}
