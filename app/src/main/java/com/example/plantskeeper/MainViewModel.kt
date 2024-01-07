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

class MainViewModel : ViewModel() {

    private val plantsRepository = PlantsRepository()

    private val mutablePlants = MutableLiveData<UiState<List<Plant>>>()
    val immutablePlantsData: LiveData<UiState<List<Plant>>> = mutablePlants

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutablePlants.postValue(UiState(isLoading = true, error = null))
                val request = plantsRepository.getAllPlantsResponse()
                val result = request.body()?.data?.docs

                mutablePlants.postValue(
                    UiState(
                        data = result
                    )
                )

            } catch (e: Exception) {
                Log.e("MainViewModel", "Operacja nie powiodla sie", e)
                mutablePlants.postValue(UiState(error = e.toString()))

            }
        }
    }
}