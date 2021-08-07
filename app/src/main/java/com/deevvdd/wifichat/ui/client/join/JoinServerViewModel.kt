package com.deevvdd.wifichat.ui.client.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.wifichat.domain.repository.CommonRepository
import com.deevvdd.wifichat.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
@HiltViewModel
class JoinServerViewModel @Inject constructor(
    private val commonRepository: CommonRepository
) : ViewModel() {

    val ipAddress = MutableLiveData(commonRepository.getLastIPAddress())
    private val _startChatClientActivity = MutableLiveData<Event<Unit>>()

    val startChatClientActivity: LiveData<Event<Unit>>
        get() = _startChatClientActivity

    val enabledToJoinServer: LiveData<Boolean> = Transformations.map(ipAddress) {
        !it.isNullOrEmpty()
    }

    fun updateIpAddress(ipAddress: String) {
        this.ipAddress.value = ipAddress
    }

    fun joinServer() {
        ipAddress.value?.let {
            commonRepository.saveLastIPAddress(it)
            _startChatClientActivity.value = Event(Unit)
        }
    }
}
