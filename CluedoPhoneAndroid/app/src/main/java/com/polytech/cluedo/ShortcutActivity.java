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
        profil_picture.setImageResource((getResources().getIdentifier(Remote.mon_perso.toLowerCase(), "drawable", getPackageName())));

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

                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(ShortcutActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }
    public void valider(View v) {
        if(radioButton.getId() == R.id.none){
            Intent intent = new Intent(Remote.context, DiceActivity.class);
            Remote.context.startActivity(intent);
        } else {
            // enregistrer le lieu et lancer la supposition
        }
    }
}
