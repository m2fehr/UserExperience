package ch.hsr.userexperience.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ch.hsr.userexperience.utils.TestEntry;
import ch.hsr.userexperience.utils.TestResults;
import ch.hsr.userexperience.utils.User;

/**
 * Created by Matthias on 13.11.15.
 */
public class DbHelper extends SQLiteOpenHelper {

    //DB Erstellen
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserExperience.db";
    public static final String TAG = "DbHelper";

    //Table user
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_AGE = "age_id";
    public static final String USER_COLUMN_GENDER = "gender_id";
    public static final String USER_COLUMN_LOCATION = "location";
    public static final String USER_COLUMN_PATIENCE = "patience_id";
    public static final String USER_COLUMN_ABO = "abo_id";
    public static final String USER_COLUMN_SATISFACTION = "satisfaction_id";
    public static final String USER_COLUMN_ABORTED  = "aborted_id";

    //Table Testresults
    public static final String TEST_TABLE_NAME = "tests";
    public static final String TEST_COLUMN_ID = "id";
    public static final String TEST_COLUMN_USERID = "userid";
    public static final String TEST_COLUMN_SITE = "site";
    public static final String TEST_COLUMN_PLT = "plt";
    public static final String TEST_COLUMN_NOFELEMENTS = "elements";
    public static final String TEST_COLUMN_NOFSERVERS = "servers";
    public static final String TEST_COLUMN_FINISHED = "finished";

    //Table Age
    public static final String AGE_TABLE_NAME = "age";
    public static final String AGE_COLUMN_ID = "id";
    public static final String AGE_COLUMN_GROUP = "agegroup";

    //Table Gender
    public static final String GENDER_TABLE_NAME = "gender";
    public static final String GENDER_COLUMN_ID = "id";
    public static final String GENDER_COLUMN_GENDER = "geschlecht";

    //Table Location
    public static final String LOCATION_TABLE_NAME = "location";
    public static final String LOCATION_COLUMN_ID = "id";
    public static final String LOCATION_COLUMN_LOCATION = "wohnort";

    //Table Patience
    public static final String PATIENCE_TABLE_NAME = "patience";
    public static final String PATIENCE_COLUMN_ID = "id";
    public static final String PATIENCE_COLUMN_PATIENCE = "geduld";

//    Table Abo
//    public static final String ABO_TABLE_NAME = "abo";
//    public static final String ABO_COLUMN_ID = "id";
//    public static final String ABO_COLUMN_ABONAME = "aboname";

    //Table Satisfaction
    public static final String SATISFACTION_TABLE_NAME = "satisfaction";
    public static final String SATISFACTION_COLUMN_ID ="id";
    public static final String SATISFACTION_COLUMN_SATISFACTION = "zufriedenheit";

    //Table Aborted
    public static final String ABORTED_TABLE_NAME = "aborted";
    public static final String ABORTED_COLUMN_ID = "id";
    public static final String ABORTED_COLUMN_REASON = "Reason";

    //Div. Strings
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PK = " INTEGER PRIMARY KEY";
    private static final String INTEGER_REF = " INTEGER REFERENCES ";
    private static final String CLOSE_DELETE_CASCADE = ") ON DELETE CASCADE";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate");
        final String createUserTable =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                USER_COLUMN_AGE + INTEGER_REF + AGE_TABLE_NAME + "(" + AGE_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                USER_COLUMN_GENDER + INTEGER_REF + GENDER_TABLE_NAME + "(" + GENDER_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                USER_COLUMN_LOCATION + INTEGER_REF + LOCATION_TABLE_NAME + "(" + LOCATION_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                USER_COLUMN_PATIENCE + INTEGER_REF + PATIENCE_TABLE_NAME + "(" + PATIENCE_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                USER_COLUMN_ABO + TEXT_TYPE + COMMA_SEP +
                USER_COLUMN_SATISFACTION + INTEGER_REF + SATISFACTION_TABLE_NAME + "(" + SATISFACTION_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                USER_COLUMN_ABORTED + INTEGER_REF + ABORTED_TABLE_NAME + "(" + ABORTED_COLUMN_ID + CLOSE_DELETE_CASCADE +
                " )";

        final String createTestResultsTable =
            "CREATE TABLE " + TEST_TABLE_NAME + " (" +
                TEST_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                TEST_COLUMN_USERID + INTEGER_REF + USER_TABLE_NAME + "(" + USER_COLUMN_ID + CLOSE_DELETE_CASCADE + COMMA_SEP +
                TEST_COLUMN_SITE + TEXT_TYPE + COMMA_SEP +
                TEST_COLUMN_PLT + INTEGER_TYPE + COMMA_SEP +
                TEST_COLUMN_NOFELEMENTS + INTEGER_TYPE + COMMA_SEP +
                TEST_COLUMN_NOFSERVERS + INTEGER_TYPE + COMMA_SEP +
                TEST_COLUMN_FINISHED + INTEGER_TYPE +
                " )";

