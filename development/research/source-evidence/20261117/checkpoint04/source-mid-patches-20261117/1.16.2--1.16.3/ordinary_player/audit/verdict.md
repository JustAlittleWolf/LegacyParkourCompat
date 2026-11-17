# Independent source coverage audit — 1.16.2 → 1.16.3

Verdict: **ACCEPT** for the exact Java source coverage report and its bounded `NO MOVEMENT DIFFERENCE FOUND` candidate. This accepts the audited comparison only; it does not freeze the pair or close resource, runtime, or broader dependency gates.

## Bound input

- Report: `build/source-mid-patches-20261117/1.16.2--1.16.3/ordinary_player/ordinary-player-source-coverage.md`
- Audited report SHA-256: `D9BEBB3F52C05A48F6DD8759D8E1444AC202FD387CC145207062E72FE775E5D5` (matches the assigned immutable revision).
- Exact IDs and published paths agree with `build/source-inputs-115-116-20261117/README.md`: 1.16.2 Mojmap and 1.16.3 Mojmap.

## Identity and inventory checks

- 1.16.2 ready marker identifies version `1.16.2`, mapping `mojmap`, status `ready`. The root source-manifest SHA-256 is `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; provenance SHA-256 is `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`.
- 1.16.3 uses the hidden mapping-local `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json`; its `sources` manifest SHA-256 is `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. The metadata URL names `1.16.3`, and the pinned client mapping artifact SHA-256 is `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`. No absent root-sidecar assumption was made.
- Independently enumerated both source manifests: each contains 3,516 paths. Every listed file on both sides was rehashed against its own manifest; there were zero mismatches. The paired inventory has no added or removed Java paths and exactly the four changed Java files named in the report.
- Each cited unchanged source digest in the report was checked against both local files and both manifests. The cited input/player tick and travel, entity position/velocity/box state, collision query/core/provider, fluid state, attributes/effects/items/enchantment, and movement packet files are byte-identical across these endpoints.

## Changed-source review

- `DetectedVersion.java` changes only release metadata values (name, world version, protocol version and release target); it does not alter player movement logic.
- `Mob.java` adds clearing of the dropped equipment slot with `ItemStack.EMPTY`. `Player` is not a `Mob` in this hierarchy, so this is outside the fixed player movement path.
- `PathNavigation.java` adds/updates the stuck-state flag and exposes `isStuck()`. This is mob navigation; it is not a player navigation operation.
- `MoveToTargetSink.java` adds the mob-navigation stuck predicate to a brain walk-target cooldown condition. This is non-player mob behavior.

These changes and their locations match the report. Together with the complete, hash-validated Java inventory, they do not reveal a changed player tick/travel/position/collision callback or direct player response in this exact edge. The report appropriately treats this as a bounded source candidate, not proof of a trajectory or patch onset.

## Remaining gates

The report correctly says packaged resource membership was not inspected, runtime was not tested, and this edge alone does not establish a broader patch boundary or pair freeze. Keep those gates open. No resource/archive or runtime conclusion is included in this verdict.
