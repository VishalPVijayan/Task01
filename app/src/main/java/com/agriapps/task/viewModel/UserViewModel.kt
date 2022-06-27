package com.agriapps.task.viewModel

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agriapps.task.database.UserDetails
import com.agriapps.task.database.UserRepository
import kotlinx.coroutines.launch
import com.agriapps.task.utility.Event


class UserViewModel(private val repository: UserRepository) : ViewModel(), Observable {

    val userDetails = repository.userdetails
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: UserDetails

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPhone = MutableLiveData<String>()

    @Bindable
    val inputAddress = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        }
//        else if(inputEmail.value == null) {
//            statusMessage.value = Event("Please enter subscriber's email")
//        } else if(Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
//            statusMessage.value = Event("Please enter a correct email address")
//        }
        else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                subscriberToUpdateOrDelete.phone = inputPhone.value!!
                subscriberToUpdateOrDelete.address = inputAddress.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                val phone: String = inputPhone.value!!
                val address: String = inputAddress.value!!
                insert(UserDetails(0, name, email,phone,address))
                inputName.value = null
                inputEmail.value = null
                inputPhone.value = null
                inputAddress.value = null
            }
        }
    }

    fun clearAllOrDelete() {
        if(isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(subscriber: UserDetails) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun update(subscriber: UserDetails) = viewModelScope.launch {
        val noOfRows = repository.update(subscriber)
        if (noOfRows > 0) {
            inputName.value = null
            inputEmail.value = null
            inputPhone.value = null
            inputAddress.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun delete(subscriber: UserDetails) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(subscriber)
        if (noOfRowsDeleted > 0) {
            inputName.value = null
            inputEmail.value = null
            inputPhone.value = null
            inputAddress.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun initUpdateAndDelete(subscriber: UserDetails) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        inputPhone.value = subscriber.phone
        inputAddress.value = subscriber.address
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}