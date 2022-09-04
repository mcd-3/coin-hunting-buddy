package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

class ArrayTools {
    companion object {
        /**
         * Finds the first index of an array where an item is not matching
         *
         * @param array Array<Int> - Array to check
         * @param item Int - Item to match against
         * @return Int? Index found, null if nothing is found
         */
        fun firstIndexWhereNot(array: Array<Int>, item: Int): Int? {
            array.forEachIndexed { index, it ->
                if (it != item) {
                    return index
                }
            }
            return null
        }

        /**
         * Finds the last index of an array where an item is not matching
         *
         * @param array Array<Int> - Array to check
         * @param item Int - Item to match against
         * @return Int? Index found, null if nothing is found
         */
        fun lastIndexWhereNot(array: Array<Int>, item: Int): Int? {
            val arrayReversed = array.reversedArray()
            arrayReversed.forEachIndexed { index, it ->
                if (it != item) {
                    return index
                }
            }
            return null
        }
    }
}