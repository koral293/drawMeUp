import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.drawmeup.data.models.User
import java.io.ByteArrayOutputStream
import java.util.Date

object UserSession {
    private const val PREF_NAME = "UserSessionPreferences"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PASSWORD = "user_password"
    private const val KEY_IS_LOGGED = "is_logged"
    private const val KEY_LAST_LOGGED = "last_logged"
    private const val KEY_USER_AVATAR = "user_avatar"

    private var sharedPreferences: SharedPreferences? = null

    var user = User(0, "", "", "", Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
    var isLogged: Boolean = false
    var lastLogged: Date = Date()

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveSession() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        user.avatar.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)

        sharedPreferences?.edit()?.apply {
            putInt(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
            putString(KEY_USER_PASSWORD, user.password)
            putBoolean(KEY_IS_LOGGED, isLogged)
            putLong(KEY_LAST_LOGGED, lastLogged.time)
            putString(KEY_USER_AVATAR, base64String)
            apply()
        }
    }

    fun loadSession() {
        val avatar = sharedPreferences?.getString(KEY_USER_AVATAR, "") ?: ""
        var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
        if (!avatar.equals("")){
            val decodedBytes: ByteArray = Base64.decode(avatar, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
        user = User(
            id = sharedPreferences?.getInt(KEY_USER_ID, -1) ?: -1,
            name = sharedPreferences?.getString(KEY_USER_NAME, "") ?: "",
            email = sharedPreferences?.getString(KEY_USER_EMAIL, "") ?: "",
            password = sharedPreferences?.getString(KEY_USER_PASSWORD, "") ?: "",
            avatar = bitmap
        )
        isLogged = sharedPreferences?.getBoolean(KEY_IS_LOGGED, false) ?: false
        lastLogged = Date(sharedPreferences?.getLong(KEY_LAST_LOGGED, Date().time) ?: Date().time)
    }

    fun clearSession() {
        sharedPreferences?.edit()?.clear()?.apply()
        user = User(0, "", "", "", Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
        isLogged = false
        lastLogged = Date()
    }
}