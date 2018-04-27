package br.grupointegrado.tads.clima


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private var dadosClima: Array<String?>?

    constructor(dadosClima: Array<String?>?) :super() {
        this.dadosClima = dadosClima
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val numeroListaItem = LayoutInflater.from(parent.context).inflate(R.layout.previsao_lista_item, parent, false)

        val numeroViewHolder = PrevisaoViewHolder(numeroListaItem)

        return numeroViewHolder
    }

    override fun getItemCount(): Int {
        val dados = dadosClima
        if (dados != null){
            return dados.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val numero = dadosClima?.get(position)
        holder.tvDadosPrevisao?.text = numero
    }

    fun setDadosClima(dadosClima: Array<String?>?){
        this.dadosClima = dadosClima
        notifyDataSetChanged()
    }

    inner class PrevisaoViewHolder : RecyclerView.ViewHolder {

        val tvDadosPrevisao: TextView?

        constructor(itemView: View?) : super(itemView) {
            tvDadosPrevisao = itemView?.findViewById<TextView>(R.id.tv_dados_previsao)

        }
    }
}