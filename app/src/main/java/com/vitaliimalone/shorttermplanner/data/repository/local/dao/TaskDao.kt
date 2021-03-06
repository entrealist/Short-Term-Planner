package com.vitaliimalone.shorttermplanner.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitaliimalone.shorttermplanner.data.repository.local.models.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun addTasks(taskEntities: List<TaskEntity>)

    @Update
    @JvmSuppressWildcards
    suspend fun updateTasks(taskEntities: List<TaskEntity>)

    @Delete
    @JvmSuppressWildcards
    suspend fun deleteTasks(taskEntities: List<TaskEntity>)

    @Query("SELECT * FROM taskentity WHERE isArchived = 0 AND dueTo BETWEEN :startDate AND :endDate")
    fun getUnarchivedTasksForPeriod(startDate: String, endDate: String): Flow<List<TaskEntity>>

    @Query("SELECT COUNT(id) FROM taskentity WHERE isArchived = 0 AND dueTo BETWEEN :startDate AND :endDate")
    fun getUnarchivedTasksCountForPeriod(startDate: String, endDate: String): Flow<Int>

    @Query("SELECT COUNT(id) FROM taskentity WHERE isArchived = 1")
    fun getArchivedTasksCount(): Flow<Int>

    @Query("SELECT * FROM taskentity WHERE isArchived = 1")
    fun getArchivedTasks(): Flow<List<TaskEntity>>

    @Query("DELETE FROM taskentity WHERE isArchived = 1")
    suspend fun deleteArchivedTasks()
}