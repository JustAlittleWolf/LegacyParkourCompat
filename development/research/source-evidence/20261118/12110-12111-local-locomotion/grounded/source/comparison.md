# 1.21.10–1.21.11 grounded local locomotion source comparison

Result: no in-scope behavior difference established for ordinary dry, grounded, non-riding player movement in the assigned operations. The one changed input expression is equivalent for pre-existing item-use defaults; its non-default built-in case is attached to the new spear item and is a modern-only feature.

## Exact source identity

Both inputs are the canonical `decompiled_minecraft/<version>/mojmap/` trees, exact client releases, Mojmap namespace, Vineflower, Java 25. The root `mojmap.ready.json`, provenance, source manifest and success marker agree on the release and mapping. Ready-marker client SHA-256 values:

- 1.21.10 client: `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; source manifest SHA-256: `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- 1.21.11 client: `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; source manifest SHA-256: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.

The manifest hashes were recomputed from the actual manifest bytes. Each consumed source-file SHA below was checked both against its source-manifest entry and against the file bytes. Member SHA-256 values are over the exact UTF-8 excerpt from declaration indentation through the matching closing brace (the field entry covers the declaration through semicolon; no normalization).

## Compared operations and evidence

- `net/minecraft/client/player/LocalPlayer.java`, `applyInput` (1.21.10: 612–625; 1.21.11: 653–666): same controlled-camera gate, input vector assignment, jump write and fallback. Full-member SHA-256 is identical: `bf98b4bd04ecf0f0bdcd272b79b1bc78c072bee516f5ed652a1d47eef2750e0c`.
- `LocalPlayer.modifyInput` (1.21.10: 627–643; 1.21.11: 668–684): zero-vector early return, `0.98F` scale, item-use gate, slow-movement modifier, and square-movement projection remain in the same order. The item-use scale changes from literal `0.2F` to `itemUseSpeedMultiplier()`, so this member differs (older SHA-256 `10f4acad886270e3b727c17493d5bfe82ce8b42328f60eb8e1111233e2f3976b`; newer `4fb26e8d0253b924b152ab6a80721650180207a2158f2b1f20b8e56e32717f60`). The newer helper at `LocalPlayer.java:530–532` reads `DataComponents.USE_EFFECTS` with `UseEffects.DEFAULT`; helper full-member SHA-256 is `0986368a8ca2be990d2cfa15f5442ef71d9795723cc4bf675ca07830bbaa4bea`. The default declaration is `new UseEffects(false, true, 0.2F)` (`net/minecraft/world/item/component/UseEffects.java`, field SHA-256 `c3a6f2688889eab5f90f9519ee49a2ed57beac345624c7cd61ef025bc48f985b`, full-file SHA-256 `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f`). Its changed built-in value is supplied by `Item.Properties.spear` (`net/minecraft/world/item/Item.java:463–509`), which sets `new UseEffects(true, false, 1.0F)`. This is the new spear item path, not an existing-era vanilla item witness. A pre-existing item without an explicit component resolves to the same 0.2 multiplier as the old literal; custom non-default component data is outside this historical feature boundary.
- `net/minecraft/world/entity/player/Player.java`, `aiStep` and `getSpeed`: the per-tick acquisition `setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED))` and player `getSpeed()` attribute read are unchanged (both full-member hashes identical across releases: `aiStep` `ab2237dfe902e6605b6399f054f3d038212aa4dc92c760ca71cf12fbc5dbc2ec`; `getSpeed` `e0c672442b4389057386132c4187a57547079165708acc9675ac40b3f111bd53`). The base-value assignment from walking ability remains at Player lines 655 (1.21.10) and 650 (1.21.11); its enclosing member behavior was not needed to establish the consumed attribute read.
- `net/minecraft/world/entity/LivingEntity.java`, `travelInAir` (1.21.10: 2306–2329; 1.21.11: 2343–2366): grounded friction is sampled before the first move as `onGround() ? getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() : 1.0F`; `friction * 0.91F` and the call into relative friction calculation are unchanged. Full-member SHA-256 is identical: `c33d3eb0c1a20d5f264675b7e3fb02a8f431cc4e93afda8a196a3497b2a2adc2`.
- `LivingEntity.handleRelativeFrictionAndCalculateMovement` (1.21.10: 2476–2486; 1.21.11: 2534–2544) retains the order: friction-influenced speed, `moveRelative`, climbable velocity adjustment, then first `move(MoverType.SELF, deltaMovement)`. Full-member SHA-256 is identical: `d060b83025c8537721b0fad2049a330a2bc4d55fc879ba68e262587d2288b590`. `getFrictionInfluencedSpeed` is also identical (`743ce82db5289fbe681f435e33b1ff6efca7cdb372126938884bd1540e15b8df`); its grounded formula remains `getSpeed() * (0.21600002F / (friction * friction * friction))`.
- `net/minecraft/world/entity/Entity.java`, `moveRelative` and `getInputVector` (1.21.10: 1590–1605; 1.21.11: 1608–1622): the input-vector threshold, normalization threshold, float trig conversion and rotation/scale order are unchanged. Full-member hashes are identical: `moveRelative` `8ede16af078df0d5655b10d2cb4fd93f7b09697ce993ef13dae698701a9c2ffa`; `getInputVector` `7aadf4deec34c598e797dba94a6d8395b929a22a2f776f8d2b00d220d6e4d28b`.

Full source-file hashes (and exact manifest agreement):

| Version | Source file | SHA-256 |
| --- | --- | --- |
| 1.21.10 | `client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| 1.21.11 | `client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.10 | `world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 | `world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.10 | `world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| 1.21.11 | `world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.10 | `world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| 1.21.11 | `world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 | `world/item/Item.java` | `53e371efb8ef6eca5e8585a4598b3251dc37bd005b6d4c068c1f8c67d4efeee3` |
| 1.21.11 | `world/item/component/UseEffects.java` | `bb69726562d4f68006732a159abceb04e63685e8c05d2338b15d69a53f60bb6f` |

Boundary: block/support-position providers and collision geometry were not compared. Post-move drag, airborne/jump/effect lifecycle, input device plumbing, sprint/sneak decisions, and other excluded branches were not used to reach this result.
