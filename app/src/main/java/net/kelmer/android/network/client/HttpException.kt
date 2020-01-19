package net.kelmer.android.network.client

import java.lang.Exception

class HttpException(val code: Int, message: String) : Exception(message)
