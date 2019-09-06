package com.kotlin.module.exo

import android.app.ActionBar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.exo_fragment_layout.*
import kotlinx.android.synthetic.main.exo_fragment_layout.view.*
import java.util.*

private const val VIDEO_URL =
    "http://54.222.148.146:20010/static/anchor_1014537@bj2.1-1.io_1563176576wa.mp4"
private const val VIDEO_URL_2 =
    "http://54.222.148.146:20010/static/anchor_1019281@bj2.1-1.io_1562292933wa.mp4"
private const val VIDEO_URL_3 =
    "http://54.222.148.146:20010/static/anchor_1014108@bj2.1-1.io_1559792466wa.mp4"
private const val VIDEO_URL_4 =
    "http://54.222.148.146:20010/static/anchor_1012500@bj2.1-1.io_1559801911wa.mp4"

class ExoPlayerFragment : Fragment() {
    private var mAgent = ("ExoMediaPlayerDemo/1.0.0" + " (Linux;Android " + Build.VERSION.RELEASE
            + ") " + ExoPlayerLibraryInfo.VERSION_SLASHY)
    private var mVideoList = mutableListOf<String>()
    private lateinit var mAnalyticsListener: SimpleAnalyticsListener
    private lateinit var mSourceFactory: DataSource.Factory
    private lateinit var mSimpleExoPlayer: SimpleExoPlayer
    private lateinit var mDemoPlayer: SimpleExoPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.exo_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSourceFactory = DefaultDataSourceFactory(context, mAgent)
        mAnalyticsListener = SimpleAnalyticsListener(this)
        initPlayerList()
        start.setOnClickListener { startPlayer() }
        stop.setOnClickListener { nextVideo() }
    }

    private fun startPlayer() {
        var random = Random()
        mSimpleExoPlayer.stop(true)
        mSimpleExoPlayer.prepare(createMediaSource(Uri.parse(mVideoList[random.nextInt(4)])))

        if (video_parent.getChildAt(0) != null) {
            var view = video_parent.getChildAt(0) as PlayerView
            view.player = null
            video_parent.removeAllViews()
        }

        mDemoPlayer.stop(true)
        mDemoPlayer.prepare(createMediaSource(Uri.parse(mVideoList[random.nextInt(4)])))

        var playerView = PlayerView(context)
        var layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        playerView.player = mDemoPlayer
        video_parent.addView(playerView, layoutParams)
    }

    fun nextVideo() {
        startPlayer()
    }

    // 扩展函数
    private fun SimpleExoPlayer.destroy() {
//        release()
        removeAnalyticsListener(mAnalyticsListener)
    }

    private fun initPlayerList() {
        mSimpleExoPlayer = createPlayer()
        mDemoPlayer = createPlayer()
        player_view.player = mSimpleExoPlayer

        mVideoList.add(VIDEO_URL)
        mVideoList.add(VIDEO_URL_2)
        mVideoList.add(VIDEO_URL_3)
        mVideoList.add(VIDEO_URL_4)
    }

    private fun createPlayer(): SimpleExoPlayer {
        val player = ExoPlayerFactory.newSimpleInstance(context)
        player.addAnalyticsListener(mAnalyticsListener)
        player.playWhenReady = true
        return player
    }


    private fun createMediaSource(uri: Uri): MediaSource {
        return when (Util.inferContentType(uri)) {
            C.TYPE_DASH -> DashMediaSource.Factory(mSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(mSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(mSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource.Factory(mSourceFactory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $this")
        }
    }

}

private const val LOG_TAG = "ExoPlayer"

class SimpleAnalyticsListener(fragment: ExoPlayerFragment) : AnalyticsListener {
    private var mFragment = fragment

    override fun onPlayerError(eventTime: AnalyticsListener.EventTime?, error: ExoPlaybackException?) {
        var exception = when (error!!.type) {
            ExoPlaybackException.TYPE_RENDERER -> error.rendererException.message
            ExoPlaybackException.TYPE_SOURCE -> error.sourceException.message
            else -> error.unexpectedException.message
        }
        Log.d(LOG_TAG, "onPlayerError $exception")
    }

    override fun onPlayerStateChanged(
        eventTime: AnalyticsListener.EventTime?,
        playWhenReady: Boolean,
        playbackState: Int
    ) {
        Log.d(LOG_TAG, "onPlayerStateChanged playWhenReady $playWhenReady -> playbackState $playbackState")
        if (playbackState == Player.STATE_ENDED) {
            mFragment.nextVideo()
        }
    }
}
