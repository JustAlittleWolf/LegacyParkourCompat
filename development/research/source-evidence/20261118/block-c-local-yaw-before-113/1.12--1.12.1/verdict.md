# Pair verdict: Feather 1.12 → 1.12.1

## Frozen scope

Blind, bounded source audit of ordinary local-player yaw and the first dry ground/air movement-acceleration use of current yaw. Source identities are frozen in `IDENTITIES_FROZEN.md`; this verdict is independent of the preceding and following pair verdicts.

## Comparison and verdict

`GameRenderer.java`, `Entity.java`, and `LivingEntity.java` have identical SHA-256 bytes in the exact 1.12 and 1.12.1 Feather trees. The 1.12.1 source records 2,050 source paths and pins its own exact 1.12.1 Mojang metadata and Feather Gen2 build.2 mapping input. The direct focused-and-`bl`-gated mouse route still calls `player.updateLocalPlayerCamera(h, i*j)` in smooth and ordinary branches. The same float yaw/pitch update, float cast after double `0.15` multiplication, pitch subtraction/clamp, previous-angle accumulator updates and ordinary tick snapshot remain byte-identical. `LivingEntity` still selects ground `getSpeed() * (0.16277136F/(s*s*s))` versus airborne `speedInAir`; it still passes its ordinary player input through the same yaw-based acceleration kernel. Current `yaw` remains the trig source.

The relevant `PlayerEntity.moveRelative` override at 1.12.1 `PlayerEntity.java:1386-1403` is text-identical to 1.12 `PlayerEntity.java:1386-1403`: both ability-flying and ordinary branches delegate the same three inputs to `super`, then update movement statistics. Although the whole PlayerEntity files differ, no change is present in this dependency.

Finite witness: with current and previous yaw 0, camera delta `(4.0F, 0.0F)` yields current and previous yaw `0.6F` in both releases. For finite forward input 1, zero sideways/player vertical input, and finite branch scale `u`, each yields `vx=-u*sinTable((int)(0.6F*(float)(PI/180)*10430.378F))` and `vz=u*cosTable((int)(0.6F*(float)(PI/180)*10430.378F+16384F))` using the identical `MathHelper` implementation. No per-pair movement difference was found for the assigned operation.

Patch introduction remains unknown outside this exact adjacent pair.

## Source files and SHA-256

- 1.12 and 1.12.1 `GameRenderer.java`: both `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`
- 1.12 and 1.12.1 `Entity.java`: both `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- 1.12 and 1.12.1 `LivingEntity.java`: both `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- 1.12 `PlayerEntity.java`: `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063`
- 1.12.1 `PlayerEntity.java`: `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640`
