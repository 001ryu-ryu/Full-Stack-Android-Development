package com.example.depinj

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// This base class initializes hilt
@HiltAndroidApp
class BaseClass : Application() {
}