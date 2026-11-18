# Blind source freeze: 26.1.1 → 26.1.2 Nether portal activation and transfer

## Frozen scope

Ordinary Nether portal contact/admission, portal processor timing and cooldown, ordered server-side transition eligibility, and the first source-defined position write for an unmounted, alive player. This is a bounded same-input equivalence verdict for those slices, not a full pair freeze. Portal shape/geometry and exit search, fluids, riding, End gateways, packet delivery/arrival, and the rest of the movement inventory are outside this assignment.

## Exact local source identity

Both canonical trees are `decompiled_minecraft/<version>/unobfuscated/`, with ready markers identifying the exact requested version, `unobfuscated` mapping, Vineflower 1.12.0 and Java 25. I recomputed each ready marker's declared SHA-256 for `unobfuscated.sources.sha256` and `artifacts.sha256`; all four match. Every source file named below also matches its entry in that version's source manifest.

| Version | source manifest SHA-256 | artifact manifest SHA-256 | client archive SHA-256 |
| --- | --- | --- | --- |
| 26.1.1 | `88afdfb8264842713f8cce86b0cf88e95c2c991ee706349d6ed8b6c22d52fcb3` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

For 26.1.1, the local provenance records client URL `https://piston-data.mojang.com/v1/objects/377031a9e733ba8ab4d355959a8f6fb8eb707556/client.jar`, matching SHA-1 `377031a9e733ba8ab4d355959a8f6fb8eb707556`, and the same SHA-256 as the ready marker and artifact manifest. For 26.1.2, the ready marker and artifact manifest locally pin the client archive by SHA-256, but its local provenance omits the client URL, release-metadata identity/hash, and client SHA-1/raw archive field. Under the permitted local evidence, 26.1.2's official Mojang origin therefore remains unverified; the local artifact and source bytes are pinned, but that pin alone does not establish origin.

## Compared source slices

The following exact unobfuscated source files have identical whole-file SHA-256 in both versions:

- `net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- `net/minecraft/world/entity/PortalProcessor.java` — `3aa6a52ed06ff08cfc1b12dc163e2dee8ca4d106c008327c0afd15d325b7a51e`
- `net/minecraft/world/level/block/NetherPortalBlock.java` — `ec2c95d659dccfba68c221f0e13359dd8c9e354934e52a899a8460b8481f8ac3`
- `net/minecraft/world/entity/player/Player.java` — `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- `net/minecraft/server/level/ServerPlayer.java` — `fe7146799ebe95ba92220ad21a38a80f5352ddbb21ccefb93063b8f9a89631dd`
- `net/minecraft/server/level/ServerLevel.java` — `e6648bb88d22f33c43272cf7434102ed98784f165ba8f1fcb21231600e5987b8`
- `net/minecraft/world/entity/PositionMoveRotation.java` — `a5f0cf32266502dd2c9250d28dfc7bc85cfcf30247e91e3311018af15be319f8`
- `net/minecraft/world/level/portal/TeleportTransition.java` — `8d040ca14866a3ae403c046aaeb45315d9bb0224c8e0dc711832412d2c13cf6a`

`ServerGamePacketListenerImpl.java` differs as a whole file between releases, so I compared the assigned `teleport(PositionMoveRotation, Set<Relative>)` method slice directly; lines 1254–1263 are text-identical. No claim is made that this entire class is unchanged.

## Frozen behavior and verdict

**Contact and admission.** `Entity.checkInsideBlocks` reaches `BlockState.entityInside` for an inside-block intersection (`Entity.java:1195–1218, 1225–1284`), which dispatches to `NetherPortalBlock.entityInside` (`NetherPortalBlock.java:110–121`). That method admits only `entity.canUsePortal(false)` and then calls `setAsInsidePortal`. For an alive, unmounted player, `Entity.canUsePortal(false)` is true; a passenger or dead entity is rejected. Contact itself does not choose the target dimension.

**Local process, delay, cooldown.** `setAsInsidePortal` (`Entity.java:2538–2549`) resets an active cooldown through `setPortalCooldown`; otherwise it creates a `PortalProcessor` for a missing/different portal, or, on the first renewed contact that tick, refreshes the immutable entry position and inside-this-tick flag. `Player.getDimensionChangingDelay` is 10 ticks (`Player.java:392–394`), so setting a player's portal cooldown assigns 10 (`Entity.java:578–598`). Cooldown processing decrements a positive counter once in `handlePortal` before processing the portal (`Entity.java:2551–2573`).

`PortalProcessor` starts with `insidePortalThisTick=true` and time zero. When contact was observed, processing clears the flag and returns `allowedToTeleport && portalTime++ >= portal.getPortalTransitionTime(...)`; short-circuiting means a false admission does not increment time. When contact was absent, it decays time by four, floored at zero, and reports expiry at zero (`PortalProcessor.java:9–68`). `NetherPortalBlock.getPortalTransitionTime` returns `max(0, rule delay)` for players, selecting creative delay when `Abilities.invulnerable`, otherwise default delay; it returns zero for nonplayers (`NetherPortalBlock.java:124–132`). Thus on a reachable continuously-contacting player path, the first successful process call occurs when the pre-increment counter has reached the configured nonnegative delay. No method/order/math difference appears across the pair.

**Authoritative transfer ordering.** `Entity.handlePortal` is server-side gated by `level() instanceof ServerLevel`, processes cooldown, then invokes the portal processor with `canUsePortal(false)`. On success it sets cooldown before requesting a destination. A null destination stops the transfer. Otherwise it checks `level.isAllowedToEnterPortal(newLevel)` first, then `(same dimension || canTeleport(from,to))`, and calls `teleport(transition)` only if both permit (`Entity.java:2551–2573`). Nether portal destination selection toggles Nether versus Overworld and may return null if that server level is absent (`NetherPortalBlock.java:135–146`); exit portal construction/search is intentionally outside this freeze. `ServerLevel.isAllowedToEnterPortal` allows all targets except Nether when `ALLOW_ENTERING_NETHER_USING_PORTALS` is false (`ServerLevel.java:1964–1966`). The later `canTeleport` End-credits veto does not apply to a Nether/Overworld pair.

**First position writer.** A successful Nether/Overworld player transfer dynamically enters `ServerPlayer.teleport(TeleportTransition)` (`ServerPlayer.java:1082–1138`), sets the destination server level, and calls `connection.teleport(...)`. The compared listener method first updates teleport bookkeeping, then calls `player.teleportSetPosition`, snapshots the resulting position, and queues `ClientboundPlayerPositionPacket` (`ServerGamePacketListenerImpl.java:1254–1263`). `Entity.teleportSetPosition` calculates the absolute move/rotation and calls `setPosRaw` first (`Entity.java:3087–3100`). When coordinates differ, `setPosRaw` first assigns `this.position = new Vec3(x,y,z)`, then updates floored block/chunk positions and callbacks (`Entity.java:3740–3755`). This freezes the authoritative source-defined position write only; it does not assert packet arrival or acknowledgment.

**Verdict: same-input equivalence for the assigned slices.** The contact, admission, processor, delay, cooldown, server eligibility, transition dispatch, and source-defined first position-write classes are byte-identical by the verified source hashes, except that the packet-listener class has unrelated whole-file differences while its compared transfer method is text-identical. The reachable alive/unmounted player witness follows the same predicates, counter progression, ordering and first position assignment in 26.1.1 and 26.1.2. No independent difference is frozen for this bounded operation.