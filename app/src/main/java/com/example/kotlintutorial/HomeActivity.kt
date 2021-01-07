package com.example.kotlintutorial

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.zxing.integration.android.IntentIntegrator
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class HomeActivity : AppCompatActivity() {

    lateinit var mTvLabel: TextView
    lateinit var tv_res: TextView
    lateinit var mImagePhoto: ImageView
    lateinit var mButtonScan: Button
    lateinit var mButtonCapture: Button
    lateinit var qrScan: IntentIntegrator
    var imageName: String = ""

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val CONTENT_REQUEST = 1337
    private var output: File? = null
    private var base64Image: String? = null
    private var mCurrentPhotoPath: String? = null
    private var CREATEBY: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        builder.detectFileUriExposure()

        Toast.makeText(applicationContext, AppPreferences.username, Toast.LENGTH_SHORT).show()
        init()

        qrScan = IntentIntegrator(this)
        mButtonScan?.setOnClickListener {
            qrScan.captureActivity
            qrScan.setOrientationLocked(true)
            qrScan.setPrompt("Scan Barcode")
            qrScan.initiateScan()
        }

        mButtonCapture?.setOnClickListener {


            /*Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 */
            /*File dir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);*/
            /*

                File dir = new File( Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM), "Camera");

                if (!imageName.equals("") || !imageName.isEmpty()) {
                    output = new File(dir, imageName + ".jpg");
                }
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

                startActivityForResult(i, CONTENT_REQUEST);*/
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val directory = File(Environment.getExternalStorageDirectory(), "Qarmatek")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            output = File(directory, "$imageName.png")
            if (!output!!.exists()) {
                try {
                    output!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            verifyStoragePermissions(this)


            // Save a file: path for use with ACTION_VIEW intents


            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + output!!.getAbsolutePath()
            /*try (FileOutputStream out = new FileOutputStream(output)) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            /*try (FileOutputStream out = new FileOutputStream(output)) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output))
            startActivityForResult(i, CONTENT_REQUEST)
        }
    }

    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    fun init() {
        mButtonCapture = findViewById(R.id.mButtonCapture)
        mTvLabel = findViewById(R.id.mTvLabel)
        tv_res = findViewById(R.id.tv_res)
        mImagePhoto = findViewById(R.id.mImagePhoto)
        mButtonScan = findViewById(R.id.mButtonScan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(applicationContext, "Result Not Found", Toast.LENGTH_SHORT).show()
            } else {
                imageName = result.contents
                tv_res.setText(result.contents)
                mTvLabel.visibility = View.VISIBLE
                mTvLabel.setTextColor(Color.BLACK)
                mTvLabel.text = "Barcode Value is:"
                mButtonCapture.visibility = View.VISIBLE
                /* if (tv_res.text.toString().length >= 16) {
                     mButtonCapture.visibility = View.VISIBLE
                 } else {
                     mButtonScan.text = "Rescan Barcode"
                 }*/
            }
        }
        if (requestCode == CONTENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                mButtonCapture.visibility = View.INVISIBLE
                mButtonScan.visibility = View.INVISIBLE

                try {

                    //String compression = compressImage(mCurrentPhotoPath);
                    val mImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.contentResolver,
                        Uri.parse(mCurrentPhotoPath)
                    )
                    //mImagePhoto.setImageBitmap(mImageBitmap);
                    val nh = (mImageBitmap.height * (512.0 / mImageBitmap.width)).toInt()
                    val scaled = Bitmap.createScaledBitmap(mImageBitmap, 512, nh, true)


                    //Bitmap rotate = RotateBitmap(mImageBitmap, 90);
                    //mImagePhoto.setImageURI(Uri.parse(mCurrentPhotoPath));

                    /*  ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    rotate.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                    byte[] ba = bao.toByteArray();*/
                    val bitmapHeight = mImageBitmap.height
                    val bitmapWidth = mImageBitmap.width
                    val afdsf = (bitmapHeight / 3.6).toFloat()
                    val vxcv = (bitmapWidth / 3.6).toFloat()
                    println("RRRR Float val height: $afdsf-$vxcv")
                    val actheight = afdsf.toInt()
                    val actwidth = vxcv.toInt()
                    println("RRRR Int val height: $actheight-$actwidth")
                    //Bitmap newBitmap = Bitmap.createScaledBitmap(mImageBitmap, bitmapWidth, bitmapHeight, true);
                    val newBitmap: Bitmap = getResizedBitmap(mImageBitmap, actheight, actwidth)
                    /*  ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                    byte[] ba = bao.toByteArray();
                    base64Image = Base64.encodeToString(ba, Base64.NO_WRAP);*/base64Image =
                        encodeImage(newBitmap)

                    /* Bitmap newBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(compressImage(mCurrentPhotoPath)));
                    Bitmap rotatation = RotateBitmap(newBitmap, 90);*/
                    val rotate: Bitmap = RotateBitmap(newBitmap, 90f)
                    mImagePhoto.setImageBitmap(rotate)
                    println("DDDD Base64: $base64Image")
                    println("AAA ImageName: $imageName")
                    println("AAA LOCATIONCODE: ${AppPreferences.plantcode}")
                    println("AAA CREATEBY: ${AppPreferences.userid}")
                    //ImageStoreApi(/*imageName, base64Image, LOCATIONCODE, CREATEBY*/);
                    Handler().postDelayed({
                       /* val uploadRequest = base64Image?.let {
                            ImageUploadReq(
                                CREATEBY = AppPreferences.userid,
                                IMAGEDATA = it,
                                LOCATIONCODE = AppPreferences.plantcode,
                                SERIALNO = imageName
                            )
                        }
                        if (uploadRequest != null) {
                            uploadImage(uploadRequest)
                        }*/

                    }, 1000)


                    /*SendImage(imageName, base64Image, LOCATIONCODE, CREATEBY);*/
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

    }


    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

    fun RotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        // GET CURRENT SIZE
        val width = bm.width
        val height = bm.height
        // GET SCALE SIZE
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)
        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle("Logout")
                //set message for alert dialog
                builder.setMessage("Are you want to sure Logout?")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    AppPreferences.isLogin = false
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                //performing negative action
                builder.setNegativeButton("No") { dialogInterface, which ->


                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}

/*fun uploadImage(userData: ImageUploadReq, onResult: (ImageUploadReq?) -> Unit) {
    val retrofit = ServiceBuilder.buildService(ApiInterface::class.java)
    retrofit.uploadImage(userData).enqueue(
        object : Callback<ImageUploadReq> {
            override fun onFailure(call: Call<ImageUploadReq>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<ImageUploadReq>,
                response: Response<ImageUploadReq>
            ) {
                val uploadImage = response.body()
                onResult(uploadImage)
            }
        }
    )
}*/

