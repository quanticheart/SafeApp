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

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build

internal open class SafeAppDialog(private val context: Context) {

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
            if (Build.MANUFACTURER == "OPPO") {
                requestAutoStartOppo()
            } else {
                context.startActivity(it)
            }
        }
    }

    private fun isCallable(context: Context, intent: Intent): Boolean {
        val list = context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        return list.size > 0
    }

    private fun requestAutoStartOppo() {
        //com.coloros.safecenter.permission.singlepage.PermissionSinglePageActivity     listpermissions
        //com.coloros.privacypermissionsentry.PermissionTopActivity                     privacypermissions
        // getPackageManager().getLaunchIntentForPackage("com.coloros.safecenter");
        if (Build.MANUFACTURER == "OPPO") {
            try {
                context.startActivity(
                    Intent().setComponent(
                        ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.permission.startup.FakeActivity"
                        )
                    )
                )
            } catch (e: java.lang.Exception) {
                try {
                    context.startActivity(
                        Intent().setComponent(
                            ComponentName(
                                "com.coloros.safecenter",
                                "com.coloros.safecenter.permission.startupapp.StartupAppListActivity"
                            )
                        )
                    )
                } catch (e1: java.lang.Exception) {
                    try {
                        context.startActivity(
                            Intent().setComponent(
                                ComponentName(
                                    "com.coloros.safecenter",
                                    "com.coloros.safecenter.permission.startupmanager.StartupAppListActivity"
                                )
                            )
                        )
                    } catch (e2: java.lang.Exception) {
                        try {
                            context.startActivity(
                                Intent().setComponent(
                                    ComponentName(
                                        "com.coloros.safe",
                                        "com.coloros.safe.permission.startup.StartupAppListActivity"
                                    )
                                )
                            )
                        } catch (e3: java.lang.Exception) {
                            try {
                                context.startActivity(
                                    Intent().setComponent(
                                        ComponentName(
                                            "com.coloros.safe",
                                            "com.coloros.safe.permission.startupapp.StartupAppListActivity"
                                        )
                                    )
                                )
                            } catch (e4: java.lang.Exception) {
                                try {
                                    context.startActivity(
                                        Intent().setComponent(
                                            ComponentName(
                                                "com.coloros.safe",
                                                "com.coloros.safe.permission.startupmanager.StartupAppListActivity"
                                            )
                                        )
                                    )
                                } catch (e5: java.lang.Exception) {
                                    try {
                                        context.startActivity(
                                            Intent().setComponent(
                                                ComponentName(
                                                    "com.coloros.safecenter",
                                                    "com.coloros.safecenter.permission.startsettings"
                                                )
                                            )
                                        )
                                    } catch (e6: java.lang.Exception) {
                                        try {
                                            context.startActivity(
                                                Intent().setComponent(
                                                    ComponentName(
                                                        "com.coloros.safecenter",
                                                        "com.coloros.safecenter.permission.startupapp.startupmanager"
                                                    )
                                                )
                                            )
                                        } catch (e7: java.lang.Exception) {
                                            try {
                                                context.startActivity(
                                                    Intent().setComponent(
                                                        ComponentName(
                                                            "com.coloros.safecenter",
                                                            "com.coloros.safecenter.permission.startupmanager.startupActivity"
                                                        )
                                                    )
                                                )
                                            } catch (e8: java.lang.Exception) {
                                                try {
                                                    context.startActivity(
                                                        Intent().setComponent(
                                                            ComponentName(
                                                                "com.coloros.safecenter",
                                                                "com.coloros.safecenter.permission.startup.startupapp.startupmanager"
                                                            )
                                                        )
                                                    )
                                                } catch (e9: java.lang.Exception) {
                                                    try {
                                                        context.startActivity(
                                                            Intent().setComponent(
                                                                ComponentName(
                                                                    "com.coloros.safecenter",
                                                                    "com.coloros.privacypermissionsentry.PermissionTopActivity.Startupmanager"
                                                                )
                                                            )
                                                        )
                                                    } catch (e10: java.lang.Exception) {
                                                        try {
                                                            context.startActivity(
                                                                Intent().setComponent(
                                                                    ComponentName(
                                                                        "com.coloros.safecenter",
                                                                        "com.coloros.privacypermissionsentry.PermissionTopActivity"
                                                                    )
                                                                )
                                                            )
                                                        } catch (e11: java.lang.Exception) {
                                                            try {
                                                                context.startActivity(
                                                                    Intent().setComponent(
                                                                        ComponentName(
                                                                            "com.coloros.safecenter",
                                                                            "com.coloros.safecenter.FakeActivity"
                                                                        )
                                                                    )
                                                                )
                                                            } catch (e12: java.lang.Exception) {
                                                                e12.printStackTrace()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}