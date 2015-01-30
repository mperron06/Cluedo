package com.polytech.cluedo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class GlobalActivity extends Activity {
    private Button main;
    private Button not;
    private TextView hello;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_global);

            main = (Button) findViewById(R.id.hand_button);
        not = (Button) findViewById(R.id.note_button);

            RelativeLayout rlDice = (RelativeLayout) findViewById(R.id.diceButtonLayout);
            rlDice.setVisibility(View.INVISIBLE);
            ImageView imgBack = (ImageView) findViewById(R.id.imageViewBackground);
            imgBack.setImageResource(R.drawable.ic_launcher);

            instantiateBandeau();

            main.setOnClickListener(onClick);
        not.setOnClickListener(onClick);


    }

    public void instantiateBandeau(){
        TextView hello = (TextView) findViewById(R.id.Hello);
        TextView persoName = (TextView) findViewById(R.id.persoName);
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String pseud = sharedPref.getString(getString(R.string.keyPseudo),"b");
        String persName = sharedPref.getString(getString(R.string.keyPersoName), "c");
        hello.setText(pseud);
        persoName.setText(persName);
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
}
