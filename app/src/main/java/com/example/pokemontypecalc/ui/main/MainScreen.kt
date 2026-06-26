package com.example.pokemontypecalc.ui.main

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.compose.ui.platform.LocalView
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import com.example.pokemontypecalc.R
import com.example.pokemontypecalc.data.PokemonType
import com.example.pokemontypecalc.data.TypeChart
import com.example.pokemontypecalc.theme.PokemonTypeCalcTheme

object AppColors {
    val Background = Color(0xFFEEEEEE)
    val Header = Color(0xFF555555)
    val DefenseTab = Color(0xFF5A7CFF)
    val AttackTab = Color(0xFFF95A5A)
    val TabUnselected = Color(0xFFAAAAAA)
    val Placeholder = Color(0xFF888888)
    val Divider = Color(0xFFCCCCCC)
    val ResultSuperEffective = Color(0xFFCC3333)
    val ResultNotEffective = Color(0xFF3366AA)
    val ResultNoEffect = Color(0xFF666666)
    val ButtonBorder = Color(0xFF333333)
}

/**
 * 寶可夢屬性相剋 APP 主畫面
 * Pokémon Type Effectiveness Calculator Main Screen
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    viewModel: TypeCalcViewModel = viewModel { TypeCalcViewModel() },
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    TypeCalcScreen(
        state = state,
        onModeSwitch = viewModel::switchMode,
        onTypeClick = viewModel::onTypeClick,
        onReset = viewModel::reset,
    )
}

/**
 * 主畫面內容（純 UI，可預覽）
 * Main screen content (pure UI, previewable)
 */
@Composable
fun TypeCalcScreen(
    state: TypeCalcUiState,
    onModeSwitch: (CalcMode) -> Unit,
    onTypeClick: (PokemonType) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // ===== 頂部標題列 / Header Bar =====
        HeaderBar(
            mode = state.mode,
            onReset = onReset
        )

        // ===== 屬性按鈕 + 結果區（可捲動）/ Type buttons + results (scrollable) =====
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 6.dp,
                end = 6.dp,
                top = 6.dp,
                bottom = 24.dp
            )
        ) {
            // 模式切換標籤移到上方 / Mode switch tab moved to top
            item {
                ModeSwitchBar(
                    mode = state.mode,
                    onModeSwitch = onModeSwitch
                )
            }

            // 屬性按鈕網格 / Type button grid
            item {
                TypeButtonGrid(
                    types = PokemonType.displayOrder,
                    isTypeSelected = state::isTypeSelected,
                    onTypeClick = onTypeClick
                )
            }

            // 結果區 / Results area
            if (!state.hasSelection) {
                item {
                    PlaceholderText(mode = state.mode)
                }
            } else {
                val sortedResults = TypeChart.multiplierOrder
                    .filter { state.results.containsKey(it) }
                    .map { it to (state.results[it] ?: emptyList()) }

                if (sortedResults.isEmpty()) {
                    item {
                        Text(
                            text = "所有屬性倍率皆為 1×",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    items(sortedResults) { (multiplier, types) ->
                        ResultRow(
                            multiplier = multiplier,
                            types = types
                        )
                    }
                }
            }
        }
    }
}

// ===== 頂部標題列 / Header Bar =====
@Composable
private fun HeaderBar(
    mode: CalcMode,
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Header)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左側空白保持標題居中 / Left spacer for centering
        Spacer(modifier = Modifier.width(36.dp))

        // 標題 / Title
        Text(
            text = if (mode == CalcMode.DEFENSE) "防禦方" else "攻擊方",
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // 重置按鈕 / Reset button
        Icon(
            painter = painterResource(id = R.drawable.ic_reset),
            contentDescription = "重置",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable { onReset() }
        )
    }
}

