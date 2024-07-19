package dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val myRepository: MyRepository,
) : ViewModel() {

    fun getTitle(): String = myRepository.fetchTitle()
}