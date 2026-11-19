# Pre-1.13 position reconstruction writer applicability audit

Status: **COMPLETED — ACCEPT, narrowly scoped**

## Verdict

The accepted `1.16.5--1.17.1/player-position-reconstruction-rounding` writer distinction applies to the sampled earlier Feather sources, including 1.8.9, 1.9, 1.11.2, 1.12.2, 1.13 and 1.13.2. In each sample, the ordinary (non-no-clip, non-piston-special-return) path calls the position reconstruction unconditionally after collision/step resolution. There is no squared-displacement threshold at the writer call site. Thus a zero or tiny clipped result still reaches the same box-to-coordinate formula, provided the earlier resolution path reaches its normal terminal point.

The writer reads the final `Box` only and performs, in order, `x = (minX + maxX) / 2.0`, `y = minY`, `z = (minZ + maxZ) / 2.0`. Box coordinates and entity position fields are doubles; there is no float cast or other intermediate operation in the formula. This matches the accepted pre-1.17 writer arithmetic. No historical input delta, clipped vector or previous coordinate is needed to compute these outputs.

This closes only the writer's applicability prerequisite. Its input must be the historically resolved final AABB and the operation must run at the historical post-resolution point. Axis translations, step candidate selection and the resulting box are upstream dependencies outside this audit; a modern box does not establish those historical results. The 1.11.2, 1.12.2 and 1.13 methods also have piston-specific early returns before collision resolution (for example, accumulated movement under `1.0E-5F`); that distinct mover-type path is excluded here. The no-clip branch calls the same helper separately and is not part of the ordinary-path conclusion.

## Exact source evidence

All inspected files are canonical local Feather sources under `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`. File hashes below were recomputed. They match the corresponding root `ornithe-feather.sources.sha256` entry, except 1.9 where its nested `.decompile-provenance.json` `sources` entry matches directly. Mappings are Feather Gen2 build 2.

| Version / mapping identity | Source SHA-256 | Relevant source |
|---|---|---|
| 1.8.9 / `feather-gen2-1.8.9+build.2` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `Entity.move` 371–636; terminal helper call 542–545; `setPositionFromShape` 638–642 |
| 1.9 / `feather-gen2-1.9+build.2` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `Entity.move` 438–717; terminal helper call 620–623; `setPositionFromShape` 719–724 |
| 1.11.2 / `feather-gen2-1.11.2+build.2` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `Entity.move(MoverType,...)` 459–790; terminal call 691–695; `setPositionFromShape` 792–797 |
| 1.12.2 / `feather-gen2-1.12.2+build.2` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `Entity.move(MoverType,...)` 462–795; terminal call 694–698; `setPositionFromShape` 797–802 |
| 1.13 / `feather-gen2-1.13+build.2` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `Entity.move(MoverType,...)` 473–779; terminal call 674–678; `setPositionFromShape` 781–786 |
| 1.13.2 / `feather-gen2-1.13.2+build.2` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `Entity.move(MoverType,...)` 473–779; terminal call 674–678; `setPositionFromShape` 782–787 |

For each ordinary path, the helper invocation follows the profiler's movement section and precedes collision flags and the next block-position-dependent movement response. In all six method bodies, this call is outside a condition on the resolved displacement. Earlier `noClip` handling is a distinct branch that translates the box and calls the helper directly. In the `MoverType.PISTON` versions, the limiter may return before the movement section; ordinary movement that continues through resolution reaches the terminal call regardless of whether the resulting displacement is zero or tiny.

The accepted 1.13/1.13.2 Feather comparison reports the same helper formula and unconditional ordinary terminal call. The supplied independent early-applicability audit also records the 1.13 and 1.14 boundary and the modern native position-write gate. This audit extends the earlier side to the four assigned pre-1.13 snapshots; it does not alter that earlier verdict or establish release-wide coverage or an intermediate introduction boundary.

## Provenance notes

1.8.9 provenance identifies `1.8.9`, Feather mapping artifacts `feather-gen2-1.8.9+build.2-{mergedv2.jar,tiny}`, and an exact decompile invocation; source hash matches its local manifest. 1.11.2 and 1.12.2 provenance similarly identifies their requested version, Feather build 2 artifacts and exact run; hashes match manifests. 1.13 and 1.13.2 exact version identities and Feather build 2 mapping artifacts are recorded in local provenance and hashes match manifests.

The 1.9 directory has no outer source manifest. Its nested `.decompile-provenance.json` records the 1.9 client jar and metadata hashes, Feather mapping URL `.../feather-gen2/1.9+build.2/feather-gen2-1.9+build.2-mergedv2.jar` (SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`), and a `sources` hash for this Entity file matching the recomputed SHA above. That nested source-level identity is sufficient for this bounded local source-body check, though it differs in completeness/schema from the outer provenance sets.

No product sources, product Git history, wiki material, backlog, shared research files or source-generation commands were consulted or changed. No tests, build or launch were performed.
