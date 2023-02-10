package com.example.challengeroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1.Room.tbsiswa
import kotlinx.android.synthetic.main.activity_siswa_adapter.view.*

class SiswaAdapter (private val siswa: ArrayList<tbsiswa>, private val listener: onAdapterlistener)
    :RecyclerView.Adapter<SiswaAdapter.SiswaViewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewholder {
        return SiswaViewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_siswa_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SiswaViewholder, position: Int) {
        val sis = siswa[position]
        holder.view.T_nama.text = sis.nama
        holder.view.T_nama.setOnClickListener{
            listener.onClik(sis)
        }
        holder.view.imageEdit.setOnClickListener{
            listener.onUpdate(sis)
        }
        holder.view.imageDelete.setOnClickListener{
            listener.onDelete(sis)
        }

    }

    override fun getItemCount() = siswa.size

    class SiswaViewholder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<tbsiswa>) {
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterlistener {
        fun onClik(tbsiswa: tbsiswa)
        fun onUpdate(tbsiswa: tbsiswa)
        fun onDelete(tbsiswa: tbsiswa)
    }

}