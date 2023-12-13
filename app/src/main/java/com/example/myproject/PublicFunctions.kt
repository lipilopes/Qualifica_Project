package com.example.myproject

import com.google.firebase.auth.FirebaseAuth


class PublicFunctions
{

    companion object
    {
        private lateinit var firebaseAuth:FirebaseAuth


        fun checkLogged(): Boolean
        {
            val user = firebaseAuth.getCurrentUser()

           return user != null

        }

        fun logout()
        {
            firebaseAuth.signOut()
        }

        fun user(): Data_User?
        {
            return null
        }
    }
}