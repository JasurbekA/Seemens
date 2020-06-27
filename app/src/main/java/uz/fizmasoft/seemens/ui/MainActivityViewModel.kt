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
            if (response.isSuccessful)
                _seemensResponse.value = LoadingResponseState.OnSuccess(response.body()!!)

        } catch (e: ConnectException) {
            _seemensResponse.value = LoadingResponseState.OnError(
                "Cannot Connect to the server.\nIs network available?"
            )
        } catch (e: Exception) {
            _seemensResponse.value = LoadingResponseState.OnError(
                "${e.message}" // in case Exception object is null
            )
        }
    }


    sealed class LoadingResponseState {
        object OnLoading : LoadingResponseState()
        data class OnSuccess(val response: SeemensResponse) : LoadingResponseState()
        data class OnError(val errorMessage: String) : LoadingResponseState()
    }

}