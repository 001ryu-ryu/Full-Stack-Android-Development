package com.example.room

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.room.room.database.ContactDataBase
import com.example.room.room.entity.Contact
import com.example.room.ui.theme.RoomTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
     lateinit var dataBase: ContactDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataBase = ContactDataBase.getDatabase(this)


        GlobalScope.launch {
            dataBase.contactDao().insertContact(Contact(0, "Mamtaz", "99999", Date(), 1))
            lifecycleScope.launch {
                dataBase.contactDao().getContacts().observe(this@MainActivity, Observer {
                    Log.d("TAG", it.toString())
                })
            }

        }
        setContent {


            RoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}
