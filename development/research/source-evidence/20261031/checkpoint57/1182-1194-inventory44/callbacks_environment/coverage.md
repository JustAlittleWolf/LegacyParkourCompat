# Callbacks and environment lane: 1.18.2–1.19.4

Read-only source inventory of the assigned callback, fluid, equipment/attribute, status-effect, registration, and externally triggered player-response paths. Sources were the six exact local Mojmap roots named in the assignment. No product code/history, wiki, runtime, tests, builds, or launches were used.

## Pair coverage

| Adjacent edge | Checked member groups and relevant source observations | Disposition |
|---|---|---|
| 1.18.2 → 1.19 | `Entity.move` step callback and fluid push/contact call sites; `Block.stepOn`/`entityInside` implementations in changed block sources; `LivingEntity` Soul Speed/Frost Walker lookup; player movement-speed/effect consumers; enchantment registration and local input path. `Entity.getOnPos()`'s y-offset/support fallback is preserved as `getOnPosLegacy()` / `getBlockStateOnLegacy()` for the old Soul Speed/Frost Walker update path. | No eligible historical callback delta found. `Entity.move` drops its outer `!isSteppingCarefully()` gate for `stepOn`, but the pre-existing movement-sensitive `SlimeBlock`, `MagmaBlock`, `RedStoneOreBlock`, and `TurtleEggBlock` callbacks each retain the careful-step gate internally in 1.19; the slime velocity response therefore remains guarded. The newly added Sculk Sensor step callback emits a server-side vibration; it does not write player position/velocity. Other changed fire/magma callbacks apply damage/fire state only. Swift Sneak is a post-1.18.2 feature; see disposition below. |
| 1.19 → 1.19.1 | `Entity` step/ground GameEvent context, `LivingEntity.tick`/`aiStep`, player, local-player input, block callback and effect sources. | GameEvent actor attribution changes; `LivingEntity` now skips `aiStep` once removed. No ordinary live-player movement response changed in these inspected members. Chat, NBT-bound, and removed-entity paths do not establish a movement candidate. |
| 1.19.1 → 1.19.2 | Same selected `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, block/fluid callback, and movement-effect members. | No movement-response candidate found in the selected members. This is not a claim that every Java source file is unchanged. |
| 1.19.2 → 1.19.3 | `Entity.checkInsideBlocks` dispatch, `Entity`/`LivingEntity` fall-state writers and readers, `Player` predicates, status-effect lifetime, attribute/effect registration and fluid registry/provider code. | Contact-inset and fall-distance/edge-backoff candidates are already covered by the permitted finish43 source-only applicability notes and are deliberately not repeated here. Other relevant effect/attribute/fluid changes in the selected members are registry/API refactors; no additional direct player movement callback was found. |
| 1.19.3 → 1.19.4 | Effect command registration and server production, effect packet encoding/client receipt, effect ticking and player jump/speed consumers; armor/attribute registration; entity tags; fluid conversion rules; block callback bodies; `Entity` max-up-step access. | Infinite-duration effects have a source-confirmed Jump Boost consumer but arise from a feature added in 1.19.4; disposition below, not an emulation finding. Other inspected changes are damage-source rewrites, modern entity tags, passenger/vehicle rules, or access/API refactors. |

## Closed movement-related dispositions

### Swift Sneak: new 1.19 feature

In 1.19, `Enchantments` registers `SWIFT_SNEAK`; `EnchantmentHelper.getSneakingSpeedBonus(LivingEntity)` returns its level multiplied by `0.15F` (`net/minecraft/world/item/enchantment/Enchantments.java:30`, `EnchantmentHelper.java:193–195`). `LocalPlayer` adds that bonus to the crouch input scale, clamps it, and passes it to `Input.tick` (`net/minecraft/client/player/LocalPlayer.java:739–740`). The enchantment and helper are absent in 1.18.2. This is directly movement-affecting but is a modern-only feature under the project invariant, so no legacy emulation finding is drafted.

### Infinite-duration status effects: new 1.19.4 producer

The 1.19.3 vanilla `/effect give` registration accepts an omitted duration or a `seconds` integer constrained to 1–1,000,000; its producer creates a positive duration (`server/commands/EffectCommands.java:48–139`). `MobEffectInstance.loadSpecifiedEffect` clamps saved duration to `Math.max(duration, 0)` (`world/effect/MobEffectInstance.java:267–289`). A source scan found no vanilla `-1` duration producer or `INFINITE_DURATION` sentinel in the 1.19.3 Mojmap tree. The client packet receiver accepts a duration from `ClientboundUpdateMobEffectPacket` and installs it through `forceAddEffect` (`client/multiplayer/ClientPacketListener.java:1433–1452`), but that is server-supplied state, not a vanilla client-side producer. No vanilla local-player input path creates a `MobEffectInstance` with a negative duration. The protocol receiver alone does not establish that ordinary 1.19.3 vanilla play can produce that state.

In 1.19.4, `EffectCommands.register` adds `/effect give <targets> <effect> infinite` branches that pass `-1`; `giveEffect` constructs and applies that instance (`server/commands/EffectCommands.java:115–162, 168–182`). A `ServerPlayer` target sends its active effect as `ClientboundUpdateMobEffectPacket` from `onEffectAdded` (`server/level/ServerPlayer.java:1181–1185`); the packet carries `MobEffectInstance.getDuration()` (`network/protocol/game/ClientboundUpdateMobEffectPacket.java:22–26`), and the client receiver preserves the received duration in its instance (`client/multiplayer/ClientPacketListener.java:1483–1502`). `MobEffectInstance.tick` now treats `-1` as a remaining duration and does not decrement it (`world/effect/MobEffectInstance.java:128–141, 168–196`); 1.19.3 only ticks while duration is positive and decrements it (`world/effect/MobEffectInstance.java:155–182`).

The movement consumer is unchanged: `LivingEntity.getJumpBoostPower()` reads active `MobEffects.JUMP`, and `jumpFromGround()` adds that power to jump velocity (`world/entity/LivingEntity.java:1951–1957` in 1.19.4; `:2020–2026` in 1.19.3). This proves that a server-applied infinite Jump Boost remains an input to local player jump movement in 1.19.4. Its creation/retention capability is new and modern-only; it is recorded as a scope disposition, not a behavior to emulate for pre-1.19.4 movement.

The 1.19.4 source roots supplied for this task contain Mojmap Java sources only. Datapack/function contents, external resource files, generated data, and custom-server packet producers were therefore not audited. The vanilla Java command and receiver path above is closed; no claim is made about those external producers.

## Other inspected paths and limits

- `Entity.move` invokes block `updateEntityAfterFallOn`/`stepOn`; block `entityInside` dispatch is in `Entity.checkInsideBlocks`. Across the changed block-hook implementations searched for these adjacent edges, the changed callback bodies either retain the same behavior or only emit events, alter damage/fire state, or concern newer block content. This report intentionally omits the prior honey callback, fall-distance edge-backoff, correction/respawn-copy, and airborne-sprint audits.
- For the `stepOn` careful-step gate comparison, exact paired hook locations are `Entity.java:607–609` (1.18.2) and `Entity.java:611–613` (1.19); `SlimeBlock.java:45–52` both versions; `MagmaBlock.java:28–34` both; `RedStoneOreBlock.java:39–42` both; and `TurtleEggBlock.java:46–49` (1.18.2), `:47–50` (1.19). The old outer gate and new internal gates are behaviorally aligned for those four existing hooks.
- `LivingEntity` Soul Speed/Frost Walker update paths use the legacy on-block query after 1.19 introduces a newer on-block query; the legacy query retains the 1.18.2 y-offset and fence/wall/gate fallback. Equipment/attribute source differences checked here did not alter player movement-effect formulas. The 1.19.4 armor type/API rework keeps the existing armor/armor-toughness values and does not introduce a movement attribute.
- The 1.19.3 fluid changes inspected in `Fluid`, `FluidState`, `FlowingFluid`, `WaterFluid`, and `LavaFluid` use built-in registry/API changes and game-rule gates for source conversion; they do not change the per-player fluid pushing/contact math in the inspected sources. Dynamic world-state production and tags' data contents remain unexamined because those resource inputs are absent from the allowed Mojmap roots.
- `EntityTypeTags.FALL_DAMAGE_IMMUNE` affects the excluded fall-damage response. `EntityTypeTags.DISMOUNTS_UNDERWATER` is used by a server-gated passenger/vehicle branch, outside player-on-foot movement and vehicle physics.
- Pose/dimension and collision-shape resolution was not closed in this lane; it is deferred to the collision owner. Profile switching, rendering, non-player physics, vehicle physics, damage resolution, and health/food production remain outside this inventory.

## Source identity and selected file hashes

The supplied source-manifest SHA-256 values were used as release identities:

| Version | Source manifest SHA-256 |
|---|---|
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

SHA-256 was independently computed for these exact files used above:

| Version | Mojmap source file | SHA-256 |
|---|---|---|
| 1.18.2 | `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.18.2 | `net/minecraft/world/level/block/SlimeBlock.java` | `4e552c1d1aa49b115f1549a8f19415b0c9f81c0524c0bf4afed37277d75f6388` |
| 1.19 | `net/minecraft/world/level/block/SlimeBlock.java` | `4e552c1d1aa49b115f1549a8f19415b0c9f81c0524c0bf4afed37277d75f6388` |
| 1.18.2 | `net/minecraft/world/level/block/MagmaBlock.java` | `7403efb17bee9ef2e1cccb587e5cce6b84f04f369a4c649e77263f164cd095ca` |
| 1.19 | `net/minecraft/world/level/block/MagmaBlock.java` | `cc1a02ac7ef23f61b4a5539ce3bd700be06ddf0d8673dee5b169413cc9236bbd` |
| 1.18.2 | `net/minecraft/world/level/block/RedStoneOreBlock.java` | `489f0b98dab62b5183fcbd2cf70e671dcdb2573f45d5e7488e13c31e6f98c8e5` |
| 1.19 | `net/minecraft/world/level/block/RedStoneOreBlock.java` | `cb408bacffd402ead0e04ad0a64f190141c73fb33e060beac8ccb0818669e511` |
| 1.18.2 | `net/minecraft/world/level/block/TurtleEggBlock.java` | `79c495789ff7183321456e357f1c34bbb678a627bb4a80d9109e8654ed1b662f` |
| 1.19 | `net/minecraft/world/level/block/TurtleEggBlock.java` | `39e4e6e92b6234e33b3dbb7d9a85f1dfb26ab1a9341209e2bafa8637b3fd4dd3` |
| 1.18.2 | `net/minecraft/client/player/LocalPlayer.java` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 | `net/minecraft/client/player/LocalPlayer.java` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.18.2 | `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `73d83d685f1f7872b5b85de26e274fc547c6094c4e2c2fa13291fb8cde712105` |
| 1.19 | `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `0565d51d86fbd0854b4bf256c0e0eff1caaace22b4c8f5f59b3a98e402f6319d` |
| 1.19.3 | `net/minecraft/server/commands/EffectCommands.java` | `8529fa88ab7cd014021fb09a1638024f44adefd111882c9010db39124ae2cb82` |
| 1.19.4 | `net/minecraft/server/commands/EffectCommands.java` | `8ca069f0e0e2631e1f41eb22e8f3a6f6168a2cbcda608383f3519f39ea9dcdcf` |
| 1.19.3 | `net/minecraft/world/effect/MobEffectInstance.java` | `151cc45017d1d77dd841cb4a6b7f7e659cbe4ead631e701fe440f9479879eb00` |
| 1.19.4 | `net/minecraft/world/effect/MobEffectInstance.java` | `72ea45b4c7fcbea5b11d4101f1e5ec9d76b3fc978a456607923e2bf0ad3b22c7` |
| 1.19.3 | `net/minecraft/server/level/ServerPlayer.java` | `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c` |
| 1.19.4 | `net/minecraft/server/level/ServerPlayer.java` | `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606` |
| 1.19.3 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| 1.19.4 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715` |
| 1.19.3 | `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| 1.19.4 | `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |

No eligible emulation finding is drafted from this lane; the two directly movement-affecting feature paths above are post-1.18.2 features, and the permitted prior audits cover the repeated contact/fall-distance candidates.
