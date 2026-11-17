# Independent correction receiver audit, revision 2

Terminal: **COMPLETED**

## Verdict

**ACCEPTED.** The corrected finding matches the exact-source behavior and closes the prior audit’s sequence-scope objection. It correctly states that `lastTeleportSequence` is compared with the acknowledgment sequence once per `endPredictionsUpTo` call, while each retained entry is independently filtered by `state.sequence <= acknowledgmentSequence`. At or below the captured cutoff, processed entries pass `null` to `syncBlockState`; above the cutoff, they pass their saved positions, including positions saved before or at the correction cutoff when those entries remain eligible.

## Exact ordinary local receiver witness

For a nonpassenger receiving a direct player-position correction, both versions apply the values from the packet and then send teleport acceptance followed by the resulting PosRot. In 26.1.2, `onTeleport()` runs after these sends and records the then-current prediction sequence `T`. In 1.21.11, there is no corresponding capture.

A retained prediction state contains a saved `player.position()` and a sequence. A subsequent retention for the same position updates its sequence but preserves the original saved position. On a block-change acknowledgment with sequence `S`, the handler processes entries whose retained sequence is at most `S`. In 1.21.11 every such entry passes its saved position to `syncBlockState`. In 26.1.2 the argument is selected by `lastTeleportSequence < S` for the whole handler call.

Thus, for an ordinary local witness where the restored block state differs and the existing `player.isColliding(pos, state)` predicate is true:

- If `S <= T`, the entry is processed when its retained sequence is at most `S`, but the supplied player position is `null` in 26.1.2. The restored block state can still be installed; `absSnapTo` is not called. In 1.21.11 the saved position is supplied and the snap occurs under the same-level/collision gates.
- If `S > T`, every processed entry receives its saved position in 26.1.2. A pre-cutoff saved position can still trigger `absSnapTo` under the same world and collision conditions. This confirms the bounded observer case without implying per-entry suppression.

This consequence is a direct local player-position response through `absSnapTo`. The state restore remains present, the collision check uses the existing vanilla state/player predicate, and no block provider, collision-shape change, UI behavior or packet serialization difference is part of the finding. The witness is conditional on the supplied ordinary local world state satisfying the stated state-difference and collision predicates; it is source-level evidence, not a runtime demonstration.

## Provenance and immutable inputs

The corrected source report and finding were SHA-256 bound before review:

- `report-r2.md`: `9c4ab9d4456fff8d3a7fc5df63d0aa31ad15a8d941c6fc990e4d0d720250d787`.
- `finding-r2.md`: `ca834d43c997bd4a4274414318d9e8a98e3d9587292ca38760ae5756897b927e`.

Canonical source identity and adjacent provenance were rechecked. The ready markers embed the exact source-manifest identities; sidecars exist with the expected version and namespace. Full source-manifest verification again found no missing files or hash mismatches:

- `1.21.11/mojmap`: source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; ready-marker SHA-256 `0ad98d8ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance `mojmap.provenance.json` identifies `1.21.11` / `mojmap`; 6,622 of 6,622 source files match.
- `26.1.2/unobfuscated`: source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; ready-marker SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; provenance `unobfuscated.provenance.json` identifies `26.1.2` / `unobfuscated`; 6,882 of 6,882 source files match.

The prior verdict remains unchanged at `c-queue-12111-2612-corrections-clean-audit/verdict.md` (SHA-256 `38ee8aec6150e9f8e23ce94740986d2b49357205254c4c28b371579092500b99`). Its original reviewed draft/report identities were respectively `b56823704008fb9f8b004981c0db1cc8482dee5db2ff691750300e9846dede98` and `880bb97eb0b771eb73dbaa74e7cb12c1f34ea015040a4705c11ebfd21d23cee7`. This verdict accepts the corrected revision only; it does not revise the earlier verdict or its immutable subject.

## Residual bounds

Accepted scope is the corrected acknowledgment-level position-snap difference for 1.21.11 Mojmap → 26.1.2 published unobfuscated under the ordinary direct local receiver path and stated collision witness. This does not establish an exact intermediate-version introduction boundary, full-pair coverage/freeze, behavior for other correction or acknowledgment families, a particular named block’s shape, implementation status, or runtime parity. No product, Wiki, Git/history, backlog, code, tests, launch, build or source generation was used.