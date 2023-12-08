package com.example.myproject

import android.content.Context
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo


public class CheckLayout_Class
{
    companion object {

        //faz o editText Balançar quando esta errado
        private fun shakeEditTextError(editText: EditText)
        {
            YoYo.with(Techniques.Shake).repeat(2).duration(1000).playOn(editText)
            //editText.setTextColor(R.color.error)
        }

        //Verifica se o email não esta nula
        //se existe @ na string
        fun emailIsOk(email: EditText,context: Context):Boolean
        {
            //email esta nulo
            if(email.text.isNullOrEmpty()){
                shakeEditTextError(email)
                toastMsg( R.string.email_error_empty,context)
                return false
            }

            //email tem '@'
            if(email.text.contains("@") == false){
                shakeEditTextError(email)
                toastMsg( R.string.email_error_fake,context)
                return false
            }
            //email.setTextColor(R.color.complete)
            return true
        }

        //Verifica se a senha não esta nula
        //Senha tem q ser maior que 8
        fun passwordIsOk(password: EditText,context: Context):Boolean
        {

            //senha esta nula
            if(password.text.isNullOrEmpty()){
                shakeEditTextError(password)
                //mensagem de erro
                toastMsg( R.string.password_error_empty,context)
                return false
            }

            //senha menor que 8
            if(password.text.toString().trim().length <= 7){
                shakeEditTextError(password)
                //mensagem de erro
                toastMsg( R.string.password_error_little,context)
                return false
            }
            //password.setTextColor(R.color.complete)
            return true
        }


        fun passwordCompare(password: EditText, password1: EditText,context: Context):Boolean
        {

            //verifica se as senhas estao ok
            if(passwordIsOk(password,context) && passwordIsOk(password1,context))
            {
                //verifica se as senhas são iguais
                if(password.text.toString().trim() == password1.text.toString().trim()) {
                    //password.setTextColor(R.color.complete)
                    //password1.setTextColor(R.color.complete)
                    return true
                }
            }
            shakeEditTextError(password1)
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
