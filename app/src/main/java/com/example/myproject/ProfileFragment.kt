package com.example.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

        //Erro esta aqui
        //val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        //buttonLogout.setOnClickListener {
            logout()
        //}
  }

    private fun logout()
    {
        //Init and attach
        var firebaseAuth = FirebaseAuth.getInstance();

        //Call signOut()
        firebaseAuth.signOut();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}