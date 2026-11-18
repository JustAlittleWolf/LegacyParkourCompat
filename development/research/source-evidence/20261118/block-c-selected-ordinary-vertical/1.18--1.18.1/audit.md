# Independent source audit: ordinary vertical air integration, 1.18 → 1.18.1

## Bounded verdict

**No source difference found** in the requested ordinary local-player dry-air vertical integration and fall-distance path. For all four Java classes used to trace the player tick, each 1.18 source is byte-identical to its 1.18.1 counterpart; each actual SHA-256 matches both releases' source-manifest entry. This supports only the bounded operation and gates below; it is not a full pair coverage audit or an implementation/runtime verdict.

## Exact source identity

Mapping family: Mojmap. Release-level metadata is one directory above each `mojmap/` source tree.

- 1.18: `mojmap.ready.json` SHA-256 `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`; `mojmap.provenance.json` `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`; `mojmap.sources.sha256` `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`.
- 1.18.1: `mojmap.ready.json` SHA-256 `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81`; `mojmap.provenance.json` `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`; `mojmap.sources.sha256` `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`.
- Ready metadata reports exact versions 1.18 and 1.18.1, Mojmap, 4,203 source files each, and the above manifest/provenance pins. Each listed metadata digest and manifest digest matched the assigned pin.

Each entry below gives the source-manifest SHA-256 and independently recomputed actual file SHA-256; values match across both releases:

| Mojmap source | 1.18 and 1.18.1 SHA-256 |
| --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |

## Traced behavior and gates

The local-player tick runs only when `level.hasChunkAt(getBlockX(), getBlockZ())` succeeds, then delegates to `super.tick()` (`LocalPlayer.java:188–200`). Its `aiStep` delegates to `Player.aiStep` (`LocalPlayer.java:647–813`, delegation at 808); that delegates to `LivingEntity.aiStep` (`Player.java:489–507`, delegation at 506). The living tick calls `aiStep` after `Entity.tick`/`baseTick` (`LivingEntity.java:2220–2259`; `Entity.java:397–459`). In `LivingEntity.aiStep`, after the input-state preparation and `updateFallFlying`, it calls the virtual `travel` (`LivingEntity.java:2543–2549`). These tick/call slices are byte-identical between the two releases.

For a non-swimming, non-flying, non-passenger player, `Player.travel` takes its ordinary `super.travel` path (`Player.java:1409–1438`; flight-special branch is gated by `abilities.flying && !isPassenger()`, lines 1424–1435). Under the assignment's exclusions (dry; no Slow Falling, Levitation, climbing, gliding or flight), `LivingEntity.travel` enters the final ordinary branch after its water, lava and fall-flying branches (`LivingEntity.java:2005–2137`). Its travel body runs only when `isEffectiveAi() || isControlledByLocalInstance()` (line 2006); a local player uses the controlled-local-instance route.

Within that branch, `handleRelativeFrictionAndCalculateMovement` first applies relative input and climbable handling, then invokes `move(MoverType.SELF, currentDeltaMovement)` before returning the resulting movement vector (`LivingEntity.java:2157–2167`). Thus, in the ordinary unconstrained case, this tick's vertical displacement is driven by the pre-update Y velocity. After that move, `travel` copies the resulting Y into a `double`; with Levitation absent and the client missing-chunk special case inapplicable, gravity subtracts the `double` literal `0.08` unless `isNoGravity()` (`LivingEntity.java:2117–2130`). It then writes velocity: X/Z use horizontal friction, while Y is multiplied by `0.98F` unless `shouldDiscardFriction()` is true; that gate instead leaves Y unscaled (`LivingEntity.java:2132–2136`; `shouldDiscardFriction` returns `discardFriction` at 637–639). The `0.98F` operand is a float widened for double arithmetic. The assignment did not specify the discard-friction flag, so this conditional is reported as a gate rather than assuming its value.

`Entity.move` writes position from the movement result (no-physics path at 542–544; ordinary path after collision resolution at 560–564). In the ordinary path, vertical collision is derived from requested-versus-resolved Y, `onGround` additionally requires requested Y `< 0.0`, and `checkFallDamage` receives resolved Y (`Entity.java:568–580`). The fall accumulator is a `float`: on landing, if it is `> 0.0F`, the current block's fall callback receives it, then `resetFallDistance()` always runs; otherwise only a negative resolved Y adds distance via `fallDistance = (float)(fallDistance - resolvedY)` (`Entity.java:969–982`). Reset assigns `0.0F` (`Entity.java:2101–2103`). Collision/support production and block callback effects are boundary dependencies excluded by assignment; their callers and the Y/fall-state handoff are unchanged. These source slices and gates have the same line locations and literal text in 1.18.1.

## Coverage and exclusions

Examined paired slices: local tick and aiStep delegation; player tick/aiStep/travel; living tick/aiStep/travel; friction-movement helper; entity movement position/vertical collision handoff; fall-distance accumulation/reset. Confirmed all these owning Java files are byte-identical across the exact pinned sources. No difference or changed numeric/order/gate behavior found in this bounded slice.

Excluded as assigned: jump causation; status/equipment/angle/horizontal behavior; fluids and effects; climbables; gliding/flying; block shape, collision and support providers; callback implementation; vehicles/non-player entities; visuals/network. Boundary gates retained in the trace include local chunk availability, AI/local control, Player flight/passenger/swimming branch selection, LivingEntity medium/glide/effect/gravity/client-chunk branches, discard-friction, and resolved vertical collision/on-ground state. No claim is made about unexamined writers to those states or full tick-graph closure.

## Independent comparison addendum (phase 2)

Auditor: `/root/c_vertical_117_1194_scheduler/audit_118_1181` (independent blind source auditor). The frozen prefix above was rehashed before author access and matched SHA-256 `21687e93e2abfdbdfca7dcfa3821ab2943621f9154bbe401739029c54a47c7fd`. The author source was verified before reading and matched SHA-256 `952093c4be51a1a9f0d27a632b7d32204bb3fef71ae26b29bda6c6bae37c7cd3`.

**Verdict: agree; no disagreement.** The author's exact source identities and Java byte claims match the independently checked pins and computed file hashes. The compared cases agree on route selection, local/client qualification, travel-before-gravity movement ordering, gravity subtraction, conditional float-literal drag, resolved-Y fall-distance addition, and ground reset. The author's worked sample `(-0.1 - 0.08) * 0.98F` and float accumulation `(float)(fallDistance - (-0.1))` are consistent with the source expressions.

The author explicitly traces `LivingEntity.checkFallDamage` (`LivingEntity.java:286–307`): water-state update at 287–289, Soul Speed and landing-particle branches gated by `!level.isClientSide` at 291–304, followed by `super.checkFallDamage` at 306. This wrapper is in the already hash-verified byte-identical `LivingEntity.java`; its client/server gates align with the bounded local-player case. Collision and block callback effects remain excluded. No change to the bounded no-difference verdict is warranted.

Final audit disposition: author source verified and independently compared; no disagreement or unresolved blocker within this assignment. This does not assert full-pair coverage.