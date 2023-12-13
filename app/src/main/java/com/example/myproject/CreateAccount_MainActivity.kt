package com.example.myproject

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myproject.CheckLayout_Class.Companion.emailIsOk
import com.example.myproject.CheckLayout_Class.Companion.passwordCompare
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class CreateAccount_MainActivity : AppCompatActivity()
{
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  btnBack: ImageButton
    private lateinit var  btnCreateAccount: Button

    private lateinit var  editTextNick: EditText
    private lateinit var  editTextEmailAddress: EditText
    private lateinit var  editTextPassword: EditText
    private lateinit var  editTextPasswordCheck: EditText

    private lateinit var  group: Group

    private lateinit var textLoading: TextView

    //inicia o layout
    private fun initLayout()
    {
        btnBack             = findViewById(R.id.buttonBack)
        btnCreateAccount    = findViewById(R.id.buttonCreateAccount)

        editTextNick          = findViewById(R.id.editTextNick)
        editTextEmailAddress  = findViewById(R.id.editTextEmailAddress)
        editTextPassword      = findViewById(R.id.editTextPassword)
        editTextPasswordCheck = findViewById(R.id.editTextPasswordCheck)

        group                 = findViewById(R.id.group)

        textLoading           = findViewById(R.id.textViewLoading)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_create_account)

        //funcao para iniciar todos os componentes do layout
        initLayout()

        //seta o firebase
        firebaseAuth = FirebaseAuth.getInstance()

        //função do botao de valtar
        btnBack.setOnClickListener{
            comeBackToLogin()
        }

        //função do butao de criar conta
        btnCreateAccount.setOnClickListener{
            createAccount()
        }
    }

    private fun createAccount()
    {
        group.visibility = View.GONE

        //faz animação no textView
        YoYo.with(Techniques.Flash).delay(2000).repeat(5).duration(1000).playOn(textLoading)

        //verifica se email senha e nick estão corretos
        if(verifyEmail() && verifyPassWord() && verifyNickName()) {

            val email: String = editTextEmailAddress.text.toString().trim()//pega email do usuario
            val password: String = editTextPassword.text.toString().trim()//pega senha do usuario
            val nick:String = editTextNick.text.toString()//pega nick

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Create a new user with a first and last name
                    val user = Data_User(firebaseAuth.uid,nick,email)

                    //inicia o firestore
                    val db = Firebase.firestore

                    //coleção user -> cria uma nova pasta com o uid do usuario
                    db.collection("users").document(firebaseAuth.uid.toString())
                        .set(user)
                        .addOnSuccessListener { comeBackToLogin(); }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


                    val fbUser: FirebaseUser? = firebaseAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(nick)
                        .build()
                    fbUser?.updateProfile(profileUpdates)

                } else {
                    group.visibility = View.VISIBLE
                    toastMsg("${it.exception?.message}", this)
                }
            }
        }
        else {
            group.visibility = View.VISIBLE

            return
        }

        toastMsg( getString(R.string.loading), this)
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
        return emailIsOk(editTextEmailAddress,this)
    }

    //verifica senha
    private fun verifyPassWord(): Boolean
    {
        return passwordCompare(editTextPassword, editTextPasswordCheck,this)
    }

    //verifica se o nick é unico
    private fun verifyNickName(): Boolean
    {
        return true
    }
}