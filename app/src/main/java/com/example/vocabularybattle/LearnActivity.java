package com.example.vocabularybattle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.vocabularybattle.adapters.LearnRecyclerViewAdapter;
import com.example.vocabularybattle.model.WordSource;
import com.example.vocabularybattle.util.VerticalSpacingItemDecorator;
import com.example.vocabularybattle.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LearnRecyclerViewAdapter adapter;
    private WordViewModel wordViewModel;
    private ProgressDialog progressDialog;
private ArrayList<WordSource> wordSources=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initRecyclerView();
        initProgressDialog();
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWordSources().observe(this, new Observer<List<WordSource>>() {
            @Override
            public void onChanged(List<WordSource> wordSources) {
                progressDialog.show();
adapter.setNotes(wordSources);
adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.learnRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new LearnRecyclerViewAdapter(wordSources,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator=new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        recyclerView.setAdapter(adapter);
    }
    public void initProgressDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("VocabularyBattle"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.v);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}