// IAlipayService.aidl
package com.jiaxufei.fistkotlinprogram;
import com.jiaxufei.fistkotlinprogram.PersonMsg;

// Declare any non-default types here with import statements

interface IAlipayService {
  int callPayInService(int account, int money);
  PersonMsg name();
  String getContentDetailByNum(String num);
}
