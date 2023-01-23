package android.example.animet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> implements Filterable
        {

            private List<Anime> animeList;
            private List<Anime> filteredanimeList ;

            private Context context;
            public MainActivityAdapter(List<Anime> animeList, Context context)
            {
                this.animeList = animeList;
                this.context = context;
                this.filteredanimeList = animeList;
            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                Context context = parent.getContext();
                int layoutId = R.layout.activity_main_rv_content;
                LayoutInflater layoutInflater =LayoutInflater.from(context);
                View view = layoutInflater.inflate(layoutId,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);


                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                Glide.with(context).load(filteredanimeList.get(position).fImageUrl).centerCrop().into(holder.imageView);
                holder.textView.setText(filteredanimeList.get(position).fAnimeName);

                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AnimeDetailActivity.class);
                        intent.putExtra("url", filteredanimeList.get(position).fAnimeUrl);
                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }

            @Override
            public int getItemCount() {
                return filteredanimeList.size();
            }

            @Override
            public Filter getFilter()
            {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence charSequence) {
                        String charString = charSequence.toString();
                        if (charString.isEmpty()) {
                            filteredanimeList = animeList;
                        }
                        else {
                            List<Anime> filteredList = new ArrayList<>();
                            for (Anime row : animeList) {


                                if (row.fAnimeName.toLowerCase().contains(charString.toLowerCase()))
                                {
                                    filteredList.add(row);
                                }
                            }

                            filteredanimeList = filteredList;
                        }

                        FilterResults filterResults = new FilterResults();
                        filterResults.values = filteredanimeList;
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                        filteredanimeList = (ArrayList<Anime>) filterResults.values;

                        // refresh the list with filtered data
                        notifyDataSetChanged();
                    }
                };
            }


            class ViewHolder extends RecyclerView.ViewHolder
        {

            ImageView imageView;
            TextView textView;
            LinearLayout layout;

            public ViewHolder(@NonNull View itemView) {
             super(itemView);

            imageView = itemView.findViewById(R.id.iv_animeImage);
            textView = itemView.findViewById(R.id.tv_animeName);
            layout = itemView.findViewById(R.id.ll_layout_allanime);
        }
    }
}
