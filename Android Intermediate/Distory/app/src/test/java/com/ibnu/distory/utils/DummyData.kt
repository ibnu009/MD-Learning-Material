package com.ibnu.distory.utils

import com.ibnu.distory.data.model.Story

object DummyData {

    fun emailDummy() = "ibnubatutah100@gmail.com"
    fun passwordDummy() = "12345678"
    fun tokenDummy() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWI2d3NBNDFDTmtUSTVITE8iLCJpYXQiOjE2ODAxMDY5ODd9.7PvzP8TiwBX61JZCRsypL8LF38NxaqdtRhNu_lVUey0"

    fun listStoryDummy(): List<Story> {
        val items = arrayListOf<Story>()
        for (i in 0 until 5) {
            val story = Story(
                id = "story-W9rD9GRtKp7Ppb0-",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1680098883251_6U8tKtjQ.jpg",
                createdAt = "2023-03-29T14:08:03.256Z",
                name = "User Name",
                description = "Story Description",
                latitude = (-10.212).toFloat(),
                longitude = (-16.002).toFloat(),
            )
            items.add(story)
        }
        return items
    }

    fun emptyListStoryDummy(): List<Story> = emptyList()

}