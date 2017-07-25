package nati.aviran.getbs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nati.aviran.getbs.model.Model;

import nati.aviran.getbs.model.BabySitter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BabySitterDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BabySitterDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BabySitterDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL = "email";

    // TODO: Rename and change types of parameters
    private String email ;
    public BabySitter CurrentbabySitter;

    private OnFragmentInteractionListener mListener;

    public BabySitterDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BabySitterDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BabySitterDetailsFragment newInstance(String email) {
        BabySitterDetailsFragment fragment = new BabySitterDetailsFragment();
        Bundle args = new Bundle();
        args.putString(EMAIL, email);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(EMAIL);

        }
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  if (item.getItemId() == R.id.main_edit) {
            MainActivity.CurrentFragment="Edit";
            //String id = ((TextView) findViewById(R.id.detId)).toString();
            AddBabySitterFragment s = AddBabySitterFragment.newInstance();
            ;
            FragmentTransaction tran3 = getFragmentManager().beginTransaction();
            tran3.replace(R.id.main_container, s);
            tran3.addToBackStack("");
            tran3.commit();
            //getActionBar().setDisplayHomeAsUpEnabled(true);
            //break;

        }
        */
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.CurrentFragment="BabySitterDetails";

        View view =inflater.inflate(R.layout.fragment_babysitter_details, container, false);


        final TextView name= (TextView)view.findViewById((R.id.detailsName));
        final TextView phone= (TextView)view.findViewById(R.id.detailsPhoneTv);
        final TextView address= (TextView)view.findViewById(R.id.detailsAddressTv);
        final TextView salary= (TextView)view.findViewById(R.id.detailsSalaryTv);
        final TextView age =(TextView) view.findViewById(R.id.detailsAgeTv);
        final TextView availability =(TextView) view.findViewById(R.id.detailsAvailabilityTv);
        final ImageView imageView = (ImageView) view.findViewById(R.id.detailsImageView);

        Model.instace.getBabySitter(email, new Model.GetBabySitterCallback() {
            @Override
            public void onComplete(BabySitter bs) {

                Log.d("TAG","got bs name: " + bs.name);

                name.setText(bs.name);
                phone.setText(bs.phone);
                salary.setText(bs.salary);
                age.setText(bs.age);
                availability.setText(bs.availability);
                address.setText(bs.address);


                if (bs.imageUrl != null && !bs.imageUrl.isEmpty() && !bs.imageUrl.equals("")){

                    Model.instace.getImage(bs.imageUrl, new Model.GetImageListener() {
                        @Override
                        public void onSuccess(Bitmap image) {

                            imageView.setImageBitmap(image);

                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }



            }

            @Override
            public void onCancel() {
                Log.d("TAG","get bs cancel" );

            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Student str) {
        if (mListener != null) {
            mListener.onFragmentInteraction(null);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
      //  void onFragmentInteraction(Student str);
    }
    public void onPrepareOptionsMenu(Menu menu)
    {
      //  MenuItem register = menu.findItem(R.id.main_edit);
     //   menu.findItem(R.id.main_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

       //getActionBar().setDisplayHomeAsUpEnabled(true);

       // register.setVisible(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
