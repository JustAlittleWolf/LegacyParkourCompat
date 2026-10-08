# Creative-flight vertical input branch across later releases

Snapshot ID: `wiki-creative-flight-input-1.9.4-1.14.4`

Status: six-endpoint source comparison of the local-player creative-flight input slice. The input operation path changes at 1.14.4; the downstream movement consequence and full flight-state dependency closure remain open. This is not a whole-flight no-difference claim or exact snapshot-cutover claim. Runtime validation was not performed.

## Wiki lead and retrieval provenance

The [Flying page](https://minecraft.wiki/w/Flying?oldid=2732007) was opened on 2026-10-08 at oldid `2732007`; the fetch log notes its content was an older cached revision. Its reported flight/sprint/fluid leads are secondary. The source interval here begins at 1.9.4 because the older Wiki cutovers predate the 1.8.9 audit baseline and a prior bounded snapshot covers 1.8.9→1.9.4 flight behavior.

## Exact source and artifact bindings

For 1.9.4 through 1.13.2, use immutable canonical revised artifacts `feather-r1-2026-10-07`; their source-manifest ties and unavailable-original-JAR limitations are documented in [feather-r1-findings-snapshot.md](feather-r1-findings-snapshot.md). For 1.14.4, no r1 revised snapshot exists; the exact Feather ready marker and client input artifact bind the cited decompiled output.

| Version | `LocalClientPlayerEntity.java` SHA-256; flight branch lines | Bound client artifact SHA-256 |
|---|---|---|
| 1.9.4 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; 715-725 | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` (feather-r1) |
| 1.10.2 | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; 738-748 | `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b` (feather-r1) |
| 1.11.2 | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`; 750-760 | `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f` (feather-r1) |
| 1.12.2 | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; 772-782 | `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87` (feather-r1) |
| 1.13.2 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; 797-807 | `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c` (feather-r1) |
| 1.14.4 | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`; 728-743 | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` (exact ready input client JAR) |

The first five class digests match their canonical revised Feather source manifests; the 1.14.4 digest matches the `ready/1.14.4/ornithe-feather.sources.sha256` row. Original derived JARs for the five r1 versions remain unavailable, so equivalence to those originals is unproven. The six exact `LivingEntity.java` source files also match their corresponding source manifests; SHA-256 values are `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` (1.9.4), `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` (1.10.2), `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` (1.11.2), `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` (1.12.2), `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` (1.13.2), and `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` (1.14.4).

## Bounded source comparison

All six local-client slices use the gate `abilities.flying && isCamera()`. The 1.9.4, 1.10.2, 1.11.2, 1.12.2, and 1.13.2 branches execute the same operations in the same order: when sneaking, divide sideways and forward input by `0.3` and subtract `getFlySpeed() * 3.0F` from vertical velocity; when jumping, add `getFlySpeed() * 3.0F` to vertical velocity. The unrelated input and elytra code surrounding those slices is not included in this comparison.

In 1.14.4 the branch still divides the two horizontal input values by `0.3` while sneaking. It instead accumulates vertical input into integer `j` (`j--` for sneaking, `j++` for jumping), then performs one vector addition of `j * getFlySpeed() * 3.0F` only when `j != 0`. When both vertical controls are held, this branch skips the vertical vector write; the five earlier branches perform a subtraction followed by an addition. This is an exact source-level operation-path change. Its end-to-end velocity consequence depends on the incoming vertical value and arithmetic; this snapshot does not assert a measured displacement or runtime result.

The paired `LivingEntity.moveRelative()` branches preserve the player creative-flight exceptions to the water and lava travel paths in all six checked versions: when in either fluid, a flying player continues into ordinary movement. The cited source ranges are 1.9.4 lines 1302-1306, 1.10.2 lines 1332-1336, 1.11.2 lines 1388-1392, 1.12.2 lines 1425-1429, 1.13.2 lines 1486-1490, and 1.14.4 lines 1795-1799, with hashes in the source identity paragraph above.

## Remaining flight inventory

This checks six exact local input endpoints and the fluid-bypass guard. It does not close the ability-speed writer/synchronization path, camera predicate producer, flight toggle eligibility, all per-tick pre/post-travel writes, fall distance and flag-7 consumers, sprint-flight acceleration, cobweb behavior, collision providers, server/client synchronization, or the complete travel loop across those releases. The 1.14.4 branch change remains a source-path finding whose movement consequence still needs an exact arithmetic/state route. Do not generalize the five stable input branches to all flight behavior or all intervening patches.
