/*
 *
 *  *                                     /@
 *  *                      __        __   /\/
 *  *                     /==\      /  \_/\/
 *  *                   /======\    \/\__ \__
 *  *                 /==/\  /\==\    /\_|__ \
 *  *              /==/    ||    \=\ / / / /_/
 *  *            /=/    /\ || /\   \=\/ /
 *  *         /===/   /   \||/   \   \===\
 *  *       /===/   /_________________ \===\
 *  *    /====/   / |                /  \====\
 *  *  /====/   /   |  _________    /      \===\
 *  *  /==/   /     | /   /  \ / / /         /===/
 *  * |===| /       |/   /____/ / /         /===/
 *  *  \==\             /\   / / /          /===/
 *  *  \===\__    \    /  \ / / /   /      /===/   ____                    __  _         __  __                __
 *  *    \==\ \    \\ /____/   /_\ //     /===/   / __ \__  ______  ____ _/ /_(_)____   / / / /__  ____ ______/ /_
 *  *    \===\ \   \\\\\\\/   ///////     /===/  / / / / / / / __ \/ __ `/ __/ / ___/  / /_/ / _ \/ __ `/ ___/ __/
 *  *      \==\/     \\\\/ / //////       /==/  / /_/ / /_/ / / / / /_/ / /_/ / /__   / __  /  __/ /_/ / /  / /_
 *  *      \==\     _ \\/ / /////        |==/   \___\_\__,_/_/ /_/\__,_/\__/_/\___/  /_/ /_/\___/\__,_/_/   \__/
 *  *        \==\  / \ / / ///          /===/
 *  *        \==\ /   / / /________/    /==/
 *  *          \==\  /               | /==/
 *  *          \=\  /________________|/=/
 *  *            \==\     _____     /==/
 *  *           / \===\   \   /   /===/
 *  *          / / /\===\  \_/  /===/
 *  *         / / /   \====\ /====/
 *  *        / / /      \===|===/
 *  *        |/_/         \===/
 *  *                       =
 *  *
 *  * Copyright(c) Developed by John Alves at 2020/3/21 at 11:56:26 for quantic heart studios
 *
 */

@file:Suppress("unused")

package com.quanticheart.safeapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable


open class SafeAppDialog(private val context: Context) {

    private var mIntent: Intent? = null
    private var appName: String? = null
    private var appIcon: Drawable? = null

    protected fun openDialog(): Boolean = context.showDialog()

    protected fun notShowMsgAgain() = context.notShowDialogAgain()

    protected fun getAppName(): String = appName ?: "SafeApp"

    protected fun getAppIcon(): Drawable? = appIcon

    init {
        for (intent in IntentsPackagesConstants.getPowerManagerIntents()) {
            if (isCallable(context, intent)) {
                try {
                    mIntent = intent

                    val packages: List<ResolveInfo> =
                        context.packageManager.queryIntentActivities(intent, 0)

                    for (res in packages) {
                        appName = res.loadLabel(context.packageManager)?.toString()
                        appIcon = getAppIconByPackageName(res.activityInfo.packageName)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (mIntent == null) {
            notShowMsgAgain()
        }
    }

    private fun getAppIconByPackageName(packageName: String): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    protected fun callIntentSafeApp() {
        mIntent?.let {
            context.startActivity(it)
        }
    }

    private fun isCallable(context: Context, intent: Intent): Boolean {
        val list = context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return list.size > 0
    }
}