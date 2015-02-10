package com.polytech.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class AccusedActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private ImageView persoView;
    private ImageView armeView;
    private ImageView pieceView;

    private String temp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accused);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        persoView = (ImageView) findViewById(R.id.persoView);
        armeView = (ImageView) findViewById(R.id.armeView);
        pieceView = (ImageView) findViewById(R.id.pieceView);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : " + Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        System.out.println(Remote.my_accused);

        if(Remote.my_accused.size()==3){
            temp = Remote.my_accused.get(0).toLowerCase().replace(" ","");
            System.out.println(temp);
            persoView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
            temp = Remote.my_accused.get(1).toLowerCase().replace(" ","");
            System.out.println(temp);
            armeView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
            temp = Remote.my_accused.get(2).toLowerCase().replace(" ","");
            System.out.println(temp);
            pieceView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
        } else  if(Remote.my_accused.size()==2){
            temp = Remote.my_accused.get(0).toLowerCase().replace(" ","");
            System.out.println(temp);
            persoView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
            temp = Remote.my_accused.get(1).toLowerCase().replace(" ","");
            System.out.println(temp);
            armeView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
            pieceView.setVisibility(View.GONE);
        } else {
            System.out.println(Remote.my_accused.get(0).toLowerCase().replace(" ",""));
            temp = Remote.my_accused.get(0).toLowerCase().replace(" ","");
            System.out.println(temp);
            persoView.setImageResource(getResources().getIdentifier(temp, "drawable", getPackageName()));
            armeView.setVisibility(View.GONE);
            pieceView.setVisibility(View.GONE);
        }
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
    @Override
    public void onBackPressed() {
        // Do Nothing
    }

    public void validerChoix1(View v) {
        Remote.card_send = Remote.my_accused.get(0).toString();

        Remote.emit_choix_carte();

        Intent intent = new Intent(Remote.context, ProfilActivity.class);
        Remote.context.startActivity(intent);
    }
    public void validerChoix2(View v) {
        Remote.card_send = Remote.my_accused.get(1).toString();

        Remote.emit_choix_carte();

        Intent intent = new Intent(Remote.context, ProfilActivity.class);
        Remote.context.startActivity(intent);
    }
    public void validerChoix3(View v) {
        Remote.card_send = Remote.my_accused.get(2).toString();

        Remote.emit_choix_carte();

        Intent intent = new Intent(Remote.context, ProfilActivity.class);
        Remote.context.startActivity(intent);
    }
}
