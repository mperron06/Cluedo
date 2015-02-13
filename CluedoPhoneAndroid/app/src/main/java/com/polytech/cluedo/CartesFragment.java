package com.polytech.cluedo;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CartesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cartes, container, false);

        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.gallery);
        ImageView[] mImages = new ImageView[Remote.my_cards_all.size()];

        for(int x=0; x<Remote.my_cards_all.size() ;x++) {
            String temp = Remote.my_cards_all.get(x).toLowerCase();
            final String name= temp.replace(" ", "");
            mImages[x] = new ImageView(this.getActivity());
            mImages[x].setImageResource((getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
            mImages[x].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ImageView im=(ImageView) view.findViewById(R.id.selected);
                    im.setImageResource((view.getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
                }
            });
            if(x==0){
                ImageView im=(ImageView) view.findViewById(R.id.selected);
                im.setImageResource((getResources().getIdentifier(name, "drawable", getActivity().getPackageName())));
            }
            linearLayout1.addView(mImages[x]);
        }

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
