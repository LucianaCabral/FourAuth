package br.leeloo.fourauth.analytics

import android.os.Bundle

internal interface AnalyticsProvider {
    fun logScreenView(screenName: String)
    fun logEvent(eventName: String, params: Bundle? = null)
    fun setUserProperty(name: String, value: String)
}