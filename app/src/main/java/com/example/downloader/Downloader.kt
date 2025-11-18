package com.example.minimaldownloader

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Downloader : AppCompatActivity() {
    
    private lateinit var urlInput: EditText
    private lateinit var downloadBtn: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create UI programmatically (no XML layout needed)
        urlInput = EditText(this).apply {
            hint = "Enter video URL"
            setText("https://example.com/video.mp4")
        }
        
        downloadBtn = Button(this).apply {
            text = "Download Video"
            setOnClickListener { startDownload() }
        }
        
        // Create vertical layout
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            addView(urlInput)
            addView(downloadBtn)
        }
        
        setContentView(layout)
    }
    
    private fun startDownload() {
        val url = urlInput.text.toString().trim()
        
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            // Use Android's built-in DownloadManager
            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Video Download")
                .setDescription("Downloading video file")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "video_${Date().time}.mp4"
                )
            
            downloadManager.enqueue(request)
            Toast.makeText(this, "Download started!", Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            Toast.makeText(this, "Download failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
