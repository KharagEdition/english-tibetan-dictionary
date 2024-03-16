package com.kharagedition.tibetandictionary.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.kharagedition.tibetandictionary.R
import com.kharagedition.tibetandictionary.util.Constant


class SettingActivity : AppCompatActivity() {
    lateinit var materialToolbar: ImageView
    lateinit var storeButton: MaterialButton
    lateinit var feedbackButton: MaterialButton
    //private lateinit var billingClient: BillingClient
    private val skuList = listOf("remove_ads_dictionary")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        materialToolbar = findViewById(R.id.setting_backbtn)
        storeButton = findViewById(R.id.store_btn)
        feedbackButton = findViewById(R.id.feedback_btn)
        //setupBillingClient()
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
        feedbackButton.setOnClickListener{
            val textInputLayout = TextInputLayout(this)
            textInputLayout.setPadding(
                    resources.getDimensionPixelOffset(R.dimen.dp_19), // if you look at android alert_dialog.xml, you will see the message textview have margin 14dp and padding 5dp. This is the reason why I use 19 here
                    0,
                    resources.getDimensionPixelOffset(R.dimen.dp_19),
                    0
            )
            val input = EditText(this)
            textInputLayout.addView(input)

            val alert = AlertDialog.Builder(this)
                    .setTitle("Feedback/Message")
                    .setView(textInputLayout)
                    .setMessage("Please leave message or feedback")
                    .setPositiveButton("Submit") { dialog, _ ->
                        val data = mutableMapOf<String, String>()
                        val message =input.text.toString();
                        if(message.isNotEmpty() && message.length>3 ){
                            data["message"]= input.text.toString();
                            FirebaseFirestore.getInstance().collection("Message").add(data).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(this, "Feedback sent!", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                            }.addOnFailureListener{
                                Toast.makeText(this, "Error:" + it.localizedMessage, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                        }


                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.cancel()
                    }.create()
            alert.window?.attributes?.windowAnimations = R.style.DialogAnimation
            alert.show()
        }

    }
/*
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
                Log.e("TAG", "onBillingServiceDisconnected: ")

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
                    if (skuDetails.sku == "remove_ads_dictionary") {
                        storeButton.setOnClickListener{
                            Log.e("TAG", "loadAllSKUs:LOGGGG ")
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
        Log.e("TAG", "loadAllSKUs:LOGGGG "+billingResult.responseCode)
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                acknowledgePurchase(purchase.purchaseToken)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.e("TAG", "onPurchasesUpdated: CANCELLED")
        } else if(billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
            Log.e("TAG", "onPurchasesUpdated: ITEM_ALREADY_OWNED")
            val prefs = getSharedPreferences(
                    "com.kharagedition.dictionary", Context.MODE_PRIVATE).edit()
            prefs.putBoolean(Constant.PURCHASED,true)
            prefs.apply();
        }else if(billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE){
            Toast.makeText(this, "ITEM NOT AVAILABLE", Toast.LENGTH_SHORT).show()
        }else{
            Log.e("TAG", "onPurchasesUpdated: ELSE")
        }
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage
            Toast.makeText(this, "TAG$responseCode", Toast.LENGTH_SHORT).show()
            //if(responseCode==0)
            val prefs = getSharedPreferences(
                    "com.kharagedition.dictionary", Context.MODE_PRIVATE).edit()
            prefs.putBoolean(Constant.PURCHASED,true)
            prefs.apply();
        }


    }*/
}