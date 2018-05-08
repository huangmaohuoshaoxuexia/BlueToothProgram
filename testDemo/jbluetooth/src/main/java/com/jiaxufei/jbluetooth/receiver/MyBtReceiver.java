package com.jiaxufei.jbluetooth.receiver;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.jiaxufei.jbluetooth.adapter.MyListAdapter;
import java.util.List;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/7 上午11:07<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class MyBtReceiver extends BroadcastReceiver {

  private Activity ctx;
  private List<BluetoothDevice> deviceList;
  private MyListAdapter listAdapter;

  public MyBtReceiver(Activity activity, List<BluetoothDevice> deviceList,
      MyListAdapter listAdapter) {
    this.ctx = activity;
    this.deviceList = deviceList;
    this.listAdapter = listAdapter;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
      toast("开始搜索 ...");
    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
      toast("搜索结束");
    } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
      BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
      if (isNewDevice(device)) {
        deviceList.add(device);
        listAdapter.notifyDataSetChanged();
        Log.e(TAG, "---------------- " + device.getName());
      }
    }
  }

  /**
   * Toast 提示
   */
  public void toast(String str) {
    Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
  }

  /**
   * 判断搜索的设备是新蓝牙设备，且不重复
   */
  private boolean isNewDevice(BluetoothDevice device) {
    boolean repeatFlag = false;
    for (BluetoothDevice d : deviceList) {
      if (d.getAddress().equals(device.getAddress())) {
        repeatFlag = true;
      }
    }
    //不是已绑定状态，且列表中不重复
    return device.getBondState() != BluetoothDevice.BOND_BONDED && !repeatFlag;
  }
}