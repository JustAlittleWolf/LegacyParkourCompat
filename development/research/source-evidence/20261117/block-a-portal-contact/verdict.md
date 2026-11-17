# Independent verdict: Nether portal contact shape

Terminal status: COMPLETED

## Evidence identities

- Pre-draft source roster: `source-roster.md`, SHA-256 `a63788e036b44908a1f96468e0d32d9ccc0f320b2f8de425fcdd5a2a92ade1dc`.
- Expanded source and provenance roster: `review-roster.md`, SHA-256 `7b6d10a54c075a1515f76ab2e5fa6bab4cb62de1834329227d967cdd87218aaf`. It records 283 selected source files, all matched against the exact local source hash manifest/provenance entry. For ready-marker trees, the source-manifest SHA matched the marker. Exact IDs: `1.21.4`–`1.21.11` use Mojmap; `26.1`, `26.1.1`, `26.1.2`, `26.2` use published unobfuscated sources. All markers/provenance and local sidecars were read-only.
- Finding payload `development/research/findings/1.21.4--1.21.5/nether-portal-contact-shape.md`: SHA-256 `5ae84801ca3c3ee752320b4c1ea72ae27b17c60fc358433d22b26ddb1b740e00`, rechecked before reading.
- Finding payload `development/research/findings/1.21.5--1.21.6/nether-portal-contact-shape.md`: SHA-256 `581bc9ca09369d3c61fe33becb8a9cbd0306fb064695e9cd0f2589c311837c97`, rechecked before reading.
- Comparison checkpoint `comparison.md`: SHA-256 `8f51e625de1d481730d16bd95a1dc9b2464b6a2c1cf607d4a985d7d6fe128475`, rechecked before reading.
- Later-provider cross-check `portal-luna-native/comparison.md`: SHA-256 `10566c7ef83d87aa560c4f19a8639a22e7f59159a163ec645351948d11d13122`, rechecked before reading. This corroborates but does not substitute for my source trace.

Provider source SHA-256s, independently recomputed and matched to local source manifests/provenance:

| Release | `NetherPortalBlock.java` SHA-256 | Provider result |
| --- | --- | --- |
| 1.21.4 | `4c2271222823f571a099e558a3aa2c6c3ddcd95911f09f436ac4c68caa218bfe` | No inside-shape override |
| 1.21.5 | `1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b` | Slab override |
| 1.21.6 | `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967` | No inside-shape override |
| 1.21.7 | `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967` | No inside-shape override |
| 1.21.8 | `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967` | No inside-shape override |
| 1.21.9 | `c6b8bce302352dc76263ebcf6bb762c1f78f37beed0dbac6592b372eb5df13a4` | No inside-shape override |
| 1.21.10 | `28367732a0ddc861c7ee227b207a8d7b6c0512e0f8171b3877c36a8c3573276a` | No inside-shape override |
| 1.21.11 | `a67f0b261834631599f7aaf15eb57d75994c4d3ed8ceb58ef10aa4e7406543e6` | No inside-shape override |
| 26.1 | `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3` | No inside-shape override |
| 26.1.1 | `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3` | No inside-shape override |
| 26.1.2 | `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3` | No inside-shape override |
| 26.2 | `749fa4f33bf71d9758404778c92e88782e510a9e0868c970c18aa81a00db70e3` | No inside-shape override |

## Per-claim verdicts

### Claim A — 1.21.4 to 1.21.5 narrows Nether portal contact to its slab

**Verdict: ACCEPT for the bounded source claim and stated conditional player consequence.** The releases are adjacent and exact. In 1.21.4, `NetherPortalBlock.getShape` uses its axis-specific four-unit plane (`NetherPortalBlock.java` 60–69), but the block has no `getEntityInsideCollisionShape` override. `BlockBehaviour.getEntityInsideCollisionShape` returns the singleton `Shapes.block()` (`BlockBehaviour.java` 353–355), and `Entity.checkInsideBlocks` bypasses the swept-shape test for that singleton (`Entity.java` 1050–1073). In 1.21.5, `NetherPortalBlock` defines `SHAPES` from `Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0))` and overrides the inside provider to return `state.getShape(...)` (`NetherPortalBlock.java` 45–69). The new entity scan applies `collidedWithShapeMovingFrom` for that non-sentinel shape and only calls `entityInside` on a hit (`Entity.java` 1079–1098). `Block.column` centers the four-unit thickness at 8/16 and `rotateHorizontalAxis` supplies the other axis (`Block.java` 168–175; `Shapes.java` 344–350). The portal registration uses `noCollission()` (`Blocks.java` 2084–2089), so this operation is separate from ordinary movement collision.

Applicability is conditional on a live, non-sleeping, non-passenger player with no active portal cooldown, block effects enabled, and the portal cell being visited by the scan. A static witness is supported by the sources: an X-axis portal, grounded standing player (width `0.6F`), feet inside the portal vertically, and center Z at portal Z + `0.05`. The player box reaches only to Z + `0.35`, short of the slab at Z + `0.375`; it still overlaps the portal block cell. The 1.21.4 sentinel calls the portal callback while the 1.21.5 slab test misses. This is a concrete source-derived witness, not a runtime trajectory result.

