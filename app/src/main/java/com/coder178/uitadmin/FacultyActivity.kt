package com.coder178.uitadmin

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.coder178.uitadmin.Data.Faculty
import com.coder178.uitadmin.Data.Notice
import com.coder178.uitadmin.databinding.ActivityFacultyBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class FacultyActivity : AppCompatActivity() {

    lateinit var binding: ActivityFacultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val facultyName = binding.facultyName
        val facultyBranch = binding.facultyBranch
        val facultyImage = binding.facultyImageUrl

        binding.UploadFaculty.setOnClickListener {
            uploadFaculty(facultyName.toString(),facultyBranch.toString(),facultyImage.toString())
        }
        binding.deleteFaculty.setOnClickListener {
            deleteFaculty(facultyName)
        }
    }

    private fun deleteFaculty(facultyName: EditText) {

        var facultyName:String = facultyName.text.toString()
        if(facultyName!=null){
            MaterialAlertDialogBuilder(this).setTitle("Alert")
                .setNegativeButton("No"){dialog,which->Toast.makeText(this,"No Clicked",Toast.LENGTH_SHORT).show()}
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                    val query = FirebaseFirestore.getInstance().collection("Faculty")
                        .whereEqualTo("facultyName",facultyName).get()
                    query.addOnSuccessListener {
                        for(document in it){
                            FirebaseFirestore.getInstance().collection("Faculty").document(document.id).delete()
                        }
                    }
                        .addOnSuccessListener {
                            Toast.makeText(this, "$facultyName Deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "$facultyName not found", Toast.LENGTH_SHORT).show()
                        }
                }
                .show()
        }

    }

    private fun uploadFaculty(facultyName: String, facultyBranch: String, facultyImage: String) {

        val name = binding.facultyName.text.toString()
        val branch = binding.facultyBranch.text.toString()
        val Url = binding.facultyImageUrl.text.toString()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading This")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val db = FirebaseFirestore.getInstance()
        val user = Faculty(name,branch,Url)
        db.collection("Faculty").add(user)
            .addOnSuccessListener {
                binding.facultyName.text.clear()
                binding.facultyBranch.text.clear()
                binding.facultyImageUrl.text.clear()
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