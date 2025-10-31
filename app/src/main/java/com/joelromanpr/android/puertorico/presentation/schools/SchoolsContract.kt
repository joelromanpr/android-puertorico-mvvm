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

import com.joelromanpr.android.essentials.arch.ui.UiAction
import com.joelromanpr.android.essentials.arch.ui.UiState
import com.joelromanpr.android.puertorico.data.model.School

interface SchoolsContract {

    data class SchoolsUiState(
        val isLoading: Boolean = false,
        val schools: List<School> = emptyList(),
        val error: String? = null
    ) : UiState

    sealed class SchoolsUiAction : UiAction {
        object LoadSchools : SchoolsUiAction()
        // Actions for search, filter, or school details could be added here.
    }
}
