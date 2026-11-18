# Independent audit: 1.17 → 1.17.1 active Elytra at climbable feet

## Verdict

**Accept the frozen report's bounded behavioral conclusion.** The stated ordinary local-player path with an already-set Elytra flag, enabled chest Elytra, airborne/non-passenger state, no Levitation, no ability flight, and dry water/lava branch predecessors selects the same Elytra travel branch in both versions while standing at a feet block tagged `CLIMBABLE`. The audit confirms the flag-preservation checks, branch order, pre-move strict double threshold and `1.0F` write, and exclusion of climbable cleanup from that branch. No 1.17→1.17.1 difference was found in those claims.

The conclusion is limited to this route. The report does not establish full-pair coverage, a historical introduction boundary, implementation acceptance, or runtime parity. Interpret its post-move fall-distance cases on the ordinary collision-resolving path (`noPhysics == false`); `Entity.move` bypasses `checkFallDamage` when `noPhysics` is true.

## Frozen input and exact source identity

- Frozen input: `c-glide-climb-117-1171-alias-gate/report.md`, SHA-256 `9203e6324d346f13debb95a34038bb88fcf86941aaa98b1026656539ddfcda36` (verified).
- 1.17 Mojmap canonical provenance: `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json`, SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. It identifies the 1.17 client SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`, official client mapping (`client.txt`) SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`, 1.17 version metadata SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`, and Vineflower 1.12.0 / Adoptium 25.0.3+9-LTS / Windows 11 amd64. The three class hashes below match the provenance entries.
- 1.17.1 Mojmap canonical readiness identity: `decompiled_minecraft/1.17.1/mojmap.ready.json`, SHA-256 `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; source manifest `mojmap.sources.sha256`, SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact manifest `artifacts.sha256`, SHA-256 `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`. Readiness metadata identifies exact version `1.17.1`, mapping `mojmap`, and client SHA-256 `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`. The artifact manifest records `client_mappings.txt` SHA-256 `2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9` and `client-mojmap.jar` SHA-256 `2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c`. The three class hashes below match the source manifest.
- Reviewed class SHA-256s, independently computed from the canonical source files:

| Class | 1.17 Mojmap | 1.17.1 Mojmap |
| --- | --- | --- |
| `world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `world/entity/player/Player.java` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |

## Independent source checks

- **Prior active flag:** `LivingEntity.updateFallFlying` starts with `getSharedFlag(7)` and keeps it true only while airborne, not riding, not under Levitation, and wearing an enabled Elytra (`1.17 LivingEntity.java:2580–2605`; `1.17.1:2584–2609`). It has no `onClimbable` test. On the client, the final shared-flag write is skipped (`1.17:2602–2604`; `1.17.1:2606–2608`). `isFallFlying` reads shared bit 7 (`1.17:3009–3011`; `1.17.1:3013–3015`).
- **Travel route:** `LivingEntity.travel` has the same effective-AI/local-control gate, Slow Falling prelude, and water → lava → Elytra branch order (`1.17:1999–2064`; `1.17.1:2003–2068`). Under the report's dry and active-flag assertions the water/lava branches do not win. Ordinary `Player.travel` delegates to `super.travel` when ability flight is off (`1.17 Player.java:1433–1447`; `1.17.1:1424–1438`).
- **Pre-move threshold:** both Elytra branches capture `vec36 = getDeltaMovement()`, test the `double` component with strict `vec36.y > -0.5`, and, only when true, assign the float literal `1.0F` before glide math and `move` (`1.17 LivingEntity.java:2064–2093`; `1.17.1:2068–2097`). Equality at `-0.5` does not write. The captured value precedes branch gravity/glide updates. If Slow Falling earlier reset `fallDistance`, this assignment replaces it only when the threshold passes.
- **Feet shape and cleanup route:** `onClimbable` checks the feet state against `BlockTags.CLIMBABLE` (`1.17 LivingEntity.java:1423–1438`; `1.17.1:1427–1442`). `handleOnClimbable` clears `fallDistance` only when called from `handleRelativeFrictionAndCalculateMovement`, which is in the final ordinary travel `else` after `else if (isFallFlying())` (`1.17:2107–2135, 2151–2158, 2179–2193`; `1.17.1:2111–2139, 2155–2162, 2183–2197`). Thus a ladder/vine feet state does not invoke that cleanup from the selected Elytra branch.
- **First move bounds:** normal `Entity.move` calls `checkFallDamage` with resolved Y displacement and `onGround` before returning (`1.17 and 1.17.1 Entity.java:534–566`). `Entity.checkFallDamage` clears on ground; otherwise it adds the magnitude of negative resolved Y using `(float)(fallDistance - d)`; a non-grounded nonnegative resolved Y leaves the value unchanged (`both Entity.java:998–1010`). This is conditional on a collision-resolving move; with `noPhysics`, `move` uses its direct-position branch and does not reach this fall-damage call.
- **Activation boundary:** `Player.tryToStartFallFlying` requires airborne, not already fall-flying, not in water, no Levitation, and an enabled chest Elytra; it has no climbable predicate (`1.17 Player.java:1551–1561`; `1.17.1:1542–1552`). The audited travel conclusion assumes bit 7 was already true before the tick and does not establish eligibility or success of activation on an earlier tick.

## Bounds

No game, TAS, Gym, server, test, decompiler, network, wiki, product implementation, or Git-history action was used. This independent verdict covers only the frozen route and source claims listed above.