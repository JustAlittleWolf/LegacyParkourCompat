# Inside-block callback eligibility closure: 1.21.2/1.21.3 vs 26.2

## Scope and result

Bounded read-only closure for the old per-position block callback admission gate used by the ordinary locally controlled player after travel. Exact endpoints: 1.21.2 Mojmap and 1.21.3 Mojmap (the latter is inherited through the accepted byte-identical `.2/.3` source closure cited below), compared with 26.2 unobfuscated. This closes the previously open query/shape-eligibility dependency only; it does not revisit the accepted swept-box arithmetic, query-box construction, DDA candidate geometry/order, 17-clip limit, callback bodies, or runtime parity.

**Result:** an implementable historical visitor can preserve the `.2/.3` admission contract under the same live client block-state assumption: after the already established candidate traversal yields a position, read its current state; skip air; reserve a non-air position in the sweep-wide visited set before testing eligibility; get the source-era entity-inside shape for that current state; admit unconditionally only when the returned object is the `Shapes.block()` singleton, otherwise require the historical `collidedWithShapeMovingFrom(from,to,pos,shape)` predicate against the shape's translated `toAabbs()`. A rejected partial shape remains visited for the rest of that sweep. On admission, retain the separately accepted synchronous block callback order. Do not inject 26.2 swept-fluid callbacks.

This is a conditional same-state contract, not a claim that the two endpoint releases enumerate the same positions or have identical candidate sequences. Their previously audited query box, DDA traversal, reverse-sweep geometry, callback order, and movement-record inputs remain separately governed.

## Immutable source identity

Canonical, read-only root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- 1.21.2 Mojmap `.decompile-provenance.json`: SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. This legacy provenance embeds source hashes and records the 1.21.2 metadata input (`793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`) and Mojang `client.txt` mapping input (`e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). Actual selected source hashes below match the embedded records. The established 1.21.2 Mojmap identity and its `.2/.3` aliases are also bound by `.native-source/20261029/partial-sweep-1212-alias-closure/report.md` (SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`) and accepted independent audit `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md`.
- 26.2 unobfuscated parent provenance: SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; ready marker: `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; source manifest: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The selected actual file hashes below were checked against that manifest.
- The two prior accepted closures reused here are `.native-source/20261030/callback-order-audit/report.md` (SHA-256 `db064c1dee695a4c51e0b7c1f5f207149fd6b673686ce00fab3462841abfcdc9`) and `.native-source/20261030/dda-collector-flush-audit/audit.md`; they bind callback order and fluid-injection distinctions, while expressly leaving this geometry/eligibility closure open.

Selected full-file SHA-256 values (1.21.2 Mojmap; 26.2 unobfuscated):

| Source | 1.21.2 | 26.2 |
| --- | --- | --- |
| `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` | `623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d` |
| `world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| `world/level/block/EndPortalBlock.java` | `725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863` | `75addd79af8670d89880c248d8bcbfaad15208b5d43c4dd2aa27487ef84bcfeb` |
| `world/level/block/TripWireBlock.java` | `8fc4647ed98d48434dc3ac10590507156b62fce924d2d7fd28ea40cf9f466012` | `3b379d732599e55b156ffde5ebd0f7043ab4d59e6556f128e830038c77c9249a` |
| `world/level/block/PowderSnowBlock.java` | `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654` | `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417` |
| `world/level/block/LayeredCauldronBlock.java` | `57329d1bef6c0b39d4532bc518a1f83fadb605899a2a3ebdb3a609d2cefe5c47` | `18be2624a0c45b6f807627a0f0febabc48a9a7092dc918411e94b49276976c2d` |
| `world/level/block/LavaCauldronBlock.java` | `146f402a93d685e116067634d100646c31c40ed387a2163f50d30e3bbd7ff82e` | `979d958a18b76671c53cadf2a2b669a2a268a53008345a5d6b8d736e0b3584c5` |
| `world/level/block/AbstractCauldronBlock.java` | `a42f9cddf69bd0429ab7befdc30c468e890e6643f41f8768475ca4d5ab2629b` | `a4e10cccf88b3c0d1cc64f33a06d8ad39c28f6b823483b910e71b9bd16191492` |

The `.2/.3` complete source alias for Entity, BlockGetter, BlockBehaviour, EndPortalBlock and TripWireBlock is established in the cited accepted alias report; no new `.3` provenance claim is needed for this bounded closure.

## Exact historical admission and per-block shape inputs

In 1.21.2 `Entity.checkInsideBlocks` (`world/entity/Entity.java:1038–1078`) reads each position's current state after the alive check. It proceeds only for non-air plus successful `visitedBlocks.add(pos.asLong())`, then obtains `state.getEntityInsideCollisionShape(level,pos)`. The state method delegates to the block at `BlockBehaviour.java:740–741`; the base block provider at `:353–355` returns the shared `Shapes.block()` object. Admission is therefore:

```text
nonAir && firstVisit &&
    (insideShape == Shapes.block() || collidedWithShapeMovingFrom(from,to,pos,insideShape))
```

The sentinel comparison is reference identity and bypasses the shape collision predicate. It does not mean “the block's ordinary collision shape is full”; it means the entity-inside provider returned the singleton. For a non-sentinel partial shape, `Entity.java:1080–1084` translates each shape AABB by the block position and calls the historical `collidedAlongVector` gate. The accepted alias closure already binds that sweep math; this report relies on, and does not recalculate, its recentered final entity box and reverse vector.

A whole block-source override scan found only `EndPortalBlock` and `TripWireBlock` overriding the old entity-inside provider (apart from the base provider and BlockState delegate). Consequently the named old-era callbacks `WebBlock`, `HoneyBlock`, `BaseFireBlock`, the water/lava block states, `PowderSnowBlock`, and both layered/lava cauldrons inherit the base `Shapes.block()` sentinel. Their own ordinary collision or outline shape does not restrict this old callback gate. Water/lava's old fluid contact/update behavior is a separate earlier entity fluid path; the block DDA does not call `FluidState.entityInside`.

The old partial providers are state-derived:

- `EndPortalBlock.java:31,48–54`: its inside provider returns `state.getShape(level,pos)`, and the block's shape is the slab `(0,6,0)–(16,12,16)` in sixteenths. It uses the partial swept intersection predicate. The same portal shape/provider exists in 26.2 (`EndPortalBlock.java:34,51–57`).
- `TripWireBlock.java:42–43,69–70,137–139`: it returns `state.getShape`; attached state uses `(0,1,0)–(16,2.5,16)`, unattached uses `(0,0,0)–(16,8,16)`, in sixteenths. 26.2 retains the same state-selected outline provider (`TripWireBlock.java:43–44,70–71,145–147`). Its 1.21.2 callback itself is client-gated off (`entityInside` tests server side), so this is a shape-contract exception but not an ordinary client callback effect.

## 26.2 provider changes and callback boundary

The 26.2 base provider still returns `Shapes.block()` (`BlockBehaviour.java:331–334`), and the BlockState delegate supplies `level,pos,entity` (`:694–695`). A full block-package scan found provider overrides in EndPortal, TripWire, PowderSnow, LayeredCauldron, and LavaCauldron. The two legacy providers retain their source-era shapes. The three added providers are real eligibility differences for era-existing block classes:

- `PowderSnowBlock.java:115–118,121–136`: 26.2 asks `getCollisionShape(state,level,pos,CollisionContext.of(entity))`, replacing an empty result by `Shapes.block()`. For an entity with `fallDistance > 2.5`, that provider returns the 0.9-block-high falling shape; other non-empty cases depend on the entity's walk-on/context gates. The 1.21.2 provider is inherited full-block sentinel regardless of those collision-shape cases. This class must use the legacy full sentinel under the old profile.
- `LayeredCauldronBlock.java:42–44,77–82`: 26.2 returns a level-indexed union of the cauldron outline and its interior content column (base fill height 6/16 plus 3/16 per level). `LavaCauldronBlock.java:19–20,41–44` similarly returns the cauldron outline plus an interior column from 4/16 to 15/16. Their 1.21.2 classes inherit `Shapes.block()`. Under the old profile these states pass every candidate already yielded by traversal; a compat visitor must not use modern shape overlap to suppress them.
- Web, honey, fire, and old water/lava block states: no old or 26.2 provider override was found for those classes, so the source-derived block admission sentinel remains full in both endpoints. The old callback code still decides whether the respective block callback does anything. This does not convert modern `WaterFluid`/`LavaFluid` swept fluid visits into historical block callbacks.
- Modern-only block classes have no 1.21.2-era callback or provider contract to emulate from this evidence. Treat them as unsupported by this source-era admission closure; do not infer historical behavior from a modern default provider.

In 26.2 `Entity.checkInsideBlocks` (`world/entity/Entity.java:1342–1365`) obtains the per-entity inside shape and computes `insideBlock` as the same sentinel shortcut or `collidedWithShapeMovingFrom` on translated shape AABBs. It separately computes `insideFluid` using `FluidState.getAABB` and the new shape-motion predicate (`:1395–1403`). It inserts the position into `visitedBlocks` only after `(insideBlock || insideFluid)`. This differs from old insertion before the block-shape test. In old `.2/.3`, even a failed partial-shape candidate is reserved; in 26.2, a block-shape failure reserves the position only if fluid independently qualifies. The old sweep has no equivalent swept-fluid test or callback injection. Preserve old non-air/pre-shape dedup timing and omit the new fluid route.

## Implementable visitor contract and remaining gates

For each candidate position in the already accepted historical candidate sequence, under the same live client world/state assumption:

1. Preserve the old alive check, then read the current `BlockState`; ignore air.
2. Insert its packed position into the sweep-wide visited set before requesting the inside shape. Duplicate or already-reserved positions are skipped.
3. Request the `.2/.3` entity-inside shape contract for the current block/state. For the source-era classes listed above, use their inherited full sentinel unless an old override is identified. The exact partial exceptions are the end-portal slab and state-selected tripwire shapes.
4. Admit the full sentinel unconditionally. For a non-sentinel shape, translate its AABBs to world position and apply the already accepted `.2/.3` moving-from collision predicate. Do not substitute 26.2's provider output for powder snow or cauldrons.
5. At eligible blocks, run the accepted old synchronous `BlockState.entityInside` then `onInsideBlock` order; do not call the modern swept-fluid callback or collector. Callback writes may affect later state/shape queries, so fetch sequentially rather than snapshotting the whole shape map.

No additional source gate is missing to state this endpoint visitor admission contract. Remaining limits are applicability, not an unresolved endpoint predicate: it relies on the accepted historical candidate/query/sweep operations; it is conditional on corresponding same live client block states; it does not supply historical callbacks for modern-only blocks, modern fluid injection, or establish an intermediate patch cutover or runtime parity. The ordinary LocalPlayer reachability and `.2/.3` alias are already independently accepted in the cited partial-sweep and callback-order closures.

## Scope exclusions

Source/static inspection only. No product implementation, wiki inventory, Git history, backlog, tests, build, game launch, decompiler, or network source was used. No source corpus was edited. This report is not a full-pair freeze, callback-body acceptance, or runtime-parity claim.
