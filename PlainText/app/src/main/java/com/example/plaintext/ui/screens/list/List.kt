package com.example.plaintext.ui.screens.list

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.ListViewState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.JetcasterAppState
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.Content
import com.example.plaintext.ui.screens.rememberJetcasterAppState

@Composable
fun ListView(
    viewModel: ListViewModel = hiltViewModel(),
    navigateToEdit: (PasswordInfo) -> Unit
) {
    val listViewState = viewModel.listViewState
    val appState: JetcasterAppState = rememberJetcasterAppState()
    Scaffold(
        topBar = {
            TopBarComponent()
        },
        floatingActionButton = {
            val newPasswordInfo = PasswordInfo(id = -1, name = "", login = "", password = "", notes = "")
            AddButton(onClick = {
                navigateToEdit(newPasswordInfo)
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ListItemContent(
                modifier = Modifier.fillMaxWidth(),
                listState = listViewState,
                navigateToEdit = navigateToEdit
            )
        }
    }
}



@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemContent(
    modifier: Modifier,
    listState: ListViewState,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
    if (!listState.isCollected) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(listState.passwordList.size) { index ->
                ListItem(
                    password = listState.passwordList[index],
                    navigateToEdit = navigateToEdit
                )
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Carregando")
    }
}

@Composable
fun ListItem(
    password: PasswordInfo,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
    val title = password.name
    val subTitle = password.login

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { navigateToEdit(password) }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .weight(.7f)
                .padding(horizontal = 5.dp),
        ) {
            Text(title, fontSize = 20.sp)
            Text(subTitle, fontSize = 14.sp)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Menu",
            tint = Color.White
        )
    }
}