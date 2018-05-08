package com.jiaxufei.basicres;

import android.app.Application;
import android.support.annotation.Nullable;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-03 上午10:04<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class BaseApplication extends Application {
private static BaseApplication mAppContext;

  @Override
  public void onCreate() {
    super.onCreate();
    mAppContext=this;
  }

  @Nullable
  public Application getAppContext() {
    return mAppContext;
  }
}
