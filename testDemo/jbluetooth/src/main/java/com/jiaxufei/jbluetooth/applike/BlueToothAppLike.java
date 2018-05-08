package com.jiaxufei.jbluetooth.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/5/4 上午11:56<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class BlueToothAppLike implements IApplicationLike {

  UIRouter uiRouter = UIRouter.getInstance();

  @Override
  public void onCreate() {
    uiRouter.registerUI("goods");
  }

  @Override
  public void onStop() {
    uiRouter.unregisterUI("goods");
  }
}