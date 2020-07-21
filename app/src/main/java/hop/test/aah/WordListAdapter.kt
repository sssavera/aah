package hop.test.aah

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter internal constructor(
    context: Context,
    var click: onItemClick
): RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>()

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
//        val priceItemView: TextView = itemView.findViewById(R.id.priceTextView)
        val showImage: ImageView = itemView.findViewById(R.id.showImage)

        fun bind(word: Word, click: onItemClick) {
            wordItemView.text = "Name: ${word.name} Price: ${word.price}"
            showImage.setImageURI(Uri.parse(word.image))
//            priceItemView.text = word.price
            itemView.setOnClickListener {
                click.MyClick(word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.name
        holder.bind(current, click)
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size

    interface onItemClick {
        fun MyClick(word: Word)
    }
}