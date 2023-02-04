package otus.gpb.homework.activities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataDTO (
    var name: String,
    var surname: String,
    var age: String
    ) : Parcelable