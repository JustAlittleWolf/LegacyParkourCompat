# F-ELYTRA-START implementation handoff

## Review request

Independently review the corrected 1.14.4 fall-flying start request path on branch `fix/elytra-start-full-request`. Confirm the hook timing, the `justToggledCreativeFlight` local-variable target, and that the old eligibility gate is reproduced while the native 26.2 path remains unchanged when this versioned behavior is not selected. This handoff does not claim runtime parity.

## Finding and source evidence

- Accepted immutable finding: snapshot commit `4a0c35f2008d785867c00360a7b72726e3506435`, `workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-START.md`, SHA-256 `b6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59`.
- Blind acceptance: `960ce5644ceacd8e27bd3cb9145bd4eaf221379c`.
- 1.14.4 `LocalPlayer.aiStep()V`: exact published source SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`. Its accepted gate checks fresh jump input, airborne state, `deltaMovement.y < 0.0`, not already fall-flying, and not ability-flying; it then checks an enabled Elytra in the chest slot and sends `START_FALL_FLYING`. It does not reject passenger, climbable, or water state and does not set the local fall-flying flag before sending.
- 1.14.4 `ElytraItem.isFlyEnabled(ItemStack)`: source body is `damage < maxDamage - 1`.
- 1.15.2 `LocalPlayer.aiStep()V`: SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; helper `Player.tryToStartFallFlying()`: SHA-256 `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`.
- Current target is 26.2. The canonical published ready marker is `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/26.2/unobfuscated.ready.json`. Current `LocalPlayer.java` source SHA-256 is `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; native jar SHA-256 is `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`.
- In current `LocalPlayer.aiStep()V`, the jump request branch checks jump pressed, no creative-flight toggle, fresh press, and `!onClimbable()` before calling `Player.tryToStartFallFlying()`. The helper checks not already fall-flying, `canGlide()`, and not in water. Current `Player.canGlide()` rejects passenger, on-ground, levitation, and non-glider equipment/durability. The request is sent by the LocalPlayer caller only when the helper returns true.

## Implementation

- `FallFlyingStartBehavior` now owns both the start-request eligibility decision and the climbable predicate used at the caller gate.
- `LocalPlayerMixin` modifies the caller's `Entity.onClimbable()Z` result and the `justToggledCreativeFlight` local only inside `LocalPlayer.aiStep()V`, and wraps the helper invocation. This removes current-only caller vetoes; the old `abilities.flying` predicate still decides whether a toggle-on request is eligible. All three dispatch to the same versioned mechanic. With no matching mechanic, they use native behavior.
- `ElytraJumpStart` checks, in order, `!onGround`, exact `!(y < 0.0)` rejection (preserving NaN), `!isFallFlying`, and `!abilities.flying`, then chest-slot Elytra identity and the old `damage < maxDamage - 1` check. It returns request eligibility without calling the modern helper or changing shared flag 7. The existing LocalPlayer caller sends the single command after a true result, preserving the old request timing and avoiding duplicate packet emission.
- The caller bridges open the climbable and flight-toggle routes for this historical mechanic. The replacement eligibility path likewise does not impose modern passenger, water, levitation, or alternate-glider exclusions. Passenger, ladder, water, and a fresh jump press that toggles creative flight off therefore follow the accepted 1.14.4 gate.
- Registration remains through the existing `V1_14` `MovementChanges` provider; no duplicate version provider or new mixin was added.

## Scope and limits

- The source comparison proves the 1.14.4-to-1.15.2 endpoint difference. The exact first changed release remains unknown within `(1.14.4, 1.15.2]`; this patch does not claim a finer boundary.
- Server acceptance and subsequent glide trajectory are outside this finding. Runtime validation was not performed.
- No build, tests, client, TAS, server, or Docker run was performed, per task instruction. `git diff --check main...HEAD` passed after the handoff was committed and main was merged.
- No implementation-derived feedback was sent to source-only owners.
