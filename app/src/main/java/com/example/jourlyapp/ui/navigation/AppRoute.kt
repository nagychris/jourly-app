package com.example.jourlyapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jourlyapp.R

sealed class AppRoute(
    val route: String,
    @StringRes val titleResourceId: Int,
    @DrawableRes val iconResourceId: Int
) {
    object Challenges : AppRoute(
        "achievements",
        R.string.challenges,
        R.drawable.ic_baseline_self_improvement_24
    )

    object Journal : AppRoute(
        "journal",
        R.string.journal,
        R.drawable.ic_journal_24
    )

    object Report : AppRoute(
        "report",
        R.string.report,
        R.drawable.ic_baseline_poll_24
    )

    object Profile : AppRoute(
        "profile",
        R.string.profile,
        R.drawable.ic_baseline_person_24
    )
}
