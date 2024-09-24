package com.upayment.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.upayment.upaymentsdk.UInterfaceSDK
import com.upayment.upaymentsdk.UPaymentCallBack
import com.upayment.upaymentsdk.constants.Environment
import com.upayment.upaymentsdk.models.UPaymentData
import com.upayment.upaymentsdk.models.addcard.request.addCardCustomer
import com.upayment.upaymentsdk.models.addcard.retrieve_response.ResponseRetrieveCard
import com.upayment.upaymentsdk.models.charge.paymentPayload
import com.upayment.upaymentsdk.models.invoice.CreateInvoiceResponse
import com.upayment.upaymentsdk.models.refund.multidelete.payload.MultiVendorRefundDeletePayload
import com.upayment.upaymentsdk.models.refund.multidelete.response.ResponseMultiRefundDelete
import com.upayment.upaymentsdk.models.refund.multirefund.response.Generated
import com.upayment.upaymentsdk.models.refund.multirefund.response.ResponseMultiVendorRefund
import com.upayment.upaymentsdk.models.refund.singlerefund.payload.DeleteSingleRefundPayload
import com.upayment.upaymentsdk.models.refund.singlerefund.payload.RefundRequestPayload
import com.upayment.upaymentsdk.models.refund.singlerefund.payload.SingleRefundPayload
import com.upayment.upaymentsdk.models.refund.singlerefund.response.SingleDeleteRefundResponse
import com.upayment.upaymentsdk.models.refund.singlerefund.response.SingleRefundResponse

class TestKotlin : AppCompatActivity(), UPaymentCallBack, OnClickListener {

