package nati.aviran.getbs;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nati.aviran.getbs.Dialogs.AddBabySitterDialogFragment;
import nati.aviran.getbs.model.BabySitter;
import nati.aviran.getbs.model.Model;
import nati.aviran.getbs.model.Student;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddBabySitterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddBabySitterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBabySitterFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    ImageView imageView;
    Bitmap imageBitmap;
    ProgressBar progressBar;


    //private static final

    private OnFragmentInteractionListener mListener;

    public AddBabySitterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddBabySitterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBabySitterFragment newInstance() {
        AddBabySitterFragment fragment = new AddBabySitterFragment();
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
        final View v =inflater.inflate(R.layout.fragment_add_babysitter, container, false);
        Button add= (Button)v.findViewById(R.id.addBSitterAddbtn);
        Button cel=(Button)v.findViewById(R.id.addBSitterCancelbtn);


        cel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(false);
                return;
            }
        });

        imageView = (ImageView)v.findViewById(R.id.bsImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        Log.d("TAG","Create view");

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText email= (EditText) v.findViewById(R.id.addBSitterEmailTv);
                EditText password= (EditText) v.findViewById(R.id.addBSitterPasswordTv);
                EditText address = (EditText) v.findViewById(R.id.addBSitterAddressTv);
                EditText age = (EditText) v.findViewById(R.id.addBSitterAgeTv);
                EditText availability= (EditText) v.findViewById(R.id.addBSitterAvailabilityTv);
                EditText name= (EditText) v.findViewById(R.id.addBSitterNameTv);
                EditText phone= (EditText) v.findViewById(R.id.addBSitterPhoneTv);
                EditText salary= (EditText) v.findViewById(R.id.addBSitterSalaryTv);

                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher mat = pattern.matcher(email.getText().toString());

                // Error message
                String errorMessage = "";

                // Check if there is an error
                if(email.getText().toString().equals("") || (!mat.matches())) {
                    errorMessage = "must specify valid email";
                }
                else if (password.getText().toString().equals("") || password.getText().toString().length() < 6){
                    errorMessage = "password must be 6 characters or more";
                }
                else if (address.getText().toString().equals("")){
                    errorMessage = "address can't be empty";
                }
                else if (age.getText().toString().equals("") || (!age.getText().toString().matches("-?\\d+(\\.\\d+)?"))){
                    errorMessage = "must specify valid age";
                }
                else if (availability.getText().toString().isEmpty()){
                    errorMessage = "availability can't be empty";
                }
                else if (phone.getText().toString().equals("")){
                    errorMessage = "phone can't be empty";
                }
                else if (salary.getText().toString().equals("")){
                    errorMessage = "salary can't be empty";
                }
                else if (name.getText().toString().equals("")){
                    errorMessage = "name can't be empty";
                }

                // If there is an error
                if (errorMessage != "")
                {
                    ((TextView) v.findViewById(R.id.errorMessage)).setText(errorMessage);
                    Log.d("TAG","error add babysitter");
                    return;
                }else {

                    final BabySitter New = new BabySitter();
                    New.email = email.getText().toString();
                    New.password = password.getText().toString();
                    New.phone = phone.getText().toString();
                    New.name = name.getText().toString();
                    New.address = address.getText().toString();
                    New.salary = salary.getText().toString();
                    New.age = age.getText().toString();
                    New.availability = availability.getText().toString();


                    if (imageBitmap != null) {
                        Model.instace.saveImage(imageBitmap, New.email + ".jpeg", new Model.SaveImageListener() {
                            @Override
                            public void complete(String url) {
                                New.imageUrl = url;
                                Model.instace.addBabySitter(New);
                                DialogFragment dialog = new AddBabySitterDialogFragment();
                                dialog.show(getFragmentManager(), "TAG");
                                mListener.onFragmentInteraction(false); // back to login
                            }

                            @Override
                            public void fail() {
                                ((TextView) v.findViewById(R.id.errorMessage)).setText("there is problem with the image");
                                return;
                            }
                        });
                    }else{
                        Model.instace.addBabySitter(New);
                        DialogFragment dialog = new AddBabySitterDialogFragment();
                        dialog.show(getFragmentManager(), "TAG");
                        mListener.onFragmentInteraction(false); // back to login
                    }


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

       //  getActionBar().setDisplayHomeAsUpEnabled(true);
      //  menu.findItem(R.id.main_add).setVisible(false);
       /// getActionBar().setDisplayHomeAsUpEnabled(true);

       // register.setVisible(true);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
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


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity( getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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
