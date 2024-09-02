package net.walksanator.hexxyskies.casting.iotas

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.resources.ResourceLocation
import net.walksanator.hexxyskies.HexSkyCommon
import java.util.function.BiConsumer

object IotaRegistry {
    private val IOTATYPES = mutableMapOf<ResourceLocation,IotaType<*>>()
    private var registered = false;

    val SHIP = IOTATYPES.put(ResourceLocation(HexSkyCommon.MOD_ID, "ship"), ShipIota.Type)


    fun register(consumer: BiConsumer<IotaType<*>, ResourceLocation>) {
        IOTATYPES.forEach { consumer.accept(it.value,it.key) }
    }
}