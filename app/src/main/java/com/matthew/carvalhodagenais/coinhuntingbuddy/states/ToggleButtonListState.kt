package com.matthew.carvalhodagenais.coinhuntingbuddy.states

class ToggleButtonListState {
    var listState = mutableListOf<String>()

    fun add(string: String) {
        listState.add(string)
    }

    fun remove(string: String) {
        listState.remove(string)
    }

    fun contains(string: String): Boolean {
        return listState.contains(string)
    }

    fun setFirst(string: String) {
        if (listState.size == 0) {
            listState.add(string)
        } else {
            listState[0] = string
        }
    }
}