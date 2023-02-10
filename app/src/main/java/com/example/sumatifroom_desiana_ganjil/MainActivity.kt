package com.example.sumatifroom_desiana_ganjil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sumatifroom_desiana_ganjil.room.Constant
import com.example.sumatifroom_desiana_ganjil.room.codepelita
import com.example.sumatifroom_desiana_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { codepelita(this) }
    lateinit var karyawanAdapter: AdapterKaryawan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()
        setUpRV()
    }

    override fun onStart() {
        super.onStart()
        loadData()

    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val karyawan = db.tbKaryawanDao().getTbKaryawan()
            Log.d("MainActivity","dbResponse:$karyawan")
            withContext(Dispatchers.Main){
                karyawanAdapter.setData(karyawan)
            }
        }
    }

    fun setUpListener() {
        btn_create.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(idKaryawan: Int,intentType:Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id",idKaryawan)
                .putExtra("intent_type",intentType)
        )
    }

    private fun setUpRV() {
        karyawanAdapter = AdapterKaryawan(arrayListOf(),object : AdapterKaryawan.OnAdapterListener{
            override fun onClick(tbkaryawan: tb_karyawan) {
                intentEdit(tbkaryawan.id,Constant.TYPE_READ)
            }

            override fun onUpdate(tbkaryawan: tb_karyawan) {
                intentEdit(tbkaryawan.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbkaryawan: tb_karyawan) {
                deleteDialog(tbkaryawan)
            }
        })
        rv_listDataKaryawan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = karyawanAdapter
        }
    }
    fun deleteDialog(tbKaryawan: tb_karyawan){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbKaryawan.nama}?")
            setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
                setPositiveButton("Ya") { dialogInterface, i ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.tbKaryawanDao().deleteTbKaryawan(tbKaryawan)
                        dialogInterface.dismiss()
                        loadData()
                    }
                }
            }
        alertDialog.show()
    }
}