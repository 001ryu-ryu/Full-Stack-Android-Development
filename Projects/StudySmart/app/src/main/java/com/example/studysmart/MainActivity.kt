package com.example.studysmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.ui.dashboard.DashBoardScreen
import com.example.studysmart.ui.session.SessionScreen
import com.example.studysmart.ui.subject.SubjectScreen
import com.example.studysmart.ui.task.TaskScreen
import com.example.studysmart.ui.theme.StudySmartTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudySmartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DestinationsNavHost(navGraph = NavGraphs.root)

                }
            }
        }
    }
}
val subjects = listOf(
    Subject(name = "English", goalHours = 16f, colors = Subject.subjectCardColors[0].map { it.toArgb() }, subjectId = 0),
    Subject(name = "Math", goalHours = 19f, colors = Subject.subjectCardColors[1].map { it.toArgb() }, subjectId = 1),
    Subject(name = "Adv Math", goalHours = 10f, colors = Subject.subjectCardColors[2].map { it.toArgb() }, subjectId = 2),
    Subject(name = "Physics", goalHours = 10f, colors = Subject.subjectCardColors[3].map { it.toArgb() }, subjectId = 3),
    Subject(name = "Chemistry", goalHours = 10f, colors = Subject.subjectCardColors[4].map { it.toArgb() }, subjectId = 4),
    Subject(name = "Chemistry", goalHours = 10f, colors = Subject.subjectCardColors[4].map { it.toArgb() }, subjectId = 4),
)

val tasks = listOf(
    Task(
        title = "Prepare Notes",
        description = "Desc",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "Eng",
        isComplete = false,
        taskId = 0,
        taskSubjectId = 4
    ),
    Task(
        title = "Finishing Chapter 2",
        description = "Desc",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 1,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),
    Task(
        title = "Finishing Chapter 3",
        description = "Desc",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "Math",
        isComplete = true,
        taskId = 2,
        taskSubjectId = 1
    ),


)

val sessions = listOf<Session>(
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "English",
        date = 12L,
        duration = 8L,
        sessionId = 0
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Mathematics",
        date = 12L,
        duration = 8L,
        sessionId = 0
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Computer Science",
        date = 12L,
        duration = 8L,
        sessionId = 0
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Computer Science",
        date = 12L,
        duration = 8L,
        sessionId = 0
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Computer Science",
        date = 12L,
        duration = 8L,
        sessionId = 0
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Computer Science",
        date = 12L,
        duration = 8L,
        sessionId = 0
    )

)