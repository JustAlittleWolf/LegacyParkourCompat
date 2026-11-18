# Independent audit verdict: 1.14 → 1.14.1 passenger dismount and mounted XYZ claim

## Immutable inputs checked

- Author comparison body: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-portal-passenger-position-114-1141/roster-comparison.md`, raw SHA-256 `ef93a1a0c605fe6571bec5d449351099e3ea72b42a207aac9cefcd66ed935537` (recomputed and matched).
- Frozen independent pre-claim source roster/analysis: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-portal-passenger-position-114-1141-audit/preclaim-source-roster-analysis.md`, raw SHA-256 `e267659ad3cdcd4170b5f356ae8d084ba31ea40e9c75cfda7b3510144fd86ab5` (recomputed and matched; left unchanged).
- Both reports identify Yarn-family exact inputs for 1.14 (`1.14+build.21`) and 1.14.1 (`1.14.1+build.10`). The author-listed pinned client, Yarn, intermediary, and provenance-manifest identities match the frozen independent roster. The relied-on LivingEntity, Entity, ServerPlayerEntity, and ClientPlayerEntity source SHA-256s also match that roster and the per-release provenance `sources` entries.

## Verdict

**Accept the bounded no-difference source claim, with a source-hash metadata caveat.** The reviewed scope is ordinary dismount/placement/correction and the concrete mounted logical-client XYZ writer for this endpoint pair. It is not full-pair freeze, a claim about an exact introduction boundary outside the pair, or runtime parity.

The source semantics support the author's conclusion:

- `LivingEntity.stopRiding` snapshots the current vehicle, calls the base dismount, then invokes `method_6038` only when the old vehicle was non-null, the relation actually changed, and `!world.isClient`. The generic placement helper is identical in both releases: it excludes boat/horse vehicles from its safe-stand-up search, searches the fixed offsets for other vehicles, and uses virtual `requestTeleport` on that branch. For a `ServerPlayerEntity`, the virtual call resolves to its requestTeleport override and requests a network correction.
- `ServerPlayerEntity.stopRiding` independently snapshots and rereads the vehicle around `super.stopRiding()`. It asks the handler to correct position/rotation only when vehicle identity changed and the handler is non-null. That outer correction follows any inner helper correction whose path reached `requestTeleport`. This gate and order match in both versions. The handler's ordinary `requestTeleport` passes an empty relative-flag set, so its XYZ packet coordinates are absolute.
- Portal handling is not a dismount-placement branch. `Entity.tickPortal` is server-world gated and changes dimension only when Nether travel is enabled, the entity has no vehicle, and the portal timer reaches its threshold. The adjacent `PortalBlock.onEntityCollision` gate additionally excludes entities with passengers and requires `canUsePortals`; it does not detach an entity. No special `PORTALS`-named branch was found.
- For a mounted client player, `Entity.tickRiding` zeroes velocity, ticks, then asks the vehicle to update passenger position if the player remains mounted. The vehicle's `updatePassengerPosition` checks that it still has this passenger and calls `setPosition(vehicle.x, vehicle.y + mountedHeight + passengerHeightOffset, vehicle.z)`. `setPosition` writes x/y/z and rebuilds the bounding box. `ClientPlayerEntity.tickRiding` calls the superclass first; its following boat-input forwarding contains no XYZ assignment. The XYZ writer and gates are unchanged between the pair.

## Hash caveat

The author-provided range digests are not verified by this audit: the report does not declare the extraction/normalization contract, and direct SHA-256 of the cited inclusive source lines encoded as UTF-8/LF does not reproduce them. The `Entity.java:1672–1695` digest is 66 hexadecimal characters rather than a 64-character SHA-256. This does not change the semantic no-difference verdict because the exact source files, canonical provenance entries, and method bodies were inspected directly, but those range-digest claims should be corrected or precisely defined before reuse as immutable evidence identities.

No packet arrival, gameplay execution, or runtime movement result is claimed or verified.

## Evidence identity

The adjacent `.sha256` file contains the SHA-256 of these exact UTF-8/LF bytes.