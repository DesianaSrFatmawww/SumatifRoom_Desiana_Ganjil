package com.example.sumatifroom_desiana_ganjil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sumatifroom_desiana_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_adapter_karyawan.view.*


class AdapterKaryawan (private val karyawann : ArrayList<tb_karyawan>, private val listener : OnAdapterListener) :
    RecyclerView.Adapter<AdapterKaryawan.KaryawanViewHolder> (){

    class KaryawanViewHolder (val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder {
        return KaryawanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_karyawan,parent,false)
        )
    }

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        val kryawan = karyawann[position]
        holder.view.tv_id.text = kryawan.id.toString()
        holder.view.tv_nama.text = kryawan.nama
        holder.view.tv_nama.setOnClickListener{
            listener.onClick(kryawan)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(kryawan)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(kryawan)
        }
    }

    override fun getItemCount() = karyawann.size

    fun setData(list: List<tb_karyawan>){
        karyawann.clear()
        karyawann.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick(tbKaryawan: tb_karyawan)
        fun onUpdate(tbKaryawan: tb_karyawan)
        fun onDelete(tbKaryawan: tb_karyawan)
    }
}
