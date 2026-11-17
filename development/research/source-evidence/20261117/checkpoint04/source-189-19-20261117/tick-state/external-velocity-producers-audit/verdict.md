# Bounded source audit: direct velocity/explosion packet producers

Verdict: **ACCEPT for the bounded producer observations only.**

Bound input: `build/source-189-19-20261117/tick-state/external-velocity-producers.md`, SHA-256 `33119428262A965543BD0A80EA00881D2F9C5B07A59A70F941BE5C70433C9C38`.

## Identity and source checks

The bound 1.8.9 source manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its provenance SHA-256 is `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`. The 1.9 mapping-local `.decompile-provenance.json` SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. These identities match the assigned source contract.

I recomputed all six paired source-file hashes in the report. Each matched the corresponding 1.8.9 source-manifest row and 1.9 provenance entry. `EntityVelocityS2CPacket.java` and `ExplosionS2CPacket.java` are byte-identical across the pair.

## Audited claims

- `ServerWorld.explode` runs the same entity/block processing order and sends an explosion packet to server players with squared distance `< 4096.0`. Both constructors receive the player-keyed vector from `Explosion.getDamagedPlayers()`.
- `ExplosionS2CPacket` copies the three vector components to float fields only when the passed vector is non-null, with the same casts and field assignments in both versions.
- `Explosion.damageEntities` changes only the player-vector admission predicate in the bounded packet observation: 1.8.9 uses `entity instanceof PlayerEntity && !abilities.invulnerable`; 1.9 uses `entity instanceof PlayerEntity`, followed by `!isSpectator() && (!isCreative() || !abilities.flying)`. Both store `(aa * af, ab * af, ac * af)` when admitted. For states admitted by both predicates, this stored vector expression is unchanged.
- The successful-hit player velocity packet envelope is present in both versions: send to a `ServerPlayerEntity` target when `target.damaged`, clear the flag, then restore the saved server velocity components. Generic tracked-velocity sends use the same `0.02²` threshold (`4.0E-4` in 1.9) and the same nonzero-to-zero reset condition. The `damaged` full-velocity send remains present in both.
- The additional 1.9 fall-flying tracked-velocity branch is post-1.8 behavior and is correctly excluded from this 1.8.9 profile comparison.

## Limits retained

The report correctly leaves the player-state crosswalk between the old invulnerability flag and the newer spectator/creative/flying predicates unresolved, along with an ordinary in-scope scenario where those gates disagree. The report does not claim an explosion trajectory, damage-formula equivalence, or tracker-recipient inclusion of the entity's own client. Client packet handlers are expressly outside this bounded producer audit and were not re-reviewed here. Upstream attack/knockback values and pair-wide tick/provider coverage remain separate.

This ACCEPT is not a claim that the conditional gate difference is reachable in an ordinary profile scenario, is implementation-ready, or closes the 1.8.9→1.9 pair. Pair coverage remains partial; implementation and runtime parity are not assessed.
