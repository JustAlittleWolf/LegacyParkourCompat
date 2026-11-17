# Jump-boost addition continuity: 1.17.1–1.19.4

## Scope and verdict

Compared the exact local Mojmap `LivingEntity` jump arithmetic at 1.17.1, 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, and 1.19.4. The widened-double jump-boost addition is continuous across every requested release. No change in the expression, operand types, effect gate, or grounded/water call gate was found.

For a player standing on ordinary blocks whose jump factor is the default `1.0F`, `getJumpPower()` evaluates `0.42F * 1.0F` and returns a `float`. With Jump Boost, `getJumpBoostPower()` evaluates `0.1F * (amplifier + 1)` as a `float` multiplication, then widens that result to its `double` return type. `jumpFromGround()` adds the returned `double` to the base `float` after widening the base to `double`, then stores the sum as Y velocity while retaining X/Z. Thus the two products are each rounded at float precision before a double-precision sum. The Jump Boost term is gated only by `hasEffect(MobEffects.JUMP)`; without that effect it is `0.0`.

The input path in each release calls `jumpFromGround()` when grounded, or under the same water-jump threshold condition, with `noJumpDelay == 0`; lava follows `jumpInLiquid` instead. `Player.jumpFromGround()` delegates to `super.jumpFromGround()` in all nine releases. The default block jump factor is `1.0F`; the local block-factor lookup and comparison are unchanged in the examined interval. No armor or equipment attribute participates in these local expressions. Armor/equipment attribute behavior outside these methods was not surveyed.

## Exact source evidence

All entries are from ready local `decompiled_minecraft/<version>/mojmap` trees. Every `mojmap.ready.json` reports the matching exact version, `status: ready`, and `mapping: mojmap`; its source-manifest SHA-256 matches the actual `mojmap.sources.sha256`. The listed `LivingEntity.java` SHA-256 matches its entry in that manifest.

| Release | `LivingEntity.java` lines (`getJumpPower`, `getJumpBoostPower`, `jumpFromGround`) | `LivingEntity.java` SHA-256 | Source manifest SHA-256 |
|---|---:|---|---|
| 1.17.1 | 1967–1979 | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 | 1969–1981 | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | 1969–1981 | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| 1.18.2 | 1973–1985 | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | 2006–2018 | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | 2006–2018 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | 2006–2018 | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | 2016–2028 | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | 1947–1959 | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

Supporting exact-source slices:

- `net/minecraft/world/entity/Entity.java`, `getBlockJumpFactor()`: 1.17.1 lines 693–696; 1.18/.1 710–713; 1.18.2 720–723; 1.19/.1/.2 734–737; 1.19.3 742–745; 1.19.4 744–747. Each reads the current and below block's `float` jump factor and returns below only when the current factor equals `1.0`.
- `net/minecraft/world/entity/player/Player.java`, `jumpFromGround()`: 1.17.1, 1.18, 1.18.1 lines 1398–1400; 1.18.2 lines 1411–1413; 1.19 lines 1448–1450; 1.19.1 and 1.19.2 lines 1453–1455; 1.19.3 lines 1437–1439; 1.19.4 lines 1429–1431. Each delegates to `super.jumpFromGround()`.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`, default property `float jumpFactor = 1.0F`: 1.17.1 line 830; 1.18/.1 line 822; 1.18.2 line 832; 1.19 line 842; 1.19.1/.2 line 845; 1.19.3 line 870; 1.19.4 line 865. `Block.getJumpFactor()` returns the stored property.
- In `LivingEntity.java`, the grounded/water call gate is 1.17.1 lines 2524–2528; 1.18/.1 lines 2526–2530; 1.18.2 lines 2530–2534; 1.19 lines 2563–2567; 1.19.1/.2 lines 2566–2570; 1.19.3 lines 2577–2581; 1.19.4 lines 2523–2527. The gate is textually equivalent in all releases, apart from local names and line movement.

This establishes continuity only for the specified exact releases and the direct default-block/Jump Boost arithmetic. It does not establish when the widened-double form was introduced before 1.17.1 or behavior in unlisted versions.