# Fresh blind source review: F-PANE-OFFSET-EPSILON

- Review date: 2026-10-08
- Snapshot commit: `40e3347bea75280bbbcc3a51d6e10ea2263ff054`
- Author-final commit named by the request: `a7670819edd230c39fbc76de634435d1b3073c94`
- Snapshot path: `workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-PANE-OFFSET-EPSILON.md`
- Snapshot blob: `6a36eb3fb168715fb6f3c39ad12b7d1da6b2963d`
- Snapshot raw SHA-256: `e9d211203107cdf481245798fa76be6ee5dedfdab334629e447c5c46af7d631b`
- Decision: **REQUEST CHANGES**
- Pair status: **PARTIAL**

## Scope and artifact binding

Reviewed only the bound pane snapshot and its named 1.12.2/1.13.2 Ornithe Feather source and readiness material. Did not inspect implementation, wiki/MCPK material, run ledgers, unrelated findings, or runtime output. The snapshot's commit/path resolves to the supplied blob and raw SHA-256. The author-final commit resolves to the requested commit.

Both ready markers and their declared manifests agree with the files on disk. A marker SHA-256 is `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`, with source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and artifact-manifest SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. B marker SHA-256 is `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`, with source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and artifact-manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. All 28 cited source files independently rehashed in the paired ready trees match their manifest rows. The earlier marker mismatch was a transcribed digest citation issue; it is not present in the actual markers, and the prior review/history remains unchanged.

The source-provenance limit is real and is preserved: the verification records zero source-file and raw-input differences, but one derived remapped-JAR difference per endpoint. It records that the mapped JAR was regenerated during verification; the original A/B mapped JARs are unavailable, so equivalence to original mapped bytecode remains unproven. This review makes no bytecode-identity claim.

## Source-supported result

The corrected inheritance and registration evidence is consistent: 1.12.2 registers `minecraft:glass_pane` as `PaneBlock`; 1.13.2 registers it as `GlassPaneBlock`, which extends `IronBarsBlock`, which extends `PaneBlock`. With four AIR neighbors and no water, A resolves all four virtual connection flags false; B's iron-bars defaults and placement path also yield four false flags and `WATERLOGGED=false`. Both collision providers retain the center pane shape `[7/16,9/16]` in X and Z over the full block height. A's collector strictly intersects translated boxes; B's swept query collects the pane shape, then its X-axis `VoxelShape.calculateMaxDistance` computes the orthogonal Z voxel range with the stated `1.0E-7` insets. For the stated near-boundary Z position, the upper index remains below center voxel index 7, so the center voxel is not considered by B's X clip. Thus the source-supported qualitative result remains: A clips the positive X request at the pane while B permits the full request.

The client velocity/travel chain is source-supported on the stated client input: both packet handlers resolve the entity ID and assign packet components divided by `8000.0`; `Entity.lerpVelocity` writes the values; the local client player is locally controlled; the paired living-entity path preserves `0.003125` because the cutoff is strictly `< 0.003`; zero directional input does not alter it; and the cited ordinary air path passes current horizontal velocity to `Entity.move`. I treat receipt of that entity-velocity packet as the stated external movement input. The report does not demonstrate which vanilla server-side event produced the packet value, so this review does not elevate that separate sender question into a vanilla gameplay claim.

## Required correction: player bounds use float half-width

The snapshot derives its exact base box from width `0.6` but omits the position-construction operation that determines the actual bounds. In both endpoint `Entity.setPosition` methods, `float f = this.width / 2.0F` is evaluated before `new Box(x - f, ..., x + f, ...)` (A lines 298–305 and B lines 309–316; source SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` and `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`). With the default `width == 0.6F`, `f` is the widened float value `0.30000001192092896`, not the exact-double half-width `0.3` assumed by the snapshot.

For its stated position `(x,z) == (0.13749, 0.13750005)`, the source operations produce these bounds (decimal renderings of the resulting doubles):

- X: `[-0.16251001192092895, 0.43749001192092896]`, so A's positive-X clip is `0.00000998807907104382`, not `0.00001`.
- Z: `[-0.16249996192092894, 0.43750006192092894]`, giving pane overlap `0.00000006192092893986`, not `0.00000005`.
- B's upper orthogonal inset is `maxZ - 1.0E-7 == 0.43749996192092894`, still below the pane's `7/16 == 0.4375` boundary. Its center-voxel exclusion therefore still holds for this corrected box, and B still permits the full `0.003125` request.

The behavioral distinction survives, but the snapshot's asserted exact box, overlap, and A displacement do not follow from the cited default dimensions and reachable position. Correct the box ranges and recompute every dependent intersection/clip value from `Entity.setPosition`'s float operation, or provide a different source-reachable player box and position that produce the claimed values. Keep the source-level conclusion separate from any trajectory claim.

## Pair disposition and history

This is a bounded numeric-witness correction, not a rejection of the pane-shape difference. The earlier **REQUEST CHANGES** decision for the old snapshot remains attached to its immutable report and has not been rewritten. The current pair stays **PARTIAL**; this report does not close the complete player movement call graph, remaining dependencies, or runtime validation.
