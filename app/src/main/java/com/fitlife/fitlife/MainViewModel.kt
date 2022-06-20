package com.fitlife.fitlife
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitlife.fitlife.resources.ON_LOGIN_BTN_CLICKED
import com.fitlife.fitlife.resources.ON_SIGN_UP_BTN_CLICKED

class MainViewModel: ViewModel() {

    val clickEvent: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun onLoginButtonClick(){


        clickEvent.value = ON_LOGIN_BTN_CLICKED
    }

    fun onSignUpClicked() {
        clickEvent.value = ON_SIGN_UP_BTN_CLICKED
    }


}