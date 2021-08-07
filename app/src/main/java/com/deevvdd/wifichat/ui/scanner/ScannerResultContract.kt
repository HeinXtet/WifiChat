package com.deevvdd.wifichat.ui.scanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.deevvdd.wifichat.ui.client.join.JoinClientActivity

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
class ScannerResultContract() : ActivityResultContract<Int, String?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, QrScannerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra(JoinClientActivity.EXTRA_IP_ADDRESS)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }
}
