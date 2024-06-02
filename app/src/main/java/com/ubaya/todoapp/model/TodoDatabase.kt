package com.ubaya.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.todoapp.util.DB_NAME
import com.ubaya.todoapp.util.MIGRATION_1_2

@Database(entities = [Todo::class], version = 3)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DB_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .build()

        @JvmStatic // Add this annotation to make the method accessible from Java code
        fun getDatabase(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

    fun markAsDone(context: Context, id: Int) {
        val db = getDatabase(context)
        db.todoDao().updateTodoStatus(id, 1) // Update checklist to 1 (mark as done)
    }
}
