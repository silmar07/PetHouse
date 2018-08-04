package com.fdi.pad.pethouse.home.utils

import android.support.v4.app.Fragment
import com.fdi.pad.pethouse.R
import com.fdi.pad.pethouse.home.fragments.*

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0,R.id.btnHome),
    USER(1, R.id.btnUsuario),
    SEARCH(2, R.id.btnLupa),
    PET(3, R.id.btnHuella),
    SETTINGS(4, R.id.btnAjustes);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.USER.id -> BottomNavigationPosition.USER
    BottomNavigationPosition.SEARCH.id -> BottomNavigationPosition.SEARCH
    BottomNavigationPosition.PET.id -> BottomNavigationPosition.PET
    BottomNavigationPosition.SETTINGS.id -> BottomNavigationPosition.SETTINGS
    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.HOME -> FragmentHomeHome.newInstance()
    BottomNavigationPosition.USER -> FragmentHomeUser.newInstance()
    BottomNavigationPosition.SEARCH -> FragmentHomeSearch.newInstance()
    BottomNavigationPosition.PET -> FragmentHomePet.newInstance()
    BottomNavigationPosition.SETTINGS -> FragmentHomeSettings.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> FragmentHomeHome.TAG
    BottomNavigationPosition.USER -> FragmentHomeUser.TAG
    BottomNavigationPosition.SEARCH -> FragmentHomeSearch.TAG
    BottomNavigationPosition.PET -> FragmentHomePet.TAG
    BottomNavigationPosition.SETTINGS -> FragmentHomeSettings.TAG
}