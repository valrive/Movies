package com.udemy.startingpointpersonal.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import java.net.NetworkInterface
import java.util.*

object WifiUtils {

    private const val TAG = "WifiUtils"

    /**
     * Returns MAC address of the given interface name.
     * @return  mac address or empty string
     */
    @SuppressLint("MissingPermission")
    fun getMACAddress(context: Context): String? {
        var macAddress: String? = null

        try {
            // enable wifi
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiEnabled = wifiManager.isWifiEnabled
            if (!wifiEnabled) {
                wifiManager.isWifiEnabled = true
            }

            // wait until wifi be enabled
            while (!wifiManager.isWifiEnabled) {
            }

            // mac adress can be accessed directly for android lower than lollipop
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                // get list of interfaces
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())

                // first, try with wlan0
                for (intf in interfaces) {
                    if (intf.name == "wlan0") {
                        macAddress = getInterfaceMac(intf)
                    }
                }

                // if wifi mac was not found, get any other mac address
                if (macAddress == null || macAddress.isEmpty()) {

                    for (intf in interfaces) {
                        val mac = intf.hardwareAddress ?: continue

                        macAddress = intf.name + "_" + getInterfaceMac(intf)
                    }
                }
            } else {
                macAddress = wifiManager.connectionInfo.macAddress.toUpperCase()
            }

            // set back the previous wifi status
            wifiManager.isWifiEnabled = wifiEnabled

        } catch (ex: Exception) {
            Log.e("WifiUtils", ex.toString(), ex)
            macAddress = null
        }

        return macAddress
    }

    private fun getInterfaceMac(intf: NetworkInterface): String? {
        try {
            val mac = intf.hardwareAddress ?: return null

            val buf = StringBuilder()
            for (idx in mac.indices) {
                buf.append(String.format("%02X:", mac[idx]))
            }

            if (buf.isNotEmpty()) {
                buf.deleteCharAt(buf.length - 1)
            }

            return buf.toString()
        } catch (e: Exception) {
            return null
        }

    }

}
