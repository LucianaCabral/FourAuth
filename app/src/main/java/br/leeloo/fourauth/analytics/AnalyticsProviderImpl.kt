package br.leeloo.fourauth.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class AnalyticsProviderImpl: AnalyticsProvider {
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    private val TAG = "AnalyticsDebug"

    override fun logScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.ITEM_NAME, "screen")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        Log.d(TAG, "Screen View: $screenName")
    }

    override fun logEvent(eventName: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(eventName,bundle)
        Log.d(TAG, "Event Logged: $eventName")
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }
}