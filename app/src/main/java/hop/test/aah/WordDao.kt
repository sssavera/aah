package hop.test.aah

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Query("SELECT * from shop ORDER BY name ASC")
    fun getWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Query("SELECT * FROM shop WHERE name LIKE :name")
    fun find(name: String): LiveData<List<Word>>

    @Query("DELETE FROM shop")
    fun deleteAll()
}