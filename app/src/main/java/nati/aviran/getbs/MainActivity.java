package nati.aviran.getbs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import nati.aviran.getbs.model.Student;


public class MainActivity extends Activity
        implements AddParentFragment.OnFragmentInteractionListener, StudentListFragment.OnFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener,AddBabySitterFragment.OnFragmentInteractionListener ,StudentDetailsFragment.OnFragmentInteractionListener{
    public static  String CurrentFragment;

    //  FragmentTransaction tran =  getFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","MainActivity onCreate");
        FragmentTransaction tran =  getFragmentManager().beginTransaction();
       // CurrentFragment = "List";
       // StudentListFragment listFragment = StudentListFragment.newInstance();
      //  tran.add(R.id.main_container, listFragment);

        CurrentFragment = "login";
        LoginFragment loginFragment = LoginFragment.newInstance();
        tran.add(R.id.main_container, loginFragment);
        tran.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.main_add:
                AddBabySitterFragment sa = AddBabySitterFragment.newInstance(null);
                this.CurrentFragment="addBS";
                FragmentTransaction tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container,sa);
                tran.addToBackStack("");
                tran.commit();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                break;
            case android.R.id.home:
/*
                if(this.CurrentFragment.equals("login"))
                {
                    getActionBar().setDisplayHomeAsUpEnabled(false);

                }else {

                    onBackPressed();
                }*/

                break;


/*
            case R.id.main_edit:
            {
                String id =((TextView)findViewById(R.id.detId)).toString();
                AddBabySitterFragment s= AddBabySitterFragment.newInstance(id);
                FragmentTransaction tran3 = getFragmentManager().beginTransaction();
                //tran3.
                tran3.replace(R.id.main_container,s);
                tran3.addToBackStack("");
                tran3.commit();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                break;
            }*/
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }






    @Override
    public void onFragmentInteraction(String id) {
        this.CurrentFragment="StudentDetails";
        StudentDetailsFragment studentDetailsFragment = StudentDetailsFragment.newInstance(id);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container,studentDetailsFragment);
        tran.addToBackStack("");
        // CurrentFragment="Details";
        getActionBar().setDisplayHomeAsUpEnabled(true);

        tran.commit();

    }



    @Override
    public void onFragmentInteraction(boolean bool) {
        Log.d("TAG","onFragmentInteraction login ");
        CurrentFragment = "login";
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction tran = getFragmentManager().beginTransaction() ;
        tran.replace(R.id.main_container, loginFragment);
        tran.commit();
        getActionBar().setDisplayHomeAsUpEnabled(false);


        /*
        CurrentFragment="List";
        StudentListFragment listFragment = StudentListFragment.newInstance();
        FragmentTransaction tran = getFragmentManager().beginTransaction() ;
        tran.replace(R.id.main_container,listFragment);
        tran.commit();
        getActionBar().setDisplayHomeAsUpEnabled(false);
*/
    }

    @Override
    public void onFragmentInteraction(Student str) {

    }

@Override
    public void onFragmentInteractionSignUp(boolean isBs) {
    if(isBs)
    {
        AddBabySitterFragment sa = AddBabySitterFragment.newInstance(null);
        this.CurrentFragment="addBS";
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container,sa);
        tran.addToBackStack("");
        tran.commit();

    }else
    {
        AddParentFragment sa = AddParentFragment.newInstance(null);
        this.CurrentFragment="addParent";
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container,sa);
        tran.addToBackStack("");
        tran.commit();

    }
        getActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
