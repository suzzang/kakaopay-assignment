package suzzang.assignment.com.kakaopayassignment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images
import android.R





class ImageSearchAdapter(var requsetManager: RequestManager) : RecyclerView.Adapter<ImageSearchAdapter.ViewHolder>() {
    private  var imgList: ArrayList<Images> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(suzzang.assignment.com.kakaopayassignment.R.layout.rcvitem_image_search, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = imgList.size

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        requsetManager.load(imgList[position].thumbnail_url).into(holder.thumbnail)
    }

    fun setItems(items: ArrayList<Images>) {
        for ( i in items.iterator()) {
            var idx = items.indexOf(i)
            this.imgList.add(Images(items[idx].thumbnail_url))
        }

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         var thumbnail = itemView!!.findViewById(suzzang.assignment.com.kakaopayassignment.R.id.rcvitem_img) as ImageView

    }
}

