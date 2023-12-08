package com.example.myproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoggedScreen_MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var uidProfile: String

    private lateinit var btnLoggout : Button

    private fun initLayout()
    {
        setContentView(R.layout.activity_logged_screen_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()

        uidProfile = intent.getStringExtra("uid").isNullOrEmpty().toString()

        // Define o fragmento inicial
        setCurrentFragment(homeFragment)

        toastMsg(uidProfile,this)

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

}