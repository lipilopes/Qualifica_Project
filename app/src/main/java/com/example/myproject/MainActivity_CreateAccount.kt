package com.example.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.myproject.CheckLayout.Companion.emailIsOk
import com.example.myproject.CheckLayout.Companion.passwordCompare
import com.google.firebase.auth.FirebaseAuth

class MainActivity_CreateAccount : AppCompatActivity()
{
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  btnBack: ImageButton
    private lateinit var  btnCreateAccount: Button

    private lateinit var  errorView: TextView

    private lateinit var  editTextNick: EditText
    private lateinit var  editTextEmailAddress: EditText
    private lateinit var  editTextPassword: EditText
    private lateinit var  editTextPasswordCheck: EditText

    private fun initLayout()
    {
        btnBack             = findViewById(R.id.buttonBack)
        btnCreateAccount    = findViewById(R.id.buttonCreateAccount)

        errorView   = findViewById(R.id.textViewFeedBack)

        editTextNick          = findViewById(R.id.editTextNick)
        editTextEmailAddress  = findViewById(R.id.editTextEmailAddress)
        editTextPassword      = findViewById(R.id.editTextPassword)
        editTextPasswordCheck = findViewById(R.id.editTextPasswordCheck)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_create_account)

        //funcao para iniciar todos os componentes do layout
        initLayout()

        firebaseAuth = FirebaseAuth.getInstance()

        btnBack.setOnClickListener{
            comeBackToLogin()
        }

        btnCreateAccount.setOnClickListener{
            createAccount()
        }
    }

    private fun createAccount()
    {
        if(verifyEmail() && verifyPassWord() && verifyNickName()) {
            val email: String = editTextEmailAddress.text.toString().trim()
            val password: String = editTextPassword.text.toString().trim()

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    errorView.text = "Conta criada\n" + it.getResult().toString()
                } else {
                    errorView.text = it.exception.toString()
                }
            }
        }
        else
            errorView.text = "Has something wrong here!!!"
    }



    private fun comeBackToLogin()
    {
        val intent = Intent(this, MainActivity_Login::class.java)

        intent.putExtra("email", editTextEmailAddress.text)

        startActivity(intent)
        finish()
    }

    //verifica email
    private fun verifyEmail(): Boolean
    {
        return emailIsOk(editTextEmailAddress.text)
    }

    //verifica senha
    private fun verifyPassWord(): Boolean
    {
        return passwordCompare(editTextPassword.text, editTextPasswordCheck.text)
    }

    //verifica se o nick é unico
    private fun verifyNickName(): Boolean
    {
        return true
    }
}