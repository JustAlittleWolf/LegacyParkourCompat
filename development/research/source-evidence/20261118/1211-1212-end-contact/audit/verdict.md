# Immutable adjudication: 1.21.1 → 1.21.2 End contact

## Bound revisions

Frozen blind audit: `audit/blind.md` SHA-256 `3e640d9eb09e7d26a74635ded60394186b6085ee2500f36ee88b909c84165cde` (verified unchanged).

Author payloads, each SHA-256 verified against the assigned identity:

- `author/coverage.md` — `924e43c26ac6a47bc3418cf7e2f019b404080ffc230ecdf1c48cbe6fc371ee29`
- `author/1.21.1--1.21.2/end-portal-swept-contact.md` — `31a593a8c962adc5cb98b2222d0ec7ee9576f25f510af3fa71c5ee42a9164413`
- `author/1.21.1--1.21.2/end-gateway-swept-contact.md` — `bf6693af75feb7e548027a20d9b3cc11f5d82bf1cc0f8b99d9d2d28eb7db093f`

## Claim-by-claim verdicts

### End Portal swept contact

**Source claim: confirmed. Movement-scope claim: qualify.** The callback gate moved from the block's final-AABB overlap test in 1.21.1 to the End Portal inside-shape test against the movement path in 1.21.2 (`Entity.checkInsideBlocks` and `EndPortalBlock` cited in the frozen blind audit). A path crossing the slab can seed the local End Portal `portalProcess` without ending in slab overlap.

For the controlled local player, that client-side state is not itself a movement or presentation effect: `Entity.handlePortal` runs only with a `ServerLevel`; `Portal.getLocalTransition()` defaults to `NONE` in both releases; and `LocalPlayer` only uses a `CONFUSION` local transition for the portal visual effect. Thus, withdraw any implication that this local pending state directly moves the player or produces an End Portal visual. The changed contact pass also runs server-side for the player and can affect server transition eligibility; retain it as a server-producer/dependency fact, not a historical server physics hook.

**Disposition:** retain the source comparison, but revise the finding's movement meaning to distinguish the dormant client-local pending state from the server-side contact producer. Do not accept it alone as a local player movement delta.

### End Gateway swept contact

**Source claim: confirmed. Movement-scope claim: server-producer only.** The gateway callback has the same `canUsePortal(false)`, server-only, matching block entity, and cooldown gates. Since that server-only guard remains, local client contact does not create the Gateway `portalProcess`. Its new movement-path contact eligibility changes the server-side transition producer, not client-side portal simulation.

The draft's phrase “full collision shape” should be “full entity-inside shape”: 1.21.2's `BlockBehaviour.getEntityInsideCollisionShape` default is `Shapes.block()`, which is the contact-pass shape contract and need not describe physical collision response.

**Disposition:** retain as a server contact/transition dependency and qualify its movement scope. Do not add a server-side historical movement hook.

### Portal processor and entry position

**Coverage assertion: incorrect; add a separate bounded claim.** The timer/decay/expiration and portal identity behavior in `PortalProcessor` are unchanged, but the coverage record says the entry-position update is also unchanged. It is not. In 1.21.1, same-portal `Entity.setAsInsidePortal` always updates `entryPosition` and marks contact. In 1.21.2, it updates only when `insidePortalThisTick` is false; later same-tick contacts leave the first entry position in place (`Entity.java:2121–2131` vs `2214–2224`).

For a movement pass contacting two End Gateway block positions that share the singleton `EndGatewayBlock` portal instance, the 1.21.1 callback sequence leaves the last position; the 1.21.2 processor retains the first. `PortalProcessor.getPortalDestination` forwards that stored position, and EndGatewayBlock resolves the block entity/exit from it. This is a source-confirmed, potentially different server-selected destination position. End Portal's destination ignores its entry block position, so the consequence is Gateway-specific.

**Disposition:** correct the coverage claim and submit a distinct End Gateway entry-position/destination producer finding for independent review. Keep it classified as server-selected placement, not as client-side contact physics. Any effect on the client's resulting position is delivered by the authoritative teleport packet.

### Direct player velocity on portal teleport packets

**Source claim: confirmed and omitted as a movement finding in author coverage. Eligible direct client movement write under controlled inputs.** For a player with nonzero current velocity and a successful End-bound End Portal or ordinary End Gateway transition:

- 1.21.1 `ServerPlayer.changeDimension` calls the position-only `connection.teleport` overload (End transfer path: `ServerPlayer.java:840–888`); `ServerGamePacketListenerImpl.teleport` uses no relative flags (or the supplied empty set) when constructing the player-position packet. The 1.21.1 client handler sets delta components to zero when `RelativeMovement.X/Y/Z` are absent (`ClientPacketListener.java:620–695`). This is true even though the block destination passes current speed in its `DimensionTransition`; the `ServerPlayer` special path does not forward that speed as a relative-delta packet value.
- 1.21.2's End-bound End Portal transition carries `Relative.DELTA`; its non-ender-pearl Gateway transition carries `Relative.DELTA | Relative.ROTATION` (`EndPortalBlock.java:76–99`, `EndGatewayBlock.java:98–112`). `ServerPlayer.teleport(TeleportTransition)` forwards these relatives to the connection (`ServerPlayer.java:1028–1079`), and both `PositionMoveRotation.calculateAbsolute` and `ClientPacketListener.setValuesFromPositionPacket` resolve zero relative delta against current movement. The local player's resulting delta is preserved.

This is a concrete local player velocity write through client packet handling, although the triggering contact and packet values are server-produced. The server source is an authority dependency; the eligible movement operation is the client-side packet application. Do not emulate the server's historical contact or destination logic. End-to-Overworld End Portal respawn/spawn-return behavior is not adjudicated here; ender-pearl velocity is non-player and excluded.

**Disposition:** add a separate client packet movement finding for the relative-delta change on these portal transitions, with the server transition/packet construction recorded as a read-only producer dependency. This movement response is eligible for source acceptance review under the direct player response invariant, subject to the project owner's bounded hook/applicability review.

## Overall gate

Both swept-contact source differences stand. They do not, by themselves, establish local End Portal/Gateway movement simulation: End Portal local state has no local transition effect, and Gateway contact is server-gated. The entry-position assertion in author coverage must be corrected, and the omitted player velocity packet write needs its own finding/review. No full-pair coverage or runtime parity is established by this adjudication.