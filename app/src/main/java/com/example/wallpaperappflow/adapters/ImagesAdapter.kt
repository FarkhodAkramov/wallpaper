package com.example.wallpaperappflow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperappflow.databinding.ImageItemBinding
import com.example.wallpaperappflow.databinding.ItemProgressBinding
import com.example.wallpaperappflow.model.Hit
import com.squareup.picasso.Picasso


class ImagesAdapter(val listener:OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_DATA = 1
    var ITEM_LOAD = 0

    var dataList = ArrayList<Hit>()
    var isLoadingAdded = false

    inner class DataVh(var imageItemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(imageItemBinding.root) {

        fun onBind(hit: Hit) {
            Picasso.get().load(hit.webformatURL).into(imageItemBinding.imageView, object: com.squareup.picasso.Callback {
                override fun onSuccess() {


                }

                override fun onError(e: java.lang.Exception?) {
                    //do smth when there is picture loading error
                }
            })
            imageItemBinding.root.setOnClickListener {
                listener.OnItemClick(hit.largeImageURL)
            }
        }
    }

    inner class ProgressVh(itemProgressBinding: ItemProgressBinding) :
        RecyclerView.ViewHolder(itemProgressBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_LOAD) {
            return ProgressVh(
                ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return DataVh(
                ImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProgressVh) {
            val progressVh = holder as ProgressVh

        } else if (holder is DataVh) {
            val dataVh = holder as DataVh
            dataVh.onBind(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        if ((position == dataList.size - 1 && isLoadingAdded)) {
            return ITEM_LOAD
        }
        return ITEM_DATA
    }

    fun addListData(list: List<Hit>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoadingAdded = true
    }

    fun removeLoading() {
        isLoadingAdded = false
    }
    interface OnClickListener{
        fun OnItemClick(url:String)
    }
}