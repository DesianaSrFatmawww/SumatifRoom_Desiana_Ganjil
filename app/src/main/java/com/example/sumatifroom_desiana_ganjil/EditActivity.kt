package com.example.sumatifroom_desiana_ganjil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sumatifroom_desiana_ganjil.room.Constant
import com.example.sumatifroom_desiana_ganjil.room.codepelita
import com.example.sumatifroom_desiana_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_adapter_karyawan.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { codepelita(this) }
    private var idKaryawan : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        tombolPerintah()
        setUpView()
        idKaryawan = intent.getIntExtra("intent_id",idKaryawan)
        Toast.makeText(this,idKaryawan.toString(),Toast.LENGTH_SHORT).show()

    }
    fun setUpView(){

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                tampilData()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                tampilData()
            }
        }
    }

    fun tombolPerintah() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbKaryawanDao().addTbKaryawan(
                    tb_karyawan(
                    et_id.text.toString().toInt(),
                    et_nama.text.toString(),
                    et_alamat.text.toString(),
                    et_usia.text.toString()
                    )
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbKaryawanDao().updateTbKaryawan(
                    tb_karyawan(
                        et_id.text.toString().toInt(),
                        et_nama.text.toString(),
                        et_alamat.text.toString(),
                        et_usia.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun tampilData(){
        idKaryawan = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val karyawan = db.tbKaryawanDao().TampilTbKaryawan(idKaryawan)[0]
            val dataId : String = karyawan.id.toString()
            et_id.setText(dataId)
            et_nama.setText(karyawan.nama)
            et_alamat.setText(karyawan.alamat)
            et_usia.setText(karyawan.usia)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}