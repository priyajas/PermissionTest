package com.test.example.permissiontest

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

sealed class Permission(val manifests: Array<String>) {
    @RequiresApi(Build.VERSION_CODES.Q)
    object LocationAPI29 : Permission(
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    )

    @RequiresApi(Build.VERSION_CODES.R)
    object LocationAPI30 : Permission(
        arrayOf(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    )

    object Location : Permission(
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    object SaveImage : Permission(
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
}

fun getLocationPermission(): Permission {
    return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q)
        Permission.LocationAPI29
    else
        Permission.Location
}
fun getAllLocationPermission(): Permission {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        Permission.LocationAPI29
    else
        Permission.Location
}
