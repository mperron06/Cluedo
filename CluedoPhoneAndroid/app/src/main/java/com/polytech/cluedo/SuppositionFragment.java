package com.polytech.cluedo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuppositionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuppositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuppositionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView perso;
    private ImageView arme;
    private ImageView lieu;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuppositionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuppositionFragment newInstance(String param1, String param2) {
        SuppositionFragment fragment = new SuppositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SuppositionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_supposition, container, false);

        //TEMP
        Remote.ma_supposition[0]="";
        Remote.ma_supposition[1]="";

        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.gallery_perso);
        ImageView[] mImages = new ImageView[Remote.perso_for_supposition.size()];

        for(int x=0; x<Remote.perso_for_supposition.size() ;x++) {
            System.out.println("Dans affichage perso : "+Remote.perso_for_supposition.size());
            String temp = Remote.perso_for_supposition.get(x).toLowerCase();
            final int temp_x = x;
            final String name= temp.replace(" ", "");
            mImages[x] = new ImageView(this.getActivity());
            mImages[x].setImageResource((getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
            mImages[x].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Remote.ma_supposition[0]= Remote.perso_for_supposition.get(temp_x);
                    System.out.println("perso "+ Remote.ma_supposition[0]);
                }
            });
            if(x==0){
                Remote.ma_supposition[0]= Remote.perso_for_supposition.get(temp_x);
                System.out.println("perso "+ Remote.ma_supposition[0]);
            }
            linearLayout1.addView(mImages[x]);
        }

        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.gallery_arme);
        ImageView[] mImagesBis = new ImageView[Remote.arme_for_supposition.size()];

        for(int x=0; x<Remote.arme_for_supposition.size() ;x++) {
            System.out.println("Dans affichage arme");
            String temp = Remote.arme_for_supposition.get(x).toLowerCase();
            final int temp_x = x;
            final String name2= temp.replace(" ", "");
            mImagesBis[x] = new ImageView(this.getActivity());
            mImagesBis[x].setImageResource((getResources().getIdentifier(name2, "drawable", getActivity().getPackageName())));
            mImagesBis[x].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Remote.ma_supposition[1]= Remote.arme_for_supposition.get(temp_x);
                    System.out.println("arme "+ Remote.ma_supposition[1]);
                }
            });
            if(x==0){
                Remote.ma_supposition[1]= Remote.arme_for_supposition.get(temp_x);
                System.out.println("arme "+ Remote.ma_supposition[1]);
            }
            linearLayout2.addView(mImagesBis[x]);
        }

        if(!Remote.accusation_turn){
            LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.gallery_lieu);
            ImageView ImagesLieu = new ImageView(this.getActivity());
            ImagesLieu.setImageResource((getResources().getIdentifier(Remote.ma_supposition[2].toLowerCase().replace(" ",""), "drawable", getActivity().getPackageName())));
            linearLayout3.addView(ImagesLieu);
        } else {
            final ArrayList<String> lieux = new ArrayList<String>();
            lieux.add("entree");
            lieux.add("salledejeux");
            lieux.add("bureau");
            lieux.add("salleamanger");
            lieux.add("garage");
            lieux.add("salon");
            lieux.add("cuisine");
            lieux.add("chambre");
            lieux.add("salledebains");
            LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.gallery_lieu);
            ImageView[] ImageLieux = new ImageView[lieux.size()];

            for(int x=0; x<lieux.size() ;x++) {
                System.out.println("Dans affichage arme");
                final int temp_x = x;
                final String name= lieux.get(x);
                ImageLieux[x] = new ImageView(this.getActivity());
                ImageLieux[x].setImageResource((getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
                ImageLieux[x].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Remote.ma_supposition[2]= lieux.get(temp_x);
                        System.out.println("lieu "+ Remote.ma_supposition[2]);
                    }
                });
                if(x==0){
                    Remote.ma_supposition[2]= lieux.get(temp_x);
                    System.out.println("lieu "+ Remote.ma_supposition[2]);
                }
                linearLayout3.addView(ImageLieux[x]);
            }
        }

        Button mButton = (Button) view.findViewById(R.id.supposeButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Remote.historique_perso.add(Remote.ma_supposition[0]);
                Remote.historique_arme.add(Remote.ma_supposition[1]);
                Remote.historique_piece.add(Remote.ma_supposition[2]);

                System.out.println(Remote.ma_supposition[0] +" "+Remote.ma_supposition[1]+ " "+Remote.ma_supposition[2]);

                if ((Remote.ma_supposition[0] != "") && (Remote.ma_supposition[1] != "")&& (Remote.ma_supposition[2] != "")) {
                    System.out.println("dans if");
                    if(!Remote.accusation_turn) {
                        Remote.emit_supposition();

                        Intent intent = new Intent(Remote.context, WaitingSuppositionActivity.class);
                        Remote.context.startActivity(intent);
                    } else {
                        Remote.emit_end_game();
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
