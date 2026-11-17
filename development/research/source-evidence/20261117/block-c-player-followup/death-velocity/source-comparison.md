# Direct player death velocity: 1.8.9 to 1.9.x

## Scope and disposition

Compared the death-time direct velocity writes in `PlayerEntity.die`, the player attack caller that can transfer those writes to the target's client, and the clientbound velocity packet encoding/handler across 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, and 1.9.4, all under `ornithe-feather`.

**Observed candidate difference:** in `PlayerEntity.die`, the angle-to-radians expression for X/Z differs beginning in 1.9.0; Y and the null-source horizontal fallback are unchanged. In the lethal player-melee path, the server sends the post-`die` motion in `EntityVelocityS2CPacket` to a `ServerPlayerEntity` target, then restores the server entity's prior velocity. The client applies received packet motion to the matching world entity. Thus the client can receive the death motion on that reachable server path.

**Disposition: no client implementation.** The changed expression is evaluated in the server-side `PlayerEntity.die` producer. The local client is a `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity`, but client `LivingEntity.takeDamage` returns immediately when `world.isClient`; incoming player-health synchronization only updates health. The packet contains only quantized X/Y/Z values; it does not carry death angle, source, or an unquantized value from which the alternate expression could be recomputed. Client packet application is generic. A client-only patch has no source inputs to reproduce the older server producer. This is a producer-side difference, not a separately recoverable local response mechanic.

The death method's default vertical state (`velocityY = 0.1F`) and null-source horizontal state (`velocityX = velocityZ = 0.0`) are local/server entity writes. They are distinct from wire encoding: the packet constructor clamps each component to [-3.9, 3.9], multiplies by 8000.0 and truncates to `int`; write/read carry shorts; the client divides the decoded ints by `8000.0` and writes those doubles. This encoding is unchanged across checked snapshots. The unrelated inventory-drop condition also changes in 1.9 (`!isSpectator()` added) and is outside this movement finding.

## Source references and observed behavior

- 1.8.9 Feather, `net/minecraft/entity/living/player/PlayerEntity.java`, `die(DamageSource)`, lines 513-535. After `super.die`, resize/reposition, and `velocityY = 0.1F`, non-null source writes `velocityX = -cos((damagedSwingDir + yaw) * (float) Math.PI / 180.0F) * 0.1F` and the matching negative-sine Z expression. Null source writes both horizontal components to zero.
- 1.9.0 through 1.9.2 Feather, same file/member, lines 483-506. 1.9.3-1.9.4, lines 484-506. The Y and null-source writes are the same. With non-null source, both horizontal expressions use `* (float) (Math.PI / 180.0)` instead of multiplying by the float-rounded PI then dividing by `180.0F`.
- 1.8.9 Feather, `net/minecraft/entity/living/player/PlayerEntity.java`, `attack`, lines 975-994. After successful `target.takeDamage(...)`, a `ServerPlayerEntity` target with `target.damaged` receives `new EntityVelocityS2CPacket(target)`; then `damaged` is cleared and the saved pre-damage velocity is restored on the server entity. The call to `takeDamage` can run `die` before this packet is built.
- 1.9.0-1.9.2 Feather, same method, lines approximately 990-1038; 1.9.3-1.9.4, lines approximately 991-1037. Same send/restore ordering and applicability. 1.9 changes the surrounding melee/knockback implementation, but this lethal-packet ordering is unchanged.
- All snapshots, `net/minecraft/network/packet/s2c/play/EntityVelocityS2CPacket.java`, constructor lines 18-52; read/write lines 54-68. Same ±3.9 clamp, `* 8000.0`, `(int)` truncation, short read/write.
- 1.8.9 Feather, `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handleEntityVelocity`, lines 402-408. 1.9.0-1.9.2 lines 434-440; 1.9.3-1.9.4 lines 435-441. After same-thread gate and entity lookup, packet integers are divided by `8000.0` and passed to `entity.lerpVelocity`; `Entity.lerpVelocity` assigns X, Y, Z in that order (1.8.9 `Entity.java` lines 1424-1428; 1.9.x lines 1565-1569).
- In all snapshots `LocalClientPlayerEntity extends ClientPlayerEntity` and `ClientPlayerEntity extends PlayerEntity`; `LivingEntity.takeDamage` has the early `world.isClient` return (1.8.9 lines 615-624; 1.9.x starts at lines 676-685, with offsets varying slightly). `ClientPlayNetworkHandler.handlePlayerHealth` updates the local player's health and hunger; it does not call `die` or write death motion (1.8.9 lines 784-789; 1.9.0-1.9.2 lines 840-845, 1.9.3-1.9.4 lines 849-854). `LivingEntity.setHealth` only clamps and updates synced health data (1.8.9 lines 607-613; 1.9.x lines 668-674, with offsets varying).

