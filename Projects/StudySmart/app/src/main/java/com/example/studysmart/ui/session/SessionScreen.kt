package com.example.studysmart.ui.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysmart.sessions
import com.example.studysmart.subjects
import com.example.studysmart.ui.components.DeleteDialog
import com.example.studysmart.ui.components.SubjectListBottomSheet
import com.example.studysmart.ui.components.studySessionList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination<RootGraph>
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator
) {
    SessionScreen(
        onBackButtonClick = {navigator.navigateUp()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(

    onBackButtonClick: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    val sheetOpenHandler = {isOpen: Boolean ->
        isSheetOpen = isOpen
    }
    val scope = rememberCoroutineScope()

    var deleteDialogOpenState by remember { mutableStateOf(false) }

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
           // relatedToSubjectTextChange(it.name)
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isSheetOpen = false
            }

        },
        onDismissButtonClick = {isSheetOpen = false},
    )

    DeleteDialog(
        isOpen = deleteDialogOpenState,
        title = "Delete Session?",
        bodyText = "Are you sure?",
        onDismissRequest = {
            deleteDialogOpenState = false
        }
    ) {
        deleteDialogOpenState = false
    }

    Scaffold(
        topBar = { SessionScreenTopBar(
            onBackButtonClick = onBackButtonClick
        ) }
    ) {
        innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item {
                TimerSection(modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f))
            }

            item {
                RelatedToSubjectSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedToSubject = "English"
                ) {
                    sheetOpenHandler(true)
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonSection(
                        buttonTitle = "Cancel"
                    ) { }
                    ButtonSection(
                        buttonTitle = "Start"
                    ) { }
                    ButtonSection(
                        buttonTitle = "Finish"
                    ) { }

                }
            }
            studySessionList(
                sectionTitle = "STUDY SESSIONS HISTORY",
                emptyListText = "I see you haven't studied yet.\n START!!!",
                sessions = sessions,
                onDeleteIconClick = {

                    deleteDialogOpenState = true
                }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreenTopBar(
    onBackButtonClick: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = "Study Session", style = MaterialTheme.typography.headlineSmall)
        }
    )
}

@Composable
fun TimerSection(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)

        )
        Text(
            text = "00:05:32",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
        )
    }
}

@Composable
fun RelatedToSubjectSection(
    modifier: Modifier,
    relatedToSubject: String,
    selectSubjectButtonClick: () -> Unit
) {
    Column(modifier = modifier) {


        Text(
            text = "Related to Subject",
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = relatedToSubject, //taskDatePickerState.selectedDateMillis.changeMillisToDateString(),
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(
                onClick = {
                    selectSubjectButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ButtonSection(
                  buttonTitle: String,
                  onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = buttonTitle)
    }
}





















