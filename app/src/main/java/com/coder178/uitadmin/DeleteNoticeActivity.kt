package com.coder178.uitadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coder178.uitadmin.Data.Notice
import com.coder178.uitadmin.UI.Adapter.DelNoticeAdapter
import com.google.firebase.firestore.*
import java.util.ArrayList

class DeleteNoticeActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var userArrayList: ArrayList<Notice>
    lateinit var myadapter: DelNoticeAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_notice)

        recyclerView = findViewById(R.id.drv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myadapter = DelNoticeAdapter(this,userArrayList)
        recyclerView.adapter = myadapter

        EventChangeListner()
    }

    private fun EventChangeListner() {
        db = FirebaseFirestore.getInstance()
        db.collection("Notice").orderBy("date", Query.Direction.DESCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {

                if(error!=null){
                    Log.e("firestore error",error.message.toString())
                    return
                }
                for(dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){

                        userArrayList.add(dc.document.toObject(Notice::class.java))

                    }
                }
                myadapter.notifyDataSetChanged()
            }

        })
    }

}