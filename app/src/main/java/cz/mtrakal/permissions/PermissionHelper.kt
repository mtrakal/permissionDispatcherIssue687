package cz.mtrakal.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.PermissionToken

abstract class PermissionHelper {
    internal fun showSettingsDialog(context: Context) {
        Log.e(PermissionHelper::class.java.simpleName, "showSettingsDialog")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings(context)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    internal fun showRationale(context: Context, permissionDispatcher: permissions.dispatcher.PermissionRequest? = null, dexter: PermissionToken? = null) = AlertDialog.Builder(context).apply {
        setTitle("Camera permission")
        setMessage("Camera permission is needed to take pictures of your cat")
        setNegativeButton(android.R.string.ok) { dialog, which ->
            dexter?.continuePermissionRequest()
            permissionDispatcher?.proceed()
            dialog.cancel()
        }
    }.show().also {
        Log.e(PermissionHelper::class.java.simpleName, "showRationale")
    }

    // navigating user to app settings
    internal fun openSettings(context: Context) {
        Log.e(PermissionHelper::class.java.simpleName, "openSettings")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    internal fun openCamera(context: Context) {
        Log.e(PermissionHelper::class.java.simpleName, "openCamera")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        context.startActivity(intent)
    }

    internal fun permissionDenied(context: Context) {
        Log.e(PermissionHelper::class.java.simpleName, "permissionDenied")
    }
}
