# 1.21.10 → 1.21.11 player tick pose and travel inventory

## Evidence identity and input boundary

Exact pair: Minecraft 1.21.10 → 1.21.11, Mojang mappings (`mojmap`). Canonical sources were consumed read-only from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Each local ready marker identifies the requested exact version and `mojmap`; the actual source-manifest SHA-256 matches its ready marker and the supplied pretravel inventory:

- 1.21.10: `mojmap.sources.sha256` SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- 1.21.11: `mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- Both local provenance records identify batch `1.21.8,1.21.10,1.21.11`, mapping option `mojmap`, Vineflower 1.12.0, mapping-io 0.9.1 and Java 25. Their ready markers have matching `versionId` and mapping. No source generation was performed.

Actual selected source hashes, each found in its version's source manifest:

| Source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |

The allowed prior evidence was checked read-only before use: `late-player-pretravel-inventory/comparison.md` SHA-256 `aa1c01bc7ae84a7a3e8115842b6de87caf4657e1aec4181d67c5811c8d2a5c27`; `late-player-pretravel-audit/verdict.md` SHA-256 `cda78c10f8ef73122c21c7db8df70b1e5968f8a57d1db18169eacd93d42df93a` (actual full digest: `cda78c10f8ef73122c21c7db8df70b1e5968f8a57d1db18169eacd93d42df93a`).

## Ordered bounded coverage

### A. LivingEntity jump admission, pending jump and velocity cutoffs

- `LivingEntity.aiStep`: no-jump-delay decrement; interpolation / unsimulated-velocity branch; equipment tick; horizontal and vertical near-zero cutoff; input application and immobility clearing; jump admission and `noJumpDelay`; then fall-flying update, fall-distance reset gate, and travel dispatch. The inspected operations and order are unchanged: 1.21.10 `LivingEntity.java:2751–2849`; 1.21.11 `:2877–2975`.
- Player cutoff remains the combined `horizontalDistanceSqr() < 9.0E-6` test that zeroes both horizontal components; the vertical `Math.abs(y) < 0.003` test remains separate. Non-player axis cutoffs remain outside the player branch. No threshold/type/order difference found.
- Local jump admission via `jumping`, ground/fluid gates, and `noJumpDelay == 0` is unchanged in the inspected path. The branch that calls `jumpFromGround()` and sets the delay to 10 remains in the same order. Fluid sensing/height math itself is excluded.
- `getJumpPower` and its `<= 1.0E-5F` early-return gate in `jumpFromGround` are unchanged (`LivingEntity.java:2222–2246` / `:2256–2280`). The bounded pretravel inventory already compared `Player.aiStep`'s pending `jumpTriggerTime` decrement and local pending-press behavior: unchanged and in the same order; bound source inventory and audit remain the hashes stated above.
- One side-effect difference exists in the shared `LivingEntity.jumpFromGround()` body: velocity writes are the same (`y = max(jumpPower, oldY)`, with the same sprint-angle horizontal addition), but 1.21.10 sets `hasImpulse = true` (`LivingEntity.java:2235–2246`) and 1.21.11 sets `needsSync = true` (`:2269–2280`). This changes synchronization bookkeeping, not the local jump admission or computed movement vector; no player trajectory difference was established in this bounded slice. It is recorded as a source delta note, not a movement finding.

### B. Player desired pose, dimensions and previous-value writers outside fit geometry

- `Player.tick` continues to call `updatePlayerPose()` after `super.tick()` and the later player tick work: 1.21.10 `Player.java:239–294`; 1.21.11 `:238–294`. The desired-pose priority (sleeping, swimming, fall-flying, spin attack, crouching/standing), pose-fit fallback order, and final `setPose` write are unchanged: `Player.java:352–384` in both releases. Fit/collision calculations are excluded; only the writer and order were inventoried.
- `Entity.setPose` still writes `DATA_POSE`; `onSyncedDataUpdated(DATA_POSE)` still calls `refreshDimensions`; refresh still captures prior dimensions, computes dimensions and eye height from the new pose, reapplies position, then retains the server-only growth/fudge gates (which explicitly exclude players). No relevant difference found: 1.21.10 `Entity.java:412–417, 3193–3222`; 1.21.11 `:413–418, 3207–3241`.
- The old-position capture helper remains unchanged: `setOldPos()` delegates using `position`; the private writer assigns each `xo/xOld`, `yo/yOld`, and `zo/zOld` pair from the same coordinate. No change found: 1.21.10 `Entity.java:1659–1681`; 1.21.11 `:1677–1699`.
- A new movement-derived sample is present in 1.21.11 `Entity`: `reapplyPosition()` clears `lastKnownPosition` (`Entity.java:458–461`); `baseTick()` calls `computeSpeed()` before its other work (`:484–487`); `computeSpeed()` initializes the previous-position sample if absent, records `position - lastKnownPosition`, then captures current position (`:540–546`). 1.21.10 has no such fields or sampler. The local-player superclass tick reaches this sampler, so it is reachable, but it does not write position or velocity. In examined callers, `getKnownSpeed` feeds the newly added kinetic-weapon attack calculation; `hasMovedHorizontallyRecently` is used by boat-following AI. Those are modern-only combat/damage and non-player/vehicle paths, respectively, so no old-profile player trajectory finding is established here. This is a bounded reachable state-addition note; the wider item/damage path was not audited.

### C. Local travel, speed thresholds and post-travel fall/air ordering

- `LivingEntity.aiStep` keeps the same player horizontal cutoff, vertical cutoff, `applyInput`, jump stage, pre-travel `updateFallFlying`, bounding-box/input capture, slow-fall/levitation fall-distance reset, and travel/travelRidden dispatch order in both releases (ranges in section A). No difference found in those gates or their order.
- The shared post-travel order remains block-effect application, client entity-animation calculation, then profiler close and subsequent server freezing / push work. This is unchanged at 1.21.10 `LivingEntity.java:2851–2860` onward and 1.21.11 `:2977–3008` onward. The full freezing/push work is not a player movement finding.
- Fall-flying's `checkFallDistanceAccumulation()` remains before its server-only glide checks in `updateFallFlying`; this routine is entered before travel at `LivingEntity.aiStep`. `Player.aiStep` still resets fall distance for flying, non-passenger players before `super.aiStep` travel. No order difference found in the inspected fall/air state gates.
- Internal block-friction/solid collision resolution in `Entity.move`, fluid sensing and fluid-specific travel math, and glide trigonometry were not compared as findings; they are outside this bounded assignment.

## Independent reachable delta notes

1. `jumpFromGround` changed only its impulse/synchronization marker write (`hasImpulse` → `needsSync`); jump-power and sprint vector math are unchanged. No altered local position/velocity result is shown by this source slice.
2. 1.21.11 added a per-base-tick position-delta sample (`lastKnownSpeed`) reachable from a player tick. The sample changes derived observer state but is consumed by modern-only combat/damage and boat-following behavior in the checked caller graph, not by the local player movement vector or pose writer in this assignment. Keep this as a scope note; do not claim an old-profile trajectory change.

No standalone movement finding draft was produced: neither delta above establishes an in-scope historical player position/velocity/pose consequence, and the remaining checked operations were equivalent.

## Open dependency and terminal

This is a bounded source inventory, not a full pair freeze. Collision response/shape providers, fluid sensing/contact/travel, glide trig, full travel-world dependencies, external direct movement responses, and intermediate releases remain unclosed or excluded. The exact introduction point of any endpoint difference remains unknown without intermediate source inspection. Prior pretravel accepted item-use findings remain separate and were not reopened.

**COMPLETED** — exact source comparison and bounded inventory written to this scratch report; source hashes verified; no product, wiki, Git history, backlog, tests, runtime, or decompilation generation used. No movement finding draft was justified by the inspected in-scope behavior. No code changes made.