package com.example.test.ui.fragment.main

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.base_mvvm.BaseViewModel
import com.example.base_mvvm.network.Result
import com.example.domain.interactor.GetPetrolsInteractor
import com.example.domain.interactor.GetUserInteractor
import com.example.domain.model.Petrol
import com.example.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserInteractor: GetUserInteractor,
    private val getPetrolsInteractor: GetPetrolsInteractor
): BaseViewModel() {

    enum class Payments{
        PAYMENT, ACCUMULATIVE
    }

    val petrols: LiveData<List<Petrol>> get() = _petrols
    private val _petrols by lazy { MutableLiveData<List<Petrol>>() }

    val user: LiveData<User> get() = _user
    private val _user by lazy { MutableLiveData<User>() }

    var paymentMethod = Payments.ACCUMULATIVE

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val data = getUserInteractor()){
                is Result.Success -> {
                    _user.postValue(data.data)
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun getPetrols(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val data = getPetrolsInteractor()){
                is Result.Success -> {
                    data.data[0].isSelected = true
                    _petrols.postValue(data.data)
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun getChangedList(new: Int, text:String): List<Petrol>? {
        val templist = petrols.value?.map { it.copy(it.name, it.price, it.image, it.isSelected) }
        templist?.get(new)?.price = templist?.get(new)?.price?.times(
            try {
                text.toInt()
            } catch (e: NumberFormatException) {
                1
            }
        )
        return templist
    }

    fun validate(otherSum:Int): Boolean {
        return when(paymentMethod){
            Payments.PAYMENT -> {
                user.value?.paymentBalance?.let {
                    it>=otherSum
                }?: false
            }
            Payments.ACCUMULATIVE -> {
                user.value?.accumulativeBalance?.let {
                    it>=otherSum
                }?: false
            }
        }
    }

    fun onConfirmClicked(price:Int) {
        when(paymentMethod){
            Payments.ACCUMULATIVE -> {
                _user.postValue(User(_user.value?.paymentBalance, _user.value?.accumulativeBalance?.minus(price), _user.value?.name, _user.value?.email))
            }
            Payments.PAYMENT -> {
                _user.postValue(User(_user.value?.paymentBalance?.minus(price), _user.value?.accumulativeBalance, _user.value?.name, _user.value?.email))
            }
        }
    }
}