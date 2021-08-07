package com.deevvdd.wifichat.ui.client.join

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.databinding.ActivityJoinBinding
import com.deevvdd.wifichat.ui.client.ClientActivity
import com.deevvdd.wifichat.ui.scanner.ScannerResultContract
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
@AndroidEntryPoint
class JoinClientActivity : AppCompatActivity() {

    private val viewModel: JoinServerViewModel by viewModels()
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@JoinClientActivity
        }
        binding.btnScan.setOnClickListener {
            requestPermission()
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        viewModel.startChatClientActivity.observe(this, {
            goToChatRoom()
        })
    }

    private fun goToChatRoom() {
        startActivity(Intent(this, ClientActivity::class.java))
        this.finish()
    }

    private fun requestPermission() {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.text_explain_to_forward_setting),
                    getString(R.string.btn_text_ok),
                    getString(R.string.btn_text_cancel)
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    openScannerActivity.launch(SCANNER_RESULT_CODE)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.text_explain_to_required_permission),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private val openScannerActivity =
        registerForActivityResult(ScannerResultContract()) { result ->
            if (result != null) {
                viewModel.updateIpAddress(result)
                goToChatRoom()
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val SCANNER_RESULT_CODE = 120
        const val EXTRA_IP_ADDRESS = "EXTRA_IP_ADDRESS"
    }
}
