# Independent audit: ordinary player border ingress follow-up

Verdict: ACCEPT for the bounded source binding and conclusion.

## Bound report and exact inputs

- Report: `ordinary-player-border-ingress-followup.md`, SHA-256 `062F311FF5CBAEAEB22B6C2C97B6267D4F9FC86C9DB96BD5338D239C13C067A4`.
- 1.9.2 provenance/source manifest identity: `.decompile-provenance.json`, SHA-256 `9928B467DEB35C7E7F891B4FEE1627A68FEECDF233C2076909FD4834B0380510`.
- 1.9.3 provenance/source manifest identity: `.decompile-provenance.json`, SHA-256 `43325CDD53CA34B88EE9AF8464782B323A948B98C8A556C4761F1E5737B84C3A`.
- Recomputed paired source hashes match both provenance `sources` entries and the report:
  - `net/minecraft/entity/Entity.java`: 1.9.2 `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A`; 1.9.3 `BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0`.
  - `net/minecraft/world/World.java`: 1.9.2 `CDDA70C97F0E4D6F6C454C8F6D3C0D47F0E0AE612E75062D8FE93DDD3963E6B0`; 1.9.3 `2FE063E0EC224EED9FC7D01B8F788C32035EED5FEE5A9D98EA5E82296236E05A`.

## Audit result

The paired `Entity.move` bodies use a direct position update only in the `noClip` branch. The ordinary branch queries collisions before applying movement, resolves Y then X then Z against the returned boxes, and derives the final position from the clipped shape. The step-up alternative performs its own collision query before clipping. This supports the report's narrow conclusion that a large ordinary movement request cannot skip the collision barrier solely by requesting a displacement beyond it.

Both paired `World.getCollisions` implementations calculate whether the entity is within the border, initialize the candidate state as stone, and read the actual state only when the block position is inside the border or the entity is not within the border predicate. Thus, for an in-border entity, scanned cells beyond the border contribute stone collision boxes to the ordinary movement query. The paired `WorldBorder` sources cap the absolute maximum edge at `maxSize` (initialized to `29,999,984`); changing the center does not move that maximum beyond the hard-coordinate exterior beginning at `30,000,000`. The report's distance/order argument is supported for the stated ordinary, collision-resolved path.

The report correctly excludes `noClip` movement and externally supplied initial positions from ordinary ingress, and keeps cached block contents, a nonempty intersecting provider shape, tick initial-state ingress, and trajectory/response as separate open gates. The accepted streaming/query packets are reused only within their existing scope. No movement effect or pair freeze is inferred.

No product/wiki/backlog inputs, builds, tests, or runtime evidence were used.
