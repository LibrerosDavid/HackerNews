package com.examen.hackernews.util

import android.os.SystemClock
import java.util.concurrent.TimeUnit


class RateLimiter(timeout: Int, timeUnit: TimeUnit)  {

    private var timestamps: Long? = null
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shoulFetch(): Boolean{
        val lastFetched = timestamps
        val now= now()

        if(lastFetched == null){
            timestamps = now
            return true
        }

        if(now - lastFetched > timeout){
            timestamps = now
            return true
        }

        return false

    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(){
        timestamps = null
    }
}