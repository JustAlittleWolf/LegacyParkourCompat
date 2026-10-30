# Independent technical review: respawn movement flags

**Verdict: ACCEPT, bounded to the accepted finding.**

- Candidate: `.native-candidates/respawn-flags44`, commit `dd7f35b3479e5793492debfa52b224feb99b34cc` (`Restore historical movement flags on respawn`).
- Finding: `development/research/findings/1.19.2--1.19.3/respawn-player-movement-flags-copy.md`.
- Source acceptance: `development/research/source-evidence/20261030/checkpoint44-respawn/respawn_review/respawn-player-movement-flags-copy.verdict-r2.md` (ACCEPT).
- Review scope: exact operation fidelity, mask and mutation behavior, packet keep-bit gate, byte capture/replacement lifecycle, client-thread dispatch, hook resolution/registration, and native fallback. No code, build, test, or game changes were made.

## Findings

1. `RespawnMovementFlags.restore` merges exactly bits 3, 4, and 7 from the old shared-flags byte into the replacement byte. The complement mask preserves every other new-player bit. It uses the raw accessor and `SynchedEntityData.set`, not `setSprinting` or attribute assignment. In the 26.2 target source, the shared-flags accessor is not a `LivingEntity` update trigger; `LivingEntity.onSyncedDataUpdated` handles sleeping and living-entity flags, while `Entity`'s accessor-specific update handles pose only. This matches the historical `assignValues` path for the movement byte without adding sprint-attribute side effects.

2. The mixin captures the old player byte immediately after `PacketUtils.ensureRunningOnSameThread` and reads the new byte at `handleRespawn` tail after vanilla has installed/replaced the player and performed its normal keep-data work. The target 26.2 `javap -p -c` output confirms the injection's exact erased invocation descriptor: `ensureRunningOnSameThread(Packet, PacketListener, PacketProcessor)`. `ensureRunningOnSameThread` schedules and throws on a different thread, so the after-invocation capture is skipped in that invocation; the scheduled replay captures on the client thread. No early return exists in target `handleRespawn`; the per-listener pending boolean is consumed and cleared at tail. Each ordinary replay therefore brackets one old-to-new player replacement.

3. The patch gates restoration on `!packet.shouldKeep((byte) 2)`, the entity-data keep bit. When that bit is set it leaves modern vanilla's nondefault entity-data copy intact; for the three bits in scope, a set old flag is nondefault and copied, while a clear old flag matches the replacement default. With the bit clear it restores only the accepted movement-byte difference. The separate attributes bit is not consulted or modified.

4. The hook is a single `@MechanicType` `RespawnMovementFlagsHook`, implemented by a public no-argument annotated `V1_19` change and found through automatic client discovery. There is no manual catalog, second loop, or exact-profile check. Resolver direction selects the 1.19 operation for 1.19/1.19.1/1.19.2, and no operation for 1.19.3 or later, retaining native fallback. The `V1_18_2` identity change is a lower native barrier for earlier selections; this review does not claim it establishes historical equality for those profiles.

## Boundary

Acceptance is limited to the three player movement flags for a respawn whose entity-data keep bit is clear, as supported by the accepted 1.19.2/1.19.3 finding. The server-side production/frequency of keep-bit values remains unasserted. Candidate review does not establish product integration, compilation, or runtime parity; the product adapter still needs its separate final wiring review.
