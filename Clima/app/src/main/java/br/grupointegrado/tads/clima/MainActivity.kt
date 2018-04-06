package br.grupointegrado.tads.clima

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.buscadorgithub.NetworkUtils
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import br.grupointegrado.tads.clima.util.JsonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        carregarDadosClima()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clima,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.menu.clima){
            carregarDadosClima()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class BuscarClimaTask : AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg params: String): String? {
            try {
                val localizacao = params[0]
                val url = NetworkUtils.construirUrl(localizacao)
                if (url!=null){
                    val result = NetworkUtils.obterRespostaDaUrlHttp(url)
                    return result
                }
                return null
            }catch (ex:Exception){
                ex.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: String?) {
            if (result!=null){
                exibirResultado()
                val clima = JsonUtils.getSimplesStringsDeClimaDoJson(this@MainActivity,result)
                for (i in 0 until clima!!.size){
                    tv_dados_clima.append(clima.get(i))
                    tv_dados_clima.append("\n\n")
                }
            }else{
                exibirMensagemErro()
            }
        }

        override fun onPreExecute() {
            exibirProgressbar()
        }
    }

    fun carregarDadosClima(){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        BuscarClimaTask().execute(localizacao)
    }

    fun exibirMensagemErro(){
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pg_aguarde.visibility = View.INVISIBLE
    }
    fun exibirResultado(){
        tv_dados_clima.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pg_aguarde.visibility = View.INVISIBLE
    }
    fun exibirProgressbar(){
        tv_dados_clima.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pg_aguarde.visibility = View.VISIBLE
    }


}
