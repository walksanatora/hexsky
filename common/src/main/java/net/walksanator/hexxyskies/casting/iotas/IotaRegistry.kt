package net.walksanator.hexxyskies.casting.iotas

import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister
import net.walksanator.hexxyskies.HexSkyCommon

object IotaRegistry {
    private val REGISTRY = DeferredRegister.create(HexSkyCommon.MOD_ID, HexRegistries.IOTA_TYPE)
    private var registered = false;

    val SHIP = REGISTRY.register("ship") {ShipIota.Type}

    fun register() {
        if (registered) return
        REGISTRY.register()
        registered = true
    }
}