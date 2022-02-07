package com.test.example.permissiontest

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.core.app.ActivityCompat

open class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            onPermissionResult(it, true)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions(getLocationPermission().manifests)
    }

    private fun checkPermissions(permissions: Array<String>) {
        return if (hasPermissions(permissions)) {
            val map = mutableMapOf<String, Boolean>()
            permissions.forEach { map[it] = true }
            onPermissionResult(map, false)
        } else {
            showDialogLocationInBackground(permissions)
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun showDialogLocationInBackground(permissions: Array<String>) {
        Toast.makeText(
            this,
            "App requires location permission to track vehicle",
            Toast.LENGTH_SHORT
        ).show()
        requestPermissionLauncher.launch(permissions)
    }

    @CallSuper
    fun onPermissionResult(permissionResult: Map<String, Boolean>, fromRequest: Boolean) {
        if (fromRequest && permissionResult.all { it.value }) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !hasPermissions(Permission.LocationAPI30.manifests)) {
                checkPermissionsWithoutPrivacyDialog(Permission.LocationAPI30.manifests)
                return
            }
            Toast.makeText(
                this,
                "Permission Granted",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Permission already allowed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkPermissionsWithoutPrivacyDialog(permissions: Array<String>) {
        requestPermissionLauncher.launch(permissions)
    }

}