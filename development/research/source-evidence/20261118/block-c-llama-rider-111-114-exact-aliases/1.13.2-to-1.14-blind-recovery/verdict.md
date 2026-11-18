# Independent blind audit: Llama passenger Y placement

## Verdict and bounds

**Accepted as a bounded no-difference source result.** The report accurately establishes equivalent direct passenger Y placement for the ordinary adult Llama across exact Minecraft 1.13.2 → 1.14 sources, including the default height input, operand order, player ride-height input and mount callback dispatch. The 1.14 Trader Llama subclass check is also supported. This verdict does not certify complete pair coverage, intermediate patch boundaries, or runtime parity.

Reviewed input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1132-114-position-blind-recovery/report.md`

Full report SHA-256: `4f49c4f04723b0c605f2fbd32f0b86ade6d2512bb8185186b2926e9f154ef546`.

## Exact source identity and integrity

- **1.13.2 / Ornithe Feather:** canonical local source tree `decompiled_minecraft/1.13.2/ornithe-feather/`; ready marker identifies version `1.13.2` and mapping `ornithe-feather`, with `feather-gen2-1.13.2+build.2`. Ready-marker SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Relevant source hashes match the manifest: `LlamaEntity.java` `5be437bcd5600c4dbcd7ef5e5224ac31baa69efbd69b11152b0bc04081922089`; `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `PlayerEntity.java` `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- **1.14 / Ornithe Feather:** canonical local source tree `decompiled_minecraft/1.14/ornithe-feather/`; ready marker identifies version `1.14` and mapping `ornithe-feather`, with `feather-gen2-1.14+build.2`. Ready-marker SHA-256 `e0ecb319a63d5d664b87ad7c00638e1cdb9dc302ee2c05bbc05a88ce96d1e60b`; provenance SHA-256 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; source-manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`. Relevant source hashes match the manifest: `LlamaEntity.java` `17190fe664a4284a4673efbcb4438906e4de1fcaa562d20e09b7b4b2ce7b3b46`; `Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`; `PlayerEntity.java` `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`; `EntityType.java` `f3aa0bbf209cd6e2bfd31869dbb292b063efd1aa3e9719bb0adf999d2f3e7017`.
- **Trader Llama subclass check:** local 1.14 Yarn source records Yarn `1.14+build.21` in `decompiled_minecraft/1.14/yarn/.decompile-provenance.json`; this provenance file has SHA-256 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`. Its entry for `net/minecraft/entity/passive/TraderLlamaEntity.java` matches the source SHA-256 `985323d40a7f35d80cc0f741c747e007c7a07e56e1332d9d1d4dfdb10b466ac5`. This side check is separate from the canonical Feather pair comparison.

## Claim review

- In 1.13.2 Feather `LlamaEntity.java:146–163`, `updateRiderPositon` gates on `hasPassenger(rider)` and calls `rider.setPosition` with Y `this.y + this.getMountHeight() + rider.getRideHeight()`. Its `getMountHeight` returns `this.height * 0.67`; `Entity.java:132` declares `height` as `float`. The Llama constructor at `LlamaEntity.java:61–64` sets dimensions to `0.9F, 1.87F`.
- In 1.14 Feather `LlamaEntity.java:149–165`, the same passenger gate and Y expression occur in the same order. `getMountHeight` uses `this.getHeight() * 0.67`; `Entity.java:2571–2573` returns `dimensions.height` as `float`. `EntityType.java:233` supplies the default Llama dimensions `0.9F, 1.87F`. For the stated default adult instance, both height operands are the same float value; multiplication by the double literal and the later Y additions retain the same Java promotion and left-to-right order.
- Both Feather `PlayerEntity` sources return the same `double` ride-height offset `-0.35` (`1.13.2:980–982`, `1.14:950–952`). Both Llama implementations return `false` from `canBeControlledByRider`.
- Callback order/authority is correctly described: 1.13.2 `Entity.java:1583–1586` ticks the rider and then, if still riding, calls `entity.updateRiderPositon(this)` on its current mount reference. 1.14 `Entity.java:1519–1525` likewise ticks, checks riding state, and calls `this.getMount().updateRiderPositon(this)`. These are virtual calls; the actual Llama passenger reaches the Llama override and its `hasPassenger` guard.
- The exact 1.14 Yarn `TraderLlamaEntity.java:20–25` extends `LlamaEntity`. The file contains no `updateRiderPositon` or `canBeControlledByRider` override, so the checked Llama placement and rider-control methods are inherited.

No claim in the reviewed report requires correction within this assigned slice. The report’s explicit bounds are appropriate: this audit does not close the full 1.13.2–1.14 movement inventory or establish runtime behavior.
