package com.heshmat.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(steam: InputStream): List<T>
}