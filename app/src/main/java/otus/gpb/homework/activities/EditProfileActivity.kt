package otus.gpb.homework.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EditProfileActivity : AppCompatActivity() {

    private var imgUri: Uri? = null
    private lateinit var imageView: ImageView
    private var isCameraPermissionWasDenied: Boolean = false
    private val openCameraContract = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (!granted) {
            val dontAskAgain = !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
            if (dontAskAgain) {
                showAppSettingsDialog()
            }

            isCameraPermissionWasDenied = true;
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat))
        }
    }

    private val openImageContract = registerForActivityResult(ActivityResultContracts.GetContent()) { contentUri ->
        if (contentUri != null) {
            populateImage(contentUri)
        }
    }

    private val openEditFormContract = registerForActivityResult(
        EditProfileAndFillFormContract()
    ) { userData ->
        if (userData != null) {
            findViewById<TextView>(R.id.textview_name).text = userData.name
            findViewById<TextView>(R.id.textview_surname).text = userData.surname
            findViewById<TextView>(R.id.textview_age).text = userData.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            openEditFormContract.launch(UserDataDTO(
                findViewById<TextView>(R.id.textview_name).text.toString(),
                findViewById<TextView>(R.id.textview_surname).text.toString(),
                findViewById<TextView>(R.id.textview_age).text.toString()
            ))
        }

        imageView.setOnClickListener {
                MaterialAlertDialogBuilder(this)
                .setPositiveButton(getString(R.string.make_photo)) { _, _ ->
                    if (isCameraPermissionWasDenied) {
                        showExplonationDialog()
                    } else {
                        getCameraPermission()
                    }
                }
                .setNeutralButton(getString(R.string.choose_photo)) { _, _ ->
                    openImageContract.launch("image/*")
                }
                .show()
            }
        }

    private fun getCameraPermission() {
        openCameraContract.launch(android.Manifest.permission.CAMERA)
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private fun showExplonationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Hello")
            .setMessage("We need camera")
            .setPositiveButton(getString(R.string.give_permission)) { _, _ ->
                isCameraPermissionWasDenied = false;
                getCameraPermission()
            }
            .setNeutralButton(getString(R.string.Cancel)) { _, _ ->
                // nothing to do
            }
            .show()
    }

    private fun showAppSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage("Чтобы сделать фото, дайте разрешение на использование камеры")
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                openAppSettings()
            }
            .show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        imgUri = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (imgUri == null) {
            Toast.makeText(this, "Choose photo first", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val telegramAppName = "org.telegram.messenger"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_TEXT, UserDataDTO(
                    findViewById<TextView>(R.id.textview_name).text.toString(),
                    findViewById<TextView>(R.id.textview_surname).text.toString(),
                    findViewById<TextView>(R.id.textview_age).text.toString()
                ).toString())
                putExtra(Intent.EXTRA_STREAM, imgUri)
                setPackage(telegramAppName)
            }

            startActivity(Intent.createChooser(intent, "Share with"))
        } catch (ex : ActivityNotFoundException) {
            Toast.makeText(this, "Telegram not installed", Toast.LENGTH_SHORT).show()
        }

    }
}