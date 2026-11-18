# Source-only alias review: 1.11–1.12 cauldron component clip

## Verdict

**Qualify the bounded 1.8.9 cauldron horizontal-clip finding through each exact release 1.11, 1.11.1, 1.11.2, 1.12, 1.12.1 and 1.12.2.** The historical side preserves the same five collision components and their emission order, the same strict orthogonal X/Y tests in the Z clipper, and the same low-canopy step fallback that makes the final displacement differ on Z. The 1.11.1 `forceShape` parameter does not change this cauldron: its implementation ignores the flag, and the ordinary world collector passes `false` while still receiving all five components.

This is a bounded source-alias conclusion for this operation and setup. It does not establish a complete movement comparison, an intermediate patch introduction boundary, runtime parity, or modern-side behavior beyond the already-audited fixture.

## Fixture anchor and evidence identity

Anchor: `review-cauldron-component-clip-observability/verdict.md`, SHA-256 `51456db253a63c257acfb32bf1156d4f1ddb9721dde5164a30533193c5aa05c8`. Its accepted claim compares the five-box 1.8.9 clip kernel against the equal-union 26.2 kernel and requires the top slab at `(1,2,0)` to suppress the old step-up route. It corrects the player width endpoints to the actual `float 0.6F` width: center X `1.299999961920929` gives minX about `0.99999995` and maxX about `1.5999999738418579`.

The exact release roots inspected were `decompiled_minecraft/<version>/ornithe-feather/`. Release IDs and Feather mapping inputs were read from each root's visible or hidden sidecars (`-Force`). For 1.11, 1.11.1, 1.11.2 and 1.12.2, the selected source-file digests match `ornithe-feather.sources.sha256`; ready/provenance markers identify those trees. For 1.12 and 1.12.1, the version roots have no visible ready/provenance sidecars: their hidden `ornithe-feather/.decompile-provenance.json` contains exact client, Feather metadata/artifact input pins and per-source hashes; its SHA-256 is respectively `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. In all six versions, current CauldronBlock, Entity, Box, World, Block, LivingEntity and SetBlockCommand hashes matched their recorded source manifests.

| Release | Feather mapping inputs | source-manifest SHA-256 |
|---|---|---|
| 1.11 | Feather Gen2 build.2; mapped JAR `658ad8c5…dec44`, Tiny `46bd6702…9c0` | `8b3e5da5…a59ca` |
| 1.11.1 | Feather Gen2 build.2; mapped JAR `83c2f497…d2928`, Tiny `4fa160c0…9c36e6` | `0d5f4697…3066d` |
| 1.11.2 | Feather Gen2 build.2, exact `1.11.2` tree/ready marker and artifact sidecar | `d908c2af…73df0` |
| 1.12 | Feather Gen2 build.2, inputs pinned inside hidden provenance | hidden source map in provenance `27515e5f…01467` |
| 1.12.1 | Feather Gen2 build.2, inputs pinned inside hidden provenance | hidden source map in provenance `0ff5246e…a9c8` |
| 1.12.2 | Feather Gen2 build.2, exact `1.12.2` tree/ready marker and artifact sidecar | `b8a37ccf…949da` |

The abbreviations in the table are display-only; the full exact identities are in the source roots. Selected operation-file hashes verified against those manifests are: 1.11 `CauldronBlock` `88064895…a5878`, `Entity` `3209feaa…4fd97`, `Box` `f788b814…66f3a`, `World` `f6490588…0ff47`; 1.11.1/1.11.2 `CauldronBlock` `8b95c69f…2c0e7`, `Entity` differs between those patch versions, `Box` remains `f788b814…66f3a`, `World` `27cfaa5f…efb58`; all three 1.12 releases have identical `CauldronBlock` `0aff4a0d…d63f9`, `Entity` `80f091bf…94a0a`, and `Box` `f788b814…66f3a` hashes. These are source-hash checks, not a claim that all files in each release are identical.

## Exact source trace

### Collision components and floor state

Across 1.11 through 1.12.2, `net/minecraft/block/CauldronBlock.java:35–39` defines:

- floor/base: `[0,0,0]–[1,0.3125,1]` (5/16 block high);
- north `[0,0,0]–[1,1,0.125]`;
- south `[0,0,0.875]–[1,1,1]`;
- east `[0.875,0,0]–[1,1,1]`;
- west `[0,0,0]–[0.125,1,1]`.

