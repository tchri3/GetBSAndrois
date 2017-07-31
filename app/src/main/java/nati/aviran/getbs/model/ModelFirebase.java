package nati.aviran.getbs.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import nati.aviran.getbs.AddParentFragment;
import nati.aviran.getbs.LoginFragment;
import nati.aviran.getbs.MainActivity;

/**
 * Created by nati2 on 29/06/2017.
 */

public class ModelFirebase {

    boolean isConnect;

    public void addBabySitter(BabySitter babySitter) {

        final BabySitter bs = babySitter;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(bs.email, bs.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
                            myRef.child(encodeUserEmail(bs.email)).setValue(value);
                        } else {

                        }

                    }
                });

    }





    public interface GetLoginCallback {
        void onSuccess();
        void onFail();
    }

    public void login(String email,String password,final GetLoginCallback callback) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            callback.onSuccess();
                        }
                    }
                });



    }


        public void addParent(Parent parent) {

        final Parent p = parent;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(parent.email, parent.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Parent");
                            Map<String, Object> value = new HashMap<>();
                            value.put("email", p.email);
                            value.put("name", p.name);
                            value.put("password", p.password);
                            value.put("address", p.address);
                            myRef.child(encodeUserEmail(p.email)).setValue(value);
                        } else {

                        }

                    }
                });
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

    interface GetBabySitterCallback {
        void onComplete(BabySitter bs);

        void onCancel();
    }

    public void getBabySitter(String email, final GetBabySitterCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("babySitter");
        myRef.child(encodeUserEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BabySitter bs = dataSnapshot.getValue(BabySitter.class);
                bs.email = decodeUserEmail(bs.email);
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
                    babySitter.email = decodeUserEmail(babySitter.email);
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
                            babySitter.email = decodeUserEmail(babySitter.email);
                            list.add(babySitter);
                        }
                        callback.onComplete(list);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    public void saveImage(Bitmap imageBmp, String name, final Model.SaveImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference imagesRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.complete(downloadUrl.toString());
            }
        });
    }


    public void getImage(String url, final Model.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.onSuccess(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                listener.onFail();
            }
        });
    }










}
