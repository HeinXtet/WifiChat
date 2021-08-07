package com.deevvdd.wifichat.ui.info

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.data.socket.Utils
import com.deevvdd.wifichat.databinding.ActivityRoomInfoBinding
import com.google.zxing.WriterException
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
class InfoActivity : AppCompatActivity() {
    private val TAG = "InfoActivity"

    private lateinit var binding: ActivityRoomInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_info)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        generateQr()
    }

    private fun generateQr() {
        val qrgEncoder =
            QRGEncoder(Utils.getIPAddress(true), null, QRGContents.Type.TEXT, 200)
        qrgEncoder.colorBlack = Color.BLACK
        qrgEncoder.colorWhite = Color.WHITE
        try {
            val bitmap = qrgEncoder.bitmap
            binding.ivQrIpAddress.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Timber.d(TAG, e.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
