# Block-break acknowledgment movement audit — Mojmap 1.15.2 → 1.16.5

## Result

The route-audit correction is supported. `ClientPacketListener.handleBlockBreakAck` invokes `MultiPlayerGameMode.handleBlockBreakAck` on the client thread in both endpoints. When its version-specific conditions pass, the game-mode method directly restores the current `Minecraft.player` position from state captured for the matching block action. `Minecraft.player` is declared `LocalPlayer`; neither endpoint overrides `absMoveTo`, so dispatch reaches inherited `Entity.absMoveTo` and its `setPos` position/bounding-box update. This is a movement-reachable, changed direct player response. Packet occurrence and server production/frequency are outside this check.

## Exact source identity

Read-only source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap` (Mojmap). SHA-256 values recomputed from the four inspected files:

| Version | File | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `d69f96ab32445bdc5a2cfa1f7ca718d9213d107b7690a15799b660cd3548a4b4` |
| 1.16.5 | `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `80d1b28891b33de967cbc26e13acec097fb94091bbc226f92ad5ba2750015b66` |
| 1.15.2 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| 1.16.5 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |

## Route and receiver

The supplied final route audit SHA-256 was recomputed and matches `f1fb1a01ee3858a92f4fffce0ce44d26b3b01b19a1d2336535e004b300bfdf87`. In its reconciled route ledger, row 397 is the exact-case `modified` path `net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, with old/new file hashes above. Its `CorrectedRoute` is erroneously `no movement dependency`, while its `CoveredSourceMembers` includes `handleBlockBreakAck` at old line 424 and new line 434, and its `Route` repeats “not on ordered graph and no direct route identified from path/body scan.” The same path appears as unresolved in `remaining-open-rows.tsv` (row 7) and in `no-movement-open-member-scan.tsv` (row 5). Those row identities match the audit's identified false negative; retain the member as movement-reachable/open pending its wider reconciliation, rather than relying on the negative path/body-scan label.

Both packet receivers call `PacketUtils.ensureRunningOnSameThread(...)`, then pass `this.level`, packet position/state/action/result directly to `this.minecraft.gameMode.handleBlockBreakAck(...)`: 1.15.2 `ClientPacketListener.java:2354–2365`; 1.16.5 `:2380–2391`. This establishes a client-thread receiver route without inferring that any server action produces or frequently produces the acknowledgment.

## Saved state and correction behavior

Both implementations save state in `unAckedActions`, keyed by `(BlockPos, Action)` when `sendBlockAction` runs, and remove that entry for the matching acknowledgment before evaluating correction (`MultiPlayerGameMode.java:418–425` in 1.15.2; `:428–435` in 1.16.5). The old value is `PosAndRot(position, xRot, yRot)` (`world/phys/PosAndRot.java:5–22`); the new value is only `Vec3 position`. The map also drops its oldest entries while size is at least 50, after acknowledgment handling. Thus a restore needs a still-present entry for that same key; absent entries cannot restore the player.

In 1.15.2, after removal, the block state is set to the acknowledged state when

`posAndRot == null || !allGood || (action != START_DESTROY_BLOCK && currentState != acknowledgedState)`.

Within that branch, any non-null saved value also triggers `minecraft.player.absMoveTo(savedX, savedY, savedZ, savedYaw, savedPitch)`. Therefore, with a saved entry, restore occurs when `!allGood` or when the action is not `START_DESTROY_BLOCK` and the pre-correction current block state differs from the acknowledged state. There is no player-level equality check or block-collision predicate on this restore path. Source lines: `MultiPlayerGameMode.java:424–434`.

In 1.16.5, let `different = currentState != acknowledgedState`. State correction and any restore are nested under

`(saved == null || !allGood || (action != START_DESTROY_BLOCK && different)) && different`.

Restore additionally requires a saved value, `clientLevel == player.level`, and `player.isColliding(blockPos, acknowledgedState)`. With a saved entry the outer condition simplifies to `different && (!allGood || action != START_DESTROY_BLOCK)`. Then `isColliding` obtains the acknowledged block state's collision shape using `CollisionContext.of(player)`, moves it to the block position, and intersects it with the player's bounding box (`Entity.java:214–218`). The `setKnownState` call precedes this collision test (`MultiPlayerGameMode.java:434–444`). Only if these gates pass does the method call position-only `player.absMoveTo(savedX, savedY, savedZ)`. The restoration therefore now depends on a changed acknowledged block state, same client level, and overlap with that acknowledged state's collision shape, and no longer restores saved yaw/pitch. The narrow 1.16.5 gate does not negate the direct movement route.

## Position dispatch

At both endpoints `Minecraft.player` has static type `LocalPlayer` (`Minecraft.java:269` in 1.15.2; `:307` in 1.16.5). `LocalPlayer` declares no `absMoveTo` override. In 1.15.2 the five-argument inherited `Entity.absMoveTo` clamps X/Z, writes previous coordinates, calls `setPos`, assigns yaw/pitch and their previous values (`Entity.java:1094–1105`). `Entity.setPos` writes the position and rebuilds the bounding box (`Entity.java:315–320`). In 1.16.5 the three-argument call reaches inherited `Entity.absMoveTo`, which clamps X/Z, updates previous coordinates, and calls `setPos` (`Entity.java:1114–1121`); `Entity.setPos` writes the position and rebuilds the bounding box (`Entity.java:331–334`). The five-argument overload delegates to it and then writes rotation (`:1106–1112`). This is an immediate coordinate restore, not merely a queued interpolation.

The old method restores saved rotation as well as position; the new method restores position only. The condition and collision changes mean an eligible ack is not guaranteed to produce a restore in either version, and the newer source reduces the restoring cases further. No trajectory beyond this direct source-level state write is predicted here.

## Status boundary

This report verifies one route and its conditional response from exact endpoint sources. It does not create a canonical accepted finding, prove an intermediate patch boundary, freeze pair coverage, assess implementation, or claim runtime parity. No server packet producer/frequency, tests, build, or game launch were examined or required.
