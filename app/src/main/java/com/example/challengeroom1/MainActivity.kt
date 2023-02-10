package com.example.challengeroom1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom1.Room.Constant
import com.example.challengeroom1.Room.Constant.Companion.TYPE_CREATE
import com.example.challengeroom1.Room.Constant.Companion.TYPE_READ
import com.example.challengeroom1.Room.dbsmksa
import com.example.challengeroom1.Room.tbsiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { dbsmksa(this) }
    lateinit var siswaAdapter: SiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbsiswaDao().tampilsemua()
            Log.d("MainActivity", "dbResponse: $siswa")
            withContext(Dispatchers.Main) {
                siswaAdapter.setData(siswa)
            }
        }
    }

    private fun halEdit() {
        btn_input.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbsisNis: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_nis", tbsisNis)
                .putExtra("intent_type", intentType)
        )
    }


    private fun setupRecyclerView() {
        siswaAdapter = SiswaAdapter(arrayListOf(), object : SiswaAdapter.onAdapterlistener {
            override fun onClik(tbsiswa: tbsiswa) {
                intentEdit(tbsiswa.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(tbsiswa: tbsiswa) {
                intentEdit(tbsiswa.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbsiswa: tbsiswa) {
                deleteDialog(tbsiswa)
            }
        }
        )
        //id recyclerview
        listdatasiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }

    private fun deleteDialog(tbsiswa: tbsiswa){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("konfirmasi")
            setMessage("Yakin hapus ${tbsiswa.nama}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsiswaDao().deletetbsiswa(tbsiswa)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        alertDialog.show()
    }

}