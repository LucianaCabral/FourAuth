package br.leeloo.fourauth.data.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

internal class AuthenticationProviderImpl(private val firebaseAuth: FirebaseAuth):
    AuthenticationProvider {

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.removeAuthStateListener(listener)
    }

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun signOut() {
        firebaseAuth.signOut()
    }
}