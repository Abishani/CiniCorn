package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMoviesScreen extends AppCompatActivity {

    //Initializing the database as movieDB
    MovieDatabase movieDB;
    //Initializing the ListView
    ListView watchedMovieslistView;
    //Initializing the Button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies_screen);

        //======= creating an instance of the database =============
        movieDB = new MovieDatabase(this);

        //============= getting the id of listView =================
        watchedMovieslistView = findViewById(R.id.listViewOfWatchedMovies);

        //=============== creating the displayMovies() method to display the registered movies ================
        displayWatchedMovies();

    }


    /**
     * References: https://www.youtube.com/watch?v=9t8VVWebRFM
     */
    private void displayWatchedMovies() {
        //array list which the registered the registered movies are stored
        ArrayList<String> movieArrayList = new ArrayList<>();
        Cursor cursor = movieDB.getFullMovieDetails();


        //cursor - stores the query which are stored in the Database
        //displaying the toast message when the cursor count equals to 0
        if (cursor.getCount() == 0) {
            Toast.makeText(EditMoviesScreen.this, "NO ANY WATCHED MOVIES ARE AVAILABLE", Toast.LENGTH_SHORT).show();
        } else {

            //displaying movie names to the display screen
            while (cursor.moveToNext()) {
                movieArrayList.add(cursor.getString(0));

                /** Reference : https://stackoverflow.com/questions/16378456/checkbox-not-clickable-in-simple-listview */
                //displaying  the checkbox in the listView
                watchedMovieslistView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //It will enable the clickable checkbox
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieArrayList);
                watchedMovieslistView.setAdapter(arrayAdapter);
                watchedMovieslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //============ selecting the checkbox item ====================
                        String selectedItem = ((TextView) view).getText().toString();
                        //============== creating an intent ===================
                        Intent intent = new Intent();
                        intent.setClass(EditMoviesScreen.this, DisplayMovieDetailsScreen.class);
                        intent.putExtra("movieName", selectedItem);
                        finish();
                        startActivity(intent);

                    }
                });
            }

        }

        /** To display list items in new screen */
        /** https://stackoverflow.com/questions/24785203/show-listview-item-details-in-second-activity */


        /** To display list items in new screen inside different textViews */
        /** https://stackoverflow.com/questions/28593589/display-data-from-sqlite-db-into-different-textviews */

    }


}