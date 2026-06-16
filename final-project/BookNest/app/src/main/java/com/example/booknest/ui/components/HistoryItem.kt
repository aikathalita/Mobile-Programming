package com.example.booknest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.booknest.data.local.entity.BorrowHistory

@Composable
fun HistoryItem(
    history: BorrowHistory,
    modifier: Modifier = Modifier
) {
    val isReturned = history.status == "RETURNED"

    val statusText = if (isReturned) "Dikembalikan" else "Sedang Dipinjam"
    val statusColor = if (isReturned) Color(0xFF15803D) else Color(0xFFEA580C)
    val statusBackground = if (isReturned) Color(0xFFE8F7EE) else Color(0xFFFFF1E7)
    val iconColor = if (isReturned) Color(0xFF16A34A) else Color(0xFF2563EB)
    val iconBackground = if (isReturned) Color(0xFFE8F7EE) else Color(0xFFE0ECFF)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isReturned) Icons.Default.CheckCircle else Icons.Default.History,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = history.bookTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF172033),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = statusBackground
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                }

                InfoRow(
                    icon = Icons.Default.Person,
                    label = "Peminjam",
                    value = history.borrowerName
                )

                InfoRow(
                    icon = Icons.Default.CalendarMonth,
                    label = "Tanggal pinjam",
                    value = history.borrowDate
                )

                if (history.returnDate != null) {
                    InfoRow(
                        icon = Icons.Default.CheckCircle,
                        label = "Tanggal kembali",
                        value = history.returnDate
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEAF2FF),
                            Color(0xFFF1F5F9)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF475569)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B)
        )
    }
}