package com.example.myproject

import android.text.Editable

public class CheckLayout
{

    companion object {

        //Verifica se o email não esta nula
        //se existe @ na string
        fun emailIsOk(email: Editable):Boolean
        {
            //email esta nulo
            if(email.isNullOrEmpty())
                return false

            //email tem '@'
            if(email.contains("@") == false)
                return false

            return true
        }

        //Verifica se a senha não esta nula
        //Senha tem q ser maior que 8
        fun passwordIsOk(password: Editable):Boolean
        {
            //senha esta nula
            if(password.isNullOrEmpty())
                return false

            //senha menor que 8
            if(password.toString().trim().length < 8)
                return false

            return true
        }


        fun passwordCompare(password: Editable, password1: Editable):Boolean
        {
            //verifica se as senhas estao ok
            if(passwordIsOk(password) && passwordIsOk(password1))
            {
                //verifica se as senhas são iguais
                if(password.toString().trim() == password1.toString().trim())
                    return true
            }

            return false
        }
    }
}