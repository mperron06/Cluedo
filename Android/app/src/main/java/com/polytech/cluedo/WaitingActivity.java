package com.polytech.cluedo;

/**
 * Created by Alexia on 15/01/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class WaitingActivity extends Activity {
    private TextView info_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Remote.context = this;

        Intent intent = getIntent();

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        info_textView = new TextView(this);
        info_textView.setText("Ce n'est pas encore votre tour, attendez svp");
        info_textView.setGravity(Gravity.CENTER);
        info_textView.setTextSize(12);

        this.addContentView(info_textView, params);
    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
