package com.kotlin.module.camera

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.camera_fragment.*


private const val CAMERA_REQUEST_CODE = 0x001
class CameraFragment: androidx.fragment.app.Fragment() {
    private lateinit var cameraSection: CameraSection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                activity?.apply {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
                }
            } else {
                showCamera()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        }
    }

    private fun showCamera() {
        activity?.apply {
            cameraSection = CameraSection.newInstance(texture_view, this)
            lifecycle.addObserver(cameraSection)
            cameraSection.openCamera()
        }
    }

}