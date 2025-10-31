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

data class GovernmentResponse(
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("executive_branch") val executiveBranch: ExecutiveBranch,
    @SerializedName("legislative_branch") val legislativeBranch: LegislativeBranch,
    @SerializedName("judicial_branch") val judicialBranch: JudicialBranch,
    @SerializedName("federal_representation") val federalRepresentation: FederalRepresentation
)

data class ExecutiveBranch(
    val name: String,
    val description: String,
    val governor: Person,
    val cabinet: List<Person>
)

data class LegislativeBranch(
    val name: String,
    val description: String,
    val senate: LegislativeBody,
    @SerializedName("house_of_representatives") val houseOfRepresentatives: LegislativeBody
)

data class JudicialBranch(
    val name: String,
    val description: String,
    @SerializedName("supreme_court") val supremeCourt: SupremeCourt
)

data class FederalRepresentation(
    val name: String,
    val description: String,
    @SerializedName("resident_commissioner") val residentCommissioner: Person
)

data class Person(
    val name: String,
    val position: String? = null,
    @SerializedName("role_description") val roleDescription: String? = null,
    val party: String? = null,
    @SerializedName("profile_picture_url") val profilePictureUrl: String? = null,
    val district: String? = null, // For senators and representatives
    @SerializedName("appointment_year") val appointmentYear: Int? = null // For justices
)

data class LegislativeBody(
    val name: String,
    val description: String,
    val leadership: List<Person>,
    val senators: List<Person>? = null, // For Senate
    val representatives: List<Person>? = null // For House of Representatives
)

data class SupremeCourt(
    val name: String,
    val description: String,
    @SerializedName("chief_justice") val chiefJustice: Person,
    @SerializedName("associate_justices") val associateJustices: List<Person>
)
