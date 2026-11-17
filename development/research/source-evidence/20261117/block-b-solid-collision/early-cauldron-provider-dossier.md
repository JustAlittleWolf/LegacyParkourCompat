# Cauldron 1.8 provider alias and resolver review

Status: read-only product reconciliation. No product files, tests, builds, runtime state, or Git state were changed. Exact source review and accepted claim identities are pinned below; no broad internal-plane or five-box sequential clipping equivalence is claimed.

## Result

The V1_12 `CauldronCollisionShape` provider has no `supports` override; `BlockCollisionShapeHook.supports(selected)` defaults to `true`. Both provider instances for `minecraft:cauldron` and `minecraft:water_cauldron` therefore support every selected historical profile that resolves them. Its `collisionShape` returns the same constant five-box union without inspecting `state`, `level`, `pos`, or `context`.

Removing the V1_8_5 empty `CauldronCollisionNativeFence` is sufficient to let the V1_12 shape provider resolve for both aliases across the early profiles. Per alias, the resulting nearest-later selection is:

| Selected profile | Active cauldron shape hook | Reason |
|---|---|---|
| V1_8 (1.8) | V1_12 provider, if V1_8_5 fence is removed | Only eligible remaining cauldron provider is V1_12. |
| V1_8_1 (1.8.1–1.8.4) | V1_12 provider, if V1_8_5 fence is removed | Same. |
| V1_8_5 (1.8.5–1.8.8) | V1_12 provider, if V1_8_5 fence is removed | Same. |
| V1_8_9 (1.8.9) through V1_12 (1.12–1.12.2) | V1_12 provider | V1_8_5 is older than selected; V1_12 is closest eligible. |
| V1_13 (1.13) and V1_13_1 (1.13.1–1.13.2) | V1_13 provider | Its `supports` allows selected >= V1_13; it is the closest eligible provider. |

With the current product tree, the first three rows instead resolve V1_8_5 `CauldronCollisionNativeFence`, because resolver selection is per `(BlockCollisionShapeHook, variant)` key and picks the earliest `emulates` ordinal that is >= the selection. That class returns `Optional.empty()`, and the mixin only replaces vanilla on a present shape. Thus early profiles retain the native 26.2 shape, whose `AbstractCauldronBlock.SHAPE` includes lower cutouts; this misses their verified historical full-footprint 5/16 floor and full-height walls. The empty fence is the only earlier cauldron hook found in the product source.

## Call-path and context gates

1. `MovementChangeDiscovery` registers each factory instance for every implemented `@MechanicType` hook. `BlockCollisionShapeHook` is `@MechanicType("block.collision")`; each instance's default `variant()` is its exact `blockId()`. The two IDs are therefore two independent keys, and each version contributes one entry for each key.
2. `ChangeResolver.resolve` groups entries by key, filters `change.emulates() >= selected`, and takes the minimum enum ordinal. Removing the V1_8_5 entry exposes the V1_12 entry; no V1_8 candidate or later cauldron barrier supersedes it before V1_13.
3. `MovementRuntime.find(BlockCollisionShapeHook.class, state.getBlock(), player)` maps the runtime block through its registry ID and retrieves exactly that keyed provider. The mixin only proceeds for a `LocalPlayer` and `MovementRuntime.appliesTo(player)`. `appliesTo` additionally requires a client-side `Player` and an enabled historical profile. Server entities, other players, disabled/current mode, and calls without a player context retain native behavior.
4. The mixin filters `shape.supports(selected)`, calls `collisionShape`, and sets the return only when the `Optional` is present. V1_12 has default-true support and returns present; the V1_8_5 fence has default-true support but returns empty. V1_13 explicitly requires selected >= V1_13.
5. `BlockStateCollisionShapeMixin` obtains a player from the collision context or from the scoped historical empty-context player. Without either, it exits. The cauldron providers have no additional context, level, or state gates.

This establishes that deleting the V1_8_5 empty-fence class (including both variants) is enough for the V1_12 provider to serve V1_8, V1_8_1, and V1_8_5 profiles. No second cauldron alias registration or `supports` barrier exists in the reviewed product files.

## Source and accepted-claim bytes

The ten exact Feather `CauldronBlock.java` sources for 1.8, 1.8.1, and 1.8.2–1.8.9 were read directly. Every callback writes/tests base, west, north, east, south, resetting the mutable shape afterward. The base bounds are `[0,0,0]–[1,5/16,1]`; the walls have thickness `1/8` and full height. `LEVEL` does not select this collision provider. Actual source-file SHA-256 values were recomputed and, for 1.8–1.8.8, matched the `sources["net/minecraft/block/CauldronBlock.java"]` entries embedded in each mapping-local `.decompile-provenance.json`; 1.8.9 matched its version-level `.sources.sha256` manifest.

