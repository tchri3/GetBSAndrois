package nati.aviran.getbs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import nati.aviran.getbs.model.Model;
import nati.aviran.getbs.model.Student;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentListFragment extends Fragment {
    ListView list;
    List<Student> data;
    StudentsListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    public StudentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment StudentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentListFragment newInstance() {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.CurrentFragment="List";

        View view=inflater.inflate(R.layout.fragment_student_list, container, false);
        data = Model.instace.getAllStudents();
        list = (ListView) view.findViewById(R.id.stlist_list);
        adapter = new StudentsListAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.onFragmentInteraction(data.get(position).id);
            }
        });
        return view;
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
        void onFragmentInteraction(String id);
    }
    class StudentsListAdapter extends BaseAdapter {
        LayoutInflater inflater = getActivity().getLayoutInflater();////check

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class CBListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Student st = data.get(pos);
                st.checked = !st.checked;
            }
        }

        CBListener listener = new CBListener();

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.students_list_row, null);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_cb);
                cb.setOnClickListener(listener);
            }

            TextView name = (TextView) convertView.findViewById(R.id.strow_name);
            TextView id = (TextView) convertView.findViewById(R.id.strow_id);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_cb);

            Student st = data.get(position);
            name.setText(st.name);
            id.setText(st.id);
            cb.setChecked(st.checked);
            cb.setTag(position);

            return convertView;
        }
}
}
