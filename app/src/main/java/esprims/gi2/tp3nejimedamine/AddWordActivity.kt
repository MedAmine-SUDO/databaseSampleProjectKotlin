package esprims.gi2.tp3nejimedamine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import esprims.gi2.tp3nejimedamine.classes.Mot
import esprims.gi2.tp3nejimedamine.classes.WordsManager
import kotlinx.android.synthetic.main.activity_add_word.*
import java.lang.Exception

class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        val types = resources.getStringArray(R.array.type)
        val genres = resources.getStringArray(R.array.genre)

        val type_spinner = findViewById<Spinner>(R.id.id_type)
        val genre_spinner = findViewById<Spinner>(R.id.id_genre)

        var genre_selected:String = ""
        var type_selected:String = ""

        var manageMots: WordsManager = WordsManager(this)

        if (type_spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
            type_spinner.adapter = adapter

            type_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val typeSelected: String = types[position]
                    type_selected = typeSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        if (genre_spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, genres)
            genre_spinner.adapter = adapter

            genre_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val genreSelected: String = genres[position]
                    genre_selected = genreSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        btn_confirm.setOnClickListener {
            val mot_text = id_mot.text.toString()
            val genre_text = genre_selected
            val type_text = type_selected
            val mot = Mot(mot_text, 10, type_text, genre_text)

            manageMots.openWriteDB()
            try{
                manageMots.addMot(mot)
                Toast.makeText(this, "Ajout avec succée", Toast.LENGTH_LONG).show()
                val intent: Intent = Intent()
                setResult(Activity.RESULT_OK)
                finish()
            }
            catch (e: Exception){
                Toast.makeText(this, "Echec d'ajout", Toast.LENGTH_LONG).show()
            }

            manageMots.closeDB()

        }

    }
}