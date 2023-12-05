package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myproject.CheckLayout.Companion.emailIsOk
import com.example.myproject.CheckLayout.Companion.passwordIsOk

class MainActivity_CreateAccount : AppCompatActivity()
{
    private var email:String?       = intent.getStringExtra("email")
    private var password:String?    = intent.getStringExtra("password")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_create_account)




    }

    fun verifyEmail(): Boolean
    {
        return emailIsOk(email)
    }

    fun verifyPassWord(): Boolean
    {
        return passwordIsOk(email)
    }
}