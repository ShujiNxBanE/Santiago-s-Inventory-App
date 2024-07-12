package com.santiagosinventory

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.santiagosinventory.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.etCorreo.text.toString()
            val password = binding.etContrasenia.text.toString()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(baseContext, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Falló al iniciar sesion: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}