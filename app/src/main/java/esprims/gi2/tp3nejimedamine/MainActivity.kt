package esprims.gi2.tp3nejimedamine

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.get
import esprims.gi2.tp3nejimedamine.classes.Mot
import esprims.gi2.tp3nejimedamine.classes.MotDbHelper
import esprims.gi2.tp3nejimedamine.classes.WordsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnCreateContextMenuListener {
    var manageMots: WordsManager = WordsManager(this)
    var objet:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var listView = findViewById<ListView>(R.id.wordsList)

        refresh_words()

        btn_Add.setOnClickListener {
            val intent = Intent(applicationContext, AddWordActivity::class.java)
            startActivityForResult(intent, 1)
        }

        btn_word.setOnClickListener {
            val queryNomMasculin = "SELECT leMot FROM "+"'"+ MotDbHelper.THE_TABLE+"'"+" WHERE genre = 'Masculin' AND type = 'Nom' ORDER BY RANDOM() LIMIT 1"
            val nomMasculin = manageMots.getRandomWords(queryNomMasculin)

            val queryNomFeminin = "SELECT leMot FROM "+"'"+ MotDbHelper.THE_TABLE+"'"+" WHERE genre = 'Feminin' AND type = 'Nom' ORDER BY RANDOM() LIMIT 1"
            val nomFeminin = manageMots.getRandomWords(queryNomFeminin)

            val queryAdjMasculin = "SELECT leMot FROM "+"'"+ MotDbHelper.THE_TABLE+"'"+" WHERE genre = 'Masculin' AND type = 'Adjective' ORDER BY RANDOM() LIMIT 1"
            val adjMasculin = manageMots.getRandomWords(queryAdjMasculin)

            val queryAdjFeminin = "SELECT leMot FROM "+"'"+ MotDbHelper.THE_TABLE+"'"+" WHERE genre = 'Feminin' AND type = 'Adjective' ORDER BY RANDOM() LIMIT 1"
            val adjFeminin = manageMots.getRandomWords(queryAdjFeminin)

            generatedText_1.text = "Le "+nomMasculin+" est "+adjMasculin+";"
            generatedText_2.text = "La "+nomFeminin+" est "+adjFeminin+";"
        }

        registerForContextMenu(listView)

    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 ->
                when(resultCode){
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "Retour avec succee", Toast.LENGTH_LONG).show()
                        refresh_words()
                    }
                }
            2 ->
                when(resultCode){
                    Activity.RESULT_OK -> {
                        refresh_words()
                    }
                }
        }
    }

    fun refresh_words(){
        manageMots.openReadDB()
        val mots = manageMots.getWords()
        wordsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mots)
        manageMots.closeDB()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(Menu.NONE,1,Menu.NONE, "Supprimer Mot")
        menu?.add(Menu.NONE,2,Menu.NONE, "Editer Mot")
        objet=v
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        var itemPos = info.position
        manageMots.openReadDB()
        val mot = manageMots.getWords()
        val motText = mot[itemPos].leMot
        val tailleText = mot[itemPos].taille
        val typeText = mot[itemPos].type
        val genreText = mot[itemPos].genre

        when(item.itemId){
            1 -> {
                manageMots.deleteWords(motText)
                refresh_words()
            }

            2 -> {
                val intent = Intent(applicationContext, EditWords::class.java)
                intent.putExtra("mot", motText)
                intent.putExtra("taille", tailleText)
                intent.putExtra("type", typeText)
                intent.putExtra("genre", genreText)

                startActivityForResult(intent, 2)
            }
        }
        return super.onContextItemSelected(item)
        manageMots.closeDB()
    }

}