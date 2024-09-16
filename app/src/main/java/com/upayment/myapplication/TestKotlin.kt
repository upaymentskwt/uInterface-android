package com.upayment.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.upayment.upaymentsdk.UPaymentCallBack
import com.upayment.upaymentsdk.activity.PostUpayData
import com.upayment.upaymentsdk.track.CreateInvoiceResponse
import com.upayment.upaymentsdk.track.UpaymentGateway
import com.upayment.upaymentsdk.track.card.request.addCardCustomer
import com.upayment.upaymentsdk.track.card.retrive.ResponseRetriveCard

import com.upayment.upaymentsdk.track.charge.upaymentGatewayEvent3
import com.upayment.upaymentsdk.track.refund.PayloadData
import com.upayment.upaymentsdk.track.refund.PostDeleteSingleRefundData
import com.upayment.upaymentsdk.track.refund.PostSingleRefundData
import com.upayment.upaymentsdk.track.refund.SingleDeleteRefundResponse
import com.upayment.upaymentsdk.track.refund.SingleRefundResponse
import com.upayment.upaymentsdk.track.refund.delete.PostMultivendorRefundDelete
import com.upayment.upaymentsdk.track.refund.multidelete.ResponseMultiRefundDelete
import com.upayment.upaymentsdk.track.refund.multirefund.Generated
import com.upayment.upaymentsdk.track.refund.multirefund.ResponseMultivendorRefund

