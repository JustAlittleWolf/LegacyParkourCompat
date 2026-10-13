# Independent source/scope proof review — MC1204-1206

## Decision

**REQUEST CHANGES** to the feature-scope proof for one verified stale-status statement. The reviewed source/scope conclusions are otherwise accepted per claim below. This review does not implement a mechanic, revise the author proof, accept endpoint findings, close pair discovery, or claim runtime parity.

The proof under review is commit `9acf4bb097ce025b5dbc9c042ef012d5cad54f63`, path `workflows/fix-implementation/reconciliations/MC1204-1206-feature-scope-2026-10-08.md`, Git blob `f20ee7926dae5c36bbfe82238d7ffcdbcd20b596`, raw-byte SHA-256 `b3ead218913011fe6889b6f444fa9ee75d397041a68b20d148dae0e9abb699d4` (14,221 bytes). The underlying accepted source snapshots are MC02 `b168d90c05e813d6dff2ccd97090dc1602d9d418`, MC03 `467e5a80114239be1135b9ebc4bd2ee1d2b8bdc2`, corrected MC04 `13cf725ab0ac943f78c2ec7e5851a473022edfb4`, MC05 `8797e40a04bbcb2de46fa4a689b181afb8a1a4ad`, and MC06 `d6479a08528c346d9462c952cf8c13da8aaf4915`. I read the exact ready Mojmap method bodies and the relevant Player/entity producers and existing mechanic resolver described below; I did not repeat the full source-pair discovery inventory.

The exact ready source read was `build/movement-campaign-2026-10-07/ready/{1.20.4,1.20.5,1.20.6}/mojmap`. The 1.20.4 and 1.20.6 `LivingEntity.java` hashes are respectively `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` and `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`; the independent boundary review confirms 1.20.5 `LivingEntity.java` has the latter hash too. In those files I checked A `getJumpPower()` / `jumpFromGround()` at lines 2000–2016 and B at lines 2057–2081; B gravity at the `getGravity()` consumer and travel around lines 2105–2110; A/B scale at lines 556/578; and A/B step at lines 3423/3490. I also checked `Blocks.java` and `BlockBehaviour.java` for the jump-factor defaults and Honey Block override, `Attributes.java` for B's `.42`, `.08`, `1.0`, and `.6` defaults, the `Player` delegation, and the exact-version `setNoGravity` writer searches. The boundary acceptance report independently revalidated all cited source files against the ready manifests.

## Per-claim verdicts

| Claim | Verdict | Independent result |
|---|---|---|
| MC02: old vanilla 1.20.4 player jumps cannot reach `power <= 1.0E-5F` | **ACCEPT** | `LivingEntity.getJumpPower()` is `0.42F * blockJumpFactor + jumpBoostPower`. Default block factor is `1.0F`; the only vanilla non-default block property found in the exact 1.20.4 block registry is Honey Block at `0.5F`, so the minimum block term is `0.21F`. Canonical vanilla Jump Boost adds a nonnegative term. That is far above the 1.20.5+ inclusive cutoff. The source-level cutoff remains real, but no old vanilla player input reaching it is established. Do not infer its eligibility from the accepted source boundary. |
| MC02: sprint arithmetic is eligible for 1.20.4 and uncovered | **ACCEPT** | A's sprint branch multiplies float trig results by `0.2F`; B's unsuffixed `0.2` promotes the product to double. Ordinary sprint jumps with positive power reach it. `SprintJumpImpulse` is registered at `V1_19_4`, while `ChangeResolver` includes only registrations whose version is at least the selected profile. The selectable 1.20.4 profile is `V1_20_2`, so that registration is not selected; no later registration exists. This is an uncovered eligible old-profile delta. For a follow-up, the relevant selectable profile is `V1_20_2` (which groups 1.20.2–1.20.4); the source transition itself is at 1.20.5. Keep those two version facts distinct. |
| MC03: generic jump strength defaults equivalent; only a modern non-default player input changes it | **ACCEPT** | A uses base `0.42F`; B multiplies default `generic.jump_strength` `0.42F` by `1.0F`, preserving the base term. 1.20.4's `horse.jump_strength` is horse-specific and is not a player input. A changed generic value has no 1.20.4 vanilla player attribute producer. |
| MC04: default gravity and Slow Falling are equivalent; ordinary no-gravity paths agree | **ACCEPT** | Default gravity is `0.08`; descending Slow Falling yields `0.01` in both paths (`Math.min(0.08, 0.01)` in B). On the ordinary paths, the old `!isNoGravity()` gates correspond to B's effective gravity of zero. A non-default generic gravity input is a newer attribute input, not an old player producer. |
| MC04: fall-flying with `noGravity` has no proven vanilla Player producer | **ACCEPT — OPEN** | The source consumer differs: A's fall-flying vertical acceleration retains local `0.08` when `noGravity` is true, while B's `getGravity()` yields zero. The exact-source writer search found `setNoGravity` in entity/NBT and non-player behavior paths, not a vanilla player gameplay writer. Keep this separate and open; do not implement it as the generic gravity attribute delta or mark it ineligible without a producer/reachability proof. |
| MC05: default step height and controlling-player passenger minimum match | **ACCEPT** | A defaults to `0.6F` and preserves the `Math.max(value, 1.0F)` controlling-player passenger rule. B's `generic.step_height` default is `0.6` and uses the same minimum. Only a newer non-default attribute input changes the supplied step value; the accepted finding does not close all step-candidate collision outcomes. |
| MC06: default player scale matches | **ACCEPT** | A's player scale is `1.0F`; B's `generic.scale` default is `1.0` and feeds pose dimensions. Only a newer non-default attribute value changes that input. No old vanilla player scale producer was found. |

