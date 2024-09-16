package com.upayment.myapplication

import android.app.Application
import android.widget.Toast
import com.upayment.upaymentsdk.track.UpaymentGateway

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // 1 for Production and zero for Sandbox

        val tokenNonWhiteLabel :String = "e66a94d579cf75fba327ff716ad68c53aae11528";
        val tokenWhiteLabel :String = "jtest123";
        UpaymentGateway.init(this,"123",tokenNonWhiteLabel,true,"0");

    }
}