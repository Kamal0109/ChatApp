package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    lateinit var chatRecyclerView: RecyclerView
    lateinit var messageBox : EditText
    lateinit var sendButton: Button
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList : ArrayList<Message>
    lateinit var mDbref : DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent = Intent()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbref = FirebaseDatabase.getInstance().getReference()


        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton= findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        // logic for adding data to recyclerView
        mDbref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }

                messageAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        // addding message to database
        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message,senderUid)

            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")

        }

    }
}