# F-06: The 1.11.2 query includes a lower-row moving-fence collision

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 903-965.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1022-1059.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 58-76.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {`, lines 960-1011.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 1013-1036.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1058-1060.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public void addCollisions(World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 318-346.

Difference: Both piston paths can place a movable fence state in a `MOVING_BLOCK` cell: `PistonBaseBlock.canMoveBlock` permits the registered oak fence when its ordinary border/height guards pass, and the push path stores the moved state in a `MovingBlockEntity`.

The oak fence uses `Material.WOOD` (whose default piston behavior is `NORMAL`), has no block entity, and has a collision post from local x/z `0.375` to `0.625` and local y `0.0` to `1.5`.

Use a moving-block cell at `(x,y,z)` with an extending piston facing WEST at progress `0.5`, and a player query box `[x+1.0,x+1.6] × [y+1.3,y+3.1] × [z+0.2,z+0.8]`.

The moved fence's progress offset is `WEST.offsetX * (0.5F - 1.0F) = +0.5`.

Its center collision post therefore occupies `[x+0.875,x+1.125] × [y,y+1.5] × [z+0.375,z+0.625]`, which strictly intersects the query box.

For that query, the moving-block cell is on the x perimeter (`o == i`) but its z coordinate is interior. 1.10.2's scan has `q == 1` and rejects the lower padded y row `r == k` with `q <= 0 || r != k && r != l - 1`. 1.11.2's shared helper reaches the same cell and admits that row because it rejects only the upper row, `q != l - 1`. 1.11.2 then calls the moving entity's collision provider, which translates the fence collision boxes by progress and appends the intersecting box.

In 1.10.2 the cell is not visited, so that candidate contributes no block collision.

This is independent of the retracting piston shape change in F-05.

Movement meaning: The source path is piston movement eligibility and moved-state storage -> `World.getCollisions` block-state dispatch -> `MovingBlock`/`MovingBlockEntity` collision boxes -> the list consumed by `Entity.move`. 1.10.2 locally controlled player reaches inherited `Entity.move` through the living-player travel path; the collision list is therefore a direct player movement input.

The pair's S4-piston and S5-fence slices cover the paired piston state path and fence post geometry. `Material.WOOD` has the default `NORMAL` move behavior, the base `hasBlockEntity` flag defaults false, and `canMoveBlock` admits the fence under valid height/world-border conditions.

Source proves that 1.11.2 supplies this collision box to the player query while 1.10.2 skips the candidate cell.

If the player is moving into the box, it can constrain the movement clipping result; no final position or observed trajectory is claimed.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-06: The 1.11.2 query includes a lower-row moving-fence collision; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(@Nullable Entity entity, Box shape) {",
      "lines": "903-965",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ae6f3ad90dc33b0833a6ebf1dc4546177d3e62e7385df02eb81c84bf8f8ae19c."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean getCollisions(Box shape) {",
      "lines": "1022-1059",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5f77e96e406206299a40031c3746876541420608d9c79cb03d1cb7196b565565."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "MovingBlockEntity#addCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "58-76",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7213ba1bd508ba54de8b77106a013027476f4c09ebd1c60f2277481bbacf2598."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {",
      "lines": "960-1011",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 7e6fc52e791970a1434bf0384cce5fafea6fab8e419852f612b517c8432fae55."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(@Nullable Entity entity, Box shape) {",
      "lines": "1013-1036",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 629c744b573939adc1cd2f676fcec026d55a13e6762adc01eb2d6ce2f36cf25f."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean getCollisions(Box shape) {",
      "lines": "1058-1060",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 0a28414321edc341d45591b03ad761b1f7519dc1440b32293f1c49e6ac2e7324."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public void addCollisions(World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "318-346",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 09cef9c347578bc13d18e525ac7e88e263f184ef56c01f5c80157c88fab37fcf."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {",
      "lines": "59-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-06-lower-row-moving-fence-collision is unknown within (1.10.2, 1.11.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-06-lower-row-moving-fence-collision",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-06-lower-row-moving-fence-collision",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
