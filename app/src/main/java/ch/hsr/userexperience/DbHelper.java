package ch.hsr.userexperience;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthias on 13.11.15.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserExperience.db";

    public static final String USER_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_AGE = "age";
    public static final String USER_COLUMN_GENDER = "gender";
    public static final String USER_COLUMN_LOCATION = "location";
    public static final String USER_COLUMN_PATIENCE = "patience";
    public static final String USER_COLUMN_ABO = "abo";
    public static final String USER_COLUMN_SATISFACTION = "satisfaction";
    public static final String USER_COLUMN_ABORTED  = "aborted";

    public static final String TEST_TABLE_NAME = "tests";
    public static final String TEST_COLUMN_ID = "id";
    public static final String TEST_COLUMN_USERID = "userid";
    public static final String TEST_COLUMN_SITE = "site";
    public static final String TEST_COLUMN_PLT = "plt";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createUserTable =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY," +
                USER_COLUMN_AGE + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_GENDER + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_LOCATION + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_PATIENCE + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_ABO + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_SATISFACTION + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_ABORTED + TEXT_TYPE +
        " )";

        final String createTestResultsTable =
            "CREATE TABLE " + TEST_TABLE_NAME + " (" +
                TEST_COLUMN_ID + " INTEGER PRIMARY KEY," +
                TEST_COLUMN_USERID + " INTEGER REFERENCES " + USER_TABLE_NAME + "(" + USER_COLUMN_ID + ") ON DELETE CASCADE" + COMMA_SEP +
                TEST_COLUMN_SITE + TEXT_TYPE + COMMA_SEP +
                TEST_COLUMN_PLT + " INTEGER" +
                " )";

        db.execSQL(createUserTable);
        db.execSQL(createTestResultsTable);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //For now, just drop old tables and make new ones
        final String deleteUserTable =
                "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        final String deleteTestResultsTable =
                "DROP TABLE IF EXISTS " + TEST_TABLE_NAME;
        db.execSQL(deleteTestResultsTable);
        db.execSQL(deleteUserTable);
        onCreate(db);
    }


    public void insertUserAndTests(User user, TestResults results) {
        if(user != null && results != null) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_AGE, user.get_age());
            values.put(USER_COLUMN_GENDER, user.get_gender());
            values.put(USER_COLUMN_LOCATION, user.get_location());
            values.put(USER_COLUMN_PATIENCE, user.get_patience());
            values.put(USER_COLUMN_ABO, user.get_abo());
            values.put(USER_COLUMN_SATISFACTION, user.get_satisfaction());
            values.put(USER_COLUMN_ABORTED, user.get_aborted());

            // Insert the new row, returning the primary key value of the new row
            long userId = db.insert(USER_TABLE_NAME, null, values);

            values = new ContentValues();
            for (TestEntry entry : results.getEntrys()) {
                values.put(TEST_COLUMN_USERID, userId);
                values.put(TEST_COLUMN_SITE, entry.getSite());
                values.put(TEST_COLUMN_PLT, entry.getTime());
            }
            db.insert(TEST_TABLE_NAME, null, values);
            db.close();
        }
    }





    //--------Here Follows the needed Stuff for AndroidDatabaseManager------------

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
