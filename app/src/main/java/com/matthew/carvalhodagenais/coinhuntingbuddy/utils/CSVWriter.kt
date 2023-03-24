package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class CSVWriter(context: Context) {
    private var ctx: Context
    private val fileName = "crhb_finds_export.xlsx"

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

    fun write(data: List<Map<String, Any?>>): XSSFWorkbook? {
        val headers = listOf(
            ctx.getString(R.string.date_header_xlsx),
            ctx.getString(R.string.ct_header_xlsx),
            ctx.getString(R.string.year_header_xlsx),
            ctx.getString(R.string.mm_header_xlsx),
            ctx.getString(R.string.variety_header_xlsx),
            ctx.getString(R.string.error_header_xlsx),
            ctx.getString(R.string.grade_header_xlsx),
            ctx.getString(R.string.region_header_xlsx),
        )

        val workbook= XSSFWorkbook()

        if (data.isEmpty()) {
            return null
        }

        try {
            if (data[0].keys.size == headers.size) {
                val sheet: XSSFSheet = workbook.createSheet("Finds")
                val headerRow: XSSFRow = sheet.createRow(0)

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
                    val mmCell = row
                        .createCell(3)
                        .setCellValue(strOrEmpty(it[mintMarkKey] as String))
                    val varietyCell = row
                        .createCell(4)
                        .setCellValue(strOrEmpty(it[varietyKey] as String))
                    val errorCell = row
                        .createCell(5)
                        .setCellValue(strOrEmpty(it[errorKey] as String))
                    val gradeCell = row
                        .createCell(6)
                        .setCellValue(strOrEmpty(it[gradeKey] as String))
                    val regionCell = row
                        .createCell(7)
                        .setCellValue(strOrEmpty(it[regionKey] as String))
                }
            }
        } catch (e: Exception) {
            Log.e(
                "CSVWRITER",
                "An error has occurred trying to write to an XSSFWorkbook file."
            )
            Log.e("CSVWRITER", "The data map might not be the correct format. Please investigate.")
            e.printStackTrace()
            return null
        }
        return workbook
    }

    fun hasPermissions(): Boolean {
        return (
            ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) && (
            ContextCompat.checkSelfPermission(
                ctx,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    fun sendToDownloads(xssfWorkbook: XSSFWorkbook): Boolean {
        return if (hasPermissions()) {
            try {
                val filePath = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    fileName
                )

                if (!filePath.exists()) {
                    filePath.createNewFile()
                }

                val fos = FileOutputStream(filePath)
                xssfWorkbook.write(fos)

                // Cleanup the stream
                fos.flush()
                fos.close()
                xssfWorkbook.close()

                true
            } catch (e: Exception) {
                Log.e(
                    "CSVWRITER",
                    "An error has occurred trying to write to an XSSFWorkbook file."
                )
                Log.e("CSVWRITER", "Could not write file to downloads directory.")
                e.printStackTrace()
                false
            }
        } else {
            Log.d("CSVWRITER", "Permission Denied.")
            false
        }
    }
}