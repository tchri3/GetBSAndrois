package nati.aviran.getbs;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TimeFormatException;
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
import nati.aviran.getbs.model.Student;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentAdd.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentAdd extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private static final String ID = "id";
    private String id ;
    private Student st;
    //private static final

    private OnFragmentInteractionListener mListener;

    public StudentAdd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StudentAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentAdd newInstance(String id) {
        StudentAdd fragment = new StudentAdd();
        Bundle args = new Bundle();
        args.putString(ID,id);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =inflater.inflate(R.layout.fragment_student_add, container, false);
        Button save= (Button)v.findViewById(R.id.mainSaveBtn);
        Button cel=(Button)v.findViewById(R.id.mainCancelBtn);
        Log.d("TAG","Create view");

          EditText nameEt = (EditText) v.findViewById(R.id.mainNameTv);
         EditText idEt= (EditText) v.findViewById(R.id.mainIdTv);
         EditText phone= (EditText) v.findViewById(R.id.mainPhoneTv);
         EditText Address= (EditText) v.findViewById(R.id.AddressEdit);
        MyTimePicker time = (MyTimePicker) v.findViewById(R.id.frag_input_time1);
        MyDatePicker date = (MyDatePicker) v.findViewById(R.id.frag_input_date1);
        Boolean checekd =((CheckBox)v.findViewById((R.id.strow_cb12))).isChecked();

        Button remove = (Button)v.findViewById(R.id.mainDelBtn);
        Log.d("Tag","***********8"+id);
        st =Model.instace.getStudent(id);
        if(id!=null)
        {
            MainActivity.CurrentFragment="Edit";
            //Log.d("Tag",st.id+"***********8"+id);
            remove.setVisibility(View.VISIBLE);
            nameEt.setText(st.name, TextView.BufferType.EDITABLE);
            idEt.setText(st.id, TextView.BufferType.EDITABLE);
            phone.setText(st.phone, TextView.BufferType.EDITABLE);
            Address.setText(st.address, TextView.BufferType.EDITABLE);
            time.setText(st.time,TextView.BufferType.EDITABLE);
            date.setText(st.date, TextView.BufferType.EDITABLE);
            ((CheckBox) v.findViewById((R.id.strow_cb12))).setChecked(st.checked);

        }
        else
            MainActivity.CurrentFragment="add";
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEt = (EditText) v.findViewById(R.id.mainNameTv);
                EditText idEt= (EditText) v.findViewById(R.id.mainIdTv);
                EditText phone= (EditText) v.findViewById(R.id.mainPhoneTv);
                EditText Address= (EditText) v.findViewById(R.id.AddressEdit);
                MyTimePicker time = (MyTimePicker) v.findViewById(R.id.frag_input_time1);
                MyDatePicker date = (MyDatePicker) v.findViewById(R.id.frag_input_date1);
                Boolean checekd =((CheckBox)v.findViewById((R.id.strow_cb12))).isChecked();
                if(view.getId() == R.id.mainCancelBtn)
                {
                    mListener.onFragmentInteraction(false);
                    return;
                }
                else if(view.getId()==R.id.mainSaveBtn)
                {

                    if(st!=null)
                    {
                        if((idEt.getText().toString().equals(""))||(nameEt.getText().toString().equals(""))||(phone.getText().toString().equals(""))||
                                (Address.getText().toString().equals("")) || (date.getText().toString().isEmpty()) || (time.getText().toString().isEmpty() ) ) {
                            ((TextView) v.findViewById(R.id.Message1)).setText("you cannot leave any value empty - please fix it ");
                            return;
                        }
                        if(((id.equals(idEt.getText().toString()))))
                        {
                        st.name = nameEt.getText().toString();
                        st.id = idEt.getText().toString();
                        st.phone = phone.getText().toString();
                        st.address = Address.getText().toString();
                        st.checked = ((CheckBox)v.findViewById((R.id.strow_cb12))).isChecked();
                        st.time = time.getText().toString();
                        st.date = date.getText().toString();
                        DialogFragment dialog = new SaveStudentDialogFragment();
                        dialog.show(getFragmentManager(),"TAG");
                        mListener.onFragmentInteraction(true);
                        }
                        else
                            ((TextView)v.findViewById(R.id.Message1)).setText("You can only edit Student "+id);
                    }
                    else {
                        if((idEt.getText().toString().equals(""))||(nameEt.getText().toString().equals(""))||(phone.getText().toString().equals(""))||
                                (Address.getText().toString().equals("")) || (date.getText().toString().isEmpty()) || (time.getText().toString().isEmpty() ) ) {
                            ((TextView) v.findViewById(R.id.Message1)).setText("you cannot leave any value empty - please fix it ");
                            return;
                        }
                        if(Model.instace.checkIfIdExists(idEt.getText().toString()))
                        {
                            ((TextView) v.findViewById(R.id.Message1)).setText("this id is already exist ");
                        }
                        else {
                            Student New = new Student();
                            New.address = Address.getText().toString();
                            New.checked = checekd;
                            New.id = idEt.getText().toString();
                            New.phone = phone.getText().toString();
                            New.name = nameEt.getText().toString();
                            New.time = time.getText().toString();
                            New.date = date.getText().toString();
                            Model.instace.addStudent(New);
                            DialogFragment dialog = new SaveStudentDialogFragment();
                            dialog.show(getFragmentManager(),"TAG");
                            mListener.onFragmentInteraction(true);
                        }
                    }
                }
                else
                {
                    if( st.id.equals(idEt.getText().toString())) {
                        Model.instace.deleteStudent(st);
                        mListener.onFragmentInteraction(true);
                    }

                }


            }
        };
        remove.setOnClickListener(click);
        save.setOnClickListener(click);
        cel.setOnClickListener(click);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.main_edit).setVisible(false);
        menu.findItem(R.id.main_add).setVisible(false);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

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
