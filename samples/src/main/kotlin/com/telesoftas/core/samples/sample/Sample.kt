package com.telesoftas.core.samples.sample

interface Sample {
    companion object {
        private val EMPTY = object : Sample {}

        fun empty() = EMPTY
    }
}