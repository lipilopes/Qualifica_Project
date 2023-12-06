package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import com.example.myproject.CheckLayout.Companion.emailIsOk
import com.example.myproject.CheckLayout.Companion.passwordIsOk
import com.google.firebase.auth.FirebaseAuth

class MainActivity_Login : AppCompatActivity()
{
    private lateinit var btnGoogleLogin: Button
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword : EditText

    private lateinit var firebaseAuth: FirebaseAuth

    private fun initLayout()
    {
        btnGoogleLogin     = findViewById(R.id.buttonGoogleLogin)
        btnLogin           = findViewById(R.id.buttonLogin)
        btnCreateAccount   = findViewById(R.id.buttonCreateAccount)

        editTextEmailAddress    = findViewById(R.id.editTextEmailAddress)
        editTextPassword        = findViewById(R.id.editTextPassword)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        //funcao para iniciar todos os componentes do layout
        initLayout()

        firebaseAuth = FirebaseAuth.getInstance()

        /*
        if(intent.getStringExtra("email").isNullOrEmpty() == false)
        {
            editTextEmailAddress.text = intent.getStringExtra("email")
        }
        */

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
        if(emailIsOk(editTextEmailAddress.text) == true && passwordIsOk(editTextPassword.text) == true)
        {
            val email       = editTextEmailAddress.text.toString().trim()
            val password    = editTextPassword.text.toString().trim()

            //faz Login
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                //vai pra tela principal
               // firebaseAuth.uid
            }
        }
        //error
    }

    //faz login pela conta goole
    private fun Googlelogin()
    {
       // firebaseAuth.isSignInWithEmailLink()
    }

    private fun goToCreateAccount()
    {
        val intent = Intent(this, MainActivity_CreateAccount::class.java)

        startActivity(intent)
        finish()
    }
}

