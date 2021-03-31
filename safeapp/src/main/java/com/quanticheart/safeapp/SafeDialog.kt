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
 *  * Copyright(c) Developed by John Alves at 2020/3/20 at 11:49:30 for quantic heart studios
 *
 */

@file:Suppress("unused")

package com.quanticheart.safeapp

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.checkbox_layout.view.*

internal class SafeDialog(context: Context) : SafeAppDialog(context) {
    init {
        if (openDialog()) {
            val checkBoxView: View = View.inflate(context, R.layout.checkbox_layout, null)
            val checkBox = checkBoxView.checkboxSafeApp
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    notShowMsgAgain()
                }
            }
            checkBox.text = context.getString(R.string.label_dont_show_again)
            val dialog = AlertDialog.Builder(context)
                .setTitle(getAppName())
                .setIcon(getAppIcon())
                .setMessage(context.getString(R.string.msg_activy_safeapp, getAppName()))
                .setView(checkBoxView)
                .setNegativeButton(context.getString(R.string.label_not_now)) { dialog, _ -> dialog.dismiss() }
//            if (Build.MANUFACTURER != "OPPO")
            dialog.setPositiveButton(context.getString(R.string.label_open)) { _, _ -> callIntentSafeApp() }

            dialog.show()
        }
    }
}