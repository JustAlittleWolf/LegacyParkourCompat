# F-EDGE-BACKOFF-TOGGLE: Toggle Crouch can keep edge backoff active after Shift release

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-COLLISION, INV-STATE; S-EDGE-BACKOFF
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: local player, grounded SELF/PLAYER movement, B toggleCrouch=true, after a Shift press and release
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

The source trees are the original-verified Mojmap outputs recorded in run.md under the A and B artifact manifests. Their readiness-marker SHA-256 values are A ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043 and B 64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0. Their source-manifest hashes are A af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b and B cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7. Source preparation provenance limitations are recorded as D-001 in run.md.

- A ready/1.14.4/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3) lines 446-466 calls applySneaking; Entity#applySneaking(Vec3,MoverType) lines 575-623 requires this instanceof Player, SELF or PLAYER mover type, onGround, and isSneaking(). The paired X, Z, and diagonal 0.05 collision-probe loops are in the applySneaking body. SHA-256 31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7.
- B ready/1.15.2/mojmap/net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3) lines 450-470 calls maybeBackOffFromEdge; Player#maybeBackOffFromEdge(Vec3,MoverType) lines 1002-1050 has the corresponding mover-type, grounded and surface guard and the same X, Z, and diagonal probes in the maybeBackOffFromEdge body. SHA-256 Entity 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e; Player 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793.
- A ready/1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean) lines 13-25 writes sneakKeyDown = options.keySneak.isDown(). LocalPlayer#isSneaking() and isTryingToSneak() lines 584-589 read that field. SHA-256 KeyboardInput 33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a; LocalPlayer 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
- B ready/1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean) lines 13-25 writes shiftKeyDown = options.keyShift.isDown(). LocalPlayer#isShiftKeyDown() lines 591-592 reads that field; Player#isStayingOnGroundSurface() lines 294-295 delegates to isShiftKeyDown(). SHA-256 KeyboardInput 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; LocalPlayer 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
- A ready/1.14.4/mojmap/net/minecraft/client/Options.java line 117 binds keySneak to "key.sneak" with default code 340 and a plain KeyMapping; A KeyMapping#set(InputConstants.Key,boolean) lines 40-44 writes keyMapping.isDown = bl. B Options.java lines 113, 121 binds keyShift to the same "key.sneak" and code 340 as a ToggleKeyMapping controlled by toggleCrouch; the boolean is declared without an initializer at line 113 (false by Java default) and is loaded/saved at lines 338-339 and 602. B KeyMapping#set lines 40-45 dispatches to keyMapping.setDown(bl); ToggleKeyMapping#setDown(boolean) lines 15-23 toggles on a press and ignores release while needsToggle is true. Hashes: A Options 55bf846ecabe4853b9d9417fa18ee927d5cdf8ff2aaba1fe2726d1cfcbae9390, KeyMapping 39726c02ac77713483b2a7cf0b751cafc0e4270084dfec1547658e8d9a51881d; B Options 5bf3f2ef925c282c8ac9c6df57b362013b295b6946b006ee72a008495c485634, KeyMapping f68badd84af608d37b419430f6875a1b4689f00c7adff317b58e01b825659649, ToggleKeyMapping 62a498fd0aa92f301b97dda87c8f58f1940afcdd3a45b2177b64577485127508.
- Both KeyboardHandler#keyPress(...) source paths call KeyMapping.set(key,true) on press and KeyMapping.set(key,false) on release (A lines 371-374, B lines 370-373); hashes A b51fbaf7f6ac3884aaf109cb689c320238e5d3e9c1703f2761379aaeb868d0d4, B e708fb96303d6a9f5a867ef2151b87e1a00479f5d6b42ec784105ea9f5a4a85b.
- A Entity#isSneaking() lines 1812-1814 and B Entity#isShiftKeyDown() lines 1783-1785 both read shared flag 1 for non-local entities; A LocalPlayer overrides the former through local input, and B LocalPlayer overrides the latter through local input. These bases preserve the corresponding synchronized player-state predicate. Hashes: A Entity above; B Entity above.

## Source-level difference

Under the default hold setting, B's ToggleKeyMapping delegates both press and release values to KeyMapping.setDown, and the edge-backoff guard matches A's sneak-state predicate for the ordinary grounded player path. With B toggleCrouch=true, a press flips the key state on; the release event passes false, but ToggleKeyMapping#setDown does not call super.setDown(false). KeyboardInput.tick then copies the still-true keyShift.isDown() into shiftKeyDown; the LocalPlayer accessor and Player guard pass true, so the next grounded SELF/PLAYER movement continues through backoff. In A, the release directly sets keySneak.isDown false, which the next input tick copies to sneakKeyDown; the old guard then skips backoff. A's source tree has no toggleCrouch or ToggleKeyMapping implementation, so there is no corresponding toggle setting in this pair's A path.

## Reachability and dependencies

Local KeyboardHandler press/release -> KeyMapping.set -> configured "key.sneak" mapping -> KeyboardInput.tick -> LocalPlayer input accessor -> edge-backoff guard -> collision probes and Entity.collide() input. The onGround and mover-type gates still apply on both sides. The paired loops themselves have matching order, thresholds, query offsets, and vector reconstruction; the changed result comes from B's optional persistent input state. The evidence establishes the local-player client path only; it does not infer server-side motion from a client setting.

## Consequence and uncertainty

The sources prove that the backoff guard stays enabled after Shift release in B when toggle crouch is active, while A's guard becomes false after release. This changes the vector supplied to collision handling when the collision probes find unsupported space. No resulting position or velocity has been measured. The default B option is false, so default hold behavior follows the matching guard path. The earliest release introducing this endpoint difference is unknown; no intermediate version was inspected.

## Handoff

Preserve the A-era hold-state gate for the selected historical profile when evaluating the local grounded edge-backoff path. Independent source review and acceptance are pending; this finding is not an implementation snapshot and does not close pair discovery.
