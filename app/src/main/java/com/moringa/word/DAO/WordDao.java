package com.moringa.word.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.moringa.word.model.Word;

import java.util.List;

@Dao
public interface WordDao {

//    onConflict = OnConflictStrategy.IGNORE: The selected on conflict strategy
//    ignores a new word if it's exactly the same as one already in the list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    //Deletes all words
    @Query("DELETE FROM word_table")
    void deleteAll();

    //Returns a list of words in ascending order
    @Query("SELECT * FROM word_table ORDER BY word_column ASC")
    LiveData<List<Word>> getAllAlphabeticalWords();

}
