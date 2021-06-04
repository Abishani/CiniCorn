package com.example.cw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import java.util.Arrays;


public class MovieDatabase extends SQLiteOpenHelper {

    /**
     * References: https://developer.android.com/training/data-storage/sqlite#java
     */
    // ============================ Initializing the variables ===================================
    private static final String DATABASE_NAME = "movies.db"; //Initializing the database name
    private static final int DATABASE_VERSION = 1;    //Initializing the version
    private static final String TABLE_NAME = "MovieDetails";  //Initializing the Table name
    private static final String COLUMN_1 = "movieName";   //Initializing the column 1 name as movieName
    private static final String COLUMN_2 = "year";       //Initializing the column 2 name as year
    private static final String COLUMN_3 = "directorName";  //Initializing the column 3 name as directorName
    private static final String COLUMN_4 = "movieCastName"; //Initializing the column 4 name as movieCastName
    private static final String COLUMN_5 = "rating";        //Initializing the column 5 name as rating
    private static final String COLUMN_6 = "review";        //Initializing the column 6 name as review
    private static final String COLUMN_7 = "favourites";    //Initializing the column 7 name as favourites


    //=============== Creating a constructor ========================
    public MovieDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //========= Creating a onCreate method to create a table and including the column names =========================
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_1 + " TEXT PRIMARY KEY , " + COLUMN_2 + " NUMBER, " + COLUMN_3 + " TEXT, " + COLUMN_4 + " TEXT," + COLUMN_5 + " NUMBER, " + COLUMN_6 + " TEXT," + COLUMN_7 + " TEXT)");
    }

    //=============== onUpgrade is crated to update any changes of database ===========

    /**
     * Reference : https://stackoverflow.com/questions/7622122/sqlite-add-column-keep-data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * References: https://www.youtube.com/watch?v=9t8VVWebRFM
     */
    //=================== adding movie details =============================
    public Boolean addMovieDetail(String movieName, String year, String directorName, String movieCastName, String rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, movieName);
        contentValues.put(COLUMN_2, year);
        contentValues.put(COLUMN_3, directorName);
        contentValues.put(COLUMN_4, movieCastName);
        contentValues.put(COLUMN_5, rating);
        contentValues.put(COLUMN_6, review);
        contentValues.put(COLUMN_7, "NULL");
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //====================== get all the details from the database =========================
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " ORDER BY " + COLUMN_1 + " COLLATE NOCASE ASC; ", null);
        System.out.println(Arrays.toString(cursor.getColumnNames()));
        return cursor;
    }


    //====================== get only the movie name from the database and displaying =========================
    public void addAvailabilityList(String movieName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String test = "UPDATE " + TABLE_NAME + " SET " + COLUMN_7 + " = 'true' WHERE movieName = '" + movieName + "'";
        System.out.println(test);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_7 + " = 'true' WHERE movieName = '" + movieName + "'");


    }

    //====================== method to check all the checkboxes and displaying it  =========================
    public void addFalseAvailabilityList(String movieName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String test = "UPDATE " + TABLE_NAME + " SET " + COLUMN_7 + " = 'false' WHERE movieName = '" + movieName + "'";
        System.out.println(test);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_7 + " = 'false' WHERE movieName = '" + movieName + "'");
    }


    // ======================== To display only favorite movie names which are added as favourites in previous activity ===============================
    public Cursor displayListOfMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from  " + TABLE_NAME + " WHERE " + COLUMN_7 + "= 'true'", null);

        // Cursor result=db.rawQuery("SELECT * FROM "+DataBase.TABLE_NAME+ " ORDER BY "+DataBase.inputString_Col+" ASC", new String[] {} );
        return result;
    }

    // ================= getting all the details of movie =============================
    public Cursor getFullMovieDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + MovieDatabase.TABLE_NAME + " ORDER BY " + MovieDatabase.COLUMN_1 + " ASC"
                , new String[]{});
        System.out.println(Arrays.toString(result.getColumnNames()));
        return result;
    }

    //============== updating the movie Details ====================
    public boolean updateEditData(String movieName, String year, String directorName, String movieCastName, String rating, String review, String favourites) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, movieName);
        contentValues.put(COLUMN_2, year);
        contentValues.put(COLUMN_3, directorName);
        contentValues.put(COLUMN_4, movieCastName);
        contentValues.put(COLUMN_5, rating);
        contentValues.put(COLUMN_6, review);
        contentValues.put(COLUMN_7, favourites);
        db.update(TABLE_NAME, contentValues, "movieName = ?", new String[]{movieName});
        //db.update(TABLE_NAME, contentValues, "NAME = ?"+name,null);
        return true;

    }

    //====================== method to search the movie name according to movieName/movieCastName/directorName =================================
    public Cursor search(String letters) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE movieName OR directorName OR movieCastName LIKE " +
                "'" + letters + "%' OR movieName LIKE '%" + letters + "%'  OR directorName LIKE '%" + letters + "%'  OR  movieCastName LIKE '%" + letters + "'", null);
        return res;
    }


}
