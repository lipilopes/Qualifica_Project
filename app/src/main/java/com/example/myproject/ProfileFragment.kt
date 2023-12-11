package com.example.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myproject.publicFunctions.Companion.logout

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview: View =inflater.inflate(R.layout.fragment_profile, container, false)
        //Erro esta aqui
        /*val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        buttonLogout.setOnClickListener {
        logout()
        }
        */

        return rootview
  }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}