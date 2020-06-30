package hop.test.aah

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_word.*
import java.io.FileDescriptor
import java.io.IOException

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)

        edit_image.setOnClickListener { pickImage() }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
/*
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editWordView.text.toString()
                val desc = edit_desc.text.toString()
                val price = edit_price.text.toString()

                replyIntent.putExtra(EXTRA_NAME, name)
                    .putExtra(EXTRA_PRICE, price)
                    .putExtra(EXTRA_DESC, desc)
                setResult(Activity.RESULT_OK, replyIntent)
            }

 */
            Toast.makeText(this, "Pick image", Toast.LENGTH_LONG).show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        edit_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSIONCODE)
                } else pickImage()
            } else pickImage()
        }

        val image = data?.data
        val resolver = applicationContext.contentResolver
        val flags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
         resolver.takePersistableUriPermission(image!!, flags)

        Log.e("resolv", resolver.toString())

//        edit_image.setImageURI(Uri.parse(image.toString()))
        getBitmapFromUri(image)
        edit_image.setImageBitmap(getBitmapFromUri(image))

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text) ||
                    TextUtils.isEmpty(edit_desc.text) ||
                    TextUtils.isEmpty(edit_price.text)) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show()
            } else {
                val name = editWordView.text.toString()
                val desc = edit_desc.text.toString()
                val price = edit_price.text.toString()

                replyIntent.putExtra(EXTRA_NAME, name)
                    .putExtra(EXTRA_PRICE, price)
                    .putExtra(EXTRA_DESC, desc)
                    .putExtra(EXTRA_IMAGE, resolver.toString())
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    fun pickImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGEPICKCODE)
    }

    companion object {
        private val IMAGEPICKCODE = 1000
        private val PERMISSIONCODE = 1001
        const val EXTRA_NAME = "name"
        const val EXTRA_DESC = "desc"
        const val EXTRA_PRICE = "price"
        const val EXTRA_IMAGE = "image"
    }
}
