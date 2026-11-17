# Riptide passenger release consequence gate

## Bounded verdict

For positive Riptide strength, a vanilla local player can start charging and execute the release branch while a passenger in 1.21.11 if it is in water or rain and the trident passes `nextDamageWillBreak()`. The unchanged `use` gate rejects dry Riptide use only; neither `use`, `startUsingItem`, nor the 1.21.11 release gate rejects passenger status. The local release path invokes `TridentItem.releaseUsing` directly. The branch writes impulse to the player with `Player.push` / inherited `Entity.push`, activates spin state, and, when `onGround`, invokes the player's `move(MoverType.SELF, ...)`. These are direct local-player operations, so the 1.21.11 passenger case contains an eligible player movement response, not just vehicle movement or external-entity physics.

The durable tick result while riding is qualified by the ordinary passenger tick: `Minecraft.tick` handles keybindings before `level.tickEntities`; `ClientLevel.tickPassenger` calls `rideTick`; inherited `Entity.rideTick` first sets passenger delta movement to zero, ticks it, then positions it from the vehicle. Thus the release does execute direct player movement operations, but riding tick behavior can clear the added velocity and reset position in that same client tick. The source establishes direct response execution and its subsequent rider reset; it does not establish a surviving displacement/velocity after the full tick for every vehicle, collision state, or ordering-dependent branch. The grounded self-move is likewise an explicit player move before rider placement, not movement of the vehicle.

In 26.1, the positive-strength release branch is admitted only by `player.isInWaterOrRain() && !player.isPassenger()`. A wet local passenger therefore returns before the Riptide push, spin activation, and grounded self-move. This is the checked changed endpoint, not a proven first release; the transition remains unresolved between 1.21.11 and 26.1.

## Side effects separated from movement

In both endpoint bodies, durability damage and non-Riptide trident creation/consumption are under `level instanceof ServerLevel`. The local client's `Level` does not satisfy that gate, so those server-side item/entity effects do not produce the local Riptide movement. The positive-strength movement block follows outside that gate and executes locally in 1.21.11 for a wet passenger. The 26.1 passenger rejection precedes both the server-only block and movement block. For zero Riptide strength, the passenger condition is bypassed in 26.1; ordinary server-side throw effects remain a separate path and are not a passenger Riptide response.

`startAutoSpinAttack` also stores attack/spin state. Its later contact/damage consequences are not counted as movement evidence here. Sound/stat calls and server synchronization do not replace the local `push`/`move` calls.

## Source dependencies

The terminal inputs were checked by byte hash: original accepted boundary packet SHA-256 `7D56DA434B0927664456C77E6DC5D7A6CA829A1D856F434F0654C9ACFCA16F74`; passenger boundary follow-up SHA-256 `AB412654F38B2930212D31BA2223CD3A822258FCFBEF98AE68F343E79E9B6978`. Endpoint manifest and mapping identities are retained in those inputs; no source generation was performed.

| Release / mapping | Source member and relevant range | Source SHA-256 |
|---|---|---|
| 1.21.11 Mojmap | `net/minecraft/world/item/TridentItem.java`, `use` 125–139 and `releaseUsing` 73–121; passenger predicate absent, local movement after `ServerLevel` block | `35207D86BCC415D485ACDF774716D3DA2C7A7AAE8F2C8FF42A0EA96E5AD7CEBC` |
| 1.21.11 Mojmap | `net/minecraft/client/Minecraft.java`, `tick` 1790–1812 and `handleKeybinds` 1963–1967; local release before client entity ticking | `E41D192579972EA043CF39AD7F754E1279DCA63AA6A576EDCEB4C9928D685D59` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, `releaseUsingItem` 494–498; sends release packet then calls local player's release | packet follow-up supplies exact range; source file not separately needed for branch semantics |
| 1.21.11 Mojmap | `net/minecraft/world/entity/LivingEntity.java`, `releaseUsingItem` 3435–3445 and `startUsingItem` 3335 onward; calls active stack release and has no passenger gate | `19ED2D565858C401C69A06750B054A633A20EA864CAB3A753B5C767F0BDD60E8` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/Entity.java`, `move` 685 onward, `push(double,double,double)` 1775–1778, `rideTick` 2254–2259, and `setDeltaMovement` 3642–3647 | `32314478C6036FA9F3F3CC409C61C622EEFC5A282D60E1011A1A33CC29CF18A3` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java`, `tickPassenger` starting at 358; invokes passenger `rideTick` | `E76D09DE5ACAD450062D5ACB98E76FF032ECB179FEF0F7AF434E4148F84183A2` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/player/Player.java`, `startAutoSpinAttack` 520–526; spin fields are distinct from impulse/move | `8E97167350A91741D0AA10D3B0D92A33150ED6DCCDC94CD5B37D9C7CA22BCC81` |
| 26.1 unobfuscated | `net/minecraft/world/item/TridentItem.java`, `use` 129–140 and `releaseUsing` 66–123; passenger-aware gate precedes server block and movement | `F8278EFC3C44C7F27BCAEA398F2E029EC369CA94E1EFD552A7FEBCA5A47EBAC3` |
| 26.1 unobfuscated | `net/minecraft/client/Minecraft.java`, `tick` and `handleKeybinds`; local release path follows same key-release flow | `8B072DF57A6EFB47BCB4F5B2AC1BB750ED8BFC7B9441B1894167A7B8C046B4B1` |
| 26.1 unobfuscated | `net/minecraft/world/entity/LivingEntity.java`, `releaseUsingItem` 3546–3556 and `startUsingItem` 3442 onward | `C3B64DE8DBABA8AD8A7CCD4F33255346D8206E66F12CA91260971BF5E5AC93BD` |
| 26.1 unobfuscated | `net/minecraft/world/entity/Entity.java`, `move` 704 onward, `push(double,double,double)` 1826–1830, `rideTick` 2314–2318, `setDeltaMovement` 3668 onward | `8B83B1F036AABBD13D990897C540C993F7120F02955486CFCF229517D4097CCF` |
| 26.1 unobfuscated | `net/minecraft/client/multiplayer/ClientLevel.java`, `tickPassenger` starting at 359 | `6E46DB964229BF53BB88C33140CFA9ACB5423DBED11A7C721B5B8BF9A7424DE7` |
| 26.1 unobfuscated | `net/minecraft/world/entity/player/Player.java`, `startAutoSpinAttack`; spin state is separate from direct movement | `44CF28E0C64E78D39FD13368E9991381DBEBAB67029070CB9DDC43F09D45D14D` |

## Scope and limits

Source-only endpoint consequence gate. The 1.21.11 and 26.1 TridentItem identities and endpoint manifest hashes are those in the passenger follow-up. Passenger-specific consequences are established by the exact local call, direct player operations, and rider tick source above. No source pair was frozen, no exact intermediate boundary was assigned, and no product implementation, backlog, wiki, Git history, build, test, runtime, or source generation was consulted or changed.
