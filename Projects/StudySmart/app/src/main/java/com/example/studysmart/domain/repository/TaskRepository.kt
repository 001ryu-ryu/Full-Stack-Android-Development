package com.example.studysmart.domain.repository

import com.example.studysmart.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)


    suspend fun deleteTask(taskId: Int)


    suspend fun deleteTaskBySubjectId(subjectId: Int)


    suspend fun getTaskById(taskId: Int): Task?


    fun getTasksForSubject(subjectId: Int): Flow<List<Task>>


    fun getAllTasks(): Flow<List<Task>>
}