# 1.9.4 → 1.10: authorized creative flight during an existing glide

## Result and bounds

The inspected player operation is present in both endpoints with the same relevant gates and order. It is **not a 1.9.4→1.10 source difference**: when `abilities.flying` is true and the player is not riding, `PlayerEntity.moveRelative` calls `LivingEntity.moveRelative` while flag 7 is still readable as true; after that call returns it restores the saved vertical velocity scaled by `0.6`, restores `speedInAir`, clears `fallDistance`, then unconditionally clears flag 7. In the inner call, `LivingEntity.moveRelative` tests `isFallFlying()` before its ordinary movement branch, so an already-live flag 7 selects glide math and reaches `move(...)` before the player wrapper clears that flag. The outer player wrapper does not require `world.isClient`; its `abilities.flying && !isRiding()` gate is the gate that matters for this operation.

This is a conditional source path, not a claim that normal play guarantees the overlap. A normal local input edge requesting `START_FALL_FLYING` is suppressed while `abilities.flying` is true. The path therefore needs an already-live, synced glide flag plus the independently authorized/synced flight ability at the time local travel runs. The sources allow those two state predicates to coexist; the passive source review does not establish a particular server event sequence or guarantee that it occurs in ordinary gameplay.

## Exact local source evidence and provenance

The 1.9.4 tree has local provenance: `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json` says exact `versionId=1.9.4`, namespace `ornithe-feather`, mapping option `feather`, and Feather artifacts `feather-gen2-1.9.4+build.2-mergedv2.jar` and `feather-gen2-1.9.4+build.2.tiny`. Its ready record identifies raw client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` and source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The current four relevant source hashes below match entries in that source manifest. Feather artifact pins in `artifacts.sha256`: merged jar `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; Tiny `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.

The local 1.10 tree is only `decompiled_minecraft/1.10/ornithe-feather/`. No 1.10 provenance JSON, ready marker, or source-hash manifest is present beside it. Thus the observed current file bytes can be identified by SHA-256, but exact client/mapping artifact identity and generated-tree completeness for 1.10 cannot be confirmed from this checkout. Treat the source comparison as provisional until its preparation owner supplies/verifies canonical 1.10 Feather provenance. No source was generated or modified.

Relevant current source-file SHA-256 values:

| Release | Source | SHA-256 |
| --- | --- | --- |
| 1.9.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| 1.9.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.4 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` |
| 1.10 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c171cab3e63c43298023078c3683f96c7ea8fed3cd` |
| 1.10 | `net/minecraft/entity/living/player/PlayerEntity.java` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` |
| 1.10 | `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| 1.10 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `14b537a3f11e57d8364e90508f45b996b7e0593b741064167d85256166a287c6` |
| 1.9.4 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` |
| 1.10 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` |

## Operation and reachability trace

- **Local input and starter (kept separate from the movement operation):** 1.9.4 `LocalClientPlayerEntity.java:690–713`; 1.10 `LocalClientPlayerEntity.java:713–737`. On a jump rising edge the creative flight toggle is processed before the Elytra starter. The starter requires airborne, downward `velocityY`, `!isFallFlying()`, `!abilities.flying`, chest Elytra, and `ElytraItem.canFly`. 1.10 adds an auto-jump guard to the flight-toggle branch and caches `falling`; neither changes the wrapper/inner movement order described above. 1.9.4 additionally plays a local Elytra sound after sending the start request.
- **Synced abilities:** client packet handler assigns `abilities.flying` and `abilities.canFly` from `PlayerAbilitiesS2CPacket` (1.9.4 `ClientPlayNetworkHandler.java:1307–1315`; 1.10 `:1314–1322`). This supports treating the creative-flight predicate as server-supplied state. It does not itself clear the glide flag.
- **Actual travel order:** `LivingEntity` travel calls `flyingTick()` and then `moveRelative(...)` (1.9.4 `LivingEntity.java:1706–1712`; 1.10 `:1742–1748`). `flyingTick()` keeps flag 7 true while airborne, not riding, and wearing a flyable Elytra; only the non-client world writes its computed flag back (1.9.4 `:1718–1736`; 1.10 `:1754–1772`).
- **Direct player after-travel clear:** 1.9.4 `PlayerEntity.java:1371–1389`; 1.10 `:1391–1408`. Under `abilities.flying && !isRiding()`, the player wrapper snapshots `velocityY` and `speedInAir`, calls `super.moveRelative`, then applies `velocityY = g * 0.6`, restores `speedInAir`, writes `fallDistance = 0.0F`, and calls `setFlag(7, false)`. These operations and their order are identical across the pair.
- **Inner glide selection and side gates:** 1.9.4 `LivingEntity.java:1302–1355`; 1.10 `:1332–1385`. `isFallFlying()` selects the glide branch before the non-glide movement path. Glide math calls `move` before the player wrapper's clear. Its horizontal-impact damage and landing clear are guarded by `!world.isClient`; the player wrapper's flag-7 clear is not. The movement method's outer gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`; for the ordinary local client this is the local-control path.
- **Server starter validation:** 1.9.4 `ServerPlayNetworkHandler.java:846–854`; 1.10 `:845–853`. The action accepts only when not grounded, descending, not already glide-flagged, not in water, and wearing a flyable chest Elytra; it sets flag 7. No `abilities.flying` exclusion appears in this server action handler. The subsequent `flyingTick()` server-side validation is the independent gate above. These relevant server gates are unchanged between the pair; server-side physics is outside this task's client movement conclusion.

## Explicitly not conflated

`fallDistance` is a separate write in the same post-call player wrapper, not evidence about flag-7 semantics. `LivingEntity` NBT reads/writes of `FallFlying` (including the 1.10 read-side flag set) are not the ordinary live input/sync path assessed here. Elytra starter eligibility and server acceptance are preconditions, not the post-travel player operation. No A/B gameplay or crafted-state experiment was performed or inferred.

## Coverage disposition

Compared the local input/starter path, tick travel order, both relevant `moveRelative` implementations, `flyingTick` validation/write gate, synced ability application, and server action gate at the named pair endpoints. No behavioral delta was found for the authorized-flight/active-glide post-call clear. The 1.10 provenance gap remains open, so this is not a closed accepted pair finding or a patch-introduction boundary.

