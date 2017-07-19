package nati.aviran.getbs.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by nati2 on 29/06/2017.
 */

public class ModelFirebase {

    public void addBabySitter(BabySitter bs) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("babySitter");
        Map<String, Object> value = new HashMap<>();
        value.put("email", bs.email);
        value.put("name", bs.name);
        value.put("imageUrl", bs.imageUrl);
        value.put("password", bs.password);
        value.put("address", bs.address);
        value.put("age", bs.age);
        value.put("availability", bs.availability);
        value.put("salary", bs.salary);
        value.put("phone", bs.phone);
        value.put("lastUpdateDate", ServerValue.TIMESTAMP);
        myRef.child(bs.email).setValue(value);
    }


    public void addParent(Parent p) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Parent");
        Map<String, Object> value = new HashMap<>();
        value.put("email", p.email);
        value.put("name", p.name);
        value.put("password", p.password);
        value.put("address", p.address);
        myRef.child(p.email).setValue(value);
    }


    interface GetBabySitterCallback {
        void onComplete(BabySitter bs);

        void onCancel();
    }

    public void getBabySitter(String email, final GetBabySitterCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("babySitter");
        myRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BabySitter bs = dataSnapshot.getValue(BabySitter.class);
                callback.onComplete(bs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }


    interface GetAllBabySittersAndObserveCallback {
        void onComplete(List<BabySitter> list);
        void onCancel();
    }
    public void getAllBabySittersAndObserve(final GetAllBabySittersAndObserveCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("babySitter");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<BabySitter> list = new LinkedList<BabySitter>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    BabySitter babySitter = snap.getValue(BabySitter.class);
                    list.add(babySitter);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }


    public void getAllBabySittersAndObserve(double lastUpdateDate,
                                         final GetAllBabySittersAndObserveCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("babySitter");

        myRef.orderByChild("lastUpdateDate").startAt(lastUpdateDate)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<BabySitter> list = new LinkedList<BabySitter>();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            BabySitter babySitter = snap.getValue(BabySitter.class);
                            list.add(babySitter);
                        }
                        callback.onComplete(list);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }













}
