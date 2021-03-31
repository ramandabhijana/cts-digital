package com.sestikom.ctsdigital.model

data class TestCenter(var name: String = "", var address: String = "") {
  var id: Int? = null
  var managerNames: Array<String> = arrayOf()

  companion object {
    fun centerIdForId(id: Int): String = "TSC-$id"
  }
}