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
package com.joelromanpr.android.puertorico.data.model

import com.google.gson.annotations.SerializedName

data class School(
    val id: String,
    val name: String,
    val municipality: String,
    val district: String,
    val type: String,
    val level: String,
    val address: String,
    @SerializedName("student_count") val studentCount: Int,
    val programs: List<String>,
    val latitude: Double,
    val longitude: Double
)
