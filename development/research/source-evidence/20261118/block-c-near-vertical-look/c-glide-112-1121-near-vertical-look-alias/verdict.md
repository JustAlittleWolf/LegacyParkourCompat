# Independent source audit verdict

## Verdict

**Accept the bounded no-difference conclusion** for the reported ordinary dry, already-active Elytra movement path between 1.12 and 1.12.1. The report identity reviewed is SHA-256 `4b2a53ebda3a6332b6990f395158760c5f0604ad78f5822ca3254754afdec9da` (`c-glide-112-1121-near-vertical-look-alias/report.md`). This verdict covers only that claim and its listed source dependencies; it does not freeze the pair or establish runtime parity.

## Source identity checked

The exact local directories are `decompiled_minecraft/1.12/ornithe-feather/` and `decompiled_minecraft/1.12.1/ornithe-feather/`. Their local `.decompile-provenance.json` files hash to, respectively, `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`, matching the report. The manifests identify the Feather mapping metadata endpoints for exact releases 1.12 and 1.12.1 and the corresponding `feather-gen2` `+build.2-mergedv2.jar` artifacts. Their recorded artifact hashes match the report: `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` and `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`. Client input hashes also match: `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` and `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`.

For every path below, the actual local file SHA-256 matches the corresponding `sources` entry in that version's local provenance manifest:

| Source path | 1.12 SHA-256 | 1.12.1 SHA-256 | Pair result |
|---|---|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same | byte-identical |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same | byte-identical |
| `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` | same | byte-identical |
| `net/minecraft/util/math/Vec3d.java` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` | same | byte-identical |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | file differs; cited override is text-identical |

## Independent trace

`LivingEntity.moveRelative` has the same ordinary movement gates in both versions: local control or logical-side movement, dry path, then `isFallFlying()` at lines 1425–1429. The state predicate is `return this.getFlag(7)` in both versions (line 2184); this review assumes the already-active state as assigned and does not review activation eligibility. `PlayerEntity.moveRelative` calls `super.moveRelative` in the ordinary non-creative-flight branch (lines 1386–1404). The entity tick calls `flyingTick()` then `moveRelative(...)` (LivingEntity lines 1846–1847).

The same shared `Entity.java` computes `getLookVector()` by passing current pitch and yaw to `getRotationVector(float,float)` (lines 1690–1692, 1239–1244); that method returns the trig-derived components directly without normalizing. `MathHelper.sin` and `cos` use the same float-scaled integer sine-table indices (lines 15–21). Thus equal finite angle lookup indices alias to the same look components in each release.

In both `LivingEntity.moveRelative` bodies, `j = sqrt(vec3d.x² + vec3d.z²)` is followed by the same descending correction under `velocityY < 0 && j > 0` (lines 1442–1447), then the pitch-up branch under `i < 0` (1449–1454). That pitch-up branch divides its x/z corrections by `j` without its own positive-j guard; the later steering branch separately checks `j > 0` (1456–1459). For the assigned near-vertical finite view with a small positive `j`, the same correction and denominator are reached in both versions, in the same order, followed by the same drag and `move(SELF, ...)` (1461–1464). No difference was found in the claim's source path.

Precision note: `Vec3d.length()` calls `MathHelper.sqrt(double)` and receives a `float` return widened to `double` (Vec3d lines 91–93; MathHelper lines 23–29). The report's summary of this as a square root of the raw squared components omits that return cast. Both source files are identical across the pair, so the omission does not change the no-difference verdict, but the cast should be included if the report is reused as an exact numeric description.

## Limits

No malformed inputs, networking, server movement, Elytra activation/eligibility, final trajectory outcome, runtime launch, or A/B comparison was examined.