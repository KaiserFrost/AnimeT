package android.example.animet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayer extends AppCompatActivity {

    private PlayerView playerView;
    private PlayerManager player;
    List<String> url = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        Intent intent = getIntent();
        url = intent.getStringArrayListExtra("url");

        playerView = findViewById(R.id.player_view);
        player = new PlayerManager(this,url.get(0));


    }

    @Override
    public void onResume() {
        super.onResume();
        player.init(this, playerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.reset();
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }
}
