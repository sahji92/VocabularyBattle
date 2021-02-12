 package com.example.vocabularybattle.persistance;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vocabularybattle.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

 public class AuthRepository {

   private FragmentActivity fActivity;
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection("/users");
   // private CollectionReference customUsersRef = rootRef.collection("/customUsers");
   private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private static final String TAG="Authrepository";

    public MutableLiveData<User> firebaseSignInWithFacebook(AuthCredential credential){
        MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(fActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if(firebaseUser!=null) {
                            String uid = firebaseUser.getUid();
                            String name = firebaseUser.getDisplayName();
                            String email = firebaseUser.getEmail();
                            String username=name.substring(0,3)+uid.substring(0,3)+ (int) (Math.random() * 200);
                            User user = new User(uid, name, email,username);
                            user.isNew=isNewUser;
                            authenticatedUserMutableLiveData.setValue(user);
                        }
                      //  updateUI(user);
                    } else {
                        //If sign in fails, display a message to the user.
                        Log.e(TAG,task.getException().getMessage());
                    }
                });
        return authenticatedUserMutableLiveData ;
    }

    public void setMainCntxt(FragmentActivity activity) {
        this.fActivity=activity;
    }

    public LiveData<User> createUserInFirestoreIfNotExists(User authenticatedUser) {
        MutableLiveData<User> newUserMutableLiveData = new MutableLiveData<>();
        Log.d(TAG,"inside createuserinfirebase()");
        DocumentReference uidRef = usersRef.document(authenticatedUser.uid);
        uidRef.get().addOnCompleteListener(uidTask -> {
            if (uidTask.isSuccessful()) {
                Log.d(TAG,"inside uidtask.isSuccessful, createuserinfirebase()");
                DocumentSnapshot document = uidTask.getResult();
                if (!document.exists()) {
                    Log.d(TAG,"inside !document.exists, createuserinfirebase()");
                    uidRef.set(authenticatedUser).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
                            authenticatedUser.isCreated = true;
                            newUserMutableLiveData.setValue(authenticatedUser);
                            Log.d(TAG,"inside usercreationtask.isSuccessful, createuserinfirebase()");
                        } else {
                            Log.d(TAG,"inside usercreationtask.isNotSuccessful, createuserinfirebase()");
                            Log.e(TAG,userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    Log.d(TAG,"inside document exist, createuserinfirebase()");
                    newUserMutableLiveData.setValue(authenticatedUser);
                }
            } else {
                Log.d(TAG,"inside !uidtask.isSuccessful, createuserinfirebase()");
                Log.e(TAG,uidTask.getException().getMessage());
            }
        });
        return newUserMutableLiveData;
    }

}
