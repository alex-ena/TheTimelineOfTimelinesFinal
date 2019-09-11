package enacica.alex.thetimelineoftimelines.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import enacica.alex.thetimelineoftimelines.adapter.InsideRecyclerViewAdapter;
import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.adapter.MainAdapter;
import enacica.alex.thetimelineoftimelines.data.DbHelper;
import enacica.alex.thetimelineoftimelines.model.HistoryEvent;
import enacica.alex.thetimelineoftimelines.model.InsideRecycler;
import enacica.alex.thetimelineoftimelines.model.MainRecycler;

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private InsideRecyclerViewAdapter insideRecyclerViewAdapter;
    private ArrayList<MainRecycler> mainRecyclerDataset;
    private DbHelper dbHelper;
    private String jsonDb;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonDb = getIntent().getStringExtra("EXTRA_JSON_OBJECT");
            category = getIntent().getStringExtra("EXTRA_CATEGORY");
        }

        dbHelper = new DbHelper(this, jsonDb);
        setContentView(R.layout.activity_timeline);
        String tempYear;


        mainRecyclerDataset = new ArrayList<>();
        int i = 1600;

        while (i < 2020 ) {
            tempYear = "Years " + i + " - " + (i+49);

            ArrayList<InsideRecycler> insideRecyclerArrayList = new ArrayList<>();

            for (int j=0;j<dbHelper.getEventsNumber();j++) {
                HistoryEvent crtEvent;
                crtEvent = dbHelper.getEvent(j+1);
                    if (crtEvent.getSource().equals(category) && i <= crtEvent.getYear() && crtEvent.getYear() < i + 50) {
                        InsideRecycler insideRecycler = new InsideRecycler();
                        insideRecycler.setSingleEvent(crtEvent);
                        insideRecycler.setInsideTitle(crtEvent.getTitle());

                        insideRecyclerArrayList.add(insideRecycler);
                    }

            }
            MainRecycler mainRecycler = new MainRecycler();
            mainRecycler.setTitle(tempYear);
            mainRecycler.setEventsArrayList(insideRecyclerArrayList);
            mainRecyclerDataset.add(mainRecycler);

            i = i + 50;

        }

        mainRecyclerView = findViewById(R.id.recycler_view);
        mainRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(mainRecyclerDataset);
        mainRecyclerView.setAdapter(mAdapter);


    }
}
