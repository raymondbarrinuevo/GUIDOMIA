package com.example.guidomia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.databinding.ItemProsAndConsBinding

class ProsAndConsRecyclerViewAdapter :
    RecyclerView.Adapter<ProsAndConsViewHolder>() {

    private val prosCons = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsAndConsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProsAndConsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_pros_and_cons, parent, false)
        return ProsAndConsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProsAndConsViewHolder, position: Int) {
        holder.bind(prosCons[position])
    }

    override fun getItemCount(): Int {
        return prosCons.size
    }

    fun setList(prosCons: List<String>) {
        this.prosCons.clear()
        this.prosCons.addAll(prosCons)
    }

}

class ProsAndConsViewHolder(val binding: ItemProsAndConsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(prosCons: String) {
        binding.tvProsCons.text = prosCons
    }
}