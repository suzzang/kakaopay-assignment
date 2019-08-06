package suzzang.assignment.com.kakaopayassignment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import suzzang.assignment.com.kakaopayassignment.R
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images


class ImageSearchAdapter(var requsetManager: RequestManager) : RecyclerView.Adapter<ImageSearchAdapter.ViewHolder>() {
    private  var imgList: ArrayList<Images> = ArrayList()
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.rcvitem_image_search, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = imgList.size

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val idx = holder.adapterPosition

        requsetManager.load(imgList[position].thumbnail_url).into(holder.thumbnail)

        holder.itemView.setOnClickListener { v: View? -> listener.onClick(idx,imgList) }

    }

    fun setItems(items: ArrayList<Images>) {
        for ( i in items.iterator()) {
            var idx = items.indexOf(i)

            this.imgList.add(Images(items[idx].thumbnail_url,
                items[idx].collection,items[idx].display_sitename,
                items[idx].doc_url,items[idx].image_url,items[idx].width,items[idx].height,items[idx].datetime))
        }
        notifyDataSetChanged()
    }

    fun setClickListener(listener: OnItemClickListener){
        this.listener = listener
    }


    interface OnItemClickListener{
        fun onClick(position: Int,imgList: ArrayList<Images>)
    }

    // 리사이클러뷰 뷰 홀더 inner class
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         var thumbnail = itemView.findViewById(R.id.rcvitem_img) as ImageView

    }




}


