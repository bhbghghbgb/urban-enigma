package com.doansgu.cafectm.util

import com.doansgu.cafectm.BACKEND_BASE_URL

/**
 * Appends the backend base url and images route to the image file name
 **/
fun backendImageRoute(imageFileName: String?) =
    if (imageFileName === null) null else "$BACKEND_BASE_URL/images/$imageFileName"
