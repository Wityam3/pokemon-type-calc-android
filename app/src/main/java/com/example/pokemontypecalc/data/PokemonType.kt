package com.example.pokemontypecalc.data

import androidx.compose.ui.graphics.Color

/**
 * 寶可夢屬性定義（18 種，第六世代以後含妖精）
 * Pokémon type definitions (18 types, Gen 6+ including Fairy)
 */
enum class PokemonType(
    val displayName: String,  // 繁體中文名稱 / Traditional Chinese name
    val color: Color,         // 代表色 / Representative color
    val iconResName: String,  // 圖示資源名稱 / Icon resource name
) {
    // 排列順序參照截圖 / Order follows screenshot layout
    NORMAL   ("一般",   Color(0xFF9DA0AA), "ic_type_normal"),
    FIGHTING ("格鬥",   Color(0xFFCE416B), "ic_type_fighting"),
    FLYING   ("飛",     Color(0xFF8FA8DD), "ic_type_flying"),
    POISON   ("毒",     Color(0xFFAA6BC8), "ic_type_poison"),
    GROUND   ("地面",   Color(0xFFD97845), "ic_type_ground"),
    ROCK     ("岩石",   Color(0xFFC5B78C), "ic_type_rock"),
    BUG      ("蟲",     Color(0xFF91C12F), "ic_type_bug"),
    GHOST    ("幽靈",   Color(0xFF5269AD), "ic_type_ghost"),
    STEEL    ("鋼",     Color(0xFF5A8EA2), "ic_type_steel"),
    FIRE     ("火",     Color(0xFFFF9D55), "ic_type_fire"),
    WATER    ("水",     Color(0xFF5090D6), "ic_type_water"),
    GRASS    ("草",     Color(0xFF63BC5A), "ic_type_grass"),
    ELECTRIC ("電",     Color(0xFFF4D23C), "ic_type_electric"),
    PSYCHIC  ("超能力", Color(0xFFFA7179), "ic_type_psychic"),
    ICE      ("冰",     Color(0xFF73CEC0), "ic_type_ice"),
    DRAGON   ("龍",     Color(0xFF0B6DC3), "ic_type_dragon"),
    DARK     ("惡",     Color(0xFF5A5465), "ic_type_dark"),
    FAIRY    ("妖精",   Color(0xFFEC8FE6), "ic_type_fairy");

    companion object {
        /**
         * 按照截圖的顯示順序排列（3 欄網格）
         * Display order matching the screenshot (3-column grid)
         */
        val displayOrder: List<PokemonType> = listOf(
            NORMAL, FIGHTING, FLYING,
            POISON, GROUND, ROCK,
            BUG, GHOST, STEEL,
            FIRE, WATER, GRASS,
            ELECTRIC, PSYCHIC, ICE,
            DRAGON, DARK, FAIRY
        )
    }
}
