package com.example.myproject

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myproject.CheckLayout_Class.Companion.emailIsOk
import com.example.myproject.CheckLayout_Class.Companion.passwordIsOk
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class Login_MainActivity : AppCompatActivity()
{
    //private lateinit var btnGoogleLogin: Button
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword : EditText

    private lateinit var group : Group
    private lateinit var textViewLoading: TextView


    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var googleSignInClient: GoogleSignInClient

    private fun initLayout()
    {
        //btnGoogleLogin     = findViewById(R.id.buttonGoogleLogin)
        btnLogin           = findViewById(R.id.buttonLogin)
        btnCreateAccount   = findViewById(R.id.buttonCreateAccount)

        editTextEmailAddress    = findViewById(R.id.editTextEmailAddress)
        editTextPassword        = findViewById(R.id.editTextPassword)

        group               = findViewById(R.id.group)

        textViewLoading     = findViewById(R.id.textViewLoading)

        val email = intent.getStringExtra("email")

        if(email != null)
            editTextEmailAddress.setText(email)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        //funcao para iniciar todos os componentes do layout
        initLayout()

        firebaseAuth = FirebaseAuth.getInstance()

        /* logar com google
        val gso = GoogleSignInOptions.Builder(GoggleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("760071566854-l42q8jucnl7md8snrgkrto9s887ej71o.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)*/

        btnLogin.setOnClickListener {
            login()
        }

        /*btnGoogleLogin.setOnClickListener {
            Googlelogin()
        }*/

        btnCreateAccount.setOnClickListener {
            goToCreateAccount()
        }
    }

    //faz login usando email e senha
    private fun login()
    {
        //desativa tela para mostrar texto de loading...
        group.visibility = View.GONE

        //faz animação no textView
        YoYo.with(Techniques.Flash).delay(1500).repeat(5).duration(1000).playOn(textViewLoading)

        //verifica se todos os campos estão preenchidos
        if(emailIsOk(editTextEmailAddress,this) == true && passwordIsOk(editTextPassword,this) == true)
        {
            val email       = editTextEmailAddress.text.toString().trim()
            val password    = editTextPassword.text.toString().trim()

            //faz Login
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    val docRef = Firebase.firestore.collection(getString(R.string.db_users))
                        .document(firebaseAuth.uid.toString())

                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            } else {
                                Log.d(TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }


                    //val string: String = getString(R.string.)
                    toastMsg(getString(R.string.welcome, firebaseAuth.uid), this)
                    //vai pra tela principal
                    goToLoggedScreen()
                }
                else
                {
                    //retira texto de loading
                    group.visibility = View.VISIBLE

                    toastMsg(it.exception.toString(), this)
                }
            }
        }
        else
        {
            //desativa tela para mostrar texto de loading...
            group.visibility = View.VISIBLE
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

