package com.jiaxufei.basicres;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.luojilab.component.componentlib.service.AutowiredService;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-02 上午11:25<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //自动装载功能
    AutowiredService.Factory.getInstance().create().autowire(this);
  }


}
