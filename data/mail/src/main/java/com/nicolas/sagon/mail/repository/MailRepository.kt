package com.nicolas.sagon.mail.repository

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

class MailRepository(val context: Context) {

    val credentials = GoogleAccountCredential.usingOAuth2(context, listOf("https://mail.google.com/"))
}