# Independent acceptance: early 1.8 Glass alias

## Verdict

**ACCEPT.** For the frozen vanilla plain-Glass witness only—non-source moving block, EAST extension, `progress=1.0F`, `lastProgress=0.5F`, owner cell `(0,0,0)`, ordinary in-border loaded-cell collision scan, with no nested piston move or direct entity-push route—the 1.8 behavior aliases across 1.8.1 through 1.8.8. I found no source-supported 1.8.x patch exception for this witness.

**Authoritative alias:** 1.8 for this bounded moved-Glass collision-shape/collision-scan operation through 1.8.8. This verdict does not extend the alias to other states, directions, phases, routes, or versions. The separately accepted 1.8.9 endpoint witness remains its own acceptance.

## Reviewed identities

- Candidate audit: `domain-b-moved-glass-alias-source/early-18-alias-audit.md`, SHA-256 `342EA1162226E2C0CE3B9DD2ACEB58E120196A3D054790B76A4BE552403CC0FA`.
- Frozen endpoint witness: `domain-b-moved-glass-source/moved-glass-east-positive-plate.md`, SHA-256 `CAB075C9B3E03281F90A58C06B9ADDBF231967206435F53BD183C7A24FB62699`.
- Read-only canonical inputs: `decompiled_minecraft/<version>/ornithe-feather/` for each exact version 1.8 through 1.8.8. Each provenance-manifest SHA and each of the ten selected source-file SHA-256 values was checked against the candidate's identity table and that manifest's source map. All nine manifests and all 90 source-file pins matched.

## Independent source checks

- **Glass registration and provider:** `block/Block.java` registers plain Glass as `new GlassBlock(Material.GLASS, false)` in every patch (1.8 line 808; 1.8.1 line 819; 1.8.2 onward line 863). `GlassBlock` still inherits `TransparentBlock` without overriding movement shape/collision. The registration's source location changes with source organization, not its Glass type/material/flag.
- **Inherited full box:** the checked base constructors initialize bounds to `(0,0,0)-(1,1,1)`. `Block.getCollisionShape` constructs the world-space box from those bounds; `Block.addCollisions` appends only a non-null box intersecting the query. I checked the exact method bodies in 1.8, 1.8.1 and 1.8.2; 1.8.2–1.8.8 source files are byte-identical for this dependency.
- **Piston state and movement eligibility:** `Material.GLASS` retains the default `pistonMoveBehavior` value 0; the block getter forwards it. `canMoveBlock` rejects behavior 2 and conditionally rejects behavior 1, leaving Glass eligible under the ordinary extension gates. The resolver and common extension `move(..., extend=true)` route preserve the moved Glass state, EAST facing and moving-block entity production. Sticky extension changes the head type only for this witness. Retraction is not asserted.
- **Moved collision geometry:** `MovingBlock.getCollisionShape` reads entity progress with tick delta `0.0F`, converts extension progress to `1-progress`, asks the moved Glass for its base collision box, and for EAST subtracts progress from the positive X maximum. With the fixed interpolated phase, the resulting plate ends at max-X `0.5`. The relevant `MovingBlock` and `MovingBlockEntity` source files are byte-identical from 1.8.2 through 1.8.8; the 1.8/1.8.1 variants were checked in source.
- **Ordinary world scan:** the query bounds `[0.6,1.7] × [0.2,2.0] × [0.2,0.8]` produce scan indices X `0..1`, Y `-1..2`, Z `0`; cell `(0,0,0)` is included. The 1.8.1/1.8.2 refactor replaces per-cell border checks and fresh positions with cached border state and a mutable position, but retains the floor/minimum and `floor(max+1)` loop bounds, loaded-chunk gate, state lookup for this in-border fixture, and `addCollisions` dispatch. The 1.8, 1.8.1 and 1.8.2 scan bodies were checked; 1.8.2–1.8.8 `World.java` is byte-identical.

The early source changes are real organization/constructor and scan refactors, but I found no change to plain Glass registration semantics, its inherited full-box collision provider, normal piston eligibility/state production, the EAST moved-shape result, or inclusion of the frozen fixture cell. This accepts only source alias safety; it makes no runtime-parity claim.
