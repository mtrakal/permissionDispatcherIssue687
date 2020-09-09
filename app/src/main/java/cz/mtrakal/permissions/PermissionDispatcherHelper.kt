package cz.mtrakal.permissions

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import permissions.dispatcher.ktx.constructPermissionsRequest

/**
 *
 * @author mtrakal on 09.09.2020.
 */
object PermissionDispatcherHelper : PermissionHelper() {
    fun permissionDispatcher(activity: AppCompatActivity) {
        val context: Context = activity
        activity.constructPermissionsRequest(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            onShowRationale = { permissionRequest ->
                Log.e(PermissionHelper::class.java.simpleName, "PD: onShowRationale")
                showRationale(context, permissionDispatcher = permissionRequest)
            },
            onPermissionDenied = {
                Log.e(PermissionHelper::class.java.simpleName, "PD: onPermissionDenied")
                permissionDenied(context)
            },
            onNeverAskAgain = {
                Log.e(PermissionHelper::class.java.simpleName, "PD: onNeverAskAgain")
                showSettingsDialog(context)
            }) {
            Log.e(PermissionHelper::class.java.simpleName, "PD: onPermissionGranted")
            openCamera(context)
        }.launch()
    }
}