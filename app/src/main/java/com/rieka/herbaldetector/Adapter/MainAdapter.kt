package com.rieka.herbaldetector.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rieka.herbaldetector.Model.Tanaman
import com.rieka.herbaldetector.databinding.ListItemGalleryBinding


class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val listTanaman = ArrayList<Tanaman>()

    fun setTanaman(items: List<Tanaman>) {
        listTanaman.clear()
        listTanaman.addAll(items)
    }

    class ViewHolder(private val binding: ListItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tanaman: Tanaman) {
            with(binding) {
                tvNamaTanaman.text = tanaman.nama
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val binding = ListItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(listTanaman[position])
    }

    override fun getItemCount(): Int = listTanaman.size

}