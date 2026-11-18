# Early east-border grounded producer: source closure

Status: COMPLETED — the ordinary grounded producer and the supplied conditional east-border query are source-supported. A vanilla WorldBorder collision candidate can still yield the rise-1 result when the actual loaded blockstate immediately above the ray hit is air.

## Source set and identity

Read-only canonical Feather trees inspected: `1.10`, `1.10.1`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, `1.12.2` under `decompiled_minecraft/<version>/ornithe-feather/`.

`1.10` and `1.10.1` have embedded `.decompile-provenance.json`; source hashes for the relevant player/movement/collision files match. Their Feather artifacts are respectively `feather-gen2/1.10+build.2` (`ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`) and `feather-gen2/1.10.1+build.2` (`6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`), with raw-client and metadata hashes embedded.

The other five releases have sibling `ornithe-feather.provenance.json`, `.ready.json`, `.sources.sha256`, and `artifacts.sha256` sidecars. For all five, actual source-manifest and artifact-manifest SHA-256 values match `.ready.json`; hashes for `LocalClientPlayerEntity.java`, `LivingEntity.java`, `PlayerEntity.java`, `Entity.java`, `World.java`, `Blocks.java`, and `Block.java` match `.sources.sha256`. Provenance names the exact Feather Gen2 build.2 artifacts. For 1.11 and 1.11.1, provenance records the mapping-artifact and raw-client hashes; for 1.10.2, 1.11.2, and 1.12.2, the artifact manifest contains those hashes and is bound by the ready marker. Exact source and mapping inputs are therefore verified for this seven-release set. No source regeneration or cache inspection was performed.

The complete `LocalClientPlayerEntity.autoJump(float,float)` body has identical SHA-256 `2ef3050e68d1ed7f2ce5e69d8899abb4d12a0a57d895e4e452a73c7682247184` in all seven trees (1.10 lines 823–922; 1.11 lines 835–934; 1.12.2 lines 857–956). It is not a patch-specific change in this set.

## Grounded producer

In the exact source, camera input is copied to player movement speeds in `LocalClientPlayerEntity.serverTickAi` (1.10 line 605; 1.11 line 617; 1.12.2 line 634). `LivingEntity.mobTick` then damps `forwardSpeed` by `0.98F` before calling `PlayerEntity.moveRelative` (1.10 lines 1742–1747; later releases retain that operation in the corresponding `mobTick`). With forward-only input and yaw -90°, input reaches the x axis as positive forward movement.

`LivingEntity.moveRelative` uses support-block slipperiness times `0.91F`; on grounded Stone, the ground scale is `getSpeed() * (0.16277136F / (s*s*s))`, with `s = 0.6F * 0.91F`. This multiplier is approximately 1.0. With Speed II yielding the supplied `getSpeed() ≈ 0.14F`, the damped forward input produces `dx ≈ 0.14 * 0.98 = 0.1372`. Source anchors: 1.10 `LivingEntity.java` lines 1386–1404 and `PlayerEntity.java` line 1412; 1.11 `LivingEntity.java` lines 1440–1452 and `PlayerEntity.java` line 1406/1408; 1.12.2 `LivingEntity.java` lines 1479–1492 and `PlayerEntity.java` line 1407. The 1.12.2 relative-movement signature adds an upward argument that is irrelevant to this grounded branch; the grounded horizontal scale and order are retained.

From horizontal rest at x≈8.85 this gives post-x≈8.9872 (≈8.99). The player’s half-width is 0.3, so its post-move x extent ends around 9.2872, still over the supplied Stone support cells x=8 and 9 and clear of the x=10 obstacle. The prior downward velocity is clipped against the y=63 Stone floor during `Entity.move`, preserving `onGround`; `LocalClientPlayerEntity.move` then calls `autoJump` with the actual post-collision horizontal delta. The grounded producer therefore meets the requested position and grounded precondition without relying on an airborne move.

## AutoJump query and wall conditions

For the actual nonzero delta, `autoJump` takes its direct movement vector. It starts `vec3d` at the post-move player position and constructs `vec3d2` at `this.x + dx`; the projection endpoint is then `vec3d2 + vec3d4 * p`. With `f≈0.14`, `p=max(f*7,1/l)≈0.98`, so the east ray endpoint is approximately `8.9872 + 0.1372 + 0.98 = 10.1044`: it crosses x=10 by about 0.104 blocks. It does not hit the one-block obstacle at x=10 prematurely. The query box is grown by the player width (0.6) around the start-to-end segment; its vertical range is feet y=64 through head y≈65.8. The two side rays are at about z=0.35 and z=0.65 and y=64.51.

For a full-collision block at x=10,y=64, with air at x=10,y=65 and the other queried headroom/corridor cells clear, the first ray hit has top y=65. In all seven aliases, the clearance loop directly samples `this.world.getBlockState(blockPos3)` (1.10 lines 888–895; 1.11 lines 900–907; 1.12.2 lines 922–929), so the y=65 read sees the actual air blockstate. The loop with `t < 1.2F` visits `t=1` only. The resulting rise `u=65-64=1` passes the strict test `u > 0.5F && u <= 1.2F`, so it arms `ticksToNextAutojump=1`. No Jump Boost is required.

`World.getCollisions` can add synthetic Stone boxes outside the border to the candidate list (1.10 `World.java` lines 911–937; 1.12.2 lines 973–1001). The ray at y=64.51 intersects the y=64 box; a y=65 box does not intersect that raised line. AutoJump derives the hit top from the intersected box, then reads above-cell clearance through direct `world.getBlockState`, which returns the loaded AIR at x=10,y=65 in the supplied fixture. The border fallback therefore does not turn the first-hit rise into 2 or defeat arming under the stated headroom condition.

## Boundaries

This closes only the ordinary forward grounded producer for the supplied east-side coordinates, AutoJump’s exact query path, and this seven-version source set. It does not establish runtime parity, intermediate patch cutovers, another direction, entity collisions, or other border configurations. No product, Git, backlog, wiki, or implementation sources were read; no code, tests, build, launch, or source generation was performed.
