package uz.fizmasoft.seemens.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.fizmasoft.seemens.data.local.model.SeemensResponse
import uz.fizmasoft.seemens.data.repo.SeemensRepo
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repo: SeemensRepo) : ViewModel() {

    private val _seemensResponse = MutableLiveData<LoadingResponseState>()
    val seemensResponse: LiveData<LoadingResponseState>
        get() = _seemensResponse

    fun loadSeemsResponse() = viewModelScope.launch(exceptionHandler) {
        _seemensResponse.value = LoadingResponseState.OnLoading
        val response = async { return@async repo.loadResponse() }.await()
        _seemensResponse.value =
            if (response.isSuccessful) LoadingResponseState.OnSuccess(response.body()!!)
            else LoadingResponseState.OnError("Cannot load from the server")
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _seemensResponse.value = when (throwable) {
            is ConnectException, is UnknownHostException ->
                LoadingResponseState.OnError("Cannot connect to the server.\nNetwork is unreachable")
            else -> LoadingResponseState.OnError("${throwable.message}")
        }
    }

    sealed class LoadingResponseState {
        object OnLoading : LoadingResponseState()
        data class OnSuccess(val response: SeemensResponse) : LoadingResponseState()
        data class OnError(val errorMessage: String) : LoadingResponseState()
    }

}