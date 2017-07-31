package nati.aviran.getbs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity
        implements AddParentFragment.OnFragmentInteractionListener, BabySitterListFragment.OnFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener,AddBabySitterFragment.OnFragmentInteractionListener ,BabySitterDetailsFragment.OnFragmentInteractionListener{
    public static  String CurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","MainActivity onCreate");
        FragmentTransaction tran =  getFragmentManager().beginTransaction();

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
            case R.id.main_logout:
                CurrentFragment = "login";
                LoginFragment loginFragment = LoginFragment.newInstance();
                FragmentTransaction tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_container, loginFragment);
                tran.commit();
               break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    // show bs details of bs by email
    @Override
    public void onFragmentInteraction(String email) {
        this.CurrentFragment="BabySitterDetails";
        BabySitterDetailsFragment babySitterDetailsFragment = BabySitterDetailsFragment.newInstance(email);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container, babySitterDetailsFragment);
        tran.addToBackStack("");
        tran.commit();

    }


    // if false login list view if true show list view
    @Override
    public void onFragmentInteraction(boolean bool) {
        Log.d("TAG","onFragmentInteraction login ");
        if(!bool) {

            CurrentFragment = "login";
            LoginFragment loginFragment = LoginFragment.newInstance();
            FragmentTransaction tran = getFragmentManager().beginTransaction();
            tran.replace(R.id.main_container, loginFragment);
            tran.commit();

        }
        else
        {
            CurrentFragment="List";
            BabySitterListFragment listFragment = BabySitterListFragment.newInstance();
            FragmentTransaction tran = getFragmentManager().beginTransaction() ;
            tran.replace(R.id.main_container,listFragment);
            tran.commit();
    }
    }


    // if is bs show add bs view else show add parent view
@Override
    public void onFragmentInteractionSignUp(boolean isBs) {
    if(isBs)
    {
        AddBabySitterFragment sa = AddBabySitterFragment.newInstance();
        this.CurrentFragment="addBS";
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container,sa);
        tran.addToBackStack("");
        tran.commit();

    }else
    {
        AddParentFragment sa = AddParentFragment.newInstance();
        this.CurrentFragment="addParent";
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_container,sa);
        tran.addToBackStack("");
        tran.commit();

    }

    }
}
