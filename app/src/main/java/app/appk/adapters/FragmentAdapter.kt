package app.appk.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(
        fm: FragmentManager?,
        var fragments: List<Fragment>) : FragmentStatePagerAdapter(fm) {

    override
    fun getItem(position: Int): Fragment = fragments[position]

    override
    fun getCount(): Int = fragments.size
}