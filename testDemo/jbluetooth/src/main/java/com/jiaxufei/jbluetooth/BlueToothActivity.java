package com.jiaxufei.jbluetooth;


import static android.content.ContentValues.TAG;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;
import com.jiaxufei.basicres.BaseActivity;
import com.jiaxufei.jbluetooth.adapter.MyPagerAdapter;
import com.jiaxufei.jbluetooth.fragment.DataTransFragment;
import com.jiaxufei.jbluetooth.fragment.DeviceListFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/4 上午11:08<br/>
 *
 * <p>
 * 蓝牙主页
 * </p>
 */
public class BlueToothActivity extends BaseActivity {

  TabLayout tabLayout;
  ViewPager viewPager;
  MyPagerAdapter myPagerAdapter;
  BluetoothAdapter bluetoothAdapter;
  String[] titleList = new String[]{"设备列表", "数据传输"};
  List<Fragment> fragmentList = new ArrayList<>();
  DeviceListFragment deviceListFragment;
  DataTransFragment dataTransFragment;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btooth_main_activity);
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    initView();
  }

  private void initView() {
    tabLayout = findViewById(R.id.tab_layout);
    viewPager = findViewById(R.id.view_pager);
    tabLayout.addTab(tabLayout.newTab().setText(titleList[0]));
    tabLayout.addTab(tabLayout.newTab().setText(titleList[1]));

    deviceListFragment = new DeviceListFragment();
    dataTransFragment = new DataTransFragment();
    fragmentList.add(deviceListFragment);
    fragmentList.add(dataTransFragment);

    myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
    viewPager.setAdapter(myPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }


  public void toast(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }

  /**
   * 返回uiHandler
   * @return
   */
  public Handler getUiHandler() {
    return uiHandler;
  }

  Handler uiHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {

      switch (msg.what) {
        case Params.MSG_REV_A_CLIENT:
          Log.e(TAG, "--------- uihandler set device name, go to data frag");
          BluetoothDevice clientDevice = (BluetoothDevice) msg.obj;
          dataTransFragment.receiveClient(clientDevice);
          viewPager.setCurrentItem(1);
          break;
        case Params.MSG_CONNECT_TO_SERVER:
          Log.e(TAG, "--------- uihandler set device name, go to data frag");
          BluetoothDevice serverDevice = (BluetoothDevice) msg.obj;
          dataTransFragment.connectServer(serverDevice);
          viewPager.setCurrentItem(1);
          break;
        case Params.MSG_SERVER_REV_NEW:
          String newMsgFromClient = msg.obj.toString();
          dataTransFragment.updateDataView(newMsgFromClient, Params.REMOTE);
          break;
        case Params.MSG_CLIENT_REV_NEW:
          String newMsgFromServer = msg.obj.toString();
          dataTransFragment.updateDataView(newMsgFromServer, Params.REMOTE);
          break;
        case Params.MSG_WRITE_DATA:
          String dataSend = msg.obj.toString();
          dataTransFragment.updateDataView(dataSend, Params.ME);
          deviceListFragment.writeData(dataSend);
          break;

      }
    }
  };

}
