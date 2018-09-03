package beebetterbee.com.wezualizer.ui.main

import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import beebetterbee.com.wezualizer.R

class MainViewModel : ViewModel(), LifecycleObserver {
    val graphTitle = MutableLiveData<String>()
    val graphValues = MutableLiveData<String>()
    val colorGraphBar = MutableLiveData<@androidx.annotation.ColorRes Int>()
    val colorGraphTitle = MutableLiveData<Int>()
    // TODO: Implement the ViewModel

   init {
       graphTitle.value = ""
       graphValues.value = ""
       colorGraphBar.value =  R.color.pink
   }
}
