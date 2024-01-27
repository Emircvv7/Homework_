package com.example.homework_3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.homework_3.databinding.ItemImageBinding

class PixaAdapter(private var list: ArrayList<ImageModel>) :
    RecyclerView.Adapter<PixaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PixaViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun updateData(newList: ArrayList<ImageModel>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }
}

class PixaViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(image: ImageModel) {
        with(binding) {
            likeTv.text = image.likes.toString()
            PixaImage.load(image.largeImageURL) {
                transformations(CircleCropTransformation())
                error(R.drawable.ic_error)
            }
        }
    }
}
