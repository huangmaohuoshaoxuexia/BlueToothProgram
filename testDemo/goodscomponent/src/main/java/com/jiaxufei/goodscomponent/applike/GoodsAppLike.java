package com.jiaxufei.goodscomponent.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-03 上午9:46<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class GoodsAppLike implements IApplicationLike {

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
