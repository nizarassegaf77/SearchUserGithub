package com.nizar.searchusergithub.mvp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.nizar.searchusergithub.R
import com.nizar.searchusergithub.model.Item

/**
 * Created by Nizar Assegaf on 19,July,2020
 */

class SearchAdapter(private val items: List<Item>, private val context: Context) : RecyclerView.Adapter<SearchAdapter.MyViewHolder?>() {

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
        holder.name.text = items[position].name
        Glide.with(context)
            .load(items[position].avatar)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_image_black_24dp).diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC)
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var avatar: ImageView = itemView.findViewById(R.id.avatar)

    }

}


