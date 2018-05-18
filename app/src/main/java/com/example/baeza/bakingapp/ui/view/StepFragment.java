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
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.baeza.bakingapp.ui.utility.Constants.STEP_CONTENT;

public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.exo_player)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.tv_short_description)
    TextView tvShortDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_exo_player_no_info)
    TextView tvExoPlayerNoInfo;

    private static final String USER_AGENT = "BakingApp";
    private static final String PLAYER_POSITION = "PLAYER_POSITION";
    private static final String PLAYING_STATE = "PLAYING_STATE";

    private MediaSessionCompat mSessionCompat;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayer mExoPlayer;
    private Step mStep;

    private long playerPosition = 0L;
    private boolean playingState;

    public StepFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layout = R.layout.fragment_step;
        View rootView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_CONTENT);
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playingState = savedInstanceState.getBoolean(PLAYING_STATE);
        } else {
            if (getArguments() == null) return null;
            mStep = getArguments().getParcelable(STEP_CONTENT);
            assert mStep != null;
            fillLayout(mStep);

            if (mStep != null) {
                if (mStep.getVideoURL() != null && !mStep.getVideoURL().isEmpty() && !mStep.getVideoURL().equals("")) {

                    mSimpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),
                            R.drawable.rectangle));

                    initializeMediaSession(getContext());
                    initializePlayer(mStep.getVideoURL());

                } else if (mStep.getVideoURL() == null || mStep.getVideoURL().isEmpty() || mStep.getVideoURL().equals("")) {
                    if (!mStep.getThumbnailURL().isEmpty() && !mStep.getThumbnailURL().equals("")) {
                        initializeMediaSession(getContext());
                        initializePlayer(mStep.getThumbnailURL());
                    } else {
                        tvExoPlayerNoInfo.setVisibility(View.VISIBLE);
                    }
                }
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

            Timber.d("Valor de position" +playerPosition);
            Timber.d("Valor de state "+playingState);


            if (playerPosition != 0) {
                mExoPlayer.seekTo(playerPosition);
                mExoPlayer.setPlayWhenReady(playingState);
            } else {
                mExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private MediaSource buildMediaSource(String uriString) {

        BandwidthMeter bandWidthMeter = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                USER_AGENT,
                (TransferListener<? super DataSource>) bandWidthMeter);
        DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(Uri.parse(uriString),
                dataSourceFactory,
                defaultExtractorsFactory,
                null,
                null);
    }

    private void fillLayout(Step step) {
        if (step != null) {
            tvShortDescription.setText(step.getShortDescription());
            tvDescription.setText(step.getDescription());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvDescription.setTextAppearance(android.R.style.TextAppearance_Large);
            }
            tvDescription.setGravity(Gravity.CENTER);
            tvDescription.setText(getContext().getResources().getString(R.string.no_item_selected));
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

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
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STEP_CONTENT, mStep);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(PLAYING_STATE, playingState);
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (Util.SDK_INT > 23) {
//            if (mStep != null) {
//                initializePlayer(mStep.getVideoURL());
//            }
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
            if (mStep != null) {
                initializePlayer(mStep.getVideoURL());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
        if (mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            playingState = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
