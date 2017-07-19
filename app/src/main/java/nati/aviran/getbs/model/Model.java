package nati.aviran.getbs.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import nati.aviran.getbs.MyApp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aviran on 19/04/2017.
 */

public class Model {

    public final static Model instace = new Model();

    //private ModelMem modelMem;
    private ModelSql modelSql;
    private ModelFirebase modelFirebase;


    private Model(){

        // modelMem = new ModelMem();
        modelSql = new ModelSql(MyApp.getMyContext());
        modelFirebase = new ModelFirebase();

     /*   for(int i=0;i<2;i++){
            Student st = new Student();
            st.name = "kuku" + i;
            st.id = "" + i * 17;
            st.checked = false;
            st.imageUrl = "";
            st.phone=""+"052-"+i;
            st.address="rishon"+i;
            st.time = "16:49";
            st.date = "1/1/2000";
            data.add(st);
        }
        */
    }

    public void addParent(Parent p) {
        //StudentSql.addStudent(modelSql.getWritableDatabase(),st);
        modelFirebase.addParent(p);
    }

    public void addBabySitter(BabySitter bs) {
      //  BabySitterSql.addBabySitter(modelSql.getWritableDatabase(),bs);
          modelFirebase.addBabySitter(bs);
    }

    public void addStudent(Student st) {
        //StudentSql.addStudent(modelSql.getWritableDatabase(),st);
      //  modelFirebase.addStudent(st);
    }

/*

    private List<Student> data = new LinkedList<Student>();

    public List<Student> getAllStudents(){
        return data;
    }


    public  void deleteStudent(Student st){data.remove(st); }

    public Student getStudent(String stId) {
        for (Student s : data){
            if (s.id.equals(stId)){
                return s;
            }
        }
        return null;
    }
    public boolean checkIfIdExists(String id )
    {
        for (Student s : data){
            if (s.id.equals(id)){
                return true;
            }
        }
        return false;
    }
*/

    public interface GetBabySitterCallback {
        void onComplete(BabySitter bs);

        void onCancel();
    }

    public void getBabySitter(String email, final GetBabySitterCallback callback) {

        modelFirebase.getBabySitter(email, new ModelFirebase.GetBabySitterCallback() {
            @Override
            public void onComplete(BabySitter bs) {
                callback.onComplete(bs);
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }
        });

    }



    public interface GetAllBabySittersAndObserveCallback {
        void onComplete(List<BabySitter> list);
        void onCancel();
    }

    public void getAllBabySittersAndObserve(final GetAllBabySittersAndObserveCallback callback) {
        //1. get local lastUpdateDate
        SharedPreferences pref = MyApp.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        final double lastUpdateDate = pref.getFloat("BabySittersLastUpdateDate",0);
        Log.d("TAG","lastUpdateDate: " + lastUpdateDate);

        //2. get updated records from FB
        modelFirebase.getAllBabySittersAndObserve(lastUpdateDate, new ModelFirebase.GetAllBabySittersAndObserveCallback() {
            @Override
            public void onComplete(List<BabySitter> list) {

                double newLastUpdateDate = lastUpdateDate;
                Log.d("TAG", "FB detch:" + list.size());

                for (BabySitter bs: list) {
                    //3. update the local db
                    BabySitterSql.addBabySitter(modelSql.getWritableDatabase(),bs);
                    //4. update the lastUpdateTade
                    if (newLastUpdateDate < bs.lastUpdateDate){
                        newLastUpdateDate = bs.lastUpdateDate;
                    }
                }

                SharedPreferences.Editor prefEd = MyApp.getMyContext().getSharedPreferences("TAG",
                        Context.MODE_PRIVATE).edit();
                prefEd.putFloat("BabySittersLastUpdateDate", (float) newLastUpdateDate);
                prefEd.commit();
                Log.d("TAG","BabySittersLastUpdateDate: " + newLastUpdateDate);


                //5. read from local db
                List<BabySitter> data = BabySitterSql.getAllBabySitters(modelSql.getReadableDatabase());

                //6. return list of students
                callback.onComplete(data);

            }

            @Override
            public void onCancel() {

            }
        });


        //todo: updatre studenty last update property

    }


}
