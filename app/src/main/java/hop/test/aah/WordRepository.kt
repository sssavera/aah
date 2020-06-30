package hop.test.aah

import androidx.lifecycle.LiveData

class WordRepository (private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    suspend fun update(word: Word) {
        wordDao.update(word)
    }
}