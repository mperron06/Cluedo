package com.polytech.cluedo;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeuilleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeuilleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeuilleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeuilleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeuilleFragment newInstance(String param1, String param2) {
        FeuilleFragment fragment = new FeuilleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FeuilleFragment() {
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_feuille, container, false);
        for(int i = 1; i<= 22; i++){
            final int row = i-1;
            int firstId = getResources().getIdentifier("first_" + i, "id", getActivity().getPackageName());
            final EditText yourFirstEditText = (EditText) view.findViewById(firstId);
            yourFirstEditText.setText(Remote.fiche_detaillee[0][row]);
            int secondId = getResources().getIdentifier("second_" + i, "id", getActivity().getPackageName());
            final EditText yourSecondEditText = (EditText) view.findViewById(secondId);
            yourSecondEditText.setText(Remote.fiche_detaillee[1][row]);

            yourFirstEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    // you can call or do what you want with your EditText here
                    Remote.fiche_detaillee[0][row] = yourFirstEditText.getText().toString();
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //yourFirstEditText.setText(Remote.fiche_detaillee[0][row]);
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
            yourSecondEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    // you can call or do what you want with your EditText here
                    Remote.fiche_detaillee[1][row] = yourSecondEditText.getText().toString();
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //yourSecondEditText.setText(Remote.fiche_detaillee[1][row]);
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        }
        /*
        yourEditText = (EditText) findViewById(R.id.yourEditTextId);

        yourEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                yourEditText. ...

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });*/
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
