package suzzang.assignment.com.kakaopayassignment.mvp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_image_detail.view.*
import suzzang.assignment.com.kakaopayassignment.R
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images



class ImageDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail,container,false)

        if(arguments!=null){
            var args = arguments
            var imgData: Images= args!!.getSerializable("item") as Images
            Glide.with(this).load(imgData.image_url)
                .into(view.img_detail)

        }
        return view
    }


}