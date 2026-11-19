# Independent source audit: 1.21 client WATER body admission

**Verdict: ACCEPT** — the scoped body-water alias/path prerequisite is supported in all eleven exact Mojmap source sets, 1.21 through 1.21.10.

## Immutable claim reviewed

- Draft: `body-admission-121-alias-source/evidence.md`
- Draft commit: `3c19ebd43e97881e18290b02546bc5fb1177a9d5`
- SHA-256 of the exact committed blob bytes: `b63c2683b38a0906a8cf81c71ea0e346d946cc4abb0afc6cdacdb8c99c3b399d`
- Scope: `Entity.updateFluidHeightAndDoFluidPushing`, `Entity.touchingUnloadedChunk`, `ClientLevel.hasChunk`, `LevelReader.hasChunksAt`, `FluidState.getHeight`, source height helper, and the supplied client geometry only.

## Audit result

The eleven source directories are the exact versions named in the draft, each under `decompiled_minecraft/<version>/mojmap`. I verified the source-set identity for `Entity.java`, `ClientLevel.java`, `LevelReader.java`, and `FluidState.java` against each nested provenance source map or sibling `mojmap.sources.sha256` manifest. For sibling-marker sets, the ready marker is `status=ready` and names the same exact version and `mojmap`; its source-manifest digest matches the manifest bytes. The nested-provenance sets bind the same four files to their current bytes. Every check passed. The 1.21.9 sibling `.mojmap.staging-*` directory was excluded.

Normalized UTF-8/LF method text hashes independently confirmed identical bodies across all eleven releases:

| Method | SHA-256 |
|---|---|
| `Entity.updateFluidHeightAndDoFluidPushing` | `6f4c4bfe6c55984652aee9c5ec26feeeee10c5e4261c7e4259d429f7647aa260` |
| `Entity.touchingUnloadedChunk` | `0a3276e2c51992bcee191cc3fccee02ff7d54c7f3ca6212ffc1960227a8bf2ec` |
| `ClientLevel.hasChunk` | `603e83121a0fb0d4e1a56cf71d232e3395daa8dad6a49f3823ae464649915dee` |
| `LevelReader.hasChunksAt(int,int,int,int)` | `9a695cc761c29e591773b4589976e77aaac38abecd5f18ffb1ed0f296a85d163` |
| `FluidState.getHeight` | `6a544179c0bbcda4f69c39a27d3bb78a523be2d3190732991fd1b6b8d40b2a23` |
| `FlowingFluid.getHeight` | `5c5db52d4baf4ba3905c880d5217a5af86aa6c40a0185da2e2b22bb6d672fa06` |
| `FlowingFluid.getOwnHeight` | `2d8bc06763c050f2c04f056d72e28c0826312e2ee3386a9583de4d0c8547ff1d` |

`updateFluidHeightAndDoFluidPushing` first exits only when `touchingUnloadedChunk()` is true. Otherwise it scans the `getBoundingBox().deflate(0.001)` coordinates, tests the fluid tag, computes `double surface = blockY + fluidState.getHeight(...)`, and admits the cell with inclusive `surface >= deflatedBox.minY`. `FluidState.getHeight` returns `float`; thus the `int + float` operation is evaluated as `float` before assignment widens it to `double`. The flowing-fluid helper returns `1.0F` when the same fluid is above, otherwise the state's positive own height.

For the supplied standing player at `(15.7,64,8.5)`, using width `0.6` and height `1.8`, the box is x `[15.4,16.0]`, y `[64,65.8]`, z `[8.2,8.8]`. After deflation it scans x=15, y=64..65, z=8. Therefore loaded WATER at `(15,64,8)` is included, and its positive surface is above `minY=64.001`. The padded unloaded-chunk probe inflates by `1.0`, reaches block x through 17, and `LevelReader.hasChunksAt` iterates chunk endpoints inclusively. `ClientLevel` overrides `hasChunk` to return `true` unconditionally in every examined release; virtual dispatch therefore prevents a missing padded neighbor from rejecting this client body scan. That alias does not itself establish whether any chunk is loaded.

## Exact source locations

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

| Version | `Entity.updateFluid...` / `touchingUnloadedChunk` | `ClientLevel.hasChunk` | `LevelReader.hasChunksAt(int,int,int,int)` | `FluidState.getHeight` | `FlowingFluid.getHeight` |
|---|---:|---:|---:|---:|---:|
| 1.21 | `Entity.java:3091` / `3158` | `ClientLevel.java:311` | `LevelReader.java:195` | `FluidState.java:49` | `FlowingFluid.java:430` |
| 1.21.1 | `Entity.java:3091` / `3158` | `ClientLevel.java:311` | `LevelReader.java:195` | `FluidState.java:49` | `FlowingFluid.java:430` |
| 1.21.2 | `Entity.java:3219` / `3286` | `ClientLevel.java:316` | `LevelReader.java:194` | `FluidState.java:50` | `FlowingFluid.java:434` |
| 1.21.3 | `Entity.java:3219` / `3286` | `ClientLevel.java:316` | `LevelReader.java:194` | `FluidState.java:50` | `FlowingFluid.java:434` |
| 1.21.4 | `Entity.java:3243` / `3310` | `ClientLevel.java:319` | `LevelReader.java:194` | `FluidState.java:50` | `FlowingFluid.java:434` |
| 1.21.5 | `Entity.java:3241` / `3308` | `ClientLevel.java:323` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:434` |
| 1.21.6 | `Entity.java:3414` / `3481` | `ClientLevel.java:340` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:434` |
| 1.21.7 | `Entity.java:3414` / `3481` | `ClientLevel.java:340` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:434` |
| 1.21.8 | `Entity.java:3414` / `3481` | `ClientLevel.java:340` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:434` |
| 1.21.9 | `Entity.java:3480` / `3547` | `ClientLevel.java:384` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:439` |
| 1.21.10 | `Entity.java:3485` / `3552` | `ClientLevel.java:384` | `LevelReader.java:198` | `FluidState.java:53` | `FlowingFluid.java:439` |

`FlowingFluid.getOwnHeight` is adjacent to its listed `getHeight` helper. It returns `getAmount() / 9.0F`; the `getHeight` helper returns `1.0F` when the same fluid is above and otherwise returns that positive own height.
This verdict accepts only the stated 1.21 client body-water applicability bridge. It establishes no 1.21 patch boundary and makes no claim about other fluids, current/eye fluid, other movement inventory, or runtime parity.