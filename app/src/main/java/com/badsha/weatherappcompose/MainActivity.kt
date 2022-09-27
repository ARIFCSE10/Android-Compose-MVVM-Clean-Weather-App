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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
                                Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        CircularProgressIndicator()
                                        Spacer(modifier = Modifier.padding(16.dp))
                                        Text(
                                            text = "Collecting Location Info",
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.primary
                                        )
                                    Spacer(modifier = Modifier.padding(16.dp))
                                    Button(onClick = { getLocation()}) {
                                        Text(
                                            text = "Retry",
                                            style = MaterialTheme.typography.h5,
                                            color = MaterialTheme.colors.background
                                        )
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
                        ) {
                            WeatherScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (mGPS) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Please Turn On the GPS", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Please provide precise location", Toast.LENGTH_LONG).show()
            getLocation()
        }
    }

    /// Check for location permission
    private fun getLocation(){
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onRestart() {
        super.onRestart()
        getLocation()
    }

    /// Collect lat lon from System
    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 10000
        }
        val finePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (finePermission == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location: Location? = locationResult.lastLocation
                    if (location != null) {
                        mLat = location.latitude
                        mLon = location.longitude
                        routeToWeatherScreen(mLat, mLon)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }, Looper.getMainLooper())
        }
    }

    /// Route to weather screen after getting lan, lon
    private fun routeToWeatherScreen(lat:Double, lon:Double) {
        navigateAndReplaceStartRoute(Screen.WeatherScreen.route.plus("?lat=$lat&lon=$lon"))
    }

    /// Replacement Route
    private fun navigateAndReplaceStartRoute(newHomeRoute: String) {
        navController.popBackStack(navController.graph.startDestinationId, true)
        navController.graph.setStartDestination(newHomeRoute)
        navController.navigate(newHomeRoute)
    }
}