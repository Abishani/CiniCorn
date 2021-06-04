package com.example.cw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchScreen extends AppCompatActivity {

    MovieDatabase movieDB;
    EditText searchText;
    Button lookupButton;
    TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        movieDB = new MovieDatabase(this);
        searchText = findViewById(R.id.inputSearchItem);
        lookupButton = findViewById(R.id.lookUpBtn);
        searchResult = findViewById(R.id.displaySearchResult);
    }


    //=================== creating a viewAll method for Lookup button ===========================
    /** Reference: https://developer.android.com/reference/android/view/inputmethod/InputMethodManager*/
    public void viewAll(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!searchText.getText().toString().equals("")) {
            String count = "\uD83C\uDF7F";
            searchResult.setText("");
            String let = searchText.getText().toString();
            Cursor cursor = movieDB.search(let);

            if (cursor.getCount() == 0) {
                Toast.makeText(SearchScreen.this, "Movie not found", Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    if (!(searchResult.getText().toString().equals(cursor.getString(0).toLowerCase()))) {
                        searchResult.append(count + " " + cursor.getString(0) + " \n\n");
                        searchResult.setTextColor(Color.WHITE);
                    }
                }
            }
            searchText.setText("");
        }
    }
}