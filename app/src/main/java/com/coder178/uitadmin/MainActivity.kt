package com.coder178.uitadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.coder178.uitadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notice.setOnClickListener {
            intent = Intent(applicationContext, NoticeActitivty::class.java)
            startActivity(intent)
        }
        binding.ebook.setOnClickListener {
            intent = Intent(applicationContext, EbookActivity::class.java)
            startActivity(intent)
        }
        binding.faculty.setOnClickListener {
            intent = Intent(applicationContext, FacultyActivity::class.java)
            startActivity(intent)
        }
        binding.placement.setOnClickListener {
            intent = Intent(applicationContext, PlacementActivity::class.java)
            startActivity(intent)
        }
        binding.gallery.setOnClickListener {
            intent = Intent(applicationContext, GalleryActivity::class.java)
            startActivity(intent)
        }
        binding.delNotice.setOnClickListener {
            intent = Intent(applicationContext, DeleteNoticeActivity::class.java)
            startActivity(intent)
        }
    }
}