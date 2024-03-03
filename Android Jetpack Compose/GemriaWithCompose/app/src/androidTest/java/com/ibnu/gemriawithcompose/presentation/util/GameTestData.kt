package com.ibnu.gemriawithcompose.presentation.util

import com.ibnu.gemriawithcompose.R
import com.ibnu.gemriawithcompose.data.model.Game
import com.ibnu.gemriawithcompose.utils.CategoryConstant

object GameTestData {
    val fakeGame = Game(
        1,
        "The Elder Scrolls V: Skyrim Special Edition",
        "Winner of more than 200 Game of the Year Awards, Skyrim Special Edition brings the epic fantasy to life in stunning detail. The Special Edition includes the critically acclaimed game and add-ons with all-new features like remastered art and effects, volumetric god rays, dynamic depth of field, screen-space reflections, and more. Skyrim Special Edition also brings the full power of mods to the PC and consoles. New quests, environments, characters, dialogue, armor, weapons and more – with Mods, there are no limits to what you can experience.",
        "Bethesda Game Studios",
        "28 Oct, 2016",
        R.drawable.skyrim_poster,
        R.drawable.skyrim_background,
        "${CategoryConstant.ACTION}, ${CategoryConstant.ADVENTURE}, ${CategoryConstant.RPG}",
        4.9,
        5192331,
        74000,
        "https://store.steampowered.com/app/489830/The_Elder_Scrolls_V_Skyrim_Special_Edition/"
    )
}