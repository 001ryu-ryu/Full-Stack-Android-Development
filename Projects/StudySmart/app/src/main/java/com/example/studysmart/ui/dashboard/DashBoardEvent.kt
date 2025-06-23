package com.example.studysmart.ui.dashboard

import androidx.compose.ui.graphics.Color
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Task

sealed class DashBoardEvent {
    data object SaveSubject: DashBoardEvent()
    data object DeleteSession: DashBoardEvent()
    data class onDeleteSessionButtonClick(val session: Session): DashBoardEvent()
    data class onTaskIsCompleteChange(val task: Task): DashBoardEvent()
    data class onSubjectCardColorChange(val colors: List<Color>): DashBoardEvent()
    data class onSubjectNameChange(val name: String): DashBoardEvent()
    data class onGoalStudyHoursChange(val hours: String): DashBoardEvent()
}
