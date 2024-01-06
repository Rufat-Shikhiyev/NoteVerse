package com.example.noteverse.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.noteverse.NoteDataBaseClass
import com.example.noteverse.NoteDataClass
import com.example.noteverse.R
import com.example.noteverse.databinding.SavednoteviewBinding

class NoteAdapter(val context: Context, var datalist: List<NoteDataClass>): BaseAdapter() {

    private val db: NoteDataBaseClass = NoteDataBaseClass(context)

    fun upgradeNote(notes:List<NoteDataClass>){
        datalist = notes
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return datalist.count()
    }

    override fun getItem(p0: Int): Any {
        return datalist[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, contentView: View?, p2: ViewGroup?): View {

        var newContentView = contentView
        val holder : ViewHolder

        if (contentView==null){
            val binding : SavednoteviewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.savednoteview,
                p2,
                false
            )
            newContentView = binding.root
            holder = ViewHolder(binding)
            val notes = datalist[p0]
            holder.bind(datalist[p0])
            newContentView?.tag = holder
            binding.deletebtn.setOnClickListener {
                db.deleteNote(notes.id)
                upgradeNote(db.getNote())
                Toast.makeText(context,"Note was deleted",Toast.LENGTH_SHORT).show()
            }

        }else{

            holder = contentView.tag as ViewHolder
            holder.bind(datalist[p0])

        }

        return  newContentView!!



    }

    private class ViewHolder(var binding: SavednoteviewBinding){

        fun bind(data : NoteDataClass){
            binding.savedName.text = data.name
            binding.savedDescript.text = data.description
        }
    }



}