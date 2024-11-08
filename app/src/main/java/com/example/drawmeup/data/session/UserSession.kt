import android.content.Context
import android.content.SharedPreferences
import com.example.drawmeup.data.models.User
import java.util.Date

object UserSession {
    private const val PREF_NAME = "UserSessionPreferences"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PASSWORD = "user_password"
    private const val KEY_IS_LOGGED = "is_logged"
    private const val KEY_LAST_LOGGED = "last_logged"

    private var sharedPreferences: SharedPreferences? = null

    var user = User(0, "", "", "")
    var isLogged: Boolean = false
    var lastLogged: Date = Date()

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveSession() {
        sharedPreferences?.edit()?.apply {
            putInt(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.name)
            putString(KEY_USER_EMAIL, user.email)
            putString(KEY_USER_PASSWORD, user.password)
            putBoolean(KEY_IS_LOGGED, isLogged)
            putLong(KEY_LAST_LOGGED, lastLogged.time)
            apply()
        }
    }

    fun loadSession() {
        user = User(
            id = sharedPreferences?.getInt(KEY_USER_ID, -1) ?: -1,
            name = sharedPreferences?.getString(KEY_USER_NAME, "") ?: "",
            email = sharedPreferences?.getString(KEY_USER_EMAIL, "") ?: "",
            password = sharedPreferences?.getString(KEY_USER_PASSWORD, "") ?: ""
        )
        isLogged = sharedPreferences?.getBoolean(KEY_IS_LOGGED, false) ?: false
        lastLogged = Date(sharedPreferences?.getLong(KEY_LAST_LOGGED, Date().time) ?: Date().time)
    }

    fun clearSession() {
        sharedPreferences?.edit()?.clear()?.apply()
        user = User(0, "", "", "")
        isLogged = false
        lastLogged = Date()
    }
}