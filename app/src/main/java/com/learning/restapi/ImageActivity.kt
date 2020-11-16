package com.learning.restapi

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileInputStream
import java.net.URL

class ImageActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val bitmap = loadAsset()
        imageView.setImageBitmap(bitmap)

        loadURLWithGlide(imageView)

        this.imageView = imageView
    }

    private fun loadURLWithGlide(imageView: ImageView) {
        Glide.with(this)
            .load("https://i2.wp.com/itc.ua/wp-content/uploads/2020/04/android_logo_stacked__rgb_.5.jpg")
            .into(imageView)
    }

    private fun loadURLWithVolley() {
        val queue = Volley.newRequestQueue(this)
        val request = ImageRequest("https://i2.wp.com/itc.ua/wp-content/uploads/2020/04/android_logo_stacked__rgb_.5.jpg", { bitmap ->
            imageView?.setImageBitmap(bitmap)
        }, 600, 600, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, {
            Toast.makeText(this, "Something's wrong", Toast.LENGTH_LONG).show()
        })

        queue.add(request)
    }

    private fun loadURL() {
        val url = "https://i2.wp.com/itc.ua/wp-content/uploads/2020/04/android_logo_stacked__rgb_.5.jpg"
        Thread {
            URL(url).openStream().use {
                val bitmap = BitmapFactory.decodeStream(it)
                imageView?.post {
                    imageView?.setImageBitmap(bitmap)
                }
            }
        }.start()
    }

    private fun loadFile(): Bitmap {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "image.jpg")
        FileInputStream(file).use {
            return BitmapFactory.decodeStream(it)
        }
    }

    private fun loadAsset(): Bitmap {
        val assetManager = assets
        assetManager.open("image.jpg").use { stream ->
            return BitmapFactory.decodeStream(stream)
        }
    }

    private fun createBitmap(): Bitmap {
        val config = Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(400, 400, config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(200f, 200f, 40f, paint)
        return bitmap
    }
}