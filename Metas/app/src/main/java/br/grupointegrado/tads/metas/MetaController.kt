package br.grupointegrado.tads.metas

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MetaController {

    companion object {
        val OWN_FILE_NAME = "dados"
        val OWN_TITULO = "titulo"
        val OWN_DESCRICAO = "descricao"
        val OWN_DTLIMITE = "dtlimite"
        val OWN_DTCONCLUSAO = "dtconclusao"
        val OWN_CONCLUIDO = "concluido"
    }

    fun fileToJSONArray(context: Context): JSONArray?{
        try {
            val file: FileInputStream = context.openFileInput(OWN_FILE_NAME)
            var stringBuilder = StringBuilder()
            var line = file.bufferedReader().readLine()

            while (line != null){
                stringBuilder.append(line).append("\n")
                line = file.bufferedReader().readLine()
            }

            if(stringBuilder.length>0){
                return JSONArray(stringBuilder.toString())
            }else{
                return null
            }

        }catch (ex: Exception){
            ex.printStackTrace()
            return null
        }
    }


    fun jsonArrayToMetaArray(jArray: JSONArray): ArrayList<ModMeta>?{
        try{
            val metasArray = ArrayList<ModMeta>()
            val formater = SimpleDateFormat("dd/MM/yyyy")
            var dtlimite: Date? = null
            var dtconclusao: Date? = null

            for (i in 0..(jArray.length() -1)){
                val aux = jArray.getJSONObject(i)
                dtlimite = formater.parse(aux.getString(OWN_DTLIMITE))
                dtconclusao = if (aux.getString(OWN_DTCONCLUSAO).equals("")){
                    null
                } else{
                    formater.parse(aux.getString(OWN_DTCONCLUSAO))
                }
                metasArray.add(ModMeta(aux.getString(OWN_TITULO), aux.getString(OWN_DESCRICAO),dtlimite, dtconclusao, aux.getBoolean(OWN_CONCLUIDO)))
            }

            return metasArray
        }catch (ex: Exception){
            ex.printStackTrace()
            println(ex)
            return null
        }
    }

    fun metaArrayToJSON(gArray: ArrayList<ModMeta>): JSONArray?{
        val formater = SimpleDateFormat("dd/MM/yyyy")
        var jArray = JSONArray()
        for (i in gArray){
            val metaJson = JSONObject();
            metaJson.put("titulo", i.titulo)
            metaJson.put("descricao", i.descricao)
            metaJson.put("dtlimite", formater.format(i.dtlimite))
            metaJson.put("dtconclusao", if(i.dtconclusao == null)"" else formater.format(i.dtconclusao))
            metaJson.put("concluido", i.concluido)
            jArray.put(metaJson)
        }
        return jArray

    }

    fun saveAllMeta(context: Context ,jArray: JSONArray?){
        try{
            val fileStream: FileOutputStream = context.openFileOutput(OWN_FILE_NAME, Context.MODE_PRIVATE);
            if (jArray == null){
                fileStream.write("".toByteArray())
                fileStream.close()
                return
            }
            val stringArray = jArray.toString()
            fileStream.write(stringArray.toByteArray())
            fileStream.close()


        }catch (e: IOException){
            e.printStackTrace()

        }
    }

}