package com.example.note.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApi {
    @GET("notes")suspend fun getAll(): List<NoteResponse>
    @GET("notes/{id}")suspend fun get(@Path("id") id: Int): NoteResponse

    @POST("notes")
    suspend fun create(@Body body: NoteResponse)

    @PUT("notes/{id}")
    suspend fun update(@Path("id") id: Int, @Body body: NoteResponse)

    @DELETE("notes/{id}")
    suspend fun delete(@Path("id") id: Int)
}