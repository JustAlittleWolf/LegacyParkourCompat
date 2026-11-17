# Independent acceptance: Riptide passenger consequence

Verdict: **ACCEPT, bounded to 1.21.11 and 26.1 endpoint source evidence.**

Reviewed immutable inputs:
- Consequence report `build/mcpk-20261116/vd-full-checklist/riptide-passenger-consequence/terminal.md`, SHA-256 `5b5a2b48644b6a13747dbbd80aec88b8bc945ef0ba3004408e9dbc6498682507`.
- Claim packet `build/mcpk-20261116/vd-full-checklist/riptide-postintro-boundary/terminal.md`, SHA-256 `7d56da434b0927664456c77e6dc5d7a6ca829a1d856f434f0654c9acfca16f74`.

## Independent checks

The canonical endpoint sources are present in ready-marked trees. `1.21.11/mojmap.ready.json` identifies version `1.21.11`, mapping `mojmap`, and source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. `26.1/unobfuscated.ready.json` identifies version `26.1`, mapping `unobfuscated`, and source manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeBA`. Both manifest hashes were recalculated from the exact manifest bytes. Both ready markers name the expected exact version/mapping; 26.1 provenance also identifies the published unobfuscated client artifact.

Direct SHA-256 checks matched the report for the 1.21.11 source files: `TridentItem.java` `35207d86bcc415d485acdf774716d3da2c7a7aae8f2c8ff42a0ea96e5ad7cebc`; `Minecraft.java` `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59`; `LivingEntity.java` `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; and `ClientLevel.java` `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2`. Direct checks also matched the report for 26.1 `TridentItem.java` `f8278efc3c44c7f27bcaea398f2e029ec369ca94e1efd552a7febca5a47ebac3`, `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`, `LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`, and `ClientLevel.java` `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7`. The 1.21.11 `MultiPlayerGameMode.java` direct hash is `4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0`; the report did not claim a separate hash for this path.

At 1.21.11, `TridentItem.use` checks breakage and positive-strength water/rain only, then calls `startUsingItem`; no passenger rejection occurs. `Minecraft.tick` handles keybindings before `level.tickEntities`; releasing use calls `MultiPlayerGameMode.releaseUsingItem`, which sends the release packet and calls the local player's `releaseUsingItem`. `LivingEntity.releaseUsingItem` calls the active stack's `releaseUsing`, dispatching to `TridentItem.releaseUsing`. The positive-strength branch's water/rain check and `nextDamageWillBreak` check do not test passenger status. It calls `Player.push`, `startAutoSpinAttack`, and, when grounded, `Player.move(MoverType.SELF, ...)`, all before the later server-only item/projectile effects. This verifies reachable direct local-player movement operations for a wet passenger in this endpoint.

The client entity tick then reaches riders through `ClientLevel.tickPassenger` and `rideTick`. `Entity.rideTick` sets delta movement to zero, ticks the passenger, then calls the vehicle's `positionRider` while it remains a passenger. This establishes a same-tick velocity clear and vehicle reposition after the release operations on that path. It does not establish the player's final post-tick velocity for every rider tick branch, nor any persistent post-tick trajectory. Do not infer one from the direct release operations.

At 26.1, the positive-strength gate in `TridentItem.releaseUsing` admits only `player.isInWaterOrRain() && !player.isPassenger()`. Its use/start gate still checks only water/rain and starts item use without a passenger check. Therefore a wet passenger can charge but returns before Riptide push/spin/self-move on release at this checked endpoint. The local release call path remains: `Minecraft.tick` handles keybindings before entity ticking; `MultiPlayerGameMode.releaseUsingItem` sends the packet and invokes `player.releaseUsingItem`.

## Limits and remaining gates

The changed endpoint fact is accepted, but the exact first release containing the passenger rejection remains **unresolved between 1.21.11 and 26.1**. Inspect exact intermediate releases before assigning a cutover. This endpoint review does not freeze the broader source pair or prove completeness of movement coverage. Persistent post-tick velocity/displacement is unproven; retain only the bounded statement that direct response operations execute in 1.21.11 and ordinary rider processing subsequently clears delta movement and repositions the rider on the checked path.

No product/backlog/wiki/Git history was consulted or modified. Canonical sources were read-only. No source generation, build, test, game launch, TAS, Gym, server or runtime check was performed.

