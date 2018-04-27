package br.grupointegrado.tads.metas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listMetas : ArrayList<ModMeta> ?= null
    val controller = MetaController()


    fun fillListView(){
        val jArray = controller.fileToJSONArray(this)

        if (jArray == null){
            controller.saveAllMeta(this, null)
        }else{
            this.listMetas = controller.jsonArrayToMetaArray(jArray)
            val adapter = myAdapter(this@MainActivity, this.listMetas!!)
            val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
            item_meta.adapter = adapter
            item_meta.layoutManager = layoutManager
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_meta, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.add_meta){
            val intent = Intent(this, NovoActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        fillListView()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fillListView()

    }
}
