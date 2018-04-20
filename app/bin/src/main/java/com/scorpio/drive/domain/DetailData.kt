package com.scorpio.drive.domain
import com.google.gson.annotations.SerializedName


/**
 * Created by scorpio on 2018/3/7.
 */

data class DetailData(
		@SerializedName("banner") var banner: Banner,
		@SerializedName("promotionImage") var promotionImage: PromotionImage,
		@SerializedName("coupon") var coupon: Coupon,
		@SerializedName("appIsFavorPrice") var appIsFavorPrice: Boolean,
		@SerializedName("appPrice") var appPrice: Int,
		@SerializedName("profile") var profile: Profile,
		@SerializedName("instalment") var instalment: Instalment,
		@SerializedName("promotions") var promotions: List<Promotion>,
		@SerializedName("supportServices") var supportServices: List<SupportService>,
		@SerializedName("recommend") var recommend: List<Recommend>,
		@SerializedName("reminder") var reminder: String
)

data class Banner(
		@SerializedName("imagePath") var imagePath: String,
		@SerializedName("link") var link: String
)

data class SupportService(
		@SerializedName("id") var id: Int,
		@SerializedName("link") var link: String,
		@SerializedName("title") var title: String,
		@SerializedName("description") var description: String
)

data class Recommend(
		@SerializedName("name") var name: String,
		@SerializedName("label") var label: Int,
		@SerializedName("link") var link: String,
		@SerializedName("linkText") var linkText: String,
		@SerializedName("list") var list: List<Item3>
)

data class Item3(
		@SerializedName("ppid") var ppid: Int,
		@SerializedName("url") var url: String,
		@SerializedName("name") var name: String,
		@SerializedName("imagePath") var imagePath: String,
		@SerializedName("price") var price: String
)

data class Instalment(
		@SerializedName("name") var name: String,
		@SerializedName("description") var description: String,
		@SerializedName("detail") var detail: List<Detail>
)

data class Detail(
		@SerializedName("name") var name: String,
		@SerializedName("isBaitiao") var isBaitiao: Boolean,
		@SerializedName("imagePath") var imagePath: String,
		@SerializedName("list") var list: List<Item1>
)

data class Item1(
		@SerializedName("company") var company: Int,
		@SerializedName("description") var description: String,
		@SerializedName("tips") var tips: String,
		@SerializedName("monthPays") var monthPays: String,
		@SerializedName("monthPay") var monthPay: Double,
		@SerializedName("month") var month: Int
)

data class Promotion(
		@SerializedName("title") var title: String,
		@SerializedName("description") var description: String,
		@SerializedName("link") var link: String,
		@SerializedName("type") var type: Int,
		@SerializedName("options") var options: List<Any>
)

data class Profile(
		@SerializedName("title") var title: String,
		@SerializedName("linkText") var linkText: String,
		@SerializedName("link") var link: String
)

data class Coupon(
		@SerializedName("isCoupon") var isCoupon: Boolean,
		@SerializedName("code") var code: String,
		@SerializedName("tag") var tag: String,
		@SerializedName("description") var description: String
)

data class PromotionImage(
		@SerializedName("ad1") var ad1: String,
		@SerializedName("ad2") var ad2: String
)