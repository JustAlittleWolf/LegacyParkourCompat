# Vanilla player input provider and registration closure (1.18.2–1.19.4)

Scope: independent inspection of only the exact local Mojmap trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The adjacent `mojmap.ready.json` and `mojmap.sources.sha256` were used only for provenance and selected-file verification. No product source, campaign output, wiki, history, build, test, game launch, or decompilation was consulted.

## Source bundle validation

For each release, the SHA-256 of the raw `mojmap.sources.sha256` file equals both the assignment digest and the `sourceManifestSha256` field in its ready marker. Manifest row count equals ready `sourceFileCount`; the selected Java files listed below were then SHA-256 checked against their individual manifest rows.

| Version | Manifest SHA-256 | Manifest rows = ready sourceFileCount |
| --- | --- | ---: |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | 4236 |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | 4430 |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | 4480 |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | 4480 |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4618 |
| 1.19.4 | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | 4740 |

## Findings

### Vanilla providers

A whole-tree `*.java` search in each exact Mojmap tree for `extends Input`, `implements Input`, and `new Input(` found exactly one concrete subclass/implementation: `net.minecraft.client.player.KeyboardInput` (`KeyboardInput.java:5`). `net.minecraft.client.player.Input` itself is a concrete base class (`Input.java:5`), with a no-op `tick` and the shared movement fields / vector predicates. No alternate vanilla provider, anonymous `Input` implementation, or direct `new Input()` site was found in any of the six releases.

The `KeyboardInput` constructor takes `Options` and retains it. Its `tick` reads `keyUp`, `keyDown`, `keyLeft`, `keyRight`, `keyJump`, and the shift key from that `Options` object; the same provider computes forward/left impulses and applies the slow-movement scale. No other vanilla class implements different input movement semantics.

### All vanilla assignments to `LocalPlayer.input`

A whole-tree Java assignment search, followed by inspection of each matching `ClientPacketListener` context, found exactly two vanilla assignments in every release:

| Release | Existing local player: `ClientPacketListener.handleLogin` | Replacement local player: `ClientPacketListener.handleRespawn` |
| --- | ---: | ---: |
| 1.18.2 | `ClientPacketListener.java:375` (method begins line 330) | `ClientPacketListener.java:960` (method begins line 910) |
| 1.19 | `ClientPacketListener.java:377` (method begins line 332) | `ClientPacketListener.java:998` (method begins line 948) |
| 1.19.1 | `ClientPacketListener.java:391` (method begins line 346) | `ClientPacketListener.java:1007` (method begins line 953) |
| 1.19.2 | `ClientPacketListener.java:391` (method begins line 346) | `ClientPacketListener.java:1007` (method begins line 953) |
| 1.19.3 | `ClientPacketListener.java:414` (method begins line 369) | `ClientPacketListener.java:1066` (method begins line 1006) |
| 1.19.4 | `ClientPacketListener.java:427` (method begins line 382) | `ClientPacketListener.java:1120` (method begins line 1060) |

Both sites assign a fresh `new KeyboardInput(this.minecraft.options)` to the player; neither substitutes a second provider. No other vanilla write to `LocalPlayer.input` occurs in the inspected source trees. The field is public, so this conclusion is limited to vanilla Mojmap source: arbitrary third-party bytecode, reflection, mixins, or runtime replacements are outside these source roots and cannot be bounded by this audit.

### Per-edge movement-input semantics

| Edge | Exact vanilla input API/behavior change |
| --- | --- |
| 1.18.2 → 1.19 | Changed. `Input.tick(boolean)` becomes `Input.tick(boolean, float)` (`Input.java:15`). `KeyboardInput` drops its `MOVING_SLOW_FACTOR = 0.3F` constant and multiplies impulses by the supplied second argument when slow movement is active (`KeyboardInput.java:21` in 1.19). `LocalPlayer.aiStep` supplies `Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` (`LocalPlayer.java:739–740`). In 1.18.2, the provider hard-codes 0.3 (`KeyboardInput.java:7, 22`) and `LocalPlayer.aiStep` calls `input.tick(this.isMovingSlowly())` (`LocalPlayer.java:656`). Thus sneaking-speed enchantment bonus changes slow input scaling beginning in 1.19.
| 1.19 → 1.19.1 | No provider/API/algorithm change. `Input.java` and `KeyboardInput.java` hashes are byte-identical to 1.19. `LocalPlayer.aiStep` retains the same dynamic multiplier call (line 746). |
| 1.19.1 → 1.19.2 | No provider/API/algorithm change. The three input source hashes are identical across this edge; assignment/tick structure is unchanged. |
| 1.19.2 → 1.19.3 | No provider/API/algorithm change. `Input.java` and `KeyboardInput.java` are unchanged; `LocalPlayer.aiStep` retains the same multiplier call (line 667). |
| 1.19.3 → 1.19.4 | No provider/API/algorithm change. `Input.java` and `KeyboardInput.java` are unchanged; `LocalPlayer.aiStep` retains the same multiplier call (line 659). |

The above comparison is limited to the vanilla input provider and its direct `LocalPlayer.aiStep` multiplier path; it does not assert that unrelated movement code is unchanged across these releases.

### Client tick reaches the registered provider's `LocalPlayer.aiStep`

