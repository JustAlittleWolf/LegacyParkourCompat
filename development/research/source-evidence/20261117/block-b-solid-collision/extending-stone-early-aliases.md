# Extending Stone plate across the 1.8 aliases

This supplements the [moving Stone collision-plate contract](extending-stone-moving-piston-plate.md). The independent early-identity review by `/root/early_moving_stone_identity_bundle` confirmed that the exact early releases use the same destination-owned, previous-progress plate. The mapping-local Feather Gen2 build 2 provenance for 1.8 is SHA-256 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`; 1.8.1 is `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca`. Their entries match the inspected source bytes, including `Block.java` and `StoneBlock.java`.

In 1.8 and 1.8.1, Feather `PistonBaseBlock.move` (lines 312–319) stores ordinary moved Stone as a non-source moving entity at the destination. `MovingBlockEntity.getProgress` and `tick` (lines 54–60,128–147) preserve the sampled prior value and the two 0.5F increments. `MovingBlock.getCollisionShape` (lines 118–129,162–195) calls that progress read with zero interpolation and subtracts the float extension distance on only the facing side of the Stone box. The ordinary Stone class inherits the full-unit `Block.getCollisionShape` provider in both releases. The same source operation persists at every exact stable 1.8 patch.

| Source relative to `net/minecraft/block` | 1.8 and 1.8.1 SHA-256 | 1.8.2 through 1.8.9 SHA-256 |
| --- | --- | --- |
| `MovingBlock.java` | `614cc52b357152f6f515b33ea59440dfe338227ea0ce4e7dac7e43a13a95ba7d` | `700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85` |
| `entity/MovingBlockEntity.java` | `37b472840fe4e9ac2427b293bf21eb0a3979d7c73137612db1d048d40c891e4b` | `05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6` |
| `PistonBaseBlock.java` | `a103503013c2919ead9b7ad411098ff12cd352b3a4e734815f8ed87058ce9227` | `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929` |
| `StoneBlock.java` | 1.8 `ed355e628e26dad8f70b4be3a157d82f0755e4219edd4eba9810a54990b16baa`; 1.8.1 `956b3d455bbe9f007213479f62b7afe0d13720023668c7b5567e4de1c24deca8` | `ca15180da4a946055afe68f8c6c578070fae700d13b50b4015272d772ddeed23` |

The identical source behavior across the 1.8 profiles supports the existing `V1_8_9` resolver boundary for this positive-thickness plate. The product scope is ordinary player self movement at native `(progress, progressO) = (1.0F, 0.5F)`. Direct pushes and the zero-thickness plane remain outside the integrated operation. The result is a verified source and projection boundary. Runtime movement parity remains unverified.
