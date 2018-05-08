package com.jiaxufei.jbluetooth.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jiaxufei.jbluetooth.R;
import java.util.ArrayList;
import java.util.List;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/4 下午6:15<br/>
 *
 * <p>
 * 蓝牙列表适配器
 * </p>
 */
public class MyListAdapter extends BaseAdapter {

  List<BluetoothDevice> deviceList = new ArrayList<>();
  Activity ctx;

  public MyListAdapter(Activity activity) {
    this.ctx = activity;

  }

  public void setDatas(List<BluetoothDevice> deviceList) {
    this.deviceList = deviceList;
  }

  @Override
  public int getCount() {
    return deviceList.size();
  }

  @Override
  public Object getItem(int position) {
    return deviceList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView == null) {
      convertView = ctx.getLayoutInflater()
          .inflate(R.layout.btooth_layout_item_bt_device, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
      viewHolder.deviceMac = (TextView) convertView.findViewById(R.id.device_mac);
      viewHolder.deviceState = (TextView) convertView.findViewById(R.id.device_state);
      convertView.setTag(viewHolder);

    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    int code = deviceList.get(position).getBondState();
    String name = deviceList.get(position).getName();
    String mac = deviceList.get(position).getAddress();
    String state;
    if (name == null || name.length() == 0) {
      name = "未命名设备";
    }
    if (code == BluetoothDevice.BOND_BONDED) {
      state = "ready";
      viewHolder.deviceState.setTextColor(ctx.getResources().getColor(R.color.btooth_green));
    } else {
      state = "new";
      viewHolder.deviceState.setTextColor(ctx.getResources().getColor(R.color.btooth_red));
    }
    if (mac == null || mac.length() == 0) {
      mac = "未知 mac 地址";
    }
    viewHolder.deviceName.setText(name);
    viewHolder.deviceMac.setText(mac);
    viewHolder.deviceState.setText(state);
    return convertView;
  }

  static class ViewHolder {

    private TextView deviceName;
    private TextView deviceMac;
    private TextView deviceState;
  }
}
