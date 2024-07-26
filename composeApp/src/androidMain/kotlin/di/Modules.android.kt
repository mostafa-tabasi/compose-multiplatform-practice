package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import util.DATA_STORE_FILE_NAME

actual val platformModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                androidContext().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath()
            }
        )
    }
}