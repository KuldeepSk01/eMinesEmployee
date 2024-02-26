package com.emines_employee.adapter.listener

import com.emines_employee.model.response.CategoryResponse

interface OnSellCategoryClickListener {
    fun onCategoryClick(category: CategoryResponse)
}