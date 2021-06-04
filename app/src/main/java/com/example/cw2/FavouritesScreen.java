package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesScreen extends AppCompatActivity {

    //==================== Initializing the database as movieDB ===================
    MovieDatabase movieDB;
    // ===================== Initializing the ListView ===================
    ListView listViewofFavorites;
    // ============= Initializing the Button ==================
    Button saveButton;

    // =========== creating an arrayList to save the checked movies=============
    ArrayList<String> checkedMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_screen);

        //======= creating an instance of the database =============
        movieDB = new MovieDatabase(this);

        //============= getting the id of listView =================
        listViewofFavorites = findViewById(R.id.listOfFavourites);

        //============== getting the id of favourite button ==============
        saveButton = findViewById(R.id.saveFavouritesBtn);

        //================= displaying  the checkbox in the listView ================
        listViewofFavorites.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //It will enable the clickable checkbox

        //=============== creating the displayMovies() method to display the registered movies ================
        displayFavouriteMovies();

    }

    /**
     * References: https://www.youtube.com/watch?v=9t8VVWebRFM
     */
    //Displaying movies stored in the MovieDatabase on the DisplayMovieScreen
    private void displayFavouriteMovies() {
        //array list which the registered the registered movies are stored
        ArrayList<String> favouriteMovies = new ArrayList<>();
        Cursor cursor = movieDB.displayListOfMovies();
        while (cursor.moveToNext()) {
            favouriteMovies.add(cursor.getString(0));
        }


        //cursor - stores the query which are stored in the Database
        //displaying the toast message when the cursor count equals to 0
        if (cursor.getCount() == 0) {
            Toast.makeText(FavouritesScreen.this, "NO ANY FAVORITE MOVIES ARE AVAILABLE", Toast.LENGTH_SHORT).show();
            return;
        }


        //displaying movie names to the display screen

        /** Reference : https://stackoverflow.com/questions/16378456/checkbox-not-clickable-in-simple-listview */
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.checkedbox_item, R.id.txt_Availability, favouriteMovies);
        listViewofFavorites.setAdapter(arrayAdapter);

        //================ getting the favourites  =================
        for (int x = 0; x < favouriteMovies.size(); x++)
            listViewofFavorites.setItemChecked(x, true);
        listViewofFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (checkedMovies.contains(selectedItem))
                    checkedMovies.remove(selectedItem); //uncheck item
                else
                    checkedMovies.add(selectedItem);

            }
        });

    }


    //================ add selected items to database =====================
    public void savedFavouriteMovies(View view) {
        String checkMovies = "";
        for (String movie : checkedMovies) {
            movieDB.addFalseAvailabilityList(movie);

            if (checkMovies == "") {
                checkMovies = movie;
            } else {
                checkMovies += "/" + movie;
            }
        }


        Toast.makeText(this, checkMovies + " removed from favourites", Toast.LENGTH_SHORT).show();

    }
}