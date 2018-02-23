package com.scorpio.drive.domain
import com.google.gson.annotations.SerializedName



/**
 * Created by scorpio on 2018/2/1.
 */


data class HomeData(
		@SerializedName("tabBar") var tabBar: List<TabBar>,
		@SerializedName("container") var container: Container,
		@SerializedName("label") var label: List<Label>
)


data class TabBar(
		@SerializedName("id") var id: String, //896919970202509314
		@SerializedName("type") var type: Int, //0
		@SerializedName("link") var link: String, //https://m.9ji.com/
		@SerializedName("title") var title: String, //首页
		@SerializedName("sellingPoint") var sellingPoint: String,
		@SerializedName("price") var price: Int, //0
		@SerializedName("imagePath") var imagePath: List<String>,
		@SerializedName("promotionTagImg") var promotionTagImg: String,
		@SerializedName("hint") var hint: String
)

data class Container(
		@SerializedName("currentPage") var currentPage: Int, //1
		@SerializedName("totalPage") var totalPage: Int, //1
		@SerializedName("labelId") var labelId: String, //891839124172845058
		@SerializedName("floor") var floor: List<Floor>
)

data class Floor(
		@SerializedName("id") var id: String, //951815902520778753
		@SerializedName("type") var type: Int, //1
		@SerializedName("title") var title: String, //首焦
		@SerializedName("floorStyleId") var floorStyleId: String, //0
		@SerializedName("hasInterval") var hasInterval: Boolean, //false
		@SerializedName("level") var level: String, //0,1,2,3,5,6,-1
		@SerializedName("content") var content: List<Content>
)

data class Content(
		@SerializedName("id") var id: String, //951816567326351361
		@SerializedName("type") var type: Int, //3
		@SerializedName("link") var link: String, //https://m.9ji.com/event/1689.html?from=banner
		@SerializedName("title") var title: String, //春节第三阶段
		@SerializedName("sellingPoint") var sellingPoint: String,
		@SerializedName("price") var price: Int, //0
		@SerializedName("imagePath") var imagePath: String, //https://img2.ch999img.com/newstatic/771/38fee1bbae5e1c.jpg
		@SerializedName("promotionTagImg") var promotionTagImg: String,
		@SerializedName("hint") var hint: String
)


data class Label(
		@SerializedName("id") var id: String, //891839124172845058
		@SerializedName("title") var title: String, //推荐
		@SerializedName("noCache") var noCache: Boolean //false
)