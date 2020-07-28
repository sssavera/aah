package hop.test.aah

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 3)
abstract class WordRoomDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "shop_db"
                ).fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.wordDao())
                    }
                }
            }
        }

        fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            var item = Word("Apple iPod touch 5 32Gb",
            "5 шт",
            "8888",
            "")
            wordDao.insert(item)

            item = Word("Samsung Galaxy S Duos S7562",
                "2 шт",
                "7230",
                "")
            wordDao.insert(item)

            item = Word("Canon EOS 600D Kit",
                "9 шт",
                "15659",
                "")
            wordDao.insert(item)

            item = Word("Samsung Galaxy Tab 2 10.1 P5100 16Gb",
                "9 шт",
                "13290",
                "")
            wordDao.insert(item)

            item = Word("PocketBook Touch",
                "2 шт",
                "5197",
                "")
            wordDao.insert(item)

            item = Word("Nikon D3100 Kit",
                "4 шт",
                "12190",
                "")
            wordDao.insert(item)
        }
    }
}