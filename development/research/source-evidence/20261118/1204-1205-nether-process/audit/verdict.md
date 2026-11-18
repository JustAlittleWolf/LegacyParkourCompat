# Immutable adjudication: ordinary Nether portal process, 1.20.4 → 1.20.5

## Verdict

**ACCEPTED — bounded no-difference finding for the assigned ordinary, unmounted, non-spectator local-player Nether portal slice.** The ordered client contact/pending-use path and its server-owned dwell/cooldown authority are paired and source-supported. No portal-specific movement behavior change is present in this slice. This verdict does not freeze the full release pair or establish parity for other movement areas.

The frozen blind report's earlier 1.20.4 mapping-pin limitation is resolved by the now-authorized version-root `artifacts.sha256` sidecar: its digest matches the ready marker, and its `client_mappings.txt` entry matches the mapping SHA in the author document. One scope clarification should be carried into the reconciled coverage record: cite the non-spectator/no-physics gate explicitly. `Player.tick` sets `noPhysics = isSpectator`, while `Entity.move` takes a position-only no-physics branch that bypasses `tryCheckInsideBlocks`; this verdict is specifically for the ordinary non-spectator route already named by the blind report.

## Immutable inputs

| Artifact | Raw SHA-256 | Review use |
|---|---|---|
| Frozen blind audit `audit/blind.md` | `0f923672ee090d01bbd8da876b33613bb524e4c45654d84c65256da79b2e2b67` | Retraced ordered coverage and operation verdicts; file was not changed. |
| Author coverage `author/coverage.md` | `da97215a9d4a68f9525b2365b98129fb40b87a8c57922f7cbde17a39f6587c10` | Hash verified before reading; compared author claims, pins and exclusions. |

## Independent identity checks

I computed each version-root `artifacts.sha256` digest and confirmed it equals the `artifactManifestSha256` in `mojmap.ready.json`. I then matched the `client_mappings.txt` row in each sidecar to the mapping hash in the author document. The source-manifest digests also match the respective ready markers, and the cited Java file hashes in the frozen audit match the source manifests.

| Version | `artifacts.sha256` SHA-256 (computed = ready marker) | `client_mappings.txt` row SHA-256 | Client artifact SHA-256 from ready marker | Source manifest SHA-256 |
|---|---|---|---|---|
| 1.20.4 | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5 | `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` | `9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |

The 1.20.5 provenance also records the official mapping file SHA directly. The 1.20.4 provenance does not, but the matching ready-pinned artifact sidecar supplies that exact official mapping input identity, so the original blind-audit limitation no longer blocks source acceptance.

## Coverage adjudication

- **Contact dispatch and eligibility:** Accepted. `Entity.move`'s ordinary collision branch reaches `tryCheckInsideBlocks`; the AABB scan dispatches through `BlockBehaviour` to `NetherPortalBlock.entityInside`, which gates on `canChangeDimensions`. The paired methods and the pending portal callback body hashes in the frozen audit match. The 1.20.5 `public` → `protected` override visibility change does not change dispatch or behavior.
- **Tick ordering and pending local callback:** Accepted. The local handler runs near the head of `LocalPlayer.aiStep`, before input/travel. A contact bit written during movement is read on the next eligible local `aiStep`. The client handler's portal-state, effect and cooldown body hash matches across the pair and writes no player position or velocity.
- **Dwell and authority:** Accepted as a dependency boundary. `Entity.baseTick` invokes `handleNetherPortal` before `aiStep`; that method processes dwell only for `ServerLevel`. `ServerPlayer.doTick` enters the inherited player/entity tick path. The portal counter, wait-rule selection and dimension-transfer gates match; the server decides transfer and the local client does not run historical server physics.
- **Cooldown and wait inputs:** Accepted. `handleInsidePortal`, `processPortalCooldown`, `Player.getPortalWaitTime` and the 10-tick player delay override have matching normalized method-body hashes. Contact refresh and once-per-applicable-tick decrement order are preserved.
- **First movement tick:** Accepted no-difference for the portal-specific response. The only client-local portal operation before movement updates portal effect state and cooldown; no portal-specific position, velocity or box write is introduced. The eventual dimension transfer is server-authoritative. This does not assert unchanged unrelated movement code in `aiStep` or full-pair movement parity.

### Gate clarification for reconciliation

For precise reachability, add the source citation `Player.java:224–226` / `Player.java:244–246` (`noPhysics = isSpectator()`) and `Entity.move`'s opening branch at `Entity.java:596–600` / `:597–601`. Spectator movement takes the direct `setPos` branch and skips block-contact callbacks. The accepted scope excludes spectators, so this is a documentation clarification rather than a behavior finding or a verdict blocker.

## Omissions retained

End portals/gateways, portal frame validation/construction, portal shape or destination-search math, rendering/UI and audio behavior beyond the local handler gate, generic movement behavior unrelated to this portal-triggered slice, and all other pair inventories remain outside this adjudication. No pair-wide freeze is claimed.
