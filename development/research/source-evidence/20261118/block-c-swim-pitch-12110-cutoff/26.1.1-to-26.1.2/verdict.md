# Independent source audit: 26.1.1 → 26.1.2

## Frozen source evidence (before author report access)

Scope: exact unobfuscated source comparison of the local, nonpassenger `Player.travel` pre-super swimming pitch correction, its authoritative view-angle/trig inputs, and the caller/order needed to establish reachability. This is a bounded operation audit, not full-pair movement coverage or runtime parity.

### Source identity and readiness

Canonical read-only root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release | Namespace | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Manifest source count / bytes | Original client SHA-256 |
| --- | --- | --- | --- | --- | --- | --- |
| 26.1.1 | unobfuscated | `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802` | `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | 6,882 / 32,992,043 | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 | unobfuscated | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | 6,882 / 32,992,615 | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

Both ready markers say `status: ready`, identify exact version and `unobfuscated`, and name their source-manifest hashes above. The relevant source bytes hash to the entries in each source manifest. Each relied file has the same SHA-256 on both sides:

| Relative source | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

`unobfuscated.ready.json` also binds each source manifest and diagnostics/artifact manifests; its `sourceManifestSha256` agrees with the on-disk manifest digest. Provenance identifies each exact release's client artifact and Vineflower 1.12.0 / Java 25.0.3 run. No source generation or online mapping lookup was used.

### Paired source trace and exact arithmetic

Both versions have byte-identical `Player.java`, `Entity.java`, `Mth.java`, `LivingEntity.java`, and `LocalPlayer.java` (hashes above); corresponding lines and operation order are therefore identical.

- `Player.java:1381–1403`, `Player.travel(Vec3)`: if passenger, it immediately delegates to `super.travel(input)`. Otherwise, it tests `isSwimming()` before any `super.travel` call. On true, it reads `double lookAngleY = this.getLookAngle().y`, chooses `double multiplier = lookAngleY < -0.2 ? 0.085 : 0.06`, and conditionally adds vertical movement when `lookAngleY <= 0.0 || this.jumping || !fluidAt(x, y + 1.0 - 0.1, z).isEmpty()`. It captures the then-current `Vec3 movement`, computes `(lookAngleY - movement.y) * multiplier`, adds `(0.0, correction, 0.0)`, and writes with `setDeltaMovement`. Only after this pre-super correction does it enter the flying/nonflying branch and call `super.travel(input)`; the flying branch later replaces Y with saved pre-super Y times `0.6`.
- `Player.java:1664–1666`, `Player.isSwimming()`: player swimming is false when flying or spectator, otherwise delegates to `super.isSwimming()`.
- `Entity.java:2512–2514`, `getLookAngle()`: uses current `getXRot()` and `getYRot()` directly, without partial-tick interpolation.
- `Entity.java:1879–1887`, `calculateViewVector(float,float)`: computes float radians in order: `realXRot = xRot * (float)(Math.PI / 180.0)`, `realYRot = -yRot * (float)(Math.PI / 180.0)`; then table values `yCos`, `ySin`, `xCos`, `xSin`; returns `(ySin*xCos, -xSin, yCos*xCos)`. Thus look-angle Y is exactly the float `-Mth.sin(realXRot)`, widened into the `Vec3` double component.
- `Mth.java:31–38,49–55`: the 65,536-element float SIN table is populated as `(float)Math.sin(i / 10430.378350470453)`. `sin(angle)` indexes `SIN[(int)((long)(angle * 10430.378350470453) & 65535L)]`; `cos(angle)` uses the same cast/mask after adding `16384.0`. No `Math.sin` runtime replacement or interpolated trig is used by this correction.
- Reachable local caller: `LivingEntity.java:3070–3074` dispatches `this.travel(input)` when movement can be simulated and effective AI is present; the local-player tick state in `LocalPlayer.java` is byte-identical between releases. Passenger dispatch in `Player.travel` excludes the audited direct operation.
- Downstream order: `LivingEntity.java:2392–2400` delegates from its `travel` to fluid, fall-flying, or air movement. The audited direct Y correction is performed before that inherited dispatch. No claim is made here about the subsequent total tick displacement.

### Concrete pre-super trace

For a reachable nonpassenger swimming player, choose current raw `xRot = +30.0F`, `yRot = 0.0F`, current vertical movement `0.0`, not jumping, no fluid at the checked head cell, and nonflying/nonspectator state. Exact source lookup arithmetic gives `realXRot = 0.5235987901687622F`, SIN index `5461`, table result `0.49997231364250183F`, and therefore `lookAngleY = -0.49997231364250183`. Since `lookAngleY < -0.2`, multiplier is `0.085`; since `lookAngleY <= 0`, the write gate is true independent of jumping/head-fluid. The double correction is `(-0.49997231364250183 - 0.0) * 0.085 = -0.042497646659612656`; the resulting pre-super Y velocity is that value. Both versions follow this exact trace and then call inherited `travel` (nonflying path).

### Frozen independent conclusion

For the assigned operation and dependencies, I find **no 26.1.1 → 26.1.2 source difference**: all five relied source files are byte-identical, and the pitch derivation, LUT index/math, threshold, gate, velocity read/write, and pre-super order are unchanged. No source finding is supported for this bounded claim. This does not close the full 26.1.1/26.1.2 movement-pair inventory, prove runtime parity, or establish the historical introduction boundary.

## Author-lab comparison (after independent evidence freeze)

Opened only after the frozen comparison above. Author report identity supplied for audit: `c-swim-pitch-vertical-2611-2612-source/report.md`, SHA-256 `23231f1bc99801b21817db364a8e0db1c9220fca37810ef21bb3061dcde9b8b0`; actual bytes match. The report's no-difference conclusion, four source hashes, gates, arithmetic, order, and 26.1.1-to-26.1.2 scope agree with this independent audit. Its downstream trace is also source-supported: `LivingEntity.travel` selects fluid/fall-flying/air at lines 2392–2399; in water `travelInFluid` calls `travelInWater` (2455–2465), which calls `moveRelative` then `move(MoverType.SELF, getDeltaMovement())` (2467–2493); inherited `Entity.move` applies collision and sets the resulting position (704 onward). LocalPlayer calls `super.tick()` from `tick()` (LocalPlayer.java:227–231) and `super.aiStep()` from `aiStep()` (767 onward, 914); no competing local-player travel override was found. These calls support reachability and the report's immediate downstream path without extending the claim to total-tick parity.

**Independent verdict: agree, no source difference for this bounded operation.** Pair-wide movement coverage and runtime verification remain open. This verdict is bound to the source identities and author report hash above.
