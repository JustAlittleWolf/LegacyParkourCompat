# Independent source review — client teleport near-coordinate fallback

**Verdict: REVISE.** The receiver predicate and endpoint delta are supported, including reachability for the registered local player. The draft's stated immediate position-write consequence is not source-correct: `LocalPlayer` inherits `LivingEntity.lerpTo`, which queues interpolation targets rather than calling `setPos` synchronously. Revise the movement-meaning claim and boundary provenance wording before acceptance.

## Immutable inputs

- Draft: `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/client-teleport-near-coordinate-fallback.md`, SHA-256 `A14DE01D32C95E7CB95584215DC8F2700E729CC55B4ED3960D90C06E9B81F5FD`.
- Endpoint source report: `.native-source/20261030/1152-1165-finish43/client-packet-player-receivers/report.md`, SHA-256 `957A85DBB8915B049139816ADCF467878F9F11975D3BB06734FD935085869354`.
- Cited endpoint independent review: `.native-source/20261030/1152-1165-finish43/packet-teleport-independent-review/verdict.md`, SHA-256 `7136192863EF3E2E078914B4E5D3D90459D696B898BCC31A377CA2388CFAA16E`.
- Boundary report: `.native-source/20261030/1152-1165-finish43/teleport-boundary-audit/report.md`, SHA-256 `B735812955161438D8C988DBDA8D8E8F3D18FB5E43F18DBB65F45792B15602BC`.

## Source checks

The exact Mojmap endpoint `ClientPacketListener.java` source hashes match the report: 1.15.2 `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30`; 1.16.5 `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`. In 1.15.2 `handleTeleportEntity` lines 593–613 writes the packet coordinates to cached packet coordinates before the `!entity.isControlledByLocalInstance()` gate. Inside that gate it keeps current coordinates only when each negated `>=` test passes; otherwise it uses the packet coordinates. In 1.16.5 lines 602–617 the same gate always passes packet coordinates to `lerpTo`.

The registered LocalPlayer path is reachable at both endpoints: `handleLogin` adds `minecraft.player` under the login ID (`ClientPacketListener.java:368–375` in 1.15.2; `:390–398` in 1.16.5), and `ClientLevel.getEntity` reads the entity-ID map (`ClientLevel.java:327–328` and `:318–319`). `LocalPlayer.isLocalPlayer()` returns true, does not override `getControllingPassenger` or `isControlledByLocalInstance`, and inherited `Entity.getControllingPassenger()` returns null. `Entity.isControlledByLocalInstance()` therefore takes `!level.isClientSide`; the `ClientLevel` constructors pass `true` for that flag at both endpoints, so the teleport receiver's negated control predicate is true for this LocalPlayer.

The finite-number explanation is correct: for finite deltas, `!(Math.abs(delta) >= threshold)` is equivalent to strict `< threshold`; the negated comparison also preserves the source's distinct NaN behavior. However, the movement-meaning sentence should require at least one nonzero coordinate delta. If current and packet coordinates are identical, both arms pass identical coordinates and there is no positional target difference.

### Required correction: `lerpTo` is deferred for LocalPlayer

The endpoint review cites `Entity.lerpTo`, but runtime dispatch for LocalPlayer resolves to its superclass override in `LivingEntity`, not the base `Entity` implementation. The audited source hashes are 1.15.2 `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` and 1.16.5 `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`. At 1.15.2 lines 2406–2413 and 1.16.5 lines 2585–2592, `LivingEntity.lerpTo` stores `lerpX/Y/Z`, rotation targets, and `lerpSteps`; it does not call `setPos`. The later interpolation block (`LivingEntity.java:2217–2225` in 1.15.2; `:2378–2386` in 1.16.5) computes an interpolated position and calls `setPos` while `lerpSteps > 0`. Thus the handlers synchronously change the LocalPlayer's interpolation target state, not its position. Any actual position divergence follows later interpolation and depends on tick ordering/state; this review does not establish packet arrival relative to the relevant tick.

Suggested bounded movement-meaning correction: state that, for finite current/packet coordinates with all absolute deltas strictly below the thresholds and at least one delta nonzero, 1.15.2 synchronously queues the current coordinate triplet while 1.16.5 queues the packet triplet for the same three-step LocalPlayer interpolation. Describe resulting position divergence only as a subsequent interpolation consequence, subject to tick ordering. Do not say either handler immediately writes a different player position. This retains the direct client receiver finding while correcting the reached operation and immediate effect.

## Boundary review

The boundary report hash matches the identity above. I independently inspected the 1.16.1 and 1.16.2 method bodies: the 1.16.1 snapshot retains the negated-threshold fallback (`ClientPacketListener.java:607–627`, file SHA-256 `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d`); the 1.16.2 snapshot is unconditional (`:602–617`, SHA-256 `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9`). The 1.16.2 file hash equals the manifest-backed 1.16.3 file hash. The 1.16.0 source tree is absent. Neither the 1.16.1 nor 1.16.2 tree has a provenance manifest, so use **snapshot labelled 1.16.1** and **first changed available snapshot labelled 1.16.2**, not a certified release cutover. The draft should also mention that the 1.16.1 snapshot is unmanifested; the boundary report identifies both `.1` and `.2` as lacking manifests. The exact earliest release remains unproven due to the missing `.0` source and absent provenance for `.1`/`.2` labels.

The `.0` path was checked and is absent. Boundary claims beyond `.2` are retained only to the extent of the cited boundary report: it records unconditional bodies for `.3`, `.4`, and `.5`, with `.3`/`.4` manifest-backed and `.5` unmanifested.

## Exact revisions requested

1. Replace the synchronous/immediate player-position-write assertion with the `LivingEntity.lerpTo` target-storage behavior and qualify downstream position effects as later interpolation, subject to tick ordering.
2. In the movement precondition, require finite current and packet coordinates, each strict threshold to pass, and at least one coordinate delta to be nonzero for a changed interpolation target.
3. In the boundary paragraph, call `.1`/`.2` snapshots by their folder labels and state that both lack provenance manifests; preserve “first changed available snapshot labelled 1.16.2” as an observation, not an exact release cutover.
