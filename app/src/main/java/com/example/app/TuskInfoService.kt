package com.example.app

import com.github.javafaker.Faker

typealias TuskListener = (tusksInfo: List<TuskInfo>) -> Unit

class TuskInfoService {
    private var tusksInfo = mutableListOf<TuskInfo>()
    val faker = Faker.instance()

    init {
        tusksInfo = (1..3).map {
            TuskInfo(
                id = it,
                tuskTitle = faker.book().title(),
                info = null,
            )
        }.toMutableList()
    }

    fun getTuskById(id: Number): TuskInfoDetails {
        val tusk = tusksInfo.firstOrNull { it.id == id } ?: throw TuskInfoNotFoundException()
        return TuskInfoDetails(
            tuskInfo = tusk,
            details = tusk.info ?: Faker.instance().lorem().paragraphs(3).joinToString("\n\n")
        )
    }

    fun getNewTusk(id: Int): TuskInfoDetails {
        val tusk = TuskInfo(id = id, tuskTitle = "", info = "")
        return TuskInfoDetails(
            tuskInfo = tusk,
            details = tusk.info ?: Faker.instance().lorem().paragraphs(3).joinToString("\n\n")
        )
    }

    fun changeTusk(tuskInfo: TuskInfo, title: String, info: String) {
        val index =
            tusksInfo.indexOfFirst { it.id == tuskInfo.id }
        if (index == -1) return
        tusksInfo = ArrayList(tusksInfo)
        tusksInfo[index] = tusksInfo[index].copy(
            tuskTitle = title,
            info = info
        )
        notifyChanges()
    }

    fun createTusk(id: Int, title: String, info: String) {
        val tusk = TuskInfo(id = id, tuskTitle = title, info = info)
        tusksInfo.add(tusk)
        notifyChanges()
    }

    fun removeTusk(tuskInfo: TuskInfo) {
        val index =
            tusksInfo.indexOfFirst { it.id == tuskInfo.id }
        if (index == -1) return
        tusksInfo = ArrayList(tusksInfo)
        tusksInfo.removeAt(index)
        notifyChanges()
    }

    private var listeners = mutableListOf<TuskListener>()

    fun addListener(listener: TuskListener) {
        listeners.add(listener)
        listener.invoke(tusksInfo)
    }

    fun removeListener(listener: TuskListener) {
        listeners.remove(listener)
        listener.invoke(tusksInfo)
    }


    private fun notifyChanges() {
        listeners.forEach { it.invoke(tusksInfo) }
    }


}