# Independent audit verdict: correction applicability early sample

Verdict: **ACCEPT**, bounded strictly to the reported samples 1.8.9, 1.9.4, and 1.12.2.

## Immutable input identity

- Report: `build/mcpk-20261116/vd-full-checklist/correction-applicability-early/report.md`
- Verified report SHA-256: `15faa5c10f953a20f15915f5a75421b63b98d0d37069a99a81640c5e18b7ae90`
- Exact local source sets: `decompiled_minecraft/{1.8.9,1.9.4,1.12.2}/ornithe-feather`

## Audit basis

For each release, the ready marker's source-manifest digest matches the SHA-256 of the local `ornithe-feather.sources.sha256`; the verification marker records the reported version/namespace, `verified-identical-source-and-input-artifacts`, and zero source-file differences. The verification markers separately disclose one regenerated derived remapped JAR per release, and the report preserves that limitation. Provenance and ready markers support the cited Feather Gen2 build.2 mapping artifacts, client hashes, and recorded toolchain.

The report digest and each receiver hash match the assigned values. For all three releases, byte hashes of `ClientPlayNetworkHandler.java`, `PlayerMoveS2CPacket.java`, `Entity.java`, and `PacketUtils.java` match their entries in the local source manifest. The local packet, handler, and helper bodies match the cited ranges.

## Claim audit

- Each `handlePlayerMove(PlayerMoveS2CPacket)` begins with `PacketUtils.ensureOnSameThread`, obtains `minecraft.player`, and has no player-null, riding, vehicle, or passenger predicate in the receiver body. Packet dispatch calls this handler, and `NetworkProtocol` registers this packet as clientbound in all three samples.
- Each receiver processes relative/absolute X, Y, and Z independently in that order. A relative coordinate adds the player's current coordinate and leaves that velocity component unwritten in the receiver; an absolute coordinate zeros only the matching component before the position helper call. Angle-relative flags affect yaw/pitch only.
- In each sample, the receiver calls `updatePositionAndAngles` after axis/angle resolution. The cited helper ranges support the reported position/last-position and angle/last-angle assignments, applicable X/Z and pitch clamps, and final `setPosition`/`setRotation` calls; the helper does not write velocity. Response packet ordering and the later `!started` initialization block match the reported ranges.
- `PacketUtils.ensureOnSameThread` schedules packet handling on the runner and throws its sentinel when invoked off-thread; on the runner thread it returns. This supports the report's caller-thread gate statement.

No material correction is required for the stated bounded sample. This verdict does not establish continuity or behavior for unsampled releases, eligibility outside the inspected receiver, server behavior, or runtime trajectories.
