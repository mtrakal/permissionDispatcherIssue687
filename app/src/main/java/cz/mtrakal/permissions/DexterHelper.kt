package cz.mtrakal.permissions

import android.Manifest
import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

/**
 *
 * @author mtrakal on 09.09.2020.
 */
object DexterHelper : PermissionHelper() {
    fun dexter(context: Context) {
        Dexter.withContext(context)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Log.e(PermissionHelper::class.java.simpleName, "Dexter: onPermissionGranted")
                    openCamera(context)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    if (response?.isPermanentlyDenied == true) {
                        Log.e(PermissionHelper::class.java.simpleName, "Dexter: isPermanentlyDenied")
                        // navigate user to app settings
                        showSettingsDialog(context);
                    } else {
                        Log.e(PermissionHelper::class.java.simpleName, "Dexter: onPermissionDenied")
                        permissionDenied(context)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Log.e(PermissionHelper::class.java.simpleName, "Dexter: onPermissionRationaleShouldBeShown")
                    showRationale(context, dexter = token)
                }

            }).check()
    }
}