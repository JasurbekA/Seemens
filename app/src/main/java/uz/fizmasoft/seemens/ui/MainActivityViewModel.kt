package uz.fizmasoft.seemens.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.fizmasoft.seemens.data.local.SeemensResponse
import uz.fizmasoft.seemens.data.repo.SeemensRepo
import java.net.ConnectException
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repo: SeemensRepo) : ViewModel() {


    private val _seemensResponse = MutableLiveData<LoadingResponseState>()
    val seemensResponse: LiveData<LoadingResponseState>
        get() = _seemensResponse

    fun loadSeemsResponse() = viewModelScope.launch {
        _seemensResponse.value = LoadingResponseState.OnLoading

        try {
            val response = async { return@async repo.loadResponse() }.await()
            _seemensResponse.value =  if (response.isSuccessful) LoadingResponseState.OnSuccess(response.body()!!)
            else LoadingResponseState.OnError("Cannot load from the server")

        } catch (e: ConnectException) {
            _seemensResponse.value = LoadingResponseState.OnError(
                "Cannot connect to the server.\nNetwork is unreachable"
            )
        } catch (e: Exception) {
            _seemensResponse.value = LoadingResponseState.OnError("${e.message}" )
        }
    }


    sealed class LoadingResponseState {
        object OnLoading : LoadingResponseState()
        data class OnSuccess(val response: SeemensResponse) : LoadingResponseState()
        data class OnError(val errorMessage: String) : LoadingResponseState()
    }

}