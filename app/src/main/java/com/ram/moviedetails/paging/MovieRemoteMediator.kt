package com.ram.moviedetails.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ram.moviedetails.database.MovieDatabase
import com.ram.moviedetails.model.MovieRemoteKey
import com.ram.moviedetails.model.Search
import com.ram.moviedetails.retrofit.MovieRequestApi
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieRequestApi: MovieRequestApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, Search>() {

    private val movieDao = movieDatabase.movieDao()
    private val remoteKeyDao = movieDatabase.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Search>): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = movieRequestApi.getPopularMovies(currentPage)

            val numberOfPages: Long =
                BigDecimal(response.body()?.totalResults).divide(
                    BigDecimal(response.body()?.Search?.size!!),
                    RoundingMode.UP
                ).longValueExact()

            val endOfPaginationReached = numberOfPages.toInt() == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteMovie()
                    remoteKeyDao.deleteAllRemoteKeys()
                }

                movieDao.addMovie(response.body()!!.Search)

                val keys = response.body()!!.Search.map { quote ->
                    MovieRemoteKey(
                        id = quote.Title,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeyDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Search>
    ): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.Title?.let { id ->
                remoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Search>
    ): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeyDao.getRemoteKeys(id = movie.Title)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Search>
    ): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeyDao.getRemoteKeys(id = movie.Title)
            }
    }
}