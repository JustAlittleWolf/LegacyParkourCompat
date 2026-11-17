# Sleeping-player pitch reset source comparison

Assignment: direct player pitch write while sleeping, with attention to write order after travel, local-client execution, movement relevance, and the earlier source bound. Source scope was limited to `decompiled_minecraft/` plus its provenance sidecars. No wiki, product implementation, or Git history was read.

## Source identities and integrity

- 1.8.9, 1.9, 1.9.4, 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, and 1.14.4 are in the local canonical tree. Provenance sidecars identify `ornithe-feather` for the 1.8.9/1.9/1.9.4/1.12.2/1.13.x sources and Feather build 2 mapping artifacts for those releases; 1.14 Feather is `net.ornithemc:feather-gen2:1.14+build.2`; 1.14.4 Feather is `net.ornithemc:feather-gen2:1.14.4+build.2` (the sidecar records the combined `feather,mojmap` run). Source manifests are present for these certified directories. The per-file hashes below bind the examined class bodies.
- 1.14.1, 1.14.2, and 1.14.3 have canonical `yarn/` source directories, but no provenance JSON, mapping artifact manifest, or source manifest exists at those version roots. Their exact Yarn mapping build and source integrity cannot be verified from permitted local provenance. Their source bodies were read, but cannot be promoted as fully verified evidence.

## Compared source slices

- 1.8.9, 1.9, 1.9.4: `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick()` and `isSleeping()` (tick lines 1259, 1476, 1489 respectively). No `pitch = 0.0F` or sleeping-conditioned pitch assignment occurs in the examined living tick. File SHA-256 values: 1.8.9 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`; 1.9 `5B5936656AACC64A4D75DB7B370985F9456AC96076331DA047BD246F76A705FB`; 1.9.4 `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E`.
- 1.12.2: `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick()` end; no sleeping pitch reset. SHA-256 `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6`. `PlayerEntity.java` tick begins at line 174 and contains no zero-pitch sleep write; SHA-256 `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E`.
- 1.13 and 1.13.2: `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick()` at lines 1670 and 1689 respectively; neither has a sleeping pitch reset. SHA-256 values: 1.13 `C38E95C2C13E088B03D77FC2C6B98D4EDD7E6CA5AEF12B187C8E3F3EA782E864`; 1.13.2 `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C`. The 1.13.1 source was also searched for the same conditional assignment and has no write; provenance identifies Feather `1.13.1+build.2`.
- 1.14: the provenance-backed source used is `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick()`: after `tickMovement()` and yaw/head-turn bookkeeping it executes `if (this.isSleeping()) this.pitch = 0.0F` at line 2114. This is an unconditional-on-side state write gated only by `isSleeping()`. SHA-256 `1DB4DD1AA95A06C48511E5567C0D3836F2A6E5A12A5CF03AB5C892E429198B79`. The parallel Yarn directory lacks a provenance sidecar; it is not used as certified evidence.
- 1.14.1–1.14.3: Yarn `LivingEntity.java` has the same post-`tickMovement()` write at 2097–2098, 2103–2104, and 2113–2114 respectively. Class SHA-256: 1.14.1 `331E2D7DFA936A6F7197910BA374C4A60B479C8565FF7C64A4D6A9E1E8B52DDA`; 1.14.2 `E8224E7DF98CCF0986BB390F35E64293263D17B9AB1DBDFEB661C65D28B6EE78`; 1.14.3 `E8AED9C63C0DFBE88E4B9E11C58881BC6E04B9CBAECBDE5E3606E3AFA406D72B`. These file hashes do not replace the missing mapping/source provenance.
- 1.14.4: `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick()` ends with the same `if (this.isSleeping()) this.pitch = 0.0F` write at lines 2120–2121. SHA-256 `2CCCF4331CE9E62013EEB8E96163E5E146E1291619EB998CE9867DAA87C02B61`.

## Interpretation

The sleeping pitch reset is in the general living-entity tick path, not a local-player-only branch. It follows `tickMovement()` and orientation bookkeeping, so the current tick's travel has already run. Local client execution reaches it through the client player `tick()` calling `super.tick()` before sending movement packets; 1.14.1 Yarn `ClientPlayerEntity.java` shows this order at lines 172–183 and packet serialization of `pitch` at lines 213–240 (file SHA-256 `6AF7E75E80619AD77CE2C84692136B0A1D562A44D0E7910DCC3F6040B2DC9933`). The zeroed pitch can therefore be serialized as player look state. In player movement, `PlayerEntity.cannotMove()` returns `super.cannotMove() || this.isSleeping()` (1.14.1 Yarn line 964), so the sleeping player is blocked from normal voluntary movement. This makes the write a direct player orientation/network-state behavior, but not a current-tick movement-physics delta; its immediate visible effect is pitch/camera orientation. It is not safely described as camera-only because local angle packets carry the value.

The verified source interval establishes absence in 1.13.2 and presence in 1.14.0. It does not establish the exact patch introduction boundary inside that interval. The claim across 1.14.1–1.14.3 remains provisional until their exact mapping and source provenance is supplied.

## Terminal status

FAILED — comparison completed at the source-body level, but exact provenance for the required 1.14.1–1.14.3 Yarn sources is absent, so the full assigned evidence set is not provenance-closed.
