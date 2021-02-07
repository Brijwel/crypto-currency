package com.brijwel.cryptocurrency.db

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * Created by Brijwel on 07-02-2021.
 */
class DecimalConverter {
    @TypeConverter
    fun bigDecimalToString(decimal: BigDecimal?): String = decimal?.toString() ?: BigDecimal.ZERO.toString()

    @TypeConverter
    fun stringToBigDecimal(decimal: String?): BigDecimal = decimal?.toBigDecimal()?:BigDecimal.ZERO
}