package com.ardev.idroid.common.ext

import java.io.File


val File.isAppModule get() = File(this, "app").exists()