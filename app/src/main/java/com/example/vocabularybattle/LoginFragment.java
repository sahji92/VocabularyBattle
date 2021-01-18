package com.example.vocabularybattle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.vocabularybattle.model.User;
import com.example.vocabularybattle.viewmodel.AuthViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private AppCompatButton loginButton;
    private LoginButton fbLoginButton;
    private MaterialTextView signupButton;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private AuthViewModel authViewModel;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "Loginfragment";


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton=view.findViewById(R.id.loginButton);
        signupButton=view.findViewById(R.id.signupButton);
        NavController controller= Navigation.findNavController(view);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_loginFragment_to_signupFragment3);
            }
        });
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            Intent intent=new Intent(getActivity(),HomeActivity.class);
            Log.d(TAG,"INSIDE IS LOG INNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
            intent.putExtra("name",mAuth.getCurrentUser().getDisplayName());
            startActivity(intent);
        }
        initFbSignInButton();
        initAuthViewModel();
        initProgressDialog();
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.setMainContext(getActivity());
    }

    private void initFbSignInButton() {
        fbLoginButton.setPermissions(Arrays.asList(EMAIL));
        fbLoginButton.setFragment(this);
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        progressDialog.show(); // Display Progress Dialog
       authViewModel.signinWithFacebook(credential);
       authViewModel.authenticatedUserLivedata.observe(this,authenticatedUser->{
           if(authenticatedUser.isNew){
           createNewUser(authenticatedUser);
           }
           else{
               goToHomeActivity(authenticatedUser);
           }
       });

    }
    private void createNewUser(User authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
               Toast.makeText(getActivity(),"user created",Toast.LENGTH_LONG).show();
            }
            goToHomeActivity(user);
        });
    }
    public void goToHomeActivity(User user){
        Intent intent=new Intent(getActivity(),HomeActivity.class);
        intent.putExtra("name",user.name);
        startActivity(intent);
        progressDialog.dismiss();
        getActivity().finish();
    }
public void initProgressDialog(){
    progressDialog=new ProgressDialog(getActivity());
    progressDialog.setMessage("Loading..."); // Setting Message
    progressDialog.setTitle("VocabularyBattle"); // Setting Title
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
    progressDialog.setCancelable(false);
    progressDialog.setIcon(R.drawable.v);
}
}