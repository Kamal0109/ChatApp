package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MyLoginActivity : AppCompatActivity() {

    lateinit var editTextEmail: EditText
    lateinit var editTextPassword : EditText
    lateinit var buttonSignUp: Button
    lateinit var buttonSignIn : Button
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_my_login)

        editTextEmail=findViewById(R.id.editTextemail)
        editTextPassword=findViewById(R.id.editTextPassword)
        buttonSignUp=findViewById(R.id.buttonSignUp)
        buttonSignIn=findViewById(R.id.buttonSignIn)



        buttonSignUp.setOnClickListener {
            val intent =Intent(this@MyLoginActivity,SignupActivity::class.java)
            startActivity(intent)
        }

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            Sign_in(email,password)

        }

    }

    fun Sign_in(email : String,password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val intent =Intent(this@MyLoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@MyLoginActivity,"Main Activity ",Toast.LENGTH_SHORT).show()



                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@MyLoginActivity,"Error has occurred",Toast.LENGTH_SHORT).show()

                }
            }

    }
}