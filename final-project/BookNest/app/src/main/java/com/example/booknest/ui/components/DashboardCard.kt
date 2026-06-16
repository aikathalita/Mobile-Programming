package com.example.booknest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardCard(
    title: String,
    value: Int,
    type: DashboardType,
    modifier: Modifier = Modifier
) {
    val accentColor = when (type) {
        DashboardType.TOTAL -> Color(0xFF2563EB)
        DashboardType.AVAILABLE -> Color(0xFF16A34A)
        DashboardType.BORROWED -> Color(0xFFEA580C)
    }

    val softColor = when (type) {
        DashboardType.TOTAL -> Color(0xFFEAF1FF)
        DashboardType.AVAILABLE -> Color(0xFFE8F9EE)
        DashboardType.BORROWED -> Color(0xFFFFF0E3)
    }

    val icon = when (type) {
        DashboardType.TOTAL -> Icons.Default.AutoStories
        DashboardType.AVAILABLE -> Icons.Default.CheckCircle
        DashboardType.BORROWED -> Icons.Default.Schedule
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            DashboardIconBox(
                icon = icon,
                backgroundColor = softColor,
                iconColor = accentColor
            )

            Column {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
fun DashboardIconBox(
    icon: ImageVector,
    backgroundColor: Color,
    iconColor: Color
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(25.dp)
        )
    }
}

enum class DashboardType {
    TOTAL,
    AVAILABLE,
    BORROWED
}