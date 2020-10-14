package com.adam.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login1.*

class Login : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        auth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            val username = loginUsername.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if(username.isEmpty()) {
                loginUsername.error = "Username Harus Diisi"
                loginUsername.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6) {
                loginPassword.error = "Password harus lebih dari 6 Character"
                loginPassword.requestFocus()
                return@setOnClickListener
            }
            loginUser(username, password)
        }
        daftarbutton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this) {
            if(it.isSuccessful) {
                Intent(this@Login, Home::class.java).also {
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
        if(auth.currentUser !=  null) {
            Intent(this@Login, Home::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    } */


}