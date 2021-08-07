package com.deevvdd.wifichat.ui.client

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deevvdd.wifichat.R
import com.deevvdd.wifichat.data.socket.ServerStatus
import com.deevvdd.wifichat.databinding.ActivityClientBinding
import com.deevvdd.wifichat.ui.common.ChatAdapter
import com.deevvdd.wifichat.utils.sendNotification
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
@AndroidEntryPoint
class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding
    private val viewModel: ClientViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client)
        chatAdapter = ChatAdapter(viewModel.getName())
        binding.apply {
            vm = viewModel
            adapter = chatAdapter
            lifecycleOwner = this@ClientActivity
        }
        setSupportActionBar(binding.toolbar)
        viewModel.serverStatus.observe(this, {
            it.getContentIfNotHandled()?.let {
                handleServerStatus(it)
            }
        })

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
        viewModel.joinServer()
    }

    private fun handleServerStatus(serverStatus: ServerStatus) {
        when (serverStatus) {
            ServerStatus.UNABLE_TO_CONNECT_SERVER -> {
                onBackPressed()
                this.finish()
            }
            ServerStatus.SERVER_CLOSED -> {
                onBackPressed()
                this.finish()
            }
            else -> {
                Timber.d("Server default")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.men_client, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuLogout -> {
                viewModel.stopServer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
