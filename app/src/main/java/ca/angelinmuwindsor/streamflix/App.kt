package ca.angelinmuwindsor.streamflix

import android.app.Application
import android.util.Log
import io.firekast.Firekast

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Firekast.setLogLevel(Log.VERBOSE)
        Firekast.initialize(this, "CLIENT_KEY", "APPLICATION_ID")
    }

}