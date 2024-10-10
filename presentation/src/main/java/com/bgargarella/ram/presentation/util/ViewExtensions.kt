package com.bgargarella.ram.presentation.util

import com.bgargarella.ram.presentation.R
import com.bgargarella.ram.domain.BuildConfig
import com.bgargarella.ram.domain.character.model.Character
import com.bgargarella.ram.domain.character.model.CharacterLocationModel
import com.bgargarella.ram.domain.episode.model.Episode
import com.bgargarella.ram.domain.location.model.Location
import com.bgargarella.ram.presentation.base.model.BaseItem
import com.bgargarella.ram.presentation.base.model.ButtonItem
import com.bgargarella.ram.presentation.base.model.TextItem

fun Character.getItemsList(): List<BaseItem> =
    emptySequence<BaseItem>()
        .plus(status?.let { info -> TextItem(R.string.status, info) })
        .plus(species?.let { info -> TextItem(R.string.species, info) })
        .plus(type?.let { info -> TextItem(R.string.type, info) })
        .plus(gender?.let { info -> TextItem(R.string.gender, info) })
        .plus(origin?.let { location -> ButtonItem(R.string.origin, location.name, location.id) })
        .plus(location?.let { location ->
            ButtonItem(
                R.string.location,
                location.name,
                location.id
            )
        })
        .filterNotNull()
        .toList()

fun Episode.getItemsList(): List<BaseItem> =
    emptySequence<BaseItem>()
        .plus(TextItem(R.string.name, name))
        .plus(TextItem(R.string.code, code))
        .plus(TextItem(R.string.release_date, releaseDate))
        .toList()

fun Location.getItemsList(): List<BaseItem> =
    emptySequence<BaseItem>()
        .plus(TextItem(R.string.name, name))
        .plus(type?.let { info -> TextItem(R.string.type, info) })
        .plus(dimension?.let { info -> TextItem(R.string.dimension, info) })
        .filterNotNull()
        .toList()

fun List<BaseItem>.filterPreviewScreen(): List<TextItem> =
    filterIsInstance<TextItem>()

fun getCharacterItemsTest(): List<Character> =
    listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            avatar = "${BuildConfig.BASE_URL}character/avatar/1.jpeg",
            status = "Alive",
            species = "Human",
            type = null,
            gender = "Male",
            origin = CharacterLocationModel(
                id = 1,
                name = "Earth (C-137)",
            ),
            location = CharacterLocationModel(
                id = 1,
                name = "Earth (C-137)",
            ),
            episodes = listOf(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40,
                41,
                42,
                43,
                44,
                45,
                46,
                47,
                48,
                49,
                50,
                51
            )
        ),
        Character(
            id = 2,
            name = "Morty Smith",
            avatar = "${BuildConfig.BASE_URL}character/avatar/2.jpeg",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = null,
            location = CharacterLocationModel(
                id = 3,
                name = "Citadel of Ricks",
            ),
            episodes = listOf(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                36,
                37,
                38,
                39,
                40,
                41,
                42,
                43,
                44,
                45,
                46,
                47,
                48,
                49,
                50,
                51
            )
        )
    )

fun getEpisodeItemsTest(): List<Episode> =
    listOf(
        Episode(
            id = 1,
            name = "Pilot",
            releaseDate = "December 2, 2013",
            code = "S01E01",
            characters = listOf(
                1,
                2,
                35,
                38,
                62,
                92,
                127,
                144,
                158,
                175,
                179,
                181,
                239,
                249,
                271,
                338,
                394,
                395,
                435
            )
        ),
        Episode(
            id = 2,
            name = "Lawnmower Dog",
            releaseDate = "December 9, 2013",
            code = "S01E02",
            characters = listOf(
                1,
                2,
                38,
                46,
                63,
                80,
                175,
                221,
                239,
                246,
                304,
                305,
                306,
                329,
                338,
                396,
                397,
                398,
                405
            )
        )
    )

fun getLocationItemsTest(): List<Location> =
    listOf(
        Location(
            id = 1,
            name = "Earth (C-137)",
            type = "Planet",
            dimension = "Dimension C-137",
            residents = listOf(
                38,
                45,
                71,
                82,
                83,
                92,
                112,
                114,
                116,
                117,
                120,
                127,
                155,
                169,
                175,
                179,
                186,
                201,
                216,
                239,
                271,
                302,
                303,
                338,
                343,
                356,
                394
            )
        ),
        Location(
            id = 2,
            name = "Abadango",
            type = "Cluster",
            dimension = null,
            residents = listOf(6)
        )
    )