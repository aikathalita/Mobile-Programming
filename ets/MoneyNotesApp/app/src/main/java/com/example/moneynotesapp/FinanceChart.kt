package com.example.moneynotesapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

private val IncomeColor = Color(0xFF8B5CF6)
private val ExpenseColor = Color(0xFFEC4899)
private val DarkText = Color(0xFF2D1B3D)
private val MutedText = Color(0xFF7C6A8A)

@Composable
fun FinanceChart(
    income: Int,
    expense: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Ringkasan Grafik",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Perbandingan pemasukan dan pengeluaran",
                fontSize = 13.sp,
                color = MutedText
            )

            Spacer(modifier = Modifier.height(18.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                val maxAmount = maxOf(income, expense, 1)
                val chartHeight = size.height
                val barWidth = size.width / 5f

                val incomeHeight = if (income == 0) {
                    0f
                } else {
                    max(16f, chartHeight * (income.toFloat() / maxAmount.toFloat()))
                }

                val expenseHeight = if (expense == 0) {
                    0f
                } else {
                    max(16f, chartHeight * (expense.toFloat() / maxAmount.toFloat()))
                }

                for (i in 1..3) {
                    val y = chartHeight * i / 4
                    drawLine(
                        color = Color(0xFFF3E8FF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 2f
                    )
                }

                drawRoundRect(
                    color = IncomeColor,
                    topLeft = Offset(
                        x = size.width * 0.28f - barWidth / 2,
                        y = chartHeight - incomeHeight
                    ),
                    size = Size(barWidth, incomeHeight),
                    cornerRadius = CornerRadius(20f, 20f)
                )

                drawRoundRect(
                    color = ExpenseColor,
                    topLeft = Offset(
                        x = size.width * 0.72f - barWidth / 2,
                        y = chartHeight - expenseHeight
                    ),
                    size = Size(barWidth, expenseHeight),
                    cornerRadius = CornerRadius(20f, 20f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ChartLegend(
                    color = IncomeColor,
                    title = "Pemasukan",
                    amount = formatRupiah(income)
                )

                ChartLegend(
                    color = ExpenseColor,
                    title = "Pengeluaran",
                    amount = formatRupiah(expense)
                )
            }
        }
    }
}

@Composable
fun ChartLegend(
    color: Color,
    title: String,
    amount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(5.dp)
                )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                color = MutedText
            )

            Text(
                text = amount,
                fontSize = 13.sp,
                color = DarkText,
                fontWeight = FontWeight.Bold
            )
        }
    }
}