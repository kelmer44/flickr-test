package net.kelmer.android.common

interface Adapter<I, O> {
    fun convert(input: I): O
}

