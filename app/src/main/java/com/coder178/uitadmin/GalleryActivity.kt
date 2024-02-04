package com.coder178.uitadmin

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coder178.uitadmin.Data.Gallery
import com.coder178.uitadmin.databinding.ActivityGalleryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class GalleryActivity : AppCompatActivity() {

    lateinit var binding:ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Image = binding.ImageUrl

        binding.UploadImage.setOnClickListener {
            uploadImage(Image.toString())
        }
        binding.deleteImage.setOnClickListener {
            deleteImage(Image.text.toString())
        }
    }

    private fun deleteImage(imageUrl: String) {

        if(imageUrl!=null){
            MaterialAlertDialogBuilder(this).setTitle("Alert")
                .setNegativeButton("No"){dialog,which->Toast.makeText(this,"No Clicked",Toast.LENGTH_SHORT).show()}
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                    val query = FirebaseFirestore.getInstance().collection("Gallery")
                        .whereEqualTo("galleryImage",imageUrl).get()
                    query.addOnSuccessListener {
                        for(document in it){
                            FirebaseFirestore.getInstance().collection("Gallery").document(document.id).delete()
                        }
                    }
                        .addOnSuccessListener {
                            Toast.makeText(this, "Image Deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
                        }
                }
                .show()
        }

    }

    private fun uploadImage(image: String) {

        val image = binding.ImageUrl.text.toString()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading This")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val db = FirebaseFirestore.getInstance()
        val user = Gallery(image)
        db.collection("Gallery").add(user)
            .addOnSuccessListener {
                binding.ImageUrl.text.clear()
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