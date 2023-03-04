package otus.gpb.homework.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FillFormActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val userData = intent.getParcelableExtra<UserDataDTO>("userData")

        if (userData != null) {
            findViewById<EditText>(R.id.name).setText(userData.name)
            findViewById<EditText>(R.id.surname).setText(userData.surname)
            findViewById<EditText>(R.id.age).setText(userData.age)
        }

        findViewById<Button>(R.id.applyButton).setOnClickListener {
            Toast.makeText(this, "Apply clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent().putExtra("userData",
                UserDataDTO(
                    findViewById<EditText>(R.id.name).text.toString(),
                    findViewById<EditText>(R.id.surname).text.toString(),
                    findViewById<EditText>(R.id.age).text.toString(),
                )
            )

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}