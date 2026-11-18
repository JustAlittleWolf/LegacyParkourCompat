# Independent source audit verdict

**Verdict: ACCEPT — bounded no-difference claim supported.** The frozen report is unchanged at SHA-256 `afdc843d93258c541be01f72366751017ebd5e8be1303cd115909fb2e52e0e1d`.

## Inputs and provenance

Audited only the immutable report at `c-glide-climb-1111-1112-alias-gate/report.md` and the adjacent exact prepared sources at `decompiled_minecraft/1.11.1/ornithe-feather/` and `decompiled_minecraft/1.11.2/ornithe-feather/`.

Both ready markers and provenance files identify their exact version IDs and `ornithe-feather` namespace. 1.11.1 provenance records Feather Gen2 build 2; 1.11.2 provenance names the matching `feather-gen2-1.11.2+build.2` artifacts. I recomputed the report's provenance, ready, source-list, artifact-list, diagnostics, and three reported Java-source hashes for both sides; all match. Their source manifests also contain the matching relevant source entries. The report correctly limits provenance confidence: the recorded cached raw clients and mapping artifacts were absent, while prepared source files and local source manifests remain present and hash-consistent. The 1.11.2 ready marker does not itself carry explicit provenance-hash fields; its adjacent provenance hash is independently computed in the report.

## Claim review

- `LivingEntity.java` is byte-identical across the pair (SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`). `ClientPlayerEntity.java` is also identical. `PlayerEntity.java` differs only at the out-of-scope serialized `DataVersion` value, 921 versus 922.
- At `LivingEntity.moveRelative`, after the local-control/logical-side and non-water/non-lava gates, the `isFallFlying()` branch is tested before the normal branch that calls `isClimbing()`. For the specified ordinary dry ladder/vine case, active glide therefore bypasses the ordinary climb clamps, fall-distance reset, downward floor, sneak stop, and post-move horizontal-collision `velocityY = 0.2` cleanup. `isClimbing()` samples the block at floored x/z and `shape.minY`; ladder/vine returns true for non-spectator players. It also recognizes a qualifying trapdoor, which does not undermine the report's deliberately narrower ladder/vine claim.
- The active-glide gate is flag 7: `isFallFlying()` returns `getFlag(7)`. `Entity.getFlag`/`setFlag` read and update bit 7 in `syncedData` on both versions. In `LivingEntity`, `flyingTick()` reads that current bit, checks airborne/not-riding and a chest-slot `Items.ELYTRA` for which `ElytraItem.canFly` is true, then writes its recomputed bit only when `!world.isClient`. `travel()` calls `flyingTick()` immediately before `moveRelative()`. Thus a local client does not recompute or clear the flag from its equipment check in that call; the bounded local case enters glide from its already-present shared flag state. This supports the report's explicit exclusion of initial activation and synchronization behavior.
- Inside the glide branch, `velocityY > -0.5` is a strict comparison against a double literal. If true, `fallDistance = 1.0F` is written before look-vector math and before `move(SELF, ...)`. After movement, the `onGround` clear of flag 7 is gated by `!world.isClient`; it is distinct from `flyingTick()`, which runs before the movement call.

## Exact references

Both versions: `LivingEntity.java` `isClimbing()` lines 978–990, `moveRelative()` lines 1388–1480, `travel()` lines 1798–1804, `flyingTick()` lines 1810–1828, and `isFallFlying()` lines 2145–2147. `Entity.java` flag accessors are lines 1813–1823 in 1.11.1 and 1800–1811 in 1.11.2. Both `LivingEntity.java` sources have the SHA above; the report's client and player hashes were independently verified.

The result remains bounded to this operation and this adjacent pair. It does not establish complete 1.11.1–1.11.2 movement coverage, initial glide activation behavior, or an intermediate implementation cutover.
