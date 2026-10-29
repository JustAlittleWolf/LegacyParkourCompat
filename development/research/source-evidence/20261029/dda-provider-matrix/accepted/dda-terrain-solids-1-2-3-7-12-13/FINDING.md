# 1.8.9 terrain solid ID and provider closure against 26.2

Scope: legacy block registry IDs 1 stone, 2 grass, 3 dirt, 7 bedrock, 12 sand, and 13 gravel. This closes identity, metadata normalization, and per-block collision-provider/bounds dependencies for these IDs. It does not establish DDA traversal order, provider visitation completeness, movement reachability, or parity for other IDs.

## Source identity

Exact 1.8.9 source is `ornithe-feather` and exact 26.2 source is `unobfuscated`; both canonical ready markers report `status: ready`. The source manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, respectively. The selected Java file hashes were recomputed and match their corresponding source manifests; exact hashes are in `source-hashes.json`.

## Legacy IDs, keys, and metadata

In 1.8.9 `net/minecraft/block/Block.java`, `Block.init` registers 1=`stone`, 2=`grass`, 3=`dirt`, 7=`bedrock`, 12=`sand`, and 13=`gravel` (lines 827–856). Thus the numeric IDs and serialized legacy keys are source-backed; this is not a same-name inference.

- **1 stone:** `StoneBlock` defines metadata 0 stone, 1 granite, 2 granite smooth, 3 diorite, 4 diorite smooth, 5 andesite, and 6 andesite smooth (lines 67–74). `getStateFromMetadata` uses `Variant.byId`; ids outside the seven enum values are normalized to id 0 (lines 53–54, 105–108). For the legacy four-bit metadata domain, 7–15 therefore decode as stone, rather than representing omitted variants.
- **2 grass:** `GrassBlock` sets no metadata-dependent collision state; inherited `Block.getStateFromMetadata` returns `defaultState` (Block.java lines 182–184). The class default is its only historical block state for this concern.
- **3 dirt:** `DirtBlock` defines metadata 0 dirt, 1 coarse dirt, 2 podzol (lines 81–84); `getStateFromMetadata` uses `Variant.byId`, whose out-of-range IDs normalize to 0 (lines 57–58, 119–124). Legacy metadata 3–15 decodes as ordinary dirt. The class default also sets `snowy=false`; only the podzol old state can participate in its old snowy-neighbor update (lines 24, 35).
- **7 bedrock:** registered as a plain `Block`, with no metadata override; inherited metadata decoding returns the default state.
- **12 sand:** metadata 0 sand and 1 red sand (lines 52–54); out-of-range values normalize to 0 (lines 38–39, 81–86). Metadata 2–15 decodes as sand.
- **13 gravel:** `GravelBlock` extends `FallingBlock`, then `Block`; no metadata override exists in either class, so metadata decodes to the default state.

The old state table and current exact identifiers are independently represented in 26.2 `util/datafix/fixes/BlockStateData.java`:

- Legacy stone variants map to current distinct keys `stone`, `granite`, `polished_granite`, `diorite`, `polished_diorite`, `andesite`, and `polished_andesite` (registers 16–22, lines 748–754). This table maps the old `stone` block's variant property into these current keys; do not preserve the legacy enum strings as modern keys.
- Legacy grass maps to current `grass_block` with `snowy=false` or `snowy=true` (register 32, line 755). The modern snowy property has no collision geometry effect in this bounded provider closure.
- Dirt, coarse dirt, and podzol map to current keys `dirt`, `coarse_dirt`, and `podzol`, with both modern snowy values represented where applicable (registers 48–50, lines 756–773).
- Bedrock maps to `bedrock` (register 112, line 793); sand and red sand map to `sand` and `red_sand` (registers 192–193, lines 858–859); gravel maps to `gravel` (register 208, line 860).
- The table lists the defined legacy variants. Legacy values falling outside a variant enum are normalized by the old state decoder as described above; they are not unhandled geometry variants. Negative metadata and values above the old four-bit domain are not valid legacy stored metadata and are outside this claim.

## Collision provider and ray path

All six legacy instances inherit `Block`'s constructor assignment of `[0,0,0]–[1,1,1]` bounds (`Block.java` lines 198–206). Their block classes contain no `setShape` or `updateShape` override, so no family-specific geometry or mutable-bound update is applied. Legacy `Block.addCollisions` obtains the current collision shape and adds it when the query `Box` intersects it (lines 339–350). That shape is constructed from the block's current bounds. Legacy `Block.rayTrace` calls `updateShape(world,pos)` before the block-specific six-plane ray calculation (lines 456 onward); for the scoped blocks that hook is the empty base implementation at lines 596–597. Consequently this family does not write mutable bounds through its own rayTrace provider path.

In 26.2, `BlockBehaviour` defaults ordinary states to `Shapes.block()` for outline shape and returns the state's shape as collision when `hasCollision` is true (lines 323–329). The `hasCollision` property defaults to true (line 985); `noCollision` is the opt-out (lines 1094–1097), and none of the scoped `Blocks` registrations opts out. `Block.isShapeFullBlock` classifies the resulting voxel shape (26.2 `world/level/block/Block.java`, line 88; `BlockBehaviour.java`, lines 335–337). The registrations for the stone variants, grass block, dirt, coarse dirt, bedrock, sand, red sand, and gravel provide ordinary properties without a no-collision option (`Blocks.java`, lines 56–92, 287–295, 324–347). Current `SandBlock` → `ColoredFallingBlock` → `FallingBlock` and `GrassBlock` → `SpreadingSnowyBlock` → `SnowyBlock` hierarchies contain no shape/collision overrides in the inspected classes; the inherited default applies. Thus all identified modern target states use a full unit-cube collision provider under the ordinary block collision route.

## Bounded result

For these six 1.8.9 IDs, a source-driven 26.2 provider candidate may recognize the mappings above and use the unit cube for collision bounds. Stone/dirt/sand metadata outside their declared variant set but inside the old 0–15 range must first normalize to each block's legacy default. The report closes only these provider/identity inputs; it does not assert which coordinates an old or modern ray traversal visits, order of visits, first-hit semantics, or completeness of the DDA provider family.

