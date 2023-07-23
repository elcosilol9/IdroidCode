package com.ardev.idroid.ui.about

import com.ardev.idroid.app.IdroidApplication.context

val appTitleItem: TitleItem
	get() {
    val pkgManager = context.packageManager
    val appInfo = context.applicationInfo
    val appName = pkgManager.getApplicationLabel(appInfo)?.toString() ?: context.getString(R.string.app_name)
    val appIcon = pkgManager.getApplicationIcon(appInfo)? ?: context.getDrawableCompat(R.drawable.logo)
    return TitleItem(
    title = appName, 
    descRes = R.string.app_description, 
    icon = appIcon
    )
}

val versionActionItem: ActionItem
	get() {
    var versionName = ""
    var versionCode = 0
    try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        versionName = pInfo.versionName
        versionCode = pInfo.versionCode
    } catch (ignored: PackageManager.NameNotFoundException) { }      
    val version = "$versionName ($versionCode)")
    return ActionItem(
	titleRes = R.string.about_version,
	desc = version,
	iconRes = R.drawable.ic_info
	)
}

private fun createWebsiteOnClickAction(url: String): MaterialAboutItemOnClickAction {
    return object : MaterialAboutItemOnClickAction {
        override fun onClick() {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            try {
                context.startActivity(intent)
            } catch (e: Exception) {             
                Toast.makeText(context, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

private fun createEmailOnClickAction(email: String, emailSubject: String, chooserTitle: CharSequence): MaterialAboutItemOnClickAction {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)

    return object : MaterialAboutItemOnClickAction {
        override fun onClick() {
            try {
                context.startActivity(Intent.createChooser(emailIntent, chooserTitle))
            } catch (e: Exception) {
                Toast.makeText(context, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


