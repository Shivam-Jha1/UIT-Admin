package com.coder178.uitadmin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coder178.uitadmin.Data.Notice
import com.coder178.uitadmin.databinding.ActivityNoticeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class NoticeActitivty : AppCompatActivity() {

    lateinit var binding:ActivityNoticeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val now = Date()
        val date = formatter.format(now)

        var name = binding.NoticeName
        var Url = binding.NoticeUrl
        binding.UploadNotice.setOnClickListener {
            uploadImage(name.toString(),date,Url.toString())

        }
    }

    private fun uploadImage(Name: String, date: String, imageUri: String) {

        val name = binding.NoticeName.text.toString()
        val Url = binding.NoticeUrl.text.toString()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading This")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val db = FirebaseFirestore.getInstance()
        val user = Notice(name,date,Url)
        db.collection("Notice").add(user)
            .addOnSuccessListener {
                binding.NoticeUrl.text.clear()
                binding.NoticeName.text.clear()
                Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
                if (progressDialog.isShowing) progressDialog.dismiss()

            }
            .addOnFailureListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }

    }
}