package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.GoogleMapsButton).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        findViewById<Button>(R.id.SendEmailButton).setOnClickListener {
            val mail = Uri.parse("mailto: android@otus.ru?subject=\"Hello, World!\"&body=\"Я учусь на Otus. Изучаю Android!\"")
            val mailIntent = Intent(Intent.ACTION_SENDTO, mail)
            try {
                startActivity(mailIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Нет активити, отправляющего письма!", Toast.LENGTH_SHORT).show()
            }

        }

        findViewById<Button>(R.id.OpenReceiverButton).setOnClickListener {
            val receiverIntent = Intent(Intent.ACTION_SEND)
            receiverIntent.type = "text/plain"
            receiverIntent.addCategory(Intent.CATEGORY_DEFAULT)
            receiverIntent.apply {
                putExtra("title", "Интерстеллар")
                putExtra("year", "2014")
                putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            }

            try {
                startActivity(receiverIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Нет активити, получающего данные!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}