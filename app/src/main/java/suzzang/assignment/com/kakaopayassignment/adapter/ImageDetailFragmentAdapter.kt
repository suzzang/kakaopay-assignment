package suzzang.assignment.com.kakaopayassignment.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class ImageDetailFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    var fragments : ArrayList<Fragment> = ArrayList()


    override fun getItem(p0: Int): Fragment {
        return fragments[p0]
    }
    override fun getCount(): Int {
        return fragments.size

    }
    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

}