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
package com.joelromanpr.android.puertorico.presentation.municipalities

import app.cash.turbine.test
import com.joelromanpr.android.puertorico.data.model.Municipality
import com.joelromanpr.android.puertorico.domain.usecase.GetMunicipalitiesUseCase
import com.joelromanpr.android.puertorico.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MunicipalitiesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MunicipalitiesViewModel
    private val getMunicipalitiesUseCase: GetMunicipalitiesUseCase = mockk()

    @Test
    fun `init on success SHOULD emit initial, loading, and then success state`() = runTest {
        // GIVEN
        val fakeMunicipalities = listOf(mockk<Municipality>(), mockk<Municipality>())
        coEvery { getMunicipalitiesUseCase() } returns fakeMunicipalities

        // WHEN
        viewModel = MunicipalitiesViewModel(getMunicipalitiesUseCase)

        // THEN
        viewModel.screenState.test {
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.municipalities.isEmpty())
            assertNull(state.error)

            state = awaitItem()
            assertTrue(state.isLoading)

            state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(fakeMunicipalities, state.municipalities)
            assertNull(state.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init on error SHOULD emit initial, loading, and then error state`() = runTest {
        // GIVEN
        val errorMessage = "An error occurred"
        coEvery { getMunicipalitiesUseCase() } throws RuntimeException(errorMessage)

        // WHEN
        viewModel = MunicipalitiesViewModel(getMunicipalitiesUseCase)

        // THEN
        viewModel.screenState.test {
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.municipalities.isEmpty())
            assertNull(state.error)

            state = awaitItem()
            assertTrue(state.isLoading)

            state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.municipalities.isEmpty())
            assertEquals(errorMessage, state.error)

            cancelAndConsumeRemainingEvents()
        }
    }
}
