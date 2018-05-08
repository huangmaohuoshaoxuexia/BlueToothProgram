package com.jiaxufei.gooddetailcomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jiaxufei.basicres.BaseActivity;
import com.jiaxufei.gooddetailcomponent.R;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-03 下午3:07<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class GoodDetailActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_good_detail);
  }
}
