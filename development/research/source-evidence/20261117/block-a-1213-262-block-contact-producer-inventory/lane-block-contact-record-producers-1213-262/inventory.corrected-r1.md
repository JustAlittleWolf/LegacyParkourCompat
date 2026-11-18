# Block-contact movement-record producer inventory

## Scope and evidence freeze

Read only the exact decompiled source trees `decompiled_minecraft/1.21.3/mojmap/` and `decompiled_minecraft/26.2/unobfuscated/`, plus their source readiness/provenance manifests. Source-only inventory; no implementation, wiki, runtime, build, test, network or Git work.

Both ready markers report `status=ready`. The 1.21.3 Mojmap marker reports 5,655 source files and source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; 26.2 unobfuscated reports 7,055 files and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. I verified each manifest's hash against its ready marker and recomputed every listed source-file hash: 0 mismatches in either tree. Exact ready/provenance/artifact/diagnostic manifest file hashes and every relied-on Java-source hash are frozen in [frozen-roster.corrected-r1.tsv](frozen-roster.corrected-r1.tsv) (UTF-8, LF). Roster SHA-256: `7207b8b90fece803e627353c4b09d9628b9cae24f97a88b3503cb3941155338f`.

## Slice 1 — record producers, removals and correction invalidation

### 1.21.3 Mojmap

`Entity.movementThisTick` is a private `List` (`Entity.java:250`). Complete source-tree search finds all reads/writes of this field in `Entity.java` only. Its append sites are:

- `recordMovementThroughBlocks(from, to)` (`Entity.java:1034-1035`); the only callers are `ServerGamePacketListenerImpl.java:452,930`.
- `applyEffectsFromBlocks(from, to)` appends the supplied pair (`Entity.java:740-749`). The no-argument overload supplies `oldPosition()` and current `position` (`Entity.java:736-737`).

The two `recordMovementThroughBlocks` callers run in `ServerGamePacketListenerImpl`, whose `player` field is declared `ServerPlayer` (`ServerGamePacketListenerImpl.java:206`); these mutate server-side player records. They are not `LocalPlayer` calls. No source call site for `recordMovementThroughBlocks` exists in the client tree. The ordinary player movement recorder is therefore the inherited `Entity` block-effects path, reached from `LivingEntity.tick`, rather than an external caller of `recordMovementThroughBlocks`.

The field is cleared within the affected-by-blocks branch of explicit block effects (`Entity.java:741-752`) and directly by `teleportSetPosition` (`Entity.java:2721-2732`). The latter is reached by the server packet listener's teleport method (`ServerGamePacketListenerImpl.java:1011-1019`) and by `Entity` teleport-transition paths (`Entity.java:2649,2681`). The packet listener path operates on its `ServerPlayer` field.

### 26.2 unobfuscated

The private queue is `ArrayDeque<Entity.Movement>` with capacity hint 100 (`Entity.java:299`). Complete source-tree search finds every direct queue read/write in `Entity.java`. `Entity.move` calls private `addMovementThisTick` when its movement-record condition passes (`Entity.java:740-754`); the helper merges the two oldest records at size 100, then appends the new record (`Entity.java:920-929`). There is no `recordMovementThroughBlocks` method or caller in this source tree.

`removeLatestMovementRecording()` removes the queue tail only if nonempty (`Entity.java:931-934`). Its callers are the piston path (`PistonMovingBlockEntity.java:197`) and server correction/rejection paths (`ServerGamePacketListenerImpl.java:500,1198`); the latter acts on the declared `ServerPlayer player` (`ServerGamePacketListenerImpl.java:239`). `clearMovementThisTick()` clears the queue (`Entity.java:937-939`); direct callers are `Entity.teleportSetPosition` (`Entity.java:3158-3171`) and the minecart override (`AbstractMinecart.java:412-417`). The normal no-argument block-effects entry also clears the live queue after copying it, below.

`LocalPlayer` is a client-package class and inherits the `LivingEntity` tick path: `LocalPlayer extends AbstractClientPlayer` (`client/player/LocalPlayer.java:108`), `AbstractClientPlayer extends Player` (`client/player/AbstractClientPlayer.java:19`), and `Player extends Avatar` (`world/entity/player/Player.java:126`), `Avatar extends LivingEntity` (`world/entity/Avatar.java:13`). In 1.21.3 the same chain is `LocalPlayer -> AbstractClientPlayer -> Player -> LivingEntity` (`LocalPlayer.java:92`, `AbstractClientPlayer.java:17`, `Player.java:118`). The `LivingEntity` block-effects call is gated by `!level.isClientSide() || isLocalInstanceAuthoritative()` in both releases (`1.21.3 LivingEntity.java:2774-2775`; `26.2 LivingEntity.java:3143-3145`). `LocalPlayer.isLocalPlayer()` returns true (`1.21.3 LocalPlayer.java:347`; `26.2 LocalPlayer.java:378`); the 1.21.3 `Player.isLocalPlayer()` base returns false (`Player.java:1349`), and 26.2 `Player.isLocalClientAuthoritative()` delegates to that virtual method (`Player.java:1281-1285`). Thus on a logical client this path runs for the local player; server-side behavior remains separate.

