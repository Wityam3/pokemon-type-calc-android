package com.example.pokemontypecalc.data

/**
 * 寶可夢屬性相剋表（第六世代以後，含妖精屬性）
 * Pokémon type effectiveness chart (Gen 6+, including Fairy)
 *
 * typeChart[攻擊屬性][防禦屬性] = 倍率
 * typeChart[attacker][defender] = multiplier
 *
 * 倍率值 / Multiplier values:
 * 2.0  = 效果絕佳 / Super effective
 * 1.0  = 普通效果 / Normal
 * 0.5  = 效果不好 / Not very effective
 * 0.0  = 沒有效果 / No effect
 */
object TypeChart {

    // 內部使用的屬性索引順序（與 PokemonType.entries 對齊）
    // Internal type index order (aligned with PokemonType.entries)
    // NORMAL=0, FIGHTING=1, FLYING=2, POISON=3, GROUND=4, ROCK=5,
    // BUG=6, GHOST=7, STEEL=8, FIRE=9, WATER=10, GRASS=11,
    // ELECTRIC=12, PSYCHIC=13, ICE=14, DRAGON=15, DARK=16, FAIRY=17

    /**
     * 完整 18×18 屬性相剋矩陣
     * Complete 18×18 type effectiveness matrix
     *
     * 行索引 = 攻擊方屬性 / Row = attacking type
     * 列索引 = 防守方屬性 / Column = defending type
     *
     * 順序 / Order:
     * NOR FIG FLY POI GRD ROC BUG GHO STE FIR WAT GRA ELE PSY ICE DRA DAR FAI
     */
    private val chart: Array<FloatArray> = arrayOf(
        //           NOR  FIG  FLY  POI  GRD  ROC  BUG  GHO  STE  FIR  WAT  GRA  ELE  PSY  ICE  DRA  DAR  FAI
        /* NOR */ floatArrayOf(1f,  1f,  1f,  1f,  1f,  .5f, 1f,  0f,  .5f, 1f,  1f,  1f,  1f,  1f,  1f,  1f,  1f,  1f ),
        /* FIG */ floatArrayOf(2f,  1f,  .5f, .5f, 1f,  2f,  .5f, 0f,  2f,  1f,  1f,  1f,  1f,  .5f, 2f,  1f,  2f,  .5f),
        /* FLY */ floatArrayOf(1f,  2f,  1f,  1f,  1f,  .5f, 2f,  1f,  .5f, 1f,  1f,  2f,  .5f, 1f,  1f,  1f,  1f,  1f ),
        /* POI */ floatArrayOf(1f,  1f,  1f,  .5f, .5f, .5f, 1f,  .5f, 0f,  1f,  1f,  2f,  1f,  1f,  1f,  1f,  1f,  2f ),
        /* GRD */ floatArrayOf(1f,  1f,  0f,  2f,  1f,  2f,  .5f, 1f,  2f,  2f,  1f,  .5f, 2f,  1f,  1f,  1f,  1f,  1f ),
        /* ROC */ floatArrayOf(1f,  .5f, 2f,  1f,  .5f, 1f,  2f,  1f,  .5f, 2f,  1f,  1f,  1f,  1f,  2f,  1f,  1f,  1f ),
        /* BUG */ floatArrayOf(1f,  .5f, .5f, .5f, 1f,  1f,  1f,  .5f, .5f, .5f, 1f,  2f,  1f,  2f,  1f,  1f,  2f,  .5f),
        /* GHO */ floatArrayOf(0f,  1f,  1f,  1f,  1f,  1f,  1f,  2f,  1f,  1f,  1f,  1f,  1f,  2f,  1f,  1f,  .5f, 1f ),
        /* STE */ floatArrayOf(1f,  1f,  1f,  1f,  1f,  2f,  1f,  1f,  .5f, .5f, .5f, 1f,  .5f, 1f,  2f,  1f,  1f,  2f ),
        /* FIR */ floatArrayOf(1f,  1f,  1f,  1f,  1f,  .5f, 2f,  1f,  2f,  .5f, .5f, 2f,  1f,  1f,  2f,  .5f, 1f,  1f ),
        /* WAT */ floatArrayOf(1f,  1f,  1f,  1f,  2f,  2f,  1f,  1f,  1f,  2f,  .5f, .5f, 1f,  1f,  1f,  .5f, 1f,  1f ),
        /* GRA */ floatArrayOf(1f,  1f,  .5f, .5f, 2f,  2f,  .5f, 1f,  .5f, .5f, 2f,  .5f, 1f,  1f,  1f,  .5f, 1f,  1f ),
        /* ELE */ floatArrayOf(1f,  1f,  2f,  1f,  0f,  1f,  1f,  1f,  1f,  1f,  2f,  .5f, .5f, 1f,  1f,  .5f, 1f,  1f ),
        /* PSY */ floatArrayOf(1f,  2f,  1f,  2f,  1f,  1f,  1f,  1f,  .5f, 1f,  1f,  1f,  1f,  .5f, 1f,  1f,  0f,  1f ),
        /* ICE */ floatArrayOf(1f,  1f,  2f,  1f,  2f,  1f,  1f,  1f,  .5f, .5f, .5f, 2f,  1f,  1f,  .5f, 2f,  1f,  1f ),
        /* DRA */ floatArrayOf(1f,  1f,  1f,  1f,  1f,  1f,  1f,  1f,  .5f, 1f,  1f,  1f,  1f,  1f,  1f,  2f,  1f,  0f ),
        /* DAR */ floatArrayOf(1f,  .5f, 1f,  1f,  1f,  1f,  1f,  2f,  .5f, 1f,  1f,  1f,  1f,  2f,  1f,  1f,  .5f, .5f),
        /* FAI */ floatArrayOf(1f,  2f,  1f,  .5f, 1f,  1f,  1f,  1f,  .5f, .5f, 1f,  1f,  1f,  1f,  1f,  2f,  2f,  1f ),
    )

