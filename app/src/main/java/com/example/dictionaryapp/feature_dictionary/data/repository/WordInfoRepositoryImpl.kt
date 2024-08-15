package com.example.dictionaryapp.feature_dictionary.data.repository

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException



class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        // Fetch local data before making network request
        val localWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = localWordInfos))

        try {
            // Make network request
            val remoteWordInfos = api.getWordInfo(word)

            // Update local database with remote data
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

            // Fetch updated local data
            val updatedWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Success(updatedWordInfos))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = localWordInfos
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = localWordInfos
            ))
        }
    }
}
