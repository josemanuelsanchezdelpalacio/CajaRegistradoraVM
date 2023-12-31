package com.dam2jms.cajaregistradoravm.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dam2jms.cajaregistradoravm.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.dam2jms.cajaregistradoravm.R
import com.dam2jms.cajaregistradoravm.data.billetesMonedas
import com.dam2jms.cajaregistradoravm.data.listaCambios

class ViewModelCambios : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onChange(importe: Double, pago: Double) {
        _uiState.update { currentState ->
            currentState.copy(importe = importe, pago = pago)
        }
    }

    fun cambios(context: Context) {
        var cambio = _uiState.value.pago - _uiState.value.importe

        for (billete in billetesMonedas) {
            val cantidad = (cambio / billete).toInt()
            if (cantidad > 0) {
                listaCambios[billete] = cantidad
                cambio %= billete
            }
        }

        _uiState.update { currentState ->
            currentState.copy(listaCambios = listaCambios)
        }
    }
}