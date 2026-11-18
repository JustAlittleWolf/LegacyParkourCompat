# 1.21.9 to 1.21.10: local flight-state and fall-flight gates

## Coverage and verdict

Compared exact Mojmap decompiles for 1.21.9 and 1.21.10. The examined player-owned flight-state operations are text-identical across the pair: local ability-flight enable/toggle/reset and vertical input in `LocalPlayer.aiStep`; the player's ability-flight veto, flight-speed branch, and fall-flight start gate in `Player`; and fall-flight dispatch, shared-flag state, and `updateFallFlying` reset in `LivingEntity`. The underlying source-file SHA-256 values are identical on both sides for all three classes (listed below). No independent flight-state gate difference is established in these slices.

One linked input is not closed: `LocalPlayer.aiStep` clears ability flight after `super.aiStep()` when `onGround()` is true (LocalPlayer.java:834-838). `Entity.onGround()` is unchanged, but `Entity.move` changed its stuck-multiplier handling for `MoverType.PISTON` (Entity.java:687-694 in 1.21.10; lines 687-691 in 1.21.9). When `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`, the 1.21.9 path multiplies the requested displacement by `stuckSpeedMultiplier` for all mover types; 1.21.10 skips that multiplication for piston movement but still clears the multiplier and delta movement. This can alter the movement passed to collision and thus the `onGround` value consumed by the unchanged flight-reset gate. Whether this produces a reachable player flight-state difference depends on the piston movement/ground-state producer; it is outside this flight-state slice and remains an unclosed dependency for the owning movement/collision lane. This is not recorded as an independent flight-state finding.

The caller's `!isInWater()` gate in `Player.tryToStartFallFlying` is unchanged but fluid response was not evaluated. Elytra equipment lifecycle, fall-flight numeric trajectory, fluid/contact response, collision geometry and runtime parity are outside this report. The local ability-flight branches are assessed only as state predicates/writes and their vertical input, not as a complete movement audit.

## Compared source slices

All references are relative to `decompiled_minecraft/<version>/mojmap/` and line numbers are inclusive.

| Slice | 1.21.9 | 1.21.10 | Result |
|---|---|---|---|
| Local ability-flight intent and toggle (`mayfly`, always-flying, double-jump toggle, grounded jump on enabling, jump-trigger reset), including fall-flight request ordering | `net/minecraft/client/player/LocalPlayer.java:697-778` | same | Identical file bytes and same predicates/order/writes. |
| Already-flying vertical input and post-`super.aiStep` grounded flight reset | `net/minecraft/client/player/LocalPlayer.java:791-838` | same | Identical file bytes. The `onGround()` producer has the open dependency described above. |
| Ability-flight predicate effects and flying-speed selection | `net/minecraft/world/entity/player/Player.java:1321-1323, 1867-1873, 1955-1957` | same | Identical file bytes; `canGlide()` rejects ability-flying, and speed/ladder gates are unchanged. |
| Fall-flight start veto and direct shared-flag write | `net/minecraft/world/entity/player/Player.java:1376-1388` | same | Identical file bytes; the fluid predicate is only noted, not analyzed. |
| Dry travel dispatch / ordinary air branch | `net/minecraft/world/entity/LivingEntity.java:2275-2283, 2306-2385` | same | Identical file bytes. Fluid branch behavior is outside this lane. |
| Fall-flight state read and update/reset | `net/minecraft/world/entity/LivingEntity.java:2390-2401, 2901-2929, 3354-3356` | same | Identical file bytes and flag/predicate gates. |
| Ground-state producer dependency | `net/minecraft/world/entity/Entity.java:666-668, 670-728` | `net/minecraft/world/entity/Entity.java:666-668, 670-730` | `onGround()` is unchanged; piston handling of `stuckSpeedMultiplier` changes as described above. Producer behavior is not resolved here. |

## Provenance and hashes

- **1.21.9 Mojmap:** canonical `decompiled_minecraft/1.21.9/mojmap/` contains `.decompile-provenance.json`. Its exact client metadata key is `https://piston-meta.mojang.com/v1/packages/91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json`; client JAR key is `https://piston-data.mojang.com/v1/objects/ce92fd8d1b2460c41ceda07ae7b3fe863a80d045/client.jar`. Provenance-file SHA-256: `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`.
- **1.21.10 Mojmap:** canonical `decompiled_minecraft/1.21.10/mojmap/` is accompanied by `decompiled_minecraft/1.21.10/mojmap.ready.json`, `.provenance.json`, and `.sources.sha256`. The marker says `status=ready`, `versionId=1.21.10`, `mapping=mojmap`, 6,386 source files, and its `sourceManifestSha256` matches the adjacent source-manifest SHA-256 below. The marker's client SHA-256 is `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. The 1.21.10 campaign provenance records an exact 1.21.8/1.21.10/1.21.11 Mojmap decompilation batch; no hidden `.decompile-provenance.json` is present in the canonical 1.21.10 tree.
- **1.21.10 source manifest:** `decompiled_minecraft/1.21.10/mojmap.sources.sha256` SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching the ready marker.
- **1.21.10 campaign provenance:** `decompiled_minecraft/1.21.10/mojmap.provenance.json` SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28fea5f8afba42cab798b47d7ad77c`.
- **1.21.10 ready marker:** `decompiled_minecraft/1.21.10/mojmap.ready.json` SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`.

The exact source-file SHA-256 values verified against the 1.21.10 source manifest and the 1.21.9 hidden provenance `sources` map are:

| Source | SHA-256 (both versions) |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/Entity.java` | 1.21.9 `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`; 1.21.10 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |

