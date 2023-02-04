package otus.gpb.homework.activities.receiver

import androidx.core.content.ContextCompat
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val description = intent.getStringExtra("description")
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.descriptionTextView).text = description
        findViewById<TextView>(R.id.yearTextView).text = year

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.interstellar))
    }
}