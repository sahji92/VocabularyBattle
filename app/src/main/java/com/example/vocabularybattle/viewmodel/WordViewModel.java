package com.example.vocabularybattle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vocabularybattle.model.WordSource;
import com.example.vocabularybattle.persistance.WordRepository;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends AndroidViewModel {
       private LiveData<List<WordSource>> wordSources;
       private WordRepository wordRepository;
    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository=new WordRepository(application);
        wordSources=wordRepository.getAllWordSources();
    }
    public LiveData<List<WordSource>> getAllWordSources(){
    return wordSources;
    }
}
