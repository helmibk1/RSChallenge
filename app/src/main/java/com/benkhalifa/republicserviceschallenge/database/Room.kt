package com.benkhalifa.republicserviceschallenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface TransportDao {

    @Query("select * from databasedriver")
    fun getDriversLiveData(): LiveData<List<DatabaseDriver>>

    @Query("select * from databaseroute")
    suspend fun getRoutes(): List<DatabaseRoute>

    @Query("select * from databasedriver")
    suspend fun getDrivers(): List<DatabaseDriver>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDrivers(drivers: List<DatabaseDriver>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRoutes(routes: List<DatabaseRoute>)


    @Query("SELECT COUNT(*) FROM databasedriver")
    suspend fun getDriverCount(): Int

    @Query("SELECT COUNT(*) FROM databaseroute")
    suspend fun getRouteCount(): Int

    @Query("DELETE FROM databasedriver")
    suspend fun clearAllDrivers()

    @Query("DELETE FROM databaseroute")
    suspend fun clearAllRoutes()
}

@Database(entities = [DatabaseDriver::class, DatabaseRoute::class], version = 1)
abstract class TransportDatabase : RoomDatabase() {
    abstract fun transportDao(): TransportDao
}

