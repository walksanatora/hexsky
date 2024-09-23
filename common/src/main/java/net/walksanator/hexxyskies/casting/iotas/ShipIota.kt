package net.walksanator.hexxyskies.casting.iotas

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.hasString
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.api.ships.properties.ShipId
import org.valkyrienskies.mod.common.shipObjectWorld

class ShipIota(val ship: ShipId, var name: String?) : Iota(Type,ship) {

    fun getShip(world: ServerLevel?): ServerShip? {
        val sow = world.shipObjectWorld
        val fly = sow.loadedShips.getById(ship)?: sow.allShips.getById(ship)
        name = fly?.slug
        return fly
    }

    object Type : IotaType<ShipIota>() {
        override fun deserialize(tag: Tag?, world: ServerLevel?): ShipIota? {
            if (tag !is CompoundTag) return null
            if (world.shipObjectWorld.allShips.getById(tag.getLong("id")) == null) {return null}
            return ShipIota(tag.getLong("id"), if(tag.hasString("name")) tag.getString("name") else "")
        }

        override fun display(tag: Tag?): Component = Component.translatable("hexsky.iota.ship",
            (tag as CompoundTag).getString("name")
        ).withStyle(ChatFormatting.GOLD)

        override fun color(): Int = 0xcda638
    }

    override fun isTruthy(): Boolean = true

    override fun toleratesOther(that: Iota?): Boolean {
        if (that !is ShipIota) {return false}
        return that.ship == this.ship
    }
    override fun serialize(): Tag {
        val tag = CompoundTag()
        tag.putLong("id", this.ship)
        if (name != null) {
            tag.putString("name",name!!)
        }
        return tag
    }
}