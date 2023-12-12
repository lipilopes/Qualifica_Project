package com.example.myproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class CustomDialogFragment : DialogFragment()
{
    lateinit var rootview: View

    lateinit var title:TextView
    lateinit var description:TextView

    lateinit var btnclose:Button
    lateinit var button0:Button
    lateinit var button1:Button

    fun initLayout()
    {
         title = rootview.findViewById(R.id.title)
         description = rootview.findViewById(R.id.description)

         btnclose = rootview.findViewById(R.id.close)
         button0 = rootview.findViewById(R.id.button0)
         button1 = rootview.findViewById(R.id.button1)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.dialog_fragment, container, false)

        initLayout()

        btnclose.setOnClickListener{
            dismiss()
        }

        return rootview
    }

    fun startDialog(titletxt:String,desctxt:String,button0_txt: String = "",button0_Event: OnClickListener? = null,button1_txt: String = "", button1_Event: OnClickListener? = null)
    {
        title.text = titletxt
        title.text = desctxt

        if(button0_Event == null )
        {
            button0.visibility = View.GONE
        }
        else
        {
            button0.text = button0_txt
            button0.visibility = View.VISIBLE
            button0.setOnClickListener{
                button0_Event
                dismiss()
            }
        }

        if(button1_Event == null || button0_Event == null)
        {
            button1.visibility = View.GONE
        }
        else
        {
            button1.text = button1_txt
            button1.visibility = View.VISIBLE
            button1.setOnClickListener{
                button1_Event
                dismiss()
            }
        }
    }
}