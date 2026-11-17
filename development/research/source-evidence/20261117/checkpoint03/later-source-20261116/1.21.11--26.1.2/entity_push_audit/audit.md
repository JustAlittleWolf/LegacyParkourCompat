# Independent source audit: entity contact push, 1.21.11 to 26.1.2

## Audit binding

- Audited report: `build/later-source-20261116/1.21.11--26.1.2/entity_push/entity-push-source.md`
- Audited report SHA-256: `C01F19E6658DED3080E23A0233C4E9808F47B35A89E4E27015D6C7E37F0A9822`
- Verdict: **ACCEPT**
- Scope: the bounded direct local-player entity-contact push route described in the report. This audit does not freeze pair coverage or establish runtime parity.

## Independent checks

I independently checked the endpoint ready descriptors, provenance records, and source-manifest hashes for 1.21.11 Mojmap and 26.1.2 unobfuscated. Both reported endpoint IDs, mappings, manifest digests, artifact-manifest digests, and pinned client-jar digests match their local ready/provenance records. Every Java source hash listed in the report's evidence table matches the exact source bytes and the corresponding source-manifest row. The audited report bytes match the SHA-256 above.

I traced the relevant source behavior at both endpoints:

- `LivingEntity.aiStep` reaches `pushEntities`; that method obtains `level().getPushableEntities(this, this.getBoundingBox())` and dispatches each result through `doPush`. The base `doPush` calls `result.push(this)`. `LocalPlayer` inherits the LivingEntity implementation through `AbstractClientPlayer`, `Player`, and `Avatar`; no intervening player/client-player `doPush` override was found in the cited source roots.
- `ClientLevel.getPushableEntities` returns only `minecraft.player`, subject to non-null, not-the-pusher, bounding-box intersection, and `EntitySelector.pushableBy(pusher)` checks. The selector retains the client-side local-player eligibility gate, pushability and spectator gates, and team collision rules/alliance logic. `LocalPlayer.isLocalPlayer()` returns true at both endpoints.
- `LivingEntity.push(Entity)` skips the inherited push when the local player is sleeping. `LivingEntity.isPushable()` requires alive, non-spectator, and not on a climbable at both endpoints.
- In the dispatched call, the local player is the receiver of `push(Entity)`. `Entity.push(Entity)` preserves the same-vehicle and no-physics gates, horizontal delta calculation, `Mth.absMax` threshold (`0.01F`), square-root normalization, reciprocal cap, `0.05F` scale, sign and write order, and per-side vehicle/pushability checks. The local-player response is the first negative impulse write. The numeric overload still rejects non-finite components, adds to delta movement, and sets `needsSync`.

The compared source operations have the same behavior despite mapping, local-variable, and formatting changes. The report's limits are accurate: it does not claim packet knockback/explosions, block collision, vehicle behavior, non-player response, complete movement coverage, or pair closure.

## Evidence paths

- `decompiled_minecraft/1.21.11/mojmap.ready.json`
- `decompiled_minecraft/1.21.11/mojmap.provenance.json`
- `decompiled_minecraft/1.21.11/mojmap.sources.sha256`
- `decompiled_minecraft/26.1.2/unobfuscated.ready.json`
- `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`
- `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256`
- Java source paths and per-endpoint SHA-256 values are listed in the audited report's evidence table.