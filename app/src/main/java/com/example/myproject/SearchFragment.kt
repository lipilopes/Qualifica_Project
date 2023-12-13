package com.example.myproject

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.io.ByteArrayOutputStream


class SearchFragment : Fragment() 
{
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var rootview: View

    lateinit var photoView: ImageView

    lateinit var editTextTitle: EditText
    lateinit var editTextDesc: EditText

    lateinit var buttonTakePict:Button

    lateinit var buttonCreatePost:Button

    private val REQUEST_IMAGE_CAPTURE = 1 // Constante usada para identificar a solicitação de captura de imagem

    private lateinit var imageBitmap: Bitmap

    private fun initLayout()
    {
        photoView = rootview.findViewById(R.id.imageView)

        editTextTitle   = rootview.findViewById(R.id.editTextTitle)
        editTextDesc    = rootview.findViewById(R.id.editTextDescription)

        buttonTakePict      = rootview.findViewById(R.id.buttonTakePicture)
        buttonCreatePost    = rootview.findViewById(R.id.buttonCreatePost)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_search, container, false)

        initLayout()

        buttonTakePict.setOnClickListener {
            dispatchTakePictureIntent() // Quando o botão é clicado, chama a função para tirar uma foto
        }

        buttonCreatePost.setOnClickListener {
            createPost()
        }

        return rootview
    }

    private fun createPost()
    {
        /*
        val user = Data_User(firebaseAuth.uid, firebaseAuth.currentUser?.displayName)

        val storage = FirebaseStorage.getReference()

        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to "mountains.jpg"
        val imgRef = storageRef.child("name.jpg")

        // Create a reference to 'images/mountains.jpg'
        val mountainImagesRef = storageRef.child("${firebaseAuth.uid}//mountains.jpg")

        // Get the data from an ImageView as bytes
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imgRef.putBytes(data)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

        var post = Data_GuestPost(user,imageBitmap,"${editTextTitle.text}","${editTextDesc.text}")

        //inicia o firestore
        val db = Firebase.firestore

        //coleção user -> cria uma nova pasta com o uid do usuario
        db.collection("users").document(firebaseAuth.uid.toString())
            .update(user)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        */
    }

    private fun dispatchTakePictureIntent() {
        /*
        // Cria uma intenção para abrir a câmera do dispositivo
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Verifica se existe uma atividade de câmera para lidar com a intenção
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            // Inicia a atividade da câmera e aguarda o resultado
        }

         */
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
            photoView.setImageBitmap(imageBitmap)
            // Exibe a foto capturada na ImageView

            /*
            // Salva a imagem na galeria
            MediaStore.Images.Media.insertImage(
                contentResolver,
                imageBitmap,
                "Title",
                "Description"
            )
             */
        }
    }


}