package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryResponse(
    @SerializedName("attribute") val attribute: Any,
    @SerializedName("back_image") val backImage: String,
    @SerializedName("banner_image") val bannerImage: Any,
    @SerializedName("brand") val brand: String,
    @SerializedName("cat_slug") val catSlug: String,
    @SerializedName("category_gst") val categoryGst: String,
    @SerializedName("categoryname") val categoryname: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("displayflag") val displayflag: String,
    @SerializedName("featured") val featured: Any,
    @SerializedName("id") val id: Int,
    @SerializedName("long_description") val longDescription: String,
    @SerializedName("parent") val parent: Any,
    @SerializedName("pickup_charge") val pickupCharge: String,
    @SerializedName("price") val price: String,
    @SerializedName("sale_comission") val saleComission: String,
    @SerializedName("service_charge") val serviceCharge: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("side_image") val side_image: String,
    @SerializedName("sortid") val sortid: Any,
    @SerializedName("store_charges") val storeCharges: String,
    @SerializedName("terms_code") val termsCode: String,
    @SerializedName("thumb_image") val thumbImage: String,
    @SerializedName("top_image") val topImage: Any,
    @SerializedName("topcat") val topcat: Any,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("urllink") val urllink: Any
) : Serializable