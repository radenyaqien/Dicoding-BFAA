package id.radenyaqien.githubuserdicoding.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

//    companion object {
//        private var INSTANCE: UsersDatabase? = null
//        private val sLock = Any()
//
//
//
//        fun getInstance(context: Context): UsersDatabase? {
//            synchronized(sLock) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        UsersDatabase::class.java, "Sample.db"
//                    )
//                        .build()
//                }
//                return INSTANCE
//            }
//        }
//    }
}
