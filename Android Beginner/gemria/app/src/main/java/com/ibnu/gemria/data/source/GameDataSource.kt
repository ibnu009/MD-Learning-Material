package com.ibnu.gemria.data.source

import com.ibnu.gemria.R
import com.ibnu.gemria.data.model.Game
import com.ibnu.gemria.utils.CategoryConstant

object GameDataSource {
    private val dataSource = arrayOf(
        arrayOf(
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
        ),
        arrayOf(
            "Counter-Strike: Global Offensive",
            "Counter-Strike took the gaming industry by surprise when the unlikely MOD became the most played online PC action game in the world almost immediately after its release in August 1999,\" said Doug Lombardi at Valve. \"For the past 12 years, it has continued to be one of the most-played games in the world, headline competitive gaming tournaments and selling over 25 million units worldwide across the franchise. CS: GO promises to expand on CS' award-winning gameplay and deliver it to gamers on the PC as well as the next gen consoles and the Mac.",
            "Valve",
            "5 Nov, 2020",
            R.drawable.cs_poster,
            R.drawable.cs_background,
            "${CategoryConstant.ACTION}, ${CategoryConstant.FPS}",
            4.85,
            6192331,
            0,
            "https://store.steampowered.com/app/730/CounterStrike_Global_Offensive/"
        ),
        arrayOf(
            "Apex Legends",
            "Apex Legends is the award-winning, free-to-play Hero Shooter from Respawn Entertainment. Master an ever-growing roster of legendary characters with powerful abilities, and experience strategic squad play and innovative gameplay in the next evolution of Hero Shooter and Battle Royale.",
            "Respawn Entertainment",
            "22 Aug, 2012",
            R.drawable.apex_poster,
            R.drawable.apex_background,
            "${CategoryConstant.ACTION}, ${CategoryConstant.FPS}",
            4.88,
            522731,
            0,
            "https://store.steampowered.com/app/1172470/Apex_Legends/"
        ),
        arrayOf(
            "Dead by Daylight",
            "Dead by Daylight is a multiplayer (4vs1) horror game where one player takes on the role of the savage Killer, and the other four players play as Survivors, trying to escape the Killer and avoid being caught and killed.",
            "Behaviour Interactive Inc",
            "14 Jun, 2016",
            R.drawable.dbd_poster,
            R.drawable.dbd_background,
            "${CategoryConstant.ACTION}, ${CategoryConstant.HORROR}",
            4.41,
            481021,
            135000,
            "https://store.steampowered.com/app/381210/Dead_by_Daylight/"
        ),
        arrayOf(
            "The Last of Us",
            "In a ravaged civilization, where infected and hardened survivors run rampant, Joel, a weary protagonist, is hired to smuggle 14-year-old Ellie out of a military quarantine zone. However, what starts as a small job soon transforms into a brutal cross-country journey.",
            "Naughty Dog LLC",
            "28 Mar, 2023",
            R.drawable.lou_poster,
            R.drawable.lou_background,
            "${CategoryConstant.ACTION}, ${CategoryConstant.ADVENTURE}",
            4.97,
            1221529,
            879000,
            "https://store.steampowered.com/app/1888930/The_Last_of_Us_Part_I/"
        ),
        arrayOf(
            "Dota 2",
            "Every day, millions of players worldwide enter battle as one of over a hundred Dota heroes. And no matter if it's their 10th hour of play or 1,000th, there's always something new to discover. With regular updates that ensure a constant evolution of gameplay, features, and heroes, Dota 2 has taken on a life of its own.",
            "Valve",
            "10 Jul, 2013",
            R.drawable.dota_poster,
            R.drawable.dota_background,
            CategoryConstant.STRATEGY,
            4.88,
            1929769,
            0,
            "https://store.steampowered.com/app/570/Dota_2/"
        ),
        arrayOf(
            "Football Manager 2023",
            "Build your dream squad, outsmart your rivals and experience the thrill of big European nights in the UEFA Champions League. Your journey towards footballing glory awaits.",
            "Sports Interactive",
            "8 Nov, 2022",
            R.drawable.fm_poster,
            R.drawable.fm_background,
            "${CategoryConstant.STRATEGY}, ${CategoryConstant.SIMULATION}, ${CategoryConstant.SPORT}",
            4.41,
            62975,
            599999,
            "https://store.steampowered.com/app/1904540/Football_Manager_2023/"
        ),
        arrayOf(
            "Brotato",
            "Brotato is a top-down arena shooter roguelite where you play a potato wielding up to 6 weapons at a time to fight off hordes of aliens. Choose from a variety of traits and items to create unique builds and survive until help arrives.",
            "Blobfish",
            "27 Sep, 2022",
            R.drawable.brotato_poster,
            R.drawable.brotato_background,
            "${CategoryConstant.CASUAL}, ${CategoryConstant.SIMULATION}",
            4.98,
            24569,
            31999,
            "https://store.steampowered.com/app/1942280/Brotato/"
        ),
        arrayOf(
            "Dead Space",
            "The sci-fi survival-horror classic returns, completely rebuilt to offer an even more immersive experience — including visual, audio, and gameplay improvements — while staying faithful to the original game’s thrilling vision.",
            "Motive",
            "27 Jan, 2023",
            R.drawable.ds_poster,
            R.drawable.ds_background,
            "${CategoryConstant.HORROR}, ${CategoryConstant.ACTION}",
            4.67,
            18367,
            659000,
            "https://store.steampowered.com/app/1693980/Dead_Space/"
        ),
        arrayOf(
            "Resident Evil 4",
            "Survival is just the beginning. Six years have passed since the biological disaster in Raccoon City. Leon S. Kennedy, one of the survivors, tracks the president's kidnapped daughter to a secluded European village, where there is something terribly wrong with the locals.",
            "CAPCOM Co., Ltd.",
            "24 Mar, 2023",
            R.drawable.re_poster,
            R.drawable.re_background,
            "${CategoryConstant.HORROR}, ${CategoryConstant.ACTION}",
            4.91,
            23075,
            749000,
            "https://store.steampowered.com/app/2050650/Resident_Evil_4/"
        ),
        arrayOf(
            "Hogwarts Legacy",
            "Hogwarts Legacy is an immersive, open-world action RPG. Now you can take control of the action and be at the center of your own adventure in the wizarding world.",
            "Avalanche Software",
            "11 Feb, 2023",
            R.drawable.hl_poster,
            R.drawable.hl_background,
            "${CategoryConstant.ADVENTURE}, ${CategoryConstant.ACTION}, ${CategoryConstant.RPG}",
            4.81,
            99060,
            749000,
            "https://store.steampowered.com/app/990080/Hogwarts_Legacy/"
        ),
    )

    val listGame: List<Game>
        get(){
            val list = ArrayList<Game>()
            for (data in dataSource) {
                val game = Game()
                game.name = data[0] as String
                game.description = data[1] as String
                game.developerName = data[2] as String
                game.releaseDate = data[3] as String
                game.posterImage = data[4] as Int
                game.backgroundImage = data[5] as Int
                game.categories = data[6] as String
                game.rating = data[7] as Double
                game.totalReviews = data[8] as Int
                game.price = data[9] as Int
                game.link = data[10] as String
                list.add(game)
            }
            return list
        }
}