package com.jiaxufei.componentservice.goods.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: 贾旭飞(<a href="mailto:jiaxufei@danlu.com">jiaxufei@danlu.com</a>)<br/>
 * version: 1.0.0<br/>
 * since: 2018-03-23 上午11:18<br/>
 *
 * <p>
 * 内容描述区域
 * </p>
 */
public class PersonMsg implements Parcelable {

  private String name = null;
  private int age = 0;

  public PersonMsg() {
  }

  protected PersonMsg(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  protected PersonMsg(Parcel in) {
    name = in.readString();
    age = in.readInt();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public static final Creator<PersonMsg> CREATOR = new Creator<PersonMsg>() {
    @Override
    public PersonMsg createFromParcel(Parcel in) {
      return new PersonMsg(in);
    }

    @Override
    public PersonMsg[] newArray(int size) {
      return new PersonMsg[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(age);
  }
}
