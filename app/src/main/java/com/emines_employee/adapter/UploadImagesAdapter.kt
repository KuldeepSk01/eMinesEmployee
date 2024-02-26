package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemUploadImagesLayoutBinding
import com.emines_employee.model.UploadImages

class UploadImagesAdapter(val list: MutableList<UploadImages>, val context: Context) :
    RecyclerView.Adapter<UploadImagesAdapter.UploadImagesVM>() {
    class UploadImagesVM(val b: ItemUploadImagesLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImagesVM {
        return UploadImagesVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_upload_images_layout, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: UploadImagesVM, position: Int) {
        val images = list[position]
        holder.b.apply {
            ivItemUploadImagesCategory.setImageBitmap(images.bitmap)
            tvItemUploadImagesCategory.text = images.title
        }
    }
}