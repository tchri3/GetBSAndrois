package nati.aviran.getbs.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aviran on 19/04/2017.
 */

public class Model {

    public final static Model instace = new Model();

    //private ModelMem modelMem;
   // private ModelSql modelSql;
    private ModelFirebase modelFirebase;


    private Model(){

        // modelMem = new ModelMem();
        //modelSql = new ModelSql(MyApplication.getMyContext());
        modelFirebase = new ModelFirebase();

        for(int i=0;i<2;i++){
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
    }

    public void addParent(Parent p) {
        //StudentSql.addStudent(modelSql.getWritableDatabase(),st);
        modelFirebase.addParent(p);
    }

    public void addBabySitter(BabySitter bs) {
        //StudentSql.addStudent(modelSql.getWritableDatabase(),st);
          modelFirebase.addBabySitter(bs);
    }

    public void addStudent(Student st) {
        //StudentSql.addStudent(modelSql.getWritableDatabase(),st);
      //  modelFirebase.addStudent(st);
    }



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
}
