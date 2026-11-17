# 1.8.9 → 1.9 status-effect lifecycle and player movement consumers

## Source identity

- Compared exact `ornithe-feather` source roots: `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/1.9/ornithe-feather/`.
- 1.8.9 source-manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; supplied provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Every cited Java file below was rehashed from disk and matched its 1.8.9 sidecar or 1.9 provenance source row.

| Source path relative to `ornithe-feather/` | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/effect/StatusEffect.java` | `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3` | `ce025466266cdc45ae51ba09ea6714bb608afb856ee2f83f9738077a2c1ed698` |
| `net/minecraft/entity/living/effect/StatusEffectInstance.java` | `6b69d6232532247f004c0f493ab619e02651d383c3c766bbc6e9c9ea8913e34e` | `ec5e4b9c9bd516b43d5eddc8c5a3c7b0f652a19a1c8b2f08a9e343ccdd18865e` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |
| `net/minecraft/network/packet/s2c/play/EntityStatusEffectS2CPacket.java` | `a7513a7f748875fd3f1a282cfb0f9550772d9bc41503d2b85cf9b4e23528b171` | `bb7a367f1f981e23203737519d9bda2e99b38256c22992cfe470eb775e727cfe` |
| `net/minecraft/network/packet/s2c/play/EntityRemoveStatusEffectS2CPacket.java` | `783cc91078918795b1c966319ee2597d3a14215aaaf83e859a963b16ba55147e` | `6151c04872c8089b633f00d0f42a6e5a2505f26cd0774242930816e199afe0e0` |
| `net/minecraft/network/packet/s2c/play/EntityAttributesS2CPacket.java` | `b24f98567111cb698abb89770b24607000a6b8c5b2d2a970db076ee69e53ce41` | `b24f98567111cb698abb89770b24607000a6b8c5b2d2a970db076ee69e53ce41` |

The 1.9-only `net/minecraft/entity/living/effect/StatusEffects.java` source row was also checked: `03c36ed3dcfe459deb1810c1595d80585a22a9cb9f76a506f598e23701aa5c3c`.

## Ordered comparison

### Effect representation and lifecycle

1.8.9 `StatusEffect.java` defines an integer `id` and stores effects in `BY_ID`; 1.9 `StatusEffect.init()` registers the same legacy IDs and names, while `StatusEffects` exposes typed references. In both releases, speed is ID 1, slowness ID 2, and jump boost ID 8. `StatusEffectInstance` changes its key/reference from an integer to a `StatusEffect` object, but its relevant state transitions remain the same:

- `StatusEffectInstance.tick()` first checks `duration > 0`, invokes `shouldApply(duration, amplifier)` and `apply(entity)` if due, decrements duration once, then returns whether duration remains positive (1.8.9 lines 83–103; 1.9 lines 84–104). `combine()` preserves the same amplifier/duration precedence and ambient/particle updates (1.8.9 lines 42–59; 1.9 lines 47–62).
- `LivingEntity.tickStatusEffects()` iterates effect instances, ticks each, removes expired entries and reapplies periodic modifiers on the same 600-tick boundary. Expired effects are removed in this loop only on the logical server in both releases (1.8.9 lines 438–452; 1.9 lines 486–500).
- `addStatusEffect` inserts a new instance or combines an existing one and calls the same apply/upgrade hook (1.8.9 lines 540–549; 1.9 lines 592–603). Effect modifiers are installed/removed/reinstalled behind `!world.isClient` in both releases (1.8.9 lines 578–598; 1.9 lines 631–652). The 1.9 calls now use the effect object stored in the instance; the modifier operation itself is unchanged.
- `StatusEffect.addModifiers()` removes the prior UUID, then adds a new modifier with the same UUID, translated name plus amplifier, `getModifier(amplifier, modifier)`, and original operation. Both `getModifier` methods return `modifier.get() * (amplifier + 1)` (1.8.9 lines 258–287; 1.9 lines 170–199).

No duration, amplifier, per-tick apply order, expiry gate, or modifier scaling difference affecting player movement was found in this slice.

### Movement-relevant effect definitions and consumers

- **Speed and slowness:** the paired definitions retain the same `MOVEMENT_SPEED` modifier UUIDs, base values (`+0.2F` and `-0.15F`) and operation `2` (1.8.9 `StatusEffect.java` lines 24–31; 1.9 `StatusEffect.init()` lines 211–227). The amplifier multiplier is unchanged as described above. `PlayerEntity.getSpeed()` returns `(float)this.getAttribute(EntityAttributes.MOVEMENT_SPEED).get()` in both versions (1.8.9 lines 1297–1300; 1.9 lines 1386–1389). `LivingEntity.moveRelative()` consumes that virtual getter for grounded acceleration and the depth-strider water branch (1.8.9 lines 1132–1140, 1212–1217; 1.9 lines 1349–1357, 1427–1434), so the modifier targets the player movement attribute on the local movement path.
- **Jump boost:** the paired definitions use the same name and ID, with no attribute modifier. `LivingEntity.jump()` assigns `getJumpStrength()` and then, when jump boost is present, adds `(amplifier + 1) * 0.1F` to `velocityY`, before the sprint-jump impulse. The expression and order match (1.8.9 lines 1092–1108; 1.9 lines 1266–1278). `PlayerEntity.jump()` calls `super.jump()` first in both releases (1.8.9 lines 1268–1276; 1.9 lines 1355–1363). Jump boost also appears in fall-damage calculation, which is excluded by project scope and not a movement finding here.
- **Other registered effects:** Haste and mining fatigue acquire `ATTACK_SPEED` modifiers in 1.9; these do not target the player movement attribute. Hunger changes the hunger/fatigue manager, and health/damage effects remain outside movement scope. 1.9 adds levitation, whose `LivingEntity` tick path changes vertical velocity, but there is no 1.8.9 counterpart; it is a post-1.8 feature and is not a historical movement delta for the 1.8.9 profile. No slow-falling effect appears in either compared effect set.

### Client packet application

- `ClientPlayNetworkHandler.handleEntityStatusEffect` resolves the target entity and adds a `StatusEffectInstance` on the client in both releases (1.8.9 lines 1133–1143; 1.9 lines 1193–1205). The 1.9 packet handler now resolves the effect ID to a registered object and passes the packet's ambient bit; 1.8.9 constructs by integer ID and forces ambient false. Ambient and particles feed effect presentation/visibility; `StatusEffectInstance.tick()` and the movement consumers above use duration/amplifier/effect, not ambient. No movement difference from this packet-field change was found.
- `handleEntityRemoveStatusEffect` removes the corresponding ID-keyed or object-keyed entry from the client's effect map (1.8.9 lines 1223–1229; 1.9 lines 1271–1277). These handlers use `removeEffect`, which only removes the client map entry; it does not call the server-only modifier hooks.
- The paired `EntityAttributesS2CPacket.java` files are byte-identical by SHA-256. `handleEntityAttributes` also has the same behavior in both releases: for each entry it finds/registers the attribute, sets the base value, clears modifiers and adds the packet's modifier list (1.8.9 lines 1581–1607; 1.9 lines 1689–1715). This is the direct client application path for the effective movement attribute snapshot; server packet production/order relative to effect packets was not traced in this bounded slice.

## Result and remaining boundary

No new movement finding was identified. The checked speed/slowness modifiers and jump-boost impulse are unchanged. The representation and client ambient-field changes do not alter the paired movement consumers in this evidence. Direct speed movement depends on the received movement-attribute value; its client packet receiver is unchanged, while broader server tracker/packet timing remains outside this effect-lifecycle report. The independent equipment/attribute lifecycle report covers its separate inventory and movement-speed attribute trace.

This report closes only the compared status-effect lifecycle and direct movement-consumer slice. It does not accept or freeze the 1.8.9→1.9 pair; pose collision providers, piston response, step-height resolution and the rest of the complete tick/provider inventory remain separate coverage and audit gates.
