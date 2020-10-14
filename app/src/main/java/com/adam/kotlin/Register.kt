package com.adam.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        signupbutton.setOnClickListener {
            val fullname = regFullname.text.toString().trim()
            val username = regUsername.text.toString().trim()
            val password = regPassword.text.toString().trim()

            if(fullname.isEmpty()) {
                regFullname.error = "Nama Harus Diisi"
                regFullname.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6) {
                regPassword.error = "Password harus lebih dari 6 Character"
                regPassword.requestFocus()
                return@setOnClickListener
            }

            registerUser(fullname, username, password)
        }

        loginbuttonpage.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(fullname: String, username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this) {
            if(it.isSuccessful) {
                Intent(this@Register, Home::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /* override fun onStart() {
        super.onStart()
        if(auth.currentUser != null) {
            Intent(this@Register, Home::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    } */
}