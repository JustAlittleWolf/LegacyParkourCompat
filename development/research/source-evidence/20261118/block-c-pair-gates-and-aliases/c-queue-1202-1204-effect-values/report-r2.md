# 1.20.2–1.20.4 movement effect values

## Terminal status

COMPLETED — bounded source values and lifecycle binding examined for Minecraft 1.20.2 → 1.20.4, Mojmap on both sides. Exact source identities: `decompiled_minecraft/1.20.2/mojmap` and `decompiled_minecraft/1.20.4/mojmap`; each side's `mojmap.provenance.json` identifies its exact version and Mojmap run. Both provenance records point to the 2026-10-07 exact run, Gradle 9.7.1, Java 25.0.3+9-LTS, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0, ASM 9.10.1. Source trees were read-only.

## Confirmed no-difference: registered movement-effect values

`net/minecraft/world/effect/MobEffects.java` is byte-identical between the pair. At lines 10–19, Speed and Slowness are registered as `BENEFICIAL` / `HARMFUL`, both on `Attributes.MOVEMENT_SPEED`, amounts `0.2F` and `-0.15F`, with `MULTIPLY_TOTAL`; these carry the same UUIDs on both sides. At line 37, Jump Boost is `BENEFICIAL` and has no attribute modifier declaration. At line 79, Dolphin's Grace is `BENEFICIAL`, likewise with no attribute modifier declaration. Registry keys are `speed`, `slowness`, `jump_boost`, and `dolphins_grace` respectively.

`net/minecraft/world/entity/player/Player.java` and `net/minecraft/world/entity/LivingEntity.java` movement consumers establish the relevant non-attribute values identically: `LivingEntity#getJumpBoostPower` returns `0.1F * (amplifier + 1.0F)` when Jump Boost is active, otherwise `0.0F`; the ground jump adds that to `0.42F * getBlockJumpFactor()`. In water travel, Dolphin's Grace is a presence gate that sets the friction interpolation variable to `0.96F`. These values and gates do not differ in the compared members.

## Confirmed no-difference: ordinary potion declarations and effect input

`net/minecraft/world/item/alchemy/Potions.java` is byte-identical. Java declarations bind Leaping / Long Leaping / Strong Leaping to Jump Boost with `(duration, amplifier)` `(3600,0)`, `(9600,0)`, `(1800,1)`; Swiftness variants bind Speed with `(3600,0)`, `(9600,0)`, `(1800,1)`; Slowness variants bind Slowness with `(1800,0)`, `(4800,0)`, `(400,3)`. Turtle Master declarations carry Slowness `(400,3)`, `(800,3)`, `(400,5)` alongside resistance, which is outside this assignment. Potion construction copies the supplied effect-instance list (`Potion.java`, byte-identical).

`PotionItem#finishUsingItem` (1.20.2 lines 43–58; 1.20.4 lines 43–58) has no difference in the relevant effect route: it processes potion effects only server-side, applies instantaneous effects directly with the declared amplifier and scale `1.0`, and otherwise calls `LivingEntity#addEffect` with a copied `MobEffectInstance`. The method sequence is: server-side gate, iterate `PotionUtils.getMobEffects`, call `applyInstantenousEffect` with the instance amplifier and scale `1.0` for instantaneous effects, or add a copied `MobEffectInstance` for persistent effects. Thus ordinary persistent potion modifiers use the same registered effect key, amplifier, and duration inputs in both releases.

## Same eligible player's runtime effect payload

The `ClientboundUpdateMobEffectPacket` class is byte-identical. Its payload contains entity ID, registry effect, amplifier encoded as a byte, duration ticks, flags, and optional factor data. `ClientPacketListener#handleUpdateMobEffect` in both releases resolves the entity and installs a `MobEffectInstance` with packet effect, duration, amplifier, ambient/visible/icon flags, and factor data. The local player therefore consumes the same vanilla effect instance state; the assigned Speed/Slowness attribute templates and Jump Boost / Dolphin's Grace inputs are unchanged.

`MobEffectInstance` and `MobEffect` are byte-identical. Their constructors preserve the supplied amplifier; the instance applies duration ticks and amplifier to timed effect ticking. The relevant LivingEntity add/update/remove lifecycle methods have matching behavior in inspected ranges (1.20.2 lines 941–1037; 1.20.4 lines 940–1036): add/update hooks maintain modifier application/removal, with attribute modifier templates scaled by `amplifier + 1`. No movement-relevant lifecycle difference was found. Broader enclosing classes differ in unrelated material; this result is limited to the named effect access, installation, and movement-consumer members.

## Instantaneous effect boundary

`PotionItem#finishUsingItem` invokes `applyInstantenousEffect` with the effect's amplifier and scale `1.0` on the server; it does not create a persistent client effect instance for instant effects. No movement-relevant persistent state or payload from this route is established by this assignment. Instant health/damage outcomes remain out of scope.

## Packaged resource membership and drafts

The canonical decompiled outputs contain Java sources, not the packaged vanilla data/resource tree. No `data/minecraft/potion/*.json` or effect resource membership is available in either published Mojmap source output, so resource membership/absence cannot be established from these exact source trees. Java registrations above are established independently from `MobEffects.java` and `Potions.java`. This is the exact missing packaged-membership evidence.

No finding drafts: no movement-value or lifecycle difference was observed in the assigned bounded comparison. Discovery-only coverage report; no code, tests, launches, or source generation performed.
