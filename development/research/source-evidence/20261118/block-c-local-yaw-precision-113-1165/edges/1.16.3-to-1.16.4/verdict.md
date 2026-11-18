# Independent audit verdict: 1.16.3 → 1.16.4 yaw setter and first dry acceleration consumer

## Verdict

**No 1.16.3 → 1.16.4 source difference is substantiated in this bounded slice.** The ordinary local mouse-to-player yaw update, its current/previous angle writes and finite behavior, and the first dry nonvehicle ground/air relative-acceleration consumer are byte-identical in the inspected canonical Mojmap source files. Do not implement a 1.16.4 cutover for these operations from this comparison. This is an individual bounded verdict, not full-pair coverage, a patch-boundary finding, or runtime parity evidence.

## Evidence identity and freeze

The exact directories are `decompiled_minecraft/1.16.3/mojmap` and `decompiled_minecraft/1.16.4/mojmap`. Their `.decompile-provenance.json` files identify official Mojang `client.txt` mappings and exact client artifacts:

| Release | `client.jar` SHA-256 | Mojang mappings URL and SHA-256 | exact version metadata SHA-256 | provenance SHA-256 | whole source-manifest digest |
|---|---|---|---|---|---|
| 1.16.3 | `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `https://piston-data.mojang.com/v1/objects/faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt` — `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` | `3899056cf8b470e2eb52a2f9457d25d4b4a9de76a1eb86817c6667a11bb90f4d` |
| 1.16.4 | `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt` — `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` | `115c823a4f8ab03a17a2376a81dc09519532fd5db098d47f86b3fb90fc7cb07f` |

Each source-manifest digest is SHA-256 over UTF-8 lines sorted by relative source path, each line `<relative path><TAB><lowercase SHA-256 from manifest><LF>`. I rehashed every listed source file against the manifest before the detailed paired method comparison: 3,516/3,516 matched for 1.16.3 and 3,523/3,523 for 1.16.4, with no missing or mismatched files. The pair's whole manifest digests differ because the source manifests are not identical; the relevant source-file hashes below match exactly.

**Process note:** An initial locator search touched the paired source members before these whole-manifest digests were computed. The digests and per-file integrity checks were frozen before the detailed method-by-method comparison recorded here, but the audit cannot claim strict freeze-before-any-source-peek ordering.

## Ordinary local yaw update and finite handling

- `net/minecraft/client/MouseHandler.java`, `turnPlayer`, line 242, sends local camera deltas to `this.minecraft.player.turn(j, k * n)` when the mouse is grabbed and the window active. This caller's SHA-256 is `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` in both releases.
- `net/minecraft/world/entity/Entity.java`, `turn`, lines 340–352, accepts two `double`s. It computes `f = e * 0.15` (pitch) and `g = d * 0.15` (yaw) as doubles; writes current pitch and yaw as `(float)(field + doubleDelta)`; clamps current pitch; then writes previous pitch and yaw as `(float)(oldField + doubleDelta)`; clamps previous pitch; and only then invokes the vehicle passenger callback when `vehicle != null`. Yaw is neither wrapped nor clamped here. There is no finite check on this direct update. This class's SHA-256 is `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` in both releases.
- The protected `setRot(float,float)` at `Entity.java:326–329` is a separate setter: it writes yaw first as `f % 360.0F`, then pitch as `g % 360.0F`; it has no finite check. It is not the ordinary local mouse call above. Its source is also identical by the enclosing `Entity.java` hash.
- The `Double.isFinite` checks at `Entity.java:1438–1445` are in entity NBT loading, after stored position/rotation values are read. Nonfinite position throws; rotation must pass the finite gate before `setRot` and the remainder of load. This load-only gate does not filter ordinary local `turn` inputs. Current and previous yaw in `turn` are both updated without a finite gate in both releases.

