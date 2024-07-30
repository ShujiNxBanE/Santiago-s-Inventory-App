package com.santiagosinventory

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.santiagosinventory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val user = auth.currentUser
        if (user != null) {
            checkUserRole(user.uid)
        } else {
            Log.e("MainActivity", "Usuario no autenticado")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        if (menu != null) {
            this.menu = menu
            // Ocultar y deshabilitar el ítem del menú inicialmente
            menu.findItem(R.id.crearNuevoUsuario)?.isEnabled = false
            menu.findItem(R.id.crearNuevoUsuario)?.isVisible = false
        } else {
            Log.e("MainActivity", "Error al inflar el menú")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.crearNuevoUsuario -> {
                // Acción para crear un nuevo usuario
                true
            }
            R.id.cerrarSesion -> {
                // Acción para cerrar sesión
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkUserRole(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("role")
                    if (role == "admin") {
                        Log.d("MainActivity", "El usuario es administrador")
                        // Mostrar opciones de administrador
                        menu?.findItem(R.id.crearNuevoUsuario)?.isEnabled = true
                        menu?.findItem(R.id.crearNuevoUsuario)?.isVisible = true
                    } else {
                        Log.d("MainActivity", "El usuario no es administrador")
                        // Mostrar opciones de usuario normal
                        menu?.findItem(R.id.crearNuevoUsuario)?.isEnabled = false
                        menu?.findItem(R.id.crearNuevoUsuario)?.isVisible = false
                    }
                } else {
                    Log.e("MainActivity", "Documento no encontrado")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Error al obtener el rol del usuario", exception)
                Toast.makeText(baseContext, "Error fetching user role: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}