`CauldronBlock.addCollisions` at `:47–52` appends them in this order: base, west, north, east, south. The level state is not read by this method; all four cauldron levels therefore emit the same boxes. There is **no cauldron floor-depth or level-dependent collision change** in any of the six inspected releases. `getShape` returns the full-block outline at `:55–57`, distinct from the collision components.

For 1.11 exactly, `addCollisions` takes `(…, Entity entity)`; 1.11.1 adds `(…, Entity entity, boolean forceShape)`. The flag is unused by the cauldron body. In 1.11.1 onward the ordinary `World.getCollisions` collector calls `BlockState.addCollisions(..., entity, false)` (`World.java` around `:997` in 1.11.1/1.11.2 and `:1001` in 1.12.x). Thus the SELF move's normal list contains the same emitted components even when the flag is false. `Block.addCollision` translates each box by the block position and appends it when the swept/query AABB intersects it (`Block.java:353–362` in 1.11; `:372–381` in 1.12).

### Strict Z clip and low-canopy fallback

`net/minecraft/util/math/Box.java:225–247`, `intersectZ`, has identical source SHA `f788b814…66f3a` across all six releases. It first requires strict overlap on X and Y using the negated `<=`/`>=` separation tests. For positive Z it clips only when the moving box's `maxZ <= obstacle.minZ`, then applies the nearer `obstacle.minZ - box.maxZ` limit. Consequently the fixture's 5e-8 overlap with the east wall remains an X overlap (`minX ≈ 0.99999995 < east.maxX 1`); the east wall does not clip +Z because it already spans the player's Z. The later south wall starts at Z 0.875 and clips a +0.2 request from maxZ≈0.8 to +0.075.

Each release's `Entity.move` collects once, clips Y, X, then Z over that ordered list (`Entity.java`, 1.11 lines about `538–555`; 1.11.1/1.11.2 about `579–596`; 1.12.x about `582–599`). The step alternative is gated on positive `stepHeight`, grounded or downward movement, and a horizontal clip (`Entity.java`, 1.11 about `560–617`; 1.11.1/1.11.2 about `601–658`; 1.12.x about `604–661`). `LivingEntity` sets `stepHeight=0.6F` (1.11 `LivingEntity.java:172`; 1.12.x `:175`). At the fixture's feet Y=0.5, an unobstructed 0.6 rise can clear the wall top at Y=1, so the known fixture correctly needs a ceiling.

With the slab at `(1,2,0)`, its collision begins at Y=2.5 while the player's initial body top is 2.3. The allowed 0.2 rise is below the 0.5 rise needed to clear the wall. The old step candidates therefore retain the +0.075 Z clipping; their horizontal squared gain ties the normal leg, and the source's `q*q + s*s >= x*x + z*z` test restores the normal clipped movement on equality (1.11 `Entity.java` around `:651`; 1.11.1/1.11.2 around `:692`; 1.12.x around `:695`). For the equal-union modern fixture already audited at 26.2, the X-sampling interval is empty and +0.2 Z remains unblocked. Under those gates the full-move Z positions differ, while net Y is zero. Without the canopy, only the direct clip-kernel result is established; the old step alternative can leave a Y-only final difference.

## Legal creation and setup gates

`SetBlockCommand.getRequiredPermissionLevel()` is 2 in each release. Its `run` path requires at least four arguments, parses the block state, rejects an unloaded target chunk, then writes via `World.setBlockState(blockPos, blockState, 2)`; no entity-collision validation occurs in this path (1.11/1.11.1/1.11.2 and 1.12/1.12.1/1.12.2 `server/command/SetBlockCommand.java:27–30, 39–57, 87–95`). Therefore an authorized operator can lawfully create the cauldron at `(0,0,0)` after placing the bottom slab at `(1,0,0)`; both targets and the collision query fit in the loaded origin chunk. `/tp` is likewise an explicit command route to set a server player's position (`TeleportCommand.java:69–104`); this review does not claim natural walking reachability.

The supported trace requires the exact boundary-overlap position and the stationary/player state to survive until the immediate movement request. Do not insert a player movement tick between block placement and request. I found no automatic cauldron push-out in the checked Entity/player source paths for these versions. `World.getCollisions` only queries loaded chunks; an unloaded target is rejected by `/setblock`, and an unloaded queried block must not be assumed to contribute the cauldron component.

## Scope boundary

This review independently extends the historical source-side conditions of the anchored clip finding to the six named Feather releases. It does not add a separate floor-depth finding, validate a broad patch range beyond those exact releases, establish which intermediate patch first introduced any change, compare modern source again, or establish runtime parity.


