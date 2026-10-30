# Bounded source census: remaining direct input and player-response paths

## Scope and result

Exact pair: Mojmap 1.16.5 → Mojmap 1.17.1. Canonical read-only roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`

This bounded census checks keyboard-to-player input, client-side movement corrections, firework Elytra propulsion, and two external impulse producers (`LivingEntity.knockback`, `Explosion.explode`). It consults the pair's prior source-only reports for ordinary tick, fluids, push, and collision/contact callback coverage, and does not re-audit those slices. It does not inspect product code/history, Git, wiki material, raw JARs, or run builds/tests/game/decompilation.

No new client-local, in-scope movement difference was established in the checked input/correction/firework paths. Two source-expression differences can affect server-produced impulses (combat knockback and explosion impulse); they are classified outside this task's applicable client-only movement execution under the project invariant that historical movement is not run on logical servers. The changed vehicle-detach correction guard is excluded with vehicle behavior. This is a bounded census, not full-pair closure or runtime parity evidence.

## Source identity

The pair source-only identity record reports source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` (1.16.5) and `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` (1.17.1), with 3,523 and 4,142 Java files respectively. Its ready-marker hashes are `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` and `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; artifact-manifest hashes are `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` and `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`. Those manifests were previously checked against all source files. This census recomputed the hashes below. The inspected source trees identify Mojmap by their canonical directory names; a separate mapping-build identifier was not present in the inspected source-only record.

| Relative source path | SHA-256, 1.16.5 | SHA-256, 1.17.1 |
| --- | --- | --- |
| `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | `ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `net/minecraft/client/Minecraft.java` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` | `d474768e60049d657cfe4bf34f6a7c2690f82afbf692bdea41c465fa560e7b72` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | `a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a` |
| `net/minecraft/world/entity/projectile/FireworkRocketEntity.java` | `8efc3d56a4320ee15d643fd8e26f2bd3478a7d615b3419976ece4755e8ce6fed` | `76b6596b47f9a42b6968578f1a2336f901267606678717b088aa8e259ba1cd68` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/level/Explosion.java` | `8b956875a7d741e61b5ae70965db91a1e8b14fa92fb92e1ed962bbab6b9789a3` | `e1c3a75f109acb07172bf38f1952df071e6b872f6c620abff1e9c40a4a4dc9d8` |

## Paired evidence and dispositions

### Keyboard movement input: no behavioral change found

- 1.16.5 `net/minecraft/client/player/KeyboardInput.java`, `tick(boolean)`, lines 13–25; 1.17.1 same path, lines 14–26. Both read the same directional/jump/shift keys, derive `forwardImpulse` / `leftImpulse` from opposite key pairs, then apply slow movement by multiplying both impulses by `0.3` and casting to float. The 1.17.1 `MOVING_SLOW_FACTOR` declaration is not used by this method; the executed literal and operation order are unchanged.
- `net/minecraft/client/player/Input.java` is byte-identical (hash above); its input-vector creation and forward-impulse predicate are therefore unchanged.
- Downstream `LocalPlayer` copies input forward/jump state and uses jump/shift in the same compared movement branches: 1.16.5 `LocalPlayer.java:613–654, 713–775`; 1.17.1 `LocalPlayer.java:617–676, 735–797`. The ordinary-tick report independently compares those tick/aiStep and sprint gates. `Minecraft.handleKeybinds()` (1.16.5 `Minecraft.java:1508+`; 1.17.1 `:1704+`) dispatches click actions and contains no movement-axis calculation in the inspected body; key sampling remains the `KeyboardInput` route above. No additional movement-input delta was found.

### Server position/motion correction: unchanged except vehicle-only detachment

- `ClientPacketListener.handleSetEntityMotion(ClientboundSetEntityMotionPacket)`: 1.16.5 `ClientPacketListener.java:563–573`; 1.17.1 `:445–455`. Both look up the entity ID and call `entity.lerpMotion(packet Xa/Ya/Za divided by 8000.0)` with the same gates and arithmetic.
- `handleMovePlayer(ClientboundPlayerPositionPacket)`: 1.16.5 starts at `ClientPacketListener.java:671`; 1.17.1 at `:549`. Both resolve relative position/rotation values, set raw position and old-position fields, write the resolved delta movement, call `absMoveTo`, acknowledge teleport, and send the current position/rotation packet in the same order. The old method has an extra `if (player.tickCount > 0 && player.getVehicle() != null) player.removeVehicle()` at lines 714–716; no equivalent appears in 1.17.1 before `setPosRaw` at line 596. Its only guarded case is an attached vehicle, which is excluded vehicle behavior; no non-vehicle local-player correction difference was established.
- `handleExplosion(ClientboundExplodePacket)`: 1.16.5 `ClientPacketListener.java:1119–1137+`; 1.17.1 `:973–992+`. Both build the visual explosion, call `finalizeExplosion(true)`, and add the packet's three knockback components to the local player's current delta movement. This direct client application is unchanged.

### Firework Elytra response: no behavioral change found

- 1.16.5 `net/minecraft/world/entity/projectile/FireworkRocketEntity.java:99–139`; 1.17.1 same path/range. When the attached entity is fall-flying, both use the same look vector, existing velocity, `0.1` look acceleration and `0.5` convergence toward `1.5 * look`, then set the rocket to the attached entity's position and velocity. Unattached rocket motion and `SELF` move calls in this range are also identical. This is a direct player velocity response reachable for an Elytra-flying player attached to a firework; no changed arithmetic/gate was found.

### Server combat knockback: source expression changed, outside client-only movement execution

- 1.16.5 `net/minecraft/world/entity/LivingEntity.java:1319–1326`, `knockback(float, double, double)`: multiplies knockback strength by `(1.0 - knockbackResistance)` and explicitly casts the product back to `float`; the `<= 0.0F` gate and velocity math then use the float strength.
- 1.17.1 same file `:1383–1390`, `knockback(double, double, double)`: strength, resistance product, gate, and vertical term are double; the float cast is removed. This is a source-level arithmetic delta to a direct velocity setter, so it can alter knockback values when resistance is nonzero.
- The inspected `hurt(DamageSource, float)` caller reaches `knockback(0.4F, dx, dz)` only after `if (this.level.isClientSide) return false` (1.16.5 `LivingEntity.java:939–946, 1052–1062`; 1.17.1 `:1013–1020, 1122–1132`). Thus this incoming damage path calculates knockback on the logical server and is not an eligible local historical-physics path under the project invariant. Combat/damage event resolution is also outside this bounded census. Do not treat it as an accepted client movement finding; if combat knockback is later brought into scope, it requires a separate client/server-boundary and packet-path decision.

### Explosion impulse producer: source math changed server-side; client packet application is unchanged

- 1.16.5 `net/minecraft/world/level/Explosion.java:199–224` calculates normalized distance and radial direction magnitude with `Mth.sqrt` (`:202, :207`), then mutates each affected entity's velocity and stores eligible player vectors.
- 1.17.1 same file `:210–234` changes these two expressions to `Math.sqrt` (`:213, :218`); the entity velocity addition and player hit-vector storage follow the same sequence (`:225–234`). This may change the authoritative server-produced impulse at float/double precision boundaries.
- The client handler only receives and adds the server-supplied player knockback vector; it does not call `Explosion.explode()` on this path. Historical movement is not run on the logical server, so the changed server producer is recorded as an excluded boundary/dependency, not a local client source finding. The direct client packet response itself was checked above and is unchanged.

## Cross-check against already-owned slices and remaining dependencies

Prior source-only reports in this pair assignment already check `KeyboardInput.tick` / input forwarding, the local-player ordinary tick and sprint gates, jump-boost write precision, swim-entry fluid predicate, push impulse precision, bubble/slime/honey/web/other selected contact responses, and their bounded endpoint hashes. This census adds no changes to those reports or claims.

Examined and paired here: keyboard abstraction and local-player input handoff (within the ordinary-tick report's ownership); keybind dispatcher's movement relevance; client entity-motion, player-position, and explosion correction handlers; firework Elytra impulse; incoming-damage knockback gate/math; explosion player-impulse producer.

Partial / not closed: client input platform polling before `KeyboardInput`; every item/potion/combat path capable of producing server-side motion; all packet routes that set position, velocity, or ground state; complete direct response producer inventory; exact applicability of server-produced impulse deltas under the client-only profile model. The prior assignment leaves piston/external velocity response and full changed-path triage open; this bounded census does not close those.

Excluded: vehicle physics/passenger movement; health and damage calculation/combat resolution; server-side historical movement; non-player physics; rendering; runtime trajectories. No build, test, or runtime verification was performed.
