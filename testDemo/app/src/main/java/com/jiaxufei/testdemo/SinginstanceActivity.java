package com.jiaxufei.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.jiaxufei.basicres.BaseActivity;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-04-10 下午1:54<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class SinginstanceActivity extends BaseActivity {
private TextView tv_task;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sing_instance);
    tv_task=(TextView)findViewById(R.id.tv_task);
    tv_task.setText("current task id: " + this.getTaskId());
    tv_task.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(SinginstanceActivity.this,MainActivity.class));
      }
    });
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }
}
