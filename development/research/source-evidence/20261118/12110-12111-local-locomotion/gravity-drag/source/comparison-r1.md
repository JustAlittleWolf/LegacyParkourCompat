# 1.21.10 to 1.21.11 ordinary dry local travel: bounded comparison

## Inputs and source identity

Compared only the canonical Windows source trees `decompiled_minecraft/1.21.10/mojmap` and `decompiled_minecraft/1.21.11/mojmap`, under Mojmap. Both exact `mojmap.ready.json` sidecars identify the requested release and mapping, report Vineflower with Java 25, and name the same artifact-manifest digest (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`). Their client SHA-256 values agree with the client pins in each matching `mojmap.provenance.json` and with the corresponding exact-pair resource manifest: 1.21.10 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; 1.21.11 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.

Revision binding: this corrected revision preserves the bounded comparison and verdict from `comparison.md`, whose raw SHA-256 is `570200e4ed673fd5164cdd7172369ec4ddd5748c7245c6bf72d54994992dcd2b`.

The version-root `decompiled_minecraft/1.21.10/mojmap.sources.sha256` manifest hashes to `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching the 1.21.10 ready sidecar; `decompiled_minecraft/1.21.11/mojmap.sources.sha256` hashes to `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, matching the 1.21.11 ready sidecar. For each manifest, the `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java` rows match the raw SHA-256 of the consumed files listed below. This binds the reviewed source bytes to the ready manifest identities.

Consumed canonical source hashes (SHA-256):

- 1.21.10 `net/minecraft/world/entity/Entity.java`: `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`
- 1.21.10 `net/minecraft/world/entity/LivingEntity.java`: `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`
- 1.21.10 `net/minecraft/client/player/LocalPlayer.java`: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`
- 1.21.11 `net/minecraft/world/entity/Entity.java`: `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- 1.21.11 `net/minecraft/client/player/LocalPlayer.java`: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`

## Compared path and verdict

For the ordinary local player path, `LocalPlayer.aiStep` calls `super.aiStep`; `LivingEntity.aiStep` builds the travel input and calls `travel` when movement simulation is enabled and the entity is not in the ridden branch (1.21.10 `LocalPlayer.java:687-834`, `LivingEntity.java:2840-2849`; 1.21.11 `LocalPlayer.java:728-875`, `LivingEntity.java:2960-2975`). With no fluid travel, no fall flying, and no vehicle, `LivingEntity.travel` selects `travelInAir` (1.21.10 `LivingEntity.java:2275-2283`; 1.21.11 `LivingEntity.java:2309-2317`). This scopes the comparison to direct dry travel and leaves water/lava and special flight branches outside this finding.

**No difference found in the assigned operation.** In both versions, `getDefaultGravity` reads the gravity attribute. `getEffectiveGravity` first checks `deltaMovement.y <= 0.0`; only then does slow falling cap gravity with `Math.min(getGravity(), 0.01)`, otherwise it uses `getGravity()` (1.21.10 `LivingEntity.java:2265-2273`; 1.21.11 `LivingEntity.java:2299-2307`). `Entity.getGravity` first returns zero for `isNoGravity()`, otherwise delegates to `getDefaultGravity`; base `applyGravity` adds `(0,-gravity,0)` only when gravity is nonzero (1.21.10 `Entity.java:1352-1372`; 1.21.11 `Entity.java:1365-1391`). No changed effect gate, value, or operation order is present.

Both `travelInAir` bodies perform the same sequence and use the same expressions: select below-block friction only while on ground (else `1.0F`); multiply it by `0.91F`; call `handleRelativeFrictionAndCalculateMovement(input, friction)`; select levitation's vertical target with `(0.05 * (amplifier + 1) - movementY) * 0.2`; otherwise subtract effective gravity when server-side or the relevant client chunk is loaded; when the client chunk is unloaded, use `-0.1` above min Y and `0.0` at/below min Y; then either preserve the computed axes when `shouldDiscardFriction()` or multiply X/Z by friction-times-`0.91F` and Y by `0.98F` (or that horizontal friction for `FlyingAnimal`). Compare 1.21.10 `LivingEntity.java:2306-2329` with 1.21.11 `LivingEntity.java:2343-2366`. The unloaded-chunk guard and both fallback values are unchanged. No positive finding is warranted.

## Limits

This is a bounded source comparison, not full-pair tick coverage, independent audit, or runtime parity evidence. It does not cover fluid movement, fall-flying, riding, collision response, movement input generation, effects lifecycle, or the separate velocity reset/snap contract. Consumed file hashes are verified against the version-root source manifests. This remains bounded source evidence and does not establish full-pair coverage or runtime parity.