package com.polytech.cluedo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.polytech.google.zxing.integration.android.IntentResult;
import com.polytech.google.zxing.integration.android.IntentIntegrator;

import java.util.regex.Pattern;


public class LoginActivity extends Activity {
    public static final int ACTIVITY_CODE = 1;
    private static final String PORT = "8080";

    private EditText pseudo_editText;
    private EditText ip_editText;
    private EditText perso_editText;

    private Button qr_button;
    private Button connexion_button;

    private ProgressDialog connexion_progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // FindView
        pseudo_editText = (EditText) findViewById(R.id.pseudo_editText);
        ip_editText = (EditText) findViewById(R.id.ip_editText);
        perso_editText = (EditText) findViewById(R.id.perso_editText);
        qr_button = (Button) findViewById(R.id.qr_button);
        connexion_button = (Button) findViewById(R.id.connexion_button);


        // Listener
        qr_button.setOnClickListener(onClick);
        connexion_button.setOnClickListener(onClick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE: {
                if (resultCode == RESULT_CANCELED){
                    System.out.println("Erreur");
                    Log.e("QR_CODE", "RESULT_CANCELED");
                } else {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    if (scanResult != null) {
                        System.out.println("Scan ok");
                        String global = scanResult.getContents();
                        String[] elements;
                        elements = global.split("_",2);
                        String ip = elements[0];
                        String perso = elements[1];
                        perso_editText.setText(perso);
                        Log.e("QR_CODE", ip);
                        if (checkIP(ip)){
                            ip_editText.setText(ip);
                        } else {
                            Toast.makeText(getApplicationContext(), "IP non conforme", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        dismissProgressDialog();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (!dismissProgressDialog()) {
            super.onBackPressed();
        }
    }

    private View.OnClickListener onClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.qr_button:
                    IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
                    integrator.initiateScan();
                    break;
                case R.id.connexion_button:
                    launchConnection();
                    break;
                default:
                    break;
            }
        }
    };

    private void launchConnection() {
        String pseudo = pseudo_editText.getText().toString().trim();
        String ip = ip_editText.getText().toString().trim();
        String perso = perso_editText.getText().toString().trim();


        if (pseudo == null || pseudo.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.pseudoMissed, Toast.LENGTH_SHORT).show();
            return;
        }
        if (ip == null || ip.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.ipMissed, Toast.LENGTH_SHORT).show();
            return;
        } else if (!checkIP(ip)) {
            Toast.makeText(getApplicationContext(), "IP mal ecrite", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (perso == null || perso.equals("")) {
            Toast.makeText(getApplicationContext(), "Pas de personnage", Toast.LENGTH_SHORT).show();
            return;
        }*/


        connexion_progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading. Please wait...", true);
        connexion_progressDialog .setCancelable(true);
        // Tout est bon
        Remote.mon_pseudo = pseudo;
        Remote.url = "http://" + ip + ":" + PORT;
        Remote.mon_perso = perso;
        //System.out.println("All good");
        Remote.context = this;
        Remote.reset();
        Remote.getInstance();
    }

    private boolean checkIP (String ip) {
        Pattern pattern = Pattern.compile("^\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b$");
        return pattern.matcher(ip).find();
    }

    private boolean dismissProgressDialog() {
        if (connexion_progressDialog != null && connexion_progressDialog.isShowing()) {
            connexion_progressDialog.dismiss();
            return true;
        }
        return false;
    }
}