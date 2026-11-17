# Fixed-route step solver: exact-source review

Verdict: **ACCEPT** for the exact Mojmap releases 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, and 1.20.6, within the bounded scenario below. This accepts the historical solver alias across these inspected releases; it does not establish uninspected releases or the exact patch where the routine first appeared.

## Scope

Ordinary loaded, border-clear, entity-free player step query. Vanilla block states and the native block collision shapes are used. Excludes world-border contact, entity collisions, unloaded chunks, special block-state behavior, and other movement paths.

## Evidence identity and source integrity

All paths are relative to `decompiled_minecraft/<version>/mojmap/` in the primary checkout. Mojmap means official Mojang client mappings. Each row identifies the local immutable publication marker and the SHA-256 of `net/minecraft/world/entity/Entity.java`; the solver-body hash repeats where the exact file is byte-identical.

| Release | Source identity marker and SHA-256 | `Entity.java` SHA-256 |
| --- | --- | --- |
| 1.19.4 | `mojmap.sources.sha256` `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` (manifest hash verified against `mojmap.ready.json`) | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| 1.20 | hidden `.decompile-provenance.json` `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.1 | `mojmap.sources.sha256` `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` (manifest hash verified against `mojmap.ready.json`) | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| 1.20.2 | `mojmap.sources.sha256` `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` (manifest hash verified against `mojmap.ready.json`) | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| 1.20.3 | hidden `.decompile-provenance.json` `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.4 | `mojmap.sources.sha256` `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` (manifest hash verified against `mojmap.ready.json`) | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| 1.20.5 | `mojmap.sources.sha256` `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` (manifest hash verified against `mojmap.ready.json`) | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | `mojmap.sources.sha256` `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` (manifest hash verified against `mojmap.ready.json`) | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |

The hidden provenance records for 1.20 and 1.20.3 bind the exact Mojang client jar, client mappings, version metadata, decompiler pipeline, runtime/tool inputs, and per-source SHA-256 hashes. For 1.20, the recorded artifact hashes are client JAR `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, client mappings `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and `1.20.json` metadata `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`. For 1.20.3, they are client JAR `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, client mappings `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, and `1.20.3.json` metadata `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`.

Each inspected `Entity.java` hash agrees with its exact source manifest/provenance. Consequently, the solver body for 1.20 is byte-identical to 1.20.1; 1.20.3 is byte-identical to 1.20.4; and 1.20.5 is byte-identical to 1.20.6. The required supporting source hashes also match the source manifests/provenance:

| Release range | `EntityGetter.java` SHA-256 | `CollisionGetter.java` SHA-256 | `BlockCollisions.java` SHA-256 |
| --- | --- | --- | --- |
| 1.19.4 | `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` | `11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127` |
| 1.20–1.20.1 | `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| 1.20.2–1.20.6 | `f7fa5641d3287d5084f4450e1c09cd4d5816c5cff5412c6f07b14779bb60215f` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |

## Members and behavior

Exact members compared:

- `net/minecraft/world/entity/Entity.java`: `move(MoverType, Vec3)` (1.19.4 line 549; 1.20 line 593; 1.20.1 line 593; 1.20.2 line 595; 1.20.3 line 596; 1.20.4 line 596; 1.20.5–1.20.6 line 597), `collide(Vec3)` (1.19.4 line 801; 1.20–1.20.1 line 870; 1.20.2 line 872; 1.20.3–1.20.4 line 873; 1.20.5–1.20.6 line 874), `collideBoundingBox(...)`, and `collideWithShapes(...)`.
- `net/minecraft/world/level/EntityGetter.java`: default `getEntityCollisions(Entity, AABB)`.
- `net/minecraft/world/level/CollisionGetter.java`: default `getBlockCollisions(Entity, AABB)` and shape-combination path.
- `net/minecraft/world/level/BlockCollisions.java`: constructor and `computeNext()` block-shape iteration.

The normal collision candidate clips the requested vector against the entity’s initial swept box, unless requested length-squared is zero. A step attempt is gated by `maxUpStep() > 0`, grounded status or clipped negative Y, and clipped horizontal movement. Candidate A is a single collision query for `(requestedX, maxUpStep, requestedZ)` from the original box. Candidate B first queries `(0, maxUpStep, 0)` against the horizontally expanded box; only if this clipped rise is below `maxUpStep` does it query `(requestedX, 0, requestedZ)` from the vertically moved box and add the rise. B replaces A only on strict greater horizontal-distance-squared. A step route replaces the normal candidate only on the same strict `>` comparison, so equal horizontal distances preserve the prior candidate. The returned step vector adds a final collision-clipped vertical query `(0, -stepY + requestedY, 0)` from the box moved by the selected step vector.

The caller and native block shapes use the same operation in every accepted release for this bounded scenario. `EntityGetter.getEntityCollisions` is byte-identical across all eight releases; the entity-free query therefore supplies an empty entity-shape list. The scalar `collideBoundingBox` call combines that list with the queried block collision shapes before `collideWithShapes` applies Y then horizontal axis clipping, ordering the smaller-magnitude horizontal axis first and preserving its strict comparison/tie behavior. Border and loaded-chunk edge conditions are excluded. `CollisionGetter`/`BlockCollisions` changed API/generic structure after 1.19.4; their selected loaded block collision shapes and iteration still feed the same native scalar clipping semantics in this scope.

## Review boundary

This is independent source acceptance for the named solver behavior and bounded applicability only. It does not establish runtime parity, all movement tick coverage, any exact introduction boundary between these releases, or behavior for excluded collision conditions.