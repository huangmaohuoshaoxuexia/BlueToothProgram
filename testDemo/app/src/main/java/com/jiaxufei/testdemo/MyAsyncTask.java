package com.jiaxufei.testdemo;


import android.os.AsyncTask;

/**
 * author: jiaxufei(<a href="jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018/4/28 上午11:23<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class MyAsyncTask extends AsyncTask<String ,Integer,Long>{


  @Override
  protected Long doInBackground(String... strings) {
    return null;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
    //这里做进度更新界面
  }

  @Override
  protected void onPostExecute(Long aLong) {
    super.onPostExecute(aLong);
    //这里做完成界面操作
  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
    //如果被停止，则做停止操作
  }
}
