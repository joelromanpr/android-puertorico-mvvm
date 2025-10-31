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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.joelromanpr.android.puertorico.data.model.ExecutiveBranch
import com.joelromanpr.android.puertorico.data.model.FederalRepresentation
import com.joelromanpr.android.puertorico.data.model.JudicialBranch
import com.joelromanpr.android.puertorico.data.model.LegislativeBranch
import com.joelromanpr.android.puertorico.data.model.Person

@Composable
fun GovernmentScreen(
    viewModel: GovernmentViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Gobierno de Puerto Rico",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cargando datos del gobierno...")
                    }
                }

                state.error != null -> {
                    Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }

                state.governmentData != null -> {
                    val data = state.governmentData
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Text(
                                text = "Última actualización: ${data?.lastUpdated}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        data?.let {
                            item { ExecutiveBranchSection(data.executiveBranch) }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { LegislativeBranchSection(data.legislativeBranch) }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { JudicialBranchSection(data.judicialBranch) }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { FederalRepresentationSection(data.federalRepresentation) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExecutiveBranchSection(executiveBranch: ExecutiveBranch) {
    SectionCard(title = executiveBranch.name, description = executiveBranch.description) {
        PersonDetail(person = executiveBranch.governor, role = "Gobernador(a)")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Gabinete:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        executiveBranch.cabinet.forEach { person ->
            PersonDetail(person = person, role = person.position ?: "Miembro del Gabinete")
        }
    }
}

@Composable
fun LegislativeBranchSection(legislativeBranch: LegislativeBranch) {
    SectionCard(title = legislativeBranch.name, description = legislativeBranch.description) {
        // Senate
        Text(
            text = legislativeBranch.senate.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = legislativeBranch.senate.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        legislativeBranch.senate.leadership.forEach { person ->
            PersonDetail(person = person, role = person.position ?: "Líder del Senado")
        }
        legislativeBranch.senate.senators?.forEach { senator ->
            PersonDetail(person = senator, role = "Senador(a) - Distrito ${senator.district}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // House of Representatives
        Text(
            text = legislativeBranch.houseOfRepresentatives.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = legislativeBranch.houseOfRepresentatives.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        legislativeBranch.houseOfRepresentatives.leadership.forEach { person ->
            PersonDetail(person = person, role = person.position ?: "Líder de la Cámara")
        }
        legislativeBranch.houseOfRepresentatives.representatives?.forEach { representative ->
            PersonDetail(
                person = representative,
                role = "Representante - Distrito ${representative.district}"
            )
        }
    }
}

@Composable
fun JudicialBranchSection(judicialBranch: JudicialBranch) {
    SectionCard(title = judicialBranch.name, description = judicialBranch.description) {
        Text(
            text = judicialBranch.supremeCourt.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = judicialBranch.supremeCourt.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        PersonDetail(person = judicialBranch.supremeCourt.chiefJustice, role = "Juez(a) Presidente")
        judicialBranch.supremeCourt.associateJustices.forEach { justice ->
            PersonDetail(person = justice, role = "Juez(a) Asociado(a)")
        }
    }
}

@Composable
fun FederalRepresentationSection(federalRepresentation: FederalRepresentation) {
    SectionCard(
        title = federalRepresentation.name,
        description = federalRepresentation.description
    ) {
        PersonDetail(
            person = federalRepresentation.residentCommissioner,
            role = "Comisionado(a) Residente"
        )
    }
}

@Composable
fun SectionCard(title: String, description: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun PersonDetail(person: Person, role: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = person.profilePictureUrl,
                contentDescription = "${person.name} profile picture",
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = person.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            person.party?.let {
                Text(
                    text = "Partido: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
