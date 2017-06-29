package nati.aviran.getbs.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nati2 on 29/06/2017.
 */

public class ModelFirebase {

    public void addStudent(Student st) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.child(st.id).setValue(st);
    }

    interface GetStudentCallback {
        void onComplete(Student student);

        void onCancel();
    }

    public void getStudent(String stId, final GetStudentCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.child(stId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                callback.onComplete(student);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    interface GetAllStudentsAndObserveCallback {
        void onComplete(List<Student> list);
        void onCancel();
    }
    public void getAllStudentsAndObserve(final GetAllStudentsAndObserveCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Student> list = new LinkedList<Student>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Student student = snap.getValue(Student.class);
                    list.add(student);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }
}
