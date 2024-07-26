package screen.username

import androidx.lifecycle.ViewModel
import data.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsernameViewModel(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    init {
        coroutineScope.launch {
            profileRepository.getUsername().collect { _username.emit(it) }
        }
    }

    fun saveUsername(username: String) {
        if (username.isEmpty()) return

        coroutineScope.launch {
            profileRepository.saveUsername(username)
        }
    }

    fun onDispose() {
        coroutineScope.cancel()
    }
}