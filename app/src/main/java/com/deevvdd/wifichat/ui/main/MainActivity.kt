package com.deevvdd.wifichat.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.databinding.ActivityMainBinding
import com.deevvdd.wifichat.ui.client.join.JoinClientActivity
import com.deevvdd.wifichat.ui.server.ServerActivity
import com.deevvdd.wifichat.utils.bounceUp
import com.deevvdd.wifichat.utils.createChannel
import com.deevvdd.wifichat.utils.fading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createChannel(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }
        viewModel.startServerActivityEvent.observe(this, {
            it.getContentIfNotHandled()?.let {
                startActivity(Intent(this, ServerActivity::class.java))
            }
        })
        viewModel.startJoinActivityEvent.observe(this, {
            it.getContentIfNotHandled()?.let {
                startActivity(Intent(this, JoinClientActivity::class.java))
            }
        })
        animateThings()
    }

    private fun animateThings() {
        with(binding) {
            tvDescriptionOfApp.fading()
            btnServerCreate.bounceUp()
            btnJoinServer.bounceUp(delay = 200)
        }
    }
}
