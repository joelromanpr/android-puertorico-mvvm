/*
 * Copyright (C) 2025 joelromanpr (Joel Roman)
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joelromanpr.android.puertorico.presentation.schools

import androidx.lifecycle.viewModelScope
import com.joelromanpr.android.essentials.arch.ui.EssentialsViewModel
import com.joelromanpr.android.puertorico.domain.usecase.GetSchoolsUseCase
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val getSchoolsUseCase: GetSchoolsUseCase
) : EssentialsViewModel<SchoolsContract.SchoolsUiState, SchoolsContract.SchoolsUiAction, NavRoutes>() {

    private val _screenState = MutableStateFlow(SchoolsContract.SchoolsUiState())
    override val screenState: StateFlow<SchoolsContract.SchoolsUiState> = _screenState.asStateFlow()

    init {
        onAction(SchoolsContract.SchoolsUiAction.LoadSchools)
    }

    override fun onAction(action: SchoolsContract.SchoolsUiAction) {
        when (action) {
            SchoolsContract.SchoolsUiAction.LoadSchools -> loadSchools()
        }
    }

    private fun loadSchools() {
        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true, error = null) }
            try {
                val data = getSchoolsUseCase()
                _screenState.update { it.copy(isLoading = false, schools = data) }
            } catch (e: Exception) {
                _screenState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Unknown error") }
            }
        }
    }
}
