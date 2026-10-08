package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class MovementChangeRegistryImplTest {
    @MechanicType("test.alpha")
    interface Alpha extends VersionedMechanic {
    }

    @MechanicType("test.beta")
    interface Beta extends VersionedMechanic {
    }

    static final class Both implements Alpha, Beta {
    }

    static final class LaterAlpha implements Alpha {
    }

    @Test
    void explicitRegistrationAddsOnlyTheDeclaredHook() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        Both both = new Both();
        registry.register(Alpha.class, ParkourVersion.V1_8, both);

        Map<MechanicKey, RegisteredChange> resolved = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8);
        assertEquals(1, resolved.size());
        assertSame(both, resolved.get(MechanicKey.of(Alpha.class)).implementation());
        assertFalse(resolved.containsKey(MechanicKey.of(Beta.class)));
    }

    @Test
    void explicitRegistrationsCanShareOneImplementationAcrossHooks() {
        MovementChangeRegistryImpl registry = new MovementChangeRegistryImpl(() -> {
        });
        Both both = new Both();
        registry.register(Alpha.class, ParkourVersion.V1_8, both);
        registry.register(Beta.class, ParkourVersion.V1_8, both);

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
        registry.register(Alpha.class, ParkourVersion.V1_8, both);
        registry.register(Beta.class, ParkourVersion.V1_8, both);
        registry.register(Alpha.class, ParkourVersion.V1_12, later);

        Map<MechanicKey, RegisteredChange> for18 = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_8);
        assertSame(both, for18.get(MechanicKey.of(Alpha.class)).implementation());
        assertSame(both, for18.get(MechanicKey.of(Beta.class)).implementation());

        Map<MechanicKey, RegisteredChange> for12 = ChangeResolver.resolve(registry.snapshot(), ParkourVersion.V1_12);
        assertSame(later, for12.get(MechanicKey.of(Alpha.class)).implementation());
        assertEquals(1, for12.size());
    }
}
