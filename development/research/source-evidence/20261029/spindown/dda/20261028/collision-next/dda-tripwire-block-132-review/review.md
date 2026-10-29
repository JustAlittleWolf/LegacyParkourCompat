# Independent review: tripwire block ID 132 provider closure

## Verdict

**ACCEPT** for the bounded source claims in the reviewed finding: old ID 132/current `tripwire` identity, legacy metadata/state and support-derived `SUSPENDED`, the old `ATTACHED`/`SUSPENDED` ray-bound formulas, null old movement collision, current lack of `SUSPENDED`, current `ATTACHED` shape formulas, and empty ordinary current movement collision. The report correctly keeps state reconstruction, caller ray-mode selection, DDA traversal/first-hit behavior, event behavior, and player outcomes open.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-tripwire-block-132/FINDING.md`
- Finding SHA-256: `CDF44312119F1AA5C675836205125D6C81CCE190D78D548AE6185B4C66A4990E`
- Source hash ledger: `.native-source/20261028/collision-next/dda-tripwire-block-132/source-hashes.json`
- Ledger SHA-256: `DA07E4202426747F816A9F2CA8D2F0C27487BF6AC4A6280E49E0D83B10A5021C`

## Source and claim checks

I independently recomputed all 12 selected Java-file SHA-256 values from the canonical read-only source tree and compared each against both the finding ledger and its release's canonical `.sources.sha256` entry. Result: 12/12 match, zero mismatches. I also checked both release ready-marker, provenance, and source-manifest digests against the ledger; both ready markers declare the recorded manifest digest.

The old source registers ID 132 as `tripwire` (`Block.java:1119`). `TripwireBlock` defines `POWERED`, `SUSPENDED`, `ATTACHED`, and `DISARMED`; metadata bits 0–3 map in that order and the directional properties are separately resolved (`TripwireBlock.java:20–53,219–251`). `onAdded` derives `SUSPENDED` from `!World.hasSolidTop(world,pos.down())` (`TripwireBlock.java:110–114`), and adjacent tripwires connect only if their `SUSPENDED` values agree (`:202–215`). The finding correctly distinguishes those connection bits from the ray-bound formula.

`TripwireBlock.getCollisionShape` returns null (`:56–59`), and base `Block.addCollisions` adds geometry only for a non-null shape (`Block.java:339–350`). The old class does not override `canRayTrace` or `rayTrace`; inherited `Block.rayTrace` invokes virtual `updateShape` before clipping (`Block.java:456–469`). The old updater's three branches are correctly reported: non-suspended `[0,0.09375]`, suspended/unattached `[0,0.5]`, and suspended/attached `[0.0625,0.15625]`, with full horizontal extents (`TripwireBlock.java:97–108`).

The current ID mapping retains key `tripwire` (`BlockItemIds.java:737`), and `Blocks.TRIPWIRE` registers `TripWireBlock` with `.noCollision()` (`Blocks.java:2668–2670`). Its state definition contains `POWERED`, `ATTACHED`, `DISARMED`, and four horizontal connections, with no `SUSPENDED` (`TripWireBlock.java:31–42,240–242`). `getShape` selects the attached `[1/16,2.5/16]` or unattached `[0,8/16]` column (`:43–44,70–72`); the cited `Block.column` conversion confirms these sixteenth coordinates. The current provider therefore matches the old suspended branches but has no current shape branch for the old non-suspended `[0,1.5/16]` bounds. `noCollision` yields empty base collision (`BlockBehaviour.java:327–329,1094–1097`); the report appropriately does not conflate the separate entity-inside shape or assume which ray mode a caller uses.

The stated old/current neighbor difference is accurate: old tripwire-to-tripwire connection compares suspension state, while current `shouldConnectTo` accepts any same-block tripwire after the hook-facing check (`TripwireBlock.java:202–215`; current `TripWireBlock.java:204–205`). The finding does not claim a complete current-world-to-old-state conversion and correctly identifies missing `SUSPENDED` reconstruction as an adapter gate.

## Evidence identity

Canonical roots checked:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`

Recorded ready-marker/provenance/source-manifest SHA-256 values match the canonical files for both endpoints. All selected source hashes match the actual files and canonical manifest entries.

## Scope limit

This review accepts source facts for this one block family only. It establishes no DDA visitation, hit ordering, caller ray-mode selection, state conversion completeness, event or server behavior, player reachability, or movement outcome.
