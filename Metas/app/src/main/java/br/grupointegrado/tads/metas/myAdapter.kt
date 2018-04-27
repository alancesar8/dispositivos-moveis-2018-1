package br.grupointegrado.tads.metas

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class myAdapter : RecyclerView.Adapter<myAdapter.ViewHolder> {


    companion object {
        val EXTRA_MENSAGEM = "br.grupointegrado.tads.metas.META"
    }

    val formater = SimpleDateFormat("dd/MM/yyyy")

    val context: Context
    var listMetas: ArrayList<ModMeta>

    constructor(context: Context, listMetas: ArrayList<ModMeta>) {
        this.context = context
        this.listMetas = listMetas
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(this.context).inflate(R.layout.item_rv_layout, parent, false)
        val viewHolder = ViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val meta = this.listMetas[position]
        val dtMeta = Calendar.getInstance()
        dtMeta.time = meta.dtlimite
        val timeNow = Calendar.getInstance()
        timeNow.time = Date()
        val days = dtMeta.get(Calendar.DAY_OF_YEAR) - timeNow.get(Calendar.DAY_OF_YEAR)
        val years = dtMeta.get(Calendar.YEAR) - timeNow.get(Calendar.YEAR)


        holder!!.text_titulo!!.text = meta.titulo


        if ((meta.concluido) && (meta.dtconclusao != null)) {
            holder.text_date!!.text = "Meta completada em: " + formater.format(meta.dtconclusao).toString()
        } else if (years < 0) {
            val aux = (365 * years - days * -1) * -1
            holder.text_date!!.text = "Meta excedida em: $aux dia(s)"
        } else if (years > 0) {
            val aux = (365 * years - days)
            holder.text_date!!.text = "$aux Dia(s) restante(s) para concluir a meta!"
        } else if (years == 0 && days == 0) {
            holder.text_date!!.text = "Último dia para concluir a meta!"
        } else {
            holder.text_date!!.text = "$days Dia(s) para concluir a meta!"
        }

        holder.btn_editar!!.setOnClickListener {
            val intent = Intent(this.context, AlterarActivity::class.java)
            intent.putExtra(EXTRA_MENSAGEM, position)
            this.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.listMetas.size
    }


    inner class ViewHolder : RecyclerView.ViewHolder {

        val btn_editar: ImageButton?
        val text_titulo: TextView?
        val text_date: TextView?


        constructor(itemView: View?) : super(itemView) {
            text_titulo = itemView?.findViewById<TextView>(R.id.tv_title)
            text_date = itemView?.findViewById<TextView>(R.id.tv_date)
            btn_editar = itemView?.findViewById<ImageButton>(R.id.btn_alter)
        }

    }
}
