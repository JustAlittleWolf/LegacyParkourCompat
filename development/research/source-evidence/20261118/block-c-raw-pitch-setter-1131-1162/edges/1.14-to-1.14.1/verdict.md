# Independent source audit: direct pitch setter, 1.14 → 1.14.1

## Verdict

**Accepted, bounded no-difference result.** The reviewed source record's conclusion holds for the assigned direct local turn setter, its current/previous pitch writes and the active Elytra travel read. The inspected body is identical across 1.14 and 1.14.1. This does not close full-pair movement coverage or establish runtime parity.

Reviewed input: `c-raw-pitch-setter-114-1141-source/report.md`, SHA-256 `45c2643103a0261e224c5accbe4aa55d203852ece72302bcd28d919395a4730a`.

## Exact source identities and byte bindings

Both release roots use canonical Yarn decompilations and their source manifest entries match local bytes. Provenance manifest SHA-256 values: 1.14 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`; 1.14.1 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`. Input pins in those manifests identify 1.14 Yarn `1.14+build.21` and client `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`; 1.14.1 Yarn `1.14.1+build.10` and client `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.

Manifest-bound movement sources:

| Release | Source | SHA-256 |
|---|---|---|
| 1.14 | `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` |
| 1.14.1 | `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| 1.14 | `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` |
| 1.14.1 | `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| 1.14 | `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` |
| 1.14.1 | `net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |

A Feather source set exists for **1.14 only**, at `decompiled_minecraft/1.14/ornithe-feather`. Its ready sidecar reports `status=ready`, `versionId=1.14`, `mapping=ornithe-feather`, and 3,152 source files. The sidecar's source-manifest, provenance and artifact-manifest hashes all match the corresponding local sidecars; the Feather `Entity.java` and `LivingEntity.java` bytes also match their listed source hashes. Its Entity turn alias (`updateLocalPlayerCamera`) agrees on double deltas, float narrowing, current-pitch clamp, separate previous-pitch accumulation/clamp and ordering. **No 1.14.1 Feather alias is present** under the canonical decompiled tree or its ready outputs. Therefore Yarn is the paired family used for the verdict; no newer Feather alias was presumed.

## Audited claims

`Entity.changeLookDirection(double,double)` is at Yarn `Entity.java:313–325` in both releases. The extracted source segment SHA-256 is identical on both sides: `b60fc591a02f1ba0c247846959450374fd91d48accf306e7c82218d26dec0914`.

The setter computes `double f = e * 0.15` for pitch and `double g = d * 0.15` for yaw; the first pitch write is `(float)(this.pitch + f)`, so the float field is promoted for double addition and narrowed back to float. It writes yaw next, clamps current pitch, then adds the same `f` independently to the old `prevPitch` and clamps that value. It does not copy current pitch into previous pitch. The direct call from `client/Mouse.java:214` is present on both sides and targets `this.client.player.changeLookDirection(j, k * n)`.

Supplemental numeric check: there is no finite-value guard before the first pitch write in this method. `MathHelper.clamp(float,float,float)` is the same comparison-based helper in both releases (`MathHelper.java:89–95`): infinities clamp to the endpoints, while NaN passes both comparisons and remains NaN. This behavior is not a 1.14→1.14.1 difference; it makes explicit the limit of the reviewed report's finite-input walkthrough.

For the next movement step, `LivingEntity.tickMovement` calls `this.travel(...)` at line 2204 in each release. For a dry player with no vehicle following the ordinary `PlayerEntity.travel` path, `super.travel(arg)` reaches `LivingEntity.travel`; its dry gates at lines 1772–1774 admit the active `isFallFlying()` branch. That branch reads current pitch at line 1781 as `float l = this.pitch * (float) (Math.PI / 180.0)`. The angle contributes to cosine/sine velocity adjustments before the movement call at line 1803. `prevPitch` is not the movement input. These travel and player-delegation slices are unchanged across the pair.

## Scope and disposition

The author report's no-difference claim is independently confirmed for the assigned setter and next dry, nonpassenger active-Elytra path. The report's finite-input qualification is accurate; this audit additionally records the unguarded first write and NaN clamp behavior above. No finding is warranted for this pair and operation. Other player-tick and movement slices remain outside this bounded audit.

## Phase-two provenance and acceptance check

The specified author input was rehashed immediately before review: `c-raw-pitch-setter-114-1141-source/report.md` is exactly SHA-256 `45c2643103a0261e224c5accbe4aa55d203852ece72302bcd28d919395a4730a`. The 1.14.1 Yarn root **does contain hidden (dot-prefixed) exact-source provenance**: `decompiled_minecraft/1.14.1/yarn/.decompile-provenance.json` exists and has SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`; its `net/minecraft/entity/Entity.java` entry matches the local file bytes. It pins the 1.14.1 raw client and Yarn/intermediary artifacts listed above, so newer-side Yarn evidence is not provenance-free. Windows does not mark this file with the Hidden attribute; “hidden” here means its dot-prefixed name.

No Feather output/alias exists for 1.14.1 in the available canonical or ready roots. This availability gap does **not** promote, weaken, or change the verdict: the exact pair was compared in Yarn on both sides, and the older 1.14 Feather file was only a corroborating alias. The no-difference conclusion remains bounded to the stated operation and does not become full-pair acceptance.