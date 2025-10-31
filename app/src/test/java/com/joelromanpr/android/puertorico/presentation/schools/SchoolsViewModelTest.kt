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

import app.cash.turbine.test
import com.joelromanpr.android.puertorico.data.model.School
import com.joelromanpr.android.puertorico.domain.usecase.GetSchoolsUseCase
import com.joelromanpr.android.puertorico.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SchoolsViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SchoolsViewModel
    private val getSchoolsUseCase: GetSchoolsUseCase = mockk()

    @Test
    fun `init on success SHOULD emit initial, loading, and then success state`() = runTest {
        // GIVEN
        val fakeSchools = listOf(mockk<School>(), mockk<School>())
        coEvery { getSchoolsUseCase() } returns fakeSchools

        // WHEN
        viewModel = SchoolsViewModel(getSchoolsUseCase)

        // THEN
        viewModel.screenState.test {
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.schools.isEmpty())
            assertNull(state.error)

            state = awaitItem()
            assertTrue(state.isLoading)

            state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(fakeSchools, state.schools)
            assertNull(state.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init on error SHOULD emit initial, loading, and then error state`() = runTest {
        // GIVEN
        val errorMessage = "An error occurred"
        coEvery { getSchoolsUseCase() } throws RuntimeException(errorMessage)

        // WHEN
        viewModel = SchoolsViewModel(getSchoolsUseCase)

        // THEN
        viewModel.screenState.test {
            var state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.schools.isEmpty())
            assertNull(state.error)

            state = awaitItem()
            assertTrue(state.isLoading)

            state = awaitItem()
            assertFalse(state.isLoading)
            assertTrue(state.schools.isEmpty())
            assertEquals(errorMessage, state.error)

            cancelAndConsumeRemainingEvents()
        }
    }
}
