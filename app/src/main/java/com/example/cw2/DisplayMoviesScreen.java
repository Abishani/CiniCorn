package com.example.cw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMoviesScreen extends AppCompatActivity {

    //==================== Initializing the database as movieDB ===================
    MovieDatabase movieDB;
    // ===================== Initializing the ListView ===================
    ListView listView;
    // ============= Initializing the Button ==================
    Button favouriteButton;

    // =========== creating an arrayList to save the checked movies=============
    ArrayList<String> checkedMovieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies_screen);


        //======= creating an instance of the database =============
        movieDB = new MovieDatabase(this);

        //============= getting the id of listView =================
        listView = findViewById(R.id.listOfMovies);

        //============== getting the id of favourite button ==============
        favouriteButton = findViewById(R.id.addToFavouritesBtn);

        //================= displaying  the checkbox in the listView ================
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //It will enable the clickable checkbox

        //=============== creating the displayMovies() method to display the registered movies ================
        displayMovies();

    }

    /**
     * References: https://www.youtube.com/watch?v=9t8VVWebRFM
     */
    //Displaying movies stored in the MovieDatabase on the DisplayMovieScreen
    private void displayMovies() {
        //array list which the registered the registered movies are stored
        ArrayList<String> movieArrayList = new ArrayList<>();
        Cursor cursor = movieDB.getData();
        while (cursor.moveToNext()) {
            movieArrayList.add(cursor.getString(0));
        }


        //cursor - stores the query which are stored in the Database
        //displaying the toast message when the cursor count equals to 0
        if (cursor.getCount() == 0) {
            Toast.makeText(DisplayMoviesScreen.this, "NO ANY MOVIES ARE AVAILABLE", Toast.LENGTH_SHORT).show();
            return;
        }

        //displaying movie names to the display screen

        /** Reference : https://stackoverflow.com/questions/16378456/checkbox-not-clickable-in-simple-listview */
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.checbox_item, R.id.txt_title, movieArrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (checkedMovieList.contains(selectedItem))
                    checkedMovieList.remove(selectedItem); //uncheck item
                else
                    checkedMovieList.add(selectedItem);

            }
        });

    }


    //add selected items to database
    public void addMoviesToFavourites(View view) {
        String checkMovies = "";
        for (String movie : checkedMovieList) {
            movieDB.addAvailabilityList(movie);

            if (checkMovies == "") {
                checkMovies = movie;
            } else {
                checkMovies += "/" + movie;
            }
        }

        Toast.makeText(this, checkMovies + " added to favourites", Toast.LENGTH_SHORT).show();

    }


}