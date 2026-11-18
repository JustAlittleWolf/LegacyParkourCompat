# Exact-source verdict: 1.13 / 1.13.1 AutoJump Stone witness

## Verdict

**The claimed “1.13 arms / 1.13.1 rejects” result is false for the stated two-Stone floor fixture.** Once the real Stone support at `(8,63,0)` and `(9,63,0)` is included in the same full-cube `BitSetVoxelSet` as the synthetic border, the exact X/Y/Z greedy sweep produces a first raised-ray box centered at x=11 in both versions. With physical Stone at `(11,66,0)`, both versions directly sample that Stone at t=1, raise `s` from 66 to 67, and return because `3 > 2.7F`; neither arms the timer. If `(11,66,0)` is AIR, both versions retain `s=66`, get rise 2, pass the Jump Boost II cap, and arm the timer.

The versions do have different finite masks and floor/side boxes. Those differences do not change this first ray-hit box’s center, height, or this sample-state outcome. The raw upper Stone is excluded from the collision mask by the relevant edge guards in both versions, but is visible to the later direct `getBlockState` height scan in both.

## Fixed fixture and exact preflight

Post-move player position is `(8.99,64,0.5)`, coming from east displacement `0.10` from x=8.89; player width/height are `0.6/1.8`. Speed II gives `f=0.14F` from the `0.1F` base and the 0.4 movement-speed multiplier. The lookahead uses `p=max(f*7,1/l)=0.98F`, where `l=(float)fastInvSqrt((float)(0.10^2))`. `autoJump` constructs `vec3d2` at post-move x plus `dx` again, then extends it by `p`, yielding endpoint x≈10.07. Its pre-raise candidate box is approximately `[8.39,10.67] × [64,65.8] × [-0.1,1.1]`; the raised east rays sit at y=64.51 and z≈0.2/0.8. The exact padded scan is x `[7,12)`, y `[63,67)`, z `[-2,3)`; `BlockPos.iterateRegionMutable` visits its integer endpoints inclusively.

Jump Boost II (amplifier 1) gives `o=1.2F+2*0.75F=2.7F`, so the cap loop evaluates only `t=1,2`. The supported fixture’s direct initial head checks require AIR at `(8,65,0)` and `(8,66,0)`. The t>1 player-column clearance check is `(8,67,0)`. The center-column cap samples are at the center selected by the first raised-ray box.

For incoming `outsideWorldBorder` state to be immaterial, the player centers must be inside the border’s one-block inset: `minX+1 < 8.89`, `8.99 < maxX-1 (=9)`, and `minZ+1 < 0.5 < maxZ-1`. Then `collidesWithBorder` is true for either incoming flag, and a stale true flag is normalized while the computed true result still controls the query. The assigned east maximum alone does not specify the other bounds; this witness assumes the ordinary centered border with the stated inset. Queried chunks are loaded.

## Exact finite occupancy and order

Here each set lists occupied block-cell z coordinates for that scan `(x,y)`; every unlisted cell in the scan is empty. Full physical Stone and full synthetic border cells enter the same version-local `BitSetVoxelSet`.

| Release / scan y | x=8 | x=9 | x=10 | x=11 |
|---|---|---|---|---|
| 1.13, 63 | `{0}` Stone | `{0}` Stone | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13, 64 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13, 65 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13, 66 | `{}` | `{}` | `{}` | `{}` |
| 1.13.1, 63 | `{0}` Stone | `{0}` Stone | `{-1,0,1}` synthetic | `{}` |
| 1.13.1, 64 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13.1, 65 | `{}` | `{}` | `{-2,-1,0,1,2}` synthetic | `{-1,0,1}` synthetic |
| 1.13.1, 66 | `{}` | `{}` | `{-1,0,1}` synthetic | `{}` |

Totals are 26 occupied full-cube cells in the 1.13 mask and 24 in 1.13.1. For 1.13, the X/Z-corner guard rejects simultaneous X/Z scan edges; the inner Y guard rejects the high row y=66, while low row y=63 remains eligible. Thus synthetic x=11,y=63,z=-1/0/1 participates. For 1.13.1, a cell is admitted only when it has fewer than two scan-edge coordinates: x=11,y=63 is excluded as an X+Y edge, x=10,y=66,z=-1/0/1 is admitted as a single Y edge, and x=11,y=66 is excluded as X+Y. The real Stone support cells at y=63 are also in this aggregate.

