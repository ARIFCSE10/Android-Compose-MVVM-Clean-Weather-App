package com.badsha.weatherappcompose.feature.data.remote.dto

import com.google.gson.annotations.SerializedName


data class WeatherDayDTO (

    @SerializedName("ts"              ) var ts             : String?  = null,
    @SerializedName("timestamp_local" ) var timestampLocal : String?  = null,
    @SerializedName("timestamp_utc"   ) var timestampUtc   : String?  = null,
    @SerializedName("datetime"        ) var datetime       : String?  = null,
    @SerializedName("snow"            ) var snow           : Double?  = null,
    @SerializedName("snow_depth"      ) var snowDepth      : Double?     = null,
    @SerializedName("precip"          ) var precip         : Double?  = null,
    @SerializedName("temp"            ) var temp           : Double?     = null,
    @SerializedName("dewpt"           ) var dewpt          : Double?     = null,
    @SerializedName("max_temp"        ) var maxTemp        : Double?  = null,
    @SerializedName("min_temp"        ) var minTemp        : Double?  = null,
    @SerializedName("app_max_temp"    ) var appMaxTemp     : Double?     = null,
    @SerializedName("app_min_temp"    ) var appMDoubleemp     : Double?     = null,
    @SerializedName("rh"              ) var rh             : Double?     = null,
    @SerializedName("clouds"          ) var clouds         : Double?     = null,
    @SerializedName("weather"         ) var weather        : WeatherIconDTO? = WeatherIconDTO(),
    @SerializedName("slp"             ) var slp            : Double?  = null,
    @SerializedName("pres"            ) var pres           : Double?     = null,
    @SerializedName("uv"              ) var uv             : Double?  = null,
    @SerializedName("max_dhi"         ) var maxDhi         : String?  = null,
    @SerializedName("vis"             ) var vis            : Double?     = null,
    @SerializedName("pop"             ) var pop            : Double?     = null,
    @SerializedName("moon_phase"      ) var moonPhase      : Double?  = null,
    @SerializedName("sunrise_ts"      ) var sunriseTs      : Double?     = null,
    @SerializedName("sunset_ts"       ) var sunsetTs       : Double?     = null,
    @SerializedName("moonrise_ts"     ) var moonriseTs     : Double?     = null,
    @SerializedName("moonset_ts"      ) var moonsetTs      : Double?     = null,
    @SerializedName("pod"             ) var pod            : String?  = null,
    @SerializedName("wind_spd"        ) var windSpd        : Double?  = null,
    @SerializedName("wind_dir"        ) var windDir        : Double?     = null,
    @SerializedName("wind_cdir"       ) var windCdir       : String?  = null,
    @SerializedName("wind_cdir_full"  ) var windCdirFull   : String?  = null

)