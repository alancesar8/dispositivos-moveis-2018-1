package br.grupointegrado.tads.metas

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class DataPickerUtils (context: Context, editText: EditText){

    val myCalendar = Calendar.getInstance()
    val edtTxt = editText
    val context = context

    val date = DatePickerDialog.OnDateSetListener{
        view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

    fun DateDialog(){
        this.edtTxt.setOnFocusChangeListener{
            v, hasfocus ->
            if (hasfocus == true){
                DatePickerDialog(this.context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show()
                v.clearFocus()
            }
        }
    }

    fun updateLabel(){
        val formater = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        edtTxt.setText(formater.format(myCalendar.time))
    }

}