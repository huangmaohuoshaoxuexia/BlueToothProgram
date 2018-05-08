package com.jiaxufei.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/1 下午8:41<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class IntentServiceTest extends Service {

  public static final String TAG = IntentServiceTest.class.getSimpleName();

  public IntentServiceTest() {

  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
 
}
