package com.upayment.myapplication

import android.app.Application
import com.upayment.upaymentsdk.UInterfaceSDK
import com.upayment.upaymentsdk.constants.Environment

class TestApplication : Application() {

    /**
     * API key for accessing the UPaymentGateway service. <br></br>
     * `**IMPORTANT:**` Replace with your actual API key from a secure source.
     */
    val API_KEY: String = "e66a94d579cf75fba327ff716ad68c53aae11528"

    /**
     * Enables logging for the UPaymentGateway library. <br></br>
     * Set to `false` for production environments.
     */
    val ENABLE_LOGGING: Boolean = false

    /**
     * Indicates sandbox mode for testing. <br></br>
     * Use `"0"` for sandbox.
     */
    val SANDBOX_MODE: Environment = Environment.PRODUCTION


    override fun onCreate() {
        super.onCreate()

        // Initialize UPaymentGateway for the application.
        UInterfaceSDK.initialize(this, API_KEY, SANDBOX_MODE, ENABLE_LOGGING)
    }
}