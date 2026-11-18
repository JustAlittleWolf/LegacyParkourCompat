# 1.17.1 → 1.18 Elytra climb lower-gate report

## Scope and verdict

**Pair:** Minecraft 1.17.1 Mojmap → 1.18 Mojmap. **Operation:** `LivingEntity.travel` for an already-active, locally controlled, on-foot player with the fall-flying shared flag set, dry at the movement dispatch, wearing a valid chest Elytra, and standing with a non-spectator climbable feet state (the target ladder/vine case).

**Verdict:** The lower-gate/climb interaction is behaviorally unchanged across this pair. In both releases, the fall-flying branch tests the current vertical movement as a `double` with the strict comparison `y > -0.5`. If true, it writes the float literal `1.0F` directly to `fallDistance` before look-angle/travel math, then writes the computed velocity with `setDeltaMovement(...)` immediately before `move`. Equality at `-0.5` does not write 1.0F. The active fall-flying branch bypasses ordinary friction travel's climbable fall-distance cleanup. The ordinary cleanup was refactored in 1.18 from a direct `0.0F` field write to `resetFallDistance()`, whose body assigns the same `0.0F`.

## Paired source evidence

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

- **1.17.1** `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2003–2111; lower gate and immediate write/move specifically lines 2068–2097. `else if (this.isFallFlying())` selects the branch; `Vec3.y` is `double`; `if (vec36.y > -0.5)` is strict; `this.fallDistance = 1.0F` occurs before `getLookAngle`, subsequent glide calculations, `setDeltaMovement(...)`, and `move(MoverType.SELF, ...)`.
- **1.18** same source/member, lines 2003–2112; lower gate and immediate write/move specifically lines 2070–2099. Same comparison, assignment type, order, and branch placement; only Vineflower parameter/local names differ.
- **1.17.1** same file, `onClimbable()`, lines 1427–1443: spectators return false; otherwise it reads `getFeetBlockState()` and accepts a state in `BlockTags.CLIMBABLE` (or the separate usable-trapdoor path). The 1.18 equivalent is lines 1429–1445 and has the same predicate and order.
- **1.17.1** `travel`, ordinary branch/helper, lines 2111–2135 and 2155–2165; `handleOnClimbable(Vec3)` at lines 2183–2198. `handleRelativeFrictionAndCalculateMovement` calls `handleOnClimbable` before `move`; when `onClimbable()` succeeds, that helper assigns `fallDistance = 0.0F`. **1.18** corresponding ordinary path/helper is lines 2113–2137, 2157–2167, and 2185–2199; it calls `resetFallDistance()` instead. `Entity.resetFallDistance()` at 1.18 `net/minecraft/world/entity/Entity.java`, lines 2101–2103, assigns `fallDistance = 0.0F`.
- In both versions the earlier water/lava branches precede `isFallFlying()` in `travel`; the stated dry precondition reaches the same fall-flying branch. In that branch there is no `onClimbable()` call or climb cleanup before the move. The target feet state matters to the skipped ordinary cleanup, not to the lower gate itself.

## Boundaries

**Upstream activation:** `LocalPlayer.aiStep()` has the same Elytra-start request condition in both source files: 1.17.1 lines 747–751 and 1.18 lines 745–749. It requires jump input, no competing flight toggle, not flying, not a passenger, and `!onClimbable()`, then checks the chest slot for an enabled Elytra and `tryToStartFallFlying()` before sending `START_FALL_FLYING`. Thus this source slice does not start gliding at the target climbable position. The report assumes the fall-flying flag is already active; it does not claim the request/remote acknowledgement made that flag active, nor inspect earlier release aliases.

**Final movement tick:** The `1.0F` write occurs at branch entry before look-angle and glide calculations; after those calculations, `setDeltaMovement(...)` is immediately followed by `move(...)`. The assignment is not necessarily the final `fallDistance` value after that movement. `Entity.move` calls `checkFallDamage` synchronously in both versions (1.17.1 `Entity.java` lines 561–566; 1.18 lines 576–580). The base implementation resets on ground contact, otherwise adds the negated resolved vertical displacement when it is downward (1.17.1 lines 998–1010; 1.18 lines 969–981). Therefore the proven fact is a pre-move write and bypass of ordinary climbable cleanup, not a universal end-of-tick value of 1.0F. This report does not trace later ticks or collision-specific outcomes.

## Source identity and hashes

Adjacent exact-version Mojmap sidecars were present and checked; no source generation was run. Their source-manifest SHA-256 matched the corresponding ready marker, and each reviewed source file's SHA-256 matched its entry in that manifest.

| Version/source | SHA-256 |
| --- | --- |
| 1.17.1 `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18 `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.17.1 `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.18 `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.17.1 `mojmap.sources.sha256` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 `mojmap.sources.sha256` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.17.1 `mojmap.provenance.json` | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` |
| 1.18 `mojmap.provenance.json` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` |

Provenance sidecars identify exact requested IDs and Mojmap; the 1.18 ready marker also binds the provenance digest. The 1.17.1 ready marker binds its source manifest and exact client digest but does not declare a provenance-file digest. No claim is made here about independently re-running the decompiler or validating archived build artifacts.