The angle expression change can alter float rounding before velocity packet quantization; a concrete client-side divergence is not asserted because the alternate value is not available at the receiving client. The exact server-generated packet integer can differ only when the small arithmetic delta crosses a truncation boundary.

## Evidence identity and hashes

The available canonical evidence has complete provenance/ready markers for endpoints only:

- 1.8.9 `ornithe-feather`: `ornithe-feather.provenance.json` names version 1.8.9, mapping artifact `feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`, exact run `decompileMinecraft --versions=1.8.9,1.9.4 --mappings=feather`; ready marker source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`.
- 1.9.4 `ornithe-feather`: provenance names version 1.9.4, mapping artifact `feather-gen2-1.9.4+build.2-mergedv2.jar` and `.tiny`, same exact run; ready marker source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` and client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`.
- 1.9, 1.9.1, 1.9.2, and 1.9.3 source directories are present, and the focal files were inspected and hashed, but those version roots have no ready marker, source manifest, or provenance JSON. Exact mapping-build/source-publication identity and manifest integrity cannot be validated from the supplied canonical metadata. Their focal `PlayerEntity.java` SHA-256 values respectively are `EDBB46236D1E42630E600E4AFCB434EB6E9ADECBFC4AC7E15710A5DEA50010B1`, `D7035F08F03B01823DFF70CA504F7244F9B0753542CC0F6DD7301E66F8866B71`, `1FC5107B9B7F5711105D521A28829F2466FE9F297E832F4D209E6EC422111AAC`, and `EF96C8A00443F724EADAD05A6A4DCC8A8A2E23CD4C3390B9E0F1F78C9FF4D1AF`; these hashes identify local bytes but do not establish exact source provenance.

Focal local-file SHA-256 values (all six snapshots, in version order 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4):

| File | SHA-256 values |
|---|---|
| `entity/living/player/PlayerEntity.java` | `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88`; `EDBB46236D1E42630E600E4AFCB434EB6E9ADECBFC4AC7E15710A5DEA50010B1`; `D7035F08F03B01823DFF70CA504F7244F9B0753542CC0F6DD7301E66F8866B71`; `1FC5107B9B7F5711105D521A28829F2466FE9F297E832F4D209E6EC422111AAC`; `EF96C8A00443F724EADAD05A6A4DCC8A8A2E23CD4C3390B9E0F1F78C9FF4D1AF`; `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85` |
| `network/packet/s2c/play/EntityVelocityS2CPacket.java` | `A2240F867E2486D6929A66928E3B45B75E7FF21D36EED132EC682634C7C7AF3D` (identical in all six) |
| `client/network/handler/ClientPlayNetworkHandler.java` | `7FA3D587325068EAA158526CB59E4850B704AC237938DF6592D1A09148DB40C3`; `7F67BD2DAA792CFC4AAC1744D30F9CFDB28306EC55583D474DDC1A251561B1D6` (1.9.0-1.9.2); `5A2FC039B81EC0E77DF3354826AAAF00DFAE6D0D1296CF111864900932E811A4C` (1.9.3-1.9.4) |
| `server/TrackedEntity.java` | `C8F4C2115E2BD348C85BE8CC4A35F18EECE8EDCCE5560989DFA45CACB39F9B15`; `82E4772FB6DA96CBC92548211F7E0F5161B68E1B71726F26A6E2B7BB08EF09F0` (identical in all five 1.9 snapshots) |

No source files were modified; no tests or launches were performed.

## Terminal result

**FAILED — provenance gate.** The source behavior and no-client-code disposition are traced, but the exact intermediate 1.9.0-1.9.3 source/mapping identities cannot be validated because canonical provenance/readiness records are absent. Treat the intermediate release statements as observations of local source snapshots, not accepted exact-source findings. Request canonical metadata or an exact-source preparation before accepting a 1.8.9-to-1.9.4 source finding.