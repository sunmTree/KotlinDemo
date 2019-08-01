package com.kotlin.billing

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.android.billingclient.api.*

const val LOG_TAG = "BillingHelper"
const val BILLING_RE_CONNECT_MSG = 0x001
const val RE_CONNECT_TIME_INTERVAL = 10000L

class BillingHelper : PurchasesUpdatedListener, BillingClientStateListener, Handler.Callback {
    private lateinit var mBillingClient: BillingClient
    private lateinit var mContext: Context
    private lateinit var mSkuDetailList: List<SkuDetails>

    private val mHandler: Handler = Handler(Looper.getMainLooper(), this@BillingHelper)

    fun start(context: Context) {
        Log.d(LOG_TAG, "start connect to BillingClient")
        this.mContext = context.applicationContext

        mBillingClient = BillingClient.newBuilder(mContext).setListener(BillingHelper@ this).build()
        mBillingClient.startConnection(BillingHelper@ this)
    }

    override fun onBillingServiceDisconnected() {
        mBillingClient.startConnection(this@BillingHelper)
    }

    override fun onBillingSetupFinished(billingResult: BillingResult?) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK) {
            mHandler.removeMessages(BILLING_RE_CONNECT_MSG)
        } else {
            mHandler.sendEmptyMessageDelayed(BILLING_RE_CONNECT_MSG, RE_CONNECT_TIME_INTERVAL)
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val params = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                mBillingClient.acknowledgePurchase(params) {
                }
            }
        }
    }

    fun querySkuDetails(skuList: List<String>, skuType: String) {
        val queryParams = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(skuType)
        mBillingClient.querySkuDetailsAsync(
            queryParams.build()
        ) { billingResult, skuDetailsList ->
            mSkuDetailList = skuDetailsList
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (skuDetail in skuDetailsList) {
                    Log.d(LOG_TAG, "SkuDetails ${skuDetail.description} ${skuDetail.sku}")
                }
            }
        }
    }

    fun purchase(sku: String, activity: Activity) {
        querySKuDetails(sku)?.apply {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(this).build()
            mBillingClient.launchBillingFlow(activity, flowParams)
        }
    }

    private fun querySKuDetails(sku: String): SkuDetails? {
        mSkuDetailList?.apply {
            for (skuDetails in this) {
                if (skuDetails.sku == sku) return skuDetails
            }
        }
        return null
    }

    override fun handleMessage(msg: Message?): Boolean {
        if (msg?.what == BILLING_RE_CONNECT_MSG) {
            mBillingClient.startConnection(this@BillingHelper)
        }
        return true
    }
}