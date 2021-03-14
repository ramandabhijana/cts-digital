package com.sestikom.ctsdigital.model

data class TestCenter(val name: String, val address: String) {
  val id: Int
  val managerNames: Array<String> = arrayOf()

  init {
    this.id = idFactory++
  }

  companion object {
    fun centerIdForId(id: Int): String = "TSC-$id"
    private var idFactory: Int = 1
  }
}