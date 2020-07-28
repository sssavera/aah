package hop.test.aah

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_change.*
import kotlinx.android.synthetic.main.activity_new_word.*
import java.io.FileDescriptor
import java.io.IOException

@Suppress("NAME_SHADOWING")
class ChangeActivity: AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val price = intent.getStringExtra("price")
        val image = intent.getStringExtra("image")

        val item =  Word(name!!, desc!!, price!!, image!!)

        val contentResolver = applicationContext.contentResolver
        val flags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        val uri = Uri.parse(item.image)
        contentResolver.takePersistableUriPermission(uri, flags)

        Log.e("URI: ", uri.toString())

        val changename: TextView = findViewById(R.id.change_name)
        val changedesc: TextView = findViewById(R.id.change_desc)
        val changeprice: TextView = findViewById(R.id.change_price)
        val changeimage: ImageView = findViewById(R.id.change_image)

        Log.e("item.image", item.image)

        changename.text = name.toString()
        changedesc.text = desc.toString()
        changeprice.text = price.toString()
        changeimage.setImageURI(uri)

        save_changes.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(changename.text) ||
                TextUtils.isEmpty(changedesc.text) ||
                TextUtils.isEmpty(changeprice.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = changename.text.toString()
                val desc = changedesc.text.toString()
                val price = changeprice.text.toString()

                replyIntent.putExtra(CHANGED_NAME, name)
                    .putExtra(CHANGED_PRICE, price)
                    .putExtra(CHANGED_DESC, desc)
                    .putExtra(CHANGED_IMAGE, image)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
/*
    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }
*/
    companion object {
        const val CHANGED_NAME = "changename"
        const val CHANGED_DESC = "changedesc"
        const val CHANGED_PRICE = "changeprice"
        const val CHANGED_IMAGE = "changeimage"
    }
}