# Bounded audit: 1.8.9 → 1.9 explosion player-gate crosswalk

## Verdict

**ACCEPT**, within the assigned crosswalk scope only.

## Audited input identity

- Crosswalk: `build/source-189-19-20261117/tick-state/explosion-player-gate-crosswalk.md`
- Crosswalk SHA-256: `33FB701CF9B585FE6F28B60A08F839690C88FD271C7FF9F93A296C5EB70C6736`
- Accepted producer report: `build/source-189-19-20261117/tick-state/external-velocity-producers.md`, SHA-256 `33119428262A965543BD0A80EA00881D2F9C5B07A59A70F941BE5C70433C9C38`
- Producer audit: `build/source-189-19-20261117/tick-state/external-velocity-producers-audit/verdict.md`, SHA-256 `2C258197FEFB5EB46AD37A45C251DBFAB2B60971A51F321CCD9747D459E53D9F`, ACCEPT
- Exact source namespaces and manifest identities are recorded in the crosswalk; the source-file hashes cited there match the inspected exact source files.

## Audit result

The report accurately states the bounded source distinction. In 1.8.9, `Explosion.damageEntities` adds a player vector when `!abilities.invulnerable`; in 1.9 the corresponding gate is `!isSpectator() && (!isCreative() || !abilities.flying)`. The cited mode predicates are server game-mode predicates, and the state table is consistent with the applied `WorldSettings.GameMode` ability assignments: Creative sets `canFly`, `creativeMode`, and `invulnerable` without assigning `flying`; Spectator sets `flying=true` and is separately rejected by the 1.9 spectator check; the other listed modes clear the relevant flags.

The applied Creative/flying-off state is supported by the cited source path. The interaction manager applies mode abilities; `ServerPlayerEntity.setGameMode` synchronizes them. The client copies the server ability packet fields. Client-side flight toggles are conditional on `canFly`, send ability packets, and non-spectator grounded flight is cleared and synchronized. Both server handlers clamp received `flying` to `canFly`. Thus the report’s distinction is about a valid synchronized server state, not merely a client mirror or an impossible flag combination.

The row outcomes follow: survival/adventure admitted in both; Creative with `flying=false` rejected in 1.8.9 and admitted in 1.9; Creative with `flying=true` rejected in both; Spectator rejected in both. The report correctly limits the consequence to conditional server-side player-vector admission and explicitly leaves explosion encounter reachability, damage, trajectory, movement outcome, and exact-pair coverage/freeze unresolved.

## Boundaries

This ACCEPT covers only the source predicates, relevant server mode/ability state application and synchronization, and the stated state-table result for this crosswalk. It does not establish that a map or fixed movement profile reaches an explosion in the differing state, does not establish a later movement consequence, and does not close or expand the 1.8.9 → 1.9 pair coverage or freeze status.
