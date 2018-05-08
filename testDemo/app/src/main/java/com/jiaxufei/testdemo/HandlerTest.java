package com.jiaxufei.testdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import com.jiaxufei.basicres.BaseActivity;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/1 下午8:20<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class HandlerTest extends BaseActivity {

  HandlerThread myHandlerThread;
  Handler handler;
  private BluetoothAdapter bluetoothAdapter;
  private BluetoothDevice bluetoothDevice;
  private  BluetoothGatt bluetoothGatt;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    myHandlerThread = new HandlerThread("test");
    myHandlerThread.start();
    handler = new Handler(myHandlerThread.getLooper()) {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //这里接收Handler发来的消息，运行在handler_thread线程中
      }
    };
    //在主线程给handler发送消息
    handler.sendEmptyMessage(1);
    new Thread(new Runnable() {
      @Override
      public void run() {
        //在子线程给handler发送消息
        handler.sendEmptyMessage(2);
      }
    });
    String name = bluetoothAdapter.getName();
    String mac = bluetoothAdapter.getAddress();
    if (bluetoothAdapter == null) {
      return;
    }
    if (bluetoothAdapter.isEnabled()) {
      bluetoothAdapter.enable();
    } else {
      bluetoothAdapter.disable();
    }

  }

  public void blueToothMethod() {
    final BluetoothAdapter.LeScanCallback callback = new LeScanCallback() {
      @Override
      public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

      }
    };
    bluetoothAdapter.stopLeScan(callback);
    bluetoothAdapter.startLeScan(callback);

    final BluetoothGattCallback callback1 = new BluetoothGattCallback() {
      @Override
      public void onCharacteristicRead(BluetoothGatt gatt,
          BluetoothGattCharacteristic characteristic,
          int status) {
        super.onCharacteristicRead(gatt, characteristic, status);

      }
    };
//    BluetoothGattService service = bluetoothGatt.getService(SERVICE_UUID);
//    BluetoothGattCharacteristic bluetoothGattCharacteristic = service.getCharacteristic(CHARACTER_UUID);
//    bluetoothGatt.readCharacteristic();


  }
}
