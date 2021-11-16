//Created a class called WordViewModel that gets the Application as a parameter and extends AndroidViewModel.
//Added a private member variable to hold a reference to the repository.
//Added a getAllWords() method to return a cached list of words.
//Implemented a constructor that creates the WordRepository.
//In the constructor, initialized the allWords LiveData using the repository.
//Created a wrapper insert() method that calls the Repository's insert() method. In this way, the implementation of insert() is encapsulated from the UI.
package com.moringa.word.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.moringa.word.model.Word;
import com.moringa.word.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application){
        super(application);
        wordRepository = new WordRepository(application);
        mAllWords = wordRepository.getmAllWords();
    }

    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        wordRepository.insert(word);
    }
}