class TestKotlin : AppCompatActivity(), UPaymentCallBack, OnClickListener {
    private var generatedInvoiceId_multiRefund: String?=""
    private var orderId_multiRefund: String?=""
    private var refundArn_multiRefund: String?=""
    private var refundOrderId_multiRefund: String?=""
    private lateinit var tvChargeAPI: TextView;
    private lateinit var tvChargeAPI2:TextView;
    private lateinit var tvInvoice:TextView;
    private lateinit var tvSingleRefund:TextView;
    private lateinit var tvMultiRefund:TextView;
    private lateinit var tvSingleDeleteRefund:TextView;
    private lateinit var tvMultiDeleteRefund:TextView;
    private lateinit var tvCreateToken:TextView;
    private lateinit var tvAddCard:TextView;
    private lateinit var tvRetrieveCard:TextView;
    private lateinit var tvNonWhiteLabel:TextView
    private lateinit var tvWhiteLabel : TextView
    var is_whitelabled_status = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_library_case);

        // Before Calling on New Devices Need to Change New Mobile Number for Getting customer Token
        // Then SDK  Will Work

        tvChargeAPI=  findViewById(R.id.tvChargeAPI)
        tvChargeAPI2=  findViewById(R.id.tvChargeAPI2)
        tvInvoice=  findViewById(R.id.tvInvoice)
        tvSingleRefund=  findViewById(R.id.tvSingleRefund)
        tvMultiRefund=  findViewById(R.id.tvMultiRefund)
        tvSingleDeleteRefund=  findViewById(R.id.tvSingleDeleteRefund)
        tvMultiDeleteRefund=  findViewById(R.id.tvMultiDeleteRefund)
        tvCreateToken=  findViewById(R.id.tvCreateToken)
        tvAddCard=  findViewById(R.id.tvAddCard)
        tvRetrieveCard=  findViewById(R.id.tvRetrieveCard)
        tvNonWhiteLabel = findViewById(R.id.tvNonWhiteLabel)
        tvWhiteLabel = findViewById(R.id.tvWhiteLabel)

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



        //Move to Application
        //  UpaymentGateway.init(this,"123","123bbdhd",true);


    }

    private fun callSingleDeleteRefundApi(){

        // Delete single refund
        var orderId= "Zl1a64XJx3mv1GZnrG2l123169461630816954952116501caf464c0b5DHZO38Oou7vorhrjYiRAGJ6YnLR9zwR";
        val refundOrderId="5DHZO38Oou7vorhrjYiRAGJ6YnLR9zwR"
        val trackDeleteSingleRefund = PostDeleteSingleRefundData(orderId,refundOrderId)

        UpaymentGateway.getInstance().trackDeleteSingleRefund(trackDeleteSingleRefund,this,is_whitelabled_status)
    }

    private fun callSingleRefundApi() {



        val refundPayload:  MutableList<PayloadData> = arrayListOf<PayloadData>()
        val params = PayloadData()
        params.ibanNumber = "KW91KFHO0000000000051010173254"
        params.amountToRefund= 1.0f
        refundPayload.add(params)

        val params2 = PayloadData()
        params2.ibanNumber = "KW31NBOK0000000000002010177457"
        params2.amountToRefund= 1.0f
        refundPayload.add(params2)

        val postSingleRefundData= PostSingleRefundData()
        postSingleRefundData.orderId ="9be2d69cc4b9444a95cc2cd38c19a41b"
        postSingleRefundData.totalPrice =2.0f
        postSingleRefundData.customerFirstName = "Aqeel2"
        postSingleRefundData.customerEmail = "Aqeel2@gmail.com"
        postSingleRefundData.customerMobileNumber = "69923192"
        postSingleRefundData.reference = "411510001547"
        postSingleRefundData.notifyUrl = "https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
        postSingleRefundData.refundPayload=refundPayload
        UpaymentGateway.getInstance().trackSingleRefund(postSingleRefundData,this,is_whitelabled_status)


    }

    private fun callInvoiceApi() {


        val eventCreateInvoice = upaymentGatewayEvent3 {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 10f
                    name="KFS"
                    qty=1
                }
                product {
                    title = "Product 2"
                    price = 20f
                    name="KFS2"
                    qty=1
                }
            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "USD"
                amount = 19.98f
            }
            paymentGateway{
                src="create-invoice"
            }
            language= "en"
            isSaveCard= false
            is_whitelabled= is_whitelabled_status
            tokens {
                kFast=""
                creditCard=""
                customerUniqueToken= "889867836";
            }
            reference{
                id="123459865234889"
            }
            customer{
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin{
                src="magento"
            }
            notificationUrl="https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl="https://developers.upayments.com/"
            notificationType="email"

            // Set other properties as needed
        }
        UpaymentGateway.getInstance().callPayment(eventCreateInvoice,this)
    }

    private fun callChargeApi() {
        val eventCharge = upaymentGatewayEvent3 {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 50.100f
                    name="KFS"
                    qty=1
                }

            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "KWD"
                amount = 50.100f
            }
            paymentGateway{
                src="knet"
            }
            language= "en"
            isSaveCard= false
            is_whitelabled= is_whitelabled_status
            tokens {
                kFast=""
                creditCard=""
                customerUniqueToken="889867836"
            }
            reference{
                id="123459865234889"
            }
            customer{
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin{
                src="magento"
            }
            notificationUrl="https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl="https://developers.upayments.com/"
            notificationType="all"


            // Set other properties as needed
        }

        UpaymentGateway.getInstance().callPayment(eventCharge,this)
    }

    //Multi Charge API
    private fun callChargeApiWithExtraMerchant() {
        // non whit elabel uniqu token : 886626828796
        val eventCharge = upaymentGatewayEvent3 {
            // to test charge api
            productList {
                product {
                    description = "Product 1"
                    price = 20f
                    name="KFS"
                    qty=1
                }
                product {
                    title = "Product 2"
                    price = 30f
                    name="KFS2"
                    qty=1
                }
            }
            order {
                id = "123"
                reference = "REF-456"
                description = "Order Description"
                currency = "KWD"
                amount = 50.00f
            }
            paymentGateway{
                src="knet"
            }
            language= "en"
            isSaveCard= false
            is_whitelabled= is_whitelabled_status
            tokens {
                kFast=""
                creditCard=""
                customerUniqueToken="889867836"
            }
            reference{
                id="123459865234889"
            }
            customer{
                uniqueId = "ABCDer22126433"
                name = "Aqeel2"
                email = "Aqeel2@gmail.com"
                mobile = "69923192"
            }
            plugin{
                src="magento"
            }
            notificationUrl="https://webhook.site/92eb6888-362b-4874-840f-3fff620f7cf4"
            returnUrl = "https://upayments.com/en/"
            cancelUrl="https://developers.upayments.com/"
            notificationType="all"

            extraMerchantList {

                extraMerchant {

                    amount = 30.0f
                    knetCharge = "5"
                    knetChargeType ="fixed"
                    ccCharge=10.0f
                    ccChargeType="percentage"
                    ibanNumber="KW91KFHO0000000000051010173254"

                }
                extraMerchant {

                    amount = 20.0f
                    knetCharge = "5"
                    knetChargeType ="fixed"
                    ccCharge=10.0f
                    ccChargeType="percentage"
                    ibanNumber="KW31NBOK0000000000002010177457"

                }

            }
            // Set other properties as needed
        }

        UpaymentGateway.getInstance().callPayment(eventCharge,this)
    }

    override fun callBackUpayment(postUpayData: PostUpayData) {
        if(postUpayData!=null){

            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin,postUpayData.result +"TrackID"+postUpayData.trackID,Toast.LENGTH_SHORT).show()
            }
            )
        }
    }



    override fun errorPayUpayment(data: String?) {

        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin,data.toString(),Toast.LENGTH_SHORT).show()
        }
        )
    }

    @SuppressLint("SuspiciousIndentation")
    override fun sucessCreateInvoice(invoiceResponse: CreateInvoiceResponse?) {
        if(invoiceResponse!=null){
            val urlDat=     invoiceResponse.url
            if(urlDat!=null){
                runOnUiThread(Runnable {
                    Toast.makeText(this@TestKotlin,invoiceResponse.message,Toast.LENGTH_SHORT).show()
                }
                )

            }
        }
    }

    override fun sucessMultiRefundDelete(multiRefundDeleteData: ResponseMultiRefundDelete?) {

        if(multiRefundDeleteData!=null){
            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin,multiRefundDeleteData.message+"RefundID:"+multiRefundDeleteData.data?.refundOrderId,Toast.LENGTH_SHORT).show()
            })
        }
    }



    override fun sucessAddCard(responseAddCard: String?) {

        if(responseAddCard!=null){
            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin,responseAddCard,Toast.LENGTH_SHORT).show()
            })
        }
    }



    override fun sucessSingleRefund(invoiceResponse: SingleRefundResponse?) {
        if(invoiceResponse!=null){
            val gson = Gson()
            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin,"RefundID"+invoiceResponse.refundOrderId,Toast.LENGTH_SHORT).show()
            }
            )
        }
    }

    override fun sucessSingleDeleteRefund(singleDeleteRefundResponse: SingleDeleteRefundResponse?) {

        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin,singleDeleteRefundResponse?.message+"Delete Refund ID"+singleDeleteRefundResponse?.refund_order_id,Toast.LENGTH_SHORT).show()
        }
        )

    }

    override fun sucessMultiRefund(responseMultiRefund: ResponseMultivendorRefund?) {
        val gson = Gson()
        // Here May be more than one but for demo purpose taking zero index only
        val generated:   Generated? =  responseMultiRefund?.data?.responseData?.generated?.get(0)
        refundOrderId_multiRefund=  generated?.refundOrderId;
        refundArn_multiRefund=  generated?.refundArn;
        orderId_multiRefund=  generated?.orderId;
        generatedInvoiceId_multiRefund=  generated?.generatedInvoiceId;

        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin,responseMultiRefund?.message+"MultiRefundId:"+responseMultiRefund?.data?.responseData?.generated?.get(0)?.refundOrderId,Toast.LENGTH_SHORT).show()
        }
        )
    }

    override fun sucessRetriveCard(retriveCard: ResponseRetriveCard?) {
        if(retriveCard!=null){

            runOnUiThread(Runnable {
                Toast.makeText(this@TestKotlin,retriveCard.message+"card No:"+retriveCard.data.customerCards?.get(0)?.number,Toast.LENGTH_SHORT).show()

            }
            )
        }
    }

    override fun failureMultiRefund(invoiceResponse: ResponseMultivendorRefund?) {
        runOnUiThread(Runnable {
            Toast.makeText(this@TestKotlin,invoiceResponse?.message,Toast.LENGTH_SHORT).show()

        }
        )
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.tvChargeAPI2 ->{
                //Buy Multi Product using Charge API with Extra Merchant Data
                callChargeApiWithExtraMerchant()
            }
            R.id.tvChargeAPI -> {
                // Buy Single Product using Charge API
                callChargeApi()
            }

            R.id.tvInvoice ->{

                callInvoiceApi()
            }
            R.id.tvSingleRefund ->{

                callSingleRefundApi()
            }
            R.id.tvMultiRefund ->{

                callSingleRefundApi()
            }
            R.id.tvSingleDeleteRefund ->{

                callSingleDeleteRefundApi()
            }
            R.id.tvMultiDeleteRefund ->{

                callMultiDeleteRefundApi()
            }
            R.id.tvNonWhiteLabel ->{
                is_whitelabled_status = false
                val tokenNonWhiteLabel :String = "jtest123";
                //Move to Application
                // No Need to call this :At a time one Auth Header Token will be used .
                // If you like to switch from Non white label to white label or wise versa then need to call
                UpaymentGateway.initAuthHeaderToken(tokenNonWhiteLabel);

                tvWhiteLabel.setBackgroundColor(   ContextCompat.getColor(this, R.color.gray))
                tvNonWhiteLabel.setBackgroundColor(   ContextCompat.getColor(this, R.color.green))

            }
            R.id.tvWhiteLabel ->{
                is_whitelabled_status = true
                val tokenWhiteLabel :String = "e66a94d579cf75fba327ff716ad68c53aae11528";
                //Move to Application
                // No Need to call this :At a time one Auth Header Token will be used .
                // If you like to switch from Non white label to white label or wise versa then need to call
                UpaymentGateway.initAuthHeaderToken(tokenWhiteLabel);
                //  tvWhiteLabel.setTextColor(resources.getColor(R.color.green))
                tvWhiteLabel.setBackgroundColor(   ContextCompat.getColor(this, R.color.green))
                tvNonWhiteLabel.setBackgroundColor(   ContextCompat.getColor(this, R.color.gray))


            }
            R.id.tvAddCard ->{

                callAddCard()
            }
            R.id.tvRetrieveCard ->{

                callRetriveCard()

            }

            else -> {
                // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }



    private fun callRetriveCard() {

        val customerUnqueToken="88986783663"
        UpaymentGateway.getInstance().getRetriveCard(this,is_whitelabled_status,customerUnqueToken)

    }

    private fun callAddCard() {

        val addCardCustomer = addCardCustomer {
            returnUrl = "https://upayments.com/en/"
            customerUniqueToken = 889867836
        }

        UpaymentGateway.getInstance().trackAddCards(addCardCustomer!!,this,is_whitelabled_status)

    }

    private fun callMultiDeleteRefundApi() {

        val refundDelete = PostMultivendorRefundDelete (
            generatedInvoiceId = generatedInvoiceId_multiRefund.toString(),
            orderId = refundOrderId_multiRefund.toString(),
            refundOrderId = refundOrderId_multiRefund.toString(),
            refundArn = refundArn_multiRefund.toString()
        )
        UpaymentGateway.getInstance().trackDeleteMultiRefund(refundDelete!!,this,is_whitelabled_status)
    }
}


