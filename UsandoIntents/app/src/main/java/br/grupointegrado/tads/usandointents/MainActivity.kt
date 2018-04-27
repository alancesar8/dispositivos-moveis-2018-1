package br.grupointegrado.tads.usandointents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_enviar.setOnClickListener({
            val activityDestino = SegundaActivity::class.java
                        val context = this

                        val intentSegundaActivity = Intent(context, activityDestino)
                        val mensagem = et_mensagem.text.toString()
            intentSegundaActivity.putExtra(Intent.EXTRA_TEXT, mensagem)

           startActivity(intentSegundaActivity)
        })


    btn_abrir_site.setOnClickListener ({
        val acao = Intent.ACTION_VIEW
        val dado = Uri.parse("http://www.grupointegrado.br")
        val intentImplicito = Intent(acao, dado)
        //verifica se a ação pode ser atendida
            if (intentImplicito.resolveActivity(packageManager) != null){
                startActivity(intentImplicito)
            }
        })

    btn_abrir_mapa.setOnClickListener ({
        val endereco = "Rua Tibirça, 865 - Centro, Juranda-PR"
        //construindo a URI
        val builder = Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", endereco)
        val uriEndereco = builder.build()
        val intent = Intent(Intent.ACTION_VIEW, uriEndereco)
        //verifica se a ação pode ser atendida
            if (intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        })

    }
}
