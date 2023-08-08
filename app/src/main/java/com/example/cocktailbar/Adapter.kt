package com.example.cocktailbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailbar.databinding.ItemRecyclerBinding

class Adapter(
    private val list: List<ItemViewModel>,
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.ivPhoto.setImageBitmap(Utils.stringToImg(item.img))
        holder.binding.tvHead.text = item.title
        holder.binding.ivPhoto.setOnClickListener { onClick(item.id) }
    }
}

class ViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

data class ItemViewModel(
    val id: Long,
    val img: String,
    val title: String
)