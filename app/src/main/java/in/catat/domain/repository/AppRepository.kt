package `in`.catat.domain.repository

import `in`.catat.domain.datasource.AppLocalDataSource
import `in`.catat.domain.datasource.AppRemoteDataSource
import javax.inject.Inject

/**
 * Created by pertadima on 06,October,2020
 */

class AppRepository @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    private val appRemoteDataSource: AppRemoteDataSource
) {

}