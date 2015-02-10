package com.polytech.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ShortcutActivity extends Activity {
    private TextView pseudo_editText;
    private TextView perso_editText;
    private ImageView profil_picture;

    private RadioGroup rg = null;
    private RadioButton radioButton;
    private Button validerButton;

    private RadioButton moved;
    private RadioButton shortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        // FindView
        pseudo_editText = (TextView) findViewById(R.id.pseudoText);
        perso_editText = (TextView) findViewById(R.id.persoText);
        profil_picture = (ImageView) findViewById(R.id.imageView1);

        pseudo_editText.setText(Remote.mon_pseudo);
        perso_editText.setText("personnage : " + Remote.mon_perso);
        profil_picture.setImageResource((getResources().getIdentifier("profil_"+Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

        moved = (RadioButton) findViewById(R.id.moved);
        shortcut = (RadioButton) findViewById(R.id.short_cut);
        if(Remote.cases_shortcut.size() == 1){
            moved.setText(Remote.cases_shortcut.get(0).toString());
            shortcut.setVisibility(View.GONE);
        } else if(Remote.cases_shortcut.size() == 2){
            moved.setText(Remote.cases_shortcut.get(0).toString());
            shortcut.setText(Remote.cases_shortcut.get(1).toString());
        } else {
            moved.setVisibility(View.GONE);
            shortcut.setVisibility(View.GONE);
        }

        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shortcut, menu);
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

    public void addListenerOnButton() {

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        validerButton = (Button) findViewById(R.id.validerRadioButton);

        validerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    System.out.println("Ici dans on click listener");
                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(ShortcutActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();


                if(radioButton.getId() == R.id.none){
                    System.out.println("1 if");
                    Intent intent = new Intent(Remote.context, DiceActivity.class);
                    Remote.context.startActivity(intent);
                } else if(radioButton.getId() == R.id.moved){
                    System.out.println("2 if");
                    // enregistrer le lieu et lancer la supposition
                    Remote.ma_supposition = new String[3];
                    Remote.ma_supposition[2] = moved.getText().toString();

                    Remote.valeur_de = 0;
                    Remote.emit_lance_de();

                    Intent intent = new Intent(Remote.context, WaitShortCutActivity.class);
                    Remote.context.startActivity(intent);
                } else if(radioButton.getId() == R.id.short_cut){
                    System.out.println("3 if");
                    Remote.ma_supposition = new String[3];
                    Remote.ma_supposition[2] = shortcut.getText().toString();

                    Intent intent = new Intent(Remote.context, WaitShortCutActivity.class);
                    Remote.context.startActivity(intent);

                    Remote.valeur_de = 0;
                    Remote.emit_lance_de();
                }
            }

        });

    }
    public void valider(View v) {
        System.out.println("valider button");
        if(radioButton.getId() == R.id.none){
            System.out.println("1 if");
            Intent intent = new Intent(Remote.context, DiceActivity.class);
            Remote.context.startActivity(intent);
        } else if(radioButton.getId() == R.id.moved){
            System.out.println("2 if");
            // enregistrer le lieu et lancer la supposition
            Remote.ma_supposition = new String[3];
            Remote.ma_supposition[2] = moved.getText().toString();
            Intent intent = new Intent(Remote.context, SuppositionActivity.class);
            Remote.context.startActivity(intent);
        } else if(radioButton.getId() == R.id.short_cut){
            System.out.println("3 if");
            Remote.ma_supposition = new String[3];
            Remote.ma_supposition[2] = shortcut.getText().toString();
            Intent intent = new Intent(Remote.context, SuppositionActivity.class);
            Remote.context.startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
