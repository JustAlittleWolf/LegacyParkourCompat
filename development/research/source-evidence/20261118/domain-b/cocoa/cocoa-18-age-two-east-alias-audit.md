# Independent source audit: cocoa age-two EAST alias (1.8–1.8.8)

## Verdict: ACCEPT

The frozen draft `cocoa-18-age-two-east-alias.md` was reviewed at SHA-256 `bcedc6260c0f2867455bf40f14f544eb1186b98e3a97c58dea51e4672fc69878`. Its bounded claim is supported for the nine assigned exact releases. This accepts only the stated EAST age-two cocoa collision witness and source alias through 1.8.8; it does not establish other ages, directions, witnesses, independent pair coverage, or runtime parity.

## Identity and byte checks

For each assigned `decompiled_minecraft/<version>/ornithe-feather` tree, the `.decompile-provenance.json` SHA-256 matches the draft's listed manifest identity. In each manifest, the pinned Feather Gen2 `build.2` Maven publication URL and SHA-256 match the draft's version-specific publication identity. The local SHA-256 for each audited CocoaBlock, Block, World, Box, Entity and LivingEntity source file also matches that file's entry in its version's provenance `sources` map.

The CocoaBlock source is byte-identical across 1.8–1.8.1 (SHA-256 `af7d10295415c50debb68d7c35363ff081b030e9eb9e0ed4c5c122d37f0f00dd`) and across 1.8.2–1.8.8 (SHA-256 `d4e3c6b53fd33b3ff5e433b333511d93cbb83dbc0bdfb5690efbef734c5542e9`). Thus the two inspected representatives, 1.8 and 1.8.2, plus the verified byte groups cover the intervening assigned releases without assuming same-name methods imply same behavior. The source identity table and CocoaBlock hashes in the draft are accurate.

## Bounded behavior retraced

- `CocoaBlock.isSupported` offsets by the state's facing and checks `Blocks.LOG` with jungle planks variant. East-facing cocoa at `(0,0,0)` therefore has the stated jungle log support at `(1,0,0)`. In 1.8–1.8.1 the enum is `PlanksBlock__Variant.JUNGLE`; the later source renames it to `PlanksBlock.Variant.JUNGLE` without changing the predicate.
- `getCollisionShape` calls `updateShape` before the inherited box construction. For age 2, `j=4+2*2=8`, `k=5+2*2=9`, and `f=j/2=4`. The EAST branch produces local bounds `[7/16,3/16,4/16]–[15/16,12/16,12/16]`, i.e. `[.4375,.1875,.25]–[.9375,.75,.75]`. The EAST formula, collision-shape call order, and support predicate are unchanged within each verified CocoaBlock byte group.
- For the frozen player box `[-.2,0,.2]–[.4,1.8,.8]` and requested `(dx,dy,dz)=(.1,0,0)`, the swept collision query extends to `x=.5`, overlaps cocoa on Y and Z, and includes the cocoa block position in the ordinary world collision scan when its chunk is loaded. `World.getCollisions` calls `Block.addCollisions`; the latter obtains `getCollisionShape` and inserts it when the query intersects the shape. The cocoa west face at `.4375` is reached by the query.
- `Box.intersectX` sees positive X motion with player max X `.4` at or west of the cocoa min X `.4375`; it clips the requested X amount to `.4375-.4=.0375`. This is fixed-request collision evidence only; it does not assert how keyboard input derives the request or its velocity.
- `Entity.move` performs Y, then X, then Z clipping. In the 1.8, 1.8.1 and 1.8.2 bodies, the ordinary step retry gate is positive `stepHeight`, grounded (`onGround` or downward-Y clipping), and a changed clipped horizontal component. The human-living default remains `0.6F`. The candidate step rise leaves the player bottom at `.6`, below cocoa's top `.75`, so Y/Z overlap remains and its positive X request is clipped by the same cocoa west face to `.0375`. Each inspected body compares candidate horizontal squared distance against the already-clipped ordinary result and restores the ordinary result when it is equal or better (`>=`); the stepped candidate therefore does not improve this witness. Although the movement body changes between these releases, these specific gates and comparisons remain equivalent for this setup; the claim does not say the full movement methods are identical.

## Scope limit

The witness assumes the stated box and grounded state, a loaded ordinary world collision query at the cocoa block, and the direct positive-X movement request. The review covers the assigned exact versions 1.8 through 1.8.8 only. It does not infer an input-to-velocity value, generalize to other cocoa states or collision environments, or claim runtime parity.

