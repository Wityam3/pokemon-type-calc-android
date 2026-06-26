package com.example.pokemontypecalc.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.pokemontypecalc.R

/**
 * 寶可夢屬性定義（18 種，第六世代以後含妖精）
 */
enum class PokemonType(
    val displayName: String,
    val color: Color,
    @DrawableRes val iconResId: Int,
) {
    // Colors and names exact match with the 3-column screenshot provided
    NORMAL   ("一般",   Color(0xFF8B969C), R.drawable.ic_type_normal),
    FIGHTING ("格鬥",   Color(0xFFE87910), R.drawable.ic_type_fighting),
    FLYING   ("飛",     Color(0xFF86B2E3), R.drawable.ic_type_flying), // Use "飛" instead of "飛行"
    POISON   ("毒",     Color(0xFF903CCF), R.drawable.ic_type_poison),
    GROUND   ("地面",   Color(0xFF8F572A), R.drawable.ic_type_ground),
    ROCK     ("岩石",   Color(0xFFB5A170), R.drawable.ic_type_rock),
    BUG      ("蟲",     Color(0xFFA0B025), R.drawable.ic_type_bug),
    GHOST    ("幽靈",   Color(0xFF6E4378), R.drawable.ic_type_ghost),
    STEEL    ("鋼",     Color(0xFF5B9DAF), R.drawable.ic_type_steel),
    FIRE     ("火",     Color(0xFFDF2C2A), R.drawable.ic_type_fire),
    WATER    ("水",     Color(0xFF2B78E4), R.drawable.ic_type_water),
    GRASS    ("草",     Color(0xFF41A229), R.drawable.ic_type_grass),
    ELECTRIC ("電",     Color(0xFFF3AB00), R.drawable.ic_type_electric),
    PSYCHIC  ("超能力", Color(0xFFE8477D), R.drawable.ic_type_psychic),
    ICE      ("冰",     Color(0xFF43C3F1), R.drawable.ic_type_ice),
    DRAGON   ("龍",     Color(0xFF505DD3), R.drawable.ic_type_dragon),
    DARK     ("惡",     Color(0xFF4C403B), R.drawable.ic_type_dark),
    FAIRY    ("妖精",   Color(0xFFEA70E6), R.drawable.ic_type_fairy);

    companion object {
        /**
         * 顯示順序，與參考截圖 3 欄式排列完全一致
         */
        val displayOrder: List<PokemonType> = listOf(
            NORMAL,   FIGHTING, FLYING,
            POISON,   GROUND,   ROCK,
            BUG,      GHOST,    STEEL,
            FIRE,     WATER,    GRASS,
            ELECTRIC, PSYCHIC,  ICE,
            DRAGON,   DARK,     FAIRY
        )
    }
}
