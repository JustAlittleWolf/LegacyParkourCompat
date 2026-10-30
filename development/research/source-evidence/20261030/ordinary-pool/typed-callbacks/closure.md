# Typed callback reuse: Honey and Web

## Scope and conclusion

This source-only closure covers only the `HoneyBlock` and `WebBlock` `entityInside` callbacks in 1.21.2/1.21.3 versus 26.2. The paired 1.21.2 and 1.21.3 files inspected below are byte-identical. **For these two callback bodies, 26.2 can be reused directly at the callback-body level in the historical synchronous dispatch path:** neither body registers a typed effect, and their immediate entity responses and gates match the old source. This does not accept other callbacks, eligibility/traversal changes, or full pair coverage.

The historical dispatch contract is synchronous: after the old sweep accepts a block position, it calls `BlockState.entityInside(level, pos, entity)`, then virtual `onInsideBlock(state)`, and adds the state to the `blocksInside` summary only after both return. If either callback throws, summary insertion is skipped and the old caller wraps the error. A compat path reusing these callbacks must preserve that order and the independently established old position/shape eligibility; modern stage collection is not needed for either callback.

## Callback comparison

- **Honey.** Old and modern `isSlidingDown` use the same ordered gates: entity is not on ground; entity Y is at most `pos.y + 0.9375 - 1.0E-7`; reconstructed old vertical speed is below `-0.08`; and either horizontal center offset exceeds `0.4375 + entity width / 2.0F` with `1.0E-7` tolerance. On success both run the same achievement check, then set delta movement using the same `getOldDeltaY` / `getNewDeltaY` formulas and `-0.13` threshold, reset fall distance, then run the same slide sound/event gates. Both then call the superclass `entityInside`; `BlockBehaviour.entityInside` is empty at both endpoints. The 26.2 `effectApplier` and `isPrecise` parameters are unused. Thus the Honey entity and immediate side-effect response is callback-body equivalent for this reuse question.
- **Web.** Both methods choose `(0.25, 0.05F, 0.25)`, or `(0.5, 0.25, 0.5)` when the entity is a `LivingEntity` with `WEAVING`, and immediately call `entity.makeStuckInBlock(state, multiplier)`. Neither method reads or registers through the 26.2 applier/precision parameters. The old and modern `Entity.makeStuckInBlock` bodies have the same order: `resetFallDistance()`, then assign `stuckSpeedMultiplier`. `resetFallDistance` stores zero in both versions (written as `0.0F` in the old source and `0.0` in modern source, matching the respective field representation). The Web response is immediate and equivalent for the scoped callback.

## Minimal source-faithful reuse order

For an old-sweep position that passes the historical eligibility gate: call the block state's Honey or Web callback synchronously; then call `onInsideBlock(state)` synchronously; only after both return, add that state to the historical `blocksInside` summary. Preserve old callback exception wrapping. Do not introduce an effect-collector registration or flush for these callbacks. This finding does not change the old shape test or DDA visitation sequence, and does not authorize the 26.2 `Entity.checkInsideBlocks` traversal wholesale.

## Evidence and immutable identities

Reviewed inputs:

- `.native-source/20261030/dda-collector-flush/report.md` — SHA-256 `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e`.
- `.native-source/20261030/dda-collector-flush-audit/audit.md` — SHA-256 `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba`.

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Source | SHA-256 |
| --- | --- |
| `1.21.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java` | `40bf4e5d55f44f7aec9716a46b11cd6ad08ed861be4d63a85544a1bfb595a71d` |
| `1.21.2/mojmap/net/minecraft/world/level/block/WebBlock.java` | `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414` |
| `1.21.2/mojmap/net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `1.21.2/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` |
| `1.21.3/mojmap` versions of the four files above | byte-identical to 1.21.2; hashes respectively `40bf4e5d55f44f7aec9716a46b11cd6ad08ed861be4d63a85544a1bfb595a71d`, `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414`, `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`, `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` |
| `26.2/unobfuscated/net/minecraft/world/level/block/HoneyBlock.java` | `ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa` |
| `26.2/unobfuscated/net/minecraft/world/level/block/WebBlock.java` | `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5` |
| `26.2/unobfuscated/net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `26.2/unobfuscated/net/minecraft/world/level/block/state/BlockBehaviour.java` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |

Relevant locations: old `HoneyBlock.java:62–70, 80–115, 117–127`; modern `HoneyBlock.java:64–79, 89–105, 114–137`; old `WebBlock.java:26–33`; modern `WebBlock.java:27–41`; old `Entity.java:1052–1072, 2490–2492, 2525–2528`; modern `Entity.java:1346–1360, 2908–2910, 2943–2946`; base no-op `BlockBehaviour.entityInside`, old `BlockBehaviour.java:350–351`, modern `BlockBehaviour.java:377–385`.

## Remaining gates

This is an independently reviewable callback-reuse finding only. It does not close the pair, establish any version introduction boundary, accept candidate generation/shape/cutoff semantics, or prove runtime trajectory parity. A blind independent review remains required before treating the finding as accepted for implementation.
