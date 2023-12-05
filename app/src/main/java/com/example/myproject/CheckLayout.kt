package com.example.myproject

public class CheckLayout
{

    companion object {

        //Verifica se o email não esta nula
        //se existe @ na string
        fun emailIsOk(email: String?):Boolean
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
        fun passwordIsOk(password: String?):Boolean
        {
            //senha esta nula
            if(password.isNullOrEmpty())
                return false

            //senha menor que 8
            if(password.length < 8)
                return false

            return true
        }
    }
}