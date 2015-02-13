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

import java.util.ArrayList;


public class SuppositionActivity extends Activity {

    final Context context = this;
    private Button valid;

    String persoHypoth;
    ImageView roseImg;
    ImageView pervImg;
    ImageView leblImg;
    ImageView olivImg;
    ImageView violetImg;
    ImageView moutImg;
    ImageView cordImg;
    ImageView revolvImg;
    ImageView chandImg;
    ImageView cleImg;
    ImageView barrImg;
    ImageView poignImg;
    private static final String IMAGEVIEW_TAG = "The Android Logo";
    ImageView background;
    IdentifyCard ident;
    ImageView cardPiece;
    ImageView tempCardPerso;
    ImageView tempCardArm;
    String[] persos;
    String[] armes;
    ArrayList<String> listPersos;
    ArrayList<String> listArmes;
    int id;
    String tempPers;
    String tempArme;
    String armeHypoth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supposition);
        valid = (Button) findViewById(R.id.ok_button);
        valid.setOnClickListener(onClick);

        roseImg = (ImageView) findViewById(R.id.Rose);
        pervImg = (ImageView) findViewById(R.id.Pervenche);
        leblImg = (ImageView) findViewById(R.id.Leblanc);
        olivImg = (ImageView) findViewById(R.id.Olive);
        violetImg = (ImageView) findViewById(R.id.Violet);
        moutImg = (ImageView) findViewById(R.id.Moutarde);

        chandImg = (ImageView) findViewById(R.id.Chandelier);
        poignImg = (ImageView) findViewById(R.id.Poignard);
        revolvImg = (ImageView) findViewById(R.id.Revolver);
        cordImg = (ImageView) findViewById(R.id.Corde);
        cleImg = (ImageView) findViewById(R.id.Cleanglaise);
        barrImg = (ImageView) findViewById(R.id.Barredefer);




        roseImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.card_layout).setOnDragListener(new MyDragListener());

        pervImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardPervLayout).setOnDragListener(new MyDragListener());

        leblImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardLebLayout).setOnDragListener(new MyDragListener());

        olivImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardOliveLayout).setOnDragListener(new MyDragListener());

        violetImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardVioletLayout).setOnDragListener(new MyDragListener());

        moutImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardMoutardeLayout).setOnDragListener(new MyDragListener());

        chandImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardChandelierLayout).setOnDragListener(new MyDragListener());

        revolvImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardRevolverLayout).setOnDragListener(new MyDragListener());

        poignImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardPoignardLayout).setOnDragListener(new MyDragListener());

        cordImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardCordeLayout).setOnDragListener(new MyDragListener());

        cleImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardCleAnglaiseLayout).setOnDragListener(new MyDragListener());

        barrImg.setOnLongClickListener(new MyClickListener());
        findViewById(R.id.cardBarredeferLayout).setOnDragListener(new MyDragListener());


        findViewById(R.id.cardFuturePerso).setOnDragListener(new MyDragListener());
        findViewById(R.id.cardFutureArme).setOnDragListener(new MyDragListener());

        roseImg.setTag(IMAGEVIEW_TAG);
        pervImg.setTag(IMAGEVIEW_TAG);
        leblImg.setTag(IMAGEVIEW_TAG);
        olivImg.setTag(IMAGEVIEW_TAG);
        moutImg.setTag(IMAGEVIEW_TAG);
        violetImg.setTag(IMAGEVIEW_TAG);

        chandImg.setTag(IMAGEVIEW_TAG);
        cordImg.setTag(IMAGEVIEW_TAG);
        cleImg.setTag(IMAGEVIEW_TAG);
        barrImg.setTag(IMAGEVIEW_TAG);
        revolvImg.setTag(IMAGEVIEW_TAG);
        poignImg.setTag(IMAGEVIEW_TAG);

        ident = new IdentifyCard();

        cardPiece = (ImageView) findViewById(R.id.cardFuturePiece);
        cardPiece.setImageResource(ident.findImage(Remote.myRoom));
        background = (ImageView) findViewById(R.id.imageViewBackground);

        background.setImageResource(ident.findBigImage(Remote.myRoom));

        instantiateBandeau();

        persos = new String[]{"Rose","Pervenche","Leblanc","Olive","Moutarde","Violet"};
        armes = new String[]{"Chandelier", "Barre de fer", "Cle anglaise", "Revolver", "Poignard", "Corde"};
        listPersos = new ArrayList<String>();
        for (int i = 0; i < persos.length; ++i) {
            listPersos.add(persos[i]);
        }
        listArmes = new ArrayList<String>();
        for (int i = 0; i < armes.length; ++i) {
            listArmes.add(armes[i]);
        }



    }

    private final class MyClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {

            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            if (view.getId() == R.id.Rose){
                tempPers = "Rose";
            }
            else if(view.getId() == R.id.Pervenche){
                tempPers = "Pervenche";
            }
            else if(view.getId() == R.id.Leblanc){
                tempPers = "Leblanc";
            }
            else if(view.getId() == R.id.Olive){
                tempPers = "Olive";
            }
            else if(view.getId() == R.id.Moutarde){
                tempPers = "Moutarde";
            }
            else if(view.getId() == R.id.Violet){
                tempPers = "Violet";
            }
            else if(view.getId() == R.id.Chandelier){
                tempArme = "Chandelier";
            }
            else if(view.getId() == R.id.Barredefer){
                tempArme = "Barre de fer";
            }
            else if(view.getId() == R.id.Cleanglaise){
                tempArme = "Cle anglaise";
            }
            else if(view.getId() == R.id.Poignard){
                tempArme = "Poignard";
            }
            else if(view.getId() == R.id.Corde){
                tempArme = "Corde";
            }
            else if(view.getId() == R.id.Revolver){
                tempArme = "Revolver";
            }

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, shadowBuilder, view, 0);

            //view.setVisibility(View.INVISIBLE);
            return true;
        }
    }


    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.ok_button:
                        Remote.persoHypo = persoHypoth;
                        //Remote.persoHypo = "Rose";
                        //Remote.armeHypo = "Revolver";
                        Remote.armeHypo = armeHypoth;
                        Remote.lieuHypo = Remote.myRoom;
                        Remote.emit_supposition();
                        //Remote.emit_tour_termine();
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
                    if (v == findViewById(R.id.cardFuturePerso) && listPersos.contains(tempPers)) {
                        tempCardPerso = (ImageView) findViewById(R.id.textSuspect);
                        tempCardPerso.setVisibility(View.GONE);

                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);


                        RelativeLayout containView = (RelativeLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);
                        persoHypoth = tempPers;
                    }
                    if (v == findViewById(R.id.cardFutureArme) && listArmes.contains(tempArme)) {
                        tempCardArm = (ImageView) findViewById(R.id.textArme);
                        tempCardArm.setVisibility(View.GONE);

                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);


                        RelativeLayout containView = (RelativeLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);
                        armeHypoth = tempArme;
                    }
                    else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        //Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
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
