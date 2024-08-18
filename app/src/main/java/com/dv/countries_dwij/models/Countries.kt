package com.dv.countries_dwij.models

import java.util.UUID

data class Countries(val name: Name, val flags: Flags, val isFavourite: Boolean = false) {
}