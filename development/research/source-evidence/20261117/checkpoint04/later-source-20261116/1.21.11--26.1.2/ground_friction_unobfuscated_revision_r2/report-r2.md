# S5.2 ground-support friction and speed factor — source comparison

## Assignment and bounded verdict

Pair: Minecraft **1.21.11 (`mojmap`) → 26.1.2 (`unobfuscated`)**. The mapping identities are intentionally different: 26.1.2's canonical published source namespace is `unobfuscated`, not Mojmap. This report covers only support-position selection for movement-affecting block reads, the ordinary LivingEntity in-air/ground-friction path, player block-speed-factor lookup/consumption, and source-backed friction/speed-factor property defaults and accessors.

**Verdict: NO DIFFERENCE OBSERVED in the examined S5.2 behavior.** Paired executable expressions, constants, conditions and call order are equivalent in this slice. This is not a full-pair freeze, an implementation recommendation, or a runtime parity claim.

## Endpoint identity and hash-bound evidence

The supplied earlier gate packet `build/later-source-20261116/1.21.11--26.1.2/ground_friction/report.md` has SHA-256 `a9078b6fbf37470f850dc22b53e2ba105a8a9445dd07e105a0204c6dfe84163e`.

- `decompiled_minecraft/1.21.11/mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; mapping `mojmap`, version `1.21.11`, status `ready`, manifest digest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- `decompiled_minecraft/1.21.11/mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; exact run records `--versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap`.
- `decompiled_minecraft/1.21.11/mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- `decompiled_minecraft/26.1.2/unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; mapping `unobfuscated`, version `26.1.2`, status `ready`, manifest digest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`.
- `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; exact run records `--versions=26.1.2 --mappings=unobfuscated`.
- `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256` SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`.

Individually recomputed source SHA-256 values matched each corresponding endpoint manifest entry:

| Relative source file | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/level/block/Block.java` | `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |

## Compared evidence and result

- **Movement support block selection:** 1.21.11 `Entity.java:957-982, 990-998`; 26.1.2 `Entity.java:986-1013, 1021-1031`. In both, `getBlockPosBelowThatAffectsMyMovement()` calls `getOnPos(0.500001F)`. `getOnPos(offset)` prefers `mainSupportingBlockPos` when present, otherwise floors position X/Z and `Y - offset`. With a supporting position, the code reads its state and returns either the original support position for wall/fence-gate cases or a Y-adjusted position. The same thresholds, wall/fence tag tests, fence-gate type check and `Mth.floor` operation appear in both. No tag membership is asserted here.
- **Ground friction and use:** 1.21.11 `LivingEntity.java:2343-2347, 2534-2543, 2578-2580`; 26.1.2 `LivingEntity.java:2430-2434, 2623-2632, 2667-2669`. Both obtain the same movement-support position; choose support block friction only when `onGround()` (otherwise `1.0F`); multiply that value by `0.91F`; and pass the raw block friction to `handleRelativeFrictionAndCalculateMovement`. That method uses `getSpeed() * (0.21600002F / (friction * friction * friction))` when grounded, otherwise `getFlyingSpeed()`, then calls `moveRelative` before movement. Post-move horizontal velocity uses `friction * 0.91F` unless `shouldDiscardFriction()`; vertical friction retains the same `FlyingAnimal` conditional and `0.98F` fallback. No order, gate, cast, constant or numeric type change was observed in this bounded path.
- **LivingEntity/Player speed-factor path and consumption:** 1.21.11 `LivingEntity.java:494-497`, `Player.java:1837-1841`, `Entity.java:990-998, 776-778`; 26.1.2 `LivingEntity.java:490-493`, `Player.java:1831-1835`, `Entity.java:1021-1031, 795-797`. `LivingEntity.getBlockSpeedFactor()` overrides the inherited entity behavior with `Mth.lerp((float)this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), super.getBlockSpeedFactor(), 1.0F)` on both sides; the expression, cast, argument order and endpoints match. The Player override returns `1.0F` while flying or fall-flying, otherwise delegates through `super` to this LivingEntity override, identically on both sides. The base entity lookup reads the block at `blockPosition()` first; for blocks other than water and bubble columns, a `1.0` value falls back to the movement-support block's speed factor, otherwise the in-block value wins. The movement code then multiplies X/Z delta movement by the selected factor and leaves Y unchanged. The lookup, LivingEntity interpolation, Player gates, water/bubble-column exceptions, fallback equality check and consumption expression match. Full `Entity.move` control flow was not audited as part of this slice.
- **Source-backed property providers:** 1.21.11 `BlockBehaviour.java:95-97, 104-115, 960-962, 1069-1076` and `Block.java:463-469`; 26.1.2 `BlockBehaviour.java:93-95, 102-114, 986-988, 1097-1104` and `Block.java:502-508`. Both store friction, speed factor and jump factor from `Properties`, whose defaults are respectively `0.6F`, `1.0F`, `1.0F`; the fluent setters assign the supplied floats and `Block` getters return the stored fields. `Blocks.java` has matching source initializer literals for the sampled explicit friction/speed-factor providers (including `0.98F`, `0.4F`, slime `0.8F`, ice `0.98F`, blue ice `0.989F`, and honey `0.4F`). These are source-level initializer values only, not a claim about effective block/tag membership.

## Coverage boundary, dependencies and next gate

- **Examined:** endpoint readiness/provenance/manifest identities; manifest integrity of the six listed Java files; support-position selector; grounded friction acquisition and use; LivingEntity and Player speed-factor overrides; base speed-factor lookup and movement-component multiplication; property defaults, setters/getters, and matching explicit `Blocks` initializer literals noted above.
- **Partial:** source provider sampling in `Blocks.java` was limited to matching explicit literals found by `friction`/`speedFactor` search. No complete inventory of all block-specific providers was undertaken.
- **Unexamined:** packaged tag/resource contents and effective membership for `BlockTags.FENCES`/`WALLS`, full `Entity.move` gates around speed-factor consumption, other ordinary travel branches/callbacks, and all other movement slices in the pair.
- **Open dependency:** tag/resource membership and resulting effective membership remain unverified; no inference is made from identifiers or source references alone.
- **Next gate:** independent S5.2 audit may retrace the lines and source identities above; whole-pair freeze still requires its separately assigned complete coverage inventory and independent coverage audit. This bounded report does not request or perform implementation, build, tests, gameplay/runtime or decompilation.

