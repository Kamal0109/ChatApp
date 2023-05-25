package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    lateinit var editTextEmail: EditText
    lateinit var editTextPassword : EditText
    lateinit var editTextName: EditText
    lateinit var buttonSignUp: Button
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        setContentView(R.layout.activity_signup_actiity)

        editTextEmail=findViewById(R.id.editTextemail)
        editTextPassword=findViewById(R.id.editTextpassword)
        editTextName=findViewById(R.id.editTextName)
        buttonSignUp=findViewById(R.id.buttonSignup)


        buttonSignUp.setOnClickListener {
            val email=editTextEmail.text.toString()
            val password=editTextPassword.text.toString()
            val name=editTextName.text.toString()

            signUp(name,email,password)

        }
    }

    fun signUp(name:String,email:String,password:String) {
        // login for user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignupActivity,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignupActivity,"Error has occurred",Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid:String ){

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}