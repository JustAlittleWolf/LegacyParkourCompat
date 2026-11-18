# Blind source verdict: 1.14.1 → 1.14.2 pitch-to-vertical swim correction

## Independent conclusion frozen before author-report access

**No source-level difference in the assigned operation is present between Minecraft 1.14.1 Yarn and 1.14.2 Yarn.** The `PlayerEntity.travel` swim-correction method, its inherited `Entity.getRotationVector` implementation, the sine/cosine lookup implementation, and the following inherited `LivingEntity.travel` water movement block have identical aligned source text. This rejects a proposed 1.14.1→1.14.2 change for this bounded operation. It is not a full-pair coverage or freeze verdict.

## Scope and exact evidence

Read-only source audit of the player-level pitch-derived swim vertical correction and the immediately following inherited water movement path. No gameplay, build, tests, product, Git, wiki, backlog, network, source generation, fluid or collision dependency investigation was performed.

Canonical source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.1\yarn\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.2\yarn\`

The hidden `.decompile-provenance.json` SHA-256 identities are respectively `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` and `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`. Each manifest lists 3,155 source files; all 3,155 listed files in each tree were present and their exact file SHA-256 values matched the manifest. The manifest digests bind the full per-file source inventory and the complete provenance records.

Exact cited source files (whole-file SHA-256):

| Mapping tree | File | Bytes | SHA-256 |
|---|---|---:|---|
| 1.14.1 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | 71,165 | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| 1.14.2 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | 71,150 | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| 1.14.1 Yarn | `net/minecraft/entity/Entity.java` | 85,030 | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| 1.14.2 Yarn | `net/minecraft/entity/Entity.java` | 85,529 | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| 1.14.1 Yarn | `net/minecraft/entity/LivingEntity.java` | 98,917 | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| 1.14.2 Yarn | `net/minecraft/entity/LivingEntity.java` | 99,072 | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| Both | `net/minecraft/util/math/MathHelper.java` | 13,113 | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |

Pinned local provenance inputs (all local files hash-matched their recorded SHA-256; mapping JAR sidecars match too):

| Release | Exact Yarn mapping artifact | Intermediary artifact | Raw client artifact |
|---|---|---|---|
| 1.14.1 | `1.14.1+build.10`, `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `1.14.1-v2`, `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | client object `55ba86ddcbc3579397f41910463ffd4056e1e523`, `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` |
| 1.14.2 | `1.14.2+build.7`, `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `1.14.2-v2`, `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | client object `ca6c5a139045967229975c0c0b7f93e78b4314c2`, `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` |

The provenance also pins Yarn metadata responses to SHA-256 `6d026a20d6165444f67df52ddcc29f575f0267ab4b545c3bb427e5803c434873` (1.14.1) and `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81` (1.14.2), and exact release metadata JSON to `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` and `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8`, respectively. Both source manifests record the same decompile pipeline and Eclipse Adoptium Java 25.0.3+9-LTS on Windows 11 amd64.

Aligned, UTF-8/LF-normalized source slices (terminal LF included) compare byte-for-byte equal:

| Operation | 1.14.1 source lines | 1.14.2 source lines | Slice SHA-256 (both) |
|---|---:|---:|---|
| `PlayerEntity.travel(Vec3d)` | `PlayerEntity.java:1362–1390` | `PlayerEntity.java:1362–1390` | `6fd634cb049cbb5d2f140a8ca15de47df5fd83f5a33e76b959c4d2d5139ed837` |
| `Entity.getRotationVector(float,float)` | `Entity.java:1180–1188` | `Entity.java:1182–1190` | `0341b814a663a43abf7ad1db988a9f4347ba4692240e105cd44139710cf91326` |
| `Entity.getRotationVector()` | `Entity.java:1635–1637` | `Entity.java:1637–1639` | `0b7277f9879d5633e66ed96ea76314a01eb4978c71c72e324238f30bba2272c5` |
| `LivingEntity.travel` water block through post-move response | `LivingEntity.java:1859–1905` | `LivingEntity.java:1865–1911` | `e493da7726a8eee87f8e0ccb69a2b6e66dba7e713879528775413156bdfde39b` |
| `MathHelper` sine-table initialization and `sin`/`cos` | `MathHelper.java:11–30` | `MathHelper.java:11–30` | `97cbbdf8a7c82d0ad13c7d18256c911a9b549560bf21bd7ae73382bbecf24c26` |

## Frozen technical trace

1. At `PlayerEntity.travel` entry, the method snapshots `x`, `y`, and `z`. Before either `super.travel(arg)` call, the correction gate is `isSwimming() && !hasVehicle()`. `PlayerEntity.isSwimming()` itself returns `!abilities.flying && !isSpectator() && super.isSwimming()`.
2. The correction reads `double g = getRotationVector().y`; selects `double h = g < -0.2 ? 0.085 : 0.06`; then applies `setVelocity(lv.add(0.0, (g - lv.y) * h, 0.0))` only when the short-circuit OR is true: `g <= 0.0`, `jumping`, or the fluid state at `BlockPos(x, y + 1.0 - 0.1, z)` is nonempty. Thus the block/fluid query occurs only when `g > 0.0` and not jumping. The threshold at `-0.2` is strict for selecting `0.085`; the application test at zero is inclusive. The correction uses doubles after reading the look-vector `y`; it runs before the inherited travel voluntary/logical-side gate.
3. `Entity.getRotationVector()` passes the unmodified `pitch` and `yaw` fields to `getRotationVector(float f, float g)`. That helper calculates float `h = f * (float)(PI / 180.0)`, float `i = -g * (float)(PI / 180.0)`, then `j = cos(i)`, `k = sin(i)`, `l = cos(h)`, `m = sin(h)` in that order, and returns `new Vec3d(k * l, -m, j * l)`. Therefore the correction's `g` is the double-valued vector Y constructed by negating the float sine-table result for pitch converted to radians.
4. `MathHelper` builds 65,536 `float` entries as `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin(float f)` indexes `SINE_TABLE[(int)(f * 10430.378F) & 65535]`; `cos(float f)` uses `SINE_TABLE[(int)(f * 10430.378F + 16384.0F) & 65535]`. Each index expression multiplies/adds in float, casts to `int` (Java truncation toward zero), then masks with `65535`. The relevant initialization and methods are identical in both releases.
5. When `super.travel` reaches the ordinary player water leg, inherited `LivingEntity.travel` first requires `canMoveVoluntarily() || isLogicalSideForUpdatingMovement()`. Its water `else` leg is selected when `isInsideWater()` is true and this player is not flying. It computes the water movement multiplier/input acceleration (including sprinting/base multiplier, depth-strider cap and airborne half, and the status-effect override), calls `updateVelocity(g, arg)`, then the actual `move(MovementType.field_6308, getVelocity())`. The common block next applies the climb collision response, velocity multiplier, the gravity/sprinting vertical adjustment (including its small-value threshold), and the horizontal-collision upward step response. This entire aligned block is unchanged, with only the surrounding line offset differing.
6. The player correction does not itself move position. Its resulting velocity enters `LivingEntity.travel` and, for the water branch, the quoted inherited `move` call. The same sequence and conditions occur on both sides of this pair.

## Bounded independent verdict

No difference; no source finding is warranted for this operation in the 1.14.1→1.14.2 Yarn comparison. This verdict says nothing about unexamined travel branches, other player operations, exact introduction in an earlier patch, or full-pair coverage.
## Assigned report comparison (opened after blind freeze)

The designated source report was opened only after the independent verdict was frozen. Its SHA-256 matched the assigned identity `d2758509a4973dbf78c2ab3b632d94f5df128fd933fa2837723b9a3092679624`. Its conclusion agrees with the frozen independent result: no 1.14.1→1.14.2 source difference in the assigned swim vertical-velocity writer and inherited water move path. The pre-report frozen verdict body had SHA-256 `0ae0ee4c6b455cc58bc4b1fad60e633aa3e1bad7f9791aca3d8d4d56be559ccb`. The author report also records wider travel-call and virtual-move resolution details; those were not used to form or amend the independent pre-report conclusion.