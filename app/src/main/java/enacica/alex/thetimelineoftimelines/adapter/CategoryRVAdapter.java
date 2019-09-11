package enacica.alex.thetimelineoftimelines.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import enacica.alex.thetimelineoftimelines.R;
import enacica.alex.thetimelineoftimelines.activity.TimelineActivity;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder> {
    Context context;
    List<String> list;
    String jsonString;

    public CategoryRVAdapter (Context context, List<String> list, String jsonString) {
        this.context = context;
        this.list = list;
        this.jsonString = jsonString;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.btnCategory.setText(list.get(i));

        categoryViewHolder.btnCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent timeLine = new Intent(v.getContext(), TimelineActivity.class);
                String category = list.get(categoryViewHolder.getAdapterPosition());
                timeLine.putExtra("EXTRA_CATEGORY", category);
                timeLine.putExtra("EXTRA_JSON_OBJECT", jsonString);
                v.getContext().startActivity(timeLine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button btnCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            btnCategory = itemView.findViewById(R.id.btnRecyclerCategory);

        }
    }
}


