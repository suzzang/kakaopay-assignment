package suzzang.assignment.com.kakaopayassignment.mvp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_image_detail.*
import suzzang.assignment.com.kakaopayassignment.adapter.ImageDetailFragmentAdapter
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images
import org.jsoup.Jsoup
import java.io.IOException
import android.os.AsyncTask
import suzzang.assignment.com.kakaopayassignment.R
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.graphics.Typeface
import android.net.Uri


class ImageDetailActivity : AppCompatActivity(){



    lateinit var imgList: ArrayList<Images>
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        getItemFromIntent()
        initView()
        initPager()


        btn_cancel.setOnClickListener {
            finish()
        }

    }

    //전달받은 데이터 값 설정
    private fun getItemFromIntent(){

        position =intent.getIntExtra("position",0)
        imgList = intent.getSerializableExtra("item") as ArrayList<Images>

    }
    //뷰 초기화 ( 초기 데이터 설정 )
    private fun initView(){

        title_image.paintFlags = title_image.paintFlags or UNDERLINE_TEXT_FLAG
        title_image.setTypeface(null, Typeface.BOLD)
        name_collection.text = imgList[position].collection
        tv_width.text = imgList[position].width.toString()
        tv_height.text = imgList[position].height.toString()
        if(imgList[position].display_sitename.equals("")){
            division.text = ""
            sitename.text =""
        }else{
            sitename.text = imgList[position].display_sitename
        }

        var test = JsoupAsyncTask(imgList[position].doc_url)
        test.execute()

        var datetime = imgList[position].datetime
        datetime = datetime.substring(0,10)
        datetime = datetime.replace("-",".")
        tv_datetime.text = datetime


    }
    //뷰페이저 초기화
    private fun initPager(){

        val imageDetailFragmentAdapter = ImageDetailFragmentAdapter(supportFragmentManager)

        //뷰페이저의 프래그먼트 아이템개수만큼 할당
        for(i in imgList.iterator()){
            val idx = imgList.indexOf(i)
            val imageDetailFragment = ImageDetailFragment()
            val bundle = Bundle()
            bundle.putInt("position",idx)
            bundle.putSerializable("item",imgList[idx])
            imageDetailFragment.arguments = bundle
            imageDetailFragmentAdapter.addFragment(imageDetailFragment)

        }
        imageDetailFragmentAdapter.notifyDataSetChanged()


        //뷰페이저 설정
        viewPager.adapter = imageDetailFragmentAdapter
        viewPager.clipToPadding = false
        viewPager.setCurrentItem(position,false)


        //뷰페이저 페이지 이벤트처리 : 뷰페이저의 각 포지션에 해당하는 정보로 바꿔주기
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                name_collection.text = imgList[p0].collection
                tv_width.text = imgList[p0].width.toString()
                tv_height.text = imgList[p0].height.toString()
                var datetime = imgList[p0].datetime
                datetime = datetime.substring(0,10)
                datetime = datetime.replace("-",".")
                tv_datetime.text = datetime
                if(imgList[p0].display_sitename.equals("")){
                    division.text = ""
                    sitename.text =""
                }else{
                    division.text = " > "
                    sitename.text = imgList[p0].display_sitename
                }

                var test = JsoupAsyncTask(imgList[p0].doc_url)
                test.execute()

                //이미지저장버튼 눌렀을때 해당 이미지 url을 이용해 새 activty 호출
                btn_img_save.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imgList[p0].image_url))
                    startActivity(intent)
                }
                //이미지타이틀을 눌렀을때 해당 이미지의 doc_url을 이용해 새 activty 호출
                title_image.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse(imgList[p0].doc_url))
                    startActivity(intent)
                }


            }

        })

    }


    //ogTag 를 이용하여 JSON의 doc_url을 통해 이미지 타이틀 가져오기
    inner class JsoupAsyncTask internal constructor(private var docUrl : String) : AsyncTask<Void, Void, Any?>() {

        var flag = 0
        override fun doInBackground(vararg params: Void): Any? {
            try {

                val con = Jsoup.connect(docUrl)
                val doc = con.get()
                val ogTags = doc.select("meta[property^=og:]")
                if (ogTags.size <= 0) {
                    changeTextView("해당 페이지로 이동")
                    return null
                }

                for (i in 0 until ogTags.size) {
                    val tag = ogTags[i]

                    val text = tag.attr("property")
                    if("og:title".equals(text)){
                        changeTextView(tag.attr("content"))
                        flag = 1
                    }
                }
                if(flag == 0){
                    changeTextView("해당 페이지로 이동")
                }

            } catch (e: IOException) {
                e.printStackTrace() //해당 doc_url이 유효하지 않을 경우
                changeTextView("해당 페이지로 이동")
            }

            return null
        }
        //UI변경을 위해
        fun changeTextView(text: String) {
            runOnUiThread { title_image.text = text }
        }

    }





}
