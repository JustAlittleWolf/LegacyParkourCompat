# Powder snow frost alias comparison

Scope: exact local Mojmap sources for `1.17`, `1.17.1`, `1.18`, `1.18.2`, `1.19`, `1.19.3`, `1.19.4`, `1.20`, and `1.20.2`. This is a bounded comparison of post-travel freeze state / frost-speed modifier processing, its non-air block gate, the local next speed read, and source-defined frozen metadata. It does not establish behavior for unlisted releases.

## Source publication identity and integrity

All roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap`. Releases use official Mojang client mappings (`mojmap`). For every row, the client and official mapping SHA-256 pins are from the exact-version publication record; the three source-file SHA-256 values were independently recomputed from the published files and match the corresponding complete-source manifest (or mapping-root provenance `sources` object for 1.17 and 1.20). The relevant file hashes bind the complete decompiled files containing each cited method, not extracted fragments.

| Release | Client SHA-256 | Official mapping SHA-256 | LivingEntity.java SHA-256 | Entity.java SHA-256 | LocalPlayer.java SHA-256 |
|---|---|---|---|---|---|
| 1.17 | `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f` | `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` |
| 1.17.1 | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` | `2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.18 | `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa` | `b4c7d514328050fa09ac04818d21e4be0b58e75c6b0847e56ce09f6637b8877b` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.2 | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` | `9e31de7fc15b1bac0e58abc441a5e56c320266112feaea981bcffd41dd6201e1` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.3 | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` | `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` | `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |
| 1.20 | `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff` | `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| 1.20.2 | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` | `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

Publication records: 1.17 and 1.20 carry the mapping-root `.decompile-provenance.json`; 1.17.1, 1.18, 1.18.2, 1.19, 1.19.3, 1.19.4 and 1.20.2 carry version-root `mojmap.ready.json`, `mojmap.provenance.json` and `mojmap.sources.sha256`. Exact `versionId` / metadata ID agrees with each table release. Each ready record's complete-source manifest hash and source count were present; the three consumed source files were recomputed against that manifest. Mapping and client pins are the exact per-version records, not a moving release alias.

## Shared operation through 1.19.4

Across 1.17–1.19.4, `LivingEntity.tick()` places `travel(...)` before the `freezing` profiler section. The freeze counter update in that section is guarded by `!level.isClientSide && !isDeadOrDying()`: `isInPowderSnow && canFreeze()` raises it with `min(required, n + 1)`, otherwise it lowers it with `max(0, n - 2)`. Both sides then call `removeFrost()` followed by `tryAddFrost()`.

`removeFrost()` gets `Attributes.MOVEMENT_SPEED`; if present, it removes the modifier identified by UUID `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce` when found. `tryAddFrost()` requires `!getBlockStateOn().isAir()` through 1.19 (the compatibility getter spelling is used in later versions), then `getTicksFrozen() > 0`, then a non-null movement-speed attribute. It computes `-0.05F * getPercentFrozen()` and adds a transient `AttributeModifier` with that same UUID, name `Powder snow slow`, and `AttributeModifier.Operation.ADDITION`. The formula and branch order are shared in all nine sources; local names and Mojmap source layout changes do not change those operations.

Through 1.19.4, `getBlockStateOnLegacy()` reads `getOnPosLegacy()`. That position uses `floor(x)`, `floor(y - 0.2F)`, `floor(z)`, with the existing fence/wall/fence-gate fallback one block below when the initial state is air. In 1.19.3/1.19.4 the old calculation is named `getOnPosLegacy()`; the public `getOnPos()` uses `1.0E-5F`. `tryAddFrost` explicitly continues using the legacy `0.2F` route.

## Eligible gate discontinuity in 1.20

In 1.20 and 1.20.2, `tryAddFrost()` still calls the legacy block-state getter and retains the same UUID, modifier name, transient addition operation, frozen-count test and float expression. However, `Entity` now has `mainSupportingBlockPos`, populated/cleared by `checkSupportingBlock()` from the on-ground path. When it is present, `getOnPosLegacy()` uses that support block's X/Z instead of flooring the entity's current X/Z; it derives Y from `floor(position.y - 0.2F)` except the existing fence/wall/fence-gate rule returns the support position. Without a main support, it uses the prior floor-X/Z calculation. The same support-aware implementation is present in both 1.20 and 1.20.2.

This changes the source-defined non-air predicate consumed by `tryAddFrost()` relative to 1.19.4 in cases where the main collision-support block and the block at the player's floored X/Z are different. On a reachable edge/partial-support position, the old gate can read air while the newer gate reads the support block, or vice versa depending on the exact support placement. The concrete effect is whether the existing frozen-count rule installs the speed modifier after travel; when installed, an ordinary subsequent speed read observes the modifier. Do not infer broader trajectory parity from this bounded state path.

## Client speed read and frozen metadata

`LocalPlayer.updateAutoJump()` reads `this.getSpeed()` once into a local before its short-displacement fallback calculations. Its `move()` calls `updateAutoJump()` after `super.move()`. `LivingEntity.getSpeed()` returns the movement-speed attribute value as a float in all checked releases. This is an ordinary local read; it does not reset, recreate, or otherwise override the powder-snow modifier. The read occurs during travel, before the post-travel freezing section installs the modifier, so that same tick's auto-jump read cannot observe the newly installed modifier. A subsequent ordinary speed read can observe it.

`Entity.DATA_TICKS_FROZEN` is an `EntityDataAccessor<Integer>` created with `SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT)` and initialized to zero. `getTicksFrozen()` and `setTicksFrozen(int)` read/write that accessor. In the checked `LivingEntity.tick()` path, local counter advancement is server-only, while `tryAddFrost()` on the client consumes the available frozen counter and `isInPowderSnow` state. The source therefore identifies the client input as entity-data frozen ticks plus the current block-contact state; this comparison does not claim a client-local counter production path. The accessor is typed/synchronized source metadata here; no numeric wire ID is asserted.

## Bounded source anchors

Exact source paths are relative to the roots above. Line numbers below are inclusive method/operation anchors; complete source-file hashes are in the identity table.

| Anchor | Releases and source location |
|---|---|
| Post-travel freezing section and ordered `removeFrost(); tryAddFrost();` calls | `LivingEntity.java`: 1.17 lines 2544–2557; 1.17.1 2548–2561; 1.18 2550–2563; 1.18.2 2554–2567; 1.19 2587–2600; 1.19.3 2601–2614; 1.19.4 2554–2567; 1.20 2576–2589; 1.20.2 2605–2618. |
| Modifier removal / addition methods | `LivingEntity.java`: 1.17/1.17.1 `removeFrost` 500–507, `tryAddFrost` 509–524; 1.18 500–507 / 509–524; 1.18.2 504–511 / 513–528; 1.19 506–513 / 515–530; 1.19.3 505–512 / 514–529; 1.19.4 500–507 / 509–524; 1.20 515–522 / 524–539; 1.20.2 516–523 / 525–540. |
| Legacy floor position before support-aware path | 1.19.4 `Entity.java` lines 720–742 (`getOnPosLegacy`, `getOnPos`, private calculation); `LivingEntity.java` line 509 gate. |
| Support-aware block gate introduced by 1.20 and retained in 1.20.2 | `Entity.java`: 1.20 lines 564–588 and 797–818; 1.20.2 lines 565–589 and 788–817. `LivingEntity.java`: 1.20 line 525 gate; 1.20.2 line 526 gate. |
| Ordinary client speed read | `LocalPlayer.java`: 1.17 line 911; 1.17.1 909; 1.18 907; 1.18.2 905; 1.19 989; 1.19.3 916; 1.19.4 896; 1.20 888; 1.20.2 887 (`updateAutoJump`). |
| Frozen metadata accessor | `Entity.java`: `DATA_TICKS_FROZEN`, `getTicksFrozen`, and `setTicksFrozen`; file path/hash in identity table. |

## Disposition

The modifier operation and post-travel ordering are shared across all nine checked releases. The state-selection implementation behind the non-air gate changes in 1.20 when a main supporting block is present. This is the only movement-eligible discontinuity identified in this bounded slice. Patch introduction is only bounded here as after 1.19.4 and by 1.20; the exact intermediate patch boundary was not checked.
