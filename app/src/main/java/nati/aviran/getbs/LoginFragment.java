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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import nati.aviran.getbs.model.Model;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddBabySitterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void onPrepareOptionsMenu(Menu menu)
    {
        menu.findItem(R.id.main_logout).setVisible(false);
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("TAG","Create view");
        MainActivity.CurrentFragment="login";

        final View v =inflater.inflate(R.layout.fragment_login, container, false);

        final EditText email= (EditText) v.findViewById(R.id.loginEmailTv);
        final EditText password= (EditText) v.findViewById(R.id.loginPasswordTv);

        // get buttons
        Button signin= (Button)v.findViewById(R.id.loginInBtn);
        Button signupBS= (Button)v.findViewById(R.id.loginSignupBabysitterBtn);
        Button signupParent= (Button)v.findViewById(R.id.loginSignupParentBtn);

        // add click event for buttons
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView errorMessage = ((TextView) v.findViewById(R.id.loginError));
                errorMessage.setVisibility(View.GONE);

                // check validation
                if((email.getText().toString().equals(""))||(password.getText().toString().equals("")) ) {
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText("You cannot leave any value empty");

                    Log.d("TAG","Error login");
                    return;
                }

                // model login
                Model.instace.login(email.getText().toString(),password.getText().toString() , new Model.GetLoginCallback()
                {
                    @Override
                    public void onSuccess() {
                        Log.d("TAG","login");
                        // go to list view
                        mListener.onFragmentInteraction(true);
                    }

                    @Override
                    public void onFail() {
                        // show error
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("Email / Password is incorrect");

                        Log.d("TAG","failed login");
                        return;
                    }

                });

            }
        };

        View.OnClickListener signupBSClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // show bs view add
                mListener.onFragmentInteractionSignUp(true);
            }
        };

        View.OnClickListener signupParentClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // show add parent view
                mListener.onFragmentInteractionSignUp(false);
            }
        };

        // add click event for buttons
        signupBS.setOnClickListener(signupBSClick);
        signupParent.setOnClickListener(signupParentClick);
        signin.setOnClickListener(click);

        return v;

    }



    @Override
    public void onAttach(Context context) {
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
        void onFragmentInteractionSignUp(boolean isBs);
    }
}
