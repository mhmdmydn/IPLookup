package id.ghodel.iplookup.ui.main

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ghodel.iplookup.data.model.IpResponse
import id.ghodel.iplookup.data.repository.IpRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IpRepository,
) : ViewModel(){

    private val _response = MutableLiveData<IpResponse>()
    val ipResponse: LiveData<IpResponse>
        get() = _response

    init {
        getIp()
    }

    private fun getIp() = viewModelScope.launch {
        repository.getLocalIp().let { response ->
            if(response.isSuccessful()){
                _response.postValue(response.body())
            }else {
                Log.d("Retrofit Error", "response error code: ${response.code()}")
            }
        }
    }

     fun searchIp(ip: String) = viewModelScope.launch {
        repository.searchIp(ip).let {response ->
            if(response.isSuccessful()){
                _response.postValue(response.body())
            }else {
                Log.d("Retrofit Error", "response error code: ${response.code()}")
            }
        }
    }


}