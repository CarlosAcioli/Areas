package com.acioli.areas.areas_service.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acioli.areas.areas_service.data.local.AreasDao

@Database(
    entities = [
        EnterpriseEntity::class,
        ContractEntity::class,
        UpEntity::class
    ],
    version = 1
)
abstract class AreasDatabase : RoomDatabase() {

    abstract val dao: AreasDao

    companion object {
        const val DATABASE_NAME = "areas_db"
    }

}