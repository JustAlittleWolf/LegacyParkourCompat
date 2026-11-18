# Independent audit — pre-1.13 position writer applicability

Status: **COMPLETED — ACCEPT, narrowly scoped**

## Bound input

- Frozen proof: `c-external-pre113-writer/verdict.md`
- SHA-256: `6D117C72635BED3538C0D370DF84D7A71B439F5E4712686E6550E674BCEE354B` (matches assigned identity)
- Scope: ordinary zero/tiny dry final resolved-box write and invocation timing in sampled exact Feather sources 1.8.9, 1.9, 1.11.2, 1.12.2, 1.13 and 1.13.2.

## Independent result

The proof's narrow applicability claim is supported. In each sampled `Entity.move` body, after the initial `noClip` branch and, for mover-typed versions, after the `MoverType.PISTON` accumulator/threshold guard, the ordinary resolution path reaches an unconditional `setPositionFromShape()` at its normal terminal point. No resolved-displacement threshold guards that terminal call. Therefore a zero or tiny result from ordinary collision/step resolution still reaches this writer, assuming that upstream resolution reached its normal terminal point.

At that point the call follows the movement profiler pop/push and precedes collision flags; the subsequent movement code reads the newly written coordinates. The helper's order and arithmetic are exactly `x = (minX + maxX) / 2.0`, `y = minY`, `z = (minZ + maxZ) / 2.0`. The AABB fields and entity coordinates are doubles in the sampled sources; the expressions introduce no float cast or other intermediate operation. This writer consumes the final box, so the formula's result is determined by that box alone.

Branches excluded from this conclusion are explicit in source: `noClip` translates the box and writes position in its own branch; 1.11.2, 1.12.2, 1.13 and 1.13.2 have a piston-only early-return gate (including `Math.abs(component) <= 1.0E-5F`, and zero Z) before ordinary resolution. These do not qualify the normal-path invocation. The 1.8.9 and 1.9 `move(double, double, double)` paths have no mover-type piston gate.

## Exact source and provenance checks

All six files are local Feather decompilations at `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`. Recomputed file hashes match the supplied proof. Lines below use the local source files.

| Version / mapping | Entity.java SHA-256 | Call site / helper |
|---|---|---|
| 1.8.9 / Feather Gen2 build 2 | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `move` 371–545 (call 544); helper 638–642 |
| 1.9 / Feather Gen2 build 2 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `move` 438–623 (call 622); helper 719–724 |
| 1.11.2 / Feather Gen2 build 2 | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `move(MoverType,...)` 459–695 (call 693); helper 792–797 |
| 1.12.2 / Feather Gen2 build 2 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `move(MoverType,...)` 462–698 (call 696); helper 797–802 |
| 1.13 / Feather Gen2 build 2 | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `move(MoverType,...)` 473–678 (call 676); helper 781–786 |
| 1.13.2 / Feather Gen2 build 2 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `move(MoverType,...)` 473–678 (call 676); helper 782–787 |

Each version's decompile provenance identifies its exact version and Feather Gen2 build 2 artifact(s), and the Entity hash agrees with its canonical `ornithe-feather.sources.sha256` entry except 1.9. The 1.9 nested `.decompile-provenance.json` has a `sources` entry for `net/minecraft/entity/Entity.java` matching the recomputed hash and records the 1.9 Feather Gen2 build 2 mapped client input (SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`). This supplies direct source-body provenance despite that directory's missing outer manifest. The 1.13 sidecar uses its structured Feather mapping-artifact entries; the other outer provenance sidecars list the matching Feather mapping artifacts directly.

## Acceptance boundary and remaining gate

Accept only the writer's applicability prerequisite for the six named snapshots and the exact final-box-to-position arithmetic. This neither establishes release-wide or intermediate-patch coverage nor changes any version cutover claim. The historical shape/support/step/piston geometry supplier remains an independent dependency: an implementation must supply the historically resolved final AABB and invoke this writer at its historically applicable point. This audit did not compare or accept that geometry supplier, new discovery, implementation behavior, or runtime parity.

No product sources/history, wiki material, backlog or shared research were consulted or changed. No build, test, launch or source-generation command was run.
