package nati.aviran.getbs;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nati.aviran.getbs.Dialogs.AddBabySitterDialogFragment;
import nati.aviran.getbs.Dialogs.AddParentDialogFragment;
import nati.aviran.getbs.model.Model;
import nati.aviran.getbs.model.Parent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddParentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddParentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddParentFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    //private static final

    private OnFragmentInteractionListener mListener;

    public AddParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddBabySitterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddParentFragment newInstance() {
        AddParentFragment fragment = new AddParentFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =inflater.inflate(R.layout.fragment_add_parent, container, false);
        Button add= (Button)v.findViewById(R.id.addParentAddBtn);
        Button cel=(Button)v.findViewById(R.id.addParentCancelBtn);


        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(false);
                return;
            }
        });


        Log.d("TAG","Create view");

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText email= (EditText) v.findViewById(R.id.addParentEmailTv);
                EditText password= (EditText) v.findViewById(R.id.addParentPasswordTv);
                EditText address = (EditText) v.findViewById(R.id.addParentAddressTv);
                EditText name= (EditText) v.findViewById(R.id.addParentNameTv);

                if((email.getText().toString().equals(""))||
                        (password.getText().toString().equals(""))||
                        (address.getText().toString().equals(""))||
                        (name.getText().toString().isEmpty() ) )
                {
                    ((TextView) v.findViewById(R.id.errorMessage)).setText("there is empty value");
                    return;
                }else {

                    Parent New = new Parent();
                    New.email = email.getText().toString();
                    New.password = password.getText().toString();
                    New.name = name.getText().toString();
                    New.address = address.getText().toString();
                    Model.instace.addParent(New);
                    mListener.onFragmentInteraction(true);
                    DialogFragment dialog = new AddParentDialogFragment();
                    dialog.show(getFragmentManager(), "TAG");

                }
            }
        };

        add.setOnClickListener(click);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.main_logout).setVisible(false);
        register.setVisible(false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  MainActivity.CurrentFragment="Add";
        Log.d("TAG","MainActivity attach");

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        Log.d("TAG","MainActivity attach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean bool);
    }
}
