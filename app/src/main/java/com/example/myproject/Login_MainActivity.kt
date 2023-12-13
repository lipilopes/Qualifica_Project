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
    private lateinit var btnLogin: Button
    private lateinit var btnCreateAccount: Button

    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword : EditText

    private lateinit var group : Group
    private lateinit var textViewLoading: TextView


    private lateinit var firebaseAuth: FirebaseAuth

    private fun initLayout()
    {
        firebaseAuth = FirebaseAuth.getInstance()

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

        btnLogin.setOnClickListener {
            login()
        }

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

            //envia o email e senha para o firebase
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful)//se o retorno for successful é por logou
                {
                    //entro nos arquivos do usuario
                    val docRef = Firebase.firestore.collection(getString(R.string.db_users))
                        .document(firebaseAuth.uid.toString())

                    //puxa informações do usuario
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

                    //mostra o nome do usuario logado
                    toastMsg(getString(R.string.welcome, firebaseAuth.currentUser?.displayName), this)

                    //vai pra tela principal
                    goToLoggedScreen()
                }
                else
                {
                    //retira texto de loading
                    group.visibility = View.VISIBLE

                    // Se falhar, tratar o erro
                    task.exception?.let { exception ->
                        // Tratar a exceção aqui
                        // Exemplo: exibir uma mensagem de erro para o usuário
                        toastMsg(
                            "${exception.message}",
                            this
                        )
                    }

                    /*
                    var dialog = CustomDialogFragment()
                    dialog.show(supportFragmentManager,"customDialog")
                    dialog.startDialog("Error","${exception.message}")
                    */
                }
            }
        }
        else
        {
            //desativa tela para mostrar texto de loading...
            group.visibility = View.VISIBLE
        }
    }

    //muda para a tela de criar conta
    private fun goToCreateAccount()
    {
        val intent = Intent(this, CreateAccount_MainActivity::class.java)

        startActivity(intent)
        finish()
    }

    //muda para a tela principal
    private fun goToLoggedScreen()
    {
        val intent = Intent(this, LoggedScreen_MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

