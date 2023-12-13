package com.example.myproject

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.File

class SearchFragment : Fragment() , View.OnClickListener
{
    lateinit var firebaseAuth: FirebaseAuth
    var storage:FirebaseStorage? = null

    private lateinit var rootview: View

    lateinit var photoView: ImageView

    lateinit var editTextTitle: EditText
    lateinit var editTextDesc: EditText

    lateinit var buttonTakePict:Button

    lateinit var buttonCreatePost:Button

    private val REQUEST_IMAGE_CAPTURE = 1 // Constante usada para identificar a solicitação de captura de imagem

    private lateinit var imageBitmap: Bitmap

    private var uri_Image: Uri? = null


    private fun initLayout()
    {
        photoView = rootview.findViewById(R.id.imageView)

        editTextTitle   = rootview.findViewById(R.id.editTextTitle)
        editTextDesc    = rootview.findViewById(R.id.editTextDescription)

        buttonTakePict      = rootview.findViewById(R.id.buttonTakePicture)
        buttonCreatePost    = rootview.findViewById(R.id.buttonCreatePost)

        storage = Firebase.storage
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_search, container, false)

        initLayout()

        buttonTakePict.setOnClickListener(this)/* {
            dispatchTakePictureIntent() // Quando o botão é clicado, chama a função para tirar uma foto
        }*/

        buttonCreatePost.setOnClickListener(this)

        return rootview
    }

    private fun createPost()
    {
        val user = Data_User(firebaseAuth.uid, firebaseAuth.currentUser?.displayName)

        val imgReference = uploadImage(imageBitmap) ?: return

        var vote = Data_VotePost(user,"${imgReference}","${editTextTitle.text}","${editTextDesc.text}")

        //inicia o firestore
        val db = Firebase.firestore

        //coleção user -> cria uma nova pasta com o uid do usuario
        db.collection("users").document(firebaseAuth.uid.toString())
            .set(vote)
            .addOnSuccessListener { }
            .addOnFailureListener { }

        var post = Data_Post(user,vote.title,vote.description,null,null,0, (System.currentTimeMillis()*10)*1000)

        var addedDocRef = db.collection(getString(R.string.db_post)).add(post).addOnSuccessListener {

        }.addOnFailureListener { }



    }

    private fun dispatchTakePictureIntent() {

        // Cria uma intenção para abrir a câmera do dispositivo
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(rootview.context.packageManager) != null) {
            // Verifica se existe uma atividade de câmera para lidar com a intenção
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            // Inicia a atividade da câmera e aguarda o resultado
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                rootview.context.contentResolver,
                imageBitmap,
                "Title",
                "Description"
            )

        }
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            buttonCreatePost.id -> { createPost() }

            buttonTakePict.id -> {
                dispatchTakePictureIntent()
            }
        }
    }

    private fun uploadImage(bitmapImg: Bitmap):StorageReference?
    {
        if(bitmapImg == null)
            return null

        var success = false

        val dialogProgression = Dialog(rootview.context,R.style.Base_Theme_MyProject )

        val baos    =  ByteArrayOutputStream()

        bitmapImg.compress(Bitmap.CompressFormat.JPEG,10,baos)

        val data = baos.toByteArray()

        val idPost = "ID_POST_HERER"
        //falta id doc do post para colocar Pictures/ID_POST/UID_USER
        val reference = storage!!.reference.child(getString(R.string.db_storage_picturePost,idPost,firebaseAuth.uid))

        val uploadTask = reference.putBytes(data)

        uploadTask.addOnSuccessListener {
            dialogProgression.dismiss()

            toastMsg(
                "${getString(R.string.uploadImg)}",
                rootview.context
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
                    rootview.context
                )
            }

            success = false
        }

         return if (success) reference else  null
    }

    fun getImageGallery()
    {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        startActivityForResult(Intent.createChooser(intent,getString(R.string.PickPicture)),11)
    }

    fun getImageCamera()
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

      if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.Q)
      {
          val contentValue = ContentValues()

          contentValue.put(MediaStore.MediaColumns.MIME_TYPE,"img.jpg")

          val resolver = rootview.context.contentResolver
          
          uri_Image = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValue)
          intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
      }
        else
      {
          val auth      = "com.example.myproject"
          val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
          val nameImg   = "${directory.path}/${getString(R.string.app_name)}${System.currentTimeMillis()}.jpg"
          var file      = File(nameImg)

          uri_Image = FileProvider.getUriForFile(rootview.context/*baseContext*/,auth,file)
      }

        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri_Image)
        startActivityForResult(intent, 22)
    }


}
