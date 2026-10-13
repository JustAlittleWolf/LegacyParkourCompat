# MCPK 1.16.2 sneak edge-backoff reconciliation

## Disposition

**Already implemented for the accepted bounded behavior; no duplicate change added.** The accepted r3 predicate resolves to the existing `V1_18_2` implementation when selecting `V1_16_2`. This record does not claim complete source-pair coverage or runtime parity.

## Accepted source identity

- Finding: 1.16.1 → 1.16.2 sneak edge-backoff predicate.
- Exact snapshot: `10d4937470804e6ffd75b35930a9c3d62044b9be:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r3.md`.
- Git blob: `2c7eeff1b8c1e9ab2913035a647a5f455befa20b`; content SHA-256: `182edbae3bed0e878f890c2a98ee402e51d0b8de91e3a954ce8030e6a77e9857`.
- Clean source acceptance: report `workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-followup-2026-10-08.md`, commit `bdda49ac76ce8b6e136917349e8b59a61d2fabe4`, merged at `e2c3ecb43a2badacf6beb6098c4f6f4f354ec7bc`.
- Ready source manifests: 1.16.1 Mojmap `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 Mojmap `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. The endpoint ready markers were checked and their cited source rows re-hashed.
- Cited source rows: 1.16.1 `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`. 1.16.2 `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`.

## Source-to-code reconciliation

- `ParkourVersion.V1_16` covers 1.16 and 1.16.1; `V1_16_2` covers 1.16.2 through 1.16.5. `ChangeResolver` selects the closest change whose `emulates` version is not older than the selected profile.
- For `V1_16`, `change/v1_16/SneakEdge` is selected and retains the old grounded-only guard. For `V1_16_2`, that change is older than the selection and `change/v1_18_2/SneakEdge` is selected as the closest later implementation. Its `!abilities.flying`, `SELF`/`PLAYER`, staying-on-ground-surface, and `isAboveGround` guards match the accepted 1.16.2 caller predicate.
- The 1.16.2 airborne predicate is exactly `fallDistance < probeDistance && !noCollision(player, box.move(0.0D, fallDistance - probeDistance, 0.0D))`, behind the `onGround ||` short circuit. The comparison remains strict; `!noCollision` requires a non-empty collision shape in the candidate volume. With the `V1_16_2` profile, no later-than-selected `SneakEdgeDistanceBehavior` resolves, so the mixin supplies `player.maxUpStep()` as the probe distance. Both operands in the vertical subtraction are floats before widening to the AABB method's double argument, matching the source operation order.
- `PlayerMixin` dispatches the historical gate from `Player.maybeBackOffFromEdge` before `Entity.move` collision resolution, passing the original movement vector into the change. `SneakEdgeBackoff` retains the source's X-only, Z-only, then combined probes, 0.05 adjustments, and sign handling. The r3 correction is honored: the airborne gate requires collision with a non-empty shape; it does not require an empty candidate volume.
- `MovementRuntime.find` returns no historical behavior for non-player entities. `CURRENT` resolves to no changes. The finding changes neither block states nor collision providers.

## Limits

- The source comparison and acceptance are bounded to the exact 1.16.1/1.16.2 gate and its existing implementation mapping. Other pair coverage and runtime trajectory are not claimed.
- The MCPK direct-page fetch returned HTTP 403; live wording and revision were not verified. The exact source evidence is Mojmap and does not establish Feather-derived JAR equivalence. The broader Feather provenance limitation remains open.
- No tests, build, runtime, or Docker work was performed.
