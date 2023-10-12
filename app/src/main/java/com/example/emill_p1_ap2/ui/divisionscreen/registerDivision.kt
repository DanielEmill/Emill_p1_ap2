package com.example.emill_p1_ap2.ui.divisionscreen


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.emill_p1_ap2.utils.CustomNumericalOutlinedTextField
import com.example.emill_p1_ap2.utils.CustomOutlinedTextField
import com.example.emill_p1_ap2.utils.MyBar
import com.example.emill_p1_ap2.utils.SaveButton
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun divisionScreen(viewModel: DivisionViewModel = hiltViewModel()) {
    val divisiones by viewModel.divisores.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    //
    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest { showMessage ->
            if (showMessage) {
                snackbarHostState.showSnackbar(
                    message = "Calculo guardado exitosamente", duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = { MyBar() },
    ) {
        ContentColumn(viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentColumn(viewModel: DivisionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DivisionesDetails(viewModel)
        SaveButton(viewModel)
        DivisionConsultascreen(viewModel)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionesDetails(viewModel: DivisionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Divisiones:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomOutlinedTextField(
            label = "Nombre",
            value = viewModel.nombre,
            modifier = Modifier.padding(vertical = 8.dp),
            isValid = viewModel.isValidNombre,
            onValueChange = { viewModel.nombre = it },
            imeAction = ImeAction.Next
        )
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomNumericalOutlinedTextField(
                    label = "Dividiendo",
                    value = viewModel.dividiendo,
                    isValid = viewModel.isValidDividiendo,
                    onValueChange = { newValue ->
                        viewModel.dividiendo = newValue
                    },
                    imeAction = ImeAction.Next,
                )
                if (viewModel.errorDividiendo.isNotEmpty()) {
                    Text(text = viewModel.errorDividiendo, color = Color.Red)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomNumericalOutlinedTextField(
                    label = "Divisor",
                    value = viewModel.divisor,
                    isValid = viewModel.isValidDivisor,
                    onValueChange = { newValue ->
                        viewModel.divisor = newValue
                    },
                    imeAction = ImeAction.Next,
                )
                if (viewModel.errorDivisor.isNotEmpty()) {
                    Text(text = viewModel.errorDivisor, color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomNumericalOutlinedTextField(
                    label = "Cociente",
                    value = viewModel.cociente,
                    isValid = viewModel.isValidCociente,
                    onValueChange = { newValue ->
                        viewModel.cociente = newValue
                    },
                    imeAction = ImeAction.Next,
                )
                if (viewModel.errorCociente.isNotEmpty()) {
                    Text(text = viewModel.errorCociente, color = Color.Red)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                CustomNumericalOutlinedTextField(
                    label = "Residuo",
                    value = viewModel.residuo,
                    isValid = viewModel.isValidResiduo,
                    onValueChange = { newValue ->
                        viewModel.residuo = newValue
                    },
                    imeAction = ImeAction.Done,
                )
                if (viewModel.errorResiduo.isNotEmpty()) {
                    Text(text = viewModel.errorResiduo, color = Color.Red)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DivisionConsultascreen(viewModel: DivisionViewModel = hiltViewModel()) {
    val divisiones by viewModel.divisores.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Historia de resultados") },
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(24.dp)) }
                items(divisiones) { division ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Nombre: ${division.nombre}",
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Row {
                                Text(
                                    text = "Dividiendo: ${division.dividiendo}",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Divisor: ${division.divisor}"
                                )
                            }
                            Row {
                                Text(
                                    text = "Cociente: ${division.cociente}",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Residuo: ${division.residuo}"
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    viewModel.deleteDivision(division)
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    )
}
