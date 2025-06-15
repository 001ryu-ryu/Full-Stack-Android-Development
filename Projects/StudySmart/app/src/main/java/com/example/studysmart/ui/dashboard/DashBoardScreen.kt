package com.example.studysmart.ui.dashboard

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysmart.R
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.ui.components.CountCard
import com.example.studysmart.ui.components.SubjectCard
import com.example.studysmart.ui.components.studySessionList
import com.example.studysmart.ui.components.tasksList
import com.example.studysmart.ui.theme.StudySmartTheme

@Composable
fun DashBoardScreen(modifier: Modifier = Modifier) {
    val subjects = listOf(
        Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0], subjectId = 0),
        Subject(name = "Math", goalHours = 10f, colors = Subject.subjectCardColors[1], subjectId = 1),
        Subject(name = "Adv Math", goalHours = 10f, colors = Subject.subjectCardColors[2], subjectId = 2),
        Subject(name = "Physics", goalHours = 10f, colors = Subject.subjectCardColors[3], subjectId = 3),
        Subject(name = "Chemistry", goalHours = 10f, colors = Subject.subjectCardColors[4], subjectId = 4),
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
        )

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
        )
    )
    
    Scaffold(
        topBar = { TopBar() }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        )
        {
             item {
                 CountCardSection(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(12.dp),
                     subjectCount = 0,
                     studiedHours = "10",
                     goalHours = "15"
                 )
             }

            item { 
                SubjectCardsSection(
                    subjectList = subjects
                )
            }

            item {
                Button(
                    onClick = {
                        
                    },
                    modifier = modifier.fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text("Start Study Session!")
                }
            }

            tasksList(
                sectionTitle = "UPCOMING TASKS",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
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
    emptyListText: String = "You don't have any subjects yet. \n Add it!"
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
                    Toast.makeText(context, "Hehe, not yet!", Toast.LENGTH_LONG).show()
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
                    gradientColors = subject.colors
                ) {
                    Toast.makeText(context, "Not yet Bro!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}






