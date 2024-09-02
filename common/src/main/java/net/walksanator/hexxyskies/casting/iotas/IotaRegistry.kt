package net.walksanator.hexxyskies.casting.iotas

import at.petrak.hexcasting.api.casting.iota.IotaType
import net.minecraft.resources.ResourceLocation
import net.walksanator.hexxyskies.casting.patterns.register
import java.util.function.BiConsumer

object IotaRegistry {
    private val IOTATYPES = mutableMapOf<ResourceLocation,IotaType<*>>()
    private var registered = false;

    val SHIP = IOTATYPES.register("ship") {ShipIota.Type}


    fun register(consumer: BiConsumer<IotaType<*>, ResourceLocation>) {
        IOTATYPES.forEach { consumer.accept(it.value,it.key) }
    }
}