package suzzang.assignment.com.kakaopayassignment.mvp.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_search.*
import suzzang.assignment.com.kakaopayassignment.R
import suzzang.assignment.com.kakaopayassignment.adapter.ImageSearchAdapter
import suzzang.assignment.com.kakaopayassignment.base.BaseActivity
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images
import suzzang.assignment.com.kakaopayassignment.mvp.presenter.ImageSearchContract
import suzzang.assignment.com.kakaopayassignment.mvp.presenter.ImageSearchPresenter
import kotlin.collections.ArrayList



class ImageSearchActivity : BaseActivity(), ImageSearchContract.View {


    lateinit var imageSearchAdapter: ImageSearchAdapter


    lateinit var imageSearchPresenter: ImageSearchPresenter

    lateinit var keyword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)



        btn_image_search.setOnClickListener{
            keyword = et_image_search.text.toString();

            setSearching(0)//첫요청
            imageSearchAdapter = ImageSearchAdapter(Glide.with(this))

            rcv_image_search.layoutManager = GridLayoutManager(this,3)
            rcv_image_search.adapter = imageSearchAdapter

            imageSearchPresenter.setView(this)
        }
        pagination()


        imageSearchAdapter = ImageSearchAdapter(Glide.with(this))

        rcv_image_search.layoutManager = GridLayoutManager(this,3)
        rcv_image_search.adapter = imageSearchAdapter

        imageSearchPresenter.setView(this)

    }





    override fun initPresenter() {
        imageSearchPresenter = ImageSearchPresenter()
    }

    private fun setSearching(flag:Int){
        imageSearchPresenter.getSearchList(keyword,flag)

    }


    private fun pagination(){
        rcv_image_search.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("더ㅚ냐","???")

                var lastVisibleItemPosition = (rcv_image_search.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                var itemTotalCount = rcv_image_search.adapter!!.itemCount
                Log.d("더ㅚ냐",lastVisibleItemPosition.toString())
                Log.d("더ㅚ냐", "itemtotal$itemTotalCount")
                if(lastVisibleItemPosition == itemTotalCount-1){
                    // 다시 요청
                    setSearching(1)
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


    }

    override fun showSearchList(imageList: ArrayList<Images>,isEnd:Boolean) {
        imageSearchAdapter.setItems(imageList)
    }

    override fun showError(error: String) {
    }
}
