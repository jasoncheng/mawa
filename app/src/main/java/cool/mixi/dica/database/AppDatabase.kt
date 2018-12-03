package cool.mixi.dica.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import cool.mixi.dica.App
import cool.mixi.dica.bean.Consts
import cool.mixi.dica.bean.Meta
import cool.mixi.dica.database.dao.MetaDao
import cool.mixi.dica.util.TimestampConverter
import java.util.*

@Database(entities = [Meta::class], version = 2)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun metaDao(): MetaDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return db ?: synchronized(this) {
                db = Room.databaseBuilder(
                    App.instance.applicationContext,
                    AppDatabase::class.java,
                    Consts.DB_NAME
                ).build()
                db!!
            }
        }

        fun getDefaultExpire(): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 0 - Consts.TTL_META)
            return calendar.time
        }
    }
}