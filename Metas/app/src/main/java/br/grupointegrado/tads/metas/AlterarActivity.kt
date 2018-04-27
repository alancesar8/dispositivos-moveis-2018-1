package br.grupointegrado.tads.metas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.grupointegrado.tads.metas.myAdapter.Companion.EXTRA_MENSAGEM
import kotlinx.android.synthetic.main.activity_meta_alterar.*
import java.text.SimpleDateFormat
import java.util.*

class AlterarActivity : AppCompatActivity()  {

    val controller = MetaController()
    var listMeta: ArrayList<ModMeta>? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.completar_alter_meta, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.gravar){
            if(validarCampos()){
                saveModificationMeta()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun saveModificationMeta(){
        val formater = SimpleDateFormat("dd/MM/yyyy")
        val position = this.intent.getIntExtra(EXTRA_MENSAGEM, 0)
        val titulo: String = text_titulo_alterar.text.toString()
        val descricao: String = text_descricao_alterar.text.toString()
        val dtlimite: Date = formater.parse(text_data_limite_alterar.text.toString())
        val concluido: Boolean = check_concluido_alterar.isChecked
        var dtconclusao: Date? = null
        if (concluido == true){
            dtconclusao = Date()
        }
        val meta = ModMeta(titulo, descricao, dtlimite, dtconclusao,concluido)
        this.listMeta!![position] = meta
        val listFinal = this.listMeta
        val jArray = controller.metaArrayToJSON(listFinal!!)
        controller.saveAllMeta(this, jArray)
    }

    fun fillListMeta(){
        val jArray = controller.fileToJSONArray(this)
        this.listMeta = controller.jsonArrayToMetaArray(jArray!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meta_alterar)
        desabilitarCampos()
        botoesPadroes()
        fillListMeta()
        fillFields()

        botao_alterar.setOnClickListener {
            estadoBotoes()
            habilitarCampos()
        }

        botao_cancelar.setOnClickListener {
            desabilitarCampos()
            fillFields()
            botoesPadroes()
        }

        botao_excluir.setOnClickListener {
            deleteMeta()
        }

        DataPickerUtils(this, text_data_limite_alterar).DateDialog()
    }

    fun deleteMeta(){
        val position = this.intent.getIntExtra(EXTRA_MENSAGEM, 0)
        val meta = this.listMeta!![position]
        this.listMeta!!.remove(meta)
        val jArray = controller.metaArrayToJSON(this.listMeta!!)
        controller.saveAllMeta(this, jArray)
        finish()
    }

    fun fillFields(){
        val position = this.intent.getIntExtra(myAdapter.EXTRA_MENSAGEM, 0)
        val formater = SimpleDateFormat("dd/MM/yyyy")
        val list = this.listMeta
        if (list != null){
            var meta = list[position]
            text_titulo_alterar.setText(meta.titulo)
            text_descricao_alterar.setText(meta.descricao)
            text_data_limite_alterar.setText(formater.format(meta.dtlimite))
            check_concluido_alterar.isChecked = meta.concluido
        }

    }


    fun validarCampos(): Boolean{

        if (text_titulo_alterar.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Título] !", Toast.LENGTH_SHORT).show()
            return false
        }

        if (text_descricao_alterar.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Descrição] !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (text_data_limite_alterar.text.toString().equals("")){
            Toast.makeText(this, "Campo Obrigatório [Data Limite] !", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun desabilitarCampos() {
        text_titulo_alterar.isEnabled = false
        text_descricao_alterar.isEnabled = false
        text_data_limite_alterar.isEnabled = false
        check_concluido_alterar.isEnabled = false
    }

    fun habilitarCampos() {
        text_titulo_alterar.isEnabled = true
        text_descricao_alterar.isEnabled = true
        text_data_limite_alterar.isEnabled = true
        check_concluido_alterar.isEnabled = true
    }

    fun botoesPadroes(){
        botao_alterar.isEnabled = true
        botao_cancelar.isEnabled = false
        botao_excluir.isEnabled = true
    }

    fun estadoBotoes(){
        botao_alterar.isEnabled = false
        botao_cancelar.isEnabled = true
        botao_excluir.isEnabled = false
    }
}