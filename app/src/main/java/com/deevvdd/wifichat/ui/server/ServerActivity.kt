package com.deevvdd.wifichat.ui.server

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.databinding.ActivityServerBinding
import com.deevvdd.wifichat.ui.common.ChatAdapter
import com.deevvdd.wifichat.ui.info.InfoActivity
import com.deevvdd.wifichat.utils.sendNotification
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */

@AndroidEntryPoint
class ServerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServerBinding
    private val viewModel: ServerViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_server)
        chatAdapter = ChatAdapter(viewModel.getUserName())
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ServerActivity
            adapter = chatAdapter
        }
        viewModel.startServer()
        setSupportActionBar(binding.toolbar)

        KeyboardVisibilityEvent.setEventListener(
            this
        ) {
            if (it) {
                binding.rv.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

        viewModel.onNewMessage.observe(this, {
            sendNotification(this, it, viewModel.getUserName())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuInfo -> {
                startActivity(Intent(this, InfoActivity::class.java))
                true
            }
            R.id.menuLogout -> {
                viewModel.stopServer()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
