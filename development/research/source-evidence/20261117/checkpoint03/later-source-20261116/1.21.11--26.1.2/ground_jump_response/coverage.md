# Ordinary local-player ground jump response: bounded no-difference coverage

## Scope and result

Bounded **NO_DIFF** for the ordinary, non-flying, non-passenger, dry-ground local-player jump path from exact 1.21.11 Mojmap to exact 26.1.2 unobfuscated source. The checked key-state consumer, jump-delay gate, jump-power formula, vertical-velocity write, sprint-jump impulse, jump-strength default, and generic block-jump-factor lookup are semantically unchanged. This report does not establish full-pair coverage or an intermediate release boundary.

The bounded precondition is a controlled local player, alive/able to simulate, not using the mayfly toggle, outside water/lava jump handling, grounded, with `noJumpDelay == 0`. The player's jump key sets the inherited `jumping` state; the normal ground branch calls inherited `LivingEntity.jumpFromGround()`.

## Exact source identities

- 1.21.11 Mojmap: source root `decompiled_minecraft/1.21.11/mojmap`; ready marker `mojmap.ready.json` SHA-256 `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`; provenance `mojmap.provenance.json` SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`; source manifest `mojmap.sources.sha256` SHA-256 `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`; artifact manifest `artifacts.sha256` SHA-256 `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C`. Ready marker identifies version `1.21.11`, mapping `mojmap`, client JAR SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- 26.1.2 unobfuscated: source root `decompiled_minecraft/26.1.2/unobfuscated`; ready marker `unobfuscated.ready.json` SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; provenance `unobfuscated.provenance.json` SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; source manifest `unobfuscated.sources.sha256` SHA-256 `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; artifact manifest `artifacts.sha256` SHA-256 `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`. Ready marker identifies version `26.1.2`, mapping `unobfuscated`, client JAR SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`.

For every Java source listed below, its SHA-256 was checked against the endpoint source manifest and local bytes.

## Input, gate, and call path

- `net/minecraft/client/player/LocalPlayer.java`: 1.21.11 `applyInput` lines 652–665 and 26.1.2 lines 691–705 set `jumping` from `input.keyPresses.jump()` for the controlled camera, otherwise defer to the superclass. Both `aiStep` bodies snapshot jump input, tick input, process the same normal jump key state, and call `super.aiStep()` (1.21.11 lines 738–747, 875; 26.1.2 lines 777–786, 914). The distinct mayfly flight-toggle invocation is excluded by the stated non-mayfly precondition.
- `net/minecraft/world/entity/player/Player.java`: both `aiStep` implementations decrement `jumpTriggerTime`, then call `super.aiStep()` before their later player work (1.21.11 lines 451–464; 26.1.2 lines 442–454). `isAffectedByFluids()` returns `!abilities.flying` at both endpoints (1.21.11 lines 884–886; 26.1.2 lines 875–877), so the stated non-flying player enters the same inherited jump branch.
- `net/minecraft/world/entity/LivingEntity.java`: both `aiStep` methods decrement `noJumpDelay` first, dispatch `applyInput`, clear jump only when immobile, and then run the same jump decision. For the dry grounded case, the water/lava guards fall through; `onGround()` with `noJumpDelay == 0` calls `jumpFromGround()` and sets the delay to 10. Exact regions: 1.21.11 lines 2877–2957; 26.1.2 lines 2976–3056. The key state and ordering are unchanged.

## Jump response and modifiers

- `net/minecraft/world/entity/LivingEntity.java`: `getJumpPower()` supplies `1.0F`; the overload computes `(float)getAttributeValue(JUMP_STRENGTH) * multiplier * getBlockJumpFactor() + getJumpBoostPower()` in that operation order. `getJumpBoostPower()` adds `0.1F * (amplifier + 1.0F)` when Jump Boost is present. `jumpFromGround()` proceeds unless jump power is `<= 1.0E-5F`, writes Y as `max(jumpPower, priorY)` while preserving X/Z, adds the same sprint impulse `(-sin(yawRadians)*0.2, 0, cos(yawRadians)*0.2)` when already sprinting, then marks `needsSync`. These bodies are semantically identical: 1.21.11 lines 2256–2280; 26.1.2 lines 2339–2363.
- `net/minecraft/client/player/LocalPlayer.java`: `canStartSprinting` and `isSprintingPossible` have the same restrictions and order at 1.21.11 lines 1096–1109 and 26.1.2 lines 1135–1148. The ground-jump impulse itself still reads the same current sprint state; there is no changed sprint-start/stop rule within this checked route.
- `net/minecraft/world/entity/ai/attributes/Attributes.java`: `JUMP_STRENGTH` is registered with default `0.42F` in both sources at lines 48–50. `LivingEntity.createLivingAttributes()` includes this attribute at 1.21.11 lines 325–346 and 26.1.2 lines 318–340; `Player.createAttributes()` builds on it at 1.21.11 lines 211–225 and 26.1.2 lines 205–219.
- Block-modifier plumbing is also unchanged. `Entity.getBlockJumpFactor()` reads the block at the entity position and the block below affecting movement, choosing the below factor only when the current factor equals `1.0` (1.21.11 lines 957–987; 26.1.2 lines 986–1018). Its `getOnPos`/support-position helper branches match (1.21.11 lines 961–982; 26.1.2 lines 990–1013). `Block.getJumpFactor()` returns the stored `jumpFactor` at 1.21.11 lines 471–473 and 26.1.2 lines 510–512.

## Cited source hashes

| Canonical relative source path | 1.21.11 Mojmap SHA-256 | 26.1.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/level/block/Block.java` | `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` |

## Limits and next action

The `jumpFromGround` and ordinary dry-ground input path show no source delta in this endpoint comparison. This does not compare fluid jumping, passenger/vehicle jump protocols, mayfly flight-toggle launching, auto-jump obstacle probes, every block's configured jump factor, equipment/attribute producer paths, or any intermediate release. The exact first changed release is not inferred. Independent audit is required; full-pair coverage, implementation readiness, and runtime parity remain open.
