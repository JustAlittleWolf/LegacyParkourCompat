# Independent source verdict: Entity.turn pitch setter, 1.18.1 → 1.18.2

## Verdict

**No source difference is established in the assigned `Entity.turn` pitch-write operation or its `setXRot` finite guard.** Both versions perform the same float conversions and multiplications, first pitch write, yaw write, second pitch clamp/write, and previous-pitch update/clamp in the same order. The setter in both versions accepts any finite `float` as-is; it does not itself clamp to ±90°. The transient first write can therefore exceed 360°, but ordinary `turn` clamps the authoritative pitch before returning. This audit does not support a 1.18.2 change to the direct pitch setter or the second clamp.

## Exact source slices

All source paths below are relative to the versioned `decompiled_minecraft/<version>/mojmap/` tree; line numbers are one-based.

- **1.18.1, Mojmap:** `net/minecraft/world/entity/Entity.java`, `turn(double,double)`, lines 383–395; `getXRot`/`setXRot`, lines 3010–3020. `net/minecraft/world/entity/LivingEntity.java`, Elytra branch in `travel`, lines 2070–2100.
- **1.18.2, Mojmap:** `net/minecraft/world/entity/Entity.java`, `turn(double,double)`, lines 385–397; `setXRot`, lines 3027–3037. `net/minecraft/world/entity/LivingEntity.java`, Elytra branch in `travel`, lines 2074–2104.

In both `turn` bodies, the operation sequence is:

1. `float pitchDelta = (float)pitchInput * 0.15F`.
2. `float yawDelta = (float)yawInput * 0.15F`.
3. `setXRot(getXRot() + pitchDelta)`.
4. `setYRot(getYRot() + yawDelta)`.
5. `setXRot(Mth.clamp(getXRot(), -90.0F, 90.0F))`.
6. Add the same pitch/yaw deltas to `xRotO`/`yRotO`, then clamp `xRotO` to ±90°.
7. If mounted, notify the vehicle.

The corresponding setters in both releases use `if (!Float.isFinite(value))` to log/discard; otherwise they directly assign `this.xRot = value`. `setXRot` has no range clamp in either release. There is no intervening hook or alternate assignment in the base `Entity.turn` body between the first pitch write and its second clamp.

A finite source witness is `turn(0.0, 3000.0)` from pitch `0.0F`: the cast remains finite, multiplication by `0.15F` yields `450.0F`, and the first `setXRot` accepts and temporarily assigns 450°. The later clamp writes 90° before `turn` returns. This proves a legal finite post-cast intermediate above 360°, not a post-call pitch above 360°.

## Reachable movement read and scope boundary

For an active fall-flying `LivingEntity`, the ordinary `travel` branch reads the resulting pitch: it computes `float pitchRadians = getXRot() * (float)(Math.PI / 180.0)`, then uses that value in the Elytra vertical-motion calculation. In 1.18.1 the local cosine is `Mth.cos(pitchRadians)` (float); in 1.18.2 it is `Math.cos(pitchRadians)` (double). This is a distinct real movement-path source difference and is deliberately **not** attributed to the pitch setter/second-clamp claim here. Since `turn` completes its clamp before a later active Elytra travel reads the field, the 450° intermediate from the witness is not the value consumed by that next movement read. This audit makes no claims about input-device scheduling, rendering, networking, teleportation, or runtime parity.

## Evidence identity and integrity

- **1.18.1:** canonical version-root ready marker says `ready`, `1.18.1`, `mojmap`, 4,203 source files, and source-manifest SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`. Ready-marker SHA-256: `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81`. Provenance run ID `prep-1.18-1.18.1-mojmap-2026-10-09-r1`; provenance SHA-256: `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`. Provenance identifies the official client mappings input SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84` and client SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`.
- **1.18.2:** canonical version-root ready marker says `ready`, `1.18.2`, `mojmap`, 4,236 source files, source-manifest SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. Ready-marker SHA-256: `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`. Provenance SHA-256: `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`. Its provenance records the Mojmap namespace and tool versions but has `mappingArtifacts: null` and omits a source-manifest digest/raw mapping artifact identity; the ready marker provides the source-manifest digest and client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`.
- For both versions, the checked `Entity.java` and `LivingEntity.java` byte hashes exactly match their entries in `mojmap.sources.sha256`; those manifests match the digest in each ready marker. The 1.18.1 hashes are respectively `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` and `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`. The 1.18.2 hashes are respectively `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` and `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.

The ready markers' `outputPath` fields point into `build/movement-campaign-2026-10-07/ready/...`, and those paths are absent in this checkout. This verdict therefore relies on the present version-root trees whose source manifests and relied file bytes match their version-root ready markers; the marker path discrepancy and the thinner 1.18.2 provenance are recorded rather than treated as stronger provenance than supplied.

## Independent Phase Two binding

Reviewed independent source report revision SHA-256: `b97280980b087c13bb4d03c900f5cac6851a750fbd071a66347072de534d01bd`.

**Qualified acceptance, bounded to this slice:** I accept the report's behavioral conclusion that the checked 1.18.1 and 1.18.2 `Entity.turn` pitch setter path has no source delta: the float cast/multiply, first finite setter, yaw setter, second `[-90F, 90F]` clamp/setter, and old-pitch update/clamp retain the same order and operations. The cited finite `1.0E20D` input remains finite through the float cast and `0.15F` multiplication, so the first setter accepts an intermediate above 360°; the second setter clamps it before `turn` returns. The ordinary local caller and inherited active-Elytra travel read support the scoped conclusion that this completed turn path supplies a clamped pitch to later movement. No runtime or scheduling conclusion is accepted.

**Evidence qualification:** The present version-root source bytes match their source-manifest entries and the source-manifest digests in the ready markers. The newer ready marker/provenance do not close source-origin provenance: its `mojmap.provenance.json` has `mappingArtifacts: null` and omits raw-input and source-manifest digest fields, while both ready markers point at absent historical `outputPath` directories. I accept the bounded source comparison on the inspected, manifest-matched bytes; I do not treat the newer raw client/mapping cache origin as independently proven by that provenance record.

The Elytra `Mth.cos(float)` → `Math.cos(float)`/double-width source delta remains separate and is not part of this accepted setter verdict. It must not be folded into the pitch-setter finding.

Signed review identity: `/root/c_raw_pitch_setter_1181_1182_audit`, independent reviewer, 2026-11-18.
