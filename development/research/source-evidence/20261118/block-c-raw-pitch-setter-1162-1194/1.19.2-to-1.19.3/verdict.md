# 1.19.2 → 1.19.3 raw pitch setter audit

## Verdict

`Entity.turn(double, double)` has no source change between the exact Mojmap 1.19.2 and 1.19.3 endpoints. The pitch computation and write order are identical. There is no source-supported pitch-setter finding for this pair. The live Elytra branch reads the final clamped pitch through both `getLookAngle()` and `getXRot()`; the adjacent `checkSlowFallDistance` change is a separate fall-distance behavior and must not be attributed to pitch.

This is a bounded trace of the assigned operation and its next active Elytra movement consumer. It does not claim complete movement-inventory coverage or pair freeze.

## Exact sources and frozen evidence identity

Only canonical local Mojmap source trees `decompiled_minecraft/1.19.2/mojmap/` and `decompiled_minecraft/1.19.3/mojmap/` and their co-located ready/provenance/manifests were read. No product implementation, research/lab/author material, Git state/history, web source, generated source, build, test, game or runtime was consulted.

Both ready markers say `status=ready`, `mapping=mojmap`, `decompiler=Vineflower`, `javaRuntime=25`; the version IDs and client hashes are:

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Client SHA-256 | Source count / bytes |
| --- | --- | --- | --- | --- | --- | --- |
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490` | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` | 4,480 / 21,921,795 |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` | 4,618 / 22,430,993 |

The ready markers' `sourceManifestSha256` and `artifactManifestSha256` match the measured co-located manifest bytes. Both provenance records identify exact batch `1.19.2, 1.19.3, 1.19.4`, `mappingOption=mojmap`, Java `25.0.3+9-LTS`, TinyRemapper `0.14.1`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, Gson `2.14.0`, Mapping IO `0.9.1`, and decompiler heap `4G`. Marker/provenance created-at values are 2026-10-07 14:51 UTC; exact records are bound by the hashes above. The source tree's two relied file hashes match their manifest entries:

| Version | Source member | Source SHA-256 (also the source-manifest entry) |
| --- | --- | --- |
| 1.19.2 | `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.2 | `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.3 | `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.3 | `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |

## Direct pitch trace

In both versions, Mojmap `net/minecraft/world/entity/Entity.java`, `turn(double $$0, double $$1)`, lines 389–400, is byte-for-byte the same source block:

```java
float $$2 = (float)$$1 * 0.15F;
float $$3 = (float)$$0 * 0.15F;
this.setXRot(this.getXRot() + $$2);
this.setYRot(this.getYRot() + $$3);
this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F));
this.xRotO += $$2;
this.yRotO += $$3;
this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
if (this.vehicle != null) {
    this.vehicle.onPassengerTurned(this);
}
```

The authoritative pitch input is the second `double` argument. It is narrowed to `float` before multiplying by `0.15F`; the result is a float pitch delta added to current pitch. The first `setXRot` occurs before yaw is written and before either pitch clamp. The second current-pitch setter receives `Mth.clamp(getXRot(), -90.0F, 90.0F)`. Old pitch is then updated by the same delta and clamped separately. The 1.19.2 and 1.19.3 `setXRot(float)` bodies are also the same: `Float.isFinite` rejects/logs a non-finite value; otherwise it directly assigns `this.xRot = $$0`, with no setter clamp (1.19.2 lines 3059–3064; 1.19.3 lines 3094–3099). Thus the first setter stores a finite out-of-range pitch transiently; the later setter clamps it.

Concrete finite, post-cast witness, independent of device scheduling: start with current pitch `0.0F` and call `turn(0.0, 2401.0)`. The second argument casts to finite `2401.0F`; its `0.15F` product is finite and greater than `360.0F` (approximately `360.15002F`). The first setter therefore accepts and stores a raw pitch greater than 360 degrees. The immediately following pitch clamp stores `90.0F`. The old-pitch accumulator is separately given the same delta and then clamped to `90.0F`. This witness establishes the raw-write-before-clamp order; it does not assume any mouse/input callback cadence.

## Next active Elytra movement read

In both versions, `Entity.getLookAngle()` returns `calculateViewVector(getXRot(), getYRot())` (1.19.2 `Entity.java` lines 1841–1843; 1.19.3 lines 1850–1852). The vector routine converts both rotations to float radians and computes its trigonometric components (1.19.2 lines 1334–1341; 1.19.3 lines 1339–1346).

When `LivingEntity.travel(Vec3)` reaches its `isFallFlying()` branch (1.19.2 `LivingEntity.java` lines 2042, 2107 onward; 1.19.3 lines 2052, 2117 onward), it reads `getLookAngle()` and separately computes `float $$15 = this.getXRot() * (float) (Math.PI / 180.0)`. It then uses the look-vector horizontal magnitude and `cos($$15)` in the Elytra vertical adjustment, and tests `$$15 < 0.0F` for the dive contribution (1.19.2 lines 2113–2129; 1.19.3 lines 2120–2135). Under the stated direct sequence, the next active-flight call therefore sees the clamped current pitch of 90 degrees through both live reads. This identifies the actual movement consumer without asserting that any particular device event schedules `turn` before `travel` in a given tick.

## Adjacent but separate slow-fall change

The Elytra branch has a neighboring fall-distance edit; it is independent of pitch. In 1.19.2, `LivingEntity.travel` reads delta movement and sets `fallDistance = 1.0F` whenever `y > -0.5` (lines 2107–2111), even if fall distance was already at or below 1. In 1.19.3, the branch first calls `Entity.checkSlowFallDistance()` (`LivingEntity.java` line 2118); that helper at `Entity.java` lines 2147–2151 sets fall distance to 1 only when `deltaMovement.y() > -0.5 && fallDistance > 1.0F`. This is a distinct gate/behavior change (e.g. for `y > -0.5` and fall distance `0.5`, 1.19.2 writes `1.0F`, 1.19.3 leaves `0.5F`). It neither changes `Entity.turn` nor changes the pitch reads above.

## Scope boundary

Reviewed slices: exact `Entity.turn`, `setXRot`/`getXRot`, `getLookAngle`/view-vector calculation, and the guarded live Elytra path in `LivingEntity.travel`, for both releases. The adjacent `checkSlowFallDistance` method/call was traced only to distinguish it. No claim is made here about other tick callers, device plumbing, other movement branches, intermediate patch versions, acceptance of a separate slow-fall finding, or full-pair inventory closure.

## Addendum: exact author-report comparison and binding

**Independent source review signature:** `/root/c_raw_pitch_setter_1192_1193_audit` binds this review to the original frozen verdict bytes SHA-256 `27e0681d5d20348e46a7d187510235f620b3127df9008dec1e0c679ad30850e9` and to the read-only author report `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-raw-pitch-setter-1192-1193-source/report.md`, SHA-256 `0bd49ecbeeb3b187a6c3a0a97f5f7a903f12507d6bc735f5deda3410c56a3c81`. The author report hash was rechecked before reading it. This addendum is appended to the signed original; it does not alter or replace the frozen prefix.

**Comparison within the direct-writer scope:** Both reports agree that Mojmap `Entity.turn` computes `(float)$$1 * 0.15F`, invokes `setXRot(current + delta)` before the explicit ±90° clamp, and that `setXRot` directly stores finite floats without its own clamp. Both agree the relevant active Elytra movement pitch reads are unchanged between these endpoints and observe the post-clamp current pitch. The shared source-file identities overlap exactly: source-manifest SHA-256s are `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` (1.19.2) and `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` (1.19.3); relied `Entity.java` and `LivingEntity.java` hashes match the exact entries listed in the frozen prefix.

**Witness comparison:** The author report's direct call uses positive finite delta `1000.0`, whose float product is `+150.0F`; the raw first setter accepts +150° and the following clamp writes +90°. My frozen witness uses positive finite delta `2401.0`; post-cast it remains finite and the float product is approximately `+360.15002F` (>360°), accepted by the same first setter and then clamped to +90°. These are distinct direct-call inputs, both legal finite post-cast witnesses; neither relies on device scheduling. The >360° witness independently confirms that the unclamped write is genuinely raw, not merely modestly outside the legal pitch range.

**Boundary:** This verdict is limited to the direct writer, its finite raw setter and the source-confirmed live pitch reads in active Elytra movement. The author report's added `MouseHandler`/`Player.travel` caller context is not needed for this verdict and is not adopted as an independently retraced claim here. The downstream `checkSlowFallDistance` behavior is excluded from this pitch comparison; its presence in the adjacent Elytra branch does not change the matched pitch computation, setter order or pitch reads.