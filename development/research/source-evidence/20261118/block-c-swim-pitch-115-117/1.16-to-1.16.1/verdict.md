# Frozen blind source audit: 1.16 → 1.16.1 swim pitch correction

**Freeze status:** source-only trace frozen before opening the paired source report. This is a bounded independent audit of the local `Player.travel` swim-pitch correction and its direct inherited movement consumer; it is not full-pair coverage or pair freeze.

## Verdict

No 1.16 → 1.16.1 source difference exists in this operation or its traced look-vector and inherited-travel dependencies. Both `Player.travel` implementations have identical SHA-256 and identical method bodies. The correction is already present in 1.16.0 and remains unchanged in 1.16.1. Any claim of a 1.16.1 introduction or changed coefficient/gate/order for this operation is unsupported by this pair.

## Frozen operation trace

For both releases, Mojmap `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)` (lines 1380–1410): the method first saves x/y/z, then checks `isSwimming() && !isPassenger()`. Under that gate it reads `double g = this.getLookAngle().y`, selects `double h = g < -0.2 ? 0.085 : 0.06`, and conditionally corrects only vertical delta: `g <= 0.0 || jumping || !fluid-empty at BlockPos(x, y + 1.0 - 0.1, z)`. On that condition it reads current delta and calls `setDeltaMovement(vec32.add(0.0, (g - vec32.y) * h, 0.0))`. The `0.1` and `0.2` literals, ternary branch, predicate order, double locals/arithmetic, and exact add/set order match. Next, flying non-passengers take the flying branch and call `super.travel(vec3)` at line 1399; all others call it at line 1406. Movement statistics follow. Thus the pre-super vertical correction is directly supplied to inherited travel in either branch; the inherited `LivingEntity.travel(Vec3)` source is also byte-identical across the pair.

The direct look dependency is identical: `Entity.getLookAngle()` (Entity.java lines 1680–1682) calls `calculateViewVector(this.xRot, this.yRot)`; `calculateViewVector` (lines 1237–1245) converts each angle to float radians, calls `Mth.cos/sin`, and constructs a `Vec3(k*l, -m, j*l)`. `Vec3` stores `double x/y/z` and its constructor accepts doubles (Vec3.java lines 12–14, 39–43), so each float product is widened on construction and the swim path reads `y` as double. `Mth` uses the same 65,536-entry float SIN table in both versions, initialized with `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)` (Mth.java lines 12–16); `sin(float)` indexes `SIN[(int)(f * 10430.378F) & 65535]` and returns float (lines 25–26). Its LUT initialization and index conversion are unchanged. This describes the old float-index / float-result widening as shared baseline behavior, not a 1.16.1 delta.

## Exact-source and publication evidence

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; both namespaces are `mojmap`. Relevant source paths are relative to each version's `mojmap/` tree. Release identity is corroborated by `mojmap.ready.json` (`status: ready`, exact `versionId`, namespace, source-manifest SHA, client SHA) and `mojmap.sources.sha256`.

| Input | Ready marker SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| 1.16 Mojmap | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 Mojmap | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |

The source-manifest SHA values match each ready marker. Each relevant source file's byte count and SHA-256 were independently recalculated and match its entry in both source manifests:

| Relative source file | Bytes (both) | SHA-256 (both) |
|---|---:|---|
| `net/minecraft/world/entity/player/Player.java` | 75,457 | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/entity/Entity.java` | 96,435 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/LivingEntity.java` | 112,004 | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/util/Mth.java` | 13,453 | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| `net/minecraft/world/phys/Vec3.java` | 6,326 | `555519ac5fa0ce1a240f90e7db64d5dbc4b005012d91edebcf940009fcbf03f2` |

The 1.16.1 ready marker records exact version/source hashes but does not itself include a provenance hash; the adjacent provenance JSON is recorded above by its independently calculated hash. No source regeneration was performed.

## Coverage boundary

Compared the complete local correction method, direct look-vector and trigonometric lookup path, Vec3 storage/widening, and inherited `LivingEntity.travel` consumer in both exact Mojmap releases. No other fluid, collision, or player movement operation was audited. No intermediate patch releases were inspected, so this verdict does not establish where behavior originated before 1.16.0.

## Post-freeze report comparison and direct move confirmation

After the source-only trace above was frozen, I verified the assigned source report's exact SHA-256 as `f6df5951c2512498886777eafad63f603f9f25ed77aace83e5251e1a1cd8ce91`. Its bounded no-difference conclusion and cited source hashes agree with this independent audit. I then directly checked the actual movement consumer in both source trees: where inherited `LivingEntity.travel` takes its water branch under `isInWater() && isAffectedByFluids() && !canStandOnFluid(...)`, it invokes `moveRelative(g, vec3)` and then `move(MoverType.SELF, getDeltaMovement())` in that order (LivingEntity.java lines 1921–1945). `Entity.move(MoverType, Vec3)` is the inherited movement entry (Entity.java line 469 onward). The pre-super player correction therefore remains present in delta movement for this inherited SELF move; this confirms consumption, without extending the audit to the body of fluid/collision mechanics. The corresponding `LivingEntity.java` and `Entity.java` files are byte-identical between the exact releases.
