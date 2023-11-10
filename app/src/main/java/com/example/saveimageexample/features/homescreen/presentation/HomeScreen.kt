package com.example.saveimageexample.features.homescreen.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.auth0.android.jwt.JWT
import com.bumptech.glide.Glide
import com.example.saveimageexample.database.entity.ImageEntity
import com.example.saveimageexample.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeScreenVM = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val context= LocalContext.current
    var uri: Uri? = null
    val requestGalleryImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {myUri->
        coroutineScope.launch {
            myUri?.let {
                Log.d("URI_IS_NOT_EMPTY","WOW")
                var bitmap = Utils.getBitmapFromUri (context = context, uri = it)
                bitmap = Utils.reduceImageSize(bitmap)
                viewModel.setImage(ImageEntity(image = bitmap))
            }
        }
    }
    val requestCameraImage = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {success->
        if (success) {
            coroutineScope.launch {
                uri?.let {
                    var bitmap = Utils.getBitmapFromUri (context = context, uri = it)
                    bitmap = Utils.reduceImageSize(bitmap)
                    viewModel.setImage(ImageEntity(image = bitmap))
                }
            }
        } else {
            val resolver = context.contentResolver
            uri?.let {
                resolver.delete(it, null, null);
            }
        }
    }
    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted){
            Toast.makeText(context,"Camera permission is requiered", Toast.LENGTH_SHORT).show()
        } else{
            uri = Utils.createBlankImageFile(context)
            uri?.let {
                requestCameraImage.launch(it)
            }
        }
    }

    LaunchedEffect(true) {
        Log.d("CHANGED_IMAGE", "WOW")
        viewModel.getImage()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var image = viewModel.homeState.imageEntity.image
        if (image != null) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "some useful description",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(15.dp))


        Button(
            modifier = Modifier
                .height(50.dp),
            onClick = {
                requestGalleryImage.launch("image/*")
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    115,
                    65,
                    135
                )
            )
        )
        {
            Text(
                text = "Choose from gallery",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier
                .height(50.dp),
            onClick = {
                cameraPermission.launch(Manifest.permission.CAMERA)
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    115,
                    65,
                    135
                )
            )
        )
        {
            Text(
                text = "Take picture",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }

}

