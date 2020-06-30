package hop.test.aah

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class Word(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "image") val image: String
)