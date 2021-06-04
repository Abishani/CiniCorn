package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMovieDetailsScreen extends AppCompatActivity {

    //============================= Initializing the TextView =============================
    TextView movieTitle, releasedYear, directorName, actorNames, rating, review, favourite;
    //============================= Initializing the MovieDatabase =============================
    MovieDatabase movieDB;
    //============================= Initializing the Button =============================
    Button updateButton;
    //============================= Initializing the minYear =============================
    int minYear = 1895;
//    RatingBar starRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_details_of_movie);

        //======= creating an instance of the database =============
        movieDB = new MovieDatabase(this);

        //======= creating intent =============
        Intent intent = getIntent();

        //======================= getting the string ===============================
        String nameOfTheMovie = intent.getExtras().getString("movieName");

        //==================== Creating the Toast message in Screen ===================
        Toast.makeText(DisplayMovieDetailsScreen.this, nameOfTheMovie, Toast.LENGTH_SHORT).show();

        //============= getting the id of movieTitle =================
        movieTitle = findViewById(R.id.inputMovieTitle);
        //============= getting the id of releasedYear =================
        releasedYear = findViewById(R.id.inputMovieYear);
        //============= getting the id of directorName =================
        directorName = findViewById(R.id.inputMovieDirector);
        //============= getting the id of actorNames =================
        actorNames = findViewById(R.id.inputMovieCast);
        //============= getting the id of rating =================
        rating = findViewById(R.id.inputMovieRating);
        //============= getting the id of review =================
        review = findViewById(R.id.inputMovieReview);
        //============= getting the id of favourite =================
        favourite = findViewById(R.id.inputFavouriteState);
        //============= getting the id of updateButton =================
        updateButton = findViewById(R.id.updateBtn);

//        starRatingBar = findViewById(R.id.ratingBar);
        //creating a fillDetailFields() method
        fillDetailFields(nameOfTheMovie);


        //============= Setting range for movie rating between 1 - 10 =====================
        int minRatingValue = 1;   //minimum rating value
        int maxRatingValue = 10;  //maximum rating value

        //============ setting the input type ==================
        rating.setInputType(InputType.TYPE_CLASS_NUMBER);
        rating.setFilters(new InputFilter[]{
                new DisplayMovieDetailsScreen.InputFilterMinMax(minRatingValue, maxRatingValue),
                new InputFilter.LengthFilter(String.valueOf(maxRatingValue).length())
        });
        rating.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

    }

    /**
     * Reference : https://stackoverflow.com/questions/41505465/making-edittext-accept-a-range-of-values-without-post-validation
     */

    //=============== creating a private class InputFilterMinMax ===================
    private class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int minRatingValue, int maxRatingValue) {
            this.min = minRatingValue;
            this.max = maxRatingValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

    }


    /** References : https://stackoverflow.com/questions/41315439/java-android-append-multiple-strings-skip-if-string-is-null*/
    //display the movie details in the DisplayMovieDetailsScreen activity
    private void fillDetailFields(String nameOfTheMovie) {
        Cursor res = movieDB.search(nameOfTheMovie);
        //displaying the Toast message if not any movies are found
        if (res.getCount() == 0) {
            Toast.makeText(DisplayMovieDetailsScreen.this, "Nothing to show", Toast.LENGTH_LONG).show();
        } else {
            //=============== getting the string from the database =======================
            while (res.moveToNext()) {
                movieTitle.append(res.getString(0));
                releasedYear.append(res.getString(1));
                directorName.append(res.getString(2));
                actorNames.append(res.getString(3));
                rating.append(res.getString(4));
                review.append(res.getString(5));
                favourite.append(res.getString(6));
                if (res.getString(6).equals("true")) {
                    favourite.setText("Favourite");
                } else {
                    favourite.setText("Not Favourite");
                }
            }
        }

    }

    /**
     * References : https://stackoverflow.com/questions/14050845/why-we-use-string-message-edittext-gettext-tostring-after-using-edittext-e
     */
    //=============== creating a onClick method for update button====================
    public void updateMovieDetails(View v) {
        //========== initializing the year ===============
        String year;

        //================ updating the database details using updateEditData==============
        boolean isUpdate = movieDB.updateEditData(
                movieTitle.getText().toString(), //getting movieTitle
                year = releasedYear.getText().toString(), //getting releasedYear
                directorName.getText().toString(), //getting directorName
                actorNames.getText().toString(),  //getting actorNames
                rating.getText().toString(), //getting rating
                review.getText().toString(), //getting review
                favourite.getText().toString());  //getting favourite


        //================ checking the range of the year ====================
        if (Integer.valueOf(year) < minYear) {
            releasedYear.setTextColor(Color.RED);
            Toast.makeText(DisplayMovieDetailsScreen.this, "Details are not added, Movies should be released after 1895", Toast.LENGTH_LONG).show();

            //================ checking the database details are updated or not  ====================
        } else if (isUpdate == true) {
            Toast.makeText(DisplayMovieDetailsScreen.this, "Data Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EditMoviesScreen.class);
            finish();
            startActivity(intent);

            //================ checking the database details are updated or not and displaying the message ====================
        } else {
            Toast.makeText(DisplayMovieDetailsScreen.this, "Movie Detail is Not Updated", Toast.LENGTH_LONG).show();
        }

    }

    /** References : https://www.tutorialspoint.com/how-does-activity-finish-work-in-android*/
    @Override
    public void finish() {
        Intent intent = new Intent(this, EditMoviesScreen.class);
        super.finish();
        startActivity(intent);
    }
}