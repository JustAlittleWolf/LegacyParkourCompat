# 1.21.10–1.21.11 local dry velocity threshold comparison (revision 1)

This is an additive repair to `comparison.md`, whose SHA-256 is `eec89743cb57c69122cb6616da2fcbe2f51fb9bd692a001a0ba342d3ba7e614a`. That original file is preserved unchanged. The exact source identities and threshold verdict recorded there remain in force; this revision adds the specialized `Entity.move` reset/writer path and bounds its finite-input equivalence.

## Added exact source evidence

Canonical Mojmap source files, with hashes already matched against their source manifests and the original comparison's ready/provenance identity:

- 1.21.10 `net/minecraft/world/entity/Entity.java`, SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`.
- 1.21.11 `net/minecraft/world/entity/Entity.java`, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a`.
- 1.21.10 `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`.
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.

The full `Entity.move(MoverType, Vec3)` member is identical in both decompilations: 1.21.10 `Entity.java` lines 670–766 and 1.21.11 lines 685–781; exact member-body SHA-256 on both sides is `cd092568f6af74a10a4c9dfe1d9e53b70582a50ad57625ff9132c9ae9cdaf697`.

The normal dry `LivingEntity.travelInAir` path calls `handleRelativeFrictionAndCalculateMovement`; that unchanged helper calls `move(MoverType.SELF, getDeltaMovement())`, then reads movement back. Helper lines are 1.21.10 `LivingEntity.java` 2476–2486 and 1.21.11 lines 2534–2544; both exact member-body hashes are `625ccacd48603ff77e42a7aaa54de632d9c743336d0eda89be8bacf59ecab392`.

## Reset and write order inside `Entity.move`

For the bounded ordinary dry `MoverType.SELF` call, hold the requested movement, finite velocity values, collision-clipped result and all provider/callback results equal between versions. This compares the writer logic only; it does not compare clipping, collision geometry, block-state lookup, or provider behavior.

1. If `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`, a non-piston move first multiplies the requested movement by that multiplier, then clears `stuckSpeedMultiplier`, then resets current velocity with `setDeltaMovement(Vec3.ZERO)`. The same strict threshold and operation order occur at 1.21.10 lines 687–694 and 1.21.11 lines 702–709.
2. The method obtains `$$3 = collide($$1)`, then computes horizontal-axis difference flags in this order: `$$10 = !Mth.equal($$1.x, $$3.x)`; `$$11 = !Mth.equal($$1.z, $$3.z)`. The `Mth.equal` calls and x-before-z comparison order are unchanged (1.21.10 lines 697–720; 1.21.11 lines 712–735). Treat `$$3` as the same fixed clip result for this bounded comparison.
3. After the collision flags and fall/collision bookkeeping, if `horizontalCollision` is true, the method snapshots current velocity and writes components in x/y/z argument order: x becomes `0.0` iff `$$10`; y remains `$$14.y`; z becomes `0.0` iff `$$11` (1.21.10 lines 742–745; 1.21.11 lines 757–760). This component reset has identical gates, comparisons and order.
4. The method then runs the conditional landing-block callback when movement y differs from the clipped y; its result is treated as a fixed same input here. After movement emissions, it obtains `$$17 = getBlockSpeedFactor()` and scales x and z while preserving y: `getDeltaMovement().multiply($$17, 1.0, $$17)` (1.21.10 lines 747–762; 1.21.11 lines 762–777). This post-collision writer order is identical.
5. Back in the unchanged helper, it reads the resulting movement. `travelInAir` then applies its existing vertical and friction update as described in the original comparison. For the stated finite dry witness, the resulting finite vector is accepted by both setters.

## Separate finite-value gate

The setter overload `setDeltaMovement(Vec3)` differs independently: 1.21.10 `Entity.java` lines 3619–3621 unconditionally assigns the vector; 1.21.11 lines 3643–3647 assigns only inside `if ($$0.isFinite())`. Exact member-body hashes are respectively `64a7ca0ab043bb5fae6fd46dbc430a9f15103c37c3aef38aa9f6d385a3dbeca2` and `87ac544caafe5b8f6155365ca6d6556a6ac3f27301ce70065dc3c4b64af074b2`. The three-double overload is unchanged (1.21.10 lines 3627–3629; 1.21.11 lines 3655–3657; body SHA-256 `659c50e81d4b412a9ba87f2693aa43d60712d0a991d6217549d4bb0498125c20`). The 1.21.11 `addDeltaMovement(Vec3)` also adds its own `isFinite()` gate (lines 3649–3653); 1.21.10 lines 3623–3625 has no such gate. Its body hashes are `7a9bf6bfdc8290cffb1ffe643b86761e6d46ad87bc25cd10f368291be70107c9` and `10d0dd2e30ca022dcbd4fd8b9d4ec06177cc6eadb78017c5e51774c4d4b06d46` respectively.

Therefore the identical `Entity.move` body establishes same-state equivalence only when all vectors written on this ordinary path are finite and the compared collision/provider inputs match. It does not establish equivalence for NaN or infinite velocity/input/intermediate values: in 1.21.11 those writes can be ignored, leaving the previous velocity in place, while 1.21.10 stores the supplied vector. No unbounded equivalence claim is made.

## Revised finding disposition and residuals

No positive finding applies to the bounded ordinary dry finite local-player reset/writer path: the player threshold remains pre-travel and unchanged; `Entity.move`'s stuck and horizontal component resets, comparisons, and finite-path write order are identical. The new setter finite guard is a separate behavior delta for non-finite values, outside the finite bounded witness; it is not generalized into ordinary movement parity. Collision provider/geometry behavior, landing callback results, non-finite inputs, and other branches remain unadjudicated. Intermediate patch introduction is unknown.