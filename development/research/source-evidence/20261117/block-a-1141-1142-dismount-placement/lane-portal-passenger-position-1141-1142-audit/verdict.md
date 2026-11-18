# Independent source audit verdict — 1.14.1 → 1.14.2

## Inputs and identity

The submitted `comparison.md` SHA-256 is `2ffe9dc31603cec58086b722dee7e4353545eccc91889796cc950a14a6da33d1`, matching the assigned identity. The submitted `source-roster.txt` SHA-256 is `ac604983703e5ed08ca6b1c0777e780084e2e36c2e8de766cf2dacc89737ada7`, also matching. Its exact Yarn release/mapping identities and listed source hashes match the local provenance manifests. The independently frozen source roster is `source-roster.txt`, supplemented before verdict by `source-roster-supplement.txt` after tracing the ordinary dismount call chain.

## Verdict

**Submitted rationale rejected as incomplete; bounded no-difference disposition confirmed after adding the omitted dismount-placement source. No source-confirmed finding for this pair.**

The comparison's statement that ordinary dismount does not choose a new XYZ is false for living passengers. `PlayerEntity.tickRiding()` detaches a sneaking player on the logical server (`PlayerEntity.java:437–440` both versions). `PlayerEntity.stopRiding()` calls `LivingEntity.stopRiding()`; on a successful detach, `LivingEntity.stopRiding()` invokes `method_6038(oldVehicle)` only under `!world.isClient` (`LivingEntity.java:2305–2311` in 1.14.1; `2311–2317` in 1.14.2). That method performs ordinary dismount placement (`LivingEntity.java:1650–1727` in 1.14.1; `1657–1734` in 1.14.2), selecting a candidate from the old vehicle's movement direction, checking collision and solid-top/fluid conditions, and either requesting a teleport or setting position. The complete method body and its gates are behaviorally identical in the pair. Its collision dependencies `ViewableWorld.doesNotCollide/getCollisionShapes`, `BlockState.getCollisionShape/hasSolidTopSurface`, and `Block.isFaceFullSquare` were checked as listed in the supplement; the relevant methods are identical. The unrelated generic `ServerPlayNetworkHandler.onPlayerMove` recheck remains outside this audit.

`ServerPlayerEntity.stopRiding()` snapshots the vehicle, calls `super.stopRiding()` (therefore allowing the server-only placement above), then requests a network teleport when the relationship changed and the handler exists (`ServerPlayerEntity.java:759–765` both releases). Thus the correction uses authoritative post-dismount XYZ, which can be a newly selected placement. It does not choose that placement itself. `ServerPlayNetworkHandler.requestTeleport/teleportRequest` has the same coordinate/flag recording, player-position update, ID and packet path in the pair (1.14.1 `799–817`; 1.14.2 `804–822`).

Portal eligibility and logical-side handling are unchanged: `PortalBlock.onEntityCollision()` requires no vehicle, no passengers and `canUsePortals()` (`PortalBlock.java:101–104` both releases); `Entity.setInPortal()` limits portal-position computation to `!world.isClient` (`Entity.java:1647–1670` / `1649–1672`); `tickPortal()` operates under `world instanceof ServerWorld`, with the same no-vehicle and elapsed-time gates (`Entity.java:1672–1695` / `1674–1697`). The changed relationship may affect later eligibility, but no portal-specific XYZ relocation was found.

Client packet XYZ handling is unchanged: `ClientPlayNetworkHandler.onPlayerPositionLook()` applies relative XYZ by adding current coordinates or takes absolute values and zeros velocity on absolute axes, updates position/rotation, confirms the teleport and sends applied XYZ (`ClientPlayNetworkHandler.java:642–690` both releases). `onEntityPassengersSet()` only reconciles passenger relations and has no dismount placement path (`:899–915` both releases).

## Scope limits and exact disposition

This verdict covers the exact endpoint pair 1.14.1+build.10 → 1.14.2+build.7 and the frozen operations only. It does not establish neighboring patch boundaries, full player-tick coverage or runtime parity. The 1.14.2 generic server collision helper extraction is outside the assigned dismount-triggered correction claim.

Disposition to master: **revise the submitted comparison text to include `LivingEntity.method_6038` and clarify that server correction replays the resulting authoritative XYZ; retain no-finding/no-difference status for the bounded pair after that closure.**