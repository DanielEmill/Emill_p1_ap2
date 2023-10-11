package com.example.emill_p1_ap2.ui.divisionscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emill_p1_ap2.data.entities.Division
import com.example.emill_p1_ap2.data.repository.DivisionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DivisionViewModel @Inject constructor(private val repository: DivisionRepository) : ViewModel(){
    var nombre by mutableStateOf("")
    var dividiendo by mutableIntStateOf(0)
    var divisor by mutableIntStateOf(0)
    var cociente by mutableIntStateOf(0)
    var residuo by mutableIntStateOf(0)
//
    var isValidNombre by mutableStateOf(true)
    var isValidDividiendo by mutableStateOf(true)
    var isValidDivisor by mutableStateOf(true)
    var isValidCociente by mutableStateOf(true)
    var isValidResiduo by mutableStateOf(true)
    //
    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()
    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }
    //
    val divisores: StateFlow<List<Division>> = repository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
    //
    private fun isValid():Boolean{
        isValidNombre = nombre.isNotBlank()
        isValidDividiendo = dividiendo != 0
        isValidDivisor = divisor != 0
        isValidCociente = cociente != 0
        isValidResiduo = residuo != 0
        return isValidNombre && isValidDividiendo && isValidDivisor && isValidCociente && isValidResiduo
    }
    //
    fun deleteDivision(division: Division){
        viewModelScope.launch {
            repository.delete(division)
            limpiar()
        }
    }
    //
    fun saveDivision(){
        viewModelScope.launch {
            if(isValid()){
                val division = Division(
                    nombre = nombre,
                    dividiendo = dividiendo,
                    divisor = divisor,
                    cociente = cociente,
                    residuo = residuo
                )
                repository.save(division)
                limpiar()
            }
        }
    }
    private fun limpiar(){
         nombre = ""
         dividiendo = 0
         divisor = 0
         cociente = 0
         residuo = 0
    }

}