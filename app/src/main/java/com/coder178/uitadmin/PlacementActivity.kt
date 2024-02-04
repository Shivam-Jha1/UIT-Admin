package com.coder178.uitadmin

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coder178.uitadmin.Data.Notice
import com.coder178.uitadmin.Data.Placement
import com.coder178.uitadmin.databinding.ActivityPlacementBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class PlacementActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlacementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = binding.StudentName
        var Url = binding.StudentImageUrl
        var branch = binding.studentBranch
        var companyName = binding.Company
        binding.UploadStudent.setOnClickListener {
            uploadStudent(name.toString(),branch.toString(),companyName.toString(),Url.toString())
        }
        binding.deleteStudent.setOnClickListener {
            deleteStudent(name.text.toString())
        }
    }

    private fun deleteStudent(name: String) {

        if(name!=null){
            MaterialAlertDialogBuilder(this).setTitle("Alert")
                .setNegativeButton("No"){dialog,which->Toast.makeText(this,"No Clicked",Toast.LENGTH_SHORT).show()}
                .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                    val query = FirebaseFirestore.getInstance().collection("Placement")
                        .whereEqualTo("studentName",name).get()
                    query.addOnSuccessListener {
                        for(document in it){
                            FirebaseFirestore.getInstance().collection("Placement").document(document.id).delete()
                        }
                    }
                        .addOnSuccessListener {
                            Toast.makeText(this, "$name Deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "$name not found", Toast.LENGTH_SHORT).show()
                        }
                }
                .show()
        }

    }

    private fun uploadStudent(Name: String, branch: String,company:String, imageUri: String) {

        val name = binding.StudentName.text.toString()
        val branch = binding.studentBranch.text.toString()
        val company = binding.studentBranch.text.toString()
        val Url = binding.StudentImageUrl.text.toString()
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading This")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val db = FirebaseFirestore.getInstance()
        val user = Placement(name,branch,company,Url)
        db.collection("Placement").add(user)
            .addOnSuccessListener {
                binding.StudentName.text.clear()
                binding.StudentImageUrl.text.clear()
                binding.studentBranch.text.clear()
                binding.Company.text.clear()
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