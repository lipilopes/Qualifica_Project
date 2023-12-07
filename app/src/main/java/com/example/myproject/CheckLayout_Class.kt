package com.example.myproject

import android.content.Context
import android.text.Editable
import android.view.Gravity
import android.widget.Toast


public class CheckLayout_Class
{

    companion object {

        //Verifica se o email não esta nula
        //se existe @ na string
        fun emailIsOk(email: Editable,context: Context):Boolean
        {
            //email esta nulo
            if(email.isNullOrEmpty()){
                toastMsg( R.string.email_error_empty,context)
                return false
            }

            //email tem '@'
            if(email.contains("@") == false){
               toastMsg( R.string.email_error_fake,context)
                return false
            }

            return true
        }

        //Verifica se a senha não esta nula
        //Senha tem q ser maior que 8
        fun passwordIsOk(password: Editable,context: Context):Boolean
        {

            //senha esta nula
            if(password.isNullOrEmpty()){
                //mensagem de erro
                toastMsg( R.string.password_error_empty,context)
                return false
            }

            //senha menor que 8
            if(password.toString().trim().length <= 7){
                //mensagem de erro
                toastMsg( R.string.password_error_little,context)
                return false
            }

            return true
        }


        fun passwordCompare(password: Editable, password1: Editable,context: Context):Boolean
        {
            //verifica se as senhas estao ok
            if(passwordIsOk(password,context) && passwordIsOk(password1,context))
            {
                //verifica se as senhas são iguais
                if(password.toString().trim() == password1.toString().trim())
                    return true
            }
            //mensagem de erro
            toastMsg( R.string.passwordCheck_error_notEqual,context)
            return false
        }

        //minha toast custumizada
        fun toastMsg(msg:String,context: Context)
        {
            val myToast = Toast(context)
            myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            myToast.duration = Toast.LENGTH_LONG
            myToast.setText(msg)
            myToast.show()
        }

        //minha toast custumizada
        fun toastMsg(resId: Int,context: Context)
        {
            val myToast = Toast(context)
            myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            myToast.duration = Toast.LENGTH_LONG
            myToast.setText(resId)
            myToast.show()
        }
    }
}