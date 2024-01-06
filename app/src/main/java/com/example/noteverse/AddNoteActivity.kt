package com.example.noteverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteverse.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db : NoteDataBaseClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseClass(this)

        binding.savemyNotes.setOnClickListener {
            var name = binding.noteName.text.toString()
            var descript = binding.noteDescription.text.toString()
            val notedb = NoteDataClass(0,name,descript)
            db.insertData(notedb)
            finish()
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
        }


    }
}