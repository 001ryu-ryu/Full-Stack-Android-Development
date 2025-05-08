package com.example.wishlistapp

import android.app.Application

class WishListApp : Application() {
    override fun onCreate() {
        super.onCreate() 
            Graph.provide(context = this)
        
    }

}

/*
Finally, the database initializer(ie. the Graph object) needs to be called from a global context that
will trigger it's creation at the launch of the application. In Android, we have the Application class
that is useful for initializing any global state that needs to be available throughout the app. This
class is instantiated before any other class when the process of an application is created. To use the
Application class, you are required to subclass it by creating a new class and extending it. Which is done
in this file up.
 */

/*
To ensure the project is aware of the Application class, it must be registered within the Android manifest
using the android:name attribute
 */