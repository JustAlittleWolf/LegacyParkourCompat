# Independent full-coverage source audit: Minecraft 1.8.2 → 1.8.3

## Verdict and boundary

**Source-coverage verdict:** I independently confirm complete endpoint Java-source path/hash coverage and the report’s conclusion for ordinary client-local-player movement: no in-scope 1.8.2→1.8.3 Java-source movement delta was found. No concrete Java movement dependency remains open for this endpoint pair. This report is an audit input; it does not decree pair freeze, source acceptance, or implementation readiness.

**Not established:** the exact patch introduction boundary between these endpoints; any intermediate release behavior; runtime tick-by-tick parity or trajectory consequences; and non-player/vehicle physics. No test, game, TAS, Gym, integrated-server, or dedicated-server launch was performed. Input-device backend internals and server simulation were outside the ordinary local-player movement slice. This endpoint Java-source audit does not claim a general non-Java resource inventory; no movement-affecting data-driven block tags or movement registry resources were evidenced for this 1.8-era path, whose checked movement properties/providers are Java-based.

## Exact source and inventory verification

Canonical source trees were read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.8.2,1.8.3}/ornithe-feather/`. Both exact release trees use Ornithe Feather Gen2 build 2 for their respective release, a compatible common mapping family.

- 1.8.2 manifest SHA-256: `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472`.
- 1.8.3 manifest SHA-256: `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`.
- Each manifest path set has 1,612 Java sources; each on-disk tree has 1,612 Java paths, with no missing or extra path and no source hash mismatch against the manifest.
- Independently compared every row in `L-full-inventory/source-hash-inventory.csv` to both manifest source entries and rehashed every canonical file. All 1,612 rows agree on each side. The two path sets are equal; 1,596 endpoint hashes are equal and 16 differ.
- Verified inventory CSV sidecar SHA-256 `ba532956f93754a36466632c8deaea555daf007b57fa67ac4dd3a701bfcbc96e`; coverage report sidecar SHA-256 `28afcb07765e0991697ecb3b5421f8b82c5242c1aa77ca675a7f14c909680d6b`.
- Both manifests record `lpc-decompile-v1`, single-threaded mapping/decompilation, Vineflower 1.12.0, Java 25 (Eclipse Adoptium 25.0.3+9-LTS on Windows 11 amd64). Manifest source trees agree with each manifest’s content hash.
- Rehashed the cached artifacts and compared with manifest inputs and pin sidecars: Mojang 1.8.2 client `a8e71f6c81acfa834d249579a242f5b0852075c1`, SHA-256 `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`; Mojang 1.8.3 client `69d14463ddc22e581bc66c66ef5eb72a8b452c46`, SHA-256 `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06`. Feather mapping JARs `feather-gen2-1.8.2+build.2-mergedv2.jar` (`8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`) and `feather-gen2-1.8.3+build.2-mergedv2.jar` (`e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2`) also match their pin and manifest hashes. Feather metadata pin hashes are `9019d76f144d8ea04b525db9e293a140cf70ca9333f056baead1391c17e1525e` for 1.8.2 and `db7a3bc38e53ffa3c0b71ae69435f9187c32480617b52721584e3ab4a2c4eda2` for 1.8.3; each metadata payload names its corresponding game version.

## Reconstructed tick-to-movement and slice verdicts

All called source bodies and the named support dependencies below were matched by per-release path/hash; changed bodies are separately classified in the next section.

| Slice | Reachable path and source evidence | Audit verdict |
|---|---|---|
| Client/world scheduling | `client/Minecraft.java`, `tick` calls `world.tickEntities()` before `world.tick()`; `world/World.java`, `tickEntities` dispatches loaded entities; local player enters through `LocalClientPlayerEntity.tick` only while its chunk is loaded. `Minecraft.java` has release-label/request-version hunks only; its tick path is unchanged. `World.java`, `ClientWorld.java` and local-player tick are hash-identical. | Complete; no movement delta. |
| Local-player input and predicates | `LocalClientPlayerEntity.mobTick` snapshots jump/sneak/forward input, calls `Input.tick`; item-use scaling and four `pushAwayFrom` probes; double-tap/manual sprint gates read ground, forward input, food-or-flight, use-item and blindness; flight and jump toggle, riding-jump charge, and portal/sprint timers. `KeyboardInput.tick` converts key bindings to axes/jump/sneak; local `serverTickAi` copies values to living movement fields when camera-controlled. `Input`, `KeyboardInput`, `GameOptions`, local-player and option sources are unchanged. | Complete for ordinary local-player input; no movement delta. Riding/vehicle simulation remains excluded. |
| Tick state and jump | Local-player tick dispatches inherited `PlayerEntity`/`LivingEntity`/`Entity` tick work. `LivingEntity.tick` covers status-effect progression and movement state; `LivingEntity.mobTick` applies cooldown/interpolation/near-zero cleanup, local AI, water/lava/ground jump selection, then scales axes and dispatches travel. `LivingEntity.jump` reads jump boost and sprint yaw impulse; player override adds player-specific counters. These classes, `StatusEffect`, `StatusEffectInstance` and related effect code are hash-identical. | Complete; no movement delta. |
| Ground/air travel and attributes | `PlayerEntity.moveRelative` reads movement speed/flight ability and delegates/adjusts; `LivingEntity.moveRelative` uses ground block slipperiness, `0.16277136F/(i*i*i)`, airborne speed, `Entity.updateVelocity`, climbing clamps/sneak gate, `Entity.move`, collision climb response, gravity and drag in sequence. Movement attribute definitions, instances, containers and modifiers are unchanged. Food production is excluded; the food-level sprint predicate is covered above. | Complete; no movement delta. |
| Water/lava and equipment/effects | `LivingEntity.moveRelative` branches through `isInWater`/`isInLava`, then applies depth-strider water drag/acceleration, airborne halving, lava acceleration, collision step-up and gravity. `EnchantmentHelper.getDepthStriderLevel` reads equipment through unchanged enchantment/equipment traversal. `Entity`, `World`, liquid blocks, `Material`, `ItemStack`, armor/equipment and effects are hash-identical; the sole `EnchantmentHelper` hunk is protection damage clamping, and direct side-by-side method inspection confirms the depth-strider method is unchanged. | Complete; changed helper is out-of-scope damage only. |
| Collision collection and shape providers | `Entity.move` handles cobweb scaling, sneaking edge back-off, ordered Y/X/Z collision clipping, two step-height candidates and horizontal-distance choice; it writes shape-derived position and collision/on-ground flags. `World.getCollisions` queries loaded chunks and block collision providers, border and nearby entity boxes; `Block.addCollisions/getCollisionShape` and `Box` geometry feed clipping. `Block`, block state, `World`, `ClientWorld`, `WorldBorder`, chunk source/chunk classes, `Box`, `Blocks`, and all block Java providers are covered by the complete inventory. All are unchanged except the unrelated spawner source. | Complete for the ordinary player collision path; no provider/query delta. |
| Post-move callbacks and direct block response | `Entity.move` calls fall handling, `beforeCollision`, supporting-block step behavior, block-volume collision callbacks and updates fire/fluid/cobweb contact. The complete block provider set (including cobweb, ladder/vine, slime, soul sand, ice, liquids, partial-shape blocks, pistons and generic collision hooks) is present in both manifests; only `MobSpawner.java` differs and is unrelated. Piston/base/head and collision callback sources are unchanged. | Complete; no callback/shape delta. |
| Position, velocity, dimensions and direct external responses | `Entity.setPosition`, `setShape`, `setPositionFromShape`, `move`, `updateVelocity`, interpolation/velocity writers, `LivingEntity` jump/travel and `PlayerEntity` knockback/push paths are unchanged. `ClientPlayNetworkHandler` plus relevant S2C movement/velocity/explosion packet consumers are unchanged; entity velocity packets write components, position corrections update the local player, and explosion/player response writes velocity. `World.explosion.Explosion` and client correction handlers are unchanged. `Box` and player dimensions/state consumers are unchanged. | Complete for source-changing endpoint reachability; no state-writer delta. |
| Other tick callbacks and changing files | The sixteenth-path comparison below accounts for every changed Java path, including changed helpers with unchanged movement consumers. | Complete; no in-scope delta. |

## Classification of every changed Java path

The exact 16 differing paths from the full equal path sets were each diffed at source-hunk level:

| Path | Hunk classification | Movement verdict |
|---|---|---|
| `net/minecraft/block/spawner/MobSpawner.java` | Null check for spawner type and `MobEntity` cast guard during spawn initialization. | Spawning only; not ordinary local-player movement. |
| `net/minecraft/client/Minecraft.java` | Window title and HTTP `X-Minecraft-Version` release string. | Metadata only; `tick` unchanged. |
| `net/minecraft/client/gui/overlay/DebugOverlay.java` | Displayed release string. | UI/rendering only. |
| `net/minecraft/client/gui/screen/game/DeathScreen.java` | Hardcore death-screen action now opens title screen directly. | Death-screen flow, not movement tick/state. |
| `net/minecraft/client/gui/screen/menu/TitleScreen.java` | Displayed release string. | UI only. |
| `net/minecraft/client/options/ServerListEntry.java` | Default server-list version string. | UI metadata only. |
| `net/minecraft/enchantment/EnchantmentHelper.java` | Clamp negative protection value in protection-damage randomization. | Damage only; depth-strider consumer/method unchanged. |
| `net/minecraft/enchantment/ThornsEnchantment.java` | Null guard around target damage/sound; item durability behavior retained. | Damage/durability only. |
| `net/minecraft/entity/projectile/ThrownEntity.java` | Catch malformed UUID parsing around ServerWorld thrower lookup. | Server-world/non-player owner lookup; ordinary local-player movement unaffected. |
| `net/minecraft/realms/RealmsSharedConstants.java` | Release string. | Version metadata only. |
| `net/minecraft/server/MinecraftServer.java` | Server status/accessor release strings. | Server metadata only. |
| `net/minecraft/server/command/handler/CommandRegistry.java` | Command exception log severity/message. | Server logging only. |
| `net/minecraft/server/integrated/IntegratedServer.java` | Startup log release string. | Server metadata only. |
| `net/minecraft/server/network/handler/RemoteHandshakeNetworkHandler.java` | Release strings in protocol rejection text. | Login text only. |
| `net/minecraft/snooper/Snooper.java` | Telemetry release string. | Metadata only. |
| `net/minecraft/util/crash/CrashReport.java` | Crash-report release string. | Metadata only. |

## Per-dependency audit verdict

| Dependency family | Verdict / endpoint evidence |
|---|---|
| Input and local control | Closed: `Input`, `KeyboardInput`, `GameOptions`, local player input-to-fields and sprint/flight/jump predicates are unchanged. |
| Entity/player/living state | Closed: `Entity`, `LivingEntity`, `PlayerEntity`, `ClientPlayerEntity`, `LocalClientPlayerEntity`; tick, jump, travel, dimensions, interpolation and movement flags unchanged. |
| Attributes and sprint modifiers | Closed: movement-speed attributes/containers/modifiers and player speed reads are unchanged. |
| Effects and effect-applied modifiers | Closed: effect definitions/instances and tick/application logic are unchanged; relevant nausea, blindness and jump boost reads remain at identical call sites. |
| Equipment/enchantments | Closed: equipment and depth-strider path unchanged. `EnchantmentHelper` protection clamp does not feed travel. Thorns/durability hunk does not write movement state. |
| Fluids/materials | Closed: entity water/lava/submersion predicates, world material/liquid queries, liquid blocks and height/flow sources unchanged. |
| World/chunk/border queries | Closed: loaded-chunk movement gating, collision enumeration, world border checks, collision box geometry and client-world scheduling are unchanged. |
| Block collision providers | Closed: complete Java path inventory covers every provider and registry; all unchanged except spawner, which does not supply ordinary movement geometry/response. Static block registration and block-state/property methods are unchanged. |
| Reachable block callbacks | Closed: step/fall/contact/collision callbacks and relevant cobweb/climb/slime/soul-sand/ice/liquid/piston providers have unchanged source. |
| Entity collision/push | Closed: nearby-entity player probes and world entity collision-box/push consumers are unchanged; non-player movement itself excluded. |
| Direct player responses/network inputs | Closed: position correction, velocity and explosion packet handlers and their packet models are unchanged. Direct `PlayerEntity` knockback/state writers unchanged. |
| Position/velocity/box state | Closed: all core position, velocity, collision-flag and shape writers/consumers are unchanged. |
| Excluded systems | Damage arithmetic, hunger/health production, rendering, server simulation, general non-player/vehicle movement and unrelated gameplay were not accepted as movement findings. Direct player movement effects remain in scope where they write player state. |

## Audit artifacts and reproducibility

This report records endpoint Java-source evidence only. The canonical trees were not written or regenerated. The source hash inventory and selected artifacts were independently rehashed as described above. `SHA256SUMS.txt` in this directory is the immutable sidecar for this report.