# Independent source audit: redstone torch wall predicate

Verdict: ACCEPT

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_redstone_torch_projection/report.md`
Report SHA-256: `11ffd2550003c9d6d5b49d51e9a2705587b22f1ccd339f31ddf74161b6409d8f`

## Verification

- Exact source manifests hash to 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; both ready markers bind those same source identities.
- Independently rehashed the report’s cited `TorchBlock.java`, `RedstoneTorchBlock.java`, `Block.java`, `WallBlock.java`, `BlockState.java`, registration files, and `Properties.java`; these match the canonical files and their source-manifest entries. The unexamined wall crosswalk input SHA cited by the report also matches the supplied roster identity.
- Both registrations use `RedstoneTorchBlock` with `.noCollision()`. `RedstoneTorchBlock` declares only `LIT` as a state property, yielding the two registered states `false` and `true` at both endpoints.
- At 1.13.2, `RedstoneTorchBlock` inherits `TorchBlock.getFaceShape`, which returns `FaceShape.UNDEFINED`. The old wall generic arm requires `SOLID`; the thick/middle-pole arm does not match this shape. Both states therefore yield false in every horizontal direction.
- At 1.14.4, the no-collision registration makes the base collision shape empty. `BlockState.m_87223014` consults the face-solid cache or `Block.isFaceSolid`, which tests the collision shape, so the generic wall face input is false. The key is absent from the pinned `WALLS` payload and is not a fence gate; the other wall arm is false. The floor outline shape inherited from `TorchBlock` does not affect collision face solidity.
- The report correctly keeps `redstone_torch` distinct from plain `torch` and `redstone_wall_torch`.

## Scope

ACCEPT is limited to the `minecraft:redstone_torch` wall-neighbor predicate for its two `LIT` states and four horizontal directions. It establishes no player trajectory or movement-impact result and does not close the pair or its remaining state, provider, callback, or tick dependencies.
