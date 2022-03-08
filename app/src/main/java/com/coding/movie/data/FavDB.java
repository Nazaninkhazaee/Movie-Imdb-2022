package com.coding.movie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "MovieDB";
    public static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String MOVIE_TITLE = "movieTitle";
    public static String MOVIE_IMAGE = "movieImage";
    public static String FAVORITE_STATUS = "fStatus";
    public static String MOVIE_RATING = "movieRating";
    public static String MOVIE_DESCRIPTION = "movieDescription";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + MOVIE_TITLE + " TEXT,"
            + MOVIE_IMAGE + " TEXT," + MOVIE_RATING + " TEXT," + MOVIE_DESCRIPTION + " TEXT," + FAVORITE_STATUS + " TEXT)";

    public FavDB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // insert data into database
    public void insertIntoTheDatabase(
            String movie_image,
            String movie_title,
            String id,
            String fav_status,
            String movie_rating,
            String movie_desc) {

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_IMAGE, movie_image);
        cv.put(MOVIE_TITLE, movie_title);
        cv.put(KEY_ID, id);
        //cv.put(FAVORITE_STATUS,fav_status);
        cv.put(MOVIE_RATING, movie_rating);
        cv.put(MOVIE_DESCRIPTION, movie_desc);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB Status", movie_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    // read all data
    public Cursor read_all_data() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME;
        return db.rawQuery(sql, null, null);
    }

//    // read all data
//    public Cursor read_all_data(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID+"="+id+"";
//        return db.rawQuery(sql,null,null);
//    }

    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  " + FAVORITE_STATUS + " ='0' WHERE " + KEY_ID + "=" + id + "";
        db.execSQL(sql);
        Log.d("remove", id.toString());

    }

    // select all favorite list

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_STATUS + " ='1'";
        return db.rawQuery(sql, null, null);
    }

    // we have created a new method for reading all the courses.
    public ArrayList<MovieFavItem> readCourses() {
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<MovieFavItem> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new MovieFavItem(cursorCourses.getString(2),
                        cursorCourses.getString(1),
                        cursorCourses.getString(0),
                        cursorCourses.getString(5),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void deleteDatabase(Context mContext) {
        mContext.deleteDatabase(DATABASE_NAME);
    }

}
