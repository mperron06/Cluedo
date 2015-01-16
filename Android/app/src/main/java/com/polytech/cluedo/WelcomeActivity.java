package com.polytech.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Alexia on 16/01/2015.
 */
public class WelcomeActivity extends Activity {
    private TextView info_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Remote.context = this;

        Intent intent = getIntent();
        int message = intent.getIntExtra("MESSAGE", -1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        info_textView = new TextView(this);
        info_textView.setText((message == -1) ? R.string.welcomePlayer : message);
        info_textView.setGravity(Gravity.CENTER);
        info_textView.setTextSize(30);

        this.addContentView(info_textView, params);
    }

    @Override
    public void onBackPressed() {
        // Do Nothing
    }
}
