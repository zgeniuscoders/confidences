package cd.zgeniuscoders.confidences.core.domain.services

import cd.zgeniuscoders.confidences.core.domain.models.Session
import kotlinx.coroutines.flow.Flow

interface SessionService {

    suspend fun add(session: Session, key: String)

    suspend fun get(key: String): Flow<Boolean>

    suspend fun set(session: Session)

    suspend fun clear(key: String)

}