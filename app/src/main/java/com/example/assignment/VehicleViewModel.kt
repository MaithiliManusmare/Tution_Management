import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Vehicle
import com.example.assignment.VehicleRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VehicleViewModel(private val repository: VehicleRepository) : ViewModel() {

    private val allVehiclesFlow: Flow<List<Vehicle>> = repository.allVehicles

    private val selectedBrands = MutableStateFlow<List<String>>(emptyList())
    private val selectedFuelTypes = MutableStateFlow<List<String>>(emptyList())

    val filteredVehicles: StateFlow<List<Vehicle>> = combine(
        allVehiclesFlow,
        selectedBrands,
        selectedFuelTypes
    ) { vehicles, brands, fuels ->
        vehicles.filter { vehicle ->
            val brandMatch = brands.isEmpty() || brands.contains(vehicle.brand)
            val fuelMatch = fuels.isEmpty() || fuels.contains(vehicle.fuelType)
            brandMatch && fuelMatch
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insert(vehicle: Vehicle) {
        viewModelScope.launch {
            repository.insert(vehicle)
        }
    }

    fun updateSelectedBrands(brands: List<String>) {
        selectedBrands.value = brands
    }

    fun updateSelectedFuelTypes(fuels: List<String>) {
        selectedFuelTypes.value = fuels
    }

}
