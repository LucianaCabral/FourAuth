package br.leeloo.fourauth.analytics

const val SCREEN_NAME = "LoginScreen"
const val BUTTON_LOGIN_CLICKED = "button_login_clicked"
const val BUTTON_REGISTER_CLICKED = "button_register_clicked"
const val BUTTON_EMAIL_CLICKED = "button_email_clicked"
const val BUTTON_PASSWORD_CLICKED = "button_password_clicked"
const val BUTTON_LOGOUT_CLICKED = "button_logout_clicked"

internal class LoginAnalytics(private val analyticsProvider: AnalyticsProvider) {

    fun screenView() {
        analyticsProvider.logScreenView(SCREEN_NAME)
//        Log.d(TAG, "Screen View: $screenName")
    }

    fun logButtonClicked(buttonType: ButtonEvent) {
        val eventName = when (buttonType) {
            ButtonEvent.LOGIN_CLICKED -> BUTTON_LOGIN_CLICKED
            ButtonEvent.REGISTER_CLICKED -> BUTTON_REGISTER_CLICKED
            ButtonEvent.EMAIL_CLICKED -> BUTTON_EMAIL_CLICKED
            ButtonEvent.PASSWORD_CLICKED -> BUTTON_PASSWORD_CLICKED
            ButtonEvent.LOGOUT_CLICKED -> BUTTON_LOGOUT_CLICKED
        }
        analyticsProvider.logEvent(eventName = eventName)
    }
}
