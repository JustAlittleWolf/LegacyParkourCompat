# 1.21.11 → 26.1.2 `CAN_GLIDE_THROUGH` source closure

## Scope and source identity

This is a blind, source-only trace of the exact `CAN_GLIDE_THROUGH` tag paths in the two canonical extracted source trees. The permitted gap context was `.native-source/20261028/frontier-c/12111-2612-pair-coverage-audit/audit.md`; that audit confirms ready identities and verifies every source-manifest entry against its source digest. Its endpoint identities are `1.21.11` Mojmap and `26.1.2` unobfuscated; source-manifest SHA-256 values are respectively `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.

Only the exact tag-bearing source files and `onClimbable` caller locations in `LivingEntity`, `Player`, and `LocalPlayer` were searched/read. Direct SHA-256 values for the relevant complete files:

| Role | 1.21.11 Mojmap | SHA-256 | 26.1.2 unobfuscated | SHA-256 |
|---|---|---|---|---|
| Tag key declaration | `net/minecraft/tags/BlockTags.java` | `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3` | `net/minecraft/tags/BlockTags.java` | `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f` |
| Built-in data provider | `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474` | `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `e34485a342b377f530a28e4eb81c40163d69a0697acf7cfd19ee13baaab7ffbe` |
| Consumer and inherited callers | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |

These file hashes are additional direct bindings for this closure; the source-manifest identity above comes from the allowed audit and is not a substitute for them.

## Declarations, providers, consumer inventory

A targeted case-sensitive/insensitive-independent source search for `CAN_GLIDE_THROUGH` / `can_glide_through` found, in each endpoint:

1. One declaration: `BlockTags.CAN_GLIDE_THROUGH = create("can_glide_through")` (`BlockTags.java`, old line 125; new line 127).
2. One built-in provider: `VanillaBlockTagsProvider` adds `VINE`, both twisting-vine blocks, both weeping-vine blocks, then `CAVE_VINES` (`VanillaBlockTagsProvider.java`, old line 320; new line 319). The declaration and provider chain are equivalent across endpoints.
3. One consumer: `LivingEntity.onClimbable()` reads `state.is(BlockTags.CAN_GLIDE_THROUGH)` (`LivingEntity.java`, old line 1675; new line 1695). No other direct tag reads or providers were found in the two searched source trees.

This establishes source declaration and default provider declarations only. It does not establish emitted resource bytes or final tag membership after resource-pack composition.

## Consumer semantics and movement reachability

`LivingEntity.onClimbable()` first rejects spectators. It then obtains `blockPosition()` and `getInBlockState()`. If the entity is fall-flying and the in-block state is in `CAN_GLIDE_THROUGH`, it returns false before checking `CLIMBABLE` or the open-trapdoor/ladder case. Otherwise, it updates `lastClimbablePos` and returns true for a `CLIMBABLE` state or a usable open trapdoor above a matching ladder; all other states return false. The operation and ordering match in both source bodies.

`Player.onClimbable()` wraps that behavior identically in both endpoints: creative ability flight returns false, otherwise it dispatches to `super.onClimbable()` (old `Player.java:2043–2044`; new `:2003–2004`). `LocalPlayer` inherits this implementation; no local-player override is needed for reachability.

The local-player caller path is movement-reachable:

- `LocalPlayer.aiStep` checks jump input, creative-flight toggle/jump gates, `!onClimbable()`, and `tryToStartFallFlying()` before sending `START_FALL_FLYING` (old `LocalPlayer.java:815–816`; new `:854–855`). This is a client movement decision. On this call, the `isFallFlying()` subgate inside `LivingEntity.onClimbable()` is itself relevant: before flight starts, the tag does not suppress climbability through that branch.
- The inherited `LivingEntity.travelFallFlying` calls `onClimbable()`. A true result switches to ordinary air travel and stops fall flying (old `LivingEntity.java:2441–2445`; new `:2528–2532`). When fall-flying and inside a tag member, the tag causes false and avoids that fallback.
- Other inherited movement uses of the same predicate include horizontal-collision climb lift in water travel (old `:2400–2402`, new `:2487–2489`), climb movement handling and jump lift (old `:2539–2541`, `:2561–2567`; new `:2628–2630`, `:2650–2656`), and `isPushable()` (old `:3202–3205`; new `:3309–3312`). These calls are dynamically dispatched through `Player.onClimbable`; thus fall-flying inside a tagged state can also suppress these climbable responses. For normal non-fall-flying player movement, the special tag branch is inactive.
- `Player.canCriticalAttack` also consults `onClimbable`, but critical-hit eligibility itself is outside movement production scope. That use is not needed to establish the client movement dependency.

The source proves a real, reachable client movement dependency conditional on fall-flight state and current in-block state. It does not prove that a particular runtime block is effectively in the tag, or that a block state overlaps the relevant player position in a given trajectory.

## Provider gates and limits

The built-in `VanillaBlockTagsProvider` definitions have no visible runtime condition around this tag entry chain. They are static provider declarations. The ready source trees do not bind generated tag JSON, datapack merges, reload outcomes, or effective client tag membership. Those remain unresolved. No server-side producer or packet timing was examined or inferred; server producer timing remains unresolved as requested and is unnecessary to establish the source-side client consumer.

No difference was found in the key, provider entries, consumer boolean order, `Player` wrapper, or inspected movement callers. This closes the specific source-level tag-consumer dependency and does not close the broader block-provider inventory, tag payload/resource membership, pair freeze, or runtime parity.
