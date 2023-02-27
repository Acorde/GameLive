package com.example.gamelivechange

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamelivechange.ui.theme.GameLiveChangeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    private lateinit var dataBase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBase = Firebase.database.reference

        val tP = dataBase.child("remainingPlayers").child("totalPlayers")
        val lostPlayers = dataBase.child("remainingPlayers").child("lostPlayers")

        val totalPlayers = mutableStateOf(0)

        lostPlayers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("IgorTest", "onDataChange ${snapshot.value?.toJsonString()}")
                totalPlayers.value = 500 - (snapshot.value as HashMap<String, String>).size
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("IgorTest", "onDataChange ${error.toJsonString()}")
            }

        })

        tP.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("IgorTest", "onDataChange ${snapshot.value?.toJsonString()}")
                //totalPlayers.value = (snapshot.value as Long).toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("IgorTest", "onDataChange ${error.toJsonString()}")
            }

        })

        setContent {
            GameLiveChangeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mTotalPlayers = totalPlayers
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                      Greeting("total players ${mTotalPlayers.value}")
                  }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "$name")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameLiveChangeTheme {
        Greeting("Android")
    }
}

fun Any.toJsonString(): String {
    return Gson().toJson(this)
}