# Independent source caller-inventory audit

Verdict: **ACCEPT with two bounded audit supplements.** The producer, local-player eligibility, copy/clear/removal, external-server-writer, and explicit Vec3-consumer claims are supported by exact source. The author’s roster needs path correction for ten source-identity rows, and the Piston inventory needs the complete `Entity.move` record gate noted below. No runtime prevalence or parity claim is accepted.

## Frozen evidence identity

- Pair: Minecraft 1.21.3 Mojmap → 26.2 unobfuscated.
- Author inventory: SHA-256 `7f46b7ca997c677fc48acb9d6e2a3e64f02a9e785cedab3e5eaa2b8611222b72`.
- Author frozen roster: SHA-256 `486f4ee2bb856388a524461b366e4249eb3bec0d5abc50be7211102c4eea855b`.
- Independent pre-comparison source roster: SHA-256 `e6524f3be107e6b74dee9276a5ef8cb978eeca181543745bf9cf400a23b12d13`.
- Canonical verified audit roster (author roster reconciled with independent roster): [audit-roster.tsv](audit-roster.tsv), SHA-256 `96871f858e90a2060b2bb7826f3686dd6e602720042f3018fd2dd1ae3960932b`.

The 1.21.3 ready marker says `ready`, exact `1.21.3/mojmap`, 5,655 sources / 26,544,115 bytes, source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. The 26.2 marker says `ready`, exact `26.2/unobfuscated`, 7,055 sources / 33,813,717 bytes, source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Both ready-marker source-manifest hashes match their sidecar bytes. I recomputed every entry in both source manifests: 5,655/5,655 and 7,055/7,055 source hashes match. Ready, provenance, artifact-manifest and diagnostic sidecar hashes match the frozen expected values after correcting their paths as recorded in `audit-roster.tsv`.

The author frozen roster’s ten `source-identity-manifest` path fields are truncated (examples: `p.ready.json`, `acts.sha256`, `cated.ready.json`, `ts.sha256`), so those ten paths do not resolve literally. Their SHA-256 values do match the uniquely inferred actual sidecars (`mojmap.ready.json`, `artifacts.sha256`, `unobfuscated.ready.json`, etc.). The canonical audit roster records the actual names; the author file itself was not changed.

## Producer and client/server separation

- 1.21.3 `Entity.movementThisTick` is private (`Entity.java:250`). Complete source search found all direct field access in `Entity.java`. Appends are the explicit `applyEffectsFromBlocks(Vec3,Vec3)` pair (`:740-749`) and `recordMovementThroughBlocks` (`:1034-1035`). The no-argument fallback passes `oldPosition()` and current position (`:736-737`).
- The only `recordMovementThroughBlocks` call sites are `ServerGamePacketListenerImpl.java:452,930`; this listener’s `player` is a `ServerPlayer`. These are server packet-processing writes, not ordinary client `LocalPlayer` writes. Client `LocalPlayer` reaches the inherited `LivingEntity.aiStep` block-effects call; its client gate is `!isClientSide || isControlledByLocalInstance` (`LivingEntity.java:2774-2775`). `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:478-480`), which makes its ordinary client path eligible through `Entity.isControlledByLocalInstance()` (`Entity.java:3149-3159`).
- 26.2 has no `recordMovementThroughBlocks`. `Entity.move` appends via private `addMovementThisTick` (`Entity.java:751-754,920-929`). This is the ordinary local-player movement-record path; server packet processing uses `removeLatestMovementRecording` at `ServerGamePacketListenerImpl.java:500,1198` on its `ServerPlayer` field, rather than an external append API.
- Local player block-effects execution remains gated in `LivingEntity.aiStep` by `!isClientSide || isLocalInstanceAuthoritative` (`26.2 LivingEntity.java:3143-3145`). On a client, `Entity.isLocalInstanceAuthoritative` delegates to `isLocalClientAuthoritative` (`Entity.java:3589-3595`); `Player.isLocalClientAuthoritative` delegates to virtual `isLocalPlayer` (`Player.java:1281-1286`), and `LocalPlayer.isLocalPlayer` is true (`LocalPlayer.java:378-380`). This is distinct from server packet-listener writers/removers.

## Queue mutation and consumption

- 1.21.3 explicit effects appends to and passes the same live list to `checkInsideBlocks`, then clears `movementThisTick` and `blocksInside` within `isAffectedByBlocks()` (`Entity.java:740-752`). `teleportSetPosition` separately clears `movementThisTick` (`:2721-2732`). Client `handleMovePlayer` goes through `setValuesFromPositionPacket`, not `teleportSetPosition`; its direct position/old-position writes do not clear the movement list (`ClientPacketListener.java:706-734,717-744`).
- 26.2 no-arg effects clears `finalMovementsThisTick`, copies the live deque, then clears the deque (`Entity.java:903-906`). It falls back to `(oldPosition(), position())` only when the copied list is empty; otherwise it appends a final segment if squared endpoint distance exceeds `9.9999994E-11F` (`:907-913`). `applyEffectsFromBlocksForLastMovements` reuses the retained final list (`:916-917`).
- The explicit Vec3 overload wraps exactly one `Movement` in `List.of` and invokes the private list consumer (`Entity.java:945-949`). It does not drain or rewrite the live deque/final list. Client position-correction source in both releases was checked directly: `handleMovePlayer` applies `setValuesFromPositionPacket` and does not call `teleportSetPosition`, queue clear, or tail removal (`1.21.3 ClientPacketListener.java:706-734`; `26.2 ClientPacketListener.java:795-806,808+`).
- The full no-arg caller set in the author inventory matches exact source search. The `LivingEntity` call is the ordinary player route; other listed projectile, item, vehicle, orb, and dragon calls are non-player routes. Piston’s generic entity enumeration can select a player when its bounding box passes the Piston criteria (`PistonMovingBlockEntity.java:209-210` in 26.2; analogous movement enumeration in 1.21.3).

## Piston exact gate supplement

The author correctly records 1.21.3’s no-arg Piston callback (`PistonMovingBlockEntity.java:182-186`) and 26.2’s explicit `(previousPos, currentPosition)` callback followed by tail removal (`:192-199`). The 26.2 callback is unconditional after `entity.move` returns, including if `move` returned early. The exact movement-record gate is:

1. `noPhysics` takes the direct `setPos` branch and bypasses `addMovementThisTick` (`Entity.java:711-715`).
2. For `MoverType.PISTON`, `limitPistonMovement` may yield `Vec3.ZERO`; `move` returns before appending (`:719-723`).
3. Otherwise append occurs only inside the collision-move condition `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7` (`:738-754`).
4. `PistonMovingBlockEntity` then always calls explicit effects and `removeLatestMovementRecording` after `move` (`:194-198`). Removal is tail-only if nonempty (`Entity.java:931-934`), with no identity or endpoint check. Thus zero-limited/noPhysics/threshold-suppressed Piston moves can remove a prior tail if one remains. This possibility is source-established; its runtime frequency/order is unmeasured.

1.21.3 has no equivalent `removeLatestMovementRecording` in the Piston path. It calls no-arg effects after the move, which uses `oldPosition()` fallback and consumes the accumulated live list under the existing block-effects gate.

## Scope limits

This audit covers source references, ownership and branch conditions in the two exact source trees. It does not test runtime ordering, profile parity, or the prevalence of Piston callbacks with an older queued record. No implementation, product source, wiki, build, test, game launch, Git history or network evidence was used.
