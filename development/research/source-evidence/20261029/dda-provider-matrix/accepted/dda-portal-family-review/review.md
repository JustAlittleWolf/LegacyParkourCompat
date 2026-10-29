# Independent review: portal family IDs 90 and 119

Verdict: **ACCEPT**, bounded to the source claims for the 1.8.9 portal blocks and their 26.2 aliases/local collision and outline providers. This review does not establish DDA visitation, hit selection, portal responses, or runtime reachability.

## Inputs and integrity

- Finding: `.native-source/20261028/collision-next/dda-portal-family-90-119/FINDING.md`
- Finding SHA-256: `B00D763D1F4D3C0210889BE0284D73E8B1A52DC4529ADECD83FB1E5BF87E4450`
- Source manifest: `.native-source/20261028/collision-next/dda-portal-family-90-119/source-hashes.json`
- Source manifest SHA-256: `5EC824ED7EBE6FD731A9E9C2A08E11805D78963290E98E56C1F1C99C01B6EA61`
- Canonical ready-marker, provenance, and source-manifest hashes for both releases match the values recorded in the input manifest.
- All 15 selected Java source files were independently rehashed against their canonical release manifests; all file and manifest entries match.

## Source checks

- 1.8.9 `Block.java` registers ID 90 as `portal` and ID 119 as `end_portal`. `PortalBlock` has axis X/Z with default X, decodes metadata using `(metadata & 3) == 2 ? Z : X`, and its `updateShape` reads the queried cell axis before writing the corresponding bounds. The report's state and bounds description matches `PortalBlock.java`.
- Neither old portal class overrides `canRayTrace` or `rayTrace`; the default ray path invokes `updateShape` before clipping. Old `PortalBlock.getCollisionShape` returns null. Old `EndPortalBlock.addCollisions` is empty, so its mutable ray bound is not an ordinary movement collision box.
- The current registry binds `BlockIds.NETHER_PORTAL` to `NetherPortalBlock` and `BlockIds.END_PORTAL` to `EndPortalBlock`. The cited data-fixer tables support the old numeric/name identities and the `portal` → `nether_portal` rename; the finding appropriately does not claim an untraced runtime conversion sequence.
- Both current block registrations set `noCollision()`, and the base collision provider returns empty when `hasCollision` is false. This supports the bounded ordinary movement-collision equivalence claim.
- Current Nether Portal outline shape starts from `Block.column(4,16,0,16)` and rotates the X/Z axis map. With `Block.column`'s sixteenth-unit arguments, those local bounds match the two old axis cases. Current End Portal outline uses `Block.column(16,6,12)`, giving Y `[6/16,12/16]`, distinct from old `EndPortalBlock.updateShape` Y `[0,1/16]`.
- The report keeps outline separate from movement collision and explicitly excludes portal entity responses and end-to-end DDA equivalence. No material contradiction found.

## Source hash verification

1.8.9 canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`.
26.2 canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`.

The source-hashes JSON records 4 selected 1.8.9 files and 11 selected 26.2 files. Every actual SHA-256 equals both the JSON value and the corresponding canonical `.sources.sha256` entry; each release's ready marker, provenance, and source-manifest digest also matches its recorded identity.
