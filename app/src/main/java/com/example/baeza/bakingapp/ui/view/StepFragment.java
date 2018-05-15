package com.example.baeza.bakingapp.ui.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.utility.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.exo_player)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.tv_short_description)
    TextView tvShortDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_exo_player_no_info)
    TextView tvExoPlayerNoInfo;

    private MediaSessionCompat mSessionCompat;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayer mExoPlayer;

    public StepFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layout = R.layout.fragment_step;
        View rootView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() == null) return null;
        Step step = getArguments().getParcelable(Constants.STEP_CONTENT);
        assert step != null;
        fillLayout(step);

        if(step != null) {
            if (step.getVideoURL() != null && !step.getVideoURL().isEmpty() && !step.getVideoURL().equals("")) {

                mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),
                        R.drawable.rectangle));

                initializeMediaSession(getContext());
                initializePlayer(step.getVideoURL());

            } else if (step.getVideoURL() == null || step.getVideoURL().isEmpty() || step.getVideoURL().equals("")) {
                mSimpleExoPlayerView.setVisibility(View.INVISIBLE);
                tvExoPlayerNoInfo.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }

    private void initializeMediaSession(Context context) {
        //Create a MediaSessionCompat
        mSessionCompat = new MediaSessionCompat(context, Constants.MEDIA_SESSION_TAG);
        //Enable callbacks from MediaButtons and TransportControls.
        mSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //Do no let MediaBottons restart the player when the app is not visible
        mSessionCompat.setMediaButtonReceiver(null);

        //Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mSessionCompat.setPlaybackState(mStateBuilder.build());

        //MySessionCallback has methods that handle callbacks from a media controller.
        mSessionCompat.setCallback(new MySessionCallback());

        //Start the Media Session since the activity is active
        mSessionCompat.setActive(true);
    }

    private void initializePlayer(String uriString) {
        if (mExoPlayer == null) {
            //create an instance of the exoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            //Set the ExoPlayer.EventListener to this activity
            mExoPlayer.addListener(this);

            //Prepare the mediaSource
            mExoPlayer.prepare(buildMediaSource(uriString));
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;}
    }

    private MediaSource buildMediaSource(String uriString) {

        BandwidthMeter bandWidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                "BakingApp",
                (TransferListener<? super DataSource>) bandWidthMeter);
        DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(Uri.parse(uriString),
                dataSourceFactory,
                defaultExtractorsFactory,
                null,
                null);
    }

    private void fillLayout(Step step) {
        if(step!=null){
        tvShortDescription.setText(step.getShortDescription());
        tvDescription.setText(step.getDescription());}
        else{
//            tvShortDescription.setText("hola");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDescription.setTextAppearance(android.R.style.TextAppearance_Large);
            }
            tvDescription.setGravity(Gravity.CENTER);
            tvDescription.setText(getContext().getResources().getString(R.string.no_item_selected));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if(mSessionCompat!=null)
        mSessionCompat.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }
        mSessionCompat.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity() { }

    public class MySessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
