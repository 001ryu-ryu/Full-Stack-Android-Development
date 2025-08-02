package com.example.studysmart.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.studysmart.subjects
import com.example.studysmart.ui.components.DeleteDialog
import com.example.studysmart.ui.components.SubjectListBottomSheet
import com.example.studysmart.ui.components.TaskCheckBox
import com.example.studysmart.ui.components.TaskDatePicker
import com.example.studysmart.utiil.Priority
import com.example.studysmart.utiil.changeMillisToDateString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val taskId: Int?,
    val subjectId: Int?
)

@Destination<RootGraph>(navArgs = TaskScreenNavArgs::class)
@Composable
fun TaskScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: TaskViewModel = hiltViewModel()
    TaskScreen(
        onBackButtonClick = { navigator.navigateUp() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onBackButtonClick: () -> Unit
) {
    var titleState by remember { mutableStateOf("") }
    var descState by remember { mutableStateOf("") }
    val onTitleChange = {title: String ->
        titleState = title
    }
    val onDescChange = {desc: String ->
        descState = desc
    }
    var deleteDialogOpenState by remember { mutableStateOf(false) }
    val deleteDialogEventHandler = {event: Boolean ->
        deleteDialogOpenState = event
    }

    var isTaskDatePickerDialogOpen by remember { mutableStateOf(false) }
    val taskDatePickerState: DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
       // selectableDates =
           // DateValidatorPointForward(System.currentTimeMillis())
    )

    var relatedToSubjectText by remember { mutableStateOf("Select a subject") }
    val relatedToSubjectTextChange = {text: String ->
        relatedToSubjectText = text
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    TaskDatePicker(
        state = taskDatePickerState,
        isOpen = isTaskDatePickerDialogOpen,
        onDismissRequest = {isTaskDatePickerDialogOpen = false},
        onConfirmButtonClicked = {isTaskDatePickerDialogOpen = false}
    )
    
    DeleteDialog(
        isOpen = deleteDialogOpenState,
        title = "Delete Task?",
        bodyText = "Are you sure?",
        onDismissRequest = {
            deleteDialogOpenState = false
        }
    ) {
        deleteDialogOpenState = false
    }

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
            relatedToSubjectTextChange(it.name)
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) isSheetOpen = false
            }

        },
        onDismissButtonClick = {isSheetOpen = false},
    )
    Scaffold(
        topBar = {
            TaskScreenTopBar(
                isTaskExists = true,
                isCompleted = false,
                checkBoxBorderColor = Color.Red,
                onDeleteButtonClick = { deleteDialogEventHandler(true) },
                onBackButtonClick = onBackButtonClick
            )

        }
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .verticalScroll(state = rememberScrollState())
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleState,
                onValueChange = onTitleChange,
                label = {Text("Title")},
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = descState,
                onValueChange = onDescChange,
                label = {Text("Description")},
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Due Date",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = taskDatePickerState.selectedDateMillis.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(
                    onClick = {
                        isTaskDatePickerDialogOpen = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Priority",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Priority.entries.forEach { priority ->
                    PriorityButton(
                        label = priority.title,
                        backGroundColor = priority.color,
                        borderColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.Transparent,
                        labelColor = if (priority == Priority.MEDIUM) {
                            Color.White
                        } else Color.White.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
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
                    text = relatedToSubjectText,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(
                    onClick = {
                        isSheetOpen = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text("Save")
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenTopBar(
    isTaskExists: Boolean,
    isCompleted: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = { Text("Tasks", style = MaterialTheme.typography.headlineSmall) },
        actions = {
            if (isTaskExists) {
                TaskCheckBox(
                    isComplete = isCompleted,
                    borderColor = checkBoxBorderColor
                ) {
                    onCheckBoxClick()
                }

                IconButton(
                    onClick = onDeleteButtonClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }

        }
    )
}

@Composable
fun PriorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backGroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(backGroundColor)
            .clickable { onClick() }
            .padding(5.dp) //this for between background and border
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp), // this for between the border and the text
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}

































