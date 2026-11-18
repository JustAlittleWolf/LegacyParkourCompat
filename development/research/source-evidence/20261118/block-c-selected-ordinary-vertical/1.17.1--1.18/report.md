# SOURCE coverage — 1.17.1 → 1.18 ordinary local-player vertical integration

Verdict: **no source-confirmed difference in the assigned ordinary dry, nonvehicle, non-gliding, non-flying vertical integration and common fall-distance update.** No canonical difference finding is warranted for this slice. The code and arguments below are exact Mojmap decompilation evidence; compare parameter renamings without treating Vineflower's `$$n` names as behavior.

## Evidence identity and file integrity

Both ready markers authenticate the literal release and `mojmap` mapping. Their exact SHA-256 identities are:

- `1.17.1`: ready `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; provenance `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`; source manifest `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`.
- `1.18`: ready `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`; provenance `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`; source manifest `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`.

For each source file below, the file's whole-content SHA-256 was recomputed and matched its version's entry in that pinned `mojmap.sources.sha256` manifest. The ready metadata says `versionId` 1.17.1 / 1.18 and `mapping` mojmap; each provenance document names the same exact version and namespace. The marker identities above are hashes of the literal marker/provenance/manifest files, not inferred from folder names.

| Slice | 1.17.1 line range; whole-file SHA-256 | 1.18 line range; whole-file SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java`: tick and `aiStep` path | 191–196, 649–810; `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | 188–193, 647–808; `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/world/entity/player/Player.java`: `travel` | 1409–1439; `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | 1409–1439; `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/entity/LivingEntity.java`: `travel`, relative-friction movement and friction-influenced speed | 2003–2139, 2155–2166, 2200–2202; `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | 2005–2142, 2157–2168, 2202–2204; `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/Entity.java`: `move`, `checkFallDamage`, no-gravity getter and reset helper | 534–652, 998–1011, 982–987; `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | 541–665, 969–982, 953–958, 2101–2103; `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |

`1.17.1/movement-diagnostics.txt` records a successful exact-metadata decompile and lists intact movement method anchors for these classes. The 1.18 diagnostics are presence-only; therefore I independently checked the cited 1.18 method bodies, which are intact in the manifest-matched source files. No network, mappings lookup, generation, build, test, or runtime action was used.

## Compared behavior and coverage

- The local player reaches its inherited movement path through `LocalPlayer.aiStep` calling `super.aiStep`; `LocalPlayer` does not declare its own `travel`. `Player.travel` keeps its swimming and flying branches and otherwise delegates to `LivingEntity.travel` in both releases. The swimming/flying/gliding paths are outside this assignment.
- In `LivingEntity.travel`, the ordinary final branch is reached for the dry, non-gliding case. With no Slow Falling or Levitation and ordinary loaded ground conditions, the Y procedure is the same in both versions: initialize gravity `d = 0.08`; calculate relative-friction movement (which moves using the current delta); take the resulting `vec*.y`; subtract `d` unless `isNoGravity()`; then write Y as that value times `0.98F`, unless `shouldDiscardFriction()` requests the unmultiplied value. The source retains the same double values, `0.08` and `0.98F`, branch order, subtraction, float multiplier, and assignment order. Ground-friction input remains an external block/support boundary as assigned.
- In `Entity.move`, both versions pass the requested Y through collision resolution, apply the returned Y to position when the resolved vector is nontrivial, set `verticalCollision` by requested-versus-resolved Y inequality, and set `onGround` only for a vertical collision with a negative requested Y. They call `checkFallDamage` with the collision-resolved Y in both releases. Collision and block callbacks are treated as boundaries; their providers/effects were not audited.
- The ordinary `checkFallDamage` update matches: on landing it conditionally invokes the block's fall callback for positive distance and then clears `fallDistance`; while not grounded, only negative resolved displacement updates it, with the exact `(float)(fallDistance - d)` narrowing cast. The other-side fall-distance path for nonnegative displacement leaves the value as-is. No numeric threshold or arithmetic/order change appears in this ordinary path.
- `LivingEntity.checkFallDamage` still delegates to `super` after the same water-state and server-only branches; the `> 3.0F` check relates to server particles/damage-facing behavior, excluded from this client vertical-integration slice.
- 1.18 extracts a public `Entity.resetFallDistance()` method that assigns `0.0F`; landing reset calls it instead of assigning the field inline. Exact-source search found no override in `LocalPlayer`, `Player`, or `LivingEntity`, so local-player dispatch reaches the same assignment. The one 1.18 override found is `ServerPlayer`, outside this logical-client path. The flying reset call is also outside this assignment.
- 1.18 `Entity.move` adds `minorHorizontalCollision` bookkeeping. It does not alter the requested/resolved Y, vertical-collision/on-ground calculation, or fall-distance update in the compared method; horizontal collision consequences are not in this slice.

## Boundaries and exclusions

Assumed ordinary dry local-player movement with ordinary gravity, not a vehicle, not gliding or flying, no Slow Falling/Levitation, and normal loaded-world inputs. Excluded: jump cause and input, status/equipment-specific movement, swimming/fluid/lava, climbables and block support/effects, collision and shape providers, horizontal velocity/angles, damage/health, visual animation, network/server prediction, and profile orchestration. Version-specific 1.18 powder-snow and other block/provider behavior is not adjudicated here. This bounded verdict does not establish full pair coverage or inspect 1.17.1→1.18.1/1.18.2 intermediate boundaries.


