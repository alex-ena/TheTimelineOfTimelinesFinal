package enacica.alex.thetimelineoftimelines.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.adapter.CategoryRVAdapter;
import enacica.alex.thetimelineoftimelines.data.DbHelper;
import enacica.alex.thetimelineoftimelines.parser.JSONparser;
import enacica.alex.thetimelineoftimelines.util.InternetConnection;

public class InitialActivity extends AppCompatActivity {
    JSONObject jsonObject;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void getData(View view) {
        Button dataBtn = findViewById(R.id.btnGetData);
        dataBtn.setEnabled(false);
        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
            Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonObject = JSONparser.getDataFromWeb();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ProgressBar progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.INVISIBLE);
            Button dataBtn = findViewById(R.id.btnGetData);
            String jsonString = jsonObject.toString();
            dbHelper = new DbHelper(getApplicationContext(), jsonString);
            dbHelper.clearDatabase(getApplicationContext(), jsonString);
            Intent categoryActivity = new Intent(getBaseContext(), SelectTimelineActivity.class);
            categoryActivity.putExtra("EXTRA_JSON_OBJECT", jsonString);
            startActivity(categoryActivity);

            dataBtn.setEnabled(true);

        }
}
}
