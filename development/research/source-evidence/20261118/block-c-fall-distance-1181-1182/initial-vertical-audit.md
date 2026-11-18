# Blind source audit: 1.18.1 → 1.18.2 ordinary vertical air integration

## Bounded verdict

**No in-scope behavioral difference found** for the assigned path: local player tick to ordinary dry, nonvehicle, non-gliding, non-flying `LivingEntity.travel`, passive vertical gravity/drag, free-air Y displacement, and common fall-distance accumulation/reset. The ordinary path retains the same operation order and numeric expressions. This is a bounded slice verdict, not a full-pair freeze.

A new 1.18.2 fall-distance reset check is present in `Entity.move` (1.18.2 `Entity.java:564–572`): after the collision result has squared length at least `1.0` and `fallDistance != 0`, it raycasts using `ClipContext.Block.FALLDAMAGE_RESETTING` and resets when the ray hits. 1.18.1 has no equivalent check in `Entity.move` (`Entity.java:541–580`). This is recorded as an excluded boundary: its result depends on the collision/block/tag query, expressly outside this assignment. In the assumed empty free-air path the ray misses and it does not change state. Do not generalize this verdict to colliding paths or fall-damage-resetting blocks.

## Exact source identity and byte verification

Both exact Mojmap source publications were read from `decompiled_minecraft/<version>/mojmap/`. The release-level ready marker, provenance file, and source manifest SHA-256 values match the assigned pins:

| Release | `mojmap.ready.json` | `mojmap.provenance.json` | `mojmap.sources.sha256` |
|---|---|---|---|
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |

Every Java file cited below was independently SHA-256 checked against its release's `mojmap.sources.sha256` entry:

| File | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afbf95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

## Trace and paired evidence

- **Local tick entry:** 1.18.1 `LocalPlayer.java:188–200`, `Player.java:220–247`, `LivingEntity.java:2220–2222`; 1.18.2 `LocalPlayer.java:183–195`, `Player.java:222–249`, `LivingEntity.java:2224–2226`. LocalPlayer calls `super.tick`; Player calls `super.tick`; LivingEntity calls `super.tick`. The chain is unchanged for this slice.
- **Travel dispatch:** 1.18.1 `LivingEntity.java:2543–2548`; 1.18.2 `LivingEntity.java:2547–2552` calls `travel`. Player's override delegates to `super.travel` outside its swimming and flying branches: 1.18.1 `Player.java:1409–1439`; 1.18.2 `Player.java:1422–1451`. Under the assignment's dry, non-flying, non-swimming assumptions, the same ordinary branch is selected.
- **Ordinary gravity and drag:** 1.18.1 `LivingEntity.java:2005–2012, 2113–2136`; 1.18.2 `LivingEntity.java:2009–2016, 2117–2140`. The travel gate is `isEffectiveAi() || isControlledByLocalInstance()`. With no slow falling, the gravity local remains double `0.08`. In the ordinary nonfluid/non-gliding branch, movement helper runs first; absent levitation, unloaded-client-chunk fallback, and no-gravity, `$$29 -= $$1` subtracts that gravity. If friction is not discarded, Y is then multiplied by `0.98F`; the same expressions and order occur in both versions. `handleRelativeFrictionAndCalculateMovement` calls `move` before this gravity update in both versions (1.18.1 `LivingEntity.java:2157–2167`; 1.18.2 `2161–2171`). Thus free-air Y position advances by the pre-update Y delta; the next Y delta is formed by subtracting `0.08` and applying `0.98F` drag.
- **Position and fall state:** `Entity.move` applies the resolved displacement with `setPos` (1.18.1 `Entity.java:541–580`; 1.18.2 `543–593`; setters at `Entity.java:370–377` and `372–379`). `fallDistance` is a `float` (`Entity.java:169` / `172`). Shared `checkFallDamage` resets it on a downward ground collision and otherwise accumulates the magnitude of negative actual Y displacement (1.18.1 `Entity.java:969–982`; 1.18.2 `979–991`). The update is spelled `this.fallDistance = (float)(this.fallDistance - $$0)` in 1.18.1 and `this.fallDistance -= (float)$$0` in 1.18.2. Compound assignment converts the double subtraction result back to the float field, so both preserve the same promoted subtraction and final float conversion. The ground-reset gate and ordering are otherwise the same.

## Coverage, exclusions, and limits

Examined and paired: local/player tick entry; Player travel override gate; LivingEntity local-control gate and ordinary travel branch; Y velocity preparation, gravity, drag, and order; Entity Y position write and post-move fall-distance update/reset. Also inspected the fall-distance-resetting raycast addition to establish its boundary.

Excluded by assignment: collision shape and resolution details, support and block/tag providers, fluids and climbables, status/equipment effects, jumps and their causation, gliding/flying/swimming, passenger/vehicle behavior, horizontal/angle behavior, visual/network behavior, and fall-damage calculation/health effects. The ordinary fall-damage threshold is therefore not accepted or compared as an in-scope operation. No runtime path or parity was tested.


## Phase 2 independent verdict addendum

The phase 1 prefix above remains byte-for-byte frozen (SHA-256 `9af125eb3bdae69802bc88bfa6bc340b4730083f1c901df9116167e8b77e54f4`). The author source compared in phase 2 is bound to SHA-256 `491074d03e02cfe42c8d62268c4e3c3351d33984657f27cc518c746c54c478d0`.

**Corrected bounded verdict: one in-scope ordinary fall-state difference is confirmed.** The prior phase 1 statement that the two `checkFallDamage` updates are numerically equivalent was incorrect. Rechecking the authenticated exact `Entity.java` bytes (1.18.1 SHA-256 `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`; 1.18.2 SHA-256 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`) confirms:

- 1.18.1 `Entity.checkFallDamage`, lines 979–981: `this.fallDistance = (float)(this.fallDistance - $$0);`. Since `$$0` is `double`, the float field is promoted to double, the subtraction is performed in double, and the result is rounded to float at the assignment cast.
- 1.18.2 same member, lines 989–991: `this.fallDistance -= (float)$$0;`. The explicit RHS cast rounds the displacement to float before the subtraction; both operands are then float, so the subtraction itself is float precision.

For `fallDistance = 0.25F` and downward resolved displacement `$$0 = -0.08` (double), the first expression stores `0.33000001311302185F`; the second stores `0.32999998331069946F`, one binary32 ULP lower (about `2.98023223877e-8`). This input reaches the ordinary `!onGround && $$0 < 0.0` accumulation gate. The local-player path reaches `Entity.move` through the shared `LivingEntity.handleRelativeFrictionAndCalculateMovement` before the ordinary travel branch updates gravity/drag, and a free-air move passes its unobstructed requested Y displacement to `checkFallDamage`. The numeric witness isolates that operation; it does not claim these exact two starting values arise from a particular initial fall. No collision, block, fluid, status, jump, or flying behavior is needed for the changed arithmetic.

The direct ordinary free-air gravity constant (`0.08`), Y drag (`0.98F`), operation order, ground reset, and `resetFallDistance()` implementation remain matched. The 1.18.2 collision/block-tag raycast reset described in the frozen prefix remains excluded under the assignment boundary. The newly confirmed precision difference is within the assigned common fall-state scope. Downstream fall-distance consumers may observe the one-ULP state difference; damage calculation itself remains excluded. This addendum supersedes only the phase 1 “no in-scope behavioral difference” conclusion and its cast-equivalence claim; the other bounded comparisons stand.