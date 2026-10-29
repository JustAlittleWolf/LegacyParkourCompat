# Independent review: piston-family provider closure

Verdict: **ACCEPT** for the bounded source claims in the reviewed finding. This does not accept a complete old-state/provider adapter, DDA visitation, first-hit parity, or player-response parity.

## Immutable inputs

- Finding: `.native-source/20261028/collision-next/dda-piston-family-29-33-34-36/FINDING.md`
  - SHA-256: `C647B896C44DFB2B0DD956E3A6C5CCAA4FA80C8C54B32BE38144BCC4FB8530C4`
- Source ledger: `.native-source/20261028/collision-next/dda-piston-family-29-33-34-36/source-hashes.json`
  - SHA-256: `831CA8BA5B8F6CBFCCEC5D913321EBA08DF3A761D39BD5DD7C46E30FA1474666`

The report binds exact 1.8.9 `ornithe-feather` and 26.2 `unobfuscated` inputs. I recomputed the hashes for all 19 listed Java files; each matches both the ledger entry and the release's canonical `.sources.sha256` entry. The ready markers are `ready`, and their recorded source-manifest digests match the actual canonical manifest files.

## Review checks

- **IDs and state projection:** 1.8.9 `Block.java:882, 886–889` registers IDs 29, 33, 34, and 36 as sticky base, base, head, and moving extension. The two bases are distinct sticky-parameterized `PistonBaseBlock` instances. 26.2 registrations and `EntityBlockStateFix` retain corresponding names/IDs; `BlockStateData` carries base facing/extended values, head facing/type values, and moving-piston facing/type values. The legacy facing order and bit-8 extended/type encoding agree with the cited source. The old head `SHORT` state is absent from its metadata conversion, while the current registration defaults it to false and current shape selection consumes it.
- **Base provider:** Old `PistonBaseBlock.updateShape` reads the world state at the queried position and writes the facing-dependent 1/4 cap only if that world state is the same base block and extended; otherwise it restores a full cube. `getCollisionShape` calls that updater. `addCollisions` resets first, then invokes the inherited collection path, which obtains the shape through `getCollisionShape`. Inherited `Block.rayTrace` also updates the shared bounds before clipping. Current base shape is state-derived: full cube or the facing-rotated 4/16 plate.
- **Head provider:** Old movement collection invokes the end-plate and arm bounds in two separate inherited collection calls and then restores the unit bounds. The old `updateShape` supplies only the end plate, which is what inherited `Block.rayTrace` clips. Current head shape combines platform and arm, with distinct short/full arm shapes; thus the report properly avoids treating current shape as a substitute for either old path.
- **Moving block context:** Old `MovingBlock.rayTrace` explicitly returns null. Its default `canRayTrace` remains true. Movement collision reads a `MovingBlockEntity` at the position and uses moved state, facing, extension status, and progress at tick delta zero. Its separate `updateShape` calls the moved block's updater and translates that block's mutable bounds. Current moving-piston outline is empty, while collision delegates to `PistonMovingBlockEntity` when present; the current provider additionally uses moved state, movement flags/progress, and thread-local no-clip direction. The report correctly treats this as a context-bearing operation rather than a state-only shape.

The cited claims are supported by the inspected exact source; the report keeps its scope bounded and explicitly leaves the whole provider/state/neighbor matrix open.
