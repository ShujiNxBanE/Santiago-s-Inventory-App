package com.santiagosinventory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.santiagosinventory.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegistrarse.setOnClickListener {
            val email = binding.etCorreo.text.toString()
            val password = binding.etContrasenia.text.toString()
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Toast.makeText(baseContext, "El registro ah sido exitoso.", Toast.LENGTH_SHORT).show()
                    // Regresar a la pantalla inicial del Admin
                } else {
                    Toast.makeText(baseContext, "El registro falló: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}