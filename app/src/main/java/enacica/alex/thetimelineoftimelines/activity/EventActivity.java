package enacica.alex.thetimelineoftimelines.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.activity.BrowserActivity;
import enacica.alex.thetimelineoftimelines.model.HistoryEvent;

import static enacica.alex.thetimelineoftimelines.util.Constants.MAPVIEW_BUNDLE_KEY;
import enacica.alex.thetimelineoftimelines.data.DbHelper;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DbHelper dbHelper;
    private GoogleMap initialMap;
    private MapView mMapView;

    HistoryEvent currentEvent = new HistoryEvent();
    Bundle mapViewBundle = null;
    int index;
    int loadedEventId;
    String loadedEventIdString;
    String someString = "input";
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this, someString);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loadedEventIdString = getIntent().getStringExtra("EXTRA_EVENT_ID");
        }
        if (loadedEventIdString != null)
        loadedEventId = Integer.parseInt(loadedEventIdString);

        if (loadedEventId != 0)
            index = loadedEventId;
        else
            index = 1;

        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);

        mMapView =  findViewById(R.id.mapViewLayout);
        mMapView.onCreate(mapViewBundle);
        setView(index);
            mMapView.getMapAsync( this);

        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

    }

    public void setMap() {
        mMapView =  findViewById(R.id.mapViewLayout);
        mMapView.onCreate(mapViewBundle);

        initialMap.addMarker(new MarkerOptions().position(new LatLng(33.6874388, -80.4363743)).title("Marker"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(33.6874388, -80.4363743)).zoom(5).build();

        initialMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        initialMap = map;
        Double lat;
        Double lng;

        String location = dbHelper.getEvent(index).getLocation();
        if (!location.equals("#N/A") && !location.equals("")&& !location.equals("#ERROR!")) {
            String[] parts = location.split(",", 2);
            lat = Double.parseDouble(parts[0]);
            lng = Double.parseDouble(parts[1]);
        }
        else
        {
            lat = 0.0;
            lng = 0.0;
        }
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng)).zoom(5).build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        if ( textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void onClickNext(View view) {
        if ( index < dbHelper.getEventsNumber()) {
            index = index + 1;
            setView(index);
            if ( textToSpeech != null){
                textToSpeech.stop();
            }
        }


    }

    public void onClickPrev(View view) {
        if (index > 1) {
            index = index - 1;
            setView(index);
            if ( textToSpeech != null){
                textToSpeech.stop();
            }
        }

    }

    public void onClickActivity(View view) {
        onBackPressed();
    }

    private void setView( int index){
        TextView textEventTitle = findViewById(R.id.eventTitle);
        TextView textDescription = findViewById(R.id.eventDescription);
        ImageView imageEvent = findViewById(R.id.eventImage);
        Button btnPrevEvt = findViewById(R.id.prevNearest);
        Button btnCrtEvt = findViewById(R.id.currentNearest);
        Button btnNextEvt = findViewById(R.id.nextNearest);
        Button btnGoToArticle = findViewById(R.id.btnOpenBrowser);

        currentEvent =  dbHelper.getEvent(index);
        if (currentEvent.getLocation().equals("") || currentEvent.getLocation().equals("#N/A") || currentEvent.getLocation().equals("#ERROR!") )
            mMapView.setVisibility(View.GONE);
        else
            mMapView.setVisibility(View.VISIBLE);
        textEventTitle.setText(currentEvent.getTitle());
        textDescription.setText(currentEvent.getDescription());
        btnCrtEvt.setText(currentEvent.getTitle());

        if (index > 1)
            btnPrevEvt.setText(dbHelper.getEvent(index-1).getTitle());
        else
            btnPrevEvt.setText("");

        if (index < dbHelper.getEventsNumber())
            btnNextEvt.setText(dbHelper.getEvent(index+1).getTitle());
        else
            btnNextEvt.setText("");


        if (currentEvent.getImageUrl() != null ) {
            imageEvent.setVisibility(View.VISIBLE);
            new DownLoadImageTask(imageEvent).execute(currentEvent.getImageUrl());
        }
        else
            imageEvent.setVisibility(View.GONE);
        mMapView.getMapAsync( this);

        if (currentEvent.getSource_url() == null || currentEvent.getSource_url().equals(""))
            btnGoToArticle.setVisibility(View.GONE);
        else
            btnGoToArticle.setVisibility(View.VISIBLE);


    }

    public void openBrowser(View view) {
        Intent browserActivity = new Intent(getBaseContext(), BrowserActivity.class);
        browserActivity.putExtra("EXTRA_EVENT_URL", currentEvent.getSource_url());
        startActivity(browserActivity);
    }

    public void clickTextToSpeech(View view) {
        textToSpeech.speak((currentEvent.getTitle() + ". " + currentEvent.getDescription()), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }


        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }


        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(result);
        }
            else
                imageView.setVisibility(View.GONE);
        }
    }
}
