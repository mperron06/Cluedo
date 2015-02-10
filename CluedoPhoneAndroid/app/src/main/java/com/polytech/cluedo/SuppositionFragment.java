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
        lieu = (ImageView) view.findViewById(R.id.lieu);
        lieu.setImageResource((getResources().getIdentifier(Remote.ma_supposition[2].toLowerCase().replace(" ",""), "drawable", getActivity().getPackageName())));

        //TEMP
        Remote.ma_supposition[0]="rose";
        Remote.ma_supposition[1]="corde";


        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.gallery_perso);
        ImageView[] mImages = new ImageView[Remote.perso_for_supposition.size()];

        for(int x=0; x<Remote.perso_for_supposition.size() ;x++) {
            //ImageView image = new ImageView(MainActivity.this);
            System.out.println("Dans affichage perso : "+Remote.perso_for_supposition.size());
            System.out.println(Remote.perso_for_supposition);
            String temp = Remote.perso_for_supposition.get(x).toLowerCase();
            final int temp_x = x;
            final String name= temp.replace(" ", "");
            System.out.println(name);
            mImages[x] = new ImageView(this.getActivity());
            mImages[x].setImageResource((getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
            //image.setBackgroundResource(R.drawable.ic_launcher);
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
            //ImageView image = new ImageView(MainActivity.this);
            System.out.println("Dans affichage arme");
            String temp = Remote.arme_for_supposition.get(x).toLowerCase();
            final int temp_x = x;
            final String name2= temp.replace(" ", "");
            mImagesBis[x] = new ImageView(this.getActivity());
            System.out.println(name2);
            mImagesBis[x].setImageResource((getResources().getIdentifier(name2, "drawable", getActivity().getPackageName())));
            //image.setBackgroundResource(R.drawable.ic_launcher);
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

        Button mButton = (Button) view.findViewById(R.id.supposeButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Remote.historique_perso.add(Remote.ma_supposition[0]);
                Remote.historique_arme.add(Remote.ma_supposition[1]);
                Remote.historique_piece.add(Remote.ma_supposition[2]);

                System.out.println("perso"+ Remote.ma_supposition[0]);
                System.out.println("arme"+ Remote.ma_supposition[1]);

                Remote.emit_supposition();

                Intent intent = new Intent(Remote.context, WaitingSuppositionActivity.class);
                Remote.context.startActivity(intent);
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
