package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.dsl.module
import util.DATA_STORE_FILE_NAME

actual val platformModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                // root path of the executable desktop file, just for practicing purposes
                // in real projects better to pick a not easily accessible path
                DATA_STORE_FILE_NAME.toPath()
            }
        )
    }
}