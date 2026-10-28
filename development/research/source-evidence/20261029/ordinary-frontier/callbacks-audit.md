# Independent callback-phase audit: 1.17.1 → 1.18.2

## Verdict

**ACCEPT, bounded claim.** Independent inspection confirms that 1.18.2 `Entity.move` adds a conditional `FALLDAMAGE_RESETTING` ray query and `resetFallDistance()` after collision clipping and before the clipped-position write. The 1.17.1 method has no corresponding ray/reset between `collide` and its position write. In both versions, `LocalPlayer.move` calls `super.move` before its auto-jump update. The 1.18.2 change therefore precedes `checkFallDamage`, landing/step callbacks, movement emissions, inside-block callbacks, and horizontal block-speed damping. This verifies source order only; it does not establish a state/shape witness, runtime outcome, or pair freeze.

## Independent source checks

Canonical read-only endpoint: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.17.1,1.18.2}\mojmap`.

- Source manifests: 1.17.1 `mojmap.sources.sha256` SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 1.18.2 SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`.
- `1.17.1/net/minecraft/world/entity/Entity.java`, SHA-256 `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; manifest entry agrees.
- `1.18.2/net/minecraft/world/entity/Entity.java`, SHA-256 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`; manifest entry agrees.
- `1.17.1/net/minecraft/client/player/LocalPlayer.java`, SHA-256 `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`; manifest entry agrees.
- `1.18.2/net/minecraft/client/player/LocalPlayer.java`, SHA-256 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; manifest entry agrees.

`Entity.move` 1.17.1 lines 534–559: after `Vec3 vec32 = this.collide(vec3)`, `vec32.lengthSqr() > 1.0E-7` directly guards the position write at lines 555–557. No fall-distance check, reset, or `FALLDAMAGE_RESETTING` query appears between clipping and this write. Rest processing follows; `checkFallDamage` is at line 566, then `updateEntityAfterFallOn` (581), `stepOn` (585), `tryCheckInsideBlocks` (629), and block-speed multiplication (631).

`Entity.move` 1.18.2 lines 563–575: it stores `double $$3 = $$2.lengthSqr()` after `collide`. Inside the existing `$$3 > 1.0E-7` position-write guard, the new nested predicate is ordered as: `fallDistance != 0.0F` and `$$3 >= 1.0`. Only then it clips from `this.position()` to `this.position().add($$2)` using `ClipContext.Block.FALLDAMAGE_RESETTING` and `ClipContext.Fluid.WATER`. A result whose type is not `MISS` calls `resetFallDistance()`, then `setPos` writes the clipped position. The lower bound is inclusive (`>= 1.0`); the position-write bound is strict (`> 1.0E-7`).

After the position write, 1.18.2 sets collision/ground flags (579–590), gets support state, and calls `checkFallDamage` (593). If not removed, it calls `updateEntityAfterFallOn` when requested Y differs from clipped Y (603–605), `stepOn` when on ground and not stepping carefully (607–609), then movement emissions, `tryCheckInsideBlocks` (652), and block-speed-factor horizontal multiplication (653–654). Thus the reset precedes all those later effects/callbacks.

`LocalPlayer.move` remains the same wrapper: 1.17.1 lines 893–898 and 1.18.2 lines 889–894 save starting X/Z, invoke `super.move`, then pass actual resulting horizontal displacement to `updateAutoJump`. Auto-jump is after `Entity.move`'s rest callbacks return. No local-player-specific reorder was found in these methods.

## Comparison to author closure

After completing the independent check above, I read `.native-source/20261028/frontier-c/1171-1182-player-callback-phases/closure.md` (SHA-256 `93af3b4837a119435c417c247388724c889b7e6464a3b694c63f911bac94acbb`) and its `source-hashes.tsv` (SHA-256 `262155a268a913a875bea676b2f214bad022f295776564db91e698c0f54d4ef4`). The bounded reset predicate, strict/inclusive movement thresholds, ray endpoints/Block and Fluid modes, non-MISS gate, LocalPlayer wrapper order, and relative callback ordering agree with my independent read. No discrepancy in the assigned claim.

## Limits and open dependencies

This audit does not inspect or certify ray traversal, collision shapes, block/fluid predicate registration, or a concrete witness state; those remain provider dependencies. It does not independently establish full pair coverage or freeze, nor client trajectory/runtime parity. Author's broader inside-block, piston, and auto-jump-provider statements are outside this audit's acceptance except for the caller ordering explicitly checked above.