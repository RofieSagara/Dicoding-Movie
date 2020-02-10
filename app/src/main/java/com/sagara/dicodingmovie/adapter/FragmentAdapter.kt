package com.sagara.dicodingmovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

//
// Created by Rofie Sagara on 9/23/19.
// Copyright (c) 2019 Sagara. All rights reserved.
//
class FragmentAdapter(
    private val fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList = mutableListOf<Fragment>()
    private val mFragmentTitleList = mutableListOf<String>()

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun invalidate(){
        mFragmentList.forEach {
            fragmentManager.beginTransaction().remove(it).commitNow()
        }
        clear()
    }

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun clear(){
        mFragmentList.clear()
        mFragmentTitleList.clear()
    }

}
