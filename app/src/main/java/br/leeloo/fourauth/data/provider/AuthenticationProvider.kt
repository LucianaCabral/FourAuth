package br.leeloo.fourauth.data.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

internal interface AuthenticationProvider {
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    val currentUser: FirebaseUser?
    fun signOut()
}