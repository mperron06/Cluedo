package com.polytech.cluedo;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoriqueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HistoriqueFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supposition, container, false);

    /*    TableLayout containerTable = (TableLayout) getActivity().findViewById(R.id.historique_tab);

        // On va calculer la largeur des colonnes en fonction de la marge de 10
        // On affiche l'enreg dans une ligne
        TableRow tableRow = new TableRow(getActivity());
        //containerTable.addView(tableRow, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
          containerTable.addView(tableRow);

        int len = Remote.historique_perso.size();
        for(int j=0; j <len; j++) {
            // On va commencer par renseigner une ligne de titre par joueur
            for (int i = 0; i < 3; i++) {
                TextView text = new TextView(this.getActivity());
                if (i == 0) {
                    text.setText(Remote.historique_perso.get(j));
                } else if (i == 1) {
                    text.setText(Remote.historique_arme.get(j));
                } else {
                    text.setText(Remote.historique_piece.get(j));
                }
                text.setGravity(Gravity.CENTER);
                tableRow.addView(text, i);
            }
        }*/

        return view;
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
