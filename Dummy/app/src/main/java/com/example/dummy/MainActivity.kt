@file:Suppress("DEPRECATION")

package com.example.dummy

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.FaceDetector
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dummy.databinding.ActivityMainBinding
import java.net.URI


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val camerarequest = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.cameraclick.setOnClickListener {
            if (checkPermission()) {
                openCamera()
            }
            else
            {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),camerarequest)
            }

        }

    }



    private fun openCamera() {

        val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, camerarequest)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode)
        {
            camerarequest->{

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PermissionGranted", Toast.LENGTH_SHORT).show()
                    openCamera()
            }
        }
    }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == camerarequest && resultCode == RESULT_OK) {

            if (data != null) {
                binding.imgone.setImageURI(data.data)
            }
        }

    }

    fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        return false
        } else {
            return true
        }
    }

}