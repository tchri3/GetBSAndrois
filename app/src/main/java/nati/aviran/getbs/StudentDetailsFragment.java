package nati.aviran.getbs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import nati.aviran.getbs.model.Model;
import nati.aviran.getbs.model.Student;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID = "id";

    // TODO: Rename and change types of parameters
    private String id ;

    private OnFragmentInteractionListener mListener;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment StudentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentDetailsFragment newInstance(String id) {
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID);

        }
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_edit) {
            MainActivity.CurrentFragment="Edit";
            //String id = ((TextView) findViewById(R.id.detId)).toString();
            AddBabySitterFragment s = AddBabySitterFragment.newInstance(id);
            ;
            FragmentTransaction tran3 = getFragmentManager().beginTransaction();
            tran3.replace(R.id.main_container, s);
            tran3.addToBackStack("");
            tran3.commit();
            //getActionBar().setDisplayHomeAsUpEnabled(true);
            //break;

        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.CurrentFragment="StudentDetails";

        View view =inflater.inflate(R.layout.fragment_student_details, container, false);
        final Student st = Model.instace.getStudent(id);
        TextView name= (TextView)view.findViewById((R.id.detName));
        TextView id1= (TextView)view.findViewById(R.id.detId);
        TextView phone= (TextView)view.findViewById(R.id.detPhone);
        TextView address= (TextView)view.findViewById(R.id.detAdr);
        TextView time =(TextView) view.findViewById(R.id.time);
        CheckBox check= (CheckBox)view.findViewById(R.id.strow_cb1);
        TextView date =(TextView) view.findViewById(R.id.date);
        id1.setText(st.id);
        name.setText(st.name);
        phone.setText(st.phone);
        address.setText(st.address);
        check.setChecked(st.checked);
        time.setText(st.time);
        date.setText(st.date);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Student str) {
        if (mListener != null) {
            mListener.onFragmentInteraction(null);
        }
    }

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
        void onFragmentInteraction(Student str);
    }
    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.main_edit);
        menu.findItem(R.id.main_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

       //getActionBar().setDisplayHomeAsUpEnabled(true);

        register.setVisible(true);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
