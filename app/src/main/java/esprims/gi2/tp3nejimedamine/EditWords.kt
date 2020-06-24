package esprims.gi2.tp3nejimedamine

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import esprims.gi2.tp3nejimedamine.classes.Mot
import esprims.gi2.tp3nejimedamine.classes.WordsManager
import kotlinx.android.synthetic.main.activity_edit_words.*

class EditWords : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_words)

        var manageMots: WordsManager = WordsManager(this)

        var genre_selected:String = ""
        var type_selected:String = ""

        var types = resources.getStringArray(R.array.type)
        var genres = resources.getStringArray(R.array.genre)

        var type_spinner = findViewById<Spinner>(R.id.type_update)
        var genre_spinner = findViewById<Spinner>(R.id.genre_update)

        var intent = intent
        var mot = intent.getStringExtra("mot")
        var taille = intent.getIntExtra("taille", 10)
        var type = intent.getStringExtra("type")
        var genre = intent.getStringExtra("genre")

        mot_update.setText(mot)

        if (type_spinner != null) {
            var type_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
            type_spinner.adapter = type_adapter
            type_spinner.setSelection(type_adapter.getPosition(type))
            type_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val typeSelected: String = types[position]
                    type_selected = typeSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    type_spinner.setSelection(type_adapter.getPosition(type))
                }
            }
        }

        if (genre_spinner != null) {
            var genre_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
            genre_spinner.adapter = genre_adapter
            genre_spinner.setSelection(genre_adapter.getPosition(genre))
            genre_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val genreSelected: String = genres[position]
                    genre_selected = genreSelected
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    genre_spinner.setSelection(genre_adapter.getPosition(genre))
                }
            }
        }


        btn_update.setOnClickListener {
            manageMots.openReadDB()
            val mt = Mot(mot_update.text.toString(), taille.toInt(), type_selected, genre_selected)

            if (mot != null) {
                manageMots.updateWord(mot, mt)
                Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            }

            manageMots.closeDB()
        }
    }
}