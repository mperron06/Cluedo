package com.polytech.cluedo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SuppositionActivity extends Activity {

    final Context context = this;
    private Button valid;
    private RadioButton roseBout;
    private RadioButton pervenBout;
    private RadioButton leblBout;
    String persoHypoth;
    ImageView roseImg;
    ImageView roseImg2;
    ImageView perImg;
    ImageView leblImg;
    ImageView olivImg;
    ImageView violetImg;
    ImageView moutImg;
    private static final String IMAGEVIEW_TAG = "The Android Logo";
    TextView tv;
    ImageView background;
    IdentifyCard ident;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supposition);
        valid = (Button) findViewById(R.id.ok_button);
        valid.setOnClickListener(onClick);
        roseImg = (ImageView) findViewById(R.id.cardPerso1);
        roseImg2 = (ImageView) findViewById(R.id.cardPerso11);
        leblImg = (ImageView) findViewById(R.id.cardPerso3);
        olivImg = (ImageView) findViewById(R.id.cardPerso4);
        violetImg = (ImageView) findViewById(R.id.cardPerso5);
        moutImg = (ImageView) findViewById(R.id.cardPerso6);

        roseImg.setOnLongClickListener(new MyClickListener());
        roseImg2.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.card_layout).setOnDragListener(new MyDragListener());
        findViewById(R.id.card_Future).setOnDragListener(new MyDragListener());
        leblImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardLebLayout).setOnDragListener(new MyDragListener());

        roseImg.setTag(IMAGEVIEW_TAG);
        roseImg2.setTag(IMAGEVIEW_TAG);
        leblImg.setTag(IMAGEVIEW_TAG);

        tv = (TextView) findViewById(R.id.textSuppos);
        tv.setText(Remote.myRoom);
        background = (ImageView) findViewById(R.id.imageViewBackground);
        ident = new IdentifyCard();
        background.setImageResource(ident.findImage(Remote.myRoom));

        instantiateBandeau();


    }

    private final class MyClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {

            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, shadowBuilder, view, 0);

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }


    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.ok_button:
                    //Remote.persoHypo = persoHypoth;
                    Remote.persoHypo = "Rose";
                    Remote.armeHypo = "Revolver";
                    Remote.lieuHypo = "Bureau";
                    Remote.emit_supposition();
                    Remote.emit_tour_termine();
                    break;
                default:
                    break;
            }
        }
    };

    private class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    if (v == findViewById(R.id.card_Future)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);


                        RelativeLayout containView = (RelativeLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
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
}
