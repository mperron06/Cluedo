package com.polytech.cluedo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class ProfilActivity extends Activity{
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private TabHost myTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : "+Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        myTabHost =(TabHost) findViewById(R.id.tabHost);
        // Before adding tabs, it is imperative to call the method setup()
        myTabHost.setup(); // Adding tabs
        // tab1 settings
        TabHost.TabSpec spec = myTabHost.newTabSpec("tab_creation"); // text and image of tab
        spec.setIndicator("Cartes",getResources().getDrawable(android.R.drawable.ic_menu_add)); // specify layout of tab
        spec.setContent(R.id.onglet1);
        // adding tab in TabHost
        myTabHost.addTab(spec);
        // otherwise :
        myTabHost.addTab(myTabHost.newTabSpec("tab_inser").setIndicator("Feuille Enquête",getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.onglet2));
        myTabHost.addTab(myTabHost.newTabSpec("tab_affiche").setIndicator("Historique",getResources().getDrawable(android.R.drawable.ic_menu_view)).setContent(R.id.onglet3));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
