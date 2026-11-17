# Early knockback response audit verdict

Terminal result: **COMPLETED — ACCEPT**, with one bounded wording clarification on the server-side producer description below.

## Bound and input identity

- Draft reviewed: `early-knockback-response-source/comparison.md`, SHA-256 `afad624a627c62bfd647b0a2e59eca4c4bd00b2d0ac6367b68914559eab3dc19`.
- Pair audited: exact Minecraft **1.8.9 ↔ 1.9.4**, Ornithe Feather. The terminal-source coverage here is limited to those two releases plus the first stable **1.9.0** cutoff. This does not close 1.9.1–1.9.3 or other release pairs.
- 1.8.9 local provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- 1.9.4 local provenance SHA-256 `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`; source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.
- 1.9.0 is a legacy nested source snapshot with embedded source hashes. Its provenance SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; each inspected handler, entity, player, living entity and velocity-packet file matched its embedded source hash. It identifies exact version 1.9, Feather build.2 and a pinned client input. This makes the first-stable cutoff usable for this bounded audit.

## Verdict

The draft's core finding is supported: the client receive-and-apply behavior for an entity-velocity packet is the same at 1.8.9, 1.9.0 and 1.9.4. Each `ClientPlayNetworkHandler.handleEntityVelocity` first schedules/validates execution on the client thread, looks up the packet entity ID, and only when the entity exists calls `lerpVelocity(packet X / 8000.0, packet Y / 8000.0, packet Z / 8000.0)`. `Entity.lerpVelocity` replaces all three velocity fields in order. There is no client-side knockback recomputation on this path.

The packet source is byte-identical across all three inspected sources: `EntityVelocityS2CPacket.java` SHA-256 `a2240f867e2486d6929a66928e3b45b75e7ff21d36eed132ec682634c7c7af3d`. Its constructor clamps each component independently to [-3.9, 3.9] and stores `(int)(component * 8000.0)`; decode reads three signed shorts. The handler therefore applies the quantized packet values as supplied.

The successful player-attack producer has additional gates. In each audited release, `PlayerEntity` saves the target's prior velocity, calls `target.takeDamage(...)`, and enters the attack response only if that call succeeds. The attack-specific impulse then requires `i > 0`. Afterward, sending an `EntityVelocityS2CPacket` requires both `target instanceof ServerPlayerEntity` and `target.damaged`; the code sends the current target velocity, clears `damaged`, and restores the saved server velocity. Living damage sets `damaged` through `markDamaged`, whose living-entity implementation incorporates knockback resistance. Thus a successful attack alone does not make the client-side handler calculate or conditionally alter the payload.

The draft's server-side contrast is accurate for the **extra attack-specific impulse**, but should say so explicitly to avoid implying it is the entire server damage response. In 1.8.9 the `i > 0` branch directly adds the yaw-derived vector to the target. In 1.9.0 and 1.9.4 it calls `LivingEntity.applyKnockback` for living targets (which halves horizontal velocity, subtracts the normalized attacker vector, and conditionally changes vertical velocity); non-living targets retain the direct `addVelocity` path. Independently, `takeDamage` in these releases also has its own living damage knockback processing. This server producer difference can alter packet payloads; it does not alter the client receive formula. The draft's packet/source claim and its separation of server generation from client response are otherwise supported.

## Exact source bounds checked

| Release | Receive handler | Velocity setter | Attack producer / reactive gates |
| --- | --- | --- | --- |
| 1.8.9 | `ClientPlayNetworkHandler.java:402–408` | `Entity.java:1424–1428` | `PlayerEntity.java:972–994`; `LivingEntity.java:675–690, 763–772, 1583–1585` |
| 1.9.0 | `ClientPlayNetworkHandler.java:434–440` | `Entity.java:1556–1560` | `PlayerEntity.java:983–1032`; `LivingEntity.java:863–882` and verified `markDamaged` implementation |
| 1.9.4 | `ClientPlayNetworkHandler.java:435–441` | `Entity.java:1565–1569` | `PlayerEntity.java:988–1037`; `LivingEntity.java:870–889, 1856–1858` |

The direct receive-handler file hashes are 1.8.9 `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3`, 1.9.0 `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`, and 1.9.4 `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`. The raw handler files differ; the compared operation and gates are equivalent. No death-velocity response was included in this verdict.

## Applicability

Accept the draft's no-client-difference conclusion for the exact 1.8.9 → 1.9.4 pair, with confirmation that the same receive/apply path and attack-packet gate already exist at stable 1.9.0. Do not treat this audit as proof for intervening 1.9 patches or later versions, and do not move server knockback production into the client finding.