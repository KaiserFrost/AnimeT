package android.example.animet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class AnimeDetailActivity extends AppCompatActivity {
    GetInfo getInfo = new GetInfo();

    public List<Anime> fanimeList = new ArrayList<>();
    String url = "";
    String aUrl = "";
    private StringRequest stringRequest, aStringRequest;
    EpisodeListAdapter adapter;
    RecyclerView recyclerView;
    int lastPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_activity_anime_detail);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        aUrl = intent.getStringExtra("url") + "/page/";



        Log.d("animeUrl", aUrl);
        AllEpisodes();

        recyclerView = findViewById(R.id.rv_detailview);
        LinearLayoutManager linearLayoutManager= new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



        adapter = new EpisodeListAdapter(fanimeList,this);
        recyclerView.setAdapter(adapter);


    }

    void AllEpisodes() {


        aStringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                lastPage = getInfo.GetLastPageNumber(response);
                Log.d("animeUrl2", String.valueOf(lastPage));


                for (int i = 1; i <= lastPage; i++) {

                    stringRequest = new StringRequest(Request.Method.GET, aUrl + i, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            final int positionStart = fanimeList.size();

                            fanimeList.addAll(getInfo.GetLatestAnimeEpisodes(response));
                            for(Anime ani : fanimeList)
                            {
                                Log.d("AnimeName", ani.fAnimeName);
                                Log.d("AnimeName", ani.fAnimeUrl);

                            }

                            adapter.notifyItemRangeInserted(positionStart, fanimeList.size());


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", "onErrorResponse: ");
                        }
                    });
                    SingleVolley.getInstance(AnimeDetailActivity.this).addToRequestQueue(stringRequest);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: ");
            }
        });
        SingleVolley.getInstance(this).addToRequestQueue(aStringRequest);


    }
}
