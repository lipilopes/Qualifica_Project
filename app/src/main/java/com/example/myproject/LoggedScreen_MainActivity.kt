package com.example.myproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myproject.PublicFunctions.Companion.checkLogged
import com.example.myproject.PublicFunctions.Companion.logout

class LoggedScreen_MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()

    private fun initLayout()
    {
        setContentView(R.layout.activity_logged_screen_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inicia layout
        initLayout()

        //verifica se o usuario esta logado
        if(checkLogged() == false)
            comeBackToLogin()

        // Define o fragmento inicial
        setCurrentFragment(profileFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    //toastMsg("Home",this)
                    true
                }
                R.id.nav_search -> {
                    setCurrentFragment(searchFragment)
                    //toastMsg("Pesquisar",this)
                    true
                }
                R.id.nav_profile -> {
                    setCurrentFragment(profileFragment)
                    //toastMsg("Perfil",this)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    //faz loggout e volta para tela de login
    private fun comeBackToLogin()
    {
        logout()

        val intent = Intent(this, Login_MainActivity::class.java)

        startActivity(intent)
        finish()
    }

}