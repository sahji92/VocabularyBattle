package com.example.vocabularybattle.viewmodel;
import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vocabularybattle.model.User;
import com.example.vocabularybattle.persistance.AuthRepository;
import com.google.firebase.auth.AuthCredential;
public class AuthViewModel extends AndroidViewModel {
    public LiveData<User> createdUserLiveData;
    AuthRepository authRepository;
  public LiveData<User> authenticatedUserLivedata;
  public FragmentActivity fActivity;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository=new AuthRepository();
    }
   public void signinWithFacebook(AuthCredential credential){
     authenticatedUserLivedata=authRepository.firebaseSignInWithFacebook(credential);
    }

    public void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }

    public void setMainContext(FragmentActivity activity) {
        authRepository.setMainCntxt(activity);
    }

    public void register(String username, String password) {
        authRepository.register(username,password);
    }
}
