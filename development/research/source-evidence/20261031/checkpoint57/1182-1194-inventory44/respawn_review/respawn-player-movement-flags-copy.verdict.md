# Independent source verdict: respawn player movement flags

**Verdict: REJECT AS SUBMITTED; substantive sprint finding is supported, but renew acceptance after correcting the 1.19 citation.**

## Inputs and evidence identity

- Draft: `.native-source/20261030/1182-1194-inventory44/findings/1.19.2--1.19.3/respawn-player-movement-flags-copy.md`, SHA-256 `41304102D3EE12CC7293A51E15540D4A9D66D54769556F1DD39D20F011FE206D` (matches assigned hash).
- Exact sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.19,1.19.1,1.19.2,1.19.3}\mojmap`; Mojmap/Vineflower roots. Each `mojmap.ready.json` agrees with its `mojmap.sources.sha256` digest and assigned identity:
  - 1.19 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`
  - 1.19.1 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`
  - 1.19.2 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
  - 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- Relevant source files and SHA-256s, verified against those manifests:
  - `ClientPacketListener.java`: 1.19 `a67c7880c967e93322de0f2e30af0eb34e24a8b7df21171e2a37032b27a21671`; 1.19.1/1.19.2 `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`; 1.19.3 `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`.
  - `ClientboundRespawnPacket.java`: 1.19–1.19.2 `4e2e903725e441d010529fd4b11a9b5086aecf26a73983c5fa2d9486eb8025b7`; 1.19.3 `26261980683e598d52685eb07e053e1093fa4a4a98e5b5898bfdd8bf362beed9`.
  - `Entity.java`: 1.19 `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766`; 1.19.1/1.19.2 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; 1.19.3 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`.
  - `LivingEntity.java`: 1.19 `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c`; 1.19.1/1.19.2 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; 1.19.3 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
  - Additional reachability source `LocalPlayer.java`: 1.19 `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805`; 1.19.1/1.19.2 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`; 1.19.3 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`.

## Reason and supported scope

The 1.19 cite `LivingEntity.isFallFlying`, lines 3048–3049, is wrong: the method is lines 3045–3047 (`return this.getSharedFlag(7)`); line 3049 is inside `isVisuallySwimming`. Correct that range before acceptance. The 1.19.3 range 3063–3065 is correct.

The central difference is source-supported. `ClientPacketListener.handleRespawn` assigns `getAll()` entity data unconditionally in 1.19 line 989, 1.19.1 line 998 and 1.19.2 line 998. In 1.19.2 the separate `shouldKeepAllPlayerData()` gate controls attributes (packet lines 106–108). In 1.19.3 only `shouldKeep((byte)2)` gates nondefault entity data (listener lines 1051–1056); attributes use bit 1 (1058–1060), and packet constants identify bit 2 as `KEEP_ENTITY_DATA` (packet lines 15–17, predicate 109–110).

`Entity` assigns sprinting/swimming/fall-flying to shared-byte bits 3/4/7 (1.19 and 1.19.3 lines 192–200); `isSprinting()` reads bit 3. The 1.19 and 1.19.3 `LivingEntity.travel` water branch selects `0.9F` when sprinting and otherwise `getWaterSlowDown()` (`0.8F`), then applies that factor to horizontal movement. The fall-flying travel branch also reads `isFallFlying()`.

The water witness is reachable with equal forward input, adequate food, underwater, nonzero forward impulse, no sprint key, and ordinary clear/non-special movement conditions. Respawn initializes the new `LocalPlayer.input` later in the handler; its constructor stores the passed old sprint value in `wasSprinting`, not the shared sprint flag. On the next `LocalPlayer.aiStep`, input is ticked before sprint handling; a nonsprinting underwater player without sprint held starts the seven-tick sprint delay, while a preserved sprint flag is not cleared for forward movement with food in water. `LivingEntity.travel` then observes the different sprint flag. This supports the stated source-level horizontal-velocity consequence. Keep claims limited to player movement effects of the copied shared flags; the whole entity-data copy also contains excluded nonmovement data. Do not infer server-side keep-bit production or reachability frequency from this evidence.
