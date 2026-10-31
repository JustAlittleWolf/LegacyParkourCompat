# Independent source review — relative move zero-delta coordinate retention

## Binding and inputs

- Verdict target: `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/relative-move-zero-delta-coordinate-retention.md`
- Bound draft SHA-256: `C6FABB503465DEE6121D0B77F281F839EA9F7E806C6413BB278C7C1D52DFA030`
- Cited packet report SHA-256: `957A85DBB8915B049139816ADCF467878F9F11975D3BB06734FD935085869354` (`client-packet-player-receivers/report.md`)
- Cited independent review SHA-256: `7136192863EF3E2E078914B4E5D3D90459D696B898BCC31A377CA2388CFAA16E` (`packet-teleport-independent-review/verdict.md`)
- Exact canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.1,1.16.2}\mojmap`
- Verified `ClientPacketListener.java` hashes: 1.16.1 `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d`; 1.16.2 `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9`.
- Verified `LivingEntity.java` hashes: 1.16.1 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; 1.16.2 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.

## Verdict

**REVISE this exact draft before treating the whole text as accepted.** The core 1.16.1→1.16.2 finding and its bounded player path are supported. Remove or separately source-bind the sentence that says “1.16 and 1.16.1 retain the long-accumulator route.” This review's exact source inputs establish the 1.16.1→1.16.2 change only; the cited packet report and review compare 1.15.2→1.16.5, so they do not verify the 1.16.0 assertion. The draft's final statement that this finding records the observed 1.16.1→1.16.2 cutover is supported and can remain.

## Source checks

- **Old movement-coordinate path:** 1.16.1 `ClientPacketListener.handleMoveEntity` (`:638–660`) enters the position branch only for `hasPosition()`, adds each short delta to `Entity.xp/yp/zp`, converts the complete long triplet with `ClientboundMoveEntityPacket.packetToEntity`, then calls `entity.lerpTo`. `Entity.java` declares those three fields as `long`; `setPacketCoordinates(double,double,double)` converts each coordinate with `entityToPacket`, which applies `Mth.lfloor(coordinate * 4096.0)`. Thus a zero delta leaves that axis's packet-unit accumulator and the resulting grid coordinate unchanged.
- **New per-axis path:** 1.16.2 `ClientPacketListener.handleMoveEntity` (`:628–650`) passes `entity.getPacketCoordinates()` to `updateEntityPosition`, stores the returned vector, then calls `lerpTo`. `ClientboundMoveEntityPacket.updateEntityPosition(Vec3)` (`:31–35`) returns the prior double verbatim when that axis's short is zero; otherwise it floors to packet units, adds the short, and divides by `4096.0`. `Entity.setPacketCoordinates(Vec3)` stores the vector directly (`Entity.java:239–249`). The distinction and the source boundary are verified in this exact pair.
- **Receiver routing:** In each endpoint, `handleLogin` adds `minecraft.player` to the client level under the login packet ID (`ClientPacketListener.java:396` in 1.16.1; `:391` in 1.16.2). `ClientboundMoveEntityPacket.getEntity(Level)` resolves through `level.getEntity`; `ClientLevel.getEntity` reads the ID map. `LocalPlayer` has no `lerpTo` override; its superclass chain reaches `LivingEntity.lerpTo`. `Entity.isControlledByLocalInstance()` uses the controlling passenger's local-player status, or `!level.isClientSide` if none; the inherited player `getControllingPassenger()` returns null, so the registered LocalPlayer on ClientLevel enters the handler's `!isControlledByLocalInstance()` branch.
- **Interpolation target and later gate:** `LivingEntity.lerpTo` stores `lerpX/Y/Z` and sets `lerpSteps` to the passed step count (three at this receiver). `LivingEntity.aiStep` processes the target under `lerpSteps > 0`, calculates a step toward it, decrements the counter, then calls `setPos`. This confirms the draft's deferred target and later interpolation gate; it does not establish packet/tick ordering or an eventual runtime trajectory.
- **Finite off-grid state reachability in 1.16.2:** `ClientPacketListener.handleTeleportEntity` obtains the absolute coordinates and calls `entity.setPacketCoordinates(d,e,f)` before its control predicate and `lerpTo` call (`:602–617`). The 1.16.2 entity setter retains those double coordinates in its `Vec3`. A finite absolute teleport coordinate not on the 1/4096 grid therefore seeds a finite off-grid packet-coordinate state for the subsequent relative-move receiver path. This is source-reachable as a receiver state; no ordinary server-production sequence is established. The 1.16.1 setter instead floors all three coordinates into the long packet accumulators.
- **Supporting packet report/review:** Both cited hashes match. Their findings support the general registered-local-player receiver route and the conditional zero-axis mechanism over their stated 1.15.2→1.16.5 pair. They also explicitly separate receiver reachability from packet production and next-tick timing. They do not replace checking the exact 1.16.1/1.16.2 source, which was done above.

No product/wiki/backlog changes, build, tests, launches, or decompilation were performed.

## Required citation correction

The draft's 1.16.1 `LivingEntity` line references do not match the verified canonical file. Its claimed `lerpTo` range `:2406–2413` is at `:2583–2591` in this source; its claimed later interpolation range `:2217–2226` is at `:2376–2385`. The 1.16.2 references (`lerpTo :2585–2592`, interpolation `:2378–2387`) match the canonical source. Correct these 1.16.1 references in the draft before acceptance. This is a citation error only; the method bodies support the stated queue and deferred `setPos` behavior.
