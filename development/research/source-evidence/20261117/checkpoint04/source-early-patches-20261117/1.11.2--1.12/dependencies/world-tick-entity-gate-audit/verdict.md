# Independent audit: `World.tickEntity` player loaded-area gate

Verdict: **ACCEPT** — this closes the bounded `World.tickEntity` changed-scheduling slice only.

## Bound evidence

- Assignment scope: Minecraft `1.11.2 → 1.12`, exact Ornithe Feather endpoint sources, the dependency note below, and the supplied partial full-pair verdict. This verdict does not assess other movement gates or pair completeness.
- Dependency note: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/world-tick-entity-gate.md`, SHA-256 `f9ad4d679f1126aad4fb2312bc7286ce30437f1e4639b5ba320f7d0d0091c429`.
- Full-pair audit input: `development/research/source-evidence/20261117/checkpoint04/source-early-patches-20261117/1.11.2--1.12/audit/full-pair-verdict-r2.md`, SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`; it lists this exact gate as an open closure requirement and explicitly leaves pair coverage partial.
- Paired `World.java` SHA-256: 1.11.2 `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`; 1.12 `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`. The assigned note's caller-source hashes also match the exact client sources inspected.

## Source findings

In 1.11.2, `World.tickEntity(Entity)` calls the two-argument overload with `true`. That overload's `!requireLoaded || isAreaLoaded(...)` condition encloses the entire bookkeeping and entity-tick body (World.java:1404–1474). A failed area check returns before previous-position/rotation snapshots, entity ticking, chunk bookkeeping, and passenger processing.

In 1.12, the overload performs the area check only inside `if (!(entity instanceof PlayerEntity))` (World.java:1408–1482). Thus PlayerEntity bypasses this area-loaded early return. The actual `entity.tick()` / `rideTick()` call remains conditional on `requireLoaded && entity.inChunk`; passenger processing retains `requireLoaded && entity.inChunk`. This change removes one prerequisite; it does not force a tick when `inChunk` is false.

The regular entity loop in both World.java files calls the one-argument overload for each nonremoved entity except `ServerPlayerEntity` (1.11.2: 1274–1295; 1.12: 1278–1299), so this route passes `requireLoaded=true`. The exact source search found the other direct call sites: passenger recursion; ServerWorld's server entity loop/override; Entity dimension-transfer and teleport calls with `false`; and PlayerManager / ServerPlayNetworkHandler calls with `false`. The false-argument calls bypass the area check on both versions and are not evidence for the client route.

The client `Minecraft` tick invokes `world.tickEntities()` while unpaused (1.11.2: 1414–1420; 1.12: 1462–1469). `ClientWorld` extends `World`, overrides `addEntity` by calling `super.addEntity`, and does not override `tickEntities` or `tickPlayers`. Client setup/respawn adds `this.player` to that world in both endpoints. `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`; it is not the excluded `ServerPlayerEntity`. Base `World.addEntity` appends accepted entities to `entities`. Therefore the local client player is reachable through the regular entity loop and the changed PlayerEntity branch with `requireLoaded=true`.

Block entities are scheduled in the distinct `tickingBlockEntities` loop in `World.tickEntities()` (1.11.2: 1313 onward; 1.12: 1317 onward), with their own removed/world/chunk-loaded/world-border gates. This loop does not call `tickEntity` and is unaffected by the player-type area gate.

## Disposition and limits

**Accept as a player-eligible movement scheduling dependency, closed for this exact gate.** The paired gate, direct caller graph, and local-client reachability are supported by the exact endpoint sources. When the 1.11.2 area predicate fails, the whole `tickEntity` body is skipped; in 1.12 a PlayerEntity proceeds past that check, subject to the unchanged inner gates. This establishes a source-level changed scheduling path with potential to control client-player ticking; it makes no claim about runtime occurrence, map frequency, or a particular trajectory.

This closes only the changed `World.tickEntity` loaded-area scheduling slice identified by the supplied partial full-pair verdict. It does not close any other player gate or neighboring dependency, establish an intermediate introduction boundary, upgrade pair coverage, or permit pair freeze.
