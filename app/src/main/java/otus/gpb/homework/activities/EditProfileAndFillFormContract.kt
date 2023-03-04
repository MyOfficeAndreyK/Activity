package otus.gpb.homework.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditProfileAndFillFormContract: ActivityResultContract<UserDataDTO, UserDataDTO?>() {
    override fun createIntent(context: Context, input: UserDataDTO): Intent {
        return Intent(context, FillFormActivity::class.java).putExtra("userData", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserDataDTO? {
        if (intent == null || resultCode != Activity.RESULT_OK)
            return null

        return intent.getParcelableExtra("userData")
    }
}