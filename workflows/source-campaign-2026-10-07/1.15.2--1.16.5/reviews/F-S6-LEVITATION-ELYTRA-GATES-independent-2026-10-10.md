# Independent review: F-S6-LEVITATION-ELYTRA-GATES

- Reviewer: independent source reviewer `/root/review_elytra_levitation`; 2026-10-10.
- Verdict: **accepted** for the exact endpoint source behavior only.
- Implementation handoff: **blocked**; first changed release is unknown within (1.15.2, 1.16.5]. No intermediate release was inspected.
- Pair discovery: **partial**, no pair freeze or full-pair audit implied.
- Runtime validation: not performed.
- Author snapshot: `16d73824f288c22a5b17b639d1c5b83682c92f50`.
- Finding: `findings/F-S6-LEVITATION-ELYTRA-GATES.md`; Git blob `74c685f57103cb7b73d5bbe2de0e51441a0505a9`; exact blob/file SHA-256 `1c0e43c5597aa1b63219e54c1fdea135b0b689c91d7f3639ae3da262c86c7905`.
- Run: `run.md`; Git blob `7c4fddd5d6b62045abcdcc43eccb24b1bc30254b`; exact blob/file SHA-256 `cda26c9dd9c852fd88229745047126cbd50a9d113af1ebd3f1aecca3066c2536`.

The reviewer did not author the finding and inspected only source-safe guidance, this immutable source report and published vanilla artifacts. No mod implementation, wiki or wiki-audit material, mixed coordination reports or main diffs were inspected. The review checkout starts at the author commit and owns this review file only. Canonical source publications were consumed read-only; no decompilation, build, test, game, server or push occurred.

## Publication and raw-byte verification

All source paths below are relative to `build/movement-campaign-2026-10-07/ready/<version>/mojmap` at the repository root, not the review checkout. Both readiness records resolve exactly 1.15.2 / 1.16.5 in Mojmap, Vineflower, Java 25. Direct SHA-256 checks matched readiness, source manifests, artifact manifests, every finding-cited Java file and the additional dependency files below. Direct checks of the retained original client jars, mapped jars and official mapping files matched the artifact manifest. No revised publication or original-equivalence assumption is needed.

| Binding | A: 1.15.2 SHA-256 | B: 1.16.5 SHA-256 |
|---|---|---|
| mojmap.ready.json | 64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0 | e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee |
| mojmap.sources.sha256 | cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7 | 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b |
| artifacts.sha256 | 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406 | f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c |
| artifacts/<version>/client.jar | 4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c | 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263 |
| artifacts/<version>/client-mojmap.jar | c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b | 7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940 |
| artifacts/<version>/client_mappings.txt | 65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777 | 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c |

The three raw-input rows reside under `build/movement-campaign-2026-10-07/artifacts`, rather than the ready source root. Preserve the finding's stated limitations: mappingArtifacts=null and success logs retained as summary excerpts rather than complete Gradle streams. No relevant source-body damage was observed. Those limitations do not overturn the bounded gate bodies reviewed here.

## Independent behavior and dependency closure

