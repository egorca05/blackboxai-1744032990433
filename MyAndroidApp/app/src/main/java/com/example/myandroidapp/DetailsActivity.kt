package com.example.myandroidapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var playSoundButton: Button
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        descriptionTextView = findViewById(R.id.tvDescription)
        imageView = findViewById(R.id.ivImage)
        playSoundButton = findViewById(R.id.btnPlaySound)

        // Get the item data from the intent
        val item = intent.getParcelableExtra<Item>(EXTRA_ITEM)
        descriptionTextView.text = item?.description

        // Load image using Glide
        Glide.with(this)
            .load(item?.imageUrl)
            .into(imageView)

        // Set up sound playback if available
        if (item?.soundUrl != null) {
            playSoundButton.visibility = View.VISIBLE
            playSoundButton.setOnClickListener {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(item.soundUrl)
                    prepareAsync()
                    setOnPreparedListener { it.start() }
                }
            }
        } else {
            playSoundButton.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        private const val EXTRA_ITEM = "EXTRA_ITEM"

        fun newIntent(context: Context, item: Item): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_ITEM, item)
            return intent
        }
    }
}