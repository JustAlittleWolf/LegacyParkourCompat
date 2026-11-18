# Sand and gravel material provider audit

Verdict: **SOURCE-CONFIRMED MATERIAL GATE; STATIC FIXTURE REJECTED**. In all 24 audited stable releases, dry `sand` (both metadata variants) and `gravel` resolve to `Material.SAND`, whose `blocksMovement()` returns true. This suppresses the one-below water-depth sample in the dry-side liquid-current branch. The material association and gate are continuous across the 24 patches; no version boundary was found. A permanently static sand/gravel block over water is not a valid fixture: both blocks are falling blocks and water is a fall-through material. Only a transient pre-fall state can exercise this provider case; runtime/player reachability of that transient was not tested.

## Bounded inputs and integrity

Read-only exact source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather/`, for `1.8`, `1.8.1`–`1.8.9`, `1.9`–`1.9.4`, `1.10`–`1.10.2`, `1.11`–`1.11.2`, and `1.12`–`1.12.2`. Each exact mapping identity is Feather Gen2 `feather-gen2-<release>+build.2`; the already accepted glass audit records the pinned mapping artifacts for all 24 releases. This audit independently rechecked the exact release directory, mapping namespace, and the scoped source hashes.

Seventeen trees contain `.decompile-provenance.json`; each of the six relevant mapped source files below matched its provenance source hash. The seven ready-sidecar trees are `1.8.9`, `1.9.4`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, and `1.12.2`; each has `status: ready`, the exact version ID and `ornithe-feather` mapping, matching source-manifest and artifact-manifest hashes, and all six scoped files matching the source-manifest entries. Source identities and all six per-release raw hashes are in [source-hashes.tsv](source-hashes.tsv); the two separate 1.8/1.8.1 sand-variant enum source hashes are included there too. No source was regenerated.

## Exact source path

In every release, `LiquidBlock.getFlow` first gets the horizontal neighbor's liquid depth. When that depth is negative (the dry side), it evaluates the neighbor block's `getMaterial().blocksMovement()` and reads `side.down()` only if the result is false. A nonnegative depth below then contributes `below - (center - 8)` times the horizontal direction. The direct material predicate is the gate; collision shape, cube/full-block status, map color, or opacity is not used here.

`Block.java` registers one `SandBlock` as block ID `sand` and one `GravelBlock` as block ID `gravel`. In 1.8 and 1.8.1 the separate `SandBlock__Variant` enum lists `SAND` and `RED_SAND`; from 1.8.2 onward those are the nested `SandBlock.Variant` states. Red sand is therefore a metadata/state variant of the same `sand` block, not a separate registered provider. `GravelBlock` has no variant. Both classes inherit `FallingBlock`'s no-argument constructor, which invokes `Block(Material.SAND)`. `Material.SAND` is constructed as a plain `Material`; base `Material.blocksMovement()` returns `true`. No override in `SandBlock` or `GravelBlock` changes material behavior.

Source lines (all paths relative to each exact release root; every file's raw SHA-256 is in `source-hashes.tsv`):

- `LiquidBlock.java`: the dry-side check, material predicate, one-below read, and nonnegative-depth gate are consecutive at lines 136–139 (1.8), 137–140 (1.8.1), 136–139 (1.8.2–1.8.9), 138–141 (1.9–1.9.2), 141–144 (1.9.3–1.9.4), 141–144 (1.10–1.10.2), 141–144 (1.11–1.11.2), and 149–152 (1.12–1.12.2). The `Material.blocksMovement()` predicate is at lines 137, 138, 137, 139, 142, 142, 142, and 150 for those same release groups.
- `Block.java` registrations: 1.8 lines 800–801; 1.8.1 811–812; 1.8.2–1.8.9 855–856; 1.9–1.9.2 702–703; 1.9.3–1.9.4 747–748; 1.10–1.10.2 752–753; 1.11–1.11.2 757–758; 1.12–1.12.2 776–777.
- `SandBlock.java`: class extends `FallingBlock`; default state initializes plain sand; the red-sand variant enters through the same state property. In 1.8/1.8.1 the enum is `SandBlock__Variant.java`; in later releases it is nested in `SandBlock.java`.
- `GravelBlock.java`: extends `FallingBlock` in each audited release.
- `FallingBlock.java`: default constructor calls `super(Material.SAND)`; `onAdded` and `neighborChanged` schedule the block tick; `tick` only calls `tryFall` when `!world.isClient`; `tryFall` tests the block below with `canFallThrough`; water and lava materials are accepted by that predicate.
- `Material.java`: `SAND` is a plain `new Material(...)`; the base `blocksMovement()` implementation returns true. Raw source hashing divides this file into five content groups and the falling-block source into seven; all groups were checked, with exact release-to-hash mapping in the TSV.

## Fixture and movement consequence

For a dry sand or gravel neighbor at `(1,64,0)`, a water block at `side.down() == (1,63,0)` satisfies the liquid-depth sample but also makes the sand/gravel's support block fall-through. `FallingBlock.onAdded` schedules the update with `getTickRate() == 2`; the server-side tick then falls through water. This proves the configuration is not a stable static fixture. The local client does not execute the falling operation in `FallingBlock.tick`, because it is guarded by `!world.isClient`, although an authoritative server can later send a block update. Thus the only source-supported ordinary-state candidate is a pre-fall snapshot after addition and before the scheduled fall/update; no indefinitely stable world fixture is accepted.

If a loaded, unmounted, non-flying local player samples water at `(0,64,0)` while this transient side state is present, the material gate rejects the below read. Reuse the previously accepted glass geometry/vector calculation at `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/legacy-fluid/water-material-glass-audit/evidence.md` (audit commit `f2298d2fd3f5b8959b741e502b6c367e2f213080`, raw evidence SHA-256 `1c115cad2d61299f8300591e3a4207c55960781ef7fd52d3f10ec1c24937e65b`). With center depth 1 and the lower east source depth 0, admission would add east weight `0 - (1 - 8) = 7`; suppression leaves that lower source out of the current vector. The accepted companion establishes that the path reaches local player liquid drag and that normalized flow is applied at `0.014`. This is the source-predicted consequence; no claim is made that a particular server/client schedule guarantees the transient overlaps a player tick.

## Limits

This audit covers only dry full vanilla `sand` with plain/red metadata and `gravel`, as the dry-side `LiquidBlock.getFlow` provider. It does not accept stable sand/gravel-over-water geometry, generalized `Material.SAND` rules, falling-wall-face logic, direct body-water predicates, portal/piston behavior, or runtime parity. No tests, build, decompilation, gameplay, or product-source access occurred.


## Alias checks and bounds

The scoped reference sweep found no `Material.GRAVEL` declaration or use in the 24 releases. The `gravel` block resolves through `GravelBlock -> FallingBlock() -> Material.SAND`; it does not have a dedicated gravel material. `Material.SAND` also appears in excluded aliases: `SoulSandBlock` throughout the range, a `NoteBlockBlockEntity` material comparison for sound selection, and `ConcretePowderBlock` in 1.12–1.12.2. These are not aliases for the requested block IDs and are not included in this finding. Their source hashes, along with every occurrence in the scoped source roots, are recorded in `source-hashes.tsv`. The sand enum confirms `SAND` and `RED_SAND` are the only sand metadata variants; no separate `red_sand` block registration was found.

