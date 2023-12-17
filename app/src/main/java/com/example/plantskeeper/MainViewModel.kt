import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantskeeper.repository.Plant
import com.example.plantskeeper.repository.PlantsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val plantsRepository = PlantsRepository()

    private val mutablePlants = MutableLiveData<List<Plant>>()
    val immutablePlantsData: LiveData<List<Plant>> = mutablePlants

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = plantsRepository.getStarResponse()
                val result = request.body()?.data?.docs
                mutablePlants.postValue(result)

            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie", e)
            }
        }
    }
}