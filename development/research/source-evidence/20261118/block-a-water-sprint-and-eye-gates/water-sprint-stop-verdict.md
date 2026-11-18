# Independent source verdict: water sprint stop

Verdict: ACCEPT, scoped to the reviewed claim below.

## Immutable reviewed draft

- Author commit: `a3ecce92c9637e1581e858034ecbc14ea02a0044`
- Path: `development/research/findings/1.12.2--1.13.2/water-sprint-stop.md`
- SHA-256: `54df18a134f04a7baa2e8728158d1854bd9a5bca2369bfaa64c96c8714d79535`

## Exact source identities

Canonical local decompilations are Ornithe Feather. Each ready record reports `status: ready`, the exact version ID and mapping `ornithe-feather`.

| Version | Source manifest SHA-256 | Artifact manifest SHA-256 | Client SHA-256 | LocalClientPlayerEntity.java SHA-256 |
|---|---|---|---|---|
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13 | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| 1.13.1 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

## Reviewed claim

The cited `LocalClientPlayerEntity.mobTick()` ranges are correct: 1.12.2 lines 743–745; 1.13 lines 748–758; 1.13.1 and 1.13.2 lines 748–758. In 1.12.2 sprint stopping checks forward input below `0.8F`, horizontal collision, or `!bl5` (food eligibility, with flight allowance). In 1.13, `bl6` retains the forward/food predicates and `bl7` adds `isInWater() && !isSubmergedInWater()` alongside horizontal collision; the ordinary branch uses `bl7`. The swimming branch instead stops for `(!onGround && !sneaking && bl6) || !isInWater()`, with Java `&&` precedence. This supports the observed 1.13 change while retaining the input and food gates as separate predicates. The cancellation code is unchanged in 1.13.1 and 1.13.2.

The reachable surface-water distinction is also supported: `Entity.baseTick()` refreshes body-water state and submerged state before `LivingEntity.tick()` invokes `mobTick()`. `PlayerEntity.tick()` updates the player's visible submersion field before `super.tick()`, and the local client player accessor reads that field. `Entity.isSubmergedInWater()` combines submerged state with `isInWater()`; the fluid submersion check samples at `y + getEyeHeight()` and tests fluid height. A player can therefore be in body water without eye submersion. In that state the ordinary branch stops sprinting, while the swimming branch can retain it when its own gates permit.
