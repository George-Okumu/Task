//The DAO is passed into the repository constructor as opposed to the whole database.
// This is because you only need access to the DAO, since it contains all the read/write methods for the database.
// There's no need to expose the entire database to the repository.
//The getAllWords method returns the LiveData list of words from Room; we can do this because of how we defined the
// getAlphabetizedWords method to return LiveData in the "The LiveData class" step. Room executes all queries on a separate thread.
// Then observed LiveData will notify the observer on the main thread when the data has changed.
//We need to not run the insert on the main thread, so we use the ExecutorService we created in the WordRoomDatabase to perform the
// insert on a background thread.
//here we only use one datasource, the repository doesn't do much, check below link for more complex implementation.
//https://github.com/android/architecture-components-samples/tree/master/BasicSample

package com.moringa.word.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.moringa.word.DAO.WordDao;
import com.moringa.word.DAO.WordRoomDatabase;
import com.moringa.word.model.Word;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> mAllWords;

   public WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        mAllWords = wordDao.getAllAlphabeticalWords();
    }

    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        WordRoomDatabase.databaseWriterExecutor.execute(() -> {
            wordDao.insert(word);
        });
    }
}