The exact vanilla route is `Minecraft.tick` → `ClientLevel.tickEntities` → `ClientLevel.tickNonPassenger` (or `tickPassenger` → `Entity.rideTick`) → dynamic `LocalPlayer.tick` → `LivingEntity.tick` → dynamic `LocalPlayer.aiStep`. `ClientLevel.tickEntities` sends non-removed, non-passenger entities to `tickNonPassenger`, which invokes `Entity.tick`; passengers use `tickPassenger`, which invokes `rideTick` for players. `Entity.rideTick` itself invokes `this.tick()`. `LocalPlayer.tick` calls `super.tick()` when its current chunk is loaded. `LivingEntity.tick` calls `this.aiStep()`; from 1.19.1 onward this call is guarded by `!this.isRemoved()`. `LocalPlayer.isEffectiveAi()` returns `true` in all six releases. Therefore each vanilla `KeyboardInput` assigned by the two registration/replacement sites feeds `LocalPlayer.aiStep` on the ordinary, loaded and non-removed local-player tick path, including while riding. The statement is about vanilla-ticked player instances; it excludes periods where the local player is not ticked (for example, unloaded chunk / removed entity) and all external runtime transformations.

## Exact member/line references by release

* `Input`: class declaration `Input.java:5`; `tick` is line 15 in all releases; shared `getMoveVector` and `hasForwardImpulse` follow it.
* `KeyboardInput`: sole subclass declaration `KeyboardInput.java:5`; constructor is line 9 in 1.18.2 and line 8 from 1.19 onward; override `tick` starts at line 22 in 1.18.2 and line 21 from 1.19 onward.
* `LocalPlayer`: class declaration and `tick`, `aiStep`, `isEffectiveAi`, and `input.tick(...)` locations are listed with the hashes below. `LocalPlayer.java` extends `AbstractClientPlayer` in every release.
* Registration/replacement method declarations and assignment lines are tabulated above. Each RHS is exactly `new KeyboardInput(this.minecraft.options)`.
* Tick route line references: `Minecraft.java` has `this.level.tickEntities()`; `ClientLevel.java` has `tickEntities`, `tickNonPassenger`, and its `$$0.tick()` invocation; `Entity.java` has `rideTick` and `this.tick()`; `LivingEntity.java` has `tick` and `this.aiStep()`; `LocalPlayer.java` has `tick`, `aiStep`, and `isEffectiveAi`.

## Selected source hashes (each local SHA-256 equals its manifest row)

Rows show `path:line anchors — SHA-256`. These are source-file hashes, not ready-marker/provenance JSON hashes.

| Version | Input.java | KeyboardInput.java | LocalPlayer.java | ClientPacketListener.java |
| --- | --- | --- | --- | --- |
| 1.18.2 | `5,15` — `eb50a4e268ec5ff8423d2805499ca3c7bae33765cb44cfecef808e38fa6de3c3` | `5,9,22` — `281622f8481654035196a7bc1554d5251c1040518375e3ac6f6439e5ec894a75` | `83,183,492,642,656` — `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `330,375,459,910,960` — `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b` |
| 1.19 | `5,15` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `5,8,21` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `96,198,575,725,740` — `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` | `332,377,461,948,998` — `a67c7880c967e93322de0f2e30af0eb34e24a8b7df21171e2a37032b27a21671` |
| 1.19.1 | `5,15` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `5,8,21` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `98,200,581,731,746` — `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | `346,391,475,953,1007` — `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3` |
| 1.19.2 | `5,15` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `5,8,21` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `98,200,581,731,746` — `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | `346,391,475,953,1007` — `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3` |
| 1.19.3 | `5,15` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `5,8,21` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `86,188,493,652,667` — `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `369,414,504,1006,1066` — `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e` |
| 1.19.4 | `5,15` — `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `5,8,21` — `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `86,187,486,645,659` — `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `382,427,517,1060,1120` — `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715` |

| Version | ClientLevel.java | Entity.java | LivingEntity.java | Minecraft.java |
| --- | --- | --- | --- | --- |
| 1.18.2 | `195,212,216` — `e44cb1126b9b8093655892fb6ca17fa934cd79cc0453cee5e443969d8792b82e` | `1674,1676` — `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `2224,2263` — `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `1639` — `2b1df2c612e51ca5ca74955afbc92e9af76b61a2293b4507e7cb7540ee6f9a81` |
| 1.19 | `239,256,260` — `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` | `1699,1701` — `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `2257,2296` — `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `1702` — `f0f1a6de32d2ad91663d828c7fa9b22480bae99ff213e55bd99aeb7706ecd092` |
| 1.19.1 | `239,256,260` — `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` | `1699,1701` — `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `2257,2297` — `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `1722` — `d5dd36acbd97baa2a1c391cd267b61fcb50a4e56f9e54b282477e756f7c07578` |
| 1.19.2 | `239,256,260` — `7c16e6aced9e1faad377be20f3aa15ce68c213ebfae5f5011af2287f814676ab` | `1699,1701` — `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `2257,2297` — `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `1723` — `0ea0edb23f7b4144cc6a7a733064cbf719dbea8af3b083b897eb8bab57041f7c` |
| 1.19.3 | `241,258,262` — `8c8a6134a4409c899c098012e1a11e7eee736edac3403b89211f8ae8fa06df85` | `1704,1706` — `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `2264,2304` — `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `1752` — `92e87cdc3a11825fe2b4bc3364d4349976f90133f4a99914e3e9e30a85b41b9e` |
| 1.19.4 | `240,257,261` — `2347e2ad9ec309ec6df5078ccd9a4870d179b846dabb7a11c6b0603b7cc93a16` | `1714,1716` — `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `2219,2259` — `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `1771` — `7ea6ae69423f3658e4084c5371c13c29acec1c442166ff6f93144e748d6362b3` |
