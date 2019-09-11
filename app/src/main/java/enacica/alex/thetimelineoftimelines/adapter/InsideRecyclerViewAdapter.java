package enacica.alex.thetimelineoftimelines.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.activity.EventActivity;
import enacica.alex.thetimelineoftimelines.model.InsideRecycler;

public class InsideRecyclerViewAdapter extends RecyclerView.Adapter<InsideRecyclerViewAdapter.InsideRVViewHolder> {

    Context context;
    ArrayList<InsideRecycler> arrayList;

    public InsideRecyclerViewAdapter (Context context, ArrayList<InsideRecycler> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public InsideRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column, parent, false);
        return new InsideRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsideRVViewHolder insideRVViewHolder, final int i) {
            InsideRecycler insideRecycler = arrayList.get(i);
        String cellString =  "<b>" + insideRecycler.getSingleEvent().getYear() + "</b> " + insideRecycler.getInsideTitle();
        insideRVViewHolder.txtViewEventTitle.setText(Html.fromHtml(cellString));


        final int eventIndex = insideRecycler.getSingleEvent().get_id();


        insideRVViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent mainActivity = new Intent(v.getContext(), EventActivity.class);
                    String eventIndexString = Integer.toString(eventIndex);
                    mainActivity.putExtra("EXTRA_EVENT_ID", eventIndexString);
                    v.getContext().startActivity(mainActivity);
                }
            });

        }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InsideRVViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewEventTitle;


        public InsideRVViewHolder (View itemView) {
            super(itemView);
            txtViewEventTitle = itemView.findViewById(R.id.columnText);
        }

    }
}
