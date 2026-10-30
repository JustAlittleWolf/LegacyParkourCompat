# Independent product review: glide vertical acceleration

## Immutable review scope

- Product baseline: `bcaecab4301d88ff24c387bf08e7070d5045a836`.
- Reviewed implementation commits: `0e888d844f3fe84b2b2547f8b995b7c1c0b65b28` and import fix `435f3e14fe543a3e31764abdd1a205611a84e670`.
- Current reviewed tree: `435f3e14fe543a3e31764abdd1a205611a84e670` on `fix/resume-movement-20261030`.
- Accepted finding: `elytra-grouping-closure/reviewer-verdict.md`, bound to finding SHA-256 `ED7AC76568ADF100F48D92B27D42785AE703BFD0B7631E32D1850D4E50A1A44E`.
- Alias extension: `.native-source/20261030/ordinary-pool/glide-vertical-acceleration-aliases/source-extension.md`, SHA-256 `55C4700AFEF47ED9426F587DE41E6D169CA8C9EC11EA71D46C93B3ECD841BEA0`.
- Independent alias audit: `.native-source/20261030/ordinary-pool/glide-vertical-acceleration-review/audit.md`. It audited the seven named source samples and correction 1; it explicitly leaves unsampled patch releases and runtime parity open.
- Primary source tree is read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Verdict

**The product wiring is technically sound for the accepted acceleration contract, but full 1.9–1.12.2 applicability acceptance remains open pending source coverage for the enum aliases listed below.** No concrete arithmetic, resolver, discovery, hook-order, descriptor, fallback, or side-gating defect was found. This review does not claim runtime parity.

## Product review

The hook is one independently resolved operation: `GlideVerticalAccelerationHook` changes only the vertical acceleration argument. The `V1_12` implementation returns the exact 1.12 expression `-0.08 + liftForce * 0.06`; Java promotes the float-widened coefficient through the double literal as required. The method preserves native acceleration when the selected profile predates 1.9, Slow Falling is present, gravity differs from `0.08`, or the coefficient is not exactly float-widened. The `V1_8_9` provider explicitly returns native behavior for the modern-only glide operation.

Resolver direction is correct. For selections from 1.9 through 1.12, `V1_12` is the closest eligible historical provider and the 1.8.9 native provider is earlier and ineligible. For selections before 1.9, `V1_8_9` is the closest eligible provider and wins over `V1_12`, preserving native behavior. For 1.13 and later, both changes are earlier than the selection and no historical acceleration provider resolves, leaving the grouped native expression intact. No exact-profile gate or duplicated movement loop was introduced. The classes use public no-argument construction and `@MovementChange`; client discovery registers each hook independently.

The injection is in the client source set and calls `MovementRuntime.find`. That lookup requires a client-side `Player` and an enabled movement profile; it returns empty for non-players, logical-server entities (including integrated-server entities), and current/native profiles. The `@ModifyArg` targets `LivingEntity.updateFallFlyingMovement(Vec3):Vec3`, `Vec3.add(DDD):Vec3`, ordinal 0, y argument index 1. It preserves all other arguments and native fallback.

The exact 26.2 cached `minecraft-common.jar` used for bytecode inspection has SHA-256 `4E10F1910E1431B9A2334093F487EFF02CE012EF1AB8902F0BF9A10A93C0F96C`. In `LivingEntity`, the target method descriptor is `(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;`. Its first `Vec3.add(DDD):Vec3` call is at bytecode offset 76; the other three calls are at offsets 132, 194, and 245. The local-variable table binds `gravity` as double slot 8 from offsets 50–263 and `liftForce` as double slot 10 from offsets 60–263, matching the named MixinExtras locals. The source file hash is `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`; its source expression and following dive, pull-up, horizontal-steering, and drag operations match the review contract. This verifies the target shape and injection ordinal statically, not a live Mixin application.

## Remaining source-coverage gate

The independently audited alias evidence establishes the expression in 1.9, 1.9.4, 1.10.2, 1.11.2, 1.12, 1.12.1, and 1.12.2. Its independent audit expressly does not establish unsampled patch versions. The product's `ParkourVersion` aliases select the same `V1_12` provider for the whole 1.9–1.12 range, so the uninspected aliases remain material to the claimed range:

- `V1_9`: 1.9.1, 1.9.2, 1.9.3.
- `V1_10`: 1.10.0.
- `V1_10_1`: 1.10.1 (1.10.2 is sampled).
- `V1_11`: 1.11.0.
- `V1_11_1`: 1.11.1.

The 1.11.2 sample closes its own `V1_11_2` alias only; it does not establish the 1.11.0 or 1.11.1 groups. Together the seven missing releases above leave alias coverage open. No pair-freeze is required for this review; an independent exact-source audit of those missing releases is sufficient to close the patch-alias gate. Until then, implementation architecture and sampled-source fidelity are accepted, while the complete 1.9–1.12.2 applicability range is not fully source-accepted.

## File identities

SHA-256 values of the reviewed product files at the reviewed tree:

| File | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java` | `49769C95874EEDC9FEBD25681B1C0180D9908E13A243EC511E514593EA3186BF` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/GlideVerticalAcceleration.java` | `497B98A2DC6D4AC543FBD8666D6DF4E46857C883D53B53FBC7A7E94C94C78E92` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8_9/NativeGlideVerticalAcceleration.java` | `30486F5091D29C34C9D46969300A7972EABAB85513EECDCBE02BD2BCE303091E` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/travel/GlideVerticalAccelerationHook.java` | `02B15CEB7AEB7A0D4F5DCF84DCE634B1A184D64351F4539BD194335C19926F0E` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | `57F3B806F65B48AD0ABB2612CDC67BF84C94D70C3A4A8EA230FD7D6F962AB763` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java` | `606529E8FDC96B3EE34B70224AA82B954065B6DF6A8D405DF835AAE8B7703B73` |

No product files were changed. No build, test, or runtime launch was performed, per assignment.
