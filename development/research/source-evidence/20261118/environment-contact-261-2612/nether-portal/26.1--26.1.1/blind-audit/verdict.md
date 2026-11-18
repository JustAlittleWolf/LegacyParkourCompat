# Independent verdict: bounded ordinary Nether portal comparison

## Binding identities

- Author comparison reviewed: `source/comparison.md`, raw SHA-256 `e4a82fd0b2063fdb16ce60c46c299300d250754b52d2cccd2da7c49be3154288` (recomputed and matched).
- Bound frozen source audit: `blind-audit/frozen.md`, raw SHA-256 `128e599d7e2fe4c1304f156ea3d81b5c985599d70154b23bdc14c194b855cfa1` (recomputed; frozen bytes preserved).
- Source identity: exact published 26.1 and 26.1.1 unobfuscated snapshots. The author’s source-manifest identities agree with the ready markers and my frozen audit. I independently confirmed the paired source bodies cited below are byte-identical.

## Verdict

**Accept the author's bounded no-difference verdict through server eligibility and the admitted `Entity.teleport(transition)` call.** The ordered contact/admission, portal processor timing, local player effect/cooldown handling, server-only processing, delay/cooldown and environmental gates all agree with the exact paired sources. The author’s cited distinctions and order are supported by the compared source. This is a bounded source verdict, not full-pair coverage or runtime parity.

The author's comparison explicitly stops before cross-dimension transfer internals and the server-to-client position correction writer. My frozen audit separately evidences that downstream boundary: `ServerPlayer.teleport` forwards `PositionMoveRotation.of(transition)` and relatives through its connection and resets tracked position; `ServerGamePacketListenerImpl.teleport` applies/stores the server position and sends `ClientboundPlayerPositionPacket`; `ClientPacketListener.handleMovePlayer` applies the position when the player is not riding, acknowledges, and sends a position/rotation response. Those paired source files are included in `frozen.md` and were byte-identical in 26.1 and 26.1.1. This is source-defined correction flow only. No packet arrival, timing, or runtime result is claimed.

## Reviewed source evidence

The author’s bounded chain is consistent with the primary sources:

1. `NetherPortalBlock.entityInside` admits through `canUsePortal(false)` before recording contact; the predicate requires alive and not passenger.
2. `Entity.setAsInsidePortal` refreshes cooldown on positive cooldown, otherwise creates/updates the portal processor and per-tick contact state.
3. `LocalPlayer.aiStep` processes local portal effects before local cooldown when not in a level-loading screen. Nether portal's local transition is `CONFUSION`; the local effect consumes the per-tick flag and adjusts effect intensity. `Entity.handlePortal` gates transfer processing on `ServerLevel`.
4. Server processing decrements cooldown, processes a portal tick, refreshes cooldown after processor success, obtains a destination, then checks non-null destination, `isAllowedToEnterPortal`, and same-dimension or `canTeleport` eligibility before invoking `teleport`.
5. `PortalProcessor` decays by four when not inside this tick; otherwise clears the flag and evaluates `allowedToTeleport && portalTime++ >= getPortalTransitionTime(...)`. Nether player delay uses the creative/default game rule selected by invulnerability abilities and clamps to zero or more. `Player` dimension-changing delay is 10; generic entity default is 300; `ServerPlayer` skips cooldown decrement while changing dimensions.

These reviewed operations are byte-identical across the pair. Relevant raw hashes, source-manifest verification, exact paths and method ranges are recorded in the bound frozen audit. The author’s hash is exact and unchanged at review time.

## Residual scope

The correction boundary above establishes source writers and handler behavior only. No claim is made about message delivery or arrival, tick/network scheduling, resulting runtime coordinates, intermediate patch introduction, or unreviewed movement systems. Portal geometry/exit search, riding, fluids and End/gateway paths remain outside this verdict.