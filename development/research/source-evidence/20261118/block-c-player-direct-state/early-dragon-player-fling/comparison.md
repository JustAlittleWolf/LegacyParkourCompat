# Ender Dragon creative-player contact fling: bounded source comparison

## Result

The first changed gate is the wing-contact candidate query in 1.14. In 1.13.2, dragon contact fling is already enclosed by a logical-server-only gate (`!world.isClient`), but the wing queries have no creative-player exclusion; `flingMobs` adds velocity to every selected `LivingEntity`. Thus, for a creative player selected by a wing query, the dragon does not directly mutate local-player velocity on the logical client in 1.13.2: its direct velocity addition runs on the logical server.

From 1.14 through 1.14.4, the same server-only gate remains and the wing query adds a creative-and-spectator exclusion (`NOT_SPECTATOR_NOT_CREATIVE`, `EXCEPT_CREATIVE_OR_SPECTATOR`, or `NO_CREATIVE_OR_SPECTATOR`). A creative player is therefore not passed to the fling helper; this contact path makes no dragon-side velocity write for that player. No examined release performs this fling as a logical-client-local direct write.

## Exact source slices

- **1.13.2, Ornithe Feather** — `EnderDragonEntity.java:303-305` gates both wing queries with `!this.world.isClient && this.damagedTimer == 0`, with no selector; `:402-417` shows `flingMobs`, including `entity.addVelocity(...)` at `:411`. The helper is called only from that server-side block.
- **1.14, Ornithe Feather** — `EnderDragonEntity.java:291-299` retains `!this.world.isClient && this.damagedTimer == 0` and supplies `EntityFilter.NOT_SPECTATOR_NOT_CREATIVE` to both wing queries; `:396-412` contains the corresponding helper and velocity addition at `:405`.
- **1.14.1, Yarn** — `EnderDragonEntity.java:272-287` retains `!this.world.isClient && this.hurtTime == 0`; both wing queries use `EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR` at `:278` and `:286`. `:385-401` has helper `method_6825`; its `addVelocity` is at `:394`.
- **1.14.2, Yarn** — same server gate and both creative/spectator-filtered wing queries at `:272-287`; helper `method_6825` and `addVelocity` at `:385-401` / `:394`.
- **1.14.3, Yarn** — same gate and filters at `:291-306`; helper `method_6825` and `addVelocity` at `:404-420` / `:413`.
- **1.14.4, Mojmap** — `EnderDragon.java:274-282` gates contact with `!this.level.isClientSide && this.hurtTime == 0` and uses `EntitySelector.NO_CREATIVE_OR_SPECTATOR` on both wing queries; `:379-395` has helper `knockBack`, whose velocity operation is `entity.push(...)` at `:388`.

The filtered list is formed before the helper receives it. The later selector is the relevant creative-player gate; the logical-side gate is not a new 1.14 change.

## Bounded packet evidence

For the 1.13.2 producer, `Entity.addVelocity` (`Entity.java:1219-1224`) updates the entity's three velocity components and sets `velocityDirty`. `TrackedEntity.java:217-232` checks `syncVelocity || entity.velocityDirty`, applies a velocity-delta threshold, then constructs and sends `EntityVelocityS2CPacket`. This establishes a server-side tracked-entity velocity-packet producer for dirty velocity. `ClientPlayNetworkHandler.handleEntityVelocity` (`ClientPlayNetworkHandler.java:507-513`) resolves the packet entity ID and applies the packet components divided by 8000 through `entity.lerpVelocity(...)`.

This is a bounded producer/consumer result, not proof that the tracked-entity fanout includes the owning client for its own player entity. The dragon contact path itself contains no direct logical-client player-velocity write. Whether a particular 1.13.2 local creative player's server velocity is delivered through that packet path is left unclaimed here.

## Provenance and evidence identity

The 1.13.2 Feather and 1.14 Feather trees have local provenance manifests identifying exact release and mapping namespace. The 1.14.4 Mojmap tree also has a local exact-version provenance manifest. The local 1.14.1, 1.14.2, and 1.14.3 source trees are under `yarn/`, but these directories contain no local provenance manifest or source manifest. Their conclusions are bound below to the exact selected local source-file bytes; mapping artifact/build provenance for those three trees was unavailable in the permitted local evidence. No online mappings, product source, wiki material, or Git history were consulted.

Selected source SHA-256 values:

- `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java` — `0c0d8aa04f5b8c2264ff889de517b67d5f084ed6e17515cf696dc4c450e4930e`
- `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java` — `aae506e5d5e7911832ed8f00aa5d2889af60daf0f39266d414421bbf468ee736`
- `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/boss/dragon/EnderDragonEntity.java` — `f07ca7c57cdba4cea873eaa94b77cc338371286959213df36fbe8634e970d1f5`
- `decompiled_minecraft/1.14.2/yarn/net/minecraft/entity/boss/dragon/EnderDragonEntity.java` — `03e4978fd77d6e6dc2814cf7829da5c3f49b89a3b242c5b111020aedb86ab61f`
- `decompiled_minecraft/1.14.3/yarn/net/minecraft/entity/boss/dragon/EnderDragonEntity.java` — `946391fa7d3355d8662449a3ce5bfd133dfa87e5c3e79e4ded539e456fc4df79`
- `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/boss/enderdragon/EnderDragon.java` — `c2ca051e803040a968504b2af059cad5e8d3a7edb22e3a6d54382bc3e19a6190`
- `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/server/TrackedEntity.java` — `9a8e5100eb56e829fe62221ac1cb6d76b8c26c37c6ee01eecec5a738a4b1f5c6`
- `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` — `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11`

Available local provenance manifest SHA-256 values:

- `decompiled_minecraft/1.13.2/ornithe-feather.provenance.json` — `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` (version `1.13.2`, namespace `ornithe-feather`, Feather Gen2 build 2)
- `decompiled_minecraft/1.14/ornithe-feather.provenance.json` — `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` (version `1.14`, namespace `ornithe-feather`, Feather Gen2 build 2)
- `decompiled_minecraft/1.14.4/mojmap.provenance.json` — `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` (version `1.14.4`, namespace `mojmap`)

**Bounds:** exact local source comparison only for 1.13.2, 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4; dragon movement, damage eligibility, combat, and gameplay replication are not assessed. No build, tests, launches, source generation, implementation, or delegation performed.

**Terminal:** COMPLETED — source and local-provenance scope above; 1.14.1-.3 mapping provenance remains unavailable.