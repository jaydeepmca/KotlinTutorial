package com.example.kotlintutorial

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var mButtonLogin: Button
    var USERCODE: String = ""
    var PASSWORD: String = ""
    lateinit var progress_bar: ProgressBar

    lateinit var textFieldPassword: TextInputLayout
    lateinit var mEditUser: TextInputEditText
    lateinit var mEditPassword: TextInputEditText


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonLogin = findViewById(R.id.mButtonLogin)
        mEditUser = findViewById(R.id.mEditUser)
        mEditPassword = findViewById(R.id.mEditPassword)
        progress_bar = ProgressBar(applicationContext)
        textFieldPassword = findViewById(R.id.textFieldPassword)


        /*mButtonLogin.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
        })*/

        mEditPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_black_24dp, 0);
                textFieldPassword.isPasswordVisibilityToggleEnabled = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_black_24dp, 0);
                textFieldPassword.isPasswordVisibilityToggleEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {
                //edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_black_24dp, 0);
                textFieldPassword.isPasswordVisibilityToggleEnabled = true
            }

        })

        mButtonLogin?.setOnClickListener {
//            Toast.makeText(applicationContext, "Click", Toast.LENGTH_SHORT).show()
            if (mEditUser.text.toString().equals("") && mEditPassword.text.toString().equals("")) {
                mEditUser.setError("Please Enter Username")
                mEditPassword.setError("Please Enter Password")
            } else if (mEditUser.text.toString().equals("")) {
                mEditUser.setError("Please Enter Username")
            } else if (mEditPassword.text.toString().equals("")) {
                mEditPassword.setError("Please Enter Password")
            } else {
                if (isNetworkConnected()) {
                    ApiCalling()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please check internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun ApiCalling() {

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setMessage("please wait")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val service = ServiceBuilder.buildService(ApiInterface::class.java)
        val call = service.getMovies(
            mEditUser.getText().toString(),
            mEditPassword.getText().toString(),
            1
        )
        call.enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    val items = response.body()
                    if (items != null) {
                        val mess = items.MESSAGE
                        val userId = items.USERID
                        val userName = items.USERNAME
                        val empId = items.EMPID
                        val empName = items.EMPLOYEENAME
                        val plantcode = items.PLANTCODE

                        AppPreferences.empid = empId.toString()
                        AppPreferences.empname = empName
                        AppPreferences.userid = userId.toString()
                        AppPreferences.plantcode = plantcode
                        AppPreferences.username = userName
                        AppPreferences.isLogin = true
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                        /*  for (i in 0 until items.count()) {
                              // ID
                              val id = items.MESSAGE ?: "N/A"
                              Log.d("MESSAGE: ", id)
                          }*/
                        Toast.makeText(applicationContext, mess, Toast.LENGTH_SHORT).show()
                    }

                    /*   recyclerView.apply {
                           setHasFixedSize(true)
                           layoutManager = LinearLayoutManager(this@MainActivity)
                           adapter = MoviesAdapter(response.body()!!.results)
                       }*/
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                connectivityManager.activeNetwork
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}