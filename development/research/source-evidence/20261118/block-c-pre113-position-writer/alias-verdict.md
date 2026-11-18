# Independent audit: pre-1.13 position reconstruction writer pins

Status: **COMPLETED — ACCEPT, narrowly scoped**

## Independent verdict

I independently confirm the bounded claim for the nine assigned exact snapshots: **1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.2, 1.11, 1.11.1, 1.12 and 1.12.1**. In each ordinary non-noClip `Entity.move` path, after movement resolution and the normal profiler `pop()` / `push("rest")`, the method calls `setPositionFromShape()` unconditionally. No test of requested, resolved, clipped, or tiny displacement guards this terminal call. Therefore zero and tiny resolved displacements still reach the writer when ordinary control reaches this terminal point.

In every snapshot the helper reads `getShape()` once into a `Box` and assigns in order:

1. `x = (box.minX + box.maxX) / 2.0`
2. `y = box.minY`
3. `z = (box.minZ + box.maxZ) / 2.0`

These are double-valued expressions with no intermediate cast or extra arithmetic. The helper does not use the requested movement delta, the resolved delta, or the previous position. This accepts the formula and call applicability only; the final AABB producer is an upstream dependency and was not traced.

`noClip` is an explicit earlier branch in every snapshot: it translates the current shape by the passed deltas, calls the same helper, and skips the ordinary branch. Versions 1.9.2–1.10.2 use `move(double,double,double)` and have no piston gate. Version 1.11 uses `move(MoverType,...)` but likewise has no `MoverType.PISTON` limiter. Versions 1.11.1, 1.12 and 1.12.1 put a piston limiter at the start of the ordinary branch, before resolution: it clamps accumulated selected-axis displacement to `[-0.51, 0.51]`, then returns when `Math.abs(adjustedComponent) <= 1.0E-5F`; the Z branch also returns for `z == 0.0`. This gate is limited to `MoverType.PISTON`, so it does not gate ordinary player movement. The 1.11 to 1.11.1 snapshot contrast is confirmed; the precise patch that introduced it is not established.

## Exact evidence checked

All source files are `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`, namespace `ornithe-feather`, Feather Gen2 build 2. Exact SHA-256 values below were recomputed from the source files and matched their source-manifest/provenance entry (or the indicated nested provenance entry). The listed call and helper lines are current one-based source lines.

| Version | Entity.java SHA-256 | Ordinary terminal call | Helper | Mapping artifact SHA-256 (mergedv2 / tiny) |
|---|---|---:|---:|---|
| 1.9.2 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | 622 | 719–724 | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` / sidecar has no tiny hash |
| 1.9.3 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | 625 | 722–727 | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` / sidecar has no tiny hash |
| 1.9.4 | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | 625 | 722–727 | `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` / `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` |
| 1.10 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 630 | 727–732 | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` / sidecar has no tiny hash |
| 1.10.2 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | 630 | 727–732 | `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` / `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` |
| 1.11 | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | 652 | 749–754 | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` / `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` |
| 1.11.1 | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | 693 | 799–804 | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` / `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` |
| 1.12 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | 696 | 797–802 | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` / sidecar has no tiny hash |
| 1.12.1 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | 696 | 797–802 | `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` / sidecar has no tiny hash |

The `1.9.2`, `1.9.3`, `1.10`, `1.12` and `1.12.1` nested `.decompile-provenance.json` source entries contain the matching `Entity.java` hashes; their mapping artifact inputs match the hashes above. The 1.9.4, 1.10.2, 1.11 and 1.11.1 `ornithe-feather.sources.sha256` entries and mapping artifact sidecars match the source and artifact hashes above. `ornithe-feather.provenance.json` identifies the exact version and namespace for the sidecar-backed releases. The 1.12 and 1.12.1 active and retained nested provenance files agree; both source versions have identical `Entity.java` content.

The noClip branch call sites are at 441, 444, 444, 449, 449, 458, 462, 465 and 465, respectively. Piston gate source ranges are absent in 1.11 and present at 464–500 in 1.11.1, 467–503 in 1.12 and 467–503 in 1.12.1. In 1.11, the method is `move(MoverType,...)` at line 455 and proceeds from the noClip branch directly into ordinary resolution.

## Scope and remaining prerequisites

This audit reuses the previously accepted 1.8.9, 1.9, 1.11.2, 1.12.2, 1.13 and 1.13.2 anchors without re-auditing them. Acceptance applies to these nine exact snapshots and does not establish release-line-wide coverage or a version cutover. The final AABB producer remains an upstream dependency and must be sourced separately for end-to-end reconstruction claims. No bounded source prerequisite remains open for this writer-call/formula claim. Product source/history, wiki inventories, backlog and primary research were not inspected or changed. No builds, tests, game/TAS/Gym/server launches, source generation or pushes were performed.

## Immutable input identity

The assigned candidate verdict was read-only and its SHA-256 matched the required pin: `CC8E975FBFBFDE62882DE0EB455B5D667327BDC5C7A78EA5258CBF190C31F4AC`.
