package com.example.jourlyapp.ui.components.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jourlyapp.R

sealed class NavigationItem(
    val route: String,
    @StringRes val titleResourceId: Int,
    @DrawableRes val iconResourceId: Int
) {
    object Challenges : NavigationItem("achievements", R.string.achieved, R.drawable.ic_baseline_self_improvement_24)
    object Journal : NavigationItem("journal", R.string.journal, R.drawable.ic_journal_24)
    object Report : NavigationItem("report", R.string.report, R.drawable.ic_baseline_poll_24)
    object Profile : NavigationItem("profile", R.string.profile, R.drawable.ic_baseline_person_24)
}
