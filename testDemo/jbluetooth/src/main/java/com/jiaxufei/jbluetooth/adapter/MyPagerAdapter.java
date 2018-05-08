package com.jiaxufei.jbluetooth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/4 下午3:36<br/>
 *
 * <p>
 * pager适配器
 * </p>
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

  List<Fragment> fragmentList;
  String[] titleList;

  public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,String[] titleList) {
    super(fm);
    this.fragmentList=fragmentList;
    this.titleList=titleList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return titleList[position];
  }
}
