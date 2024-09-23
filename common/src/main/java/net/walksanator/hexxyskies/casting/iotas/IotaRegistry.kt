package net.walksanator.hexxyskies.casting.iotas

import at.petrak.hexcasting.api.casting.iota.IotaType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.walksanator.hexxyskies.HexSkyCommon
import net.walksanator.hexxyskies.casting.patterns.register
import net.walksantor.hextweaks.computer.IotaSerde
import net.walksantor.hextweaks.computer.IotaSerdeRegistry
import java.util.function.BiConsumer

@Suppress("UNCHECKED_CAST")
object IotaRegistry {
    private val IOTATYPES = mutableMapOf<ResourceLocation,IotaType<*>>()

    val SHIP: IotaType<ShipIota> = IOTATYPES.register("ship") {ShipIota.Type} as IotaType<ShipIota>

    init {
        IotaSerdeRegistry.register(
            ResourceLocation(HexSkyCommon.MOD_ID, "ship"), SHIP, object : IotaSerde<ShipIota> {
                override fun deserialize(value: Map<*, *>, world: Level): ShipIota? {
                    val id = (value["id"] as? Number)?.toLong() ?: return null
                    val name = value["name"] as? String
                    return ShipIota(id,name)
                }

                override fun serialize(input: ShipIota): Any = mutableMapOf(
                    "id" to input.ship,
                    "name" to input.name
                )
            })
    }

    fun register(consumer: BiConsumer<IotaType<*>, ResourceLocation>) {
        IOTATYPES.forEach { consumer.accept(it.value,it.key) }
    }
}