## Requested correction

The proof's MC02 boundary paragraph says the memo `e277edd19eb8f8ce63681ab7985a5d92df8a259f` “is not accepted” and that the 1.20.5 cutoff/sprint boundary “remains unconfirmed.” That status is stale and materially contradicts the independent review at `1e5de3b32966e8766cdcf9f7ad56a9278fb79bb0`, which **ACCEPTS** the memo's bounded source-boundary claims and rebinds the exact memo blob `cda4a40cadde768c36b5523cc5c11a3a56adc78f` / SHA-256 `a97e20b5abaa131f9ec090d1d186eae63592d839e8cd9c3c254b80acdea8de82`. The accepted boundary establishes the cutoff and double-literal sprint arithmetic first appear at 1.20.5 in this inspected interval; it explicitly leaves low-power input eligibility open. Update the proof to reflect that acceptance without turning it into an eligibility or runtime claim.

The sprint follow-up recommendation should also keep the source transition (1.20.5) distinct from the current selectable profile (`V1_20_2` for 1.20.2–1.20.4). The resolver evidence supports the uncovered 1.20.4 profile claim; no code change was made here.

The proof's latest-main note names `7e7b7bb7068ec7b68f3a8f09cbb42bc498a04bf` as the included main state. This review worktree was fast-forwarded to local `main` at `3a60fe735560e478bf0aa0d05f5e306c74800f6a` before its report commit. The intervening source changes inspected (`LocalPlayerMixin`, sprint-input-start behavior and sleep-safety behavior) did not modify `LivingEntityMixin`, `ChangeResolver`, `ParkourVersion`, or the reviewed jump/gravity/step/scale hooks, so they do not alter the scope verdicts above.

## Review angles

1. **Requirement/scope:** The proof distinguishes source findings from old-map eligibility and keeps the pair partial. One stale boundary-status assertion requires correction.
2. **Correctness and edge cases:** The default and reachable input comparisons above match the exact source. The fall-flying `noGravity` case remains explicitly open; no trajectory claim was checked or inferred.
3. **Completeness:** The proof accounts for all five requested finding groups and separates the two MC02 slices. The MC04 reachability dependency remains open as stated.
4. **Conventions/duplicates:** No duplicated movement loop or code change was introduced. The recommendation must use the existing `V1_20_2` selectable grouping when describing a future code registration.
5. **Permissions/visibility:** No permission, configuration, user-visible message, or runtime visibility changes are part of this documentation proof.
6. **Security:** No executable code or input boundary changed; no security issue found in the reviewed artifact.

## Not run / limits

No tests, build, Gradle task, decompilation, client, server, TAS, or runtime validation was run. The review does not prove full pair coverage, exact historical map input availability beyond the bounded producer checks above, or runtime parity.

**Next step:** correct the stale acceptance status in the proof, then request a narrowly scoped documentation rereview; no implementation is needed to resolve this review finding.
