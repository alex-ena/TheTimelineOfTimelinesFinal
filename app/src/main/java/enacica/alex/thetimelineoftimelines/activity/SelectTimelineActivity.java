package enacica.alex.thetimelineoftimelines.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.adapter.CategoryRVAdapter;
import enacica.alex.thetimelineoftimelines.data.DbHelper;

public class SelectTimelineActivity extends AppCompatActivity {

    private String jsonDb;
    private DbHelper dbHelper;
    private List<String> categoriesDataSet;
    private HashMap<String, Boolean> categoriesMap;
    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timeline);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonDb = getIntent().getStringExtra("EXTRA_JSON_OBJECT");
        }
        dbHelper = new DbHelper(this, jsonDb);
        categoriesMap = new HashMap<>();
        for (int i=0;i<dbHelper.getEventsNumber();i++){
            String tml  = dbHelper.getEvent(i+1).getSource();
            if (!categoriesMap.containsKey(tml))
                if (!tml.equals(""))
                categoriesMap.put(dbHelper.getEvent(i+1).getSource(), true);
        }
        Set<String> keys = categoriesMap.keySet();
        categoriesDataSet = new ArrayList<>();
        categoriesDataSet.addAll(keys);
        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(new CategoryRVAdapter(this, categoriesDataSet, jsonDb));



    }
}
