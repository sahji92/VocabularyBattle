package com.example.vocabularybattle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vocabularybattle.model.User;
import com.facebook.login.LoginManager;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private Button logoutButton;
    private TextView nameTextView;
    private MaterialCardView learnCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String name = getIntent().getStringExtra("name");
        //nameTextView=findViewById(R.id.nameTxtView);
        // nameTextView.setText(name);
        logoutButton = findViewById(R.id.logoutButton);
        learnCard = findViewById(R.id.learnCard);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if (name != null)
                    LoginManager.getInstance().logOut();
                transitionToMainActivity();
            }
        });
        learnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LearnActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.to_left, R.anim.to_right);
            }
        });
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }

    public void transitionToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.to_left, R.anim.to_right);
    }
}