package com.example.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myproject.CheckLayout_Class.Companion.toastMsg
import com.example.myproject.PublicFunctions.Companion.logout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ProfileFragment : Fragment()
{
    lateinit var firebaseAuth:FirebaseAuth

    private lateinit var rootview: View

    private lateinit var name: TextView

    private lateinit var profileImg: ImageView

    private fun initLayout()
    {
        name = rootview.findViewById(R.id.TextViewName)

        profileImg = rootview.findViewById(R.id.imageViewProfile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_profile, container, false)

        initLayout()

        val buttonLogout = rootview.findViewById<Button>(R.id.buttonLogout)

        buttonLogout.setOnClickListener{
            logout()
        }

        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection(getString(R.string.db_users)).document("${firebaseAuth.uid}")
        docRef.get()
            .addOnSuccessListener {
            }
            .addOnFailureListener { exception ->
                toastMsg("${exception?.message}", this.rootview.context)
            }

        val fbUser: FirebaseUser? = firebaseAuth.currentUser

        name.text = getString(R.string.welcome,fbUser?.displayName)

        //baixar imagem do perfil

        return rootview
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}