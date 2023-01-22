package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.jda.JDAPlatform
import me.alexirving.lib.util.pq

class TestEnv {

    init {
        val p = JDAPlatform("OTE3ODQyNjg4MDQ3NDY0NDc4.GTYaJn.tmrbASpMqOQEBIWxDvu8-ZF9y1ARPlnkajMDgs")
        p.register(TestCommand().pq())
    }
}

