# Independent source freeze — 26.1 → 26.1.1

## Scope and decision frozen before author-report access

Host: Windows PowerShell. This is an independent exact-source audit of ordinary nonpassenger `Player.travel` swim-pitch vertical correction, its direct view-vector arithmetic and lookup-table implementation, gates/order, and inherited movement call. I inspected the canonical `decompiled_minecraft/<version>/unobfuscated/` sources read-only. I did not inspect author-lab material before freezing this source assessment.

**Source finding: no difference in the bounded operation.** The complete `Player.java`, `Entity.java`, `Mth.java`, and `LivingEntity.java` source bytes are identical across 26.1 and 26.1.1 (per-file SHA-256 listed below); the `travel`, `calculateViewVector`, `getLookAngle`, lookup table and trig accessors therefore have identical gates, arithmetic, casts, order and inherited move call. No 26.1.1 source delta is supported for this operation.

## Exact canonical source identity

Both canonical ready markers state `status=ready`, `mapping=unobfuscated`, 6,882 source files, and matching source-byte totals (26.1: 32,992,042; 26.1.1: 32,992,043). The canonical source trees independently match those counts/byte totals. Each ready marker's `sourceManifestSha256` matches the actual adjacent `.sources.sha256` file hash. The four cited files' SHA-256 hashes match their entries in each source manifest.

| Evidence | 26.1 SHA-256 | 26.1.1 SHA-256 |
|---|---|---|
| `unobfuscated.ready.json` | `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5` | `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802` |
| `unobfuscated.provenance.json` | `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b` | `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf` |
| `unobfuscated.sources.sha256` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` |
| `artifacts.sha256` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` |
| `movement-diagnostics.txt` | `6590c89a7d3baf27490ce024d49519ed0dd4ee4e0cc4369c3f419588b15312da` | `38688a7bf9adf0c34a28264178b4361287d45c6ded444a7ad6f8e41ba9d745fa` |

Ready manifests identify distinct exact client inputs (26.1 SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`; 26.1.1 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`) and exact unobfuscated publication, under one paired preparation run. Provenance records 25.0.3+9, Vineflower 1.12.0, and the pair's success log identity. Canonical ready markers and manifests validate these source snapshots even though the ready/staging paths recorded by preparation are historical build paths; conclusions here derive from the extant canonical files and their verified hashes, not an assumption that those raw/staging paths still exist.

| Cited source | SHA-256 on both versions |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |

## Frozen operation trace

- `Player.java:1381–1405`, `travel(Vec3)`: passenger path delegates immediately to `super.travel(input)` and skips the player swim correction. For nonpassengers, only `isSwimming()` enters the pitch correction. It reads `this.getLookAngle().y` into `double lookAngleY`; selects a `double` multiplier using `lookAngleY < -0.2 ? 0.085 : 0.06`; then, if `lookAngleY <= 0.0 || jumping || !fluidAt(eye-near upper sample)`, adds only Y: `movement.add(0.0, (lookAngleY - movement.y) * multiplier, 0.0)` and writes with `setDeltaMovement`. The fluid probe is `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())`; it is evaluated only if the preceding `||` terms are false. Thus upward pitch with no jump and fluid above suppresses this correction; nonpositive pitch or jumping permits it regardless of that fluid probe. The swimming correction precedes inherited movement. Flying then saves the corrected current Y, delegates to `super.travel(input)`, and overwrites resultant Y with saved Y times `0.6`; otherwise it delegates once. This is the full Player-level ordering relevant to the bounded correction.
- `Entity.java:2512–2513`, `getLookAngle()`, uses current `getXRot()` and `getYRot()`, not head rotation or interpolated angles, and delegates to `calculateViewVector`.
- `Entity.java:1879–1887`, `calculateViewVector(float xRot, float yRot)`: both parameters are `float`; degrees-to-radians multiplication uses `(float)(Math.PI / 180.0)` and stores into floats. It negates yaw before that multiplication. It calls `Mth.cos(realYRot)`, `Mth.sin(realYRot)`, `Mth.cos(realXRot)`, `Mth.sin(realXRot)` in that order; returns `Vec3(ySin * xCos, -xSin, yCos * xCos)`. The Y component used by Player is `-xSin` (pitch only); yaw still participates in the horizontal components but not the chosen Y scalar.
- `Mth.java:30–39,49–55`: table length/quantization 65,536, mask 65,535, cosine offset 16,384, scale `10430.378350470453`; table element `i` is `(float)Math.sin(i / 10430.378350470453)` with double division before float cast. `sin(double)` calculates `SIN[(int)((long)(i * 10430.378350470453) & 65535L)]`; `cos(double)` adds `16384.0` before the long conversion and same mask/int cast. Preserve double arithmetic, narrowing cast to `long`, bit-mask, then cast to int. The float argument from Entity widens to double on these calls. There is no direct `Math.sin`, alternate LUT, interpolation or clamping in the path.
- `LivingEntity.java` is byte-identical across the pair, and the Player method above has the same superclass delegation positions. The inherited actual movement entry point is therefore unchanged for this pair. This audit does not claim that the whole player tick or every swim-adjacent behavior is identical.

## Coverage boundary and limitations

Compared the complete paired `Player`, `Entity`, `Mth`, and `LivingEntity` files; focused on `Player.travel`, `Entity.getLookAngle`, `Entity.calculateViewVector`, Mth static sine table initialization and `sin`/`cos`, and the inherited `LivingEntity.travel` caller chain. The relevant methods are unchanged because the complete files have identical bytes. This bounded result does not establish pair-wide player-movement coverage or the patch introduction boundary outside the named operation.

## Post-freeze comparison with author report

Only after the source assessment above was written and its SHA-256 frozen (`91ba49193e07f30970808dd1bdfdf550ca3a359c725fe1d79dc4fc1ca79cee0a`), I opened the assigned author report. Its required SHA-256 is `2cdf9ad704224f5babb3f58d524f9f36c2f226db3205ef183c58ad73809be24d`; the on-disk bytes match. The report's no-difference conclusion, Player correction arithmetic/gates/order, Entity pitch conversion and Mth LUT narrowing/index procedure agree with the independent freeze above. It does not assert a 26.1.1 delta, so there is no claim conflict.

I then checked the report's additional reachability details: `Player.isSwimming()` at `Player.java:1664–1666` is `!abilities.flying && !isSpectator() && super.isSwimming()`; `LocalPlayer.java` has no `travel` declaration in either source tree; and `LivingEntity.travel` dispatches to `travelInFluid`, fall-flying, or air travel, while its water subpath has the report's described speed, slow-down, movement and fluid-fall order. `Player`, `Entity`, `Mth`, and `LivingEntity` are byte-identical across the pair, so these elaborations introduce no paired difference. I agree with the report's stated bounded-coverage limitation.
