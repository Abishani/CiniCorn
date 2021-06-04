package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterMovieScreen extends AppCompatActivity {

    EditText movieTitle, movieYear, movieDirector, movieCast, movieRating, movieReview;
    Button saveButton;
    //    Button clearButton;
    MovieDatabase movieDB;

    int minYear = 1895;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);


        //========= initializing the button id to button variable ==========
        saveButton = findViewById(R.id.saveBtn);

        //============ Creating an instance for MovieDatabase class =================
        movieDB = new MovieDatabase(this);

        //============ initializing the editText id to editText variables ===========
        movieTitle = findViewById(R.id.inputMovieTitle);
        movieYear = findViewById(R.id.inputMovieYear);
        movieDirector = findViewById(R.id.inputMovieDirector);
        movieCast = findViewById(R.id.inputMovieCast);
        movieRating = findViewById(R.id.inputMovieRating);
        movieReview = findViewById(R.id.inputMovieReview);


        /**
         * Reference : https://stackoverflow.com/questions/41505465/making-edittext-accept-a-range-of-values-without-post-validation
         */

        //============= Setting range for movie rating between 1 - 10 =====================
        int minRatingValue = 1;
        int maxRatingValue = 10;

        movieRating.setInputType(InputType.TYPE_CLASS_NUMBER);
        movieRating.setFilters(new InputFilter[]{
                new InputFilterMinMax(minRatingValue, maxRatingValue),
                new InputFilter.LengthFilter(String.valueOf(maxRatingValue).length())
        });
        movieRating.setKeyListener(DigitsKeyListener.getInstance("0123456789"));


        //======= adding onClickListener to the Save button ==========
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedMovieName = String.valueOf(movieTitle.getText());

                String movieTitleText = movieTitle.getText().toString();
                String movieYearText = movieYear.getText().toString();
                String movieDirectorText = movieDirector.getText().toString();
                String movieCastText = movieCast.getText().toString();
                String movieRatingText = movieRating.getText().toString();
                String movieReviewText = movieReview.getText().toString();


                //======================= creating a arrayList to store the movie =======================
                ArrayList<String> movieArrayList = new ArrayList<>();
                Cursor cursor = movieDB.getData();
                while (cursor.moveToNext()) {
                    if (Integer.valueOf(movieYearText) < minYear) {
                        movieYear.setTextColor(Color.RED);
                        Toast.makeText(RegisterMovieScreen.this, "Movies should be released after 1895", Toast.LENGTH_SHORT).show();
                    }
                    movieArrayList.add(cursor.getString(0));
                }

                //===================== checking if the movie is added or not =============================
                boolean foundMovie = movieArrayList.contains(addedMovieName);
                if (foundMovie) {
                    Toast.makeText(RegisterMovieScreen.this, "Already this movie is added", Toast.LENGTH_SHORT).show();
                    movieTitle.setText("");
                    movieYear.setText("");
                    movieYear.setTextColor(Color.WHITE);
                    movieCast.setText("");
                    movieDirector.setText("");
                    movieRating.setText("");
                    movieReview.setText("");
                }
                //============== Checking the movie year is greater than 1895 ================
                else if (Integer.valueOf(movieYearText) < minYear) {
                    movieYear.setTextColor(Color.RED);
                    Toast.makeText(RegisterMovieScreen.this, "Details are not added, Movies should be released after 1895", Toast.LENGTH_LONG).show();

                } else {
                    //=============== Checking if the data is inserted or not ======================
                    boolean checkInsertData = movieDB.addMovieDetail(movieTitleText, movieYearText, movieDirectorText, movieCastText, movieRatingText, movieReviewText);
                    if (checkInsertData == true) {
                        Toast.makeText(RegisterMovieScreen.this, "New Movie Details are Added", Toast.LENGTH_SHORT).show();
                        //clearing all the edit text details after adding movie details
                        movieTitle.setText("");
                        movieYear.setText("");
                        movieYear.setTextColor(Color.WHITE);
                        movieCast.setText("");
                        movieDirector.setText("");
                        movieRating.setText("");
                        movieReview.setText("");
                    } else
                        Toast.makeText(RegisterMovieScreen.this, "New Entry is not added, TRY AGAIN", Toast.LENGTH_SHORT).show();
                    movieTitle.setText("");
                    movieYear.setText("");
                    movieYear.setTextColor(Color.WHITE);
                    movieCast.setText("");
                    movieDirector.setText("");
                    movieRating.setText("");
                    movieReview.setText("");
                }

            }

        });


    }


    /**
     * Reference : https://stackoverflow.com/questions/41505465/making-edittext-accept-a-range-of-values-without-post-validation
     */

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

    //==================== method for clear button it will clear all the texts in the plain text===================
    public void clearText(View v) {
        movieTitle.setText("");
        movieYear.setText("");
        movieDirector.setText("");
        movieCast.setText("");
        movieRating.setText("");
        movieReview.setText("");
    }


}

