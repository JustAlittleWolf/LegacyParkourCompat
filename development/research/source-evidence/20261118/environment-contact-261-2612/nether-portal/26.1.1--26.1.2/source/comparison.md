# Nether portal comparison: 26.1.1 → 26.1.2

## Scope and verdict

Bounded source comparison for an ordinary, non-riding player contacting a Nether portal: block contact callback, portal admission, cooldown and delay progression, transition eligibility, and the server player's first position correction. The compared call path is textually identical in both releases. No positive movement finding is supported for this slice.

This does not establish full-pair tick coverage. Portal collision geometry and fluid intersection are outside this bounded comparison; End portals, End gateways, vehicle/passenger behavior, full destination-search pairing, and arrival collision resolution are also not assessed here. “Arrival” below means the accepted server transition and its correction, not a claim about destination geometry or runtime outcome.

## Exact source identity

Both inputs are the local canonical `decompiled_minecraft/<version>/unobfuscated/` trees: published unobfuscated class names, with no mapping set applied. These are Vineflower decompilations of client artifacts, not Mojang-published Java source.

| Release | Ready/source identity | Official artifact identity and origin limits |
|---|---|---|
| 26.1.1 | Ready marker `unobfuscated.ready.json`: version `26.1.1`, mapping `unobfuscated`, 6,882 source files. Source-manifest SHA-256 `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; artifact-manifest SHA-256 `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81`. | Provenance records Mojang client URL `https://piston-data.mojang.com/v1/objects/377031a9e733ba8ab4d355959a8f6fb8eb707556/client.jar`, client SHA-1 `377031a9e733ba8ab4d355959a8f6fb8eb707556`, and client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`. Published unobfuscated; mapping is not applicable. |
| 26.1.2 | Ready marker `unobfuscated.ready.json`: version `26.1.2`, mapping `unobfuscated`, 6,882 source files. Source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact-manifest SHA-256 `89f648229de83b010946069c5f9f1f3bed7ef36efb84ede9224e80cce87118f92`. | Ready marker pins client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; artifact manifest contains `26.1.2/client.jar` and `26.1.2/version.json` hashes. The local provenance record is abbreviated and omits their source URL and the client SHA-1, so those fields cannot be independently established from this tree's provenance. Mapping is not applicable. |

For the consumed files listed below, computed SHA-256 values match their entries in each release's `unobfuscated.sources.sha256`. The ready markers' declared source-manifest hashes match the manifest files. No source tree was regenerated or modified.

## Reachable route and gates

- `Entity.checkInsideBlocks` invokes the contacted block's `entityInside` callback for an intersecting block (`Entity.java:1195–1270`). `NetherPortalBlock.entityInside` admits an entity only through `entity.canUsePortal(false)` and calls `setAsInsidePortal(this, pos)` (`NetherPortalBlock.java:110–121`). `Entity.canUsePortal(false)` requires an alive entity that is not a passenger (`Entity.java:3135–3137`); this matches the requested ordinary non-riding case.
- `Entity.setAsInsidePortal` resets cooldown while it is positive. Otherwise it creates a processor for a new portal, or updates its entry block and marks it inside once for the current tick (`Entity.java:2538–2549`). `Entity.baseTick` calls `handlePortal` before its subsequent fluid interaction (`Entity.java:503–525`). `handlePortal` only processes the portal while `level()` is a `ServerLevel`, so the authoritative progression and teleport are server-side (`Entity.java:2551–2573`).
- Each server tick first decrements a positive cooldown (`Entity.java:594–598`). `PortalProcessor` decays progress by `max(portalTime - 4, 0)` on a tick without contact; on contact it clears the per-tick flag and tests `allowedToTeleport && portalTime++ >= getPortalTransitionTime(...)` (`PortalProcessor.java:21–28, 39–41`). Nether portal delay for players is the nonnegative value of the creative delay gamerule when abilities are invulnerable, otherwise the default delay gamerule; nonplayers get zero (`NetherPortalBlock.java:124–132`).
- After the processor fires, `Entity.handlePortal` sets cooldown before obtaining the destination. A null destination stops there. Otherwise, entry requires `level.isAllowedToEnterPortal(newLevel)` and either a same-dimension transition or `canTeleport(level, newLevel)` (`Entity.java:2555–2565`). A Nether portal chooses the opposite of Nether/Overworld, and returns null if that target server level is absent (`NetherPortalBlock.java:135–146`). For this Nether-to-Overworld/Overworld-to-Nether route, `Entity.canTeleport` has no special rejection; its rejection branch is End-to-Overworld with a passenger player who has not seen credits (`Entity.java:3139–3149`).
- `ServerPlayer.teleport` sends a respawn packet first for a dimension change, then calls `connection.teleport(PositionMoveRotation.of(transition), transition.relatives())` after switching the server level (`ServerPlayer.java:1082–1124`). That is the first position/rotation correction writer on the ordinary cross-dimension player path. The transition is constructed with zero delta movement and relative delta/rotation flags in the Nether portal destination helper (`NetherPortalBlock.java:214–239`); arrival position selection itself is outside this slice.

## Paired source coverage

Each row's 26.1.1 and 26.1.2 file hashes are identical, and each agrees with its source-manifest entry. The common hashes are:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/PortalProcessor.java` | `3aa6a52ed06ff08cfc1b12dc163e2dee8ca4d106c008327c0afd15d325b7a51e` |
| `net/minecraft/world/level/block/NetherPortalBlock.java` | `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3` |
| `net/minecraft/world/level/block/Portal.java` | `23d82f821e16c6dfeb2bc9c38f962cf62beb525e78577884a48df10e3c7701d5` |
| `net/minecraft/world/level/portal/TeleportTransition.java` | `8d040ca14866a3ae403c046aaeb45315d9bb0224c8e0dc711832412d2c13cf6a` |
| `net/minecraft/world/level/portal/PortalShape.java` | `d24eb17a520a2b02f594bdfb2d9f4ef5608a35fe27f9678788e096bb6a1cf7a3` |
| `net/minecraft/world/level/Level.java` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |
| `net/minecraft/server/level/ServerLevel.java` | `e6648bb88d22f33c43272cf7434102ed98784f165ba8f1fcb21231600e5987b8` |
| `net/minecraft/server/level/ServerPlayer.java` | `fe7146799ebe95ba92220ad21a38a80f5352ddbb21ccefb93063b8f9a89631dd` |

The source-wide search finds no additional `setAsInsidePortal`, `processPortalTeleportation`, or Nether portal delay/destination call sites beyond these paths (the other call sites belong to End portals/gateways and were excluded). Since all paired files consumed for this route are byte-identical, no operation-order, numeric, state-write, eligibility, or correction difference is established.
