package br.leeloo.fourauth.data.source

import br.leeloo.fourauth.data.exceptions.AuthExceptions
import br.leeloo.fourauth.data.mapper.AuthUserMapper
import br.leeloo.fourauth.data.mapper.AuthUserMapper.toUserResponse
import br.leeloo.fourauth.data.provider.AuthenticationProvider
import br.leeloo.fourauth.domain.model.User
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

internal class AuthUserDataSourceImpl(
    private val authenticationProvider: AuthenticationProvider,
    private val authUserMapper: AuthUserMapper
) : AuthUserDataSource {

    init {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
        }
    }
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override  fun login(email: String, password: String):
            Flow<Result<User>> = flow {
        try {
            val authResult = authenticationProvider.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw AuthExceptions.firebaseUserNullException()
            val userResponse = firebaseUser.toUserResponse()
            val user = authUserMapper.run { userResponse.toDomain() }
            emit(Result.success(user))
        } catch (e: Exception) {
            emit(Result.failure(AuthExceptions.unknownAuthenticationException()))
        } catch(e:Exception) {
            emit(Result.failure(AuthExceptions.userNotAuthenticatedException()))
        }
    }.flowOn(Dispatchers.IO)

    override fun register(email: String, password: String): Flow<Result<User>> = flow {
        try {
            val authResult = authenticationProvider.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw AuthExceptions.firebaseUserNullException()
            val userResponse = firebaseUser.toUserResponse()
            val user = authUserMapper.run { userResponse.toDomain() }
            emit(Result.success(user))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override  fun logout() {
        authenticationProvider.signOut()
        authStateListener?.let { authenticationProvider.removeAuthStateListener(it) }
        authStateListener = null
    }
}