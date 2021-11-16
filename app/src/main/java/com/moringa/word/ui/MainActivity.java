//Check this link to understand Diff
//https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil.ItemCallback
package com.moringa.word.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moringa.word.R;
import com.moringa.word.adapter.WordListAdapter;
import com.moringa.word.model.Word;
import com.moringa.word.viewModel.WordViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //request code
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordViewModel wordViewModel;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.addButton);
        floatingActionButton.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        // add an observer for the LiveData returned by getAlphabetizedWords()
        //The onChanged() method fires when the observed data changes and the activity is in the foreground:

        wordViewModel.getmAllWords().observe(this, words -> {
         // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(EditWordActivity.EXTRA_REPLY));
            wordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.warning_if_word_empty,
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View view) {
        if(view == floatingActionButton){
            Intent intent = new Intent(MainActivity.this, EditWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        }
    }
}