package com.ibnu.gemria.data.source

import com.ibnu.gemria.data.model.Category
import com.ibnu.gemria.utils.CategoryConstant

object CategoryDataSource {
    val listCategory: List<Category>
        get() {
            val categories = ArrayList<Category>()
            categories.add(Category(name = CategoryConstant.ALL))
            categories.add(Category(name = CategoryConstant.ACTION))
            categories.add(Category(name = CategoryConstant.ADVENTURE))
            categories.add(Category(name = CategoryConstant.HORROR))
            categories.add(Category(name = CategoryConstant.RPG))
            categories.add(Category(name = CategoryConstant.FPS))
            categories.add(Category(name = CategoryConstant.SIMULATION))
            categories.add(Category(name = CategoryConstant.SPORT))
            categories.add(Category(name = CategoryConstant.STRATEGY))
            categories.add(Category(name = CategoryConstant.CASUAL))
            return categories
        }
}