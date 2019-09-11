package enacica.alex.thetimelineoftimelines.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.model.InsideRecycler;
import enacica.alex.thetimelineoftimelines.model.MainRecycler;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public ArrayList<MainRecycler> mDataset;
    Context context;


    public MainAdapter(ArrayList<MainRecycler> mDataset) {

        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int i) {
            MainRecycler mainRecycler = mDataset.get(i);
            String title = mainRecycler.getTitle();
            ArrayList<InsideRecycler> singleEvent = mainRecycler.getEventsArrayList();

            holder.mTitle.setText(title);
            InsideRecyclerViewAdapter insideRecyclerAdapter = new InsideRecyclerViewAdapter(context, singleEvent);

            holder.verticalRecyclerView.setHasFixedSize(true);
            holder.verticalRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.verticalRecyclerView.setAdapter(insideRecyclerAdapter);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle;
        public RecyclerView verticalRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            verticalRecyclerView = itemView.findViewById(R.id.recyclerViewVertical);
        }
    }
}
