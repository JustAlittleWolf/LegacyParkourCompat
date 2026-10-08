# MCPK 1.15 slipperiness sample reconciliation

## Disposition

**Implemented the missing exact 1.15.2 sampling path.** The existing `V1_14` change covers the 1.14.4 sample. The 26.2 native path uses a later `getOnPos(0.500001F)` support lookup with cached supporting-block handling, so it is not an exact replacement for the accepted 1.15.2 `minY - 0.5000001` calculation. A new versioned change now restores that exact B endpoint calculation for historical profiles.

Implementation commit: recorded in this task branch after the code change. Independent implementation review is still required; this handoff asks the reviewer to verify the exact double literal, the selected profile behavior, and the shared friction call site. No runtime parity is claimed.

## Accepted source identity

- Finding: 1.14.4 → 1.15.2 slipperiness sampling examples.
- Exact snapshot: `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md`.
- Git blob: `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`; content SHA-256: `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c`.
- Clean review acceptance: `workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-followup-2026-10-08.md`, commit `bdda49ac76ce8b6e136917349e8b59a61d2fabe4`, merged at `e2c3ecb43a2badacf6beb6098c4f6f4f354ec7bc`. The follow-up corrected the exact blob identity; snapshot content was unchanged.
- Ready source manifests: 1.14.4 Mojmap `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.15.2 Mojmap `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. Both ready markers were checked and cited source files re-hashed.
- Current target source: canonical 26.2 unobfuscated ready marker has source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; current `Entity.java` is SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` and `BlockPos.java` is `c86391b8b18c0ba47117a197376adef8d79c8781435cfddc91f2e5806691b899`.
- Cited source rows: 1.14.4 `LivingEntity.java` `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`. 1.15.2 `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `BlockPos.java` `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`; `Vec3i.java` `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`; bed, slab, and Soul Sand source identities are in the immutable snapshot.

## Source-to-code reconciliation

- In 1.14.4, grounded movement samples `BlockPos(x, minY - 1.0, z)`. Existing `change/v1_14/GroundFrictionSupportCellChange` returns `BlockPos.containing(entity.getX(), minY - 1.0, entity.getZ())`, preserving double subtraction and floor semantics for the old profile.
- In 1.15.2, `Entity.getBlockPosBelowThatAffectsMyMovement()` samples `BlockPos(x, minY - 0.5000001, z)`. The `BlockPos(double, double, double)` path floors each coordinate. The current native 26.2 method instead delegates to `getOnPos(0.500001F)` and may use `mainSupportingBlockPos`; its float offset and support cache are later behavior, so the 1.15.2 endpoint needed its own exact hook.
- New `change/v1_15_2/GroundFrictionSamplePoint` returns `BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ())`. The Y expression is double arithmetic in the same order as the source; `BlockPos.containing` floors coordinates. It is registered in the existing V1_15_2 provider under the existing `entity.supporting_block` mechanic key.
- Existing `LivingEntityMixin` dispatches that key at the current `LivingEntity.travelInAir` support-position call. The friction value is consumed for grounded movement. `MovementRuntime.find` is player-only, so non-player movement continues through native lookup. The code changes no block state, shape, or world-state lifecycle. `CURRENT` continues with the native 26.2 implementation.
- Resolver mapping: `V1_14` selects the old `minY - 1.0` change; `V1_15_2` selects the new `minY - 0.5000001` change. `V1_15` (1.15/1.15.1) has no closer registered change and therefore selects the nearest later V1_15_2 change under the established resolver policy. The exact first changed release within `(1.14.4, 1.15.2]` remains unverified; this fallback is not a claim that the cutover was independently established at 1.15.0.

## Limits and review request

- The bounded snapshot examples are a bottom slab (new sample still selects the block below), a bed, and Soul Sand (new sample selects the block's own cell). These establish sampled-cell outcomes, not full movement trajectories or complete 1.14-to-1.15 pair coverage.
- The MCPK direct-page fetch returned HTTP 403; live wording/revision was not verified. The compared endpoint sources are Mojmap; equivalence to Feather-derived JARs is not established. Preserve the open Feather provenance limitation.
- Independent implementation review is required. In particular, review the literal `0.5000001` type/order, current mixin target/guard, and resolver fallback described above. No subagents or other chats were used.
- No tests, build, runtime, or Docker work was performed.
