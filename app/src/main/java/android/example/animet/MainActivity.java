package android.example.animet;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GetInfo getInfo = new GetInfo();
    String title;
    String vidUrl;
    public List<Anime> animeList = new ArrayList<>();
    public List<Anime> fanimeList = new ArrayList<>();
    Integer value;
    String url = "https://anitube.cz/anime";
    String aUrl = "https://anitube.cz/anime/page/";
    private StringRequest stringRequest, aStringRequest;
    ImageView image;
    MainActivityAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;

    int lastPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MainActivityAdapter(fanimeList,this);
        recyclerView = findViewById(R.id.rv_view);
        LinearLayoutManager linearLayoutManager= new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        AllAnime();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {

                adapter.getFilter().filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

        return true;
    }

    void LatestAnimeEpisodes()
    {

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                fanimeList.addAll(getInfo.GetLatestAnimeEpisodes(response));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: ");
            }
        });
        SingleVolley.getInstance(this).addToRequestQueue(stringRequest);
    }


    void AllAnime() {


        aStringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                lastPage = getInfo.GetLastPageNumber(response);
                Log.d("NumPage", String.valueOf(lastPage));
                for (int i = 1; i <= 2; i++) {

                    stringRequest = new StringRequest(Request.Method.GET, aUrl + i, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            final int positionStart = fanimeList.size();



                           /* if (fanimeList.size() > 0) {
                                Collections.sort(fanimeList, new Comparator<Anime>() {
                                    @Override
                                    public int compare(final Anime object1, final Anime object2) {
                                        return object1.fAnimeName.compareTo(object2.fAnimeName);
                                    }
                                });
                            }*/

                            fanimeList.addAll(getInfo.GetAnime(response));

                            adapter.notifyItemRangeInserted(positionStart, fanimeList.size());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", "onErrorResponse: is it here? ");
                        }
                    });
                    SingleVolley.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: in here?");
            }
        });


        SingleVolley.getInstance(this).addToRequestQueue(aStringRequest);


    }









}
