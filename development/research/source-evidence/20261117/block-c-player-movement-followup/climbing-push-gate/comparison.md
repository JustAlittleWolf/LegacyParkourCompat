# Climbing push-gate source comparison

## Result

The living-entity `isPushable()` gate changes between 1.10.2 and 1.11. A living entity was pushable whenever it was not removed; from 1.11 it is pushable only while alive and not climbing. For an ordinary ladder, `isClimbing()` is true. The new gate therefore prevents another living entity's collision query from selecting a climbing local player as the entity to push.

This is candidate/receiver eligibility, not a change to the push vector or to the local player's own push-away query. The player's own `pushAwayCollidingEntities()` still queries neighboring entities and calls `neighbor.push(player)`. That path may still write local-player velocity while climbing. Do not interpret the predicate as a blanket prohibition on all pushes received by a climbing local player.

## Exact source evidence

All examined source trees are `decompiled_minecraft/<version>/ornithe-feather/`, with `ornithe-feather.ready.json` declaring the exact version and `ornithe-feather` mapping, and `ornithe-feather.sources.sha256` checked against the source file bytes. Mapping artifacts are Feather Gen2 build 2 for each listed exact release.

| Release | Ready source-manifest SHA-256 | Relevant `LivingEntity.java` SHA-256 | Relevant `EntityFilter.java` SHA-256 |
|---|---|---|---|
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `49f2c3cbeea0bb9421fdfaad49a7742394f69cd73981a9cdd51e1badcf30c629` |

File hashes above match the corresponding entries in each version's source hash manifest. The 1.10.2, 1.11.1, and 1.12.2 ready markers also identify client artifact SHA-256 values `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`, `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`, and `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, respectively. The 1.11 and 1.11.2 provenance records also identify those releases and the same Feather mapping family; their ready markers provide the client hashes.

### Predicate difference

- **1.10.2 Feather:** `net/minecraft/entity/living/LivingEntity.java`, `isPushable()`, lines 1882–1889: `return !this.removed;` Thus climbing does not make a living entity ineligible.
- **1.11 Feather:** same file/member, lines 1943–1950: `return this.isAlive() && !this.isClimbing();` The exact same expression occurs at lines 1945–1952 in 1.11.1 and 1.11.2, and lines 1984–1991 in 1.12.2. The climbing exclusion is therefore present throughout the examined 1.11 patch releases and at 1.12.2. This comparison bounds the introduction to after 1.10.2 and by 1.11; it does not establish a finer intermediate boundary.
- In `LivingEntity.java`, `isClimbing()` checks the block at `(floor(x), floor(shape.minY), floor(z))`; after the spectator guard, ladder and vine return true. Relevant lines are 928–940 in 1.10.2, 978–990 in 1.11/1.11.1/1.11.2, and 1006–1018 in 1.12.2. This comparison concerns the ordinary ladder case only; the separate trapdoor ascent predicate is outside this assignment.

### Local-player candidate path

- `net/minecraft/entity/EntityFilter.java`, `canBePushedBy(Entity)`, tests candidate `entity.isPushable()` first (line 65 in 1.10.2; line 59 in 1.11 onward). On the logical client its remaining collision-rule branch admits player candidates only when `((PlayerEntity) entity).isLocal()`; the local `LocalClientPlayerEntity.isLocal()` returns true (line 317 in 1.10.2, 323 in 1.11–1.11.2, 329 in 1.12.2).
- `net/minecraft/entity/living/LivingEntity.java`, `pushAwayCollidingEntities()`, queries `world.getEntities(this, this.getShape(), EntityFilter.canBePushedBy(this))`, then calls `this.pushAway(entity)`; `pushAway` delegates to `entity.push(this)`. The relevant query/loop is lines 1778–1790 in 1.10.2, 1832–1859 in 1.11/1.11.1/1.11.2, and 1878–1904 in 1.12.2. When another living entity performs this query, the local player is a candidate entity. While on a ladder in 1.10.2 the candidate passes `isPushable`; from 1.11 it fails before the client/local-player collision-rule branch.
- `net/minecraft/entity/Entity.java`, `push(Entity)`, retains the same horizontal response procedure in the compared releases: reject shared vehicles or either `noClip`; require `absMax(dx,dz) >= 0.01F`; normalize using square root; cap reciprocal distance at `1.0`; scale by `0.05F` and `1.0F - pushSpeedReduction`; add opposite horizontal velocities to both entities if each has no passengers. References: lines 1085–1113 (1.10.2), 1107–1135 (1.11), and 1176–1203 (1.12.2). No changed push math was found in the examined versions.
- `PlayerEntity.push(Entity)` only adds a sleeping guard before delegating to `super.push(entity)` (lines 1904–1908 in 1.10.2, 1897–1901 in 1.11, and 1917–1921 in 1.12.2); it does not add a climbing check. The behavioral change described here is the earlier candidate filter through `isPushable()`.
- The player's own `pushAwayCollidingEntities()` query tests neighboring candidate entities' `isPushable()` values. Its call to a neighbor's `push(player)` does not test the player's `isPushable()`. Thus the new gate only stops the reverse query from selecting the climbing local player as its candidate; it does not establish zero player velocity response for every collision direction.

## Coverage and limits

Examined the exact 1.10.2, 1.11, 1.11.1, 1.11.2, and 1.12.2 Feather source members named above, their local-player inheritance/identity, and the nearby filter-to-push call chain. Rechecked `isPushable()` and `EntityFilter.canBePushedBy()` at every listed release. No difference in these methods was found between 1.11, 1.11.1, and 1.11.2, or between 1.11.2 and 1.12.2. No decompilation, product source, wiki, Git history, builds, tests, game runtime, or unrelated movement loop was used.

The result is bounded to the receiver-eligibility/collision-query path for an ordinary climbing player. It does not establish that no local-player push can occur while climbing, characterize the excluded trapdoor ascent path, or report the overall movement pair as frozen. The `isAlive()` part of the newer expression also has semantics outside the climbing-only claim; this draft isolates the climbing behavior.