    /**
     * 取得攻擊屬性對防禦屬性的倍率
     * Get the effectiveness multiplier of an attacking type vs a defending type
     */
    fun getEffectiveness(attacker: PokemonType, defender: PokemonType): Float {
        return chart[attacker.ordinal][defender.ordinal]
    }

    /**
     * 計算攻擊屬性對雙防禦屬性組合的總倍率（相乘）
     * Calculate total multiplier of an attacking type vs a dual-type defense (multiply)
     */
    fun getEffectiveness(attacker: PokemonType, defenders: List<PokemonType>): Float {
        if (defenders.isEmpty()) return 1f
        var result = 1f
        for (defender in defenders) {
            result *= chart[attacker.ordinal][defender.ordinal]
        }
        return result
    }

    /**
     * 防禦方模式：計算所有攻擊屬性打到防守屬性組合的倍率，按倍率分組
     * Defense mode: Calculate all attacking types' effectiveness vs defense combo, grouped by multiplier
     */
    fun getDefenseResults(defenders: List<PokemonType>): Map<Float, List<PokemonType>> {
        val results = mutableMapOf<Float, MutableList<PokemonType>>()
        for (attacker in PokemonType.entries) {
            val multiplier = getEffectiveness(attacker, defenders)
            // 跳過 1x / Skip 1x
            if (multiplier == 1f) continue
            results.getOrPut(multiplier) { mutableListOf() }.add(attacker)
        }
        return results
    }

    /**
     * 攻擊方模式：計算攻擊屬性打到所有單一防守屬性的倍率，按倍率分組
     * Attack mode: Calculate an attacking type's effectiveness vs all single defending types, grouped by multiplier
     */
    fun getAttackResults(attacker: PokemonType): Map<Float, List<PokemonType>> {
        val results = mutableMapOf<Float, MutableList<PokemonType>>()
        for (defender in PokemonType.entries) {
            val multiplier = getEffectiveness(attacker, defender)
            // 跳過 1x / Skip 1x
            if (multiplier == 1f) continue
            results.getOrPut(multiplier) { mutableListOf() }.add(defender)
        }
        return results
    }

    /**
     * 倍率顯示順序（由高到低）
     * Multiplier display order (high to low)
     */
    val multiplierOrder = listOf(4f, 2f, 0.5f, 0.25f, 0f)

    /**
     * 倍率轉為顯示文字
     * Convert multiplier to display text
     */
    fun multiplierLabel(multiplier: Float): String = when {
        multiplier == 4f    -> "4x"
        multiplier == 2f    -> "2x"
        multiplier == 0.5f  -> "1/2x"
        multiplier == 0.25f -> "1/4x"
        multiplier == 0f    -> "0x"
        else -> "${multiplier}x"
    }
}
