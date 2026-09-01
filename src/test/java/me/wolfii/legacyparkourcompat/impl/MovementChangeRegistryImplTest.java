package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.EyeHeightBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import net.minecraft.world.entity.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovementChangeRegistryImplTest {
    @MechanicType("test.alpha")
    interface Alpha extends VersionedMechanic {
    }

    @MechanicType("test.beta")
    interface Beta extends VersionedMechanic {
    }

    @MovementChange(emulates = ParkourVersion.V1_8)
    static final class Both implements Alpha, Beta {
    }

    @MovementChange(emulates = ParkourVersion.V1_12)
    static final class LaterAlpha implements Alpha {
    }

    @Test
    void poseAndEyeHeightAreSeparateMechanicTypes() {
        assertEquals("player.pose", PlayerPoseBehavior.class.getAnnotation(MechanicType.class).value());
        assertEquals("player.eye_height", EyeHeightBehavior.class.getAnnotation(MechanicType.class).value());
        assertEquals("player.dimensions", PlayerDimensionsBehavior.class.getAnnotation(MechanicType.class).value());
        assertEquals("player.auto_jump", AutoJumpBehavior.class.getAnnotation(MechanicType.class).value());
        assertEquals("player.sneak.edge", SneakEdgeBehavior.class.getAnnotation(MechanicType.class).value());
        assertEquals(
            "player.sneak.edge.distance",
            SneakEdgeDistanceBehavior.class.getAnnotation(MechanicType.class).value()
        );
        assertEquals("block.collision", BlockCollisionShape.class.getAnnotation(MechanicType.class).value());
    }

    @Test
    void mechanicTypesCollectsEveryHookInterface() {
        List<Class<? extends VersionedMechanic>> types = MovementChangeRegistryImpl.mechanicTypes(Both.class);
        assertEquals(2, types.size());
        assertTrue(types.contains(Alpha.class));
        assertTrue(types.contains(Beta.class));
    }

    @Test
    void registerAnnotatesEachImplementedHook() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        Both both = new Both();
        registry.register(both);

        Map<MechanicKey, RegisteredChange> resolved = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8);
        assertEquals(2, resolved.size());
        assertSame(both, resolved.get(MechanicKey.of(Alpha.class)).implementation());
        assertSame(both, resolved.get(MechanicKey.of(Beta.class)).implementation());
    }

    @Test
    void laterOverrideWinsOnlyForThatHook() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        Both both = new Both();
        LaterAlpha later = new LaterAlpha();
        registry.register(both);
        registry.register(later);

        Map<MechanicKey, RegisteredChange> for18 = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8);
        assertSame(both, for18.get(MechanicKey.of(Alpha.class)).implementation());
        assertSame(both, for18.get(MechanicKey.of(Beta.class)).implementation());

        Map<MechanicKey, RegisteredChange> for12 = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_12);
        assertSame(later, for12.get(MechanicKey.of(Alpha.class)).implementation());
        assertEquals(1, for12.size());
    }

    @MovementChange(emulates = ParkourVersion.V1_8)
    static final class CorrectCocoa implements BlockCollisionShape {
        @Override
        public String blockId() {
            return "minecraft:cocoa";
        }
    }

    @MovementChange(emulates = ParkourVersion.V1_10_1)
    static final class BuggedCocoa implements BlockCollisionShape {
        @Override
        public String blockId() {
            return "minecraft:cocoa";
        }
    }

    @Test
    void closestCocoaWinsSo18DoesNotInheritThe19Bug() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        CorrectCocoa correct = new CorrectCocoa();
        BuggedCocoa bugged = new BuggedCocoa();
        registry.register(correct);
        registry.register(bugged);

        MechanicKey cocoa = MechanicKey.of(BlockCollisionShape.class, "minecraft:cocoa");
        assertSame(correct, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8).get(cocoa).implementation());
        assertSame(bugged, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_9).get(cocoa).implementation());
        assertSame(bugged, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10).get(cocoa).implementation());
        assertSame(bugged, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10_1).get(cocoa).implementation());
        assertNull(ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_11).get(cocoa));
    }

    @MovementChange(emulates = ParkourVersion.V1_10)
    static final class FullFarmland implements BlockCollisionShape {
        @Override
        public String blockId() {
            return "minecraft:farmland";
        }
    }

    @Test
    void fullFarmlandStopsAt101() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        FullFarmland farmland = new FullFarmland();
        registry.register(farmland);

        MechanicKey key = MechanicKey.of(BlockCollisionShape.class, "minecraft:farmland");
        assertSame(farmland, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8).get(key).implementation());
        assertSame(farmland, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10).get(key).implementation());
        assertNull(ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10_1).get(key));
    }

    @MovementChange(emulates = ParkourVersion.V1_10_1)
    static final class OneBlockSneak implements SneakEdgeDistanceBehavior {
        @Override
        public float edgeFallDistance(Player player, float vanillaMaxUpStep) {
            return 1.0F;
        }
    }

    @Test
    void oneBlockSneakEdgeLastsThrough101() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        OneBlockSneak sneak = new OneBlockSneak();
        registry.register(sneak);

        MechanicKey key = MechanicKey.of(SneakEdgeDistanceBehavior.class);
        assertSame(sneak, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8).get(key).implementation());
        assertSame(sneak, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10).get(key).implementation());
        assertSame(sneak, ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_10_1).get(key).implementation());
        assertNull(ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_11).get(key));
    }
}
