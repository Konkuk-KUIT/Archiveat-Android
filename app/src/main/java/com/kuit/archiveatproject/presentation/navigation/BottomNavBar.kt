package com.kuit.archiveatproject.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.core.util.noRippleClickable
import kotlin.enums.EnumEntries

@Composable
fun BottomNavBar(
    visible: Boolean,
    tabs: EnumEntries<NavTab>,
    currentTab: NavTab?,
    onItemSelected: (NavTab) -> Unit
) {
    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .background(Color.White)
    ) {
        Column {

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .navigationBarsPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tabs.forEach { tab ->
                    val isSelected = tab == currentTab

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable {
                                onItemSelected(tab)
                            },
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = tab.label,
                            tint = if (isSelected) {
                                Color.Black
                            } else {
                                Color(0xFF8B91A1)
                            }
                        )
                        Text(
                            text = tab.label,
                            fontSize = 12.sp,
                            color = if (isSelected) {
                                Color.Black
                            } else {
                                Color(0xFF939DA9)
                            }
                        )
                    }
                }
            }
        }
    }
}