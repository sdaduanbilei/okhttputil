package com.scorpio.drive.domain
import com.google.gson.annotations.SerializedName


/**
 * Created by sdaduanbilei on 18-1-22.
 */
data class CityData(
		@SerializedName("pid") var pid: Int,
		@SerializedName("id") var id: Int,
		@SerializedName("name") var name: String,
		@SerializedName("py") var py: String,
		@SerializedName("items") var items: List<Item>
)

data class Item(
		@SerializedName("id") var id: Int,
		@SerializedName("name") var name: String,
		@SerializedName("iskc") var iskc: Boolean,
		@SerializedName("py") var py: String,
		@SerializedName("ids") var ids: String
)
}