| Exact source | `CauldronBlock.java` SHA-256 | Provenance / manifest SHA-256 | Source location |
|---|---|---|---|
| 1.8 | `b69111377b6b72f2e86be0163287c89dfcff9dc9b71d2e4ee3cde6074a58c749` | `.decompile-provenance.json`: `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05` | `addCollisions` 35–47 |
| 1.8.1 | `dd008f83f8256d8761276c3f7964ad55e3ec20a46247075500fe4bc86c0fec84` | `.decompile-provenance.json`: `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` | `addCollisions` 36–48 |
| 1.8.2 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` | `addCollisions` 36–48 |
| 1.8.3 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` | `addCollisions` 36–48 |
| 1.8.4 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` | `addCollisions` 36–48 |
| 1.8.5 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `addCollisions` 36–48 |
| 1.8.6 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `addCollisions` 36–48 |
| 1.8.7 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `addCollisions` 36–48 |
| 1.8.8 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | `.decompile-provenance.json`: `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `addCollisions` 36–48 |
| 1.8.9 | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` | Version-level `ornithe-feather.sources.sha256`: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | `addCollisions` 36–48 |

The bounded 1.8.9→1.9 provider comparison and its independent audit are also fixed by these exact claim bytes:

- `development/research/source-evidence/20261117/checkpoint04/source-189-19-20261117/provider-residual/missing-rows.md`: SHA-256 `174cf90b0a97d7393a81482cf6fa57a2194023f213263f812d6a0d9804b97300`.
- `development/research/source-evidence/20261117/checkpoint04/source-189-19-20261117/provider-residual/missing-rows-audit/verdict.md`: SHA-256 `b477095566310976c3485298852ba9188287bead9655766a2cd967e25cc96f07` (ACCEPT for bounded six-row report; explicitly not pair-wide freeze).
- The canonical 1.12.2→1.13.2 finding records its remaining general clipping limitation: `development/research/findings/1.12.2--1.13.2/cauldron-collision-basin-depth.md`, SHA-256 `92783e6dcd84c72e8997967bddf25b3d565af59d3a66c0971b8a48471961a527`.
- The accepted conditional floor witness is `development/research/source-evidence/20261104/cauldron-floor-witness.md`, SHA-256 `a151e6ffeac018d41c3b866cdf004f700ee1fabaa12e986342a661852511503b`.
- The recent bounded provider summary is `development/research/source-evidence/20261117/block-b-solid-collision/bounded-provider-audits.md`, SHA-256 `7888f3b5e8b12e9eb4a843ef05069aabc72b86d660386573f25538b41479c471`.

Coordinator-supplied acceptance input for this task states that the independent reviewer’s exact source manifests now close cauldron basin coverage across all 1.8.0–1.8.9 releases. The per-release source/provenance hashes above were independently recomputed in this checkout. The independently reviewed manifest package itself was not separately present as a report in this checkout; no reviewer-file hash is invented here.

## Floor witness scope and parity limit

The accepted floor witness is deliberately narrow: a dry level-0 cauldron at `(0,0,0)`, normal 0.6F-wide player centered at `(0.5,0.5,0.5)`, airborne downward movement request `(0,-0.30,0)`, ordinary gravity, no step path, no flight/noClip and no interfering fluid, fire, border, unloaded query or other collider. The player's horizontal bounds stay strictly inside the wall opening, so the walls are avoided. It compares old 5/16 floor contact against 1.13.2's 4/16 floor contact and derives a 1/16 feet-position difference for that request.

That witness establishes the bounded floor outcome from the named old/new source paths. It does **not** establish that the product's `Shapes.or` union is clipping-equivalent for arbitrary requests to five separately appended and sequentially tested boxes. In particular, wall intersections, internal voxel planes, component order effects, and general per-axis/multi-block movement parity stay out of the accepted claim. Runtime parity remains unverified.

## Product and resolver source digests

| Product evidence | SHA-256 |
|---|---|
| `CauldronCollisionNativeFence.java` | `7feec17d800c97cffea1e5da6676a89c4606c25904c9b947ad1f018b356e2d6a` |
| `v1_12/CauldronCollisionShape.java` | `a22102cdaa3f520ee5e93b9630770b6a20b15dccee26fa07b305851bbb2f1055` |
| `v1_13/CauldronCollisionShape.java` | `62815190461a020bf5946e35366627e5f8376280c31a833bcb2238e81e2a5caa` |
| `BlockStateCollisionShapeMixin.java` | `9e8eaf7e4c1ef9dcb4e57c8631319ff2881bd44d94f4a6b29080545d05c3585c` |
| `BlockCollisionShapeHook.java` | `f35536ac132f2d6accaadd0c8c79f6ffd42cd79b5ab2ef47ad2a87cfa07d7ebb` |
| `MovementChangeDiscovery.java` | `985729cbfef21344018328f115d38676d841b154470ad5009d6edf9a68975a70` |
| `ChangeResolver.java` | `f9dd060f232bb3d34a58a55b792283a3aa01875b44e80fc454b9298075af8587` |
| `MovementControllerImpl.java` | `0409675535daa60f660441d2167833a44c027f2e276f2e45ce9567c98ca57b4e` |
| `MovementRuntime.java` | `682b10fd8f4c0581b5a8b0dc91fe3163aa7401fce0346397ce86060647d5bb81` |
| `ParkourVersion.java` | `04f35c28791cab6210851362c0e534085b37176f58bad14a341148ff4a413329` |
| 26.2 `AbstractCauldronBlock.java` native baseline | `a4e10cccf88b3c0d1cc64f33a06d8ad39c28f6b823483b910e71b9bd16191492` |

The 1.9.4/1.10.2/1.11.2/1.12.2 `CauldronBlock.java` file hashes matched their exact Ornithe source manifest rows, respectively: `84b346d09f7badf7821a43ef8a6a16fbcd161972ad67a8030c7574780ca6b205`, `84b346d09f7badf7821a43ef8a6a16fbcd161972ad67a8030c7574780ca6b205`, `8b95c69febce0002140c2f960cfa191737dd51dabf5ab99f7e74416dd382c0e7`, `0aff4a0de147e0c677f4b931cd9a881154666bb288f128c9d8041b70c38d63f9`. Their source methods/constants preserve the same five-box sequence; 1.11+ adds a `forceShape` parameter only.

## Recommendation

The minimal product correction is to remove the V1_8_5 empty barrier for both variants; the existing V1_12 provider then covers all 1.8.0–1.12.2 selections through normal closest-later resolution. This establishes the old occupied shape but does not close the separate union-versus-sequential-box clipping parity gate.
