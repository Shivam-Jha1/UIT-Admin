package com.coder178.uitadmin.UI.Adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.coder178.uitadmin.Data.Notice
import com.coder178.uitadmin.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.ArrayList

class DelNoticeAdapter(val context: Context, val users: ArrayList<Notice>):RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notice_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = users[position]
        holder.name.text = user.name
        holder.date.text = user.date
//        Glide.with(context)
//            .load(user.image)
//            .into(holder.image)
        Picasso.get()
            .load(user.image)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val view = holder.itemView
            val name:String = holder.name.text.toString()
            holder.itemView.setOnClickListener {
                showAlertDialog(view,name)
            }
        }
    }

    private fun showAlertDialog(view: View?,name:String) {

        MaterialAlertDialogBuilder(context).setTitle("Alert")
            .setNegativeButton("No"){dialog,which->Toast.makeText(context,"No Clicked",Toast.LENGTH_SHORT).show()}
            .setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                val query = FirebaseFirestore.getInstance().collection("Notice")
                    .whereEqualTo("name",name).get()
                query.addOnSuccessListener { 
                    for(document in it){
                        FirebaseFirestore.getInstance().collection("Notice").document(document.id).delete()
                    }
                }
                Toast.makeText(context,"$name Deleted",Toast.LENGTH_SHORT).show()
            }
            .show()
    }


    override fun getItemCount(): Int {
        return users.size
    }
}

class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val name:TextView = itemView.findViewById(R.id.noticeText)
    val date:TextView = itemView.findViewById(R.id.noticeTime)
    val image:ImageView = itemView.findViewById(R.id.noticeImage)

}

