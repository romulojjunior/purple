package app.appk.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentAdapter(
        fm: FragmentManager?,
        var fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override
    fun getItem(position: Int): Fragment = fragments[position]

    override
    fun getCount(): Int = fragments.size
}