// ===== 屬性按鈕網格 / Type Button Grid =====
@Composable
private fun TypeButtonGrid(
    types: List<PokemonType>,
    isTypeSelected: (PokemonType) -> Boolean,
    onTypeClick: (PokemonType) -> Unit
) {
    // 手動排列成 3 欄 / Manually layout in 3 columns
    val rows = types.chunked(3)
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        rows.forEach { rowTypes ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                rowTypes.forEach { type ->
                    TypeButton(
                        type = type,
                        isSelected = isTypeSelected(type),
                        onClick = { onTypeClick(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

// ===== 單一屬性按鈕（平面化設計）/ Single Type Button (flat design) =====
@Composable
private fun TypeButton(
    type: PokemonType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(6.dp)
    val textColor = Color.White // Text is white for all buttons in the new reference image

    // 動態計算縮放與陰影 / Animate scale and elevation
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.04f else 1.0f, // 稍微縮小縮放比例以避免碰撞 / Reduced scale to avoid collision
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scale"
    )
    val elevation by animateFloatAsState(
        targetValue = if (isSelected) 12f else 0f,
        label = "elevation"
    )

    Row(
        modifier = modifier
            .height(52.dp)
            .zIndex(if (isSelected) 1f else 0f) // 確保選取的按鈕會浮在其他按鈕上方
            .scale(scale)
            .shadow(elevation = elevation.dp, shape = shape)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, AppColors.ButtonBorder, shape) // 保留原本的黑框 / Keep the black border
                } else {
                    Modifier
                }
            )
            .clip(shape)
            .background(type.color)
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start // 置左對齊
    ) {
        // 屬性圖示 / Type icon
        Icon(
            painter = painterResource(id = type.iconResId),
            contentDescription = type.displayName,
            tint = textColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 屬性名稱 / Type name
        Text(
            text = type.displayName,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ===== 模式切換標籤 / Mode Switch Bar =====
@Composable
private fun ModeSwitchBar(
    mode: CalcMode,
    onModeSwitch: (CalcMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // 防禦方按鈕 / Defense tab
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(
                    if (mode == CalcMode.DEFENSE) AppColors.DefenseTab
                    else AppColors.TabUnselected
                )
                .clickable { onModeSwitch(CalcMode.DEFENSE) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "防禦方",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 攻擊方按鈕 / Attack tab
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(
                    if (mode == CalcMode.ATTACK) AppColors.AttackTab
                    else AppColors.TabUnselected
                )
                .clickable { onModeSwitch(CalcMode.ATTACK) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "攻擊方",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ===== 佔位文字 / Placeholder Text =====
@Composable
private fun PlaceholderText(mode: CalcMode) {
    Text(
        text = if (mode == CalcMode.DEFENSE) {
            "請選擇防禦方寶可夢的屬性"
        } else {
            "請選擇攻擊方寶可夢的屬性"
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        textAlign = TextAlign.Center,
        color = AppColors.Placeholder,
        fontSize = 15.sp
    )
}

// ===== 結果行 / Result Row =====
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResultRow(
    multiplier: Float,
    types: List<PokemonType>
) {
    val label = TypeChart.multiplierLabel(multiplier)
    val labelColor = when {
        multiplier >= 2f  -> AppColors.ResultSuperEffective
        multiplier == 0f  -> AppColors.ResultNoEffect
        else              -> AppColors.ResultNotEffective
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 分隔線 / Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AppColors.Divider)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            // 倍率文字 / Multiplier label
            Text(
                text = label,
                color = labelColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .width(56.dp)
                    .padding(top = 2.dp)
            )

            // 屬性標籤流式排列（使用內建 FlowRow）
            // Type tags in flow layout (using built-in FlowRow)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                types.forEach { type ->
                    TypeTag(type = type)
                }
            }
        }
    }
}

// ===== 屬性標籤（結果區用）/ Type Tag (for results area) =====
@Composable
private fun TypeTag(type: PokemonType) {
    val contentColor = if (type.color.luminance() > 0.5f) Color(0xFF333333) else Color.White

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(type.color)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = type.iconResId),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        
        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = type.displayName,
            color = contentColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun TypeCalcScreenPreview() {
    PokemonTypeCalcTheme {
        TypeCalcScreen(
            state = TypeCalcUiState(),
            onModeSwitch = {},
            onTypeClick = {},
            onReset = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun TypeCalcScreenSelectedPreview() {
    val state = TypeCalcUiState(
        mode = CalcMode.DEFENSE,
        selectedDefenseTypes = listOf(PokemonType.FIGHTING, PokemonType.GROUND),
        results = TypeChart.getDefenseResults(listOf(PokemonType.FIGHTING, PokemonType.GROUND))
    )
    PokemonTypeCalcTheme {
        TypeCalcScreen(
            state = state,
            onModeSwitch = {},
            onTypeClick = {},
            onReset = {}
        )
    }
}
