package com.example.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myproject.PublicFunctions.Companion.logout
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment()
{
    private lateinit var rootview: View

    private lateinit var name: TextView

    private lateinit var firebaseAuth:FirebaseAuth

    private fun initLayout()
    {
        name = rootview.findViewById(R.id.TextViewName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_profile, container, false)

        initLayout()

        //Erro esta aqui

        val buttonLogout = rootview.findViewById<Button>(R.id.buttonLogout)

        buttonLogout.setOnClickListener{
            logout()
        }

        name.text = "Welcome, "+firebaseAuth.currentUser?.displayName

        return rootview
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}