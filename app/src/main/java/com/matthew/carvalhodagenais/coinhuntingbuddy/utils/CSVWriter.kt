package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import android.content.Context
import android.util.Log
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook

class CSVWriter(context: Context) {
    private var ctx: Context

    init {
        ctx = context
    }

    companion object {
        private const val dateKey = "date_found"
        private const val yearKey = "year"
        private const val errorKey = "error"
        private const val varietyKey = "variety"
        private const val mintMarkKey = "mint_mark"
        private const val coinTypeKey = "coin_type"
        private const val gradeKey = "grade"
        private const val regionKey = "region"

        fun createDataMap(
            dateFound: String?,
            year: String?,
            error: String?,
            variety: String?,
            mintMark: String?,
            coinType: String?,
            grade: String?,
            region: String?
        ): Map<String, Any?> {
            val map = mutableMapOf<String, Any?>(
                dateKey to String,
                yearKey to String,
                errorKey to String,
                varietyKey to String,
                mintMarkKey to String,
                coinTypeKey to String,
                gradeKey to String,
                regionKey to String
            )

            map[dateKey] = dateFound
            map[yearKey] = year
            map[errorKey] = error
            map[varietyKey] = variety
            map[mintMarkKey] = mintMark
            map[coinTypeKey] = coinType
            map[gradeKey] = grade
            map[regionKey] = region

            return map
        }
    }

    private fun strOrEmpty(str: String?): String {
        return if (str.isNullOrBlank() || str == "null") {
            ""
        } else {
            str
        }
    }

    fun write(data: List<Map<String, Any?>>): HSSFWorkbook? {
        // TODO: Get these from string resources
        val headers = listOf(
            "Date Found",
            "Coin Type",
            "Year",
            "Variety",
            "Error",
            "Mint Mark",
            "Region"
        )

        val workbook: HSSFWorkbook = HSSFWorkbook()

        try {
            if (data[0].keys.size == headers.size) {
                val sheet: HSSFSheet = workbook.createSheet()
                val headerRow: HSSFRow = sheet.createRow(0)

                // Create header
                headers.forEachIndexed { index, it ->
                    // val headerRow
                    headerRow
                        .createCell(index)
                        .setCellValue(it)
                }

                // Create body
                data.forEachIndexed { index, it ->
                    // +1 is used here to not overwrite the header
                    val row = sheet.createRow(index + 1)

                    val dateCell = row
                        .createCell(0)
                        .setCellValue(strOrEmpty(it[dateKey] as String))
                    val ctCell = row
                        .createCell(1)
                        .setCellValue(strOrEmpty(it[coinTypeKey] as String))
                    val yearCell = row
                        .createCell(2)
                        .setCellValue(strOrEmpty(it[yearKey] as String))
                    val varietyCell = row
                        .createCell(3)
                        .setCellValue(strOrEmpty(it[varietyKey] as String))
                    val errorCell = row
                        .createCell(4)
                        .setCellValue(strOrEmpty(it[errorKey] as String))
                    val mmCell = row
                        .createCell(5)
                        .setCellValue(strOrEmpty(it[mintMarkKey] as String))
                    val regionCell = row
                        .createCell(6)
                        .setCellValue(strOrEmpty(it[regionKey] as String))
                }
            }
        } catch (e: Exception) {
            Log.e(
                "CSVWRITER",
                "An error has occurred trying to write to an HSSFWorkbook file."
            )
            Log.e("CSVWRITER", "The data map might not be the correct format. Please investigate.")
            Log.e("CSVWRITER", e.toString())
            return null
        }
        return workbook
    }

    fun sendToDownloads(hssfWorkbook: HSSFWorkbook) {
        // Use context here

        // Ask for permission

        // If no permission, return false

        // If permission, go ahead with the download and return true
    }
}