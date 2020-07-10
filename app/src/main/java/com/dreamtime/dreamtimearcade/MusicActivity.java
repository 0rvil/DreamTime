package com.dreamtime.dreamtimearcade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MusicActivity extends AppCompatActivity {
    VideoView vv;

    SimpleExoPlayer player;
    SimpleExoPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        vv = findViewById(R.id.video_view);
        playerView = findViewById(R.id.player_view);
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv.start();
            }
        });
        final Context context = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_arcade:
                        Intent intentSettings = new Intent(context, MenuActivity.class);
                        startActivity(intentSettings);
                        finish();
                        break;

                    case R.id.action_home:
                        Intent intentHome = new Intent(context, Menu2.class);
                        startActivity(intentHome);
                        finish();
                        break;

                    case R.id.action_account:
                        Intent intentAccount = new Intent(context, AccountActivity.class);
                        startActivity(intentAccount);
                        finish();
                        break;
                }
                return false;
            }
        });
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.doganimation;
        Uri uri = Uri.parse(videoPath);
        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();

        Uri[] musicFiles = new Uri[13];
        musicFiles[0] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-Hope-03.mp3?alt=media&token=539d6c80-6199-4a98-aedb-5298e6ebfc84");
        musicFiles[1] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-Working-Together.mp3?alt=media&token=d23485c6-90d3-4574-a20c-4df9237279e5");
        musicFiles[2] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/animation-music_fy-0jBrO.mp3?alt=media&token=6103276b-6f14-47a4-a247-f0fffeb71ecc");
        musicFiles[3] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-Awake.mp3?alt=media&token=4de8f969-ed8b-4446-a309-528d31644e82");
        musicFiles[4] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-Typical-Day.mp3?alt=media&token=268d3b49-d0ed-4c9b-897f-ed02b6b03a6f");
        musicFiles[5] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-Peaceful-Mind.mp3?alt=media&token=cbab09f4-9113-44e3-b109-a222389716e8");
        musicFiles[6] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/ssm-102518-space-lullaby.mp3?alt=media&token=8f52938b-007f-4bbc-a634-535a32e5e6cd");
        musicFiles[7] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/in-the-arms-of-home_G1-_iUBd.mp3?alt=media&token=f254e6c8-caab-41b5-bba5-46d94798480d");
        musicFiles[8] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/walk-in-the-park_GyD77Lr_.mp3?alt=media&token=36a2515f-3030-4f76-a2bc-b38bf86a60d2");
        musicFiles[9] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/welcome-home_fy7v7UB_.mp3?alt=media&token=9e3790b5-788d-4210-9a83-1f1c94b11669");
        musicFiles[10] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/searching-for-hope_MyqQQIru.mp3?alt=media&token=7bbe9d33-e394-4245-b8f6-e504abf7b32d");
        musicFiles[11] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/mid-summer-evening_z1FwVUB_.mp3?alt=media&token=f5fea43e-500f-4edb-a122-a2a6339311d5");
        musicFiles[12] = Uri.parse("https://firebasestorage.googleapis.com/v0/b/dreamtime-fd725.appspot.com/o/sleeping-peacefully_GJkBQ8ru.mp3?alt=media&token=fc8772e5-aebf-4bbb-a300-b97510e6acfd");
        playerPlaylist(musicFiles);
    }

    private void playerPlaylist(Uri[] musicFiles){
        if(player == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(this,
                    trackSelector, loadControl);
            playerView.setPlayer(player);

            MediaSource[] mediaSources = new MediaSource[13];
            String userAgent = Util.getUserAgent(this, "DreamTime");
            for (int i = 0; i < musicFiles.length; i++)
            {
                new DefaultDataSourceFactory(this, userAgent);
                MediaSource audioSource = new ExtractorMediaSource(musicFiles[i], new DefaultDataSourceFactory(
                        this, userAgent), Mp3Extractor.FACTORY, null, null);
                mediaSources[i] = audioSource;
            }
            ConcatenatingMediaSource concatenatedSource = new ConcatenatingMediaSource(mediaSources);

            player.prepare(concatenatedSource);
            player.setPlayWhenReady(true);
        }

    }
    private void releasePlayer(){
        player.stop();
        player.release();
        player = null;
    }
    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
        vv.pause();
        vv.suspend();
    }
}