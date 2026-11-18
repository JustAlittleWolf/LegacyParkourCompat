# Blind exact-pair audit: 1.16.5 → 1.17 (1.17.0)

## Verdict

**The source bytes show a yaw update precision change that can alter ordinary relative acceleration. Formal exact-pair acceptance is blocked because the exact `1.17` source tree has no version-root readiness, provenance, source-hash, or artifact-hash sidecars.** The 1.16.5 sidecars are present and internally match the inspected source files. Hashing the unready 1.17 tree binds the bytes inspected but cannot establish their exact published-release or Mojmap provenance. No source generation or runtime/build/test execution was performed.

## Exact source identity and readiness

The compared paths are exactly:

- `decompiled_minecraft/1.16.5/mojmap/` (version ID `1.16.5`)
- `decompiled_minecraft/1.17/mojmap/` (version ID `1.17`, the canonical ID for 1.17.0)

The 1.16.5 version-root `mojmap.ready.json` reports `status: ready`, `versionId: 1.16.5`, `mapping: mojmap`, 3,523 source files, and `sourceManifestSha256: 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. Its declared artifact-manifest SHA is `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`; its client SHA is `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`. Actual SHA-256 checks of `mojmap.sources.sha256` and `artifacts.sha256` match those ready-marker values. The 1.16.5 artifact sidecar records the client (`client.jar`) hash above, Mojang mapping text (`client_mappings.txt`) hash `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`, and `client-mojmap.jar` hash `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`. The relevant source-file hashes below match the 1.16.5 source manifest entries.

The exact `decompiled_minecraft/1.17/` version root contains only the `mojmap/` directory. `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256` are all absent there. Thus the current 1.17 source bytes cannot pass the project's exact-source publication/readiness gate.

SHA-256 of the compared source files (the 1.17 values are local byte identities only, without a published source-manifest match):

| Exact version | Source path relative to `mojmap/` | SHA-256 |
|---|---|---|
| 1.16.5 | `net/minecraft/world/entity/Entity.java` | `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666` |
| 1.17 | `net/minecraft/world/entity/Entity.java` | `81231AD8AC1B9A7A9F7EE186352C7E73C0C5211A3034C3A35C04BADC6914C8EF` |
| 1.16.5 | `net/minecraft/world/entity/LivingEntity.java` | `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88` |
| 1.17 | `net/minecraft/world/entity/LivingEntity.java` | `8E81E5F1317D154FA41B676721FF581BADD46296F77BCEBCADB3C63448E528ED` |
| 1.16.5 | `net/minecraft/client/MouseHandler.java` | `2611DCEB1078A63A33D621BF02F466AE70F6D015C902813935B9836F39415949` |
| 1.17 | `net/minecraft/client/MouseHandler.java` | `6FF70F8634171709BA08A42158AEA7C8D4D3974D73F89B43BFE08A5CA6DB5FB2` |
| 1.16.5 | `net/minecraft/util/Mth.java` | `0E4424FFA12923314C309AE13E012768271DFCACE4B5A84727F74E8170AFE290` |
| 1.17 | `net/minecraft/util/Mth.java` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` |
| 1.16.5 | `net/minecraft/client/player/KeyboardInput.java` | `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7` |
| 1.17 | `net/minecraft/client/player/KeyboardInput.java` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` |

The corresponding 1.16.5 ready, provenance, source-manifest, and artifact-manifest file SHA-256 values are respectively `E9DD4397C6ADE5F68D27F50BAF285086DC367B56D2119B03A116CF3CF3B23FEE`, `B71DF61EB775134119881915198F87C4337BAE409E1F0DE8D8D6C007E062902D`, `9499F2611D0E6DDE37CB635F1A28A591416382D6BC188B37D5F99E2B2A20823B`, and `F9B9812D6995012CEFCC1201A63855E5931E8551B508753BE6C46D442D8B370C`. The ready file's source/artifact manifest references verify against those content hashes.

## Exact source difference

- **1.16.5 `Entity.turn(double d, double e)`, `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/Entity.java:340–352`:** calculates `f = e * 0.15` and `g = d * 0.15` in double precision. It adds the double delta to each current float rotation and then casts once to float. It separately adds the same double deltas to the previous rotations and casts those sums. It clamps pitch only. The yaw update has no finite check and does not wrap/normalize yaw.
- **1.17.0 `Entity.turn`, `decompiled_minecraft/1.17/mojmap/net/minecraft/world/entity/Entity.java:377–388`:** first casts each input delta to float, multiplies by `0.15F`, and updates current rotations using float arithmetic through setters. `setYRot`, at `Entity.java:3009–3015`, discards a non-finite current yaw with `Float.isFinite`; pitch is then clamped. Previous rotations are updated separately with float `+=`, without that setter or finite check. Neither `turn` method normalizes yaw. `setRot` applies `% 360.0F` in both versions (1.16.5 `Entity.java:326–329`; 1.17 `:355–358`), but is not called by `turn`.
- **Ordinary input witness:** after the mouse baseline is established, a finite cursor displacement of `+375` in X feeds `accumulatedDX` (1.16.5 `MouseHandler.java:175–208`; 1.17 `:171–204`). With mouse smoothing off, not scoped, and sensitivity `0.5F`, the ordinary turn path passes `d = 375.0` to `turn`: in both versions the sensitivity factor is exactly `0.5F`, and its cube times eight is exactly 1. The 1.17 scoping-specific branch is avoided. No event timing or scheduling assumption is needed for the arithmetic witness.
- **Finite cast/order witness from yaw 0:** in 1.16.5, `float(375.0 * 0.15) = 56.25F`; in 1.17, `float(375.0F * 0.15F) = 56.250003814697266F`. Both current yaw values are finite and accepted, so the witness does not depend on the finite guard.

## Movement trace and numerical result

The ordinary non-fluid, non-fall-flying travel path calls `LivingEntity.handleRelativeFrictionAndCalculateMovement`, which calls `moveRelative` with the friction-influenced speed (1.16.5 `LivingEntity.java:2062–2108`; 1.17 `:2151–2198`). That speed is `flyingSpeed` off ground; the source field initializes it to `0.02F` in both versions (1.16.5 `LivingEntity.java:154`; 1.17 `:182`). On ground, the same method uses the ground friction-influenced speed. The question here is the vector added by `moveRelative`, before `move` collision resolution.

The relative-vector kernel is unchanged: 1.16.5 `Entity.java:1075–1089` and 1.17 `:1174–1188` both use current yaw, compute `g * (float)(Math.PI / 180.0)`, look up `Mth.sin` and `Mth.cos`, then return `(x*cos - z*sin, y, z*cos + x*sin)`. Both `Mth` versions build the same 65,536-entry float sine table and use the same lookup expressions (1.16.5 `Mth.java:12–31`; 1.17 `:27–49`). The trig lookup is a material dependency: adjacent yaw values matter only when their radians-to-table conversion crosses an integer table index.

For keyboard forward only, `KeyboardInput.tick` sets `forwardImpulse = 1.0F` (1.16.5 `KeyboardInput.java:13–25`; 1.17 `:14–26`), giving the ordinary forward-only relative input `(0,0,1)`. At off-ground speed `0.02F`, direct IEEE-754 evaluation of the source expressions yields:

| Version | Stored yaw | Sine table index | Cosine table index | Added X | Added Z |
|---|---:|---:|---:|---:|---:|
| 1.16.5 | 56.25F | 10239 | 26624 | -0.0166283269980 | 0.0111114046474 |
| 1.17.0 | 56.250003814697266F | 10240 | 26624 | -0.0166293915369 | 0.0111114046474 |

The yaw cast/order difference crosses one sine lookup bucket and changes the X component of the relative acceleration vector. The cosine bucket is unchanged because the cosine lookup adds `16384.0F` before integer truncation. The same yaw-dependent rotation applies to the ordinary ground path; its scalar differs with ground friction.

## Edge behavior and limits

For finite but extremely large `double d`, the earlier 1.16.5 double multiply can still produce a finite float delta where the 1.17 cast-first path overflows `(float)d` to infinity. 1.17 rejects a non-finite current yaw setter write but still performs `yRotO += g`; 1.16.5 has no corresponding current-yaw guard. This is API-level edge behavior, not part of the ordinary finite cursor witness. The 1.17 readiness gap prevents attributing these inspected bytes formally to the exact published 1.17 release.

This bounded audit separates pitch, mouse hardware scheduling, vehicle callbacks, fall-flying, collision resolution, and post-move friction/position outcomes. It establishes only the source-level yaw-to-relative-acceleration result and the 1.17 evidence-readiness blocker.

## Addendum: nested exact-source provenance check

The earlier readiness conclusion above was based only on the version-root filenames and is superseded by this check. The exact `1.17` Mojmap tree uses the nested schema at the literal path `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` (present via `Get-Item -LiteralPath -Force`; SHA-256 `AFC9DC30CD8F6406D8EA306E64CC0B3601CC8D7C7BCD314F737573D830A79357`). The pinned local `version.json` reports ID `1.17`. All 4,141 source rows recorded in the nested manifest were rehashed against their exact files: 4,141 matched, with no missing or mismatched rows. All 39 manifest input pins were compared with their corresponding local `.pin.json` URLs/digests and actual cached artifact SHA-256 values: 39 matched, zero mismatches. This includes the exact `1.17` client, official mapping text, and version-metadata pins listed above. The six cited movement/input source rows also match their nested manifest hashes.

Under the explicitly permitted nested exact-source schema, the source identity gate for `1.17` is satisfied despite absent version-root readiness sidecars. The exact-pair source verdict is therefore **accepted for this bounded yaw-to-ordinary-relative-acceleration claim**: the finite `+375` cursor witness changes the sine lookup bucket and the pre-collision acceleration vector as calculated above. This remains a source finding only; runtime parity was not tested.

## Phase-two independent comparison to frozen source report

Before reading the comparison report, its whole-file SHA-256 was verified as `8B862816C21C161606ED6C3B62172B7A969B69586E9F04F893AC515703C821C1` at `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-turn-precision-yaw-1165-117-source\report.md`. The pre-append SHA-256 of this verdict was rechecked as `2949A1E7C6DE658B055877C775738163455DC11F92736E7E46C4042BCB266614`; this addendum preserves that entire prefix.

The report's source identity matches the exact pair under review (`1.16.5` → `1.17`, not `1.17.1`). Rehashed 1.16.5 `Entity.java`, `LivingEntity.java`, and `Mth.java` match both the report and the 1.16.5 source manifest. Rehashed 1.17 counterparts match the report and nested provenance. A fresh full check of the 1.17 nested provenance found all 4,141 source rows matching and all 39 pinned inputs matching their cache pin sidecars and cached artifact bytes; its pinned version metadata ID is `1.17`. No source identity discrepancy was found.

The report's finite yaw witness is independently confirmed for `d = 6.774901758530177`, initial yaw 0, and `e = 0`:

- 1.16.5 stores `1.0162352323532104F`; 1.17 stores `1.0162353515625F`.
- The float radians-to-table-scale values are `184.99996948242188F` and `185.0F`, selecting sine indices 184 and 185. The table samples and X deltas in the report are correct: at `moveRelative(0.1F, new Vec3(0, 0, 1))`, X is approximately `-0.001763986449897087` vs. `-0.0017735723670359305`. Thus the finite yaw change alters the ordinary relative acceleration vector.
- **Correction:** the report's cosine-index and Z-difference sentence is incorrect. The exact `Mth.cos` expression adds `16384.0F` before float truncation; both values round to cosine index 16569. Both use `sin[16569] = 0.9998427033424377F`, so for the report's speed 0.1F the Z component is the same, approximately `0.0999842718241255`, in both releases. At ordinary off-ground speed `0.02F`, X is approximately `-0.0003527972768366944` vs. `-0.0003547144601930424`, while Z remains approximately `0.019996853619884236` in both. The sine-bucket crossing alone is sufficient for the accepted movement claim.

This confirms the bounded source finding and corrects only the asserted cosine/Z consequence. No author report, product file, or other verdict was changed.
