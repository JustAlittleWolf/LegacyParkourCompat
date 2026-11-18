# Blind source audit: 1.21.8 to 1.21.9 direct contact effects

## Evidence identity

Host/source environment: Windows; canonical read-only Mojmap sources in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- **1.21.8:** `decompiled_minecraft/1.21.8/mojmap.ready.json` identifies `versionId=1.21.8`, `mapping=mojmap`, 6,105 files, and raw client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. `mojmap.sources.sha256` is bound by SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; `mojmap.provenance.json` records the exact Mojmap batch `1.21.8,1.21.10,1.21.11`. The canonical source files below match their manifest entries.
- **1.21.9:** `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` pins the raw client (`.../ce92fd8d1b2460c41ceda07ae7b3fe863a80d045/client.jar`, SHA-256 `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`), Mojmap client mappings (`.../3641ccb54eac2153c7e8274823c5a8e046beaba0/client.txt`, SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`), and exact release metadata `.../91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json` (SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`). Recorded source hashes match the canonical `HoneyBlock.java`, `WebBlock.java`, `SweetBerryBushBlock.java`, and `Entity.java` files.
- Both provenance records identify the same decompiler pipeline and tool versions (Vineflower 1.12.0, Java 25.0.3+9-LTS, Tiny Remapper 0.14.1, Mapping IO 0.9.1). This audit checks the named local evidence and source bodies; it does not claim pair-wide source coverage.

## Bounded trace

Assignment: dry, local, ordinary player movement; no riding, flying, portal or fluid path. Trace only Honey Block slide, cobweb stuck velocity, and sweet berry bush stuck velocity through the first ordinary `MoverType.SELF` movement. Damage evaluation, berry bush server damage, piston movement, geometry changes, support, attributes/sprint, fluid and portal movement, and full pair/runtime parity are outside this audit.

### Honey Block slide

`HoneyBlock.java`, 1.21.8 lines 63-70 and 81-116; 1.21.9 lines 63-70 and 81-116. Both versions use the same gate and math in `isSlidingDown`: reject grounded entities; reject `y > blockY + 0.9375 - 1.0E-7`; reject reconstructed old Y velocity `getOldDeltaY(dy) = dy / 0.98F + 0.08` when it is `>= -0.08`; then require either horizontal offset from block center, with `1.0E-7` tolerance, to exceed `0.4375 + bbWidth / 2.0F`.

When that gate passes, `doSlideMovement` reads the current velocity. If reconstructed old Y velocity is `< -0.13`, it multiplies X and Z by `-0.05 / getOldDeltaY(current dy)`; otherwise X and Z are retained. In either branch Y becomes `getNewDeltaY(-0.05)`, where `getNewDeltaY(v) = (v - 0.08) * 0.98F`. It then resets fall distance. `entityInside` runs this movement before `super.entityInside`. These local movement operations, their order, float constants/casts, comparisons and reset are identical in the two releases. The adjacent `fallOn` callback (1.21.8 and 1.21.9 lines 51-60) invokes fall-damage handling and is excluded from the requested no-damage trace.

### Cobweb

The Mojmap source class is `WebBlock.java`. In both versions (`entityInside`, lines 27-34), the default multiplier is `(0.25, 0.05F, 0.25)`. For a `LivingEntity` with WEAVING, it is `(0.5, 0.25, 0.5)`. The chosen vector is passed to `Entity.makeStuckInBlock`.

In both `Entity.java` versions (`makeStuckInBlock`: 1.21.8 lines 2715-2718; 1.21.9 lines 2783-2786), that call resets fall distance first, then stores the vector. In the first subsequent ordinary `move(MoverType.SELF, request)` (`Entity.move`: 1.21.8 lines 663-683; 1.21.9 lines 670-693), absent the excluded no-physics branch, the stored vector scales the requested displacement componentwise when its squared length is greater than `1.0E-7`; the code then clears the stored vector and sets entity delta movement to zero before edge adjustment and collision. SELF does not take the piston limiter/early-return branch. The assigned vector, condition, multiply/clear/velocity-zero order and first-move consumer are identical across the pair.

### Sweet berry bush

`SweetBerryBushBlock.java`, 1.21.8 lines 81-93; 1.21.9 lines 82-94. Both versions require a `LivingEntity` whose type is neither FOX nor BEE, then call `makeStuckInBlock` with `(0.8F, 0.75, 0.8F)`. Thus the local effect stores the same vector and resets fall distance, followed on the first ordinary SELF move by the same componentwise displacement scaling and clear/zero sequence described above. The later `ServerLevel`/age/movement gate calls `hurtServer`; it is not part of this local direct movement trace.

### Producer order and reachable witness

For all three blocks, the scoped callback is reached from `Entity.checkInsideBlocks` after a recorded movement intersects the block's inside-collision shape; in 1.21.8 this calls `BlockState.entityInside` at `Entity.java` lines 1138-1161, and in 1.21.9 at lines 1164-1203. `applyEffectsFromBlocks` calls this traversal before applying its collected inside effects (1.21.8 lines 785-835; 1.21.9 lines 798-848). A concrete witness is a local living player whose ordinary movement intersects a honey block while airborne, below its top threshold and far enough from center while descending faster than the gate; the same contact traversal also reaches a web or eligible berry bush when its collision shape is crossed. The block callback writes state during this post-movement contact processing. On the next ordinary SELF move, `Entity.move` consumes any stored stuck multiplier before edge adjustment/collision. This demonstrates a reachable direct local path without requiring the excluded piston path. Exact player travel inputs and full tick schedule are not audited here.

## Verdict

No change was found in the requested direct contact effects between 1.21.8 and 1.21.9: honey slide Y replacement, conditional horizontal scaling, and fall-distance reset are unchanged; web and berry vectors, their eligibility gates, `makeStuckInBlock` order, and first ordinary SELF-move consumption are unchanged. This is a scoped finding of equivalence only, not full-pair coverage or runtime parity.