package com.polytech.cluedo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class SuppositionActivity extends Activity implements ActionBar.TabListener{
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supposition);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : " + Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier(Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab().setText("Supposition")
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("Cartes")
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("Feuille Enquête")
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText("Historique")
                    .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_supposition, menu);
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //mViewPager.setCurrentItem(tab.getPosition());
        // Créez une transaction pour changer le fragment courant
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(tab.getPosition() == 0) {
            transaction.replace(R.id.fragment_container, new SuppositionFragment());
        } else if (tab.getPosition() == 1) {
            transaction.replace(R.id.fragment_container, new CartesFragment());
        } else if (tab.getPosition() == 2) {
            transaction.replace(R.id.fragment_container, new FeuilleFragment());
        } else {
            transaction.replace(R.id.fragment_container, new HistoriqueFragment());
        }
        // Appliquez les changements
        transaction.commit();

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
