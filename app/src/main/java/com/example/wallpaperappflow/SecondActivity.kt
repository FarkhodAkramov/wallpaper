package com.example.wallpaperappflow

import android.R
import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.wallpaperappflow.databinding.ActivitySecondBinding
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import android.graphics.Bitmap
import android.widget.Toast
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.app.DownloadManager
import android.net.Uri


class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val wallpaperManager = WallpaperManager.getInstance(this)

        supportActionBar?.hide()
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val url = intent.getStringExtra("url")
        Picasso.get().load(url)
            .into(binding.imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    binding.progress.hide()

                }

                override fun onError(e: java.lang.Exception?) {
                    //do smth when there is picture loading error
                }
            })
        binding.setButton.setOnClickListener {
            Glide.with(this).asBitmap().load(url)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?,
                        o: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap?>?,
                        b: Boolean
                    ): Boolean {
                        Toast.makeText(baseContext, "Fail to load image..", Toast.LENGTH_SHORT)
                            .show()
                        return false
                    }

                    override fun onResourceReady(
                        bitmap: Bitmap?,
                        o: Any?,
                        target: Target<Bitmap?>?,
                        dataSource: DataSource?,
                        b: Boolean
                    ): Boolean {
                        // on below line we are setting wallpaper using
                        // wallpaper manager on below line.
                        try {
                            val width = wallpaperManager.desiredMinimumWidth
                            val height = wallpaperManager.desiredMinimumHeight
//                            val bitWidth: Int = url.getWidth()
                            wallpaperManager.setBitmap(bitmap)

                        } catch (e: IOException) {
                            // on below line we are handling exception.
                            e.printStackTrace()
                        }
                        return false
                    }
                }
                ).submit()
        }
        binding.download.setOnClickListener {
            val request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setDestinationInExternalPublicDir("/Happy", "Happy.jpg")
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(
                applicationContext, "Downloading File",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}