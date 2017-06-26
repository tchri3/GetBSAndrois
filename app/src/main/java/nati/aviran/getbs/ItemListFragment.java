package nati.aviran.getbs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;

public class ItemListFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public ItemListFragment() {
    }


    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","enter on create"); super.onCreate(savedInstanceState);
    }

    EditText itemIdEditTest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_item_list, container, false);

        Button select = (Button) contentView.findViewById(R.id.itemlist_select_btn);
        select.setOnClickListener(this);
        itemIdEditTest = (EditText) contentView.findViewById(R.id.itemlist_item_id);
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("TAG","enter on attach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Log.d("TAG","enter");
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onAttach(Activity context) {
        Log.d("TAG","enter on attach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Log.d("TAG","enter");
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG",itemIdEditTest.getText().toString());
        mListener.onItemSelected(itemIdEditTest.getText().toString());
    }


    public interface OnFragmentInteractionListener {
        void onItemSelected(String itemId);
    }
}
