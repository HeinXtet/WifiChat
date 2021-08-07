package com.deevvdd.wifichat.data.socket

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.InetSocketAddress
import java.net.Socket
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 07,August,2021
 */
class Client constructor(val name: String, val ip: String) {

    lateinit var clientSocket: Socket
    lateinit var ps: PrintStream

    fun sendNewMessage(message: String) {
        val st = Thread(SendThread(message))
        st.start()
        Timber.d("send new client message $message")
    }

    inner class StartCommunication(
        val onNewMessage: (name: String, message: String) -> Unit,
        val onServerClosed: () -> Unit,
        val unableConnectToServer: () -> Unit
    ) : Runnable {
        override fun run() {
            try {
                val inetAddress =
                    InetSocketAddress(ip, Utils.DEFAULT_PORT_NUMBER)
                clientSocket = Socket()
                clientSocket.connect(inetAddress, 7000)
                ps = PrintStream(clientSocket.getOutputStream())
                onNewMessage(name, "Connected to $ip !!\\n")
                ps.println("j01ne6:$name")
                val br = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                while (true) {
                    val str = br.readLine()
                    Timber.d("client read line $str")
                    if (str.equals("exit", ignoreCase = true)) {
                        onNewMessage(name, "Server Closed the Connection!")
                        Thread.sleep(2000)
                        onServerClosed()
                        break
                    }
                    Timber.d("client send message $str")
                    val senderName = str.substringBefore(":")
                    val message = str.substringAfter(":")
                    onNewMessage(senderName, message)
                }
            } catch (e: Exception) {
                Timber.d("client send error ${e.localizedMessage}")
                onNewMessage(name, "Server Closed the Connection!")
                try {
                    Thread.sleep(2000)
                } catch (exx: Exception) {
                }
                unableConnectToServer()
            }
        }
    }

    inner class SendThread(val message: String) : Runnable {
        override fun run() {
            try {
                val message = "$name: $message"
                ps.println(message)
                ps.flush()
            } catch (e: Exception) {
                Timber.d("can't send client thread ${e.localizedMessage}")
            }
        }
    }

    fun closeServer() {
        val thread = Thread {
            try {
                ps.println("Ex1+:$name")
                ps.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}
