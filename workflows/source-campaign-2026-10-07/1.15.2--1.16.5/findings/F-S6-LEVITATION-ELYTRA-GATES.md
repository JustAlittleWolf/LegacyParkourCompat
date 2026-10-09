# F-S6-LEVITATION-ELYTRA-GATES: Levitation blocks elytra glide start and continuation in B

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: fall-flying state eligibility; S6-ELYTRA-LEVITATION-GATES
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: run.md Artifact manifest A — 1.15.2, Mojmap
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`
- Evidence artifact SHA-256: source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap.sources.sha256` — `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`
- Original artifact-manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/artifacts.sha256` — `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`
- Original derived-artifact availability and SHA-256 or expected hash: Mojmap client jar available; `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b`
- Source/raw-input hash relation and verification reference: all cited Java source hashes match their entries in the A source manifest; ready JSON SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` verified.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; no revised evidence used.
- Provenance limitations: ready record and file manifests are intact; provenance records `mappingArtifacts=null`, and the retained success log is a summary excerpt rather than the full Gradle stream. No damage was reported for the relevant source bodies.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep`, 715-719; `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
  - `net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, 1578-1588; `startFallFlying`, 1590-1592; `updatePlayerPose`, 357-384; `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
  - `net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying`, 2299-2318; fall-flying `travel` branch, 1842-1884; `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
  - `net/minecraft/world/entity/Entity.java`, `getSharedFlag` / `setSharedFlag`, 1872-1882; `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
  - `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, `handlePlayerCommand` START_FALL_FLYING case, 1137-1141; `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c`.
  - `net/minecraft/network/syncher/SynchedEntityData.java`, `set` / `packDirty` / `assignValues`, 120-168 and 225-238; `d804085da3d5713c570694319285bf4a39937d393e6b72fedbb13b1db79b53f8`.
  - `net/minecraft/server/level/ServerEntity.java`, `sendDirtyEntityData`, 260-264; `dc5892e3b2d7c6a141f235053c414284a6c77876981db3ba1ba8419af08d5527`.
  - `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleSetEntityData`, 567-573; `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30`.

### B artifact identity

- Evidence artifact record ID: run.md Artifact manifest B — 1.16.5, Mojmap
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`
- Evidence artifact SHA-256: source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap.sources.sha256` — `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`
- Original artifact-manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/artifacts.sha256` — `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`
- Original derived-artifact availability and SHA-256 or expected hash: Mojmap client jar available; `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940`
- Source/raw-input hash relation and verification reference: all cited Java source hashes match their entries in the B source manifest; ready JSON SHA-256 `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` verified.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; no revised evidence used.
- Provenance limitations: ready record and file manifests are intact; provenance records `mappingArtifacts=null`, and the retained success log is a summary excerpt rather than the full Gradle stream. No damage was reported for the relevant source bodies.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep`, 725-729; `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
  - `net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, 1520-1530; `startFallFlying`, 1532-1534; `updatePlayerPose`, 350-377; `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
  - `net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying`, 2478-2497; fall-flying `travel` branch, 1979-2022; `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
  - `net/minecraft/world/entity/Entity.java`, `getSharedFlag` / `setSharedFlag`, 1898-1908; `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
  - `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, `handlePlayerCommand` START_FALL_FLYING case, 1270-1274; `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80`.
  - `net/minecraft/network/syncher/SynchedEntityData.java`, `set` / `packDirty` / `assignValues`, 120-168 and 225-238; `d804085da3d5713c570694319285bf4a39937d393e6b72fedbb13b1db79b53f8`.
  - `net/minecraft/server/level/ServerEntity.java`, `sendDirtyEntityData`, 264-268; `be366b94da3225bc71859138ab702b1f3b2cb4322d241e67737693487c3c81de`.
  - `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleSetEntityData`, 576-583; `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`.

## Source-level difference

With an airborne player who is not already fall-flying or in water and has an enabled Elytra, A's `tryToStartFallFlying` accepts the start even when Levitation is active. B adds `!hasEffect(MobEffects.LEVITATION)` to that gate, so the same state rejects the start. B also adds the same Levitation guard to `LivingEntity.updateFallFlying`: if the shared fall-flying bit is already set and the existing ground, passenger and Elytra checks pass, A retains the bit while B clears it on the server. The guards and call order are exact-source differences; the first changed release is unknown within the interval.

The reachable client jump path calls the start method and sends `START_FALL_FLYING` only when the start gate succeeds. The server packet handler calls the same gate again and calls `stopFallFlying` on failure. `startFallFlying` sets shared-flag bit 7; `Entity.setSharedFlag` updates that bit in the synchronized flags byte. `SynchedEntityData.set` marks changed values dirty, the tracked server entity packages dirty data, and the client listener assigns it. These paths match in A and B, so the B-only effect guards are the source of the eligibility difference.

## Reachability and dependencies

Client jump input -> LocalPlayer Elytra/jump gates -> Player.tryToStartFallFlying -> Player.startFallFlying / bit-7 write -> serverbound START_FALL_FLYING -> server tryToStartFallFlying recheck -> server updateFallFlying before travel -> tracked entity-data update to client. The active Levitation flag is a supplied effect-state input; effect production is not included. `Player.updatePlayerPose` reads fall-flying state to select the fall-flying pose in both versions, and `LivingEntity.travel` dispatches to the paired fall-flying equations already covered by S2-POSE-EPSILON and S3-FALL-FLYING.

## Consequence and uncertainty

Source proves that B refuses to start or maintain the fall-flying state while Levitation is active and that the shared flag is synchronized through the ordinary entity-data path. It also proves that the subsequent pose and travel consumers read that state. No position or velocity trajectory was observed or predicted here. Runtime validation was not performed; the independent finding review and full-pair audit are pending.

## Handoff

Independent delta description: Levitation blocks fall-flying start and continuation in B. Related finding IDs: none. Applicability: direct player movement with active Levitation and a functional Elytra. Release boundary remains unknown within (1.15.2, 1.16.5]. No implementation snapshot or handoff has been submitted.
