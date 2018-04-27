package br.grupointegrado.tads.provaprimeirobimestre

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.Calcular){
            if (validarCampos()){
                val valorBase : Int = (text_base.text.toString()).toInt()
                val valorAltura : Int = (text_Altura.text.toString()).toInt()
                val valorProfundidade : Int = (text_profundidade.text.toString()).toInt()

                area.text = "Área: " + (valorBase * valorAltura).toString()
                volume.text = "Volume: "  + (valorBase * valorAltura * valorProfundidade).toString()

                return true
            }
            area.text = "Área: "
            volume.text = "Volume: "
            return false
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_calcular, menu)
        return true
    }

    fun validarCampos(): Boolean{

        if (text_base.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Base] !", Toast.LENGTH_SHORT).show()
            return false
        }

        if (text_Altura.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Altura] !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (text_profundidade.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Profundidade] !", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
