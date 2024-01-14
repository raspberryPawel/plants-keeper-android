import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantskeeper.repository.Plant
import com.example.plantskeeper.repository.PlantsRepository
import com.example.plantskeeper.repository.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SinglePlantActivityModel : ViewModel() {

    private val plantsRepository = PlantsRepository()

    private val mutablePlant = MutableLiveData<UiState<Plant>>()
    val immutablePlantData: LiveData<UiState<Plant>> = mutablePlant

    fun getData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutablePlant.postValue(UiState(isLoading = true, error = null))
                val request = plantsRepository.getPlantsResponse(id)
                val result = request.body()?.data

                mutablePlant.postValue(
                    UiState(
                        data = result
                    )
                )

            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie", e)
                mutablePlant.postValue(UiState(error = e.toString()))
            }
        }
    }
}