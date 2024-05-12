package com.doansgu.cafectm.util

import com.doansgu.cafectm.BACKEND_BASE_URL

object BackendImageRoute {
    fun backendImageRoute(imageFileName: String?) =
        if (imageFileName === null) null else "$BACKEND_BASE_URL/images/$imageFileName"
}