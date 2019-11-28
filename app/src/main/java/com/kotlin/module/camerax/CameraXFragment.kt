package com.kotlin.module.camerax

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.camerax_fragment_layout.*
import java.io.File
import java.util.concurrent.Executors

private const val REQUEST_CODE_PERMISSION = 10

class CameraXFragment : Fragment() {

    private val requestPermissions = arrayOf(Manifest.permission.CAMERA)

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camerax_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder = view_finder
        if (allPermissionGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                requestPermissions,
                REQUEST_CODE_PERMISSION
            )
        }

        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> updateTransform() }
    }

    private fun startCamera() {
        // Create configuration object for the viewFinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is update, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()

        }

        // ImageCapture configure
        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            // We don't set a resolution for image capture; instead, we
            // select a capture mode which will infer the appropriate
            // resolution based on aspect ration and requested mode
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        capture_img.setOnClickListener {
            val file = File(Environment.getExternalStorageDirectory(), "${System.currentTimeMillis()}.jpg")

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        viewFinder.post {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        Log.d("CameraXApp", msg)
                        viewFinder.post {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }

        // Bind use case to lifecycle
        // If android studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher
        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of viewFinder
        val centerX = viewFinder.width / 2F
        val centerY = viewFinder.height / 2F

        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally apply transformation to our TextureView
        viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (allPermissionGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(context!!,
                    "Permission not granted for camera",
                    Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
    }

    private fun allPermissionGranted(): Boolean = requestPermissions.all {
        return (ContextCompat.checkSelfPermission(context!!, it)
                == PackageManager.PERMISSION_GRANTED)
    }

}