1. **Start gate:** A Player.tryToStartFallFlying 1578-1588 versus B 1520-1530. A checks airborne, not already gliding, outside water, chest Elytra and enabled durability. B inserts `!hasEffect(MobEffects.LEVITATION)` at the end of the outer conjunction, before equipment lookup. A starts under supplied active Levitation; B returns false. Player.startFallFlying A 1590-1592 / B 1532-1534 sets bit 7. stopFallFlying A 1594-1597 / B 1536-1539 preserves the true-then-false sequence.
2. **Client/server reachability:** LocalPlayer.aiStep saves prior jumping A 632 / B 634, samples input A 635 / B 641 and reaches the start caller A 715-719 / B 725-729 before super.aiStep A 778 / B 788. Concrete common precondition: fresh jump press, no ability-flight toggle that tick, abilities.flying=false, no passenger/climbable, airborne outside water, enabled chest Elytra, supplied Levitation. The successful local gate sends START_FALL_FLYING. ServerGamePacketListenerImpl rechecks the same gate at A 1137-1141 / B 1270-1274 and stops on failure. This is reachable player behavior, not an inference from a dead helper. Climbable-provider breadth is outside this witness; ordinary open air satisfies the enclosing negative gate in both versions.
3. **Continuation:** LivingEntity.updateFallFlying A 2299-2318 / B 2478-2497 starts from shared bit 7. B adds the same negative Levitation predicate after the airborne/non-passenger checks. With bit=true and common remaining checks passing, A retains true; B sets its local boolean false. Only the server executes the final shared-flag write, A 2315-2317 / B 2494-2496. The client updater alone does not clear the shared bit under Levitation. The update executes before travel at A 2282-2287 / B 2454-2459. Equipment durability damage is gated server-side every twentieth fall-fly tick in both; production of damage/effects is outside this bounded eligibility claim.
4. **Flag synchronization:** Entity shared-byte default A 206 / B 203 is zero; getSharedFlag/setSharedFlag A 1872-1882 / B 1898-1908 preserve the bit masks. LivingEntity.isFallFlying A 2690-2692 / B 2891-2893 reads bit 7. SynchedEntityData.set 120-128 marks changes dirty; packDirty 147-168 copies dirty entries; assignValues/assignValue 225-255 assigns received values. These source files are byte-identical across endpoints. ServerEntity dirty-data dispatch A 260-264 / B 264-268 constructs ClientboundSetEntityDataPacket with false; its constructor 16-24 packages packDirty, write/read 26-35 serialize/unpack, handle 38-40 dispatches. ServerEntity.broadcastAndSend A 287-292 / B 290-295 also sends to the tracked ServerPlayer itself. ClientPacketListener A 567-573 / B 576-582 resolves that entity and assigns its data. This closes the local-player receive route; no packet latency or trajectory claim is made.
5. **Supplied inputs and consumers:** LivingEntity.hasEffect A 720-722 / B 828-830 reads activeEffects.containsKey with no amplifier/duration gate. MobEffects Levitation A 80 / B 71 registers the same ID 25 and name. Player.getItemBySlot A 1759-1767 / B 1701-1709 reads armor by slot index; EquipmentSlot CHEST is ARMOR index 2 in both. ElytraItem.isFlyEnabled A 19-21 / B 17-19 uses the same damage < maxDamage - 1 predicate. Existing effect state and equipped stack are supplied vanilla inputs; effect production and item damage production are not emulated by this finding. Player.updatePlayerPose A 357-384 / B 350-377 consumes the flag as first pose choice subject to canEnterPose checks. LivingEntity.travel consumes it at A 1842 / B 1979 under the respective surrounding fluid and effective-AI/local-control guards. The claim is that these consumers read changed state, not that pose is unconditional or that trajectories were measured. Other travel/shape/effect inventories stay open in the pair.

The finding-specific endpoint state/gate/caller/synchronization dependencies are closed to the supplied-input boundary above. The release-boundary dependency is explicitly open. No missing endpoint route requiring a finding revision was found. Acceptance covers both stated gate differences and the carefully bounded affected-state consumers; it does not accept broad provider coverage, full tick coverage or implementation feasibility.

## Next action

Integrate this review report only and append the immutable endpoint acceptance event through the source-report owner. Keep handoff blocked until independent exact intermediate-release evidence establishes the required first changed boundary for each gate. Preserve pair partial status and all other open discovery slices; do not rediscover or revise the accepted endpoint bytes for a status-only update.

## Cited Java file bindings

Every row was independently rehashed from the published tree; the original eight rows also match the exact hashes in the immutable finding. Added dependency rows are review evidence only.

| Relative source file | A SHA-256 | B SHA-256 |
|---|---|---|
| net/minecraft/client/player/LocalPlayer.java | 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd | 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b |
| net/minecraft/world/entity/player/Player.java | 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793 | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 |
| net/minecraft/world/entity/LivingEntity.java | 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54 | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 |
| net/minecraft/world/entity/Entity.java | 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| net/minecraft/server/network/ServerGamePacketListenerImpl.java | 3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c | 3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80 |
| net/minecraft/network/syncher/SynchedEntityData.java | d804085da3d5713c570694319285bf4a39937d393e6b72fedbb13b1db79b53f8 | d804085da3d5713c570694319285bf4a39937d393e6b72fedbb13b1db79b53f8 |
| net/minecraft/server/level/ServerEntity.java | dc5892e3b2d7c6a141f235053c414284a6c77876981db3ba1ba8419af08d5527 | be366b94da3225bc71859138ab702b1f3b2cb4322d241e67737693487c3c81de |
| net/minecraft/client/multiplayer/ClientPacketListener.java | d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30 | a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e |
| net/minecraft/world/item/ElytraItem.java | 701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43 | d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d |
| net/minecraft/world/effect/MobEffects.java | 472b25ede6bed3ad3ed1090db3310cea9708b637f111981bcf083f492c030412 | 64b3b592a48eac1016c3a307b85c3e201689662dea68c80b6d642cffa35fd289 |
| net/minecraft/network/protocol/game/ClientboundSetEntityDataPacket.java | 30dd768389254f32ec5e22731a6ad0d8e320bd900ee59e44415243a4c5be9529 | 30dd768389254f32ec5e22731a6ad0d8e320bd900ee59e44415243a4c5be9529 |
| net/minecraft/world/entity/EquipmentSlot.java | 7e197ce285965458f269a9f9ecc36f83c35ec151bf1c8735216fef2ea30384f4 | 7e197ce285965458f269a9f9ecc36f83c35ec151bf1c8735216fef2ea30384f4 |
