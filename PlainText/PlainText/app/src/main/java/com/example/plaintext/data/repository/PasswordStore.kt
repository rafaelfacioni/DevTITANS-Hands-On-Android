package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow

interface PasswordDBStore {
    fun getList(): Flow<List<Password>>
    suspend fun add(password: Password): Long
    suspend fun update(password: Password)
    fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    fun isEmpty(): Flow<Boolean>
}

class LocalPasswordDBStore(
    private val passwordDao: PasswordDao
) : PasswordDBStore {

    override fun getList(): Flow<List<Password>> = passwordDao.getAllPasswords()

    override suspend fun add(password: Password): Long = passwordDao.insert(password)

    override suspend fun update(password: Password) = passwordDao.update(password)

    override fun get(id: Int): Password? = passwordDao.getPasswordById(id)

    override suspend fun save(passwordInfo: PasswordInfo) {
        val password = Password(
            id = passwordInfo.id,
            name = passwordInfo.name,
            login = passwordInfo.login,
            password = passwordInfo.password,
            notes = passwordInfo.notes
        )
        passwordDao.insert(password)
    }

    override fun isEmpty(): Flow<Boolean> = passwordDao.isEmpty()
}