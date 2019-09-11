package enacica.alex.thetimelineoftimelines.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.model.HistoryEvent;

import static android.provider.BaseColumns._ID;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_ADDITIONAL_RESOURCES;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_DESCRIPTION;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_END;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_IMAGE;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_LOCATION;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_PLACE;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_SOURCE;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_SOURCE_URL;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_START;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_TITLE;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_VIDEO;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.COLUMN_YEAR;
import static enacica.alex.thetimelineoftimelines.data.DbContract.EventEntry.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private Resources mResources;
    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 15;
    Context newcontext;
    SQLiteDatabase db;
    String jsonDatabase;

    public DbHelper(Context context, String jsonData) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        this.newcontext = context;
        this.jsonDatabase = jsonData;
        db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                DbContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT UNIQUE NOT NULL, " +
                COLUMN_START + " TEXT, " +
                COLUMN_END + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_VIDEO + " TEXT, " +
                COLUMN_SOURCE + " TEXT, " +
                COLUMN_SOURCE_URL + " TEXT, " +
                COLUMN_ADDITIONAL_RESOURCES + " TEXT, " +
                COLUMN_YEAR + " INTEGER " + ");";



        db.execSQL(SQL_CREATE_BUGS_TABLE);
        Log.d(TAG, "Database Created Successfully" );


        try {
            readDataToDb(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//         jsonParse();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {

        final String EVT_TITLE = "title";
        final String EVT_START = "start";
        final String EVT_END = "end";
        final String EVT_DESCRIPTION = "description";
        final String EVT_IMAGE = "image";
        final String EVT_PLACE = "place";
        final String EVT_LOCATION = "location";
        final String EVT_VIDEO = "video";
        final String EVT_SOURCE = "source";
        final String EVT_SOURCE_URL = "source_url";
        final String EVT_ADDITIONAL_RESOURCES= "additional_resources";
        final String EVT_YEAR = "year";


        try {
//for local db file            String jsonDataString = readJsonDataFromFile();

            JSONObject jsonDb = new JSONObject(jsonDatabase);
            JSONArray eventItemsJsonArray = jsonDb.getJSONArray("Sheet1");


//for local db file             JSONArray eventItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < eventItemsJsonArray.length(); ++i) {

                String title;
                String start;
                String end;
                String description;
                String image;
                String place;
                String location;
                String video;
                String source;
                String source_url;
                String additional_resources;
                int year;



                JSONObject eventItemObject = eventItemsJsonArray.getJSONObject(i);


                title = eventItemObject.getString(EVT_TITLE);
                start = eventItemObject.getString(EVT_START);
                end = eventItemObject.getString(EVT_END);
                description = eventItemObject.getString(EVT_DESCRIPTION);
                image = eventItemObject.getString(EVT_IMAGE);
                place = eventItemObject.getString(EVT_PLACE);
                location = eventItemObject.getString(EVT_LOCATION);
                video = eventItemObject.getString(EVT_VIDEO);
                source = eventItemObject.getString(EVT_SOURCE);
                source_url = eventItemObject.getString(EVT_SOURCE_URL);
                additional_resources = eventItemObject.getString(EVT_ADDITIONAL_RESOURCES);
                year = eventItemObject.getInt(EVT_YEAR);


                ContentValues eventValues = new ContentValues();

                eventValues.put(COLUMN_TITLE, title);
                eventValues.put(COLUMN_START, start);
                eventValues.put(COLUMN_END, end);
                eventValues.put(COLUMN_DESCRIPTION, description);
                eventValues.put(COLUMN_IMAGE, image);
                eventValues.put(COLUMN_PLACE, place);
                eventValues.put(COLUMN_LOCATION, location);
                eventValues.put(COLUMN_VIDEO, video);
                eventValues.put(COLUMN_SOURCE, source);
                eventValues.put(COLUMN_SOURCE_URL, source_url);
                eventValues.put(COLUMN_ADDITIONAL_RESOURCES, additional_resources);
                eventValues.put(COLUMN_YEAR, year);

                db.insert(TABLE_NAME, null, eventValues);


                Log.d(TAG, "Inserted Successfully " + eventValues );
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.event_items);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    public HistoryEvent getEvent(int event_id) {

        HistoryEvent historyEvent = new HistoryEvent();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {_ID, COLUMN_TITLE, COLUMN_START, COLUMN_END, COLUMN_DESCRIPTION, COLUMN_IMAGE, COLUMN_PLACE, COLUMN_LOCATION, COLUMN_VIDEO, COLUMN_SOURCE, COLUMN_SOURCE_URL, COLUMN_ADDITIONAL_RESOURCES, COLUMN_YEAR};
        //Select condition
        String selection = _ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(event_id)};


        Cursor cursor = db.query(TABLE_NAME, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            historyEvent.set_id(cursor.getInt(0));
            historyEvent.setTitle(cursor.getString(1));
            historyEvent.setStart(cursor.getString(2));
            historyEvent.setEnd(cursor.getString(3));
            historyEvent.setDescription(cursor.getString(4));
            historyEvent.setImageUrl(cursor.getString(5));
            historyEvent.setPlace(cursor.getString(6));
            historyEvent.setLocation(cursor.getString(7));
            historyEvent.setVideoUrl(cursor.getString(8));
            historyEvent.setSource(cursor.getString(9));
            historyEvent.setSource_url(cursor.getString(10));
            historyEvent.setAdditional_resources(cursor.getString(11));
            historyEvent.setYear(cursor.getInt(12));
        }
        db.close();
        return historyEvent;

    }

    public long getEventsNumber() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

    public void clearDatabase(Context context, String jsonData) {
        DbHelper helper = new DbHelper(context, jsonData);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(database);
    }


}
