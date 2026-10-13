# Blind source snapshot review — 2026-10-08

Reviewer branch: feat/blind-source-snapshot-review-2026-10-08

Review mode: exact vanilla-source snapshots only. No mod implementation, implementation review, Minecraft Wiki, MCPK Wiki, or wiki-audit report was inspected. I hashed the cited source files individually and checked them against their exact source-manifest entries; readiness records and artifact-manifest identities match. I did not rehash unrelated source-tree contents.

## Decision 1 — ACCEPT

- Finding: WORLD-03; immutable snapshot commit aa66894e64733ee729bf7176e08232d73b3bc03f.
- Path: workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-03-end-portal-frame-player-ejection.md
- Finding-file SHA-256: f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d
- Source roots: ready/1.8.9/ornithe-feather and ready/1.9.4/ornithe-feather. Readiness markers resolve to the exact requested versions; marker SHA-256 values are FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95 and 3D0A810D9F3A93233D880CA07FFC806EA232230D33197197AD50BA69181171C6.
- A source-manifest SHA-256: 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004; artifact-manifest SHA-256: da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446.
- B source-manifest SHA-256: c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19; artifact-manifest SHA-256: 9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77.

Independently verified source identities (each file hash matched its source manifest):

- A LocalClientPlayerEntity.java 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053; Block.java ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528; EndPortalFrameBlock.java c2c876aefe34d001ff0e3eebddd0df01ee85b0bf499e205eb1eb95ec44857b9e; Material.java 017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2; LivingEntity.java 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e.
- B LocalClientPlayerEntity.java 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d; Block.java e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6; EndPortalFrameBlock.java 1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb; Material.java f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19; StateDefinition.java 10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493; LivingEntity.java bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5.

Source result and scope:

- A Block.isSolid() requires solid-blocking material, isCube(), and not isSignalSource(). EndPortalFrame uses Material.STONE, inherits the true cube and false signal-source defaults, and its analog-signal-source override is a distinct predicate. Its A solidity is true.
- B BlockState.isSolid() delegates through StateDefinition to Block.isSolid(state). EndPortalFrame isCube(state) returns false; state signal-source is false and material remains STONE. Its B solidity is false.
- LivingEntity.tick() calls this.mobTick() on both sides. The local-player override makes four body probes in mobTick(); pushAwayFrom() skips noClip players, samples each probe position, then checks solidity at that position and above it. For a probe on the frame with nonsolid space above, A enters the escape search and B skips it. A survivable horizontal escape neighbor reaches the direct velocityX/velocityZ write of ±0.1F. This is reachable player motion; the source proves the branch and possible write, not a final trajectory.
- The earlier piston candidate is correctly withdrawn: A PistonBaseBlock.isCube() is false (lines 223-225; SHA-256 3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929); B isCube(state) is false (lines 216-218; SHA-256 4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36). Both are non-solid through the relevant cube gate.
- Finding-specific dependency closure is sufficient: player probes/call path, helper and velocity consumer, EndPortalFrame registration/material, solidity formula, and the material/cube/signal producers are paired. The broad collision-provider inventory and pair discovery remain open.

Acceptance is limited to the End Portal Frame gate and the stated probe, clearance and escape-neighbor conditions. No first-changed-release claim or runtime outcome is established.

## Decision 2 — ACCEPT

- Finding: F-ELYTRA-FLIGHT-TOGGLE-ORDER; immutable finding snapshot commit 105ec98f7b092a8b68e3d93a9739a720fe10e578. The event-05 index context is updated on the source-pair branch through 16134a5a25a395521baabb9e9c40fb247ecb81a3.
- Path: workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-FLIGHT-TOGGLE-ORDER.md
- Finding-file SHA-256: b6668fc03b614b70bed323f73ded8e816e919d9f3d34112b162726501505b403
- Source roots: ready/1.14.4/mojmap and ready/1.15.2/mojmap. Readiness markers resolve to the exact requested versions; marker SHA-256 values are ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043 and 64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0.
- A source-manifest SHA-256: af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b; artifact-manifest SHA-256: 308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970.
- B source-manifest SHA-256: cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7; artifact-manifest SHA-256: 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406.

Independently verified source identities (each file hash matched its source manifest):

- A LocalPlayer.java 0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f.
- B LocalPlayer.java 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; Player.java 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793.

Source result and scope:

- In A LocalPlayer.aiStep(), a rising jump edge can toggle mayfly flight off, after which the same tick's Elytra branch independently checks descending motion, current flight state and an enabled chest Elytra before sending START_FALL_FLYING.
- In B, the ability-flight toggle sets bl8 before the later Elytra condition, which requires !bl8; that tick skips the attempt. B Player.tryToStartFallFlying() independently requires airborne, not already fall-flying, and not in water; startFallFlying() writes shared flag 7.
- The reachable common scenario is bounded to mayfly ability with flight currently on and not forced by always-flying mode; no swimming; a fresh jump edge with the flight-toggle double-tap timer eligible and auto-jump not supplying the jump; airborne and descending; not a passenger or on a ladder; not already fall-flying or in water; and an enabled Elytra in the chest slot. Under these guards A sends the request in that aiStep and B suppresses it due to bl8. No server acceptance or glide trajectory is claimed.
- A's decompile run also produced Feather output, but this finding cites only the A Mojmap tree; B is Mojmap. The revised Feather publication is not an input. This review makes no claim that revised Feather output is equivalent to the original derived artifact; its recorded equivalence limitation remains unchanged.

Acceptance is limited to same-tick local command-request arbitration. The pair run remains partial; this is not a full-pair freeze, implementation disposition, precise first-release boundary, or runtime validation.

## Handoff boundary

These decisions accept only the two cited immutable finding snapshots for their bounded source claims. They do not close either version pair. Implementation and wiki-audit material were not inspected. No code, tests, client, server, TAS, or Docker action was performed.