Client correction handling was checked directly, not inferred from `teleportSetPosition`: `ClientPacketListener.handleMovePlayer` applies the packet to `minecraft.player` through `setValuesFromPositionPacket` (`1.21.3 ClientPacketListener.java:706-734`; `26.2 ClientPacketListener.java:795-828`). For this non-interpolated player correction, both versions call `setPos`, set velocity/rotation, and set old position/rotation; neither calls `teleportSetPosition`, `clearMovementThisTick`, nor `removeLatestMovementRecording`. Therefore the explicit queue clear in `Entity.teleportSetPosition` establishes teleport invalidation for callers of that method, not for the LocalPlayer packet-correction handler. The 26.2 client handler adds block-state-prediction `onTeleport()` at line 805, which also is not a movement-record queue mutation.

## Slice 2 — no-argument/explicit effects call graph and clearing behavior

The frozen roster lists every Java file in each source tree containing `applyEffectsFromBlocks(`, with hashes. The direct player path in both releases is `LivingEntity`'s gated tick callback above. The piston block-entity callback is a second direct route whenever its entity iteration selects the local player. Other call sites are entity-specific non-player paths and remain inventoried without treating them as LocalPlayer callbacks.

### 1.21.3 behavior

`Entity.applyEffectsFromBlocks()` delegates to explicit `applyEffectsFromBlocks(oldPosition(), position)` (`Entity.java:736-737`). The explicit method, when `isAffectedByBlocks()` is true, handles grounded step-on, appends its one pair to the live `movementThisTick` list, then gives that same live list to `checkInsideBlocks`; it clears the live movement list and `blocksInside` before leaving the branch (`Entity.java:740-752`). This is live-list accumulation/consumption, not a snapshot. If `isAffectedByBlocks()` is false, that entire append/check/clear branch is skipped.

Piston invokes the no-argument method after `Entity.move` (`PistonMovingBlockEntity.java:182-186`). Other explicit Vec3 callers found are `AbstractMinecart.java:397` and `AbstractArrow.java:249`; all are in the frozen caller roster.

### 26.2 behavior

`Entity.applyEffectsFromBlocks()` clears `finalMovementsThisTick`, copies the live `movementThisTick` queue into it, and clears the live queue (`Entity.java:903-906`). If the copy is empty, it adds one fallback movement from `oldPosition()` to current `position()`; otherwise it appends a final segment only when the last recorded endpoint differs from current position by squared distance greater than `9.9999994E-11F` (`Entity.java:907-911`). It passes `finalMovementsThisTick` to the private common consumer (`Entity.java:913,949+`), which retains the existing list for `applyEffectsFromBlocksForLastMovements()` (`Entity.java:916-917`). This is snapshot-and-clear of the live queue, unlike 1.21.3's live-list append/check/clear.

The explicit `applyEffectsFromBlocks(from,to)` creates `List.of(new Entity.Movement(from,to))` and calls that same private list consumer (`Entity.java:945-949`). It does not snapshot or clear `movementThisTick` and does not replace `finalMovementsThisTick`. Direct explicit callers in the inspected tree are `AbstractMinecart.java:416`, `AbstractArrow.java:290`, and `PistonMovingBlockEntity.java:196`.

The exact no-argument direct caller roster (excluding the overload definitions and internal `super`/override dispatch) is:

- 1.21.3: `LivingEntity`, `OldMinecartBehavior`, `NewMinecartBehavior`, `AbstractMinecart`, `AbstractBoat`, `EnderDragon`, `EndCrystal`, `ThrowableProjectile`, `ExperienceOrb`, `ShulkerBullet`, `ItemEntity`, `PrimedTnt`, `FallingBlockEntity`, `FishingHook`, `FireworkRocketEntity`, `AbstractHurtingProjectile`, `AbstractArrow`, `PistonMovingBlockEntity`.
- 26.2: `OldMinecartBehavior`, `NewMinecartBehavior`, `AbstractMinecart`, `AbstractBoat`, `ThrowableProjectile`, `ShulkerBullet`, `LivingEntity`, `PrimedTnt`, `ItemEntity`, `FallingBlockEntity`, `ExperienceOrb`, `AbstractHurtingProjectile`, `FishingHook`, `FireworkRocketEntity`, `EndCrystal`, `EnderDragon`, `AbstractArrow`. The piston call changed to the explicit overload in this release.

