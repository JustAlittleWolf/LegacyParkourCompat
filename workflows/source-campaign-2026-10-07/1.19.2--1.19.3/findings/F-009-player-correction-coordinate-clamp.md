# F-009: Absolute player correction bypasses the X/Z entity-coordinate clamp

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: absolute server player-position correction; S7-player-position-correction-range, S7-player-rotation-correction
- Classification: changed behavior
- Confidence: source-confirmed conditional client response to an external/custom-server correction packet
- Applicability: historical player behavior, when the client receives a finite absolute X or Z correction coordinate strictly outside `[-3.0E7, 3.0E7]` from an external/custom server
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned original Mojmap source publications

## Paired evidence

### A artifact identity

- Evidence artifact record ID: original A publication, `../run.md` Artifact manifest A
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/`
- Evidence artifact SHA-256: source manifest `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.sources.sha256` / same hash above
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.2/artifacts.sha256` / `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`
- Original derived-artifact availability and SHA-256 or expected hash: published Mojmap source tree and mapped jar available; mapped jar SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- Source/raw-input hash relation and verification reference: verified against the original A readiness and artifact manifests recorded in `../run.md`
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication
- Provenance limitations: exact requested/resolved release and official Mojang mapping provenance are recorded in `../run.md`
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` lines 563-628, SHA-256 `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`; `net/minecraft/world/entity/Entity.java`, `Entity.absMoveTo(double,double,double,float,float)` and coordinate overload lines 1194-1209, plus `Entity.setPos(double,double,double)` lines 376-379, SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, `teleport(double,double,double,float,float,Set,boolean)` lines 994-1010, SHA-256 `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2`; `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` lines 18-52, SHA-256 `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0`

### B artifact identity

- Evidence artifact record ID: original B publication, `../run.md` Artifact manifest B
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/`
- Evidence artifact SHA-256: source manifest `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap.sources.sha256` / same hash above
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.3/artifacts.sha256` / `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`
- Original derived-artifact availability and SHA-256 or expected hash: published Mojmap source tree and mapped jar available; mapped jar SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- Source/raw-input hash relation and verification reference: verified against the original B readiness and artifact manifests recorded in `../run.md`
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication
- Provenance limitations: exact requested/resolved release and official Mojang mapping provenance are recorded in `../run.md`
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` lines 592-667, SHA-256 `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`; `net/minecraft/world/entity/Entity.java`, `Entity.absMoveTo(double,double,double,float,float)` and coordinate overload lines 1199-1214, plus `Entity.setPos(double,double,double)` lines 376-379, SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`; `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, `teleport(double,double,double,float,float,Set,boolean)` lines 988-1004, SHA-256 `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`; `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` lines 18-52, SHA-256 `18f1e4f2ebc30510eefb53f139bb97402cf3cf4e1ba803f95d486e983a76e114`

## Source-level difference

For an absolute correction, A's `ClientPacketListener.handleMovePlayer` resolves the coordinates, writes the player position and velocity, then calls `Entity.absMoveTo`. That helper clamps X and Z to `[-3.0E7, 3.0E7]` before setting position; Y is not clamped. B writes position through `Entity.setPos` and does not call `absMoveTo` from this handler, so finite packet X/Z values outside that interval remain outside the clamp. `Entity.setPos` updates the position and bounding box but applies no coordinate clamp.

The paired packet codec stores and writes X/Y/Z as doubles without clamping them. The client handler therefore applies the coordinate behavior described above to the packet values it receives. The server-side `ServerGamePacketListenerImpl.teleport` helper constructs a correction packet from its coordinate arguments, but that helper alone does not establish that a vanilla producer can supply an out-of-range value. This is separate from F-005's angle normalization and the interpolation-history disposition.

## Reachability and dependencies

An external/custom server can send a correction packet with the stated coordinate; the packet codec preserves its double value, and the client receives it in `ClientPacketListener.handleMovePlayer`, which updates the local player's position, velocity and bounding box, acknowledges the teleport, and sends a movement packet using the resulting player position. The changed coordinate is therefore direct player movement state. Later movement and collision queries read that current position and bounding box. This finding does not claim that vanilla gameplay produces such a packet or how often an external/custom server supplies one.

The paired vanilla `/teleport` command path is checked negative evidence for reachability: `TeleportCommand.performTeleport` constructs a `BlockPos` and rejects it with `INVALID_POSITION` when `Level.isInSpawnableBounds` is false, before the player connection teleport call. `Level.isInWorldBoundsHorizontal` requires both X and Z block coordinates to be at least `-30000000` and strictly less than `30000000`. Thus ordinary vanilla `/teleport` does not establish a producer for finite X/Z coordinates beyond the client clamp. A/B `TeleportCommand.java` are byte-identical, SHA-256 `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a`; A `Level.java` SHA-256 `2ff6b7ab66c73f0fa41136ab83fb8076742c6bde7c3af10dd6f3170d6d536539`; B `Level.java` SHA-256 `df465bcb6eb6ceaeb423485070e00737102abb3da064e44c440f69f3aede61c1`.

## Consequence and uncertainty

Source-proven: when the client receives the stated external/custom-server correction, A clamps the corrected client X/Z position while B retains the packet value. The position and bounding box consumed by subsequent movement differ at the correction boundary. No vanilla producer, realized trajectory, persistent client/server divergence, or runtime reproduction is claimed; later server handling may issue another correction.

## Handoff

Independent delta: B's client player correction path bypasses the existing entity X/Z coordinate clamp for a received external/custom-server correction. Related finding: F-005 covers the separate correction-angle path. Applicability requires an absolute finite X or Z coordinate outside the clamp range. Vanilla `/teleport` is guarded against positions outside the spawnable bounds, so this finding does not claim vanilla producer reachability. The release boundary is unresolved within 1.19.2–1.19.3. The original snapshot was rejected for its unsupported vanilla-reachability claim; this revised report requires a fresh independent blind review. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-TICK, INV-STATE, INV-EXTERNAL; S7-player-position-correction-range, S7-player-rotation-correction. **Implementation disposition:** deferred until blind review and freeze.
