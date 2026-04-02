package net.mysticcreations.lib.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.mysticcreations.lib.LibraryContext;
import net.mysticcreations.lib.MysticLib;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EntityUtil {
    // Player
    public static void applyEffect(ServerPlayer player, MobEffect effects, int duration, int power) {
        player.addEffect(new MobEffectInstance(effects, duration, power, false, false, false));
    }
    public static void damage(Entity entity, Function<DamageSources, DamageSource> sourceSelector, float amount) {
        if (entity != null) {
            DamageSources sources = entity.level().damageSources();
            DamageSource source = sourceSelector.apply(sources);

            entity.hurt(source, amount);
        }
    }
    public static void damageWithInterval(Entity entity, Function<DamageSources, DamageSource> sourceSelector, float amount, int interval) {
        if (entity != null && entity.level().getGameTime() % interval == 0) {
            damage(entity, sourceSelector, amount);
        }
    }
    public static void moveToValidRespawnPos(ServerPlayer player) {
        BlockPos respawnPos = player.getRespawnPosition();
        ResourceKey<Level> respawnDim = player.getRespawnDimension();

        if (respawnPos != null) {
            ServerLevel targetLevel = player.server.getLevel(respawnDim);
            if (targetLevel != null) {
                Optional<Vec3> maybeSpot = Player.findRespawnPositionAndUseSpawnBlock(targetLevel, respawnPos, 0, player.isRespawnForced(), false);

                if (maybeSpot.isPresent()) {
                    Vec3 spot = maybeSpot.get();
                    player.teleportTo(targetLevel, spot.x, spot.y+0.05, spot.z, 180, 0);
                    return;
                }

                double fallbackX = respawnPos.getX()+0.5;
                double fallbackY = respawnPos.getY();
                double fallbackZ = respawnPos.getZ()+0.5;
                player.teleportTo(targetLevel, fallbackX, fallbackY+0.05, fallbackZ, 180,0);
            }
        }
    }
    public static boolean hasAdvancement(ServerPlayer player, String AdvancementID) {
        return player.getAdvancements().getOrStartProgress(
            player.server.getAdvancements().getAdvancement(MysticLib.Lib.asResource(AdvancementID))
        ).isDone();
    }
    public static void grantAdvancement(ServerPlayer player, String AdvancementID) {
        Advancement advancement = player.server.getAdvancements().getAdvancement(MysticLib.Lib.asResource(AdvancementID));
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria())
                player.getAdvancements().award(advancement, criteria);
        }
    }

    // Non-player
    // ----------

    // Generic
    public static <T extends Mob> List<T> getNearby(ServerPlayer player, Class<T> mob, double radius) {
        return player.level().getEntitiesOfClass(mob, player.getBoundingBox().inflate(radius));
    }
}
