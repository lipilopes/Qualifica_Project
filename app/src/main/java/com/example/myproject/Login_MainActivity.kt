package com.example.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import com.example.myproject.CheckLayout_Class.Companion.emailIsOk
import com.example.myproject.CheckLayout_Class.Companion.passwordIsOk
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.firebase.auth.FirebaseAuth

class Login_MainActivity : AppCompatActivity()
{
    private lateinit var btnGoogleLogin: Button
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword : EditText

    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var googleSignInClient: GoogleSignInClient

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

        /*val gso = GoogleSignInOptions.Builder(GoggleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("760071566854-l42q8jucnl7md8snrgkrto9s887ej71o.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)*/

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
        if(emailIsOk(editTextEmailAddress.text,this) == true && passwordIsOk(editTextPassword.text,this) == true)
        {
            val email       = editTextEmailAddress.text.toString().trim()
            val password    = editTextPassword.text.toString().trim()

            //faz Login
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {

                    //val string: String = getString(R.string.)
                    toastMsg(getString(R.string.welcome, firebaseAuth.uid), this)
                    //vai pra tela principal
                    goToLoggedScreen()
                }else
                    toastMsg(it.exception.toString(), this)
            }
        }
    }


    //faz login pela conta goole
    private fun Googlelogin()
    {
       //val intent = googleSignInClient.signInIntent
    }

    private fun goToCreateAccount()
    {
        val intent = Intent(this, CreateAccount_MainActivity::class.java)

        startActivity(intent)
        finish()
    }

    private fun goToLoggedScreen()
    {
        val intent = Intent(this, LoggedScreen_MainActivity::class.java)
        intent.putExtra("uid",firebaseAuth.uid)
        startActivity(intent)
        finish()
    }
}

