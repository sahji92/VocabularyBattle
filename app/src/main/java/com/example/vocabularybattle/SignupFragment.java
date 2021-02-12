package com.example.vocabularybattle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.vocabularybattle.model.User;
import com.example.vocabularybattle.viewmodel.AuthViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private Button register;
private AuthViewModel authViewModel;
private TextInputEditText username;
private ProgressDialog progressDialog;
    private TextInputEditText password;
    private TextInputEditText repeatPassword;
    String usernamePattern="[a-zA-Z]+[@]+[v]+[b]+[.]+[c]+[o]+[m]";
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection("/users");
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    private static final String TAG="Authrepository";
    private ScrollView mainlayout;
    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialTextView signinButton=view.findViewById(R.id.signinButton);
        register=view.findViewById(R.id.registerButton);
        username=view.findViewById(R.id.signupUsername);
        password=view.findViewById(R.id.signupPassword);
        repeatPassword=view.findViewById(R.id.signupRepeatPassword);
        mainlayout=view.findViewById(R.id.mainlayout);
        initProgressDialog();
       initCustomUserViewModel();
        NavController controller= Navigation.findNavController(view);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_signupFragment3_to_loginFragment2);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    progressDialog.show();
                    String uname=username.getText().toString();
                    Log.d(TAG, "inside registeRRRRRRRRRR");
                    mAuth.createUserWithEmailAndPassword(uname,password.getText().toString())
                            .addOnCompleteListener(getActivity(), task -> {
                                Log.d(TAG, "inside addONCPMPLETElisterrrrrrrrrr");
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:successSSSSSSSSS");
                                    firebaseUser= task.getResult().getUser();
                                    String usrname = uname.substring(0, 3) +firebaseUser.getUid().substring(0, 2) + (int) (Math.random() * 200);
                                    User user = new User(firebaseUser.getUid(),uname, usrname);
                                    usersRef.add(user);
                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "createUserWithEmail:failureEEEEEEEEEEEEEEE", task.getException());
                                    Snackbar.make(mainlayout,"user already exist",Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.red))
                                            .setAction("close", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(R.color.primaryTextColor))
                                            .show();
                                }
                            });

                }
            }
        });
    }
    private void initCustomUserViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.setMainContext(getActivity());
    }
    public void initProgressDialog(){
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("VocabularyBattle"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.v);
    }
    private boolean validate() {
        boolean valid = true;

        String id = username.getText().toString();
        String pwd = password.getText().toString();
        String rPwd=repeatPassword.getText().toString();

        if (id.isEmpty() || id.length()<6||id.length()>16) {
            username.setError("id must contain atleast 6 and atmost 16 characters");
            valid = false;
        }
        else if(!id.trim().matches(usernamePattern)){
            username.setError("username must be in the format of JohnDoe@vb");
            valid = false;
        }
        else {
            username.setError(null);
        }
        if (pwd.isEmpty() || pwd.length() < 6 || pwd.length() > 16) {
            password.setError("password must contain atleast 6 and atmost 16 characters");
            valid = false;
        } else {
            password.setError(null);
        }
        if (rPwd.isEmpty() || rPwd.length() < 6 || rPwd.length() > 16) {
            repeatPassword.setError("password must contain atleast 6 and atmost 16 characters");
            valid = false;
        } else {
            repeatPassword.setError(null);
        }
        if (!pwd.equals(rPwd)) {
            repeatPassword.setError("password do not match");
            password.setError("password do not match");
            valid = false;
        } else {
            repeatPassword.setError(null);
        }
        return valid;
    }
}