The minecart override is important to the full call graph but is not a LocalPlayer callback: 1.21.3 `AbstractMinecart.applyEffectsFromBlocks()` selects same-position explicit processing or `super` (`AbstractMinecart.java:395-399`); 26.2 selects `super` or same-position explicit processing and then clears its own movement queue (`AbstractMinecart.java:412-417`).

## Slice 3 — piston follow-on removal when movement is absent/present

1.21.3 `moveEntityByPiston` sets its no-clip direction, calls `entity.move`, invokes the no-argument block-effects path, then clears the no-clip direction (`PistonMovingBlockEntity.java:182-186`). There is no `removeLatestMovementRecording` call in the piston source, and no-argument effects consume the accumulated movement list as described above.

26.2 performs: capture `previousPos`, call `entity.move`, explicitly apply block effects to `(previousPos, entity.position())`, then call `removeLatestMovementRecording()` (`PistonMovingBlockEntity.java:192-199`). The explicit pair is wrapped as a singleton and passed to the same common consumer as the no-argument path; the subsequent removal separately targets the live queue tail.

Consequences directly established by the branch code:

- If the piston move produced a movement record, the tail removal removes that newest queue record after the explicit effects callback.
- If the piston move produced no record and the queue is empty, removal is a no-op; the explicit singleton callback still ran.
- If the piston move produced no record but an earlier queue record remains, the method has no identity/position check and removes that earlier tail. Whether such a prior record remains at this callback is a runtime/tick-order question and is not claimed here.
- A no-record move can follow the `Entity.move` path that skips recording (`Entity.java:739-754`); the source supplies a path where the callback's removal has no newly appended record to identify.

## Per-slice disposition

- Producer and remover inventory: **complete** for the exact source trees. All direct log-field access, append helper, record API callers, queue-tail removal callers and clear callers are enumerated above; the frozen roster carries relied-on hashes.
- Client/server separation and correction invalidation: **complete** for direct source observation. Server packet listener operations are on `ServerPlayer`; LocalPlayer's local effects gate is explicit; the client correction handler bypasses queue-clear methods in both releases.
- No-arg/explicit effects closure: **complete** for direct source references in both trees. The all-callsite file inventory and non-player caller sources are frozen in the roster.
- Piston follow-on: **complete** for conditional queue-removal semantics. Runtime prevalence/order of a leftover earlier queue record remains unmeasured and is outside this source-only assignment.
- Missing source edge: **none** within the three assigned source inventories.
## Precision supplement r1 — 26.2 `Entity.move` record gate

Before the 26.2 piston callback's explicit scan and tail removal (`PistonMovingBlockEntity.java:192-199`), `Entity.move` has three recording gates (`Entity.java:711-754`):

1. If `noPhysics` is true, it takes the direct `setPos` branch and updates collision flags; it does not call `addMovementThisTick`.
2. Otherwise, for `MoverType.PISTON`, it replaces `delta` with `limitPistonMovement(delta)`. If that post-limiter `delta.equals(Vec3.ZERO)`, `move` returns before the collision-move and append logic.
3. Otherwise, it collides the delta, computes `movementLength = movement.lengthSqr()`, and appends only when `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7` (`Entity.java:738-754`). The append stores `(pos, newPosition, delta)`.

`PistonMovingBlockEntity.moveEntityByPiston` proceeds after the `move` call even when `move` returned from the post-limiter zero branch: it still invokes `applyEffectsFromBlocks(previousPos, entity.position())`, then `removeLatestMovementRecording()`. The noPhysics branch also returns normally from `move`, so both follow-on calls occur without adding a record. The explicit overload reaches the common consumer, whose block scan is gated by `isAffectedByBlocks() = !isRemoved() && !noPhysics` (`Entity.java:977-979`); therefore the noPhysics case invokes the overload but skips that scan, while a nonremoved post-limiter-zero case enters it. Tail removal remains conditional only on the queue being nonempty; no record identity or endpoint is checked. An earlier tail can therefore be removed when no new piston record was appended and a prior entry remains. This is source behavior, not a statement about runtime prevalence. Existing queue contents and record counts are not inferred here.