package com.scorpio.baselib.http.utils

import java.io.File

/**
 * Created by scorpio on 2018/2/24.
 */
class FileInput(key: String, filename: String, file: File?) {
    var key:String = ""
    var filename:String = ""
    var file: File?  = null

    init {
        this.key = key
        this.filename = filename
        this.file = file
    }


}