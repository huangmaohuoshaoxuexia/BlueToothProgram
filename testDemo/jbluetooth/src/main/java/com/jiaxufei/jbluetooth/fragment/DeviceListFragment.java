package com.jiaxufei.jbluetooth.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.jiaxufei.jbluetooth.BlueToothActivity;
import com.jiaxufei.jbluetooth.ClientThread;
import com.jiaxufei.jbluetooth.receiver.MyBtReceiver;
import com.jiaxufei.jbluetooth.adapter.MyListAdapter;
import com.jiaxufei.jbluetooth.Params;
import com.jiaxufei.jbluetooth.R;
import com.jiaxufei.jbluetooth.ServerThread;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/4 下午3:47<br/>
 *
 * <p>
 * 蓝牙设备列表
 * </p>
 */
public class DeviceListFragment extends Fragment {

  final String TAG = "DeviceListFragment";
  ListView listView;
  MyListAdapter myListAdapter;
  List<BluetoothDevice> deviceList = new ArrayList();
  ClientThread clientThread;
  ServerThread serverThread;
  BluetoothAdapter bluetoothAdapter;
  MyBtReceiver btReceiver;
  BlueToothActivity blueToothActivity;
  Handler uiHandler;
  IntentFilter intentFilter;

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case Params.MY_PERMISSION_REQUEST_CONSTANT:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //运行时权限已授权
        }
        return;
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    if (bluetoothAdapter == null) {
      Log.e(TAG, "--------------- 不支持蓝牙");
      getActivity().finish();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.btooth_layout_bt_list, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    listView = view.findViewById(R.id.lvDevice);
    myListAdapter = new MyListAdapter(getActivity());
    listView.setAdapter(myListAdapter);
    myListAdapter.notifyDataSetChanged();

  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    blueToothActivity = (BlueToothActivity) getActivity();
    uiHandler = blueToothActivity.getUiHandler();
  }

  @Override
  public void onResume() {
    super.onResume();
    // 蓝牙未打开，询问打开
    if (!bluetoothAdapter.isEnabled()) {
      Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(turnOnBtIntent, Params.REQUEST_ENABLE_BT);
    }

    intentFilter = new IntentFilter();
    btReceiver = new MyBtReceiver(getActivity(),deviceList,myListAdapter);
    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
    getActivity().registerReceiver(btReceiver, intentFilter);

    // 蓝牙已开启
    if (bluetoothAdapter.isEnabled()) {
      showBondDevice();
      // 默认开启服务线程监听
      if (serverThread != null) {
        serverThread.cancel();
      }
      Log.e(TAG, "-------------- new server thread");
      serverThread = new ServerThread(bluetoothAdapter, uiHandler);
      new Thread(serverThread).start();
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 关闭服务器监听
        if (serverThread != null) {
          serverThread.cancel();
          serverThread=null;
          Log.e(TAG , "---------------client item click , cancel server thread ," +
              "server thread is null");
        }
        BluetoothDevice device = deviceList.get(position);
        // 开启客户端线程，连接点击的远程设备
        clientThread = new ClientThread(bluetoothAdapter, device, uiHandler);
        new Thread(clientThread).start();

        // 通知 ui 连接的服务器端设备
        Message message = new Message();
        message.what = Params.MSG_CONNECT_TO_SERVER;
        message.obj = device;
        uiHandler.sendMessage(message);

      }
    });

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    getActivity().unregisterReceiver(btReceiver);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.enable_visibility:
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        enableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 600);
        startActivityForResult(enableIntent, Params.REQUEST_ENABLE_VISIBILITY);
        break;
      case R.id.discovery:
        if (bluetoothAdapter.isDiscovering()) {
          bluetoothAdapter.cancelDiscovery();
        }
        if (Build.VERSION.SDK_INT >= 6.0) {
          ActivityCompat.requestPermissions(getActivity(),
              new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
              Params.MY_PERMISSION_REQUEST_CONSTANT);
        }

        bluetoothAdapter.startDiscovery();
        break;
      case R.id.disconnect:
        bluetoothAdapter.disable();
        deviceList.clear();
        myListAdapter.notifyDataSetChanged();
        toast("蓝牙已关闭");
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case Params.REQUEST_ENABLE_BT: {
        if (resultCode == RESULT_OK) {
          showBondDevice();
        }
        break;
      }
      case Params.REQUEST_ENABLE_VISIBILITY: {
        if (resultCode == 600) {
          toast("蓝牙已设置可见");
        } else if (resultCode == RESULT_CANCELED) {
          toast("蓝牙设置可见失败,请重试");
        }
        break;
      }
    }
  }

  /**
   * 向 socket 写入发送的数据
   */
  public void writeData(String dataSend) {
    if (serverThread != null) {
      serverThread.write(dataSend);
    } else if (clientThread != null) {
      clientThread.write(dataSend);
    }
  }

  /**
   * 用户打开蓝牙后，显示已绑定的设备列表
   */
  private void showBondDevice() {
    deviceList.clear();
    Set<BluetoothDevice> tmp = bluetoothAdapter.getBondedDevices();
    for (BluetoothDevice d : tmp) {
      deviceList.add(d);
    }
    myListAdapter.setDatas(deviceList);
    myListAdapter.notifyDataSetChanged();
  }

  /**
   * Toast 提示
   */
  public void toast(String str) {
    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
  }
}
