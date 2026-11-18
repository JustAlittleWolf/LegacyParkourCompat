# 26.1 → 26.1.1: ordinary Nether portal contact (bounded source comparison)

## Source identity and integrity

Both sides are the exact published `26.1` and `26.1.1` releases, decompiled from the official unobfuscated client artifacts with Vineflower 1.12.0 / Java 25 under the same recorded preparation run (`fcd113c3fafcfde91d5cb5c8308b77292c601f9d`). Ready markers identify `versionId` and `versionMetadataId` as `26.1` / `26.1.1`; artifact client SHA-256 values are `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` and `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`. Published mapping: unobfuscated (no external mapping file).

The complete source-manifest SHA-256 matches each ready marker: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` (`26.1`) and `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` (`26.1.1`). Relevant full source bodies are intact and identical on both sides (these are whole-file hashes, also matching each manifest entry):

| Source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/PortalProcessor.java` | `3aa6a52ed06ff08cfc1b12dc163e2dee8ca4d106c008327c0afd15d325b7a51e` |
| `net/minecraft/world/level/block/NetherPortalBlock.java` | `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3` |
| `net/minecraft/world/level/block/Portal.java` | `23d82f821e16c6dfeb2bc9c38f962cf62beb525e78577884a48df10e3c7701d5` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

## Bounded trace and verdict

**Scoped verdict: no source difference found** for ordinary non-riding player eligibility at Nether-portal contact, portal processor contact state, local-player portal effect/cooldown handling, server-side delay/cooldown/environment gate order, or the boundary between local prediction and server-authoritative transition. Each listed file has byte-identical decompiled contents across this pair. This establishes equivalence for this bounded trace only; it is not a full-pair movement audit.

1. `NetherPortalBlock.entityInside` calls `entity.canUsePortal(false)` before `setAsInsidePortal(this, pos)` (`NetherPortalBlock.java:110–121`, both releases). `Entity.canUsePortal(false)` permits an alive entity only when it is not a passenger (`Entity.java:3135–3137`).
2. `Entity.setAsInsidePortal` checks cooldown first (`Entity.java:2538–2549`). If cooldown is positive, it resets cooldown via `setPortalCooldown()` and does not create/mark the portal processor on that contact. Otherwise it creates a processor for a new/different portal, or updates its entry position and marks it inside for the tick when the existing processor was not marked inside (`Entity.java:2542–2546`).
3. A local player’s `aiStep` skips both local transition effects and cooldown processing while the `LevelLoadingScreen` is active; otherwise it calls `handlePortalTransitionEffect(getActivePortalLocalTransition() == CONFUSION)` and then `processPortalCooldown()` in that order (`LocalPlayer.java:766–775`). `NetherPortalBlock.getLocalTransition` returns `CONFUSION` (`NetherPortalBlock.java:241–244`), so ordinary Nether portal contact makes that local effect branch eligible. The effect routine acts only when the processor is inside this tick; it clears that flag and adjusts visual intensity (`LocalPlayer.java:931–967`). This routine does not count Nether portal delay or choose/apply a destination.
4. `Entity.baseTick` calls `handlePortal()` (`Entity.java:503–517`), but `handlePortal` has an outer `level() instanceof ServerLevel` gate, so it does not process teleportation on the logical client (`Entity.java:2551–2573`). On the server, order is: decrement cooldown; if a processor exists, run `processPortalTeleportation(..., canUsePortal(false))`; only on success set cooldown; obtain a transition; then require non-null transition and `level.isAllowedToEnterPortal(newLevel)` plus either same dimension or `canTeleport(level, newLevel)` before calling `teleport` (`Entity.java:2551–2573`). `ServerPlayer.processPortalCooldown` additionally skips decrement while `isChangingDimension` (`ServerPlayer.java:1936–1941`).
5. `PortalProcessor.processPortalTeleportation` decays time by four and returns false if it was not marked inside this tick; otherwise it clears the flag and short-circuits on `allowedToTeleport` before evaluating `portalTime++ >= getPortalTransitionTime(...)` (`PortalProcessor.java:21–28`). For a `Player`, `NetherPortalBlock.getPortalTransitionTime` clamps the selected game-rule delay to at least zero and selects the creative delay when abilities are invulnerable, otherwise the default delay (`NetherPortalBlock.java:123–132`). These are server-side transition gates; they are not client movement prediction.

## Boundary / partial coverage

The source shows the server calling `Entity.teleport(transition)` only after the gates pass; the teleport method itself rejects non-`ServerLevel` entities (`Entity.java:2981–2993`). I did not trace exit-portal construction, cross-dimension transfer internals, the server-to-client position packet, packet arrival, or the client packet handler’s resulting position write. Thus the first post-transition client correction writer is not claimed here. Portal collision shape/geometry, End/gateway routing, riding branches, runtime behavior, and full-pair coverage were not examined.

**Canonical difference finding:** none for this bounded slice; no positive source difference was observed.