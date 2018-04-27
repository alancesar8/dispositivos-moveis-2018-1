package br.grupointegrado.tads.metas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_meta_novo.*
import java.text.SimpleDateFormat
import java.util.*

class NovoActivity : AppCompatActivity(){


    val controller = MetaController()

    fun saveMetas(): Boolean{

        if (validarCampos()){
            val formater = SimpleDateFormat("dd/MM/yyyy")
            val datestring = text_data_limite_novo.text.toString()
            println(datestring)
            val titulo:String = text_titulo_novo.text.toString()
            val descricao:String = text_descricao_novo.text.toString()
            var dtconclusao: Date? = null
            var concluido = false
            if (check_concluido_novo.isChecked){
                concluido = true
                dtconclusao = Date()

            }
            val dtlimite = formater.parse(datestring)
            println(dtlimite)
            val meta = ModMeta(titulo, descricao, dtlimite, dtconclusao, concluido)

            var jArray = controller.fileToJSONArray(this)
            var MetasArray = ArrayList<ModMeta>()

            if (jArray != null){
                MetasArray = controller.jsonArrayToMetaArray(jArray)!!
            }

            MetasArray.add(meta)
            jArray = controller.metaArrayToJSON(MetasArray)
            controller.saveAllMeta(this, jArray)
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta_novo)

        botao_gravar_novo.setOnClickListener{
            if(saveMetas()){
                finish()
            }

        }

        DataPickerUtils(this, text_data_limite_novo).DateDialog()

    }


    fun validarCampos(): Boolean{

        if (text_titulo_novo.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Título] !", Toast.LENGTH_SHORT).show()
            return false
        }

        if (text_descricao_novo.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Descrição] !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (text_data_limite_novo.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Data Limite] !", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


}