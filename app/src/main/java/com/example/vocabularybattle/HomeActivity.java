package com.example.vocabularybattle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.vocabularybattle.model.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
private Button logoutButton;
private TextView nameTextView;
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
private CollectionReference usersRef = rootRef.collection("/users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String name=getIntent().getStringExtra("name");
        String uname=getIntent().getStringExtra("uname");
        String usrname=uname.substring(0,3)+FirebaseAuth.getInstance().getCurrentUser().getUid().substring(0,3)+ (int) (Math.random() * 200);
        User user=new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),uname,usrname);
        usersRef.add(user);
        nameTextView=findViewById(R.id.nameTxtView);
        nameTextView.setText(name);
        logoutButton=findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if(name!=null)
                LoginManager.getInstance().logOut();
                finish();
            }
        });
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }
}