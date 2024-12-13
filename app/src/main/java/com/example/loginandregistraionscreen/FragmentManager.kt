package com.example.loginandregistraionscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.commit(
    allowStateLoss: Boolean = false,
    action: FragmentTransaction.() -> Unit
) {
    val transaction = beginTransaction()
    transaction.action()
    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        transaction.commit()
    }
}
