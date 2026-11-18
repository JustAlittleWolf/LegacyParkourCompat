# Independent source audit: 1.9.3 → 1.9.4 glide/climb gate

**Verdict: confirmed — no difference in the bounded operation.** I independently checked the frozen claim against the canonical local Feather Gen2 build 2 source trees. This verdict covers only the ordinary dry on-foot local player with a usable chest Elytra, synchronized fall-flying flag, normal ladder/vine, default abilities, and the adjacent fall-distance update. It is not full-pair coverage and does not establish an intermediate or broader version boundary.

## Frozen input

- Report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-193-194-alias-gate/report.md`
- SHA-256: `f73b01e80aeeef8ba20eb390bbecd673cb7f507a0033b6362acd5261ab6b1200` (matches the supplied frozen identity)
- Exact sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.9.3/ornithe-feather/` and `.../1.9.4/ornithe-feather/`.

## Provenance and bytes checked

- 1.9.3 `.decompile-provenance.json` SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`; its source map agrees with the on-disk hashes below. It records Feather Gen2 `1.9.3+build.2-mergedv2.jar` SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`, exact Feather metadata SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`, and raw 1.9.3 client object `b6985b0d3a0520dfb6f17eeb1e8ba58ce9577061` SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`.
- 1.9.4 ready marker identifies release `1.9.4`, Feather Gen2 build 2, and raw client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. Its source manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the verification sidecar records all 1,819 published source files identical and zero source differences. The verified artifact difference is only a regenerated intermediate remapped JAR.
- The local 1.9.3 and 1.9.4 SHA-256s match for `net/minecraft/entity/living/LivingEntity.java` (`bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`), `net/minecraft/entity/Entity.java` (`bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`), and `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (`8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`). The `PlayerEntity.java` files differ overall, but their `moveRelative(float,float)` override at lines 1372–1387 is text-identical; with default abilities it delegates to `super.moveRelative`.

## Independent trace

- `LivingEntity.java:905–916`: for a non-spectator player, `isClimbing()` returns true when the block at the player's position is `Blocks.LADDER` or `Blocks.VINE`.
- `LocalClientPlayerEntity.java:143–156, 446–449, 604–615, 751–759` and `LivingEntity.java:1489–1552, 1645–1712`: the loaded local player ticks through `super.tick()`, reports `isLocallyControlled() == true`, and reaches `flyingTick()` then `moveRelative(...)` through the `mobTick()` chain.
- `LivingEntity.java:1302–1310`: the movement updater guard is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`, followed by the water/lava gates and then the `isFallFlying()` branch. That branch checks the strict `velocityY > -0.5` comparison and assigns float `1.0F` immediately before glide math. Both releases have identical complete `LivingEntity.java` bytes. The updater predicate can also be true on the logical updating side (`Entity.java:2254–2257`); the source fact here is the local-client route, not a claim that vanilla's shared method is server-exclusive or client-exclusive.
- `LivingEntity.java:1355–1394`: the ladder/vine climb clamp and `fallDistance = 0.0F` are inside the `else` arm of `if (isFallFlying())`. Thus an active glide does not enter that cleanup even while `isClimbing()` is true. The glide arm calls `move(...)` at line 1341.
- `Entity.java:625–645, 795–805`: after resolving movement and ground state, `move` calls `checkFallDamage(dy, ...)`; that method resets distance on landing, otherwise adds `-dy` only for resolved negative `dy`, via `(float)(fallDistance - dy)`. The paired `Entity.java` bytes are identical.

The strict threshold, arm precedence, climb cleanup bypass, and immediate movement fall-distance handling therefore match in 1.9.3 and 1.9.4. No source-backed correction to the frozen bounded conclusion is needed.
