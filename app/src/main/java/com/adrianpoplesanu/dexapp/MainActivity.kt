package com.adrianpoplesanu.dexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var webSocket : WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateWebSocket()

        val signinButton = findViewById<Button>(R.id.signinButton)
        signinButton.setOnClickListener() {
            signinButtonClickEvent(it)
        }
    }

    private fun signinButtonClickEvent(view : View) {
        Log.d("DexApp", "signin button clicked!")
        Log.d("DexApp", view.toString())
        if (!webSocket?.send("check connection still active...")!!) {
            initiateWebSocket()
        }
        sendToServer("connect", buildCredentials())
    }

    private fun initiateWebSocket() {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url("ws://192.168.0.108:8092").build()
        val wsListener = EchoWebSocketListener ()
        webSocket = client.newWebSocket(request, wsListener)
    }

    private fun sendToServer(operation: String?, message: JSONObject?) {
        val data : JSONObject = JSONObject()
        data.put("operation", operation)
        data.put("data",  message)
        val text = data.toString()
        sendRawDataToServer(text)
    }

    private fun sendRawDataToServer(text: String) {
        webSocket?.send(text)
    }

    private fun buildCredentials() : JSONObject {
        val usernameTextInput = findViewById<TextView>(R.id.usernameText)
        val passwordTextInput = findViewById<TextView>(R.id.passwordText)
        val data : JSONObject = JSONObject()
        data.put("username", usernameTextInput.text.toString())
        data.put("password", passwordTextInput.text.toString())
        return data
    }

    private class EchoWebSocketListener : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket?, text: String?) {
            Log.d("EchoWebSocketListener", text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.d("EchoWebSocketListener", t.message)
        }
    }
}