Movement path: `NetherPortalBlock.entityInside` gates on `canUsePortal(false)` and calls `setAsInsidePortal` (1.21.4 104–108; 1.21.5 107–111). The entity processor starts with its per-tick flag set; `LocalPlayer.aiStep` consumes that flag on the following local tick before input polling (1.21.4 `PortalProcessor.java` 15–28, `Entity.java` 2242–2253, `LocalPlayer.java` 642–663 and 844–873). With a disallowed non-pausing screen, a focused non-macOS window, and physically held keyboard Space, the source path closes the screen, grabs the mouse, repolls key mappings, samples `KeyboardInput`, and can set jump before grounded jump/travel (1.21.4 `Minecraft.java` 1030–1069, `MouseHandler.java` 359–373, `KeyMapping.java` 55–61, `KeyboardInput.java` 22–30, `LivingEntity.java` 2739–2786; corresponding 1.21.5 slices are cited in the immutable draft). Thus the portal contact difference can reach player Y velocity via ordinary jump. Local sound and screen/effect presentation are not movement by themselves.

### Claim B — 1.21.5 to 1.21.6 removes the slab override and restores the full-cell sentinel

**Verdict: ACCEPT for the bounded source claim and stated conditional player consequence.** `1.21.5` is the only one of the two releases whose portal provider returns the axis-dependent outline (`NetherPortalBlock.java` 45–69). In 1.21.6, that override is absent; the portal still defines its ordinary outline and callback (`NetherPortalBlock.java` 45–69, 102–106), while `BlockBehaviour.getEntityInsideCollisionShape` returns `Shapes.block()` (`BlockBehaviour.java` 310–316). The entity visitor again bypasses swept shape testing for the sentinel (`Entity.java` 1113–1162). The same portal-cell-only witness can therefore callback in 1.21.6 after the 1.21.5 slab provider misses. `PortalProcessor`, local effect handling and the portal callback gates remain reachable; the callback itself has no position/velocity write. `Entity.handlePortal` is guarded by `ServerLevel` in both versions (`Entity.java` 2223–2245 in .5; 2378–2413 in .6), so this local cause does not require server teleport or arrival-transform code.

### Later provider check — 1.21.8 through 26.2

**Verdict: ACCEPT as a sampled provider invariant only.** I independently read and source-hash-checked `NetherPortalBlock`, `BlockBehaviour`, `BlockState`, and `Entity` for 1.21.8–1.21.11 and 26.1–26.2. The portal classes have no inside-provider override; the inherited default remains the singleton `Shapes.block()`, and the visitor retains the identity shortcut. In 26.2 this is `BlockBehaviour.java` 331–333 and `Entity.java` 1342–1345; `NetherPortalBlock.entityInside` still gates and calls `setAsInsidePortal` (`NetherPortalBlock.java` 112–123). The later candidate traversal can differ, so this does not assert whole contact-scan parity.

26.2 keeps a semantically equivalent screen-close → key-poll → jump/travel route for a survival inventory screen. `Minecraft` opens `InventoryScreen` for `keyInventory` when inventory is not server-controlled (1910–1916); it inherits `AbstractContainerScreen.isPauseScreen() == false` and does not override `Screen.isAllowedInPortal`, whose default delegates to `isPauseScreen()` (`AbstractContainerScreen.java` 531–538; `Screen.java` 426–436). The portal transition handler closes this screen before `input.tick` (`LocalPlayer.java` 943–967, 767–786). It sends `ServerboundContainerClosePacket` and immediately runs the client-side close; it does not wait for a reply or write movement state (`LocalPlayer.java` 335–343; `Player.java` 425–427). `MouseHandler.grabMouse` calls `KeyMapping.setAll` on non-macOS through `InputQuirks.RESTORE_KEY_STATE_AFTER_MOUSE_GRAB = !ON_OSX` (`MouseHandler.java` 407–423; `InputQuirks.java` 5–10); `KeyboardInput.tick` samples jump (22–30). `LocalPlayer.applyInput` copies jump (692–704), then `LivingEntity.aiStep` can call `jumpFromGround` before travel (3084–3145). Caveat: 26.2 `ChatScreen` explicitly allows portal transition, so the chat example in the 1.21.x draft is not portable to 26.2; inventory is a valid non-chat witness. The close packet remains an independent server/menu side effect; runtime server correction is unverified.

## Open dependencies and limits

- The two accepted claims are adjacent exact-version observations. They do not locate any wider feature introduction or removal beyond those adjacent release boundaries.
- Endpoint static tracing does not independently close every 1.21.4–1.21.8 scan traversal edge (high-speed, axis ordering/recomposition, and movement queue limits). The stated witness avoids depending on those edges by using a stationary or sufficiently short movement and a visited cell. Broader contact parity remains open.
- The client path is conditional on game focus, non-macOS key-state restoration, held key state, grounded jump eligibility and no later correction. No runtime, game, TAS or server launch was performed. The container-close packet may have remote menu consequences; no synchronous client movement reset appears in the trace, but network correction is outside this verdict.
- Source identity was verified from local source manifests and provenance only. Campaign-cached raw client/mapping archives were not independently downloaded or rehashed. No claim about full-pair discovery, implementation, server behavior or runtime parity is made.
