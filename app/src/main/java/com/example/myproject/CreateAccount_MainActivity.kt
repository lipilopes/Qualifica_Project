package com.example.myproject

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.myproject.CheckLayout_Class.Companion.emailIsOk
import com.example.myproject.CheckLayout_Class.Companion.passwordCompare
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase

class CreateAccount_MainActivity : AppCompatActivity()
{
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  btnBack: ImageButton
    private lateinit var  btnCreateAccount: Button

    private lateinit var  editTextNick: EditText
    private lateinit var  editTextEmailAddress: EditText
    private lateinit var  editTextPassword: EditText
    private lateinit var  editTextPasswordCheck: EditText

    private fun initLayout()
    {
        btnBack             = findViewById(R.id.buttonBack)
        btnCreateAccount    = findViewById(R.id.buttonCreateAccount)

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
        //verifica se email senha e nick estão corretos
        if(verifyEmail() && verifyPassWord() && verifyNickName()) {

            val email: String = editTextEmailAddress.text.toString().trim()//pega email do usuario
            val password: String = editTextPassword.text.toString().trim()//pega senha do usuario
            val nick:String = editTextNick.text.toString()//pega nick

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    // Create a new user with a first and last name
                    val user = hashMapOf(
                        "nick" to "$nick",
                        "email" to "$email"
                    )

                    val db = Firebase.firestore

                    db.collection("users").document(firebaseAuth.uid.toString())
                        .set(user)
                        .addOnSuccessListener { goToLoggedScreen(); Log.d(TAG, "DocumentSnapshot successfully written!")}
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                } else {
                    toastMsg(it.exception.toString(), this)
                }
            }
        }
        else {
            toastMsg( getString(R.string.createAccount_error), this)
            return
        }

        toastMsg( getString(R.string.loading), this)
    }

    private fun goToLoggedScreen()
    {
        toastMsg(getString(R.string.createAccount_completed, editTextNick.text.toString()), this)

        //vai para a tela
        val intent = Intent(this, LoggedScreen_MainActivity::class.java)
        intent.putExtra("uid",firebaseAuth.uid)
        startActivity(intent)
        finish()
    }

    private fun comeBackToLogin()
    {
        val intent = Intent(this, Login_MainActivity::class.java)

        intent.putExtra("email", editTextEmailAddress.text)

        startActivity(intent)
        finish()
    }

    //verifica email
    private fun verifyEmail(): Boolean
    {
        return emailIsOk(editTextEmailAddress.text,this)
    }

    //verifica senha
    private fun verifyPassWord(): Boolean
    {
        return passwordCompare(editTextPassword.text, editTextPasswordCheck.text,this)
    }

    //verifica se o nick é unico
    private fun verifyNickName(): Boolean
    {
        return true
    }
}