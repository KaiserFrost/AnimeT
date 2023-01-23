package android.example.animet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder> {

    private List<Anime> animeList;
    Anime anime;
    private Context context;
    private StringRequest stringRequest;
    GetInfo getInfo = new GetInfo();
    //private List<String> episodeUrl;
    private List<String> urlEpisode = new ArrayList<>();

    public EpisodeListAdapter(List<Anime> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.activity_anime_detail;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        EpisodeListAdapter.ViewHolder viewHolder = new EpisodeListAdapter.ViewHolder(view);

       /* if (animeList.size() > 0) {
            Collections.sort(animeList, new Comparator<Anime>() {
                @Override
                public int compare(final Anime object1, final Anime object2) {
                    return object1.fAnimeName.compareTo(object2.fAnimeName);
                }
            });
        }*/

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(animeList.get(position).fImageUrl).centerCrop().into(holder.imageView);
        holder.textView.setText(animeList.get(position).fAnimeName);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestEpisodesUrl( animeList.get(position).fAnimeUrl);


            }
        });

    }

    void RequestEpisodesUrl(String url)
    {
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //episodeUrl.addAll(getInfo.GetVideoUrls(response));

                urlEpisode = getInfo.GetVideoUrls(response);


                Intent intent = new Intent(context, VideoPlayer.class);
                intent.putStringArrayListExtra("url", (ArrayList<String>) urlEpisode);
                context.startActivity(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: ");
            }
        });
        SingleVolley.getInstance(context).addToRequestQueue(stringRequest);
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
        return animeList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_episodeImage);
            textView = itemView.findViewById(R.id.tv_episodeName);
            layout = itemView.findViewById(R.id.ll_layout_episodelist);
        }
    }
}
