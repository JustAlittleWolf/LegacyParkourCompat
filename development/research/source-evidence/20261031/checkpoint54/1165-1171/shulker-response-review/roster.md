# Frozen independent review roster: Shulker opening response

This review will compare exact Mojmap 1.16.5, 1.17, and 1.17.1 source for the client-reachable Shulker-opening movement response. Roster frozen before opening the candidate closure body or other prior discovery reports. Candidate topic named by coordinator: direct Shulker response to nearby player when synchronized peek increases.

Exact paths to inspect:
- `net/minecraft/world/entity/monster/Shulker.java`: tick, synced-data update/peek interpolation, physical-peek calculation, response selection and movement dispatch.
- `net/minecraft/world/entity/Entity.java`: entity-data update dispatch, target movement call contract, movement-type resolution and thresholds.
- `net/minecraft/world/entity/EntityDimensions.java`, `net/minecraft/world/phys/AABB.java`, `net/minecraft/world/phys/Vec3.java`: attached Shulker box and target-player query geometry.
- `net/minecraft/world/level/EntityGetter.java`, `net/minecraft/world/level/Level.java`, and `net/minecraft/world/entity/EntitySelector.java`: query overload defaults and client target enumeration.
- `net/minecraft/util/Mth.java`: exact sine-table and float/double operations in `Mth.sin`.
- `net/minecraft/client/multiplayer/ClientPacketListener.java`: actual received metadata path that applies replicated Shulker peek state.
- `net/minecraft/client/multiplayer/ClientLevel.java`: client entity tick / entity-data application route as needed.

Comparison assumptions will hold fixed the Shulker entity data/attachment face, its peek animation state, the same LocalPlayer position/dimensions/flags, and relevant collision environment. Separate source facts from runtime behavior. Exclude server-side peek production and do not claim actual trajectory or frequency.
