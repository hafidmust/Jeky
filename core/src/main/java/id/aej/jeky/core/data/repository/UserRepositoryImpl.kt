package id.aej.jeky.core.data.repository

import id.aej.jeky.core.data.source.Resource
import id.aej.jeky.core.data.source.local.datastore.JekyDataStore
import id.aej.jeky.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl constructor(
    private val dataStore: JekyDataStore
) : UserRepository {
    override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            dataStore.email.collect {
                emit(Resource.Success(it.isNotEmpty()))
            }
        }.catch {
            emit(Resource.Error(-1, it.message ?: "something wrong bruh"))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun storeEmail(email: String) {
        dataStore.storeData(JekyDataStore.EMAIL,email)
    }

    override suspend fun logout() {
        dataStore.clear()
    }

}