        final String createAgeGroupTable =
                "CREATE TABLE " + AGE_TABLE_NAME + " (" +
                        AGE_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        AGE_COLUMN_GROUP + TEXT_TYPE +
                        " )";

        final String createGenderTable =
                "CREATE TABLE " + GENDER_TABLE_NAME + " (" +
                        GENDER_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        GENDER_COLUMN_GENDER + TEXT_TYPE +
                        " )";

        final String createLocationTable =
                "CREATE TABLE " + LOCATION_TABLE_NAME + " (" +
                        LOCATION_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        LOCATION_COLUMN_LOCATION + TEXT_TYPE +
                        " )";

        final String createPatienceTable =
                "CREATE TABLE " + PATIENCE_TABLE_NAME + " (" +
                        PATIENCE_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        PATIENCE_COLUMN_PATIENCE + TEXT_TYPE +
                        " )";

/*
        final String createAboTable =
                "CREATE TABLE " + ABO_TABLE_NAME + " (" +
                        ABO_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        ABO_COLUMN_ABONAME + TEXT_TYPE +
                        " )";
*/

        final String createSatisfactionTable =
                "CREATE TABLE " + SATISFACTION_TABLE_NAME + " (" +
                        SATISFACTION_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        SATISFACTION_COLUMN_SATISFACTION + TEXT_TYPE +
                        " )";

        final String createAbortedTable =
                "CREATE TABLE " + ABORTED_TABLE_NAME + " (" +
                        ABORTED_COLUMN_ID + INTEGER_PK + COMMA_SEP +
                        ABORTED_COLUMN_REASON + TEXT_TYPE +
                        " )";

        //Helper tables
        db.execSQL(createAgeGroupTable);
        db.execSQL(createGenderTable);
        db.execSQL(createLocationTable);
        db.execSQL(createPatienceTable);
       // db.execSQL(createAboTable);
        db.execSQL(createSatisfactionTable);
        db.execSQL(createAbortedTable);

        //Main tables
        db.execSQL(createUserTable);
        db.execSQL(createTestResultsTable);

        insertHelperTable(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade");
        //For now, just drop old tables and make new ones
        final String deleteUserTable =
                "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        final String deleteTestResultsTable =
                "DROP TABLE IF EXISTS " + TEST_TABLE_NAME;
        final String deleteAgeGroupTable =
                "DROP TABLE IF EXISTS " + AGE_TABLE_NAME;
        final String deleteGenderTable =
                "DROP TABLE IF EXISTS " + GENDER_TABLE_NAME;
        final String deleteLocationTable =
                "DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME;
        final String deletePatinceTable =
                "DROP TABLE IF EXISTS " + PATIENCE_TABLE_NAME;
//        final String deleteAboTable =
//                "DROP TABLE IF EXISTS " + ABO_TABLE_NAME;
        final String deleteSatisfactionTable=
                "DROP TABLE IF EXISTS " + SATISFACTION_TABLE_NAME;
        final String deleteAbortedTable =
                "DROP TABLE IF EXISTS " + ABORTED_TABLE_NAME;

        db.execSQL(deleteTestResultsTable);
        db.execSQL(deleteUserTable);
        db.execSQL(deleteAgeGroupTable);
        db.execSQL(deleteGenderTable);
        db.execSQL(deleteLocationTable);
        db.execSQL(deletePatinceTable);
//        db.execSQL(deleteAboTable);
        db.execSQL(deleteSatisfactionTable);
        db.execSQL(deleteAbortedTable);
        onCreate(db);
    }

    public void insertHelperTable(SQLiteDatabase db){
        Log.e(TAG, "insertHelperTable");
        insertAgeTable(db);
        insertGenderTable(db);
        insertLocationTable(db);
        insertPatienceTable(db);
        insertSatisfactionTable(db);
        insertAbortedTable(db);
        //insertAboTable(db);
        db.close();
    }

    public void renewDb() {
        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
    }

    public void insertAgeTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(AGE_COLUMN_ID, 0);
        values.put(AGE_COLUMN_GROUP, "0 - 15");
        db.insert(AGE_TABLE_NAME, null, values);

        values.put(AGE_COLUMN_ID, 1);
        values.put(AGE_COLUMN_GROUP, "16 - 30");
        db.insert(AGE_TABLE_NAME, null, values);

        values.put(AGE_COLUMN_ID, 2);
        values.put(AGE_COLUMN_GROUP, "31 - 45");
        db.insert(AGE_TABLE_NAME, null, values);

        values.put(AGE_COLUMN_ID, 3);
        values.put(AGE_COLUMN_GROUP, "46 -");
        db.insert(AGE_TABLE_NAME, null, values);
//        db.close();
    }
    public void insertGenderTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(GENDER_COLUMN_ID, 0);
        values.put(GENDER_COLUMN_GENDER, "mänlich");
        db.insert(GENDER_TABLE_NAME, null, values);

        values.put(GENDER_COLUMN_ID, 1);
        values.put(GENDER_COLUMN_GENDER, "weiblich");
        db.insert(GENDER_TABLE_NAME, null, values);
