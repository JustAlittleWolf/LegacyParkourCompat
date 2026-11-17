# Independent source audit: movement status effects, 1.21.11 to 26.1.2

Verdict: **ACCEPT** the bounded NO_DIFF source report without correction.

Reviewed report: `build/later-source-20261116/1.21.11--26.1.2/status_effect_movement/coverage.md`  
SHA-256: `755FF6249627F162133D61FCDE36B98400298E3B9571398A464E7F55DE9A82E0`

## Source identity verification

The older endpoint is 1.21.11 Mojmap and the newer endpoint is 26.1.2 published unobfuscated. The exact ready markers declare these IDs/namespaces and their manifest digests. All four cited version-root sidecars at each endpoint hash to the values in the report; each ready marker's source-manifest and artifact-manifest digest agrees with the corresponding on-disk file. Provenance declares the same release ID and namespace.

For every cited Java source below, its local SHA-256 matches the declared source-manifest entry and the report:

| Source relative to mapping root | 1.21.11 Mojmap | 26.1.2 unobfuscated |
|---|---|---|
| `net/minecraft/world/effect/MobEffects.java` | `e27eef187ce134c3b64300fd7a4c7f691d553a172ec26ebac79797a030db7047` | `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684` |
| `net/minecraft/world/effect/MobEffect.java` | `1c4877780a246facd2e5ac392763c960580881d9ea90335d0841a5e982a12df7` | `5412fe3210df040882f2b5860a9fb3e1d66de151453740ebcdeaa22374acf28c` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `d786ece1cbc91c67907c422a9369bbbb9949af35a76dcc44ee8d1767c22c6367` | `f7bba4691d48273990eae848238189f84ff4f8de6807961c929c7faefbf6a321` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |

## Audit result

I independently checked the named operations and their paired source bodies. The listed SPEED/SLOWNESS and JUMP_BOOST registrations retain their same attribute, base amount and modifier operation; LEVITATION, SLOW_FALLING and DOLPHINS_GRACE registrations are unchanged. `MobEffect.AttributeTemplate.create` multiplies amount by amplifier plus one in both endpoints, and modifier add/remove behavior is the same. The direct jump-boost formula, falling-only slow-falling gravity cap, levitation vertical adjustment, water slowdown for Dolphin's Grace, SLOW_FALLING/LEVITATION fall-distance reset and levitation exclusion from `canGlide` match.

`MobEffectInstance.tickServer`, `tickClient`, duration decrement and hidden-effect downgrade ordering match. `LivingEntity.tickEffects` retains the server/client split and corresponding duration/removal handling. The client update receiver constructs the effect instance, applies the no-blend gate and calls `forceAddEffect`; the removal receiver calls `removeEffectNoUpdate` in both versions. Parameter names, access visibility, line positions and formatting differ, without changing the bounded movement operations or their ordering.

No omission was found that changes the report's bounded conclusion. The report appropriately leaves effect producers, client attribute synchronization/effective values, resources, wider tick/travel/provider closure and intermediate versions unresolved. This audit accepts only this bounded NO_DIFF report; it does not freeze the pair or establish runtime behavior.
