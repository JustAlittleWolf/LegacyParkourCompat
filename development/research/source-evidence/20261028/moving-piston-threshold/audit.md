# Independent source audit: moving-piston threshold alias closure

Verdict: **ACCEPT**

## Reviewed revision

- Comparison: `.native-source/20261028/moving-piston-threshold-alias-closure/comparison.md`
- Required SHA-256: `82815EB9862CEDB301627AC1321F8B88A10FBE231F71F949935B0B2004B1403F`
- Recomputed SHA-256: `82815EB9862CEDB301627AC1321F8B88A10FBE231F71F949935B0B2004B1403F`
- Canonical read-only source tree: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`

## Identity and source-hash audit

I directly enumerated each exact version directory (including hidden entries) and inspected the version-specific provenance/ready/manifests at their literal paths. The named source files exist in each canonical tree. Recalculated SHA-256 values match the comparison and the source records: `MovingBlockEntity.java` is `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` for 1.12, 1.12.1, and 1.12.2, and `c61aff4526dc75c16b7b3db2374261762c9c4bccae5e0bfd5d33400ada201a83` (1.13), `6684ccc05adf8b29cac55f9796133405c6d4f6853051e651895303b657cd1c46` (1.13.1), and the same 1.13.1 hash (1.13.2). `PistonHeadBlock.java` is `849757a70f525192fc01bac4883388326f9cbe55267ac336d78f378beddeac39` for the three 1.12 releases and `d0de8ab39c2d350054d7ee9ed66f0e1695f2b7d1d78324b9eabd955361b58e5f` for all three 1.13 releases. `MovingBlock.java` is `2bcea815334849934eaa3283eead3a7a27f7b77503a8789aae1479af725df5c4` for the 1.12 releases, `19b8a41104da7b13eac58e2c01704043f6ff7ac1eea33457833bd41ea5810d61` for 1.13 and 1.13.1, and the same 1.13.1 hash for 1.13.2.

The pins also match the directly read records: 1.12 and 1.12.1 `.decompile-provenance.json` inputs identify their distinct Mojang client hashes and Feather Gen2 build.2 merged jars; 1.12.2's `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` match the stated client, source-manifest, and mapping-artifact hashes. The 1.13/1.13.1 provenance and ready records identify their exact version IDs and per-version Feather coordinates, with the listed client, mapping-artifact, and source-manifest hashes. The 1.13.2 ready/source/artifact manifests match the listed hashes. Its verification record reports zero source-file differences and identifies the regenerated intermediate mapped jar separately. I found no mismatch in the comparison's identity or pin table.

## Behavioral claims

- In the three 1.12 files, `MovingBlockEntity.addCollisions` uses `.set(PistonHeadBlock.SHORT, this.extending != 1.0F - this.progress < 0.25F)` (line 331); all three source files are byte-identical. In 1.13, 1.13.1, and 1.13.2 the same expression uses `4.0F` (lines 338, 342, and 342 respectively); those latter source files are also byte-identical. Java precedence makes the relational test bind before `!=`, as the comparison states.
- Both constructors leave `progress` and `lastProgress` at Java's float default because neither declares initializers nor assigns them. `tick` copies current progress to `lastProgress`, adds `0.5F`, and clamps at/above `1.0F`; it removes the block entity on the following tick after progress reached one. Thus the ordinary live path is 0, 0.5, 1.0. The threshold truth values in the comparison follow directly. Since the stored property is `(extending != thresholdResult)`, the 1.13 result is `!extending` on that live path.
- `readNbt` directly assigns `nbt.getFloat("progress")` and mirrors it to `lastProgress`; there is no lower clamp in the subsequent tick. At `-3.0F`, `1.0F - progress` is exactly `4.0F`, so `< 4.0F` is false. The comparison correctly limits its always-true conclusion to normal live progress.
- In 1.12, the ordinary `MovingBlock.addCollisions` hook delegates to the moving block entity. Its threshold branch is skipped only when `progress < 1.0` and the thread-local direction equals the movement direction; the thread-local's initial value is null. The source head calls `PistonHeadBlock.getArmShape`, which reads `SHORT` and selects the short/full arm box.
- In 1.13, `MovingBlock.getCollisionShape` delegates to `MovingBlockEntity.getCollisionShape`; the analogous guard returns early only when `progress < 1.0` and the thread-local direction equals `getMoveDirection()`. Otherwise the method builds the source-head state with the threshold property and obtains its collision shape; `PistonHeadBlock.getArmShape` consumes that property to choose a short/full voxel arm shape. The comparison appropriately labels this ordinary-provider reachability and expressly avoids claiming the complete player-query/traversal path or a movement-parity result.

## Findings

No concrete source or reasoning defects found. The alias closure, boundary values, raw-NBT counterexample, and stated reachability limits are supported by the exact pinned sources.

## Scope

This audit read the assigned comparison, the required discovery procedures, and exact source/provenance records only. It did not inspect product implementation/history, wiki material, prior audit reports, backlog, runtime, builds, or tests.