//        db.close();
    }
    public void insertLocationTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(LOCATION_COLUMN_ID, 0);
        values.put(LOCATION_COLUMN_LOCATION, "Stadt");
        db.insert(LOCATION_TABLE_NAME, null, values);

        values.put(LOCATION_COLUMN_ID, 1);
        values.put(LOCATION_COLUMN_LOCATION, "Agglomeration");
        db.insert(LOCATION_TABLE_NAME, null, values);

        values.put(LOCATION_COLUMN_ID, 2);
        values.put(LOCATION_COLUMN_LOCATION, "ländlich");
        db.insert(LOCATION_TABLE_NAME, null, values);
//        db.close();
    }
    public void insertPatienceTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(PATIENCE_COLUMN_ID, 0);
        values.put(PATIENCE_COLUMN_PATIENCE, "geduldig");
        db.insert(PATIENCE_TABLE_NAME, null, values);

        values.put(PATIENCE_COLUMN_ID, 1);
        values.put(PATIENCE_COLUMN_PATIENCE, "mittel");
        db.insert(PATIENCE_TABLE_NAME, null, values);

        values.put(PATIENCE_COLUMN_ID, 2);
        values.put(PATIENCE_COLUMN_PATIENCE, "ungeduldig");
        db.insert(PATIENCE_TABLE_NAME, null, values);
//        db.close();
    }
    public void insertSatisfactionTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(SATISFACTION_COLUMN_ID, 0);
        values.put(SATISFACTION_COLUMN_SATISFACTION, "schnell");
        db.insert(SATISFACTION_TABLE_NAME, null, values);

        values.put(SATISFACTION_COLUMN_ID, 1);
        values.put(SATISFACTION_COLUMN_SATISFACTION, "mittel");
        db.insert(SATISFACTION_TABLE_NAME, null, values);

        values.put(SATISFACTION_COLUMN_ID, 2);
        values.put(SATISFACTION_COLUMN_SATISFACTION, "langsam");
        db.insert(SATISFACTION_TABLE_NAME, null, values);

        values.put(SATISFACTION_COLUMN_ID, 3);
        values.put(SATISFACTION_COLUMN_SATISFACTION, "unbrauchbar");
        db.insert(SATISFACTION_TABLE_NAME, null, values);
//        db.close();
    }
    public void insertAbortedTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(ABORTED_COLUMN_ID, 0);
        values.put(ABORTED_COLUMN_REASON, "keine Angabe");
        db.insert(ABORTED_TABLE_NAME, null, values);

        values.put(ABORTED_COLUMN_ID, 1);
        values.put(ABORTED_COLUMN_REASON, "keine Lust mehr");
        db.insert(ABORTED_TABLE_NAME, null, values);

        values.put(ABORTED_COLUMN_ID, 2);
        values.put(ABORTED_COLUMN_REASON, "unbrauchbar");
        db.insert(ABORTED_TABLE_NAME, null, values);

        values.put(ABORTED_COLUMN_ID, 3);
        values.put(ABORTED_COLUMN_REASON, "um weitere Option zu testen");
        db.insert(ABORTED_TABLE_NAME, null, values);
//        db.close();
    }
//    public void insertAboTable(SQLiteDatabase db){
//        ContentValues values = new ContentValues();
//        values.put(ABO_COLUMN_ID, 0);
//        values.put(ABO_COLUMN_ABONAME, "abo1");
//        db.insert(AGE_TABLE_NAME, null, values);
//        db.close();
//    }


    public void insertUserAndTests(User user, TestResults results) {
        Log.e(TAG, "insertUserAndTests");
        if(user != null && results != null) {
            Log.e(TAG, user.toString());
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_AGE, user.get_age());
            values.put(USER_COLUMN_GENDER, user.get_gender());
            values.put(USER_COLUMN_LOCATION, user.get_location());
            values.put(USER_COLUMN_PATIENCE, user.get_patience());
            values.put(USER_COLUMN_ABO, "Abo1"); //TODO: enter correct value
            values.put(USER_COLUMN_SATISFACTION, user.get_satisfaction());
            values.put(USER_COLUMN_ABORTED, user.get_aborted());

            // Insert the new row, returning the primary key value of the new row
            long userId = db.insert(USER_TABLE_NAME, null, values);
            Log.e(TAG, "userID: " + userId);
            values = new ContentValues();
            for (TestEntry entry : results.getEntrys()) {
                values.put(TEST_COLUMN_USERID, userId);
                values.put(TEST_COLUMN_SITE, entry.getSite());
                values.put(TEST_COLUMN_PLT, entry.getTime());
                values.put(TEST_COLUMN_NOFELEMENTS, entry.getNofElements());
                values.put(TEST_COLUMN_NOFSERVERS, entry.getNofServers());
                values.put(TEST_COLUMN_FINISHED, 1); //TODO: enter correct value
                db.insert(TEST_TABLE_NAME, null, values);
            }
//            db.insert(TEST_TABLE_NAME, null, values);
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
