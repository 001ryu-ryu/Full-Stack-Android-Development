package com.example.studysmart.ui.subject

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.sessions
import com.example.studysmart.subjects
import com.example.studysmart.tasks
import com.example.studysmart.ui.components.AddSubjectDialog
import com.example.studysmart.ui.components.CountCard
import com.example.studysmart.ui.components.DeleteDialog
import com.example.studysmart.ui.components.studySessionList
import com.example.studysmart.ui.components.tasksList
import com.example.studysmart.ui.task.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SubjectScreenRouteDestination
import com.ramcosta.composedestinations.generated.destinations.SubjectScreenRouteDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.TaskScreenRouteDestination
import com.ramcosta.composedestinations.generated.destinations.TaskScreenRouteDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SubjectScreenNavArg(
    val subjectId: Int
)

@Destination<RootGraph>(navArgs = SubjectScreenNavArg::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
) {
    SubjectScreen(
        onBackButtonClick = {navigator.navigateUp()},
        onAddTaskButtonClick = {
            val navArg = TaskScreenNavArgs(taskId = null, subjectId = -1)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        },
        onTaskCardClick = {

            val navArg = TaskScreenNavArgs(taskId = it, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit
) {

    val listState = rememberLazyListState()
    val isFABExpanded by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
        /*
        “I want isFABExpanded to be true only when the first visible item is at index 0 — i.e.,
        the user is at the top of the list.”
        So derivedStateOf ensures Compose only recomposes when this specific derived condition changes,
        instead of on every scroll pixel.
         */
    }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColors by remember { mutableStateOf(Subject.subjectCardColors.random()) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    val onSubjectNameChange = {name: String ->
        subjectName = name
    }

    val onGoalHoursChange = {hour: String ->
        goalHours = hour
    }

    val onColorChange = {colors: List<Color> ->
        selectedColors = colors
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButton = {
            isAddSubjectDialogOpen = false
        },
        selectedColors = selectedColors,
        subjectName = subjectName,
        goalHours = goalHours,
        onColorChange = onColorChange,
        onSubjectNameChange = onSubjectNameChange,
        onGoalHoursChange = onGoalHoursChange,
    )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject",
        bodyText = "Are You Sure?", // Later it will be dynamic according to goal hours
        onDismissRequest = {
            isDeleteSubjectDialogOpen = false
        },
        onConfirmButton = {
            isDeleteSubjectDialogOpen = false
        }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you don't want to keep track?",
        onDismissRequest = {
            isDeleteSessionDialogOpen = false
        },
        onConfirmButton = {
            isDeleteSessionDialogOpen = false
        }
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopAppBar(
                title = "English",
                onBackButtonClick = onBackButtonClick,
                scrollBehaviour = scrollBehavior,
                onDeleteButtonClick = {isDeleteSubjectDialogOpen = true}
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = isFABExpanded,
                onClick = onAddTaskButtonClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Task") }
            )
        }
    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            item { 
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    studiedHours = "10",
                    goalHours = "15",
                    progress = 0.75f
                )
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )
            item {
                Spacer(Modifier.height(20.dp))
            }

            tasksList(
                sectionTitle = "COMPLETED TASKS",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )


            item {
                Spacer(Modifier.height(20.dp))
            }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "I see you haven't studied yet.\n START!!!",
                sessions = sessions,
                onDeleteIconClick = {

                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreenTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {},
    onEditButtonClick: () -> Unit = {},
    scrollBehaviour: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehaviour,
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.headlineSmall)

        },
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
        actions = {
            IconButton(
                onClick = onDeleteButtonClick
            ) { 
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = null
                )
            }
            IconButton(
                onClick = onEditButtonClick
            ) { 
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = null
                )
            }
        }
    )
}


@Composable
fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
) {
    val percentageProgress = remember(progress) { // it should only calculate the value when the progress value changes
        (progress * 100).toInt().coerceIn(0, 100)
    }
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CountCard(
            modifier = modifier.weight(1f),
            headlineText = "Goal Study Hour",
            count = goalHours
        )
        Spacer(Modifier.width(10.dp))
        CountCard(
            modifier = modifier.weight(1f),
            headlineText = "Study Hours",
            count = studiedHours
        )
        Spacer(Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
            )
            CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress%")
        }
        
    }
}























