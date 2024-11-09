package com.example.plaintext.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.KProperty

// Implemente a classe Password e PasswordInfo
// Password deve ser uma entidade do Room
// PasswordInfo deve ser uma classe de dados serializável

@Entity(tableName = "passwords")
@Immutable
@Parcelize
data class Password(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "notes") val notes: String? = null
) : Parcelable

@Serializable
@Parcelize
data class PasswordInfo(
    val id: Int,
    val name: String,
    val login: String,
    val password: String,
    val notes: String,
) : Parcelable {
    operator fun getValue(nothing: Nothing?, property: KProperty<*>): Password =
        Password(
            id = id,
            name = name,
            login = login,
            password = password,
            notes = notes,
        )
    }