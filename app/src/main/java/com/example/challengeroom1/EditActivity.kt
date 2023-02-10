package com.example.challengeroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom1.Room.Constant
import com.example.challengeroom1.Room.dbsmksa
import com.example.challengeroom1.Room.tbsiswa
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { dbsmksa(this) }
    private var tbsisNis: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        simpandata()
        tbsisNis = intent.getIntExtra("intent_nis", tbsisNis)
        Toast.makeText(this,tbsisNis.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_simpan.visibility = View.GONE
                btn_update.visibility = View.GONE
                ETnis.visibility = View.GONE
                tampilsiswa()
            }
            Constant.TYPE_UPDATE -> {
                btn_simpan.visibility = View.GONE
                ETnis.visibility = View.GONE
                tampilsiswa()
            }
        }
    }

    fun simpandata(){
        btn_simpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().addtbsiswa(
                    tbsiswa(ETnis.text.toString().toInt(),ETnama.text.toString(),ETkelas.text.toString(),ETalamat.text.toString())
                )
                finish()
            }
        }
        btn_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbsiswaDao().updatetbsiswa(
                    tbsiswa(tbsisNis, ETnama.text.toString(), ETkelas.text.toString(),ETalamat.text.toString() )
                )
                finish()
            }
        }
    }


    fun tampilsiswa(){
        tbsisNis = intent.getIntExtra("intent_nis", 0)
        CoroutineScope(Dispatchers.IO).launch {
        val siswa = db.tbsiswaDao().tampilsemuaa(tbsisNis)[0]
            //ETnis.setText(siswa.nis)
            ETnama.setText(siswa.nama)
            ETkelas.setText(siswa.kelas)
            ETalamat.setText(siswa.alamat)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}