# Independent source review: note block and jukebox providers

**Verdict: REVISE** for one material mischaracterization of the historical full-cube predicate. The remaining bounded provider, state, identity, and hash claims reviewed here are supported.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-blockentity-audio-family-25-84/FINDING.md`
  - SHA-256: `42DA4BD7258AD6AA9653A4AC8A1DE13A6C7451C177780AFC95F324885F1475A8`
- Source ledger: `.native-source/20261028/collision-next/dda-blockentity-audio-family-25-84/source-hashes.json`
  - SHA-256: `7E88AE1FE3D81BC0CC95C179C5CEF67A3D0FC29BA4BD30F87C0AF1EBC68A424E`
- Canonical manifests verified against actual manifest files:
  - 1.8.9 Ornithe Feather: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`
  - 26.2 unobfuscated: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`
- All 12 selected source file SHA-256 values were recomputed from the canonical source paths and matched both the ledger and canonical `.sources.sha256` entries (0 mismatches).

## Findings

1. **REVISE: the cited `hasBlockEntity` check is not a full-cube classification.** The finding says `BlockWithBlockEntity` makes full-cube classification false at old `Block.java:668–673`. Those lines are `hasSilkTouchDrops()`, whose result is `isCube() && !hasBlockEntity`; this controls silk-touch drop behavior. The historical `Block.isCube()` at `Block.java:245–247` returns `true` unconditionally, and it is not gated by `hasBlockEntity`. The finding should say that the BE flag disables the silk-touch-drop predicate while the block remains classified as a cube. This correction does not undermine the separate unit collision-box/ray-box claim.

2. **ACCEPT: IDs, metadata, and block-entity distinction.** Old `Block.java:878,1015` register IDs 25 and 84 as NoteBlock and JukeboxBlock. Both extend `BlockWithBlockEntity`; its constructor sets the BE flag after `Block` initializes unit bounds (`Block.java:198–205`; `BlockWithBlockEntity.java:13–22`). NoteBlock uses base default-state metadata (`Block.java:182–186`); Jukebox maps metadata greater than zero to `HAS_RECORD` and back to 1/0 (`JukeboxBlock.java:116–123`). Current `BlockItemIds` and `Blocks` identify/register `note_block` and `jukebox`; the current NoteBlock is a plain `Block`, while Jukebox remains a block entity.

3. **ACCEPT: bounded old provider behavior.** Neither historical subclass overrides `getCollisionShape`, `rayTrace`, or `updateShape`. The base collision provider builds a box from stored bounds (`Block.java:346–350`); base `rayTrace` invokes `updateShape` before clipping (`456–458`), and base `updateShape` is empty (`596`). Constructor bounds are the unit cube. The block-entity fields are used by note tuning/playback, jukebox record handling, and comparator/redstone behavior, not these provider methods.

4. **ACCEPT: current shape/collision defaults.** Current registrations at `Blocks.java:691–695,2016–2020` do not call `noCollision`. `BlockBehaviour.Properties` defaults `hasCollision=true` (`985`); outline defaults to `Shapes.block()` (`323–324`) and collision returns the state shape when enabled (`327–328`). The examined NoteBlock/Jukebox classes do not override those shape providers. Their gameplay/BE dependencies do not feed their shape calculation.

This review establishes only these bounded source/provider facts. It does not establish DDA visitation, first-hit equivalence, player reachability, or end-to-end parity.
