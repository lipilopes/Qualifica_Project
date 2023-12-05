package com.example.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myproject.CheckLayout.Companion.emailIsOk
import com.example.myproject.CheckLayout.Companion.passwordIsOk

class MainActivity_Login : AppCompatActivity()
{
    private val btnGoogleLogin: Button     = findViewById(R.id.buttonGoogleLogin)
    private val btnLogin: Button           = findViewById(R.id.buttonLogin)
    private val btnCreateAccount: Button   = findViewById(R.id.buttonCreateAccount)

    private val editTextEmailAddress: EditText  = findViewById(R.id.editTextEmailAddress)
    private val editTextPassword : EditText     = findViewById(R.id.editTextPassword)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        btnLogin.setOnClickListener {
            login()
        }

        btnGoogleLogin.setOnClickListener {
            Googlelogin()
        }

        btnCreateAccount.setOnClickListener {
            goToCreateAccount()
        }
    }

    //faz login usando email e senha
    private fun login()
    {
        //verifica se todos os campos estão preenchidos
        if(emailIsOk(editTextEmailAddress.text.toString()) == false && passwordIsOk(editTextPassword.text.toString()) == false)
        {
            //Error
            return
        }
        //faz Login
    }

    //faz login pela conta goole
    private fun Googlelogin()
    {

    }

    private fun goToCreateAccount()
    {
        val intent = Intent(this, MainActivity_CreateAccount::class.java)

        intent.putExtra("email", editTextEmailAddress.text)
        intent.putExtra("password", editTextPassword.text)

        startActivity(intent)
        finish()
    }
}

