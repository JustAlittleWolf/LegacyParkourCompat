# Pair verdict: Feather 1.12.1 → 1.12.2

## Frozen scope

Blind, bounded source audit of ordinary local-player yaw and the first dry ground/air movement-acceleration use of current yaw. Source identities are frozen in `IDENTITIES_FROZEN.md`; this verdict is independent of the prior pair verdicts.

## Comparison and verdict

The exact 1.12.1 and 1.12.2 `GameRenderer.java`, `Entity.java`, and `LivingEntity.java` source files are byte-identical. The direct focused-and-`bl`-gated mouse route, both smoothing branches, and direct `player.updateLocalPlayerCamera(h, i*j)` call therefore retain identical yaw width/sign/cast/order. `Entity.updateLocalPlayerCamera` still updates current float yaw with the casted `yaw + mouseDelta*0.15`, adjusts float `lastYaw` from the current-minus-old yaw delta, and runs after the entity tick's `lastYaw=yaw` snapshot. The dry ground/air acceleration still chooses ground `getSpeed() * (0.16277136F/(s*s*s))` versus airborne `speedInAir`, and its horizontal components still use current yaw's same MathHelper sine/cosine kernel. The ordinary player input call and three-axis kernel are unchanged.

The `PlayerEntity.moveRelative` body at 1.12.2 `PlayerEntity.java:1386-1403` is text-identical to 1.12.1 `PlayerEntity.java:1386-1403`, including the same delegation and speed restoration in the ability-flying branch. Whole-file hashes differ, but the current dependency does not.

Finite witness: current and previous yaw 0 plus camera delta `(4.0F,0.0F)` yields yaw/lastYaw `0.6F`; then forward-only input 1, sideways/player vertical input 0, and finite ground/air scale `u` yield the same signed X/Z trig expressions in both releases. No per-pair movement difference was found for the assigned operation.

Patch introduction remains unknown outside this exact adjacent pair.

## Source files and SHA-256

- 1.12.1 and 1.12.2 `GameRenderer.java`: both `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`
- 1.12.1 and 1.12.2 `Entity.java`: both `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- 1.12.1 and 1.12.2 `LivingEntity.java`: both `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- 1.12.1 `PlayerEntity.java`: `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640`
- 1.12.2 `PlayerEntity.java`: `e4e0fdbbe07a0d0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`
