package com.example.myproject

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class CreateAccount_MainActivity : AppCompatActivity()
{
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  btnBack: ImageButton
    private lateinit var  btnCreateAccount: Button

    private lateinit var  editTextNick: EditText
    private lateinit var  editTextEmailAddress: EditText
    private lateinit var  editTextPassword: EditText
    private lateinit var  editTextPasswordCheck: EditText

    private lateinit var  photoView: ImageButton

    private lateinit var  group: Group

    private lateinit var textLoading: TextView

    private val REQUEST_IMAGE_CAPTURE = 1 // Constante usada para identificar a solicitação de captura de imagem

    private lateinit var imageBitmap: Bitmap

    //inicia o layout
    private fun initLayout()
    {
        btnBack             = findViewById(R.id.buttonBack)
        btnCreateAccount    = findViewById(R.id.buttonCreateAccount)

        editTextNick          = findViewById(R.id.editTextNick)
        editTextEmailAddress  = findViewById(R.id.editTextEmailAddress)
        editTextPassword      = findViewById(R.id.editTextPassword)
        editTextPasswordCheck = findViewById(R.id.editTextPasswordCheck)

        photoView            = findViewById(R.id.imageProfile)

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

        photoView.setOnClickListener{
                TakePicture()
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

                    var profileImg =  uploadImageInStorage(imageBitmap) ?: null

                    // Create a new user with a first and last name
                    val user = Data_User(firebaseAuth.uid,nick,email,profileImg.toString())

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

    private fun TakePicture()
    {
        // Cria uma intenção para abrir a câmera do dispositivo
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Verifica se existe uma atividade de câmera para lidar com a intenção
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            // Inicia a atividade da câmera e aguarda o resultado
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Manipula o resultado da atividade da câmera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Se a foto foi tirada com sucesso (RESULT_OK), continue
            imageBitmap = data?.extras?.get("data") as Bitmap

            // Exibe a foto capturada na ImageView
            photoView.setImageBitmap(imageBitmap)

            // Salva a imagem na galeria
            MediaStore.Images.Media.insertImage(
                contentResolver,
                imageBitmap,
                "Title",
                "Description"
            )

        }
    }

    private fun uploadImageInStorage(bitmapImg: Bitmap): StorageReference?
    {
        if(bitmapImg == null)
                return null

        var success = false

        val dialogProgression = Dialog(this)

        val baos    =  ByteArrayOutputStream()

        bitmapImg.compress(Bitmap.CompressFormat.JPEG,10,baos)

        val data = baos.toByteArray()

        var storage = Firebase.storage
        val reference = storage!!.reference.child(getString(R.string.db_storage_pictureProfile,firebaseAuth.uid))

        val uploadTask = reference.putBytes(data)

        uploadTask.addOnSuccessListener {
            dialogProgression.dismiss()

            toastMsg(
                "${getString(R.string.uploadImg)}",
                this
            )

            success = true
        }.addOnFailureListener{

            dialogProgression.dismiss()

            // Se falhar, tratar o erro
            uploadTask.exception?.let { exception ->
                // Tratar a exceção aqui
                // Exemplo: exibir uma mensagem de erro para o usuário
                toastMsg(
                    "${exception.message}",
                    this
                )
            }

            success = false
        }

        return if (success) reference else  null
    }


}