`VoxelSet.forEachBox` scans X, then Y, then Z, takes contiguous Z runs, and greedily extends X then Y. The support row consumes z=0 before the border-only side runs are emitted. In 1.13, the floor box is `[8,12] × [63,64] × [0,1]`; in 1.13.1 it is `[8,11] × [63,64] × [0,1]`. Both only touch the candidate box at its lower Y face and miss the raised rays. Earlier border side boxes either end at z=0 or start at z=1 and miss z≈0.2/0.8. In both traces the first raised-ray box is `[10,12] × [64,66] × [0,1]`; its center `(11,65,0.5)` floors to `(11,65,0)`, and its `maxY` is 66. This is the exact old first-hit height and x coordinate after including both physical floor Stones; the earlier no-floor/border-only trace is not this fixture.

For both versions, with center `(11,65,0)`, the direct reads are:

- AIR at the player-column preflight cells `(8,65,0)` and `(8,66,0)`;
- cap sample `(11,66,0)` at t=1;
- cap sample `(11,67,0)` at t=2 if t=1 did not return;
- player-column clearance `(8,67,0)` at t=2.

With AIR at `(11,66,0)`, `s` remains 66, `u=66-64=2`, and `0.5F < 2 <= 2.7F`, so both write `ticksToNextAutojump=1`. With full Stone at `(11,66,0)`, both direct reads obtain the physical Stone collision shape, set `s=66+1=67`, then immediately return on `67-64=3 > 2.7F` before t=2 clearance or timer write. The block is not replaced by border synthesis during those direct state reads.

## Physical Stone and evidence identity

The physical Stone at `(11,66,0)` is a lawful stored state for this setup if its chunk is loaded: the border collector substitutes synthetic shapes at its query cells and skips state reads there, but `World.setBlockState` writes to the chunk and `World.getBlockState` retrieves from the chunk without a world-border gate. A stationary border can therefore sit inside already stored terrain. The upper cell does not enter either aggregate mask: in 1.13 it is on the high-Y scan row; in 1.13.1 it is simultaneously on the high-X and high-Y scan edges. It still matters to the later direct sample because both versions select x=11.

Both source trees are canonical local `decompiled_minecraft/<release>/ornithe-feather` outputs from the same Feather Gen2 build.2 preparation, Java `25.0.3+9-LTS`, Vineflower `1.12.0` run. Provenance and per-file source-manifest checksums were verified. Relevant exact identities:

| Release | Mapping coordinate | Raw client SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|---|
| 1.13 | `net.ornithemc:feather-gen2:1.13+build.2` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `net.ornithemc:feather-gen2:1.13.1+build.2` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |

The requested witness pin `b32834860f56151e434259b43d441223e80eaf6c247aa18c942923d1000cc990` matches the named witness file. The requested caller-scope pin as supplied, `66f5875ca732af5621815122f3fce97037e4021a68eae769f394d438b7957e8`, does **not** match the named caller-scope file; its observed SHA-256 is `66f5875ca732af5621815122f3cece97037e4021a68eae769f394d438b7957e8`. I reviewed the named file at that observed hash, so the immutable author pin remains unresolved. The relevant `LocalClientPlayerEntity.java`, `WorldView.java`, `World.java`, `VoxelSet.java`, and `BlockPos.java` sources match their local manifests.

Exact source slices: `LocalClientPlayerEntity.move/autoJump` (1.13 lines 866–975; 1.13.1 lines 870–980); `WorldView.getBlockCollisions`, `getCollisions`, `collidesWithBorder` (1.13 lines 113–218, 249–268; 1.13.1 lines 112–199); `World.getCollisions` (1.13.1 lines 1755–1758); `VoxelSet.forEachBox` (1.13 lines 234–285; 1.13.1 lines 200–255); inclusive scan iterator `BlockPos.iterateRegionMutable` (both lines 212–239); `World.setBlockState/getBlockState` (1.13 lines 190–201, 507–515); `WorldBorder.contains/setCenter/setSize` (1.13 lines 25–27, 106–145); `PlayerEntity.getSpeed` and Speed modifier setup (`PlayerEntity.java:1482–1485`; `StatusEffect.java:179–199, 210–218`).

## Scope

This is a bounded exact-source correction for the supplied loaded, standing-player geometry. It shows that adding the raw upper Stone does not distinguish 1.13 from 1.13.1 for the Jump Boost II timer result; both suppress the timer. The no-upper-Stone AIR case also arms in both. Different finite masks, a different floor box, or a separately bounded scan-matrix behavior may still matter under another geometry, border, support, or scan order. This does not establish pair-wide coverage, a universal algorithm equivalence, runtime parity, or that a particular input always produces the stipulated displacement. No product, wiki, backlog, or Git history was read; no build, test, launch, or decompiler task was run; no primary or author file was changed.
