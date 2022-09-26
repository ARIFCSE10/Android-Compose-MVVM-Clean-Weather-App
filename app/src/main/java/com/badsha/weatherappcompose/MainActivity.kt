package com.badsha.weatherappcompose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.badsha.weatherappcompose.feature.presentation.Screen
import com.badsha.weatherappcompose.feature.presentation.weatherScreen.WeatherScreen
import com.badsha.weatherappcompose.ui.theme.WeatherAppComposeTheme
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var mGPS: Boolean = false
    private lateinit var mLocationManager: LocationManager

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLat: Double = 0.0
    private var mLon: Double = 0.0

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocation()
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LocationScreen.route
                    ) {
                        composable(route = Screen.LocationScreen.route){
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                        composable(
                            route = Screen.WeatherScreen.route.plus("?lat={lat}&lon={lon}"),
                            arguments = listOf(
                                navArgument("lat") {
                                    type = NavType.FloatType
                                },
                                navArgument("lon") {
                                    type = NavType.FloatType
                                },
                            )
                        ) { backStackEntry ->
                            val lat = backStackEntry.arguments?.getFloat("lat") ?: 0.0
                            val lon = backStackEntry.arguments?.getFloat("lon") ?: 0.0
                            WeatherScreen(navController = navController,lat = lat.toDouble(),lon = lon.toDouble()).apply {

                            }
                        }
//                        composable(route = Screen.WeatherScreen.route) {
//                            WeatherScreen(navController = navController, )
//                        }
                    }
                }
            }
        }


    }

    private fun getLocation(){
        isLocationPermissionGranted()
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (mGPS) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Please Turn On the GPS", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (mGPS) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Please Turn On the GPS", Toast.LENGTH_LONG).show()
        }
    }


    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            false
        } else {
            true
        }
    }

    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 10000
        }
        val permission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        mLat = location.latitude
                        mLon = location.longitude
                        fusedLocationClient.removeLocationUpdates(this)
                        routeToWeatherScreen(mLat, mLon)
                    }
                }
            }, Looper.getMainLooper())
        }
    }

    private fun routeToWeatherScreen(lat:Double, lon:Double) {
        navController.navigate(route = Screen.WeatherScreen.route.plus("?lat=$lat&lon=$lon"))
    }
}