A lawful finite witness through the ordinary setter is `d=300.0`, `e=0.0`, initial `yRot=0.0F` and `yRotO=0.0F`: `g=45.0` as a finite double, then both current and previous yaw become `45.0F`. A mouse delta of 300 is reachable with finite accumulated mouse input and sensitivity 0.5 (`(0.5F * 0.6F + 0.2F)^3 * 8.0 == 1.0`). This confirms the bounded path uses the ordinary setter; it is not a runtime parity test.

## First dry, nonvehicle ground/air acceleration consumer

The client-local path reaches `LivingEntity.aiStep`'s `travel(new Vec3(xxa, yya, zza))` call at `LivingEntity.java:2459`; `LocalPlayer.aiStep` calls `super.aiStep` at line 788. Both files are byte-identical across the pair (`LocalPlayer.java` SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`; `LivingEntity.java` SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`). In the nonfluid, non-lava, non-fall-flying branch of `LivingEntity.travel` (lines 2020–2047), the ground/air friction `t` is read and passed through `handleRelativeFrictionAndCalculateMovement` (`:2062–2064`). `getFrictionInfluencedSpeed` (`:2106–2108`) selects `getSpeed() * (0.21600002F / (t*t*t))` when grounded and `flyingSpeed` in air. Both routes call `Entity.moveRelative` (`Entity.java:1075–1089`), which reads **current** `yRot`, adds the resulting input vector to current delta movement, and then returns to the travel branch's horizontal friction update. It does not use `yRotO` for this acceleration.

`Entity.getInputVector` computes input squared length in `double`, returns zero below `1.0E-7`, normalizes only above 1.0, scales by the `float` speed, then computes yaw radians from float yaw times `(float)(Math.PI / 180.0)`. It calls `Mth.sin(float)` and `Mth.cos(float)` and constructs a `Vec3` with double components. `Mth`'s trigonometric lookup and `Vec3` are unchanged as well (`Mth.java` SHA-256 `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`; `Vec3.java` SHA-256 `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48`). Thus the first dry acceleration consumer has no 1.16.3 → 1.16.4 math, type, order, or finite-handling difference in this slice.

## Bounded conclusion

The compared direct mouse turn and its earliest dry relative-acceleration path are unchanged at the exact endpoints. The finite checks found in the same `Entity` class are scoped to NBT load, not local turning. This establishes no yaw/consumer finding for 1.16.3 → 1.16.4; it does not inspect every later yaw consumer, other external rotation writer, intermediate patch, or establish tick-level runtime equivalence.




## Phase-two review of the source report

**Disposition: ACCEPT for the bounded no-difference claim.** I rehashed and compared the assigned lab report `c-yaw-precision-1163-1164-source/report.md` (SHA-256 `365b4825c2565ad17eaaffe55011e87f01098ae4fe733f1775f2d3889fcbfd55`) against this audit. Its exact release artifacts, official mapping inputs and hashes for `Entity.java`, `LivingEntity.java` and `Player.java` agree with the phase-one evidence. Its yaw-only extraction from `Entity.turn` agrees with the audited writes: finite `double` increment `d * 0.15`, current yaw cast/write before previous yaw cast/write, with neither yaw wrap nor clamp in this method and no finite gate. Its finite example (`d=10.0`, `e=0.0`, current `0.25F`, previous `0.125F`) correctly yields increment `1.5`, current `1.75F` and previous `1.625F`; byte-identical methods rule out a finite input that would distinguish these releases for this operation.

The report's downstream consumer summary agrees with the unchanged `LivingEntity.travel` → `handleRelativeFrictionAndCalculateMovement` → `Entity.moveRelative` yaw path and the phase-one source comparison. The report additionally cites `Player.travel` at `Player.java:1387–1417` as the delegation edge; that override body was not re-opened in this phase-two comparison, so I do not extend this audit's independent coverage to that additional edge. This does not contradict the bounded no-difference result for `Entity.turn` or the unchanged first consumer already inspected.

The phase-one source-timing caveat above remains in force. No earlier bytes of this verdict were edited for phase two; this section was appended after the preserved original prefix. The source report was treated as read-only.