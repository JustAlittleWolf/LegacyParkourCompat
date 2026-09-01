package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.EyeHeightBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
