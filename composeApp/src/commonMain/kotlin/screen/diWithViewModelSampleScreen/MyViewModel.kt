package screen.diWithViewModelSampleScreen

import androidx.lifecycle.ViewModel
import dependencies.MyRepository

class MyViewModel(
    private val myRepository: MyRepository,
) : ViewModel() {

    fun getTitle(): String = myRepository.fetchTitle()
}