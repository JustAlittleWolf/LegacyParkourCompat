# Conditional LocalPlayer route boundaries — Mojmap 1.15.2 through 1.16.5

## Scope and bound inputs

Boundary audit of the accepted conditional opened-Shulker escape response and fall-flying request climbable gate from `../remaining-localplayer-member-inventory/report.md` (SHA-256 `5278c1ba1283bf289c46751a192c975ffcbfc6e2129eeb044ac6c9a7f1361545`) and its independent review at `../localplayer-residual-independent-review/review.md` (SHA-256 `adba017de4d4be46231c539b248171d553c91b34f56dec4e1bf206d25f107d82`). Exact Mojmap roots checked: 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5. Claims remain method- and gate-bounded. The climbable claim stays conditional on effective tag membership; this audit does not infer default membership or add a witness.

## Source publication and integrity

For 1.15.2, 1.16, 1.16.1, 1.16.2, and 1.16.5, root `mojmap.ready.json` identifies the exact version, Mojmap, ready state, and source count; the actual root `mojmap.sources.sha256` hash equals the ready record's source-manifest hash. The source count and manifest hash are:

| Release | Ready identity | SHA-256 of root `mojmap.sources.sha256` |
|---|---|---|
| 1.15.2 | `1.15.2 / mojmap / ready / 3332 files` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `1.16 / mojmap / ready / 3572 files` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `1.16.1 / mojmap / ready / 3572 files` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `1.16.2 / mojmap / ready / 3516 files` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | `1.16.5 / mojmap / ready / 3523 files` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

