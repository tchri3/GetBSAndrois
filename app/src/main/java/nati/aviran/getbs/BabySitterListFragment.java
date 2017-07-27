package nati.aviran.getbs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import nati.aviran.getbs.model.Model;
import nati.aviran.getbs.model.BabySitter;


//import nati.aviran.getbs.model.Student;

import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BabySitterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BabySitterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BabySitterListFragment extends Fragment {
    ListView list;
    ProgressBar progressBar;

    List<BabySitter> data;
    BabySitterListAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    public BabySitterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment BabySitterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BabySitterListFragment newInstance() {
        BabySitterListFragment fragment = new BabySitterListFragment();
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

        View view=inflater.inflate(R.layout.fragment_babysitter_list, container, false);
      //  data = Model.instace.getAllBabySitters();
        list = (ListView) view.findViewById(R.id.list_list);

        progressBar = (ProgressBar) view.findViewById(R.id.listProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new BabySitterListAdapter();




        list.setAdapter(adapter);





        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.onFragmentInteraction(data.get(position).email);
            }
        });


        Model.instace.getAllBabySittersAndObserve(new Model.GetAllBabySittersAndObserveCallback() {
            @Override
            public void onComplete(List<BabySitter> list) {
                data = list;
                progressBar.setVisibility(GONE);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancel() {

            }
        });

/*
        data = Model.instace.getAllSql();
       adapter.notifyDataSetChanged();

*/

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
    class BabySitterListAdapter extends BaseAdapter {
        LayoutInflater inflater = getActivity().getLayoutInflater();////check

        @Override
        public int getCount()
        {   if(data != null)
                 return data.size();

            return 0;
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
                BabySitter bs = data.get(pos);

            }
        }

        CBListener listener = new CBListener();

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.babysitter_list_row, null);
               // CheckBox cb = (CheckBox) convertView.findViewById(R.id.strow_cb);
               // cb.setOnClickListener(listener);
            }

            TextView name = (TextView) convertView.findViewById(R.id.strow_name);
            TextView age = (TextView) convertView.findViewById(R.id.strow_age);
            TextView salary = (TextView) convertView.findViewById(R.id.strow_salary);

            final BabySitter bs = data.get(position);
            name.setText(bs.name);
            age.setText(bs.age);
            salary.setText(bs.salary);

            final ImageView imageView = (ImageView) convertView.findViewById(R.id.strow_image);

            if (bs.imageUrl != null && !bs.imageUrl.isEmpty() && !bs.imageUrl.equals("")){

                Model.instace.getImage(bs.imageUrl, new Model.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                      //  if(imageView.getTag() != null) {
                          //  String tagUrl = imageView.getTag().toString();
                          //  if (tagUrl.equals(bs.imageUrl)) {
                                imageView.setImageBitmap(image);
                        //    }
                       // }
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }

            return convertView;
        }
}
}
