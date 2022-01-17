package makhonko.examBroad

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val requestCode = 314
    private val neededPermissions =
        arrayOf(Manifest.permission.BROADCAST_SMS,
            Manifest.permission.RECEIVE_SMS)
    private val broadcastReceiver = MyBroadcastReceiver()
    private val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    override fun onCreate(savedInstanceState: Bundle?) {
        checkAllGranted()
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
    }

    private fun checkAllGranted() {
        val allGranted = neededPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!allGranted) {
            requestPermissions(neededPermissions, requestCode)
        }
    }

    override fun onStart() {
        registerReceiver(broadcastReceiver, intentFilter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == this.requestCode) {
            checkAllGranted()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}