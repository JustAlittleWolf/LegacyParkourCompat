# Independent implementation review: 1.15.2 friction sampler and 1.16.2 edge backoff

**Decision — V1_15_2 slipperiness sampler: ACCEPT**
**Decision — V1_16_2 edge-backoff no-code proof: ACCEPT**

**Reviewed implementation branch:** `fix/mcpk-accepted-candidate-reconciliation`
**Exact reviewed implementation tip:** `ad684f454d969117ae786f4c038ae573c3170a99`
**Implementation commit:** `ad653ac30465d990b34e98601b6566ab3f150bc0`
**Feature base / merge base:** `b099aa02d82e57c1672f68375edcbcacdf8e3973`
**Current main merged before handoff:** `0bfb72a0bc08726f2ee3a984203c08b5d90cf34e`
**Review branch:** `fix/review-mcpk-slipperiness-1-15-2-2026-10-08`

## Scope and outcome

The feature diff from the base to the implementation tip contains four files and 83 additions: one new V1_15_2 hook, its provider registration, and two reconciliation records. The edge-backoff reconciliation contains no Java change. No confirmed functional findings were found. `git diff --check` is clean.

The two decisions are independent: the V1_15_2 sampler is accepted as implemented; the V1_16_2 edge-backoff candidate is accepted as a no-code reconciliation because the existing resolver-selected behavior already matches the accepted source evidence.

## Source identity and limits

The accepted slipperiness snapshot is commit `78683ba65928004ce8b7b6b9371359164a68d43b`, path `workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md`, blob `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`, content SHA-256 `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c`. Clean source re-review acceptance is commit `bdda49ac76ce8b6e136917349e8b59a61d2fabe4`.

The accepted edge-backoff r3 snapshot is commit `10d4937470804e6ffd75b35930a9c3d62044b9be`, path `workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r3.md`, blob `2c7eeff1b8c1e9ab2913035a647a5f455befa20b`, content SHA-256 `182edbae3bed0e878f890c2a98ee402e51d0b8de91e3a954ce8030e6a77e9857`; the same clean acceptance commit applies. Source manifests, artifact manifests, and checked source-file hashes are retained in the implementation handoffs at reviewed commit `ad684f4`.

The MCPK page fetch returned HTTP 403, so its live wording and revision could not be independently checked. The reviewed source evidence is the accepted snapshot. Mojmap sources also do not establish equivalence to unavailable Feather-derived JARs. These are source-provenance limits, not implementation findings.

## V1_15_2 slipperiness sampler

`GroundFrictionSamplePoint.java:11-20` returns `BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ())`; `MovementChanges.java:9` registers it for V1_15_2. This preserves the 1.15.2 source's double subtraction and operand order. The historical `BlockPos(double, double, double)` path floors coordinates through `Mth.floor`; current `BlockPos.containing(double, double, double)` does likewise. There is no float cast or altered rounding in the hook.

The exact 1.15.2 `Entity.java` source uses `new BlockPos(this.x, this.getBoundingBox().minY - 0.5000001, this.z)` at lines 600-602. `LivingEntity.java` uses that result to obtain friction at lines 1886-1889. The 1.14.4 source instead samples `minY - 1.0`; the repository's V1_14 change retains that behavior. The accepted artifacts establish the endpoint sources 1.14.4 and 1.15.2, but do not establish the exact cutover within `(1.14.4, 1.15.2]`. In particular, this review does not assert a 1.15.0 versus 1.15.2 boundary beyond the accepted evidence.

The current `LivingEntityMixin` redirect dispatches this hook only for player movement and invokes vanilla when no change resolves. `MovementRuntime.find` remains the existing selection boundary. The per-mechanic resolver chooses the closest registered change whose version is not older than the selected profile; `CURRENT` resolves no changes. Therefore V1_14 and earlier retain the V1_14 sample, V1_15/V1_15_2 select this V1_15_2 sample, and V1_16 and later do not receive this hook and use the native sampler. This verifies the version boundary and avoids leakage into modern profiles.

Source inspection found the direct double sampler through 1.19.4 and a supporting-block cache plus float-epsilon path by 1.20.1. The exact transition between those source endpoints is not established here. The V1_15_2 handler is excluded for V1_16+ by the resolver, so that later-source transition does not change this patch's effect.

## V1_16_2 edge-backoff no-code proof

The implementation handoff proposes no edge Java change. The source snapshot accepts the 1.16.2 step-down behavior, and the existing V1_18_2 `SneakEdge` is the closest eligible `SneakEdgeBehavior` for selected V1_16_2: the V1_16 handler is older than the selection and is filtered out. `SneakEdgeDistance` is only registered at V1_10_1, so it is also ineligible and the caller supplies the native `maxUpStep()` distance. This resolves the two hooks independently, as the API intends.

The selected V1_18_2 handler preserves the accepted conditions: flying bypass, `SELF`/`PLAYER` mover types, staying on the ground surface, and the `onGround || (fallDistance < probeDistance && !noCollision(...))` probe. The comparison is strict and the collision box is moved vertically by `fallDistance - probeDistance`. When eligible, it calls the shared existing `SneakEdgeBackoff.apply`; that implementation's X-only, Z-only, then combined probes and 0.05 backoff increments remain unchanged. `PlayerMixin` supplies the movement and ground-surface arguments before collision resolution. No missing code change is indicated by the accepted evidence.

The reviewed branch adds only the edge reconciliation record, not a new 1.16.2 implementation. This is a no-code proof of existing behavior, not a claim of runtime parity or exhaustive pairwise testing.

## Latest-main integration and semantic overlap

Current `main` (`0bfb72a0bc08726f2ee3a984203c08b5d90cf34e`) was merged into this isolated review branch. The merge was clean. Its code changes cover pose dimensions, fall-flying saved state, and their mixin wiring. It does not modify `GroundFrictionSamplePoint`, the V1_15_2 provider registration, `GroundFrictionBlockBehavior`, `ChangeResolver`, `LivingEntityMixin`'s friction redirect, `SneakEdge`, `SneakEdgeBackoff`, or the edge hook dispatch. The new `PlayerMixin` pose and fall-flying injections are separate from the existing edge method hooks. No semantic overlap or regression was found.

## Six review angles

1. **Requirement fit — PASS.** The accepted 1.15.2 sample formula is present; edge behavior is already supplied by the closest eligible existing hook.
2. **Correctness and edge cases — PASS.** Coordinate math, floor conversion, edge predicate, strict threshold and vertical probe match the available accepted source evidence.
3. **Coverage and version boundaries — PASS with source limits.** Resolver behavior prevents the new sampler from affecting V1_16+ and preserves the older V1_14 handler. The exact 1.15.x source cutover remains unverified; no unsupported boundary is claimed.
4. **Architecture and conventions — PASS.** The sampler is a small versioned mechanic change behind the shared hook. The edge case uses existing independent behavior hooks and shared backoff code; no physics loop was duplicated.
5. **Permissions and user-visible behavior — PASS.** No permission, configuration, networking, or UI behavior changed.
6. **Security — PASS.** No new external input, trust boundary, persistence, or resource access was introduced.

No tests, build, client, TAS, server, or runtime checks were run, as requested. These verdicts are static source and resolver reviews only.
