package com.raywenderlich.listmaker

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList) {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreference.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreference.apply()
    }

    fun readLists(): ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferenceContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            taskLists.add(list)
        }

        return taskLists
    }
}