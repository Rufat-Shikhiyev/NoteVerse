package com.example.noteverse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteverse.adapter.NoteAdapter
import com.example.noteverse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var db : NoteDataBaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNoteButton.setOnClickListener {
            val intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }

        db = NoteDataBaseClass(this)

        println("Rufetin ${db.getNote()}Melumati qeyd edildi")


        adapter = NoteAdapter(this, db.getNote())
        binding.notelist.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.upgradeNote(db.getNote())
    }
}