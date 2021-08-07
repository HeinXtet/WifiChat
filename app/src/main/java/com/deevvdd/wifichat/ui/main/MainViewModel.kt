package com.deevvdd.wifichat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.wifichat.domain.repository.CommonRepository
import com.deevvdd.wifichat.domain.repository.ServerRepository
import com.deevvdd.wifichat.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ServerRepository,
    private val commonRepository: CommonRepository
) : ViewModel() {
    var userName = MutableLiveData<String>()
    val isValidToStart: LiveData<Boolean> = Transformations.map(userName) { !it.isNullOrEmpty() }
    private val _startServerActivityEvent = MutableLiveData<Event<Unit>>()
    private val _startJoinActivityEvent = MutableLiveData<Event<Unit>>()

    val startServerActivityEvent: LiveData<Event<Unit>>
        get() = _startServerActivityEvent

    val startJoinActivityEvent: LiveData<Event<Unit>>
        get() = _startJoinActivityEvent

    fun onCreateNewServer() {
        commonRepository.saveUserName(userName.value.orEmpty())
        userName.value = ""
        _startServerActivityEvent.value = Event(Unit)
    }

    fun joinServer() {
        Timber.d("Join Server")
        userName.value = ""
        commonRepository.saveUserName(userName.value.orEmpty())
        _startJoinActivityEvent.value = Event(Unit)
    }
}
