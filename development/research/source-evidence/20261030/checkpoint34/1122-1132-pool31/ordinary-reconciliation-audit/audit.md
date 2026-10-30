# Blind audit: ordinary reconciliation supplement

## Scope and immutable identities

Audited only `.native-source/20261030/1122-1132-pool31/ordinary-core/reconciliation-supplement.md` and the canonical read-only `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` source trees for `1.12.2`, `1.13`, `1.13.1`, and `1.13.2`, all `ornithe-feather`. The audited supplement SHA-256 is `31d36a26d14cf9e8268a15935b679a35d86b186751b3213f541b2f22a58936d3`, matching the assigned identity.

Ready/provenance markers identify each exact release and `ornithe-feather` build. Their actual marker hashes, along with the ready-marker manifest hashes, are:

| Release | Ready marker SHA-256 | Provenance marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` |
| 1.13 | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |

The Feather mapping artifact manifest entries record the expected build-2 merged JAR and Tiny hashes for all four releases (1.12.2: `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`, `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`; 1.13: `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`, `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84`; 1.13.1: `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`, `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14`; 1.13.2: `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`). These are manifest-recorded artifact hashes; this audit independently recomputed the source-manifest and source-file hashes, not the absent cached mapping payloads. The following relevant source-file SHA-256 values match both the supplement and the corresponding source-manifest entries:

| File | 1.12.2 | 1.13.2 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |

For intermediate ground-acceleration checks, `LivingEntity.java` hashes are 1.13 `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` and 1.13.1 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; both match their source manifests. This is a bounded claim audit, not pair-coverage closure or a freeze verdict.

## Operation verdicts

### A — water-jump selection gate: ACCEPT, with cached-height qualification

In 1.12.2, `LivingEntity.mobTick()` 1827–1836 routes a held jump to `jumpInWater()` when `isInWater()`, then lava, then ordinary jump only for `onGround && jumpingCooldown == 0`. `jumpInWater()` 1413–1415 adds `0.04F`. In 1.13.2, `mobTick()` 1903–1917 reads `f_85121000`: ordinary/lava handling is entered when height is zero or when grounded with height at most `0.4`; inside it, ordinary jump requires `(onGround || (height > 0 && height <= 0.4)) && cooldown == 0`. Otherwise it selects the water-fluid impulse. The impulse helper at 1470–1472 also adds `0.04F`; the impulse itself is not new.

The summary is right that grounded shallow fluid can take the ordinary-jump branch and that airborne positive fluid height selects the fluid impulse. Preserve the entire nested gate: the ordinary jump is cooldown-gated, while the water impulse branch is not. Height is written at `Entity.m_69693160()` 2569 after the fluid scan, but this is not an unconditional live refresh: an unloaded scan area returns at 2517–2518 before the write, and the boat branch in `checkWaterState()` 961–964 bypasses the scan and only clears `inWater`. The height can therefore remain cached in those cases. Treat the gate as reading the stored height, not as a direct fresh-water predicate. The ordinary fluid result and +0.04F comparison are accepted.

### B — existing-water vertical gravity update: ACCEPT

`LivingEntity.moveRelative()` 1.12.2 1573–1580 moves, applies vertical drag `*= 0.8F`, then subtracts `0.02` if gravity is enabled. In 1.13.2 1639–1649 the order remains move, drag, then gravity; gravity is skipped for sprinting or no-gravity. With ordinary `d=0.08`, it subtracts `d / 16.0` (0.005), except the stated near-zero comparison snaps to `-0.003`. If falling Slow Falling selects `d=0.01`, the decrement is `0.000625`; that status-effect branch is separate from the baseline existing-water change. The supplement's scope and amounts are supported.

### C — local direct-sprint shallow-water behavior: ACCEPT, with swimming conditional retained

In 1.12.2 `LocalClientPlayerEntity.mobTick()` 734–745, direct sprint-key start has no water predicate and stop checks forward input, horizontal collision and food/flight only. In 1.13.2 738–746 the direct-key start requires `!isInWater() || isSubmergedInWater()`. Stop computes shallow-water as part of `bl7` at 748–750, but applies `bl7` only in the nonswimming `else` at 755–757. Swimming uses its separate condition at 751–754: sprint is stopped for its stated airborne/non-sneaking low-forward-or-food case, or when water is lost. Thus an already-swimming player can retain sprint during a shallow-water transition. The double-tap branch at 723–736 is separate and newly includes submerged-water eligibility; it should not be folded into the pre-existing direct-key operation. The LocalClientPlayer gate delta is accepted, but the exact contact interval it sees depends on the changed `isInWater()` producer in D; do not attribute the whole reachable shallow-water boundary to this local gate alone. The supplement's qualification is accurate.

### D — water-contact predicate and height producer: ACCEPT, correct old-surface wording

In 1.12.2 `Entity.checkWaterCollisions()` 948–963 retains the boat exception and calls `World.applyLiquidDrag()` with `getShape().grown(0,-0.4F,0).contract(0.001)`. `Box.grown()` 132–139 subtracts `dy` from `minY` and adds it to `maxY`, so the negative value contracts by 0.4 at both vertical faces before the additional 0.001 contraction. The separate `checkWaterState()` 944–946 uses `grown(0,-20,0)` and is not that contact box.

In 1.13.2 `checkWaterState()` 961–977 calls the tagged-fluid scan, which contracts the entity box by 0.001 in all axes; `m_69693160()` 2510–2570 tests each matching fluid surface against `box.minY`, records maximum depth and returns contact. This scan also accumulates current (operation E), so the two outputs are coupled in the newer method. Correction: describe the old surface condition narrowly. `World.applyLiquidDrag()` 1580–1605 compares the cell's fluid top to `ceil(bounds.maxY)` (`l >= d`), while bounding the sampled Y cells using floor/ceil of the query box; it does not perform the newer explicit fluid-surface-versus-`box.minY` test. The D contact-geometry and predicate difference is accepted with that wording correction and with the shared-provider limitation below.

### E — fluid-current force: SOURCE DELTA ACCEPTED; OWNERSHIP/DEPENDENCY OPEN

The cited endpoint mechanics are supported. In 1.12.2 `World.applyLiquidDrag()` 1594–1618 aggregates `Block.applyMaterialDrag()` results; if nonzero and `entity.hasLiquidCollision()`, it normalizes the result and adds `0.014` times it to velocity. In 1.13.2 `Entity.m_69693160()` 2522–2567 reads fluid flow only when `hasLiquidCollision()` is true, adds qualifying samples, scales a sample when current maximum depth is below `0.4`, averages by sample count, normalizes only for non-players, and adds the 0.014-scaled vector when nonzero. This supports the stated PlayerEntity possibility without establishing provider equivalence.

Do not publish E as an independently reconciled claim yet. The 1.13.2 force and D's contact/depth output share the same scan; its eligibility also depends on `hasLiquidCollision`, fluid-flow providers, and the sampled block/fluid contracts. This audit was not authorized to read the collision/fluid lane artifact, so it cannot determine whether an existing lane claim already owns this operation. Cross-lane ownership and provider dependencies remain unresolved; reconcile once against that lane's exact evidence and avoid duplicate ownership.

### F — grounded acceleration float literal: ACCEPT

The grounded scaling uses `0.16277136F` in 1.12.2 `LivingEntity.moveRelative()` 1485–1488 and `0.16277137F` in 1.13.2 1545–1549. The surrounding logic applies `getSpeed() * u` when grounded; the literal's calculated `u` is not the airborne acceleration input. Exact intermediate sources confirm `0.16277137F` in 1.13 at line 1526 and 1.13.1 at line 1545; 1.13.1 and 1.13.2 `LivingEntity.java` hashes are identical. The two literals round to adjacent binary32 values (bit patterns `3E26AD89` and `3E26AD8A`). This establishes presence by 1.13 and persistence through 1.13.2, but not an exact patch boundary inside 1.12.2–1.13.

## Reconciliation limits

Operations A–D and F are accepted as bounded endpoint source comparisons with the qualifications recorded above. E's mechanical comparison is supported, but its independent ownership/dependency remains open. The 1.13-only swimming behaviors listed in the supplement are properly distinguished from these pre-existing operations. This audit does not close the pair inventory, establish full-pair freeze, establish exact introduction patches beyond F's checked intermediate presence, or assess runtime parity.
