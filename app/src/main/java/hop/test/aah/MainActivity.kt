package hop.test.aah

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), WordListAdapter.onItemClick {

    private val newWordActivityRequest = 1
    private val changeActivity = 2
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { adapter.setWords(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequest)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.e("RESULT", requestCode.toString())

        when (requestCode) {
           newWordActivityRequest ->
               if (requestCode == newWordActivityRequest && resultCode == Activity.RESULT_OK) {
                intentData?.let { data ->
                    val word = Word(data.getStringExtra(NewWordActivity.EXTRA_NAME),
                        data.getStringExtra(NewWordActivity.EXTRA_DESC),
                        data.getStringExtra(NewWordActivity.EXTRA_PRICE),
                    data.getStringExtra(NewWordActivity.EXTRA_IMAGE))
                    wordViewModel.insert(word)
                    Log.e("old", word.toString())
                    Unit
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error",
                    Toast.LENGTH_LONG
                ).show()
            }
           changeActivity ->
               if (requestCode == changeActivity && resultCode == Activity.RESULT_OK) {
                intentData?.let { data ->
                    val word = Word(
                        data.getStringExtra(ChangeActivity.CHANGED_NAME),
                        data.getStringExtra(ChangeActivity.CHANGED_DESC),
                        data.getStringExtra(ChangeActivity.CHANGED_PRICE),
                        data.getStringExtra(ChangeActivity.CHANGED_IMAGE)
                    )
                    wordViewModel.update(word)
                    Unit
                    Log.e("update", word.toString())
                }
            } else {
            Toast.makeText(
                applicationContext,
                "returned",
                Toast.LENGTH_LONG
            ).show()
               }
        }
    }

    override fun MyClick(word: Word) {
        var intent = Intent(this@MainActivity, ChangeActivity::class.java)
        intent.putExtra("name", word.name)
            .putExtra("desc", word.desc)
            .putExtra("price", word.price)
            .putExtra("image", word.image)
        startActivityForResult(intent, changeActivity)
    }
}