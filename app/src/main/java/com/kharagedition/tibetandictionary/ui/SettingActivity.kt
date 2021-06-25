package com.kharagedition.tibetandictionary.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.google.android.material.button.MaterialButton
import com.kharagedition.tibetandictionary.R

class SettingActivity : AppCompatActivity(),PurchasesUpdatedListener {
    lateinit var materialToolbar: ImageView
    lateinit var storeButton: MaterialButton
    private lateinit var billingClient: BillingClient
    private val skuList = listOf("remove_ads_dictionary")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        materialToolbar = findViewById(R.id.setting_backbtn)
        storeButton = findViewById(R.id.store_btn)
        setupBillingClient()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.settings_container,
                    SettingsFragment()
                )
                .commit()
        }
        initListener()

    }

    private fun initListener() {
        materialToolbar.setOnClickListener {
            finish()
        }

    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(this)
            .build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                Log.i("TAG", "onBillingSetupFinished: ")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    loadAllSKUs()
                    // The BillingClient is setup successfully
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("TAG", "onBillingServiceDisconnected: ",)

            }
        })

    }

    private fun loadAllSKUs() = if (billingClient.isReady) {
        val params = SkuDetailsParams
            .newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)
            .build()
        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            // Process the result.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && (skuDetailsList != null && skuDetailsList.isNotEmpty())) {
                for (skuDetails in skuDetailsList) {
                    if (skuDetails.sku == "remove_ads_product") {
                        storeButton.setOnClickListener{
                            Log.e("TAG", "loadAllSKUs:LOGGGG ", )
                            val billingFlowParams = BillingFlowParams
                                .newBuilder()
                                .setSkuDetails(skuDetails)
                                .build()
                            billingClient.launchBillingFlow(this, billingFlowParams)
                        }

                    }
                }
            }
        }

    } else {
        println("Billing Client not ready")
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                acknowledgePurchase(purchase.purchaseToken)

            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.e("TAG", "onPurchasesUpdated: CANCELLED",)
        } else {
            Log.e("TAG", "onPurchasesUpdated: ELSE",)
        }
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage
            Toast.makeText(this, "" + responseCode, Toast.LENGTH_SHORT).show()

        }


    }
}