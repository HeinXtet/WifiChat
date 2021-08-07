package com.deevvdd.wifichat.data.socket

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.ServerSocket
import java.net.Socket
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class Server {

    private var arr = arrayOfNulls<Socket>(100)
    private var num = 0
    private lateinit var serverSocket: ServerSocket
    private val ip = Utils.getIPAddress(useIPv4 = true)
    private val portNumber = Utils.DEFAULT_PORT_NUMBER

    fun sendNewMessage(
        msg: String,
        name: String,
        onNewMessage: (name: String, message: String) -> Unit
    ) {
        onNewMessage(
            name,
            msg
        )
        for (i in 0 until num) {
            val temp: Socket? = arr[i]
            val thread = SendToAll(temp!!, "$name :$msg")
            thread.start()
        }
    }

    inner class StartCommunication(
        val onNewMessage: (name: String, message: String) -> Unit,
        val serverStarted: (message: String) -> Unit
    ) :
        Runnable {
        override fun run() {
            try {
                Timber.d("Server Starting at IP : $ip")
                serverSocket = ServerSocket(portNumber)
                serverStarted("Server Starting at IP : $ip")
                for (i in 0 until num) {
                    val temp: Socket? = arr[i]
                    val thread =
                        SendToAll(
                            temp!!,
                            "message"
                        )
                    thread.start()
                }
                while (true) {
                    val clientSocket: Socket = serverSocket.accept()
                    val thread =
                        ServerThread(
                            clientSocket,
                            onNewMessage
                        )
                    arr[num++] = clientSocket
                    thread.start()
                }
            } catch (e: Exception) {
//                onNewMessage("", e.localizedMessage.orEmpty())
            }
        }
    }

    inner class SendToAll(var s: Socket, var msg: String) : Thread() {
        override fun run() {
            try {
                val ps = PrintStream(s.getOutputStream())
                ps.println(msg)
                Timber.d("server send to all $msg")
                if (msg.equals("exit", ignoreCase = true)) for (i in 0 until num) {
                    if (arr[i] === s) {
                        s.close()
                        break
                    }
                }
                ps.flush()
            } catch (e: Exception) {
            }
        }
    }

    inner class ServerThread(
        var clientSocket: Socket,
        val onNewMessage: (name: String, message: String) -> Unit
    ) :
        Thread() {
        override fun run() {
            try {
                var str: String
                val br = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                while (true) {
                    str = br.readLine()
                    Timber.d("Server received message $str")
                    if (str.startsWith("Ex1+:")) {
                        val name = str.substring(5, str.length)
                        str = "$name Left"
                        for (i in 0 until num) {
                            if (arr[i] === clientSocket) for (j in i until num - 1) arr[j] =
                                arr[j + 1]
                            num--
                        }
                        clientSocket.close()
                        for (i in 0 until num) {
                            val temp: Socket? = arr[i]
                            val thread = SendToAll(temp!!, str)
                            thread.start()
                        }
                        onNewMessage(
                            name.substringBefore(":"),
                            """
                            $str                            
                            """.trimIndent()
                        )
                        break
                    }
                    var name = ""
                    if (str.substring(0, 6) == "j01ne6") {
                        name = str.substring(7, str.length)
                        str =
                            "$name Joined"
                    }
                    onNewMessage(str.substringBefore(":"), str.substringAfter(":"))
                    for (i in 0 until num) {
                        val temp: Socket? = arr[i]
                        val thread = SendToAll(temp!!, str)
                        thread.start()
                    }
                }
            } catch (e: java.lang.Exception) {
                try {
                    var i = 0
                    while (i < num) {
                        if (arr[i] === clientSocket) {
                            var j = i
                            while (j < num - 1) {
                                arr[j] = arr[j + 1]
                                j++
                            }
                        }
                        i++
                    }
                    num--
                    clientSocket.close()
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    fun stopServer() {
        for (i in 0 until num) {
            val temp: Socket? = arr[i]
            val thread = SendToAll(temp!!, "exit")
            thread.start()
        }
        try {
            Timber.d("Server: Stop")
            serverSocket.close()
        } catch (e: java.lang.Exception) {
        }
    }
}
