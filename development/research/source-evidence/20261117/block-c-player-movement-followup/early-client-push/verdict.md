# Early client entity push audit

Status: COMPLETED

Input comparison SHA-256: `83eb98297e12175ade974203cc5659fbfcf216199e7b3a699ba7c22548bcda33`

## Verdict

The bounded claim is confirmed: the first checked stable release with the direct client-side local-player velocity write is 1.9. In 1.8.9, the common living-entity movement tick only calls `pushAwayCollidingEntities()` when `!world.isClient`. In 1.9, the call is unconditional. The examined 1.9.1, 1.9.2, 1.9.3 and 1.9.4 caller/filter/player-push/base-writer bodies preserve the relevant behavior; source hashes show shared bodies across 1.9–1.9.2 and across 1.9.3–1.9.4.

The dispatch order and player role are supported. A non-riding ordinary living caller, such as a pig, runs the common tick query. Its candidate list includes an overlapping local player when the player is non-spectator and pushable. In 1.9 the client predicate admits that candidate specifically through `candidate instanceof PlayerEntity && candidate.isLocal()`. `LivingEntity.pushAway(candidate)` dispatches `candidate.push(caller)`: the local player is the receiver of `PlayerEntity.push`, which applies the sleeping guard and then calls `Entity.push(caller)`. The 1.9 base writer adds the negative horizontal delta to the player if it has no passengers. With unrestricted collision rules, horizontal separation at least `0.01F`, no no-clip, and no shared vehicle, this produces the stated direct velocity write. The witness needs neither riding nor an out-of-era entity.

The comparison also correctly records the writer-side distinction: 1.8.9's base writer checks the direct rider/vehicle pair and rider-null write gates; 1.9 uses `hasSameVehicle` and passenger checks. This distinction does not open a client path in 1.8.9 because the caller-side logical-side gate returns before the query.

## Evidence identity correction

One hash in the frozen comparison is mistyped. Its 1.8.9 `Entity.java` table entry gives `d4c10932cb5bb1067a5e58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; the exact local file hashes to `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`, matching the `ornithe-feather.sources.sha256` inventory. The comparison's source behavior description matches the actual file; the typo is confined to this digest string. The other checked requested source hashes match the comparison and the nested provenance `sources` entries or adjacent inventory sidecars. The listed outer/nested provenance identities were present and their file hashes matched the comparison.

## Bounds

Audited only the exact canonical local Ornithe Feather sources for 1.8.9 and stable 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4, limited to the compared living-entity caller, push predicate, player override and base velocity writer, with the ordinary living witness and local-player identity needed to establish reachability. This is not full tick or pair coverage; it does not establish the precise pre-release boundary, unexamined releases, late climbing/non-finite behavior, implementation, runtime parity, or tests. No source generation, product/wiki/Git inspection, test or runtime action was performed.