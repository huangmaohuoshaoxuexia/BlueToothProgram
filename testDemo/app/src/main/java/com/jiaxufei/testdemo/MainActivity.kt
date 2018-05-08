package com.jiaxufei.testdemo


import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jiaxufei.basicres.BaseActivity
import com.jiaxufei.componentservice.goods.bean.Goods
import com.jiaxufei.fistkotlinprogram.IAlipayService
import com.jiaxufei.fistkotlinprogram.PersonMsg
import com.luojilab.component.componentlib.router.ui.UIRouter
import com.luojilab.component.componentlib.service.JsonService
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : BaseActivity() {

    var tvWord: TextView? = null//点击的事件
    var btnSearch: Button? = null
    var etNum: EditText? = null
    var num: String? = null
    var btnGoods: Button? = null//商品
    private var iServer: IAlipayService? = null
    val REQUEST_CODE = 7777
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            iServer = IAlipayService.Stub.asInterface(service)
            Log.d("onServiceConnected", "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            iServer = null
            Log.d("onServiceDisconnected", "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvWord = findViewById<TextView>(R.id.tv_text) as TextView
        btnSearch = findViewById<Button>(R.id.btn_search) as Button
        etNum = findViewById<EditText>(R.id.et_num) as EditText
        btnGoods = findViewById<Button>(R.id.btn_goods) as Button
      //  tvWord!!.text = "current task id: " + this.getTaskId()
        bindService()
        btnGoods!!.setOnClickListener {
            // goToGoodsActivity()
            //goToGoodsActivityForResult()
           // startActivity(Intent(this, SinginstanceActivity::class.java))
            Toast . makeText (this@MainActivity, "点击了商品", Toast.LENGTH_SHORT).show()
        }
        btnSearch!!.setOnClickListener {
            if (iServer != null) {
                num = etNum!!.text.toString()
                if (num == "") {
                    Toast.makeText(this@MainActivity, "官人，请输入正确的幸运数字哦", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        tvWord!!.text = iServer!!.getContentDetailByNum(num)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }

                }
            } else {
                Toast.makeText(this@MainActivity, "官人，后台服务没有启动哦", Toast.LENGTH_SHORT).show()
            }
        }
        tvWord!!.setOnClickListener {
            if (iServer != null) {
                call()
            } else {
                Log.d("提示", "服务为空！")
            }
        }


    }


    fun bindService() {

        //绑定 alipay的 服务

        Log.d("bindService", "bindService")
        val intent = Intent()
        intent.`package` = "com.jiaxufei.fistkotlinprogram"
        intent.action = "com.test.alipay"
        //  intent.setComponent(new ComponentName("com.jiaxufei.fistkotlinprogram","com.jiaxufei.fistkotlinprogram.IAlipayService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    fun call() {

        try {
            val result = iServer!!.callPayInService(1, 2)
            val mode = iServer!!.name()
            Log.d("提示数字结果：", result.toString() + "")
            Log.d("提示对象结果：", mode.name + "")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return if (!isNum.matches()) {
            false
        } else true
    }

    fun goToGoodsActivity() {
        var goods = Goods()
        goods.name = "贾旭飞"
        var bundle = Bundle()
        bundle.putInt("goodsNum", 11)
        bundle.putString("goods", JsonService.Factory.getInstance().create().toJsonString(goods))
        UIRouter.getInstance().openUri(this, "DDComp://goods/goodsList", bundle)
    }

    fun goToGoodsActivityForResult() {
        var goods = Goods()
        goods.name = "贾旭飞"
        var bundle = Bundle()
        bundle.putInt("goodsNum", 11)
        bundle.putString("goods", JsonService.Factory.getInstance().create().toJsonString(goods))
        UIRouter.getInstance().openUri(this, "DDComp://goods/goodsList", bundle, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Toast.makeText(this@MainActivity, "返回的提示", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
