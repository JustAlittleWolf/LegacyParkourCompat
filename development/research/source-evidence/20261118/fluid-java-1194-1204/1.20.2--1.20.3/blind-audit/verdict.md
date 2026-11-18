# Independent narrow review verdict

## Bound identities

Reviewed comparison: `source/comparison.md`, SHA-256 `2ca246968f841b315a20216bf6e6de8ae545ba3a99e08b4fec7a6320872ab372`.

Frozen source audit: `blind-audit/frozen.md`, SHA-256 `9ad14a7fcbf2c8144b4596e3e0fcd7da1f7850fda8d086729f91975fc15dc6ab`. The frozen artifact was preserved unchanged.

## Verdict

The bounded four-operation movement conclusion is supported for a non-riding, non-flying local player with equal effective fluid tags and queried world/player inputs. No canonical movement finding is warranted for those exact operations. The draft needs one caller-path clarification and one wrapper exclusion before treating it as a fully precise handoff.

1. **Water/lava contact and stored height — supported.** The reviewed `Entity` methods and `baseTick` call order match in both releases. Water scan writes `wasTouchingWater`; lava scan stores its height, while `isInLava()` suppresses the result on `firstTick`. The floor/ceil scan bounds, `0.001` deflation, surface threshold and max-height accumulation match.
2. **Current push and player normalization — supported for the assigned state.** The fluid-flow accumulation, averaging, `instanceof Player` normalization skip, scale and minimum-push gates match. One resolver detail should be stated precisely: `Player.isPushedByFluid()` returns `!abilities.flying` in both releases (1.20.2 `Player.java:1877-1880`; 1.20.3 `Player.java:1803-1806`), so an ordinary non-flying player enables flow push on both sides. The draft's reference to the base `Entity.isPushedByFluid()` returning true is not the player's dispatch implementation, although it does not change the verdict for this state.
3. **Eye-fluid ordering — supported.** `Entity.baseTick()` calls fluid contact/current, `updateFluidOnEyes()`, then `updateSwimming()` in both releases. `updateFluidOnEyes()` queries prior `fluidOnEyes` for `wasEyeInWater`, clears the set, and rebuilds it from the current eye sample in the same order.
4. **Ordinary water/lava travel gates — supported, with wrapper scope stated.** The water and lava branches in `LivingEntity.travel(Vec3)` are byte-identical, including local-control, fluid-affect, standable-fluid, drag, falling adjustment, threshold, gravity and collision gates. The draft's path wording should say `LivingEntity.aiStep` selects its ordinary `this.travel(...)` call instead of `travelRidden`; that virtual call reaches `Player.travel`, whose ordinary non-flying branch then calls `super.travel(...)` into `LivingEntity.travel`. `LocalPlayer.aiStep()` calls `super.aiStep()` on both releases.

## Required scope note and residuals

The `Player.travel(Vec3)` wrapper body changed between these releases. In 1.20.2 it captures the starting coordinates and calls `checkMovementStatistics(dx,dy,dz)` after `super.travel`; 1.20.3 removes both. The remaining call was movement-statistics/food-exhaustion bookkeeping, outside this assignment's immediate movement operation. This does not change the ordinary water/lava branch's position or velocity writes, but the draft should name it as an excluded wrapper difference and should include `Player.java` in its consumed-source identity list if it retains the detailed local-player reachability claim. Exact source digests: 1.20.2 `Player.java` SHA-256 `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c`, `Player.travel` body `84f9a7396f451c13e59d82997a05d641faedf6ffa7b20a948951fb6f0c16021a`; 1.20.3 `Player.java` SHA-256 `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`, `Player.travel` body `45edd1c97c4167bf73456682c662aaebacdd5674eb553105422c2992e2edd8a3`.

The 1.20.3 embedded provenance has pinned metadata/client/mapping inputs and matching source-file hashes, but does not include explicit top-level `versionId` or `namespace` fields or a sibling ready marker. The version/mapping directory and exact pinned `1.20.3.json` and `client.txt` inputs close source identity for this audit; retain that metadata-label limitation in the handoff.

Residual equal-input requirements include fluid-tag membership and fluid states/heights/flows, bounding box, loaded-chunk status, velocity, dimension ultra-warm state, and the predicates enumerated in the draft. The verdict does not establish broader tick coverage, data-driven resource equivalence, an intermediate patch boundary, or runtime parity.