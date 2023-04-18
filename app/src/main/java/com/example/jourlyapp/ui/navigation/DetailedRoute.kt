package com.example.jourlyapp.ui.navigation

class DetailedRoute {
    companion object {
        fun journalEntryRoute(entryId: Int, editable: Boolean): String {
            return AppRoute.Journal.route + "/$entryId" + "?editable=$editable"
        }
    }
}
