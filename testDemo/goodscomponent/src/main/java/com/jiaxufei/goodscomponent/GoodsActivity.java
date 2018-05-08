package com.jiaxufei.goodscomponent;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.jiaxufei.basicres.BaseActivity;
import com.jiaxufei.componentservice.goods.bean.Goods;
import com.jiaxufei.goodscomponent.R;
import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-03 上午11:27<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
@RouteNode(path = "/goodsList", desc = "商品列表页面")
public class GoodsActivity extends BaseActivity {

  @Autowired
  int goodsNum;

  @Autowired
  Goods goods;

  TextView tvNum;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.goods_activity);
    tvNum=(TextView)findViewById(R.id.tv_num);

    tvNum.setText(goods.getName()+goodsNum+"");
    Intent intent=new Intent();
    intent.putExtra("result","跳转商品页成功");
    setResult(RESULT_OK,intent);
  }

}
