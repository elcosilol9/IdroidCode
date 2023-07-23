import org.gradle.api.Project
import java.io.File

const val KEY_ALIAS = "SIGNING_ALIAS"
const val AUTH_PASS = "SIGNING_AUTH_PASS"
const val AUTH_USER = "SIGNING_AUTH_USER"
const val KEY_PASS = "SIGNING_KEY_PASS"
const val KEY_STORE_PASS = "SIGNING_STORE_PASS"
const val KEY_URL = "SIGNING_URL"
const val KEY_BIN = "SIGNING_KEY_BIN"

val Project.signingKey: File
  get() = rootProject.file("IdroidCode.jks")