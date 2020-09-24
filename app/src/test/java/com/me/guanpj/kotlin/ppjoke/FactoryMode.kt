package com.me.guanpj.kotlin.ppjoke

interface Computer {
    val cpu: String
}

class PC(override val cpu: String = "core") :
    Computer

class Server(override val cpu: String = "Xeon") :
    Computer

enum class ComputerType {
    PC, Server
}

class ComputerFactory {
    fun produce(type: ComputerType): Computer = when(type) {
        ComputerType.PC -> PC()
        ComputerType.Server -> Server()
    }
}

object ComputerFactory1 {
    fun produce(type: ComputerType): Computer = when(type) {
        ComputerType.PC -> PC()
        ComputerType.Server -> Server()
    }
}

object ComputerFactory2 {
    operator fun invoke(type: ComputerType): Computer = when(type) {
        ComputerType.PC -> PC()
        ComputerType.Server -> Server()
    }
}

interface Computer1 {
    val cpu: String
    companion object Factory {
        operator fun invoke(type: ComputerType): Computer = when(type) {
            ComputerType.PC -> PC()
            ComputerType.Server -> Server()
        }
    }
}

fun Computer1.Factory.formCPU(cpu: String): ComputerType? = when(cpu) {
    "core" -> ComputerType.PC
    "xeon" -> ComputerType.Server
    else -> null
}

fun main() {
    val computer = ComputerFactory()
        .produce(ComputerType.Server)
    val computer1 =
        ComputerFactory1.produce(ComputerType.Server)
    val computer2 =
        ComputerFactory2(ComputerType.Server)

    val computer3: Computer = Computer1.Factory(ComputerType.PC)

    Computer1.Factory.formCPU("core")?.let { println(it.toString()) }

    println(computer3.cpu)
}