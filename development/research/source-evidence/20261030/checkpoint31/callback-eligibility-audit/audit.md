# Independent audit: inside-block callback eligibility closure

## Binding and verdict

Reviewed immutable input: `.native-source/20261030/callback-eligibility-closure/report.md`, SHA-256 `d3d20d5a1a02d07fc509cc7b4af39348419f397b12652a908e0a053b9bb3450a` (recomputed; exact match).

**Verdict: ACCEPT the bounded source-level 1.21.2/1.21.3-to-26.2 admission contract, with one evidence-reference correction required before reusing the cited callback-order audit as an immutable dependency.** The primary report’s source claims are directly confirmed below. Its claimed hash for `.native-source/20261030/callback-order-audit/report.md` is stale: it says `db064c1dee695a4c51e0b7c1f5f207149fd6b673686ce00fab3462841abfcdc9`, while the current bytes hash to `1f8d091f1e52705a76447a8972cfeee13d4138516025c281f9bedb2c1beeb8d0`. The `db064...` hash instead matches `.native-source/20261029/partial-sweep-no-fluid-callback-source/report.md`. This does not overturn the admission result: I rechecked the relevant exact source directly, but the dependency fingerprint in the primary report must be corrected or removed before its callback-order-audit citation can be treated as bound.

## Source identity and integrity

Canonical read-only sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.2\mojmap`, `...\1.21.3\mojmap`, and `...\26.2\unobfuscated`.

- 1.21.2 `.decompile-provenance.json` recomputes to `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. Actual hashes for `Entity.java`, `BlockGetter.java`, `BlockBehaviour.java`, `Shapes.java`, `EndPortalBlock.java`, `TripWireBlock.java`, `PowderSnowBlock.java`, `LayeredCauldronBlock.java`, `LavaCauldronBlock.java`, and `AbstractCauldronBlock.java` match their embedded source records.
- 1.21.3 `mojmap.ready.json` and `mojmap.sources.sha256` recompute to `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` and `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. The selected `.2/.3` files above are byte-identical. I also recomputed all 422 block-package Java file hashes for each old release; every file matched its embedded record or manifest. The exhaustive entity-inside provider scan returns the same declarations in both versions.
- 26.2 parent provenance, ready marker, and source manifest recompute to `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`, `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. All 468 block-package Java source hashes match the manifest. The selected `Entity`, `BlockGetter`, `BlockBehaviour`, `Shapes`, portal, tripwire, powder-snow, cauldron, and water/lava fluid files also match their listed manifest entries.
- The 1.21.2/1.21.3 alias closure is bound to the recomputed report hash `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4`; its independent audit accepts the bounded source closure. I reused that accepted query-box and swept-AABB evidence and the separately bounded callback-order/collector evidence, without expanding their claims.

## Verified old admission predicate and complete callback roster

In 1.21.2 `Entity.checkInsideBlocks` (`Entity.java:1038–1078`) first checks `isAffectedByBlocks`, computes one shared deflated final bounding box, and walks movement records and `BlockGetter.boxTraverseBlocks` positions in order. It checks alive before reading the current state. For a non-air state, `visitedBlocks.add(pos.asLong())` is evaluated before `getEntityInsideCollisionShape`; therefore every first-seen non-air candidate is reserved even when its partial shape later rejects the callback. An accepted shape then runs `state.entityInside(...)` followed synchronously by `onInsideBlock(state)`. Only after both return does the old summary set receive the state. This also means subsequent queries observe synchronous callback mutations.

The `BlockState` delegate in `BlockBehaviour.java:740–741` dispatches to the block provider. The base provider at `BlockBehaviour.java:353–355` returns `Shapes.block()`. `Shapes.java:18–22, 42–44` stores one static `BLOCK` object and `block()` returns that same object. Thus the old `shape != Shapes.block()` branch is an identity sentinel bypass, not an ordinary full-collision-shape query.

The exhaustive old block-source provider scan finds only `EndPortalBlock`, `TripWireBlock`, the base provider and the state delegate, in both 1.21.2 and 1.21.3. The only partial-provider cases are:

- End Portal: `EndPortalBlock.java:31, 48–55`, slab `(0,6,0)–(16,12,16)` in block units of sixteenths; its inside provider returns the state outline shape.
- Tripwire: `TripWireBlock.java:42–43, 68–71, 137–139`; attached and unattached state shapes are selected by state, and returned by the inside provider.

For completeness, the complete set of old block classes declaring an `entityInside` override is: BaseFireBlock, BasePressurePlateBlock, BigDripleafBlock, BubbleColumnBlock, ButtonBlock, CactusBlock, CampfireBlock, CropBlock, DetectorRailBlock, EndGatewayBlock, EndPortalBlock, FrogspawnBlock, HoneyBlock, HopperBlock, LavaCauldronBlock, LayeredCauldronBlock, NetherPortalBlock, PitcherCropBlock, PowderSnowBlock, SweetBerryBushBlock, TripWireBlock, WaterlilyBlock, WebBlock, and WitherRoseBlock. Every one inherits the full `Shapes.block()` admission sentinel except EndPortalBlock and TripWireBlock. Other old block states also use the default provider and are admitted by that sentinel; the callback inventory is not the universe of positions receiving the generic state callback.

## Modern endpoint changes and fluid separation

In 26.2 `BlockBehaviour.java:331–332` keeps the default singleton, but the inside-provider API carries the entity. The complete block-package provider scan finds five overrides: EndPortalBlock, TripWireBlock, PowderSnowBlock, LayeredCauldronBlock, and LavaCauldronBlock. The legacy portal and tripwire providers retain state-shape behavior. The three additional providers change eligibility for block classes present in the old source:

- Powder snow (`PowderSnowBlock.java:115–136`) derives its inside shape from `getCollisionShape(..., CollisionContext.of(entity))`; the entity/context gates include `fallDistance > 2.5` and walk-on/above/descending checks. The 1.21.2/1.21.3 class inherits the unconditional full sentinel.
- Layered cauldron (`LayeredCauldronBlock.java:40–44, 79–82`) selects a state-level filled shape. Lava cauldron (`LavaCauldronBlock.java:42–44`) returns its filled shape. Both methods accept an entity argument in 26.2, but these provider bodies do not inspect it. Their old classes inherit the full sentinel.

26.2 `Entity.java:1342–1365` separately computes block and fluid eligibility, marks a position only after `(insideBlock || insideFluid)`, and invokes a swept `FluidState.entityInside` callback after the block callback when fluid qualifies. The old `.2/.3` `checkInsideBlocks` body contains only the block-state callback route and reserves non-air positions before shape eligibility; it has no swept-fluid callback. The older base-tick fluid contact/update path is separate and is not a substitute for a per-position DDA fluid visit.

## Implementable contract and remaining gate

For the exact 1.21.2/1.21.3 profiles, preserving the independently accepted old candidate sequence and sweep math, the historical admission contract is: perform the old alive/non-air gates; insert the packed non-air position into the sweep-wide visited set before shape lookup; use the old state’s inside-provider result; admit the `Shapes.block()` singleton unconditionally; otherwise apply the old moving-from partial-shape predicate to the translated provider shape; then invoke block-state `entityInside` and `onInsideBlock` synchronously, adding the state to the summary only after both return. Do not use modern powder-snow or cauldron provider shapes, or add 26.2 swept-fluid visits.

The source boundary is exact `.2/.3` alias behavior versus the inspected 26.2 endpoint. If a provider override is explicitly limited to those old profiles, no uninspected intermediate release is a prerequisite: this audit makes no cutover claim for any other profile. The remaining applicability condition is the already stated same-live-state assumption and the separately accepted candidate/query/sweep inputs; modern-only block behavior and runtime parity remain outside this source closure. No additional endpoint admission predicate is open.

## Audit scope

Read-only exact-source inspection, whole block-package hash/provider scans, and source-record/manifest checks only. No product source, wiki, history, tests, build, game, decompiler, network or canonical source tree was changed or used.
