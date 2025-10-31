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
package com.joelromanpr.android.puertorico.presentation.government

import app.cash.turbine.test
import com.joelromanpr.android.puertorico.data.model.GovernmentResponse
import com.joelromanpr.android.puertorico.domain.usecase.GetGovernmentDataUseCase
import com.joelromanpr.android.puertorico.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GovernmentViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: GovernmentViewModel
    private val getGovernmentDataUseCase: GetGovernmentDataUseCase = mockk()

    @Test
    fun `init on success SHOULD emit initial, loading, and then success state`() = runTest {
        // GIVEN
        val fakeResponse = createFakeGovernmentResponse()
        coEvery { getGovernmentDataUseCase() } returns fakeResponse

        // WHEN
        viewModel = GovernmentViewModel(getGovernmentDataUseCase)

        // THEN
        viewModel.screenState.test {
            // Initial state
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.governmentData)
            assertNull(state.error)

            // Loading state
            state = awaitItem()
            assertTrue(state.isLoading)

            // Success state
            state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(fakeResponse, state.governmentData)
            assertNull(state.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init on error SHOULD emit initial, loading, and then error state`() = runTest {
        // GIVEN
        val errorMessage = "An error occurred"
        coEvery { getGovernmentDataUseCase() } throws RuntimeException(errorMessage)

        // WHEN
        viewModel = GovernmentViewModel(getGovernmentDataUseCase)

        // THEN
        viewModel.screenState.test {
            // Initial state
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.governmentData)
            assertNull(state.error)

            // Loading state
            state = awaitItem()
            assertTrue(state.isLoading)

            // Error state
            state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.governmentData)
            assertEquals(errorMessage, state.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    private fun createFakeGovernmentResponse(): GovernmentResponse {
        return GovernmentResponse(
            lastUpdated = "2025-01-01",
            executiveBranch = mockk(relaxed = true),
            legislativeBranch = mockk(relaxed = true),
            judicialBranch = mockk(relaxed = true),
            federalRepresentation = mockk(relaxed = true)
        )
    }
}
