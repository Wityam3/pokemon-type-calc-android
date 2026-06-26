package com.example.pokemontypecalc.ui.main

import androidx.lifecycle.ViewModel
import com.example.pokemontypecalc.data.PokemonType
import com.example.pokemontypecalc.data.TypeChart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 寶可夢屬性相剋計算器 ViewModel
 * Pokémon Type Effectiveness Calculator ViewModel
 */
class TypeCalcViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TypeCalcUiState())
    val uiState: StateFlow<TypeCalcUiState> = _uiState.asStateFlow()

    /**
     * 切換模式（防禦方 / 攻擊方）
     * Switch mode (defense / attack)
     */
    fun switchMode(mode: CalcMode) {
        _uiState.update {
            it.copy(
                mode = mode,
                selectedDefenseTypes = emptyList(),
                selectedAttackType = null,
                results = emptyMap()
            )
        }
    }

    /**
     * 點擊屬性按鈕
     * Handle type button click
     */
    fun onTypeClick(type: PokemonType) {
        _uiState.update { state ->
            when (state.mode) {
                CalcMode.DEFENSE -> handleDefenseSelect(state, type)
                CalcMode.ATTACK -> handleAttackSelect(state, type)
            }
        }
    }

    /**
     * 防禦方模式的屬性選擇邏輯
     * Defense mode type selection logic
     */
    private fun handleDefenseSelect(state: TypeCalcUiState, type: PokemonType): TypeCalcUiState {
        val currentSelection = state.selectedDefenseTypes.toMutableList()

        if (currentSelection.contains(type)) {
            // 取消選擇 / Deselect
            currentSelection.remove(type)
        } else if (currentSelection.size < 2) {
            // 新增選擇 / Add selection
            currentSelection.add(type)
        } else {
            // 已選 2 個，替換最早選取的 / Replace oldest selection
            currentSelection.removeAt(0)
            currentSelection.add(type)
        }

        val results = if (currentSelection.isNotEmpty()) {
            TypeChart.getDefenseResults(currentSelection)
        } else {
            emptyMap()
        }

        return state.copy(
            selectedDefenseTypes = currentSelection,
            results = results
        )
    }

    /**
     * 攻擊方模式的屬性選擇邏輯
     * Attack mode type selection logic
     */
    private fun handleAttackSelect(state: TypeCalcUiState, type: PokemonType): TypeCalcUiState {
        val selectedType = if (state.selectedAttackType == type) null else type

        val results = if (selectedType != null) {
            TypeChart.getAttackResults(selectedType)
        } else {
            emptyMap()
        }

        return state.copy(
            selectedAttackType = selectedType,
            results = results
        )
    }

    /**
     * 重置選擇
     * Reset selection
     */
    fun reset() {
        _uiState.update {
            it.copy(
                selectedDefenseTypes = emptyList(),
                selectedAttackType = null,
                results = emptyMap()
            )
        }
    }
}

/**
 * 計算模式 / Calculation mode
 */
enum class CalcMode {
    DEFENSE,  // 防禦方 / Defense
    ATTACK    // 攻擊方 / Attack
}

/**
 * UI 狀態 / UI State
 */
data class TypeCalcUiState(
    val mode: CalcMode = CalcMode.DEFENSE,
    val selectedDefenseTypes: List<PokemonType> = emptyList(),
    val selectedAttackType: PokemonType? = null,
    val results: Map<Float, List<PokemonType>> = emptyMap()
) {
    /**
     * 判斷某個屬性是否被選取
     * Check if a type is currently selected
     */
    fun isTypeSelected(type: PokemonType): Boolean = when (mode) {
        CalcMode.DEFENSE -> type in selectedDefenseTypes
        CalcMode.ATTACK -> type == selectedAttackType
    }

    /**
     * 是否有任何屬性被選取
     * Whether any type is selected
     */
    val hasSelection: Boolean
        get() = when (mode) {
            CalcMode.DEFENSE -> selectedDefenseTypes.isNotEmpty()
            CalcMode.ATTACK -> selectedAttackType != null
        }
}
