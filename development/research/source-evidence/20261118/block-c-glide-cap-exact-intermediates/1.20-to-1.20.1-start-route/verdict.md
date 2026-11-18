# Independent source audit — 1.20 → 1.20.1 Elytra start route

## Verdict

**Accept the bounded no-difference claim for the requested start-to-first-move route.** The 1.20 and 1.20.1 Mojmap `LocalPlayer.java`, `Player.java`, and `LivingEntity.java` files are byte-identical, and the 1.20.1 source manifest binds those exact files to a ready 1.20.1 Mojmap output. The ladder/vine feet gate, flag validation, update-before-travel order, fresh-glide counter phase, travel branch, pre-move look factor and damping are present in both releases in the same order.

This closes only the reported Elytra start/first-movement slice. It does not establish final glide-tick behavior, A/B contact or geometry, or complete 1.20–1.20.1 pair coverage and freeze.

## Input identity

Audited input: `c-glide-1201-cap-route/report.md`, SHA-256 `ff2a8e7b8d0a8c46fee6c7447826f0913a29be663727a2b5125effd2c692347c`.

The report's earlier “unverified pair” disposition is superseded for this bounded review by the canonical 1.20.1 sidecars that are present now:

- `decompiled_minecraft/1.20.1/mojmap.ready.json`: SHA-256 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed`; records `status: ready`, `versionId: 1.20.1`, `mapping: mojmap`, and the source/artifact manifest digests below.
- `decompiled_minecraft/1.20.1/mojmap.provenance.json`: SHA-256 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; records the successful exact 1.20.1 Mojmap `decompileMinecraft` run and tool versions.
- `decompiled_minecraft/1.20.1/mojmap.sources.sha256`: SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; its entries match the three source files reviewed.
- `decompiled_minecraft/1.20.1/artifacts.sha256`: SHA-256 `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`; records `client.jar` SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`, `client_mappings.txt` SHA-256 `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d`, and `version.json` SHA-256 `990c243ea1c716b6617839d1db1d2f2f378e8666c1a9a742b136e45d6c3039ea`.
- `decompiled_minecraft/1.20.1/movement-diagnostics.txt`: SHA-256 `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd`; it states the decompile succeeded and the required entity/player sources are present.

The hidden `decompiled_minecraft/1.20.1/.decompile-provenance.json` is absent. That is distinct from the sidecar provenance and ready files above, which are present. The raw input jar is also not currently retained at `.minecraft-decompile-cache/1.20.1/client.jar` (the entire cache version directory is absent); its identity is recorded in `artifacts.sha256` and `mojmap.ready.json`, but I could not re-hash the jar bytes in this checkout. The recorded `ready.outputPath`, provenance `stagingRoot`, `cacheDirectory`, and `workingDirectory` all point to old absolute build/worktree locations that no longer exist. They describe the original run; they are not the current canonical source location and their absence does not negate the retained source manifests.

The reviewed source hashes are identical across both version trees:

| Mojmap source | 1.20 and 1.20.1 SHA-256 |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |

## Claim audit

1. **Start request and climbable feet — no difference.** In `LocalPlayer.aiStep` (both versions, lines 647–801), the request at lines 738–741 requires a newly pressed jump (`$$0` snapshots the prior input state), no flight-toggle action, no abilities flight, no passenger state, and `!onClimbable()` before it checks the chest Elytra and calls `tryToStartFallFlying()`. `LivingEntity.onClimbable` (lines 1478–1494) reads `getFeetBlockState()` and returns true for `BlockTags.CLIMBABLE`; it also has the spectator exception and a separate open-trapdoor/ladder case. For an ordinary player whose feet are on a ladder or vine in that tag, the request is rejected before the Elytra start checks.

2. **Flag validation and update order — no difference.** `Player.tryToStartFallFlying`, `startFallFlying`, and `stopFallFlying` (lines 1576–1595) require airborne, not already gliding, dry, no Levitation, and an enabled chest Elytra; successful start sets shared flag 7 before `LocalPlayer` sends the command packet. `LivingEntity.aiStep` calls `updateFallFlying` at line 2562 before `travel` at line 2572. The updater (lines 2610–2635) validates the flag, ground/passenger/Levitation state and enabled Elytra, and only writes the resolved flag back on the server. With a fresh local start and the stated valid dry preconditions, the client flag remains set for this tick's travel. The `fallFlyTicks + 1` expression is at line 2616; the server-only durability/event work is not a client movement update.

3. **Fresh first move and pre-move math — no difference.** `LivingEntity.tick` invokes `aiStep` at line 2278 and increments `fallFlyTicks` after that call (lines 2348–2351). Thus a newly started glide takes the travel path with the fresh counter still zero; the updater's `+1` value is one. `LivingEntity.travel` (lines 2004–2112) selects its fall-flying branch at line 2068 after water/lava handling and before the ordinary branch. The ordinary helper calls `handleOnClimbable` before its move (lines 2171–2174); the fall-flying branch does not use that helper. Before its first `move`, the glide branch computes `Math.min(1.0, lookLength / 0.4)` in the look factor (line 2077), then applies `multiply(0.99F, 0.98F, 0.99F)` (line 2093) and moves at line 2094. The start gate means the specified ladder/vine-feet input does not enter this newly started glide route.

## References and boundary

All ranges above refer to the Mojmap sources in `decompiled_minecraft/1.20/mojmap/` and `decompiled_minecraft/1.20.1/mojmap/`; the corresponding full-file hashes match across the pair. `LocalPlayer.aiStep` and the inherited `Player.aiStep` establish the dispatch chain; `LivingEntity.aiStep`, `tick`, `updateFallFlying`, `travel`, `onClimbable`, and `handleRelativeFrictionAndCalculateMovement` establish the state and movement order.

The audit ends at the first glide movement and the fresh counter phase. It makes no claim about the later glide ticks, completion/cancellation at later phases, collision outcomes, or complete pair inventory.
