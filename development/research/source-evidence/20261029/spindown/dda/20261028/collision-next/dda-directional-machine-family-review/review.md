# Independent review: directional machine provider closure

## Verdict

**ACCEPT**, bounded to historical IDs 23, 61, 62, and 158; their explicit current aliases and state projections; and their individual movement/ray-shape providers. This review does not establish DDA visitation, first-hit, or whole-operation parity.

## Reviewed inputs and hash checks

- Finding: `../dda-directional-machine-family-23-61-62-158/FINDING.md`
- Finding SHA-256: `47361828698469CAC872814737F6B3259347AACF58FD71FF93E7BE8B06BBDABC`
- Source ledger: `../dda-directional-machine-family-23-61-62-158/source-hashes.json`
- Source ledger SHA-256: `0AFBCEEF7FCCC8DDF2D26BE0A35D58F319E6D2D80482A43659D9D7018A4F955A`

I rehashed all 16 selected Java files against their live canonical files and the corresponding `.sources.sha256` rows: **16/16 matched**. I also recomputed all six 1.8.9 and 26.2 ready/provenance/source-manifest identity-file hashes: **6/6 matched**.

## Source checks

- `Block.java` registers ID 23 as `dispenser`, IDs 61/62 as distinct `FurnaceBlock(false/true)` instances named `furnace`/`lit_furnace`, and ID 158 as `dropper` (1.8.9 `Block.java:875, 960–962, 1174`).
- Dispenser metadata masks the facing bits with `& 7`, stores `TRIGGERED` in bit 3, and its direction decoder calls `Direction.byId` (`DispenserBlock.java:177–179, 200–215`). Dropper inherits this state codec (`DropperBlock.java:16`). Furnace metadata decodes the direction and replaces vertical directions with NORTH (`FurnaceBlock.java:187–194`); the old lit distinction is the separate ID/object registration, not an extra furnace metadata bit.
- `ItemIdFix` preserves old numeric names for dispenser, both furnace IDs, and dropper. `BlockItemIds` explicitly names current `dispenser`, `furnace`, and `dropper`. The `BlockStateData.register` argument order is current target first, legacy source(s) afterward (`BlockStateData.java:686–697`). The reviewed rows map old `lit_furnace` facings to current `furnace` with `lit=true` (`:3397–3400`); old `furnace` facings map to `furnace` with `lit=false` (`:3393–3396`). Dispenser and dropper rows cover false/true trigger states across all six facings (`:968–979, 7075–7086`).
- Historical `Block` initializes unit bounds in its constructor (`Block.java:201`), default `updateShape` is empty (`:596–598`), default `rayTrace` updates then clips those bounds (`:456–516`), and default movement collision returns a box from the same bounds (`:346–350`). The three old provider classes do not override these shape methods; Dropper inherits Dispenser.
- Current `Blocks` registrations use ordinary `Properties.of()` for these blocks, with no `.noCollision()` call. `BlockBehaviour` defaults `getShape` to the unit cube and returns that shape for collision when `hasCollision` is true (`state/BlockBehaviour.java:323–328, 985`). The reviewed current classes add no ray/shape provider override. The report's local provider-equivalence conclusion is therefore supported.
- Placement/onAdded neighbor reads and block-entity behavior are outside the shape-provider methods cited by the finding; the bounded finding does not need those values as inputs to the local ray/collision shape calculation.

No product code, runtime behavior, DDA traversal, first-hit behavior, or player outcome was reviewed or inferred.
