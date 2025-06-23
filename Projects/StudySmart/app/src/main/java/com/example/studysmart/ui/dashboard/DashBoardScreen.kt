package com.example.studysmart.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studysmart.R
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.sessions
import com.example.studysmart.tasks
import com.example.studysmart.ui.components.AddSubjectDialog
import com.example.studysmart.ui.components.CountCard
import com.example.studysmart.ui.components.DeleteDialog
import com.example.studysmart.ui.components.SubjectCard
import com.example.studysmart.ui.components.studySessionList
import com.example.studysmart.ui.components.tasksList
import com.example.studysmart.ui.subject.SubjectScreenNavArg
import com.example.studysmart.ui.task.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SessionScreenRouteDestination
import com.ramcosta.composedestinations.generated.destinations.SubjectScreenRouteDestination
import com.ramcosta.composedestinations.generated.destinations.TaskScreenRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(start = true)
@Composable
fun DashBoardScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: DashBoardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    DashBoardScreen(
        state = state,
        onEvent = viewModel::onEvent, //“Hey Composable, here’s a reference to the onEvent() function inside viewModel. You can call it later with a DashBoardEvent when you need to.”
        onSubjectCardClick = {
            it?.let {
                val navArg = SubjectScreenNavArg(subjectId = it)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }
        },
        onTaskCardClick = {
            val navArg = TaskScreenNavArgs(taskId = it, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        },
        onStartSessionButtonClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}


@Composable
fun DashBoardScreen(
    state: DashBoardState,
    onEvent: (DashBoardEvent) -> Unit,
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionButtonClick: () -> Unit
) {



    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }




    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButton = {
            onEvent(DashBoardEvent.SaveSubject)
            isAddSubjectDialogOpen = false
        },
        selectedColors = state.subjectCardColors,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onColorChange = {onEvent(DashBoardEvent.onSubjectCardColorChange(it))},
        onSubjectNameChange = {onEvent(DashBoardEvent.onSubjectNameChange(it))},
        onGoalHoursChange = {onEvent(DashBoardEvent.onGoalStudyHoursChange(it))}
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you don't want to keep track?",
        onDismissRequest = {
            isDeleteSessionDialogOpen = false
        },
        onConfirmButton = {
            onEvent(DashBoardEvent.DeleteSession)
            isDeleteSessionDialogOpen = false
        }
    )
    
    Scaffold(
        topBar = { TopBar() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
        {
             item {
                 CountCardSection(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(12.dp),
                     subjectCount = state.totalSubjectCount,
                     studiedHours = state.totalStudiedHours.toString(),
                     goalHours = state.totalGoalStudyHours.toString()
                 )
             }

            item { 
                SubjectCardsSection(
                    subjectList = state.subjects,
                    onAddIconClick = {
                        isAddSubjectDialogOpen = true
                    },
                    onSubjectCardClick = onSubjectCardClick
                )
            }

            item {
                Button(
                    onClick = {
                        onStartSessionButtonClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text("Start Study Session!")
                }
            }

            tasksList(
                sectionTitle = "UPCOMING TASKS",
                tasks = tasks,
                onCheckBoxClick = {onEvent(DashBoardEvent.onTaskIsCompleteChange(it))},
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
                    onEvent(DashBoardEvent.onDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen = true
                }
            )
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Study Smart", style = MaterialTheme.typography.headlineMedium)
        }
    )
}

@Composable
fun CountCardSection(modifier: Modifier = Modifier, 
                     subjectCount: Int,
                     studiedHours: String,
                     goalHours: String) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
        /*The fix worked because when you place a Row inside a LazyColumn,
        the Row tries to use the minimum height needed by its children.
        If the children (your CountCards) do not have an explicit height,
        they may collapse and not be visible.

        By setting modifier = modifier.height(IntrinsicSize.Min) on the Row,
         you force the Row to take the minimum height required by its tallest child,
         ensuring the cards are visible and not collapsed.
         This makes the cards display correctly inside the LazyColumn.

         Or we could give some height in the elevated card
        */
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headlineText = "Subject Count",
            count = subjectCount.toString()
        )
        Spacer(Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headlineText = "Studied Hours",
            count = studiedHours
        )
        Spacer(Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headlineText = "Goal Study Hours",
            count = goalHours
        )

    }
}

@Composable
fun SubjectCardsSection(
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects yet. \n Add it!",
    onAddIconClick: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    val context = LocalContext.current
    Column { 
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp)
            )
            IconButton(
                onClick = {
                    onAddIconClick()

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }

        }

        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) {
                subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors.map { Color(it) }
                ) {
                    onSubjectCardClick(subject.subjectId)
                }
            }
        }
    }
}






