package com.example.vocabularybattle.persistance;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vocabularybattle.model.Word;
import com.example.vocabularybattle.model.WordSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WordRepository {
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference wordSourceRef = rootRef.collection("/wordsource");
    private  MutableLiveData<List<WordSource>> wordSources=new MutableLiveData<>();
    private static final String TAG="Wordrepository";
    public WordRepository(Application application) {

    }

    public LiveData<List<WordSource>> getAllWordSources() {
       wordSourceRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   List<WordSource> list = new ArrayList<>();
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       WordSource wordSource=new WordSource((String) document.get("sourceName"),new Word(),(String) document.get("imageUrl"));
                       list.add(wordSource);
                   }
                   wordSources.setValue(list);
               }
               else {

               }
           }

       });
        return wordSources;
    }
}
