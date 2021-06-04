package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();  //displaying a log message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Reference: https://developer.android.com/training/basics/firstapp/starting-activity*/
    //===================== method for Register Movie Button =====================
    public void launchRegisterMovie(View view) {
        Log.d(LOG_TAG,"'Register Movie' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, RegisterMovieScreen.class);
        startActivity(intent);
    }

    //=====================  method for Display Movies Button =====================
    public void launchDisplayMovies(View view) {
        Log.d(LOG_TAG,"'Display Movies' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, DisplayMoviesScreen.class);
        startActivity(intent);
    }

    //===================== method for Favourites Button =====================
    public void launchFavourites(View view) {
        Log.d(LOG_TAG,"'Favourites' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, FavouritesScreen.class);
        startActivity(intent);
    }

    //===================== method for Edit Movies Button ==========================
    public void launchEditMovies(View view) {
        Log.d(LOG_TAG,"'Edit Movies' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, EditMoviesScreen.class);
        startActivity(intent);
    }

    //========================== method for Search Button ============================
    public void launchSearch(View view) {
        Log.d(LOG_TAG,"'Search' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, SearchScreen.class);
        startActivity(intent);
    }

    //======================== method for Ratings Button ===========================
    public void launchRatings(View view) {
        Log.d(LOG_TAG,"'Ratings' Button clicked!"); // displaying log message
        //creating an explicit intent by using Intent and opening the activity by using startActivity()
        Intent intent = new Intent(this, RatingsScreen.class);
        startActivity(intent);
    }

}