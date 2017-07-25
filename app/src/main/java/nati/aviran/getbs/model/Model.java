package nati.aviran.getbs.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.URLUtil;

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

    public interface GetLoginCallback {
        void onSuccess();
        void onFail();
    }

    public void login(String email,String password,final GetLoginCallback callback) {

        modelFirebase.login(email, password, new ModelFirebase.GetLoginCallback()
        {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFail() {
                callback.onFail();
            }


        });

    }

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


    }




    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void saveImage(final Bitmap imageBmp, final String name, final SaveImageListener listener) {
        modelFirebase.saveImage(imageBmp, name, new SaveImageListener() {
            @Override
            public void complete(String url) {
                String fileName = URLUtil.guessFileName(url, null, null);
                ModelFiles.saveImageToFile(imageBmp,fileName);
                listener.complete(url);
            }

            @Override
            public void fail() {
                listener.fail();
            }
        });


    }


    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }
    public void getImage(final String url, final GetImageListener listener) {
        //check if image exsist localy
        final String fileName = URLUtil.guessFileName(url, null, null);
        ModelFiles.loadImageFromFileAsynch(fileName, new ModelFiles.LoadImageFromFileAsynch() {
            @Override
            public void onComplete(Bitmap bitmap) {
                if (bitmap != null){
                    Log.d("TAG","getImage from local success " + fileName);
                    listener.onSuccess(bitmap);
                }else {
                    modelFirebase.getImage(url, new GetImageListener() {
                        @Override
                        public void onSuccess(Bitmap image) {
                            String fileName = URLUtil.guessFileName(url, null, null);
                            Log.d("TAG","getImage from FB success " + fileName);
                            ModelFiles.saveImageToFile(image,fileName);
                            listener.onSuccess(image);
                        }

                        @Override
                        public void onFail() {
                            Log.d("TAG","getImage from FB fail ");
                            listener.onFail();
                        }
                    });

                }
            }
        });

    }



}