    private var generatedInvoiceIdMultiRefund: String? = ""
    private var orderIdMultiRefund: String? = ""
    private var refundArnMultiRefund: String? = ""
    private var refundOrderIdMultiRefund: String? = ""
    private lateinit var tvChargeAPI: TextView
    private lateinit var tvChargeAPI2: TextView
    private lateinit var tvInvoice: TextView
    private lateinit var tvSingleRefund: TextView
    private lateinit var tvMultiRefund: TextView
    private lateinit var tvSingleDeleteRefund: TextView
    private lateinit var tvMultiDeleteRefund: TextView
    private lateinit var tvCreateToken: TextView
    private lateinit var tvAddCard: TextView
    private lateinit var tvRetrieveCard: TextView
    private lateinit var tvNonWhiteLabel: TextView
    private lateinit var tvWhiteLabel: TextView
    private lateinit var tvGetCustomerToken: TextView
    private var isWhiteLabeledStatus = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_library_case)

        // Before Calling on New Devices Need to Change New Mobile Number for Getting customer Token
        // Then SDK  Will Work

        tvChargeAPI = findViewById(R.id.tvChargeAPI)
        tvChargeAPI2 = findViewById(R.id.tvChargeAPI2)
        tvInvoice = findViewById(R.id.tvInvoice)
        tvSingleRefund = findViewById(R.id.tvSingleRefund)
        tvMultiRefund = findViewById(R.id.tvMultiRefund)
        tvSingleDeleteRefund = findViewById(R.id.tvSingleDeleteRefund)
        tvMultiDeleteRefund = findViewById(R.id.tvMultiDeleteRefund)
        tvCreateToken = findViewById(R.id.tvCreateToken)
        tvAddCard = findViewById(R.id.tvAddCard)
        tvRetrieveCard = findViewById(R.id.tvRetrieveCard)
        tvNonWhiteLabel = findViewById(R.id.tvNonWhiteLabel)
        tvWhiteLabel = findViewById(R.id.tvWhiteLabel)
        tvGetCustomerToken = findViewById(R.id.tvGetCustomerToken)

        tvChargeAPI.setOnClickListener(this)
        tvChargeAPI2.setOnClickListener(this)
        tvInvoice.setOnClickListener(this)
        tvSingleRefund.setOnClickListener(this)
        tvMultiRefund.setOnClickListener(this)
        tvSingleDeleteRefund.setOnClickListener(this)
        tvMultiDeleteRefund.setOnClickListener(this)
        tvAddCard.setOnClickListener(this)
        tvRetrieveCard.setOnClickListener(this)
        tvNonWhiteLabel.setOnClickListener(this)
        tvWhiteLabel.setOnClickListener(this)
        tvGetCustomerToken.setOnClickListener(this)


        // Get Customer Unique Token
        //  UPaymentGateway.getInstance().getCustomerUniqueToken(String(),this)
    }

    private fun callSingleDeleteRefundApi() {
        // Delete single refund
        val orderId =
            "Zl1a64XJx3mv1GZnrG2l123169461630816954952116501caf464c0b5DHZO38Oou7vorhrjYiRAGJ6YnLR9zwR"
        val refundOrderId = "5DHZO38Oou7vorhrjYiRAGJ6YnLR9zwR"
        val trackDeleteSingleRefund = DeleteSingleRefundPayload(orderId, refundOrderId)

        UInterfaceSDK.deleteSingleRefund(trackDeleteSingleRefund, this)
    }

    private fun callSingleRefundApi() {
        val refundPayload: ArrayList<RefundRequestPayload?> = arrayListOf()
        val params = RefundRequestPayload()
        params.ibanNumber = "KW91KFHO0000000000051010173254"
        params.amountToRefund = 1.0f
        refundPayload.add(params)

        val params2 = RefundRequestPayload()
        params2.ibanNumber = "KW31NBOK0000000000002010177457"
        params2.amountToRefund = 1.0f
        refundPayload.add(params2)

        val singleRefundPayload = SingleRefundPayload()
        singleRefundPayload.orderId = "9be2d69cc4b9444a95cc2cd38c19a41b"
        singleRefundPayload.totalPrice = 2.0f
        singleRefundPayload.customerFirstName = "Aqeel2"
        singleRefundPayload.customerEmail = "Aqeel2@gmail.com"
        singleRefundPayload.customerMobileNumber = "69923192"
        singleRefundPayload.reference = "411510001547"
        singleRefundPayload.notifyUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
        singleRefundPayload.refundPayload = refundPayload
        UInterfaceSDK.createRefund(singleRefundPayload, this)
    }

    private fun callInvoiceApi() {
        val eventCreateInvoice = paymentPayload {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 10f
                    name = "KFS"
                    qty = 1
                }
                product {
                    title = "Product 2"
                    price = 20f
                    name = "KFS2"
                    qty = 1
                }
            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "USD"
                amount = 19.98f
            }
            paymentGateway {
                src = "create-invoice"
            }
            language = "en"
            isSaveCard = false
            isWhiteLabeled = isWhiteLabeledStatus
            tokens {
                kFast = ""
                creditCard = ""
                customerUniqueToken = "889867836"
            }
            reference {
                id = "123459865234889"
            }
            customer {
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin {
                src = "magento"
            }
            notificationUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl = "https://developers.upayments.com/"
            notificationType = "email"
            // Set other properties as needed
        }
        UInterfaceSDK.processPayment(eventCreateInvoice, this)
    }

    private fun callChargeApi() {
        val eventCharge = paymentPayload {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 50.100f
                    name = "KFS"
                    qty = 1
                }
            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "KWD"
                amount = 50.100f
            }
            paymentGateway {
                src = "knet"
            }
            language = "en"
            isSaveCard = false
            isWhiteLabeled = isWhiteLabeledStatus
            tokens {
                kFast = ""
                creditCard = ""
                customerUniqueToken = "889867836"
            }
            reference {
                id = "123459865234889"
            }
            customer {
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin {
                src = "magento"
            }
            notificationUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl = "https://developers.upayments.com/"
            notificationType = "all"
            // Set other properties as needed
        }

        UInterfaceSDK.processPayment(eventCharge, this)
    }

    //Multi Charge API
    private fun callChargeApiWithExtraMerchant() {
        // non whit elabel unique token : 886626828796
        val eventCharge = paymentPayload {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 20f
                    name = "KFS"
                    qty = 1
                }
                product {
                    title = "Product 2"
                    price = 30f
                    name = "KFS2"
                    qty = 1
                }
            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "KWD"
                amount = 50.00f
            }
            paymentGateway {
                src = "knet"
            }
            language = "en"
            isSaveCard = false
            isWhiteLabeled = isWhiteLabeledStatus
            tokens {
                kFast = ""
                creditCard = ""
                customerUniqueToken = "889867836"
            }
            reference {
                id = "123459865234889"
            }
            customer {
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin {
                src = "magento"
            }
            notificationUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl = "https://developers.upayments.com/"
            notificationType = "all"
            extraMerchants {

                extraMerchant {

                    amount = 30.0f
                    knetCharge = "5"
                    knetChargeType = "fixed"
                    ccCharge = 10.0f
                    ccChargeType = "percentage"
                    ibanNumber = "KW91KFHO0000000000051010173254"

                }
                extraMerchant {

                    amount = 20.0f
                    knetCharge = "5"
                    knetChargeType = "fixed"
                    ccCharge = 10.0f
                    ccChargeType = "percentage"
                    ibanNumber = "KW31NBOK0000000000002010177457"

                }

            }
            // Set other properties as needed
        }

        UInterfaceSDK.processPayment(eventCharge, this)
    }

    override fun onPaymentProcessed(paymentData: UPaymentData) {
        runOnUiThread(Runnable {
            Toast.makeText(
                this@TestKotlin,
                paymentData.result + "TrackID" + paymentData.trackID,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onPaymentError(data: String?) {
        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin, data.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onInvoiceCreated(invoiceResponse: CreateInvoiceResponse?) {
        if (invoiceResponse != null) {
//            val urlDat = invoiceResponse.url
            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin, invoiceResponse.message, Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }

    override fun onMultipleRefundDelete(multiRefundDeleteData: ResponseMultiRefundDelete?) {
        if (multiRefundDeleteData != null) {
            runOnUiThread(Runnable {
                Toast.makeText(
                    this@TestKotlin,
                    multiRefundDeleteData.message + "RefundID:" + multiRefundDeleteData.multiDeleteRefundData?.refundOrderId,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    override fun onCardAdded(responseAddCard: String?) {
        if (responseAddCard != null) {
            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin, responseAddCard, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onSingleRefund(invoiceResponse: SingleRefundResponse?) {
        if (invoiceResponse != null) {
//            val gson = Gson()
            runOnUiThread(Runnable {
                Toast.makeText(
                    this@TestKotlin,
                    "RefundID" + invoiceResponse.refundOrderId,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    override fun onSingleRefundDelete(singleDeleteRefundResponse: SingleDeleteRefundResponse?) {
        runOnUiThread(Runnable {
            Toast.makeText(
                this@TestKotlin,
                singleDeleteRefundResponse?.message + "Delete Refund ID" + singleDeleteRefundResponse?.refundOrderId,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onMultiVendorRefund(responseMultiRefund: ResponseMultiVendorRefund?) {
        // Here May be more than one but for demo purpose taking zero index only
        val generated: Generated? = responseMultiRefund?.multiVendorRefundData?.responseData?.generated?.get(0)
        refundOrderIdMultiRefund = generated?.refundOrderId
        refundArnMultiRefund = generated?.refundArn
        orderIdMultiRefund = generated?.orderId
        generatedInvoiceIdMultiRefund = generated?.generatedInvoiceId

        runOnUiThread(Runnable {
            Toast.makeText(
                this@TestKotlin,
                responseMultiRefund?.message + "MultiRefundId:" + responseMultiRefund?.multiVendorRefundData?.responseData?.generated?.get(
                    0
                )?.refundOrderId,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    override fun onCardRetrieved(retriveCard: ResponseRetrieveCard?) {
        if (retriveCard != null) {
            runOnUiThread(Runnable {
                Toast.makeText(
                    this@TestKotlin,
                    retriveCard.message + "card No:" + retriveCard.cardData.customerCards?.get(0)?.number,
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    override fun onMultiVendorRefundFailure(invoiceResponse: ResponseMultiVendorRefund?) {
        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin, invoiceResponse?.message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.tvChargeAPI2 -> {
                //Buy Multi Product using Charge API with Extra Merchant Data
                callChargeApiWithExtraMerchant()
            }

            R.id.tvChargeAPI -> {
                // Buy Single Product using Charge API
                callChargeApi()
            }

            R.id.tvInvoice -> {
                callInvoiceApi()
            }

            R.id.tvSingleRefund -> {
                callSingleRefundApi()
            }

            R.id.tvMultiRefund -> {
                callSingleRefundApi()
            }

            R.id.tvSingleDeleteRefund -> {
                callSingleDeleteRefundApi()
            }

            R.id.tvMultiDeleteRefund -> {
                callMultiDeleteRefundApi()
            }

            R.id.tvNonWhiteLabel -> {
                isWhiteLabeledStatus = false
                val tokenNonWhiteLabel = "jtest123"
                //Move to Application
                // No Need to call this :At a time one Auth Header Token will be used .
                // If you like to switch from Non white label to white label or wise versa then need to call
                UInterfaceSDK.initializeDebugOnly(
                    applicationContext,
                    tokenNonWhiteLabel,
                    Environment.SANDBOX,
                    true
                )

                tvWhiteLabel.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                tvNonWhiteLabel.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            }

            R.id.tvWhiteLabel -> {
                isWhiteLabeledStatus = true
                val tokenWhiteLabel = "e66a94d579cf75fba327ff716ad68c53aae11528"
                //Move to Application
                // No Need to call this :At a time one Auth Header Token will be used .
                // If you like to switch from Non white label to white label or wise versa then need to call
                UInterfaceSDK.initializeDebugOnly(
                    applicationContext,
                    tokenWhiteLabel,
                    Environment.PRODUCTION,
                    true
                )
                //  tvWhiteLabel.setTextColor(resources.getColor(R.color.green))
                tvWhiteLabel.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                tvNonWhiteLabel.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            }

            R.id.tvAddCard -> {
                callAddCard()
            }

            R.id.tvRetrieveCard -> {
                callRetrieveCard()
            }

            else -> {
                // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }

    private fun callRetrieveCard() {
        val customerUniqueToken = "88986783663"
        UInterfaceSDK.retrieveCustomerCards(customerUniqueToken, this)
    }

    private fun callAddCard() {
        val addCardCustomer = addCardCustomer {
            returnUrl = "https://upayments.com/en/"
            customerUniqueToken = 889867836
        }

        UInterfaceSDK.addCardDetails(addCardCustomer, this)
    }

    private fun callMultiDeleteRefundApi() {
        val refundDelete = MultiVendorRefundDeletePayload(
            generatedInvoiceId = generatedInvoiceIdMultiRefund.toString(),
            orderId = refundOrderIdMultiRefund.toString(),
            refundOrderId = refundOrderIdMultiRefund.toString(),
            refundArn = refundArnMultiRefund.toString()
        )
        UInterfaceSDK.deleteMultiVendorRefund(refundDelete, this)
    }
}


