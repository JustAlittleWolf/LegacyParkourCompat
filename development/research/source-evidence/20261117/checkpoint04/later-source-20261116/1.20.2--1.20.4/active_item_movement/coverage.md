# Active-item movement slowdown — 1.20.2 → 1.20.4

## Scope and verdict

**Source coverage: NO_DIFF for the assigned active-item-use movement-slowdown operation.** The bounded behavior is the local-player input slowdown and sprint-trigger reset when active item use is in progress and the player is not a passenger. Both exact Mojmap sources implement the same gate, fields, multiplier, reset, lifecycle state, and route into ordinary `LivingEntity.travel`. This is a static source comparison; it is not runtime parity or full-pair coverage.

Included: `LocalPlayer.aiStep` active-item movement gate; LocalPlayer active-use state writers/readers and synchronized flag transition; the `LivingEntity` use-state refresh/stop path; the player AI/travel handoff needed to establish where the scaled impulses go.

Excluded: input key sampling and vector construction, all other tick/travel branches, item-use effects/outcomes, attributes/enchantments, food/stamina, resources, runtime behavior, and the rest of pair coverage. No claim is made about item actions or server-side movement.

## Exact source identities

For each version, the ready marker reports `status=ready`, exact `versionId`, and `mapping=mojmap`; provenance independently reports the same version ID and namespace. The marker's declared source-manifest and artifact-manifest digests match the actual sidecar bytes.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Marker bindings |
|---|---|---|---|---|---|
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | Both match |
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | Both match |

Sidecars are at `decompiled_minecraft/<version>/mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`; Java paths below are relative to `decompiled_minecraft/<version>/mojmap/`. Each cited Java file's actual SHA-256 matched its entry in that version's source manifest.

| Java source | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |

## Paired operation and call order

`LocalPlayer.aiStep()` first calls `this.input.tick(...)` and the tutorial input callback, then at lines 666–670 in both releases evaluates:

```java
if (this.isUsingItem() && !this.isPassenger()) {
    this.input.leftImpulse *= 0.2F;
    this.input.forwardImpulse *= 0.2F;
    this.sprintTriggerTime = 0;
}
```

The operation scales the already-ticked lateral/forward input impulses by exactly `0.2F`; it does not directly scale movement speed or the current movement delta. Both predicates are required: no slowdown when `isUsingItem()` is false, and no slowdown while a passenger. The same branch clears `sprintTriggerTime`. The complete `LocalPlayer.java` files have identical hashes across endpoints, so the operation and its immediate surrounding local-player state logic are byte-identical.

`LocalPlayer.isUsingItem()` returns `startedUsingItem`. Its `startUsingItem(hand)` override accepts a nonempty held stack only when not already using, calls the superclass, then sets `startedUsingItem=true` and records the hand; `stopUsingItem()` calls the superclass and clears the local flag. `onSyncedDataUpdated` decodes the using-item and off-hand bits from `DATA_LIVING_ENTITY_FLAGS`, starting or stopping local use when synchronized state changes. These methods are identical at both endpoints (`LocalPlayer.java` lines 492–528).

`LivingEntity.tick()` invokes `updatingUsingItem()` before its AI step (1.20.2 lines 2272–2275; 1.20.4 lines 2269–2272). If the selected hand no longer matches the active stack, that helper calls virtual `stopUsingItem()` (1.20.2 lines 2896–2905; 1.20.4 lines 2898–2907). The superclass `startUsingItem` sets held-stack/use-duration state and, server-side, the shared flag bits (1.20.2 lines 2946–2956; 1.20.4 lines 2948–2958); superclass stopping clears use state and the server flag (1.20.2 lines 3058–3069; 1.20.4 lines 3060–3071). No difference in the relevant state lifecycle was found.

The client tick route is `LocalPlayer.tick()` → superclass tick chain → `LivingEntity.tick()` → virtual `aiStep()`; the `LivingEntity.tick()` body invokes `this.aiStep()` (1.20.2 line 2312; 1.20.4 corresponding body). `LocalPlayer.aiStep()` applies slowdown before `super.aiStep()`. `LivingEntity.aiStep()` enters `serverAiStep()` for effective AI, and LocalPlayer reports effective AI as true. In `LocalPlayer.serverAiStep()`, when `isControlledCamera()` is true, the already-scaled `input.leftImpulse` and `input.forwardImpulse` are copied to `xxa` and `zza` (lines 610–620, both endpoints). `LivingEntity.aiStep()` then applies its subsequent shared impulse handling and constructs the travel vector from `xxa/yya/zza`, dispatching to `travel` unless the entity is traveling as a ridden entity (1.20.2 lines 2588–2602; 1.20.4 lines 2590–2604). This establishes the bounded input-to-travel route, not equivalence of unrelated downstream travel behavior.

## Comparison result and limitations

No difference was found in the assigned active-item movement-slowdown gate, its local/synchronized active-use state feeding `isUsingItem()`, its stop-on-held-stack-mismatch path, or the client impulse handoff. The source evidence supports a bounded NO_DIFF only for this operation. Other `Player`/`LivingEntity` changes and all other movement operations remain unexamined here; no whole-pair freeze, live-world reachability, or runtime claim is made.
