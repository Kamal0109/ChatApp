package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var userRecyclerView : RecyclerView
    lateinit var userList : ArrayList<User>
    lateinit var adapter: UserAdapter
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        setContentView(R.layout.activity_main)

        userList= ArrayList()
        adapter = UserAdapter(this,userList)

        userRecyclerView=findViewById(R.id.userRecyclerView)

        userRecyclerView.adapter=adapter
        userRecyclerView.layoutManager=LinearLayoutManager(this)



        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnapShot in snapshot.children){
                    val currentUser = postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid!=currentUser?.uid){
                        if (currentUser != null) {
                            userList.add(currentUser)
                        }
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {


            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,MyLoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)

    }

}