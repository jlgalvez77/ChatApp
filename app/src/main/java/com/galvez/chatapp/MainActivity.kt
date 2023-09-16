package com.galvez.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.galvez.chatapp.Fragmentos.FragmentoChats
import com.galvez.chatapp.Fragmentos.FragmentoUsuarios
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InicializarComponentes()
    }

    fun InicializarComponentes(){

        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        val tabLayout : TabLayout = findViewById(R.id.TabLayoutMain)
        val viewPager : ViewPager = findViewById(R.id.ViewPagerMain)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addItem(FragmentoUsuarios(), "Usuarios")
        viewPagerAdapter.addItem(FragmentoChats(), "Chats")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
    class ViewPagerAdapter(fragmentManager : FragmentManager):FragmentPagerAdapter(fragmentManager) {

        private val listaFragmentos : MutableList<Fragment> = ArrayList()
        private val listaTitulos : MutableList<String> = ArrayList()


        override fun getCount(): Int {
            return listaFragmentos.size
        }

        override fun getItem(position: Int): Fragment {
            return listaFragmentos[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listaTitulos[position]
        }

        fun addItem(fragment: Fragment, titulo:String){
            listaFragmentos.add(fragment)
            listaTitulos.add(titulo)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_salir->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, Inicio::class.java)
                Toast.makeText(applicationContext, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}