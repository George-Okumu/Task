//The database class for Room must be abstract and extend RoomDatabase
// You annotate the class to be a Room database with @Database and use the annotation parameters
// to declare the entities that belong in the database and set the version number. Each entity corresponds to a
// table that will be created in the database. Database migrations are beyond the scope of this codelab, so we set
// exportSchema to false here to avoid a build warning. In a real app, you should consider setting a directory for Room to
// use to export the schema so you can check the current schema into your version control system.
// The database exposes DAOs through an abstract "getter" method for each @Dao.
// We've defined a singleton, WordRoomDatabase, to prevent having multiple instances of the database opened at the same time.
// getDatabase returns the singleton. It'll create the database the first time it's accessed,
// using Room's database builder to create a RoomDatabase object in the application context from the
// WordRoomDatabase class and names it "word_database".
// We've created an ExecutorService with a fixed thread pool that you will use to run database
// operations asynchronously on a background thread.

//Note: When you modify the database schema, you'll need to update the version number and define a migration strategy
// For a sample, a destroy and re-create strategy can be sufficient. But, for a real app, you must implement a migration strategy.
// Check https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
package com.moringa.word.DAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.moringa.word.model.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    private static volatile WordRoomDatabase INSTANCE;
    private static int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }

        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriterExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

//                Word word = new Word("Hello");
//                dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
            });
        }
    };
}
