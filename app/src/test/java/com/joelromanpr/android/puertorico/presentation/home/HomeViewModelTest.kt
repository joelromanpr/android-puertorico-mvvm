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
package com.joelromanpr.android.puertorico.presentation.home

import app.cash.turbine.test
import com.joelromanpr.android.puertorico.navigation.NavRoutes
import com.joelromanpr.android.puertorico.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `init SHOULD set current date in Spanish`() {
        // GIVEN
        val expectedSdf = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val expectedDate = expectedSdf.format(Date())

        // WHEN
        val actualDate = viewModel.screenState.value.currentDate

        // THEN
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `onAction NavigateTo Government SHOULD navigate to government screen`() = runTest {
        // WHEN
        viewModel.onAction(HomeContract.HomeUiAction.NavigateTo(NavRoutes.Government.route))

        // THEN
        viewModel.nav.receiveAsFlow().test {
            val destination = awaitItem()
            assertEquals(NavRoutes.Government, destination)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onAction NavigateTo Municipalities SHOULD navigate to municipalities screen`() = runTest {
        // WHEN
        viewModel.onAction(HomeContract.HomeUiAction.NavigateTo(NavRoutes.Municipalities.route))

        // THEN
        viewModel.nav.receiveAsFlow().test {
            val destination = awaitItem()
            assertEquals(NavRoutes.Municipalities, destination)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onAction NavigateTo Schools SHOULD navigate to schools screen`() = runTest {
        // WHEN
        viewModel.onAction(HomeContract.HomeUiAction.NavigateTo(NavRoutes.Schools.route))

        // THEN
        viewModel.nav.receiveAsFlow().test {
            val destination = awaitItem()
            assertEquals(NavRoutes.Schools, destination)
            cancelAndConsumeRemainingEvents()
        }
    }
}