For 1.16.3 and 1.16.4, authoritative exact-release identity is embedded at `mojmap/.decompile-provenance.json`, using the `lpc-decompile-v1` pipeline. Embedded provenance SHA-256 values are `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` (1.16.3) and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` (1.16.4). The exact input metadata, client, and published client-mapping SHA-256 values are:

- 1.16.3: metadata `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`, client `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, mapping `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4: metadata `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`, client `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, mapping `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

Every cited file was rehashed and matches its own root source-manifest entry for 1.15.2, 1.16, 1.16.1, 1.16.2, and 1.16.5. For 1.16.3 and 1.16.4, every cited file matches its same-release embedded `sources` map. Relevant full-file SHA-256 fingerprints, grouped when identical:

| File | 1.15.2 | 1.16 and 1.16.1 | 1.16.2–1.16.5 |
|---|---|---|---|
| `client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `world/level/block/ShulkerBoxBlock.java` | `de5e19cffb75499b509bc361fcfb3dbe0b0fa7ab490ac42adedf89466721b02d` | `9c8ef886c1c737ba53c810a92b589973eb09cf280206030d64463aac89739435` | `a88a18bc4c4299876053f9ec38987861fa81acf3a38289a49cc3dbee04b940d8` |
| `world/level/block/Blocks.java` | `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` |
| `world/level/block/entity/ShulkerBoxBlockEntity.java` | `fb4742271c97668118d954c24ce43d0828755947c9ca5c642be01f4ffe589517` | `4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322` | same as 1.16 and 1.16.1 |
| `tags/BlockTags.java` | `2e6248215ac4075d899c71b419c1c81a1c868b7d0725b06052c92644004fa97f` | `7f506e2386851a128c12a449683b6f4fe1d0007fe2e74524116df73c8db30a7d` | `99f23e86191ba49033800be965341c4408b002404e9f403e3f4d42f962f1e88b` |
| `world/level/block/state/BlockBehaviour.java` | absent as a standalone file in this release | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` |
| `world/level/block/state/BlockState.java` | `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21` | — (state method is in `BlockBehaviour.java`) | — (state method is in `BlockBehaviour.java`) |

## Route 1: opened-Shulker local escape response

There are two staggered boundaries: the **conditional Shulker suffocation provider changes in 1.16**, and the **LocalPlayer caller changes from `checkInBlock` to `moveTowardsClosestSpace` in 1.16.2**. The accepted open-box response witness's first suppressed response is already supported by the 1.16.0 caller/provider combination. The 1.16.2 caller form and conditional registration persist through 1.16.5.

- In 1.15.2, `LocalPlayer.checkInBlock` (`LocalPlayer.java:385–429`) point-samples `blocked` (`:431–443`) for each of the four call sites in `aiStep` (`:650–655`). `blocked` scans vertical block cells and calls inherited `Player.freeAt`; `freeAt` evaluates `BlockState.isSuffocating`, which dispatches to the block implementation (`BlockState.java:268–269`). `ShulkerBoxBlock.isSuffocating` unconditionally returns `true` (`ShulkerBoxBlock.java:62–64`), and `Blocks` registers each Shulker directly with the block class (`Blocks.java:1563–1643`). Thus the reviewed open-box witness is reported as blocking by the old route.
- In 1.16 and 1.16.1, `LocalPlayer.checkInBlock` and the four calls remain (`LocalPlayer.java:414–458`, `:678–685`), and `Player.freeAt` still asks the block state for `isSuffocating` (`Player.java:1421–1423`). The override is removed from `ShulkerBoxBlock`; instead, `Blocks.shulkerBox` installs a state predicate for both suffocation and view-blocking (`Blocks.java:3172–3184`). The predicate returns true when the block entity is not a Shulker box, otherwise returns `ShulkerBoxBlockEntity.isClosed()` (`ShulkerBoxBlockEntity.java:290`). Therefore an open box with its matching block entity evaluates false on the old `checkInBlock` path already in 1.16.0. This is the earliest audited release supporting suppression of the accepted open-box escape response.
- From 1.16.2, the four call sites use `moveTowardsClosestSpace` (`LocalPlayer.java:657–660`) and its new `suffocatesAt` helper (`:443–447`) instead of `checkInBlock`/`blocked`. The helper uses the player's vertically clipped block-column box and filters collisions with `state.isSuffocating`. `Blocks.shulkerBox` retains the same conditional predicate at `Blocks.java:3224–3236`; the methods and registration persist unchanged by source hash through 1.16.5.

The accepted witness remains conditional on the specified loaded open Shulker block entity and surrounding air. This audit does not claim that every Shulker state or all suffocation paths changed the same way. The concrete open-box response boundary is no later than 1.16; the new LocalPlayer search method has the later 1.16.2 boundary.

## Route 2: fall-flying request climbable gate

**First audited release using the new `!onClimbable()` gate: 1.16. It persists through 1.16.5.** The gate change is conditional on differences between effective `CLIMBABLE` tag membership and the old explicit ladder/vine/scaffolding set; default effective membership is not asserted.

- In 1.15.2, the LocalPlayer fall-flying request gate is `... && !this.onLadder()` (`LocalPlayer.java:715`). `LivingEntity.onLadder` rejects spectators and otherwise accepts explicit `LADDER`, `VINE`, or `SCAFFOLDING` blocks, plus its aligned open trapdoor-on-ladder case (`LivingEntity.java:1226–1251`).
- Starting in 1.16, the same request uses `... && !this.onClimbable()` (`LocalPlayer.java:750` in .0/.1; `:725` in .2–.5). `LivingEntity.onClimbable` checks `BlockTags.CLIMBABLE`, then the aligned open trapdoor case, and records `lastClimbablePos` on success (`LivingEntity.java:1361–1378` in .0/.1; `:1363–1380` in .2–.5). `BlockTags.CLIMBABLE` is bound as a named tag at `BlockTags.java:79` from 1.16 through 1.16.5. These forms and the request gate persist across the checked releases.
- The request remains behind the accepted jump-state, ability-flight, passenger, equipped-Elytra, and enabled-Elytra gates. The only claim here is that the climb predicate used by this local request changed. Whether that changes request eligibility depends on the effective tag contents at the feet block and cannot be resolved from these Java source maps alone. No tag-only state is claimed as a default witness.

## Conclusions

The opened-Shulker witness's provider condition first appears in 1.16 and applies through the still-old `checkInBlock` path; `moveTowardsClosestSpace`/`suffocatesAt` replaces that method in 1.16.2 and persists through 1.16.5. The fall-flying request switches from `!onLadder()` to `!onClimbable()` in 1.16 and keeps that gate through 1.16.5, with effect conditional on effective tag membership. These are bounded operation results, not pair freeze or runtime parity.
