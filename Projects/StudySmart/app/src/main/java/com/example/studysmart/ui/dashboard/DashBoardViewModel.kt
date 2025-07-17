package com.example.studysmart.ui.dashboard

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.domain.repository.SessionRepository
import com.example.studysmart.domain.repository.SubjectRepository
import com.example.studysmart.domain.repository.TaskRepository
import com.example.studysmart.utiil.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository) : ViewModel() {
    private val _state = MutableStateFlow(DashBoardState())
    // combine more flows
    val state = combine( //Think of combine() like: “whenever any of these change, update the screen with all the latest values.”
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHours(),
        subjectRepository.getAllSubjects(),
        sessionRepository.getTotalSessionDuration()

    ) {
        state, subjectCount, goalHours, subjects, totalSessionDuration ->

        state.copy(
            totalSubjectCount = subjectCount,
            totalStudiedHours = totalSessionDuration.toHours(),
            subjects = subjects,
            totalGoalStudyHours = goalHours

        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashBoardState()
    )

    val tasks: StateFlow<List<Task>> = taskRepository.getAllUpcomingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentSessions: StateFlow<List<Session>> = sessionRepository.getAllSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvent(event: DashBoardEvent) {
        when(event) {
            DashBoardEvent.DeleteSession -> TODO()
            DashBoardEvent.SaveSubject -> saveSubject()
            is DashBoardEvent.onDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(session = event.session)
                }
            }

            is DashBoardEvent.onGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is DashBoardEvent.onSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColors = event.colors)
                }
            }
            is DashBoardEvent.onSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }
            is DashBoardEvent.onTaskIsCompleteChange -> {

                }
            }
        }

    private fun saveSubject() {
        viewModelScope.launch {
            subjectRepository.upsertSubject(
                subject = Subject(
                    name = state.value.subjectName,
                    goalHours = state.value.goalStudyHours.toFloatOrNull()!!,
                    colors = state.value.subjectCardColors.map { it.toArgb() }
            ))
        }
    }
}

// flow of combine()ed flows
/*
 ┌────────────┐      ┌────────────────┐       ┌──────────────┐
 │ Flow:      │      │ Flow:          │       │ Flow:        │
 │ Subjects   │      │ GoalStudyHours │       │ Sessions     │
 └────┬───────┘      └────┬───────────┘       └────┬──────────┘
      │                  │                           │
      └─────┬────────────┴─────┬─────────────────────┘
            ▼                  ▼
         ┌──────────────────────────┐
         │     combine() block      │
         │                          │
         │  → returns DashBoardState│
         └──────────┬───────────────┘
                    ▼
       ┌─────────────────────────────┐
       │   StateFlow<DashBoardState> │
       └──────────┬────────────┬─────┘
                  ▼            ▼
    ┌─────────────────┐   ┌────────────────────┐
    │ Composable A     │   │ Composable B        │
    │ Subject List     │   │ Total Goal Hours    │
    └─────────────────┘   └────────────────────┘

 */