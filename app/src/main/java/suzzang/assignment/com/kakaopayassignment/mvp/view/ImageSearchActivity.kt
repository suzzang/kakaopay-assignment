package suzzang.assignment.com.kakaopayassignment.mvp.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_search.*
import suzzang.assignment.com.kakaopayassignment.adapter.ImageSearchAdapter
import suzzang.assignment.com.kakaopayassignment.base.BaseActivity
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images
import suzzang.assignment.com.kakaopayassignment.mvp.presenter.ImageSearchContract
import suzzang.assignment.com.kakaopayassignment.mvp.presenter.ImageSearchPresenter
import kotlin.collections.ArrayList
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import suzzang.assignment.com.kakaopayassignment.R


class ImageSearchActivity : BaseActivity(), ImageSearchContract.View, ImageSearchAdapter.OnItemClickListener {

    lateinit var imageSearchAdapter: ImageSearchAdapter

    lateinit var imageSearchPresenter: ImageSearchPresenter

    lateinit var imm:InputMethodManager

    lateinit var keyword : String

    var isEnd = false
    var isBtnClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)


        filter_group.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.btn_accuracy){
                if(isBtnClicked){
                    setSearching(0)
                }

            }else{
                if(isBtnClicked){
                    setSearching(0)
                }
            }
            setAdapter()
        }

        btn_image_search.setOnClickListener{
            isBtnClicked = true

            keyword = et_image_search.text.toString()

            setSearching(0)//첫 요청 : 파라미터를 0으로 보낸다

            hideKeyboard()
            et_image_search.isCursorVisible = false

            setAdapter()

        }
        et_image_search.setOnClickListener { et_image_search.isCursorVisible = true }

        pagination()

        setAdapter()

        imageSearchPresenter.setView(this)

    }




    override fun initPresenter() {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //키보드 내리기위해서
        imageSearchPresenter = ImageSearchPresenter()
    }

    private fun setSearching(flag:Int){
        var sort = "accuracy"
        if(btn_accuracy.isChecked){
            sort = "accuracy"
        }else{
            sort = "recency"
        }
        imageSearchPresenter.getSearchList(keyword,flag,sort)

    }
    private fun pagination(){
        rcv_image_search.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var lastVisibleItemPosition = (rcv_image_search.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                var itemTotalCount = rcv_image_search.adapter!!.itemCount

                if(lastVisibleItemPosition == itemTotalCount-1){

                    if(!isEnd){ //페이지가 끝이아닐때 계속 요청
                        setSearching(1) // 첫 요청이 아닐 떄 : 파라미터를 1로 보낸다
                    }else{ // 끝이면 더 이상 요청하지 않음

                    }
                }

            }

        })

    }

    // 리사이클러뷰 및 리사이클러뷰 어댑터 설정
    private fun setAdapter(){

        imageSearchAdapter = ImageSearchAdapter(Glide.with(this))

        rcv_image_search.layoutManager = GridLayoutManager(this,3)
        rcv_image_search.adapter = imageSearchAdapter

        imageSearchAdapter.setClickListener(this)

    }

    private fun hideKeyboard()
    {
        imm.hideSoftInputFromWindow( et_image_search.windowToken, 0);
    }

    // 리사이클러뷰 아이템 클릭 처리
    override fun onClick(position: Int,imgList: ArrayList<Images>) {

        val intent = Intent(this, ImageDetailActivity::class.java)
        intent.putExtra("position",position)
        intent.putExtra("item",imgList)
        startActivity(intent)
    }



    override fun onDestroy() {
        super.onDestroy()
        imageSearchPresenter.dropView()
    }

    override fun showSearchList(imageList: ArrayList<Images>,isEnd:Boolean) {
        imageSearchAdapter.setItems(imageList)
        this.isEnd = isEnd
    }

    override fun showError(error: String) {
        isBtnClicked = false
        Toast.makeText(this,"검색어를 입력해주세요!",Toast.LENGTH_SHORT).show()
        Log.e("ERROR",error)
    }
}
