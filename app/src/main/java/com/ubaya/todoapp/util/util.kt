package com.ubaya.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubaya.todoapp.model.TodoDatabase

const val DB_NAME = "new_todo_db"

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add priority column to the 'todo' table
        database.execSQL(
            "ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null"
        )
        // Add checklist column to the 'todo' table
        database.execSQL(
            "ALTER TABLE todo ADD COLUMN checklist INTEGER NOT NULL DEFAULT 0"
        )
    }
}

fun buildDb(context: Context): TodoDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DB_NAME
    )
        .addMigrations(MIGRATION_1_2) // Apply the migration
        .build()
}
