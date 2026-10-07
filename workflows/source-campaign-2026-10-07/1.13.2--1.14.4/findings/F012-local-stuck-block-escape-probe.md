# F012: Local stuck-block escape probe

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local player push-away/stuck escape; S009
- Classification: changed behavior
- Confidence: candidate (A artifact-integrity repair pending before acceptance)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LocalClientPlayerEntity.java`::`mobTick`, lines 717-721, and `pushAwayFrom`/`wouldCollideAt`, lines 401-455; whole-file SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. `PlayerEntity.java`::`doesNotSuffocate`/`shouldSuffocate`, lines 1479-1485; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LocalClientPlayerEntity.java`::`mobTick`, lines 645-651, and `pushAwayFrom`/`wouldCollideAt`, lines 388-447; whole-file SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`. `PlayerEntity.java`::`shouldSuffocate`, lines 1420-1422; SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.

## Source-level difference

A invokes `pushAwayFrom` at four points before sprint checks; its method returns immediately for noClip. B surrounds the four calls with `!noClip`. Those guards are equivalent at the call boundary. The recovery probe differs: A tests one BlockPos; when not swimming it uses `!doesNotSuffocate(pos)`, where `doesNotSuffocate` requires `shouldSuffocate(pos)` and a non-solid block above; when swimming it uses `!shouldSuffocate(pos)`. A's neighbor candidates use `doesNotSuffocate` directly. B scans integer Y positions from floor of player box minY to ceil of maxY and reports collision if any `shouldSuffocate` check is false; neighbor candidates use the same full scan. B's `shouldSuffocate` uses `!state.isViewBlocking(world,pos)`; A uses `!state.isSolid()`.

## Reachability and dependencies

LocalClientPlayerEntity.mobTick -> four push-away probes -> suffocation predicates for current/neighbor blocks -> direct velocity component assignment to ±0.1. The scan depends on player dimensions and block view-blocking/solid definitions; collision/provider equivalence is not closed.

## Consequence and uncertainty

Source confirms changed sampling extent, neighbor test, and block predicate. Escape direction/velocity may differ for tall poses, swimming, and state-dependent block views. The reachability and outcome are not runtime tested; full shape/block inventory and A artifact integrity remain open.

## Handoff

Independent source candidate. Related finding F002 (pose/dimensions). Implementation handoff blocked by `D-ARTIFACT-INTEGRITY`, `D-COLLISION-SHAPES`, and absent independent reviewer acceptance. First changed release unknown.
