# Pre-1.13 position reconstruction writer: omitted representative pins

Status: **COMPLETED — ACCEPT, narrowly scoped**

## Verdict

The earlier accepted position-writer applicability claim also holds in each assigned exact source pin: **1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.2, 1.11, 1.11.1, 1.12 and 1.12.1**. In the ordinary non-noClip path, each `move` method calls `setPositionFromShape()` after movement resolution at its normal terminal point, without a condition on the resolved displacement. A zero or tiny result therefore still reaches the writer if that ordinary path reaches its terminal point.

In every pin, the helper reads the final `Box` and assigns, in order, `x = (box.minX + box.maxX) / 2.0`, `y = box.minY`, `z = (box.minZ + box.maxZ) / 2.0`. There is no intermediate cast or additional arithmetic in this helper. The result depends on the final box alone; no requested delta, clipped vector or previous position is read by the helper. This confirms the writer's arithmetic and ordinary-path applicability for these snapshots. It does not establish that an upstream historical resolver produced that box.

`noClip` is an explicit separate branch that translates the box and invokes the same helper directly. In 1.9.2–1.10.2, the mover is `move(double, double, double)` and has no piston gate. In 1.11, the mover-type method still has no `MoverType.PISTON` gate. In 1.11.1 and 1.12/1.12.1, the `MoverType.PISTON` limiter runs before ordinary resolution and may return early: it accumulates/clamps the selected axis to `[-0.51, 0.51]`, returns when the adjusted component satisfies `Math.abs(component) <= 1.0E-5F`, and for the Z branch also returns when `z == 0.0`. These are piston-only gates; they do not guard the ordinary terminal writer. This confirms the difference between the sampled 1.11 and 1.11.1 sources, but does not establish the exact patch where it was introduced.

The conclusion is limited to the named snapshots. It does not establish release-line-wide coverage or a version cutover. The final AABB producer, including axis translations, step candidate selection and geometry/support inputs, remains an upstream dependency outside this assignment. A modern or otherwise incorrect box cannot be repaired by matching this writer formula.

## Exact source and provenance

Canonical source path for every row: `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`. Mapping identity: Feather Gen2 build 2. All file hashes below were recomputed and matched either the listed nested `sources` entry or the outer `ornithe-feather.sources.sha256` line. The 1.9.2, 1.9.3 and 1.10 sidecars record their per-version mergedv2 artifact hashes directly; the other mapping artifacts are recorded by the corresponding provenance and `artifacts.sha256` files.

| Version | Entity.java SHA-256 | Move terminal call | Helper lines | Feather mapping artifact SHA-256 (mergedv2 / tiny) |
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

The caller lines are the normal terminal call following `world.profiler.pop()` and `world.profiler.push("rest")`; the noClip call is in the earlier separate branch. The helper line ranges contain the complete three-coordinate formula. In 1.11.1, the piston-only gate is at `Entity.move(MoverType,...)` 464–500; corresponding method lines are 467–503 in 1.12 and 1.12.1.

## Acceptance boundary

Accept the ordinary final-box-to-position writer applicability and formula for the nine named exact snapshots. Retain the final resolved AABB as a required upstream input and the ordinary post-resolution invocation point as part of applicability. The piston early-return path is expressly excluded. No geometry, collision/step resolution, other tick inventory, product source/history, wiki material, backlog or primary research files were inspected or changed. No tests, build, launch or source-generation command was run.
