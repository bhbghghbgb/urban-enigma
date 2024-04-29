package com.example.learncode.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipShow(title: String, selected: String, onSelected: (String) -> Unit) {
    val isSelected = selected == title
    FilterChip(
        onClick = { onSelected(title) },
        label = {
            Text(title, fontSize = 16.sp)
        },
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF9C7055),
            labelColor = Color(0xFF9C7055),
            selectedLabelColor = Color.White,
            containerColor = Color(0xFFEDE0CF)
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Color.White,
            enabled = true,
            selected = true
        )
    )
}