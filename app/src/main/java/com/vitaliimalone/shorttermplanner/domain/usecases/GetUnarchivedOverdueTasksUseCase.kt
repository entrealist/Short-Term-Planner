package com.vitaliimalone.shorttermplanner.domain.usecases

import com.vitaliimalone.shorttermplanner.data.repository.TaskRepository
import com.vitaliimalone.shorttermplanner.domain.mappers.TaskMapper
import com.vitaliimalone.shorttermplanner.domain.models.Task
import com.vitaliimalone.shorttermplanner.presentation.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.format.DateTimeFormatter

class GetUnarchivedOverdueTasksUseCase(
    private val taskRepository: TaskRepository
) {
    fun getUnarchivedOverdueTasks(): Flow<List<Task>> {
        val (startDate, endDate) = DateTimeUtils.getMinDate() to DateTimeUtils.getPreviousDayEndDate()
        val startDateString = startDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val endDateString = endDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return taskRepository.getUnarchivedTasksForPeriod(startDateString, endDateString)
            .map { it.map(TaskMapper::map) }
            .map { it.sortedBy(Task::dueTo) }
    }
}