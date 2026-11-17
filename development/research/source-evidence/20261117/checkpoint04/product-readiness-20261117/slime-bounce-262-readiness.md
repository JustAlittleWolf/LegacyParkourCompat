# Slime bounce product readiness: 26.1.2 → 26.2

**Disposition: COVERED_NO_CODE** for the bounded accepted SlimeBlock landing response and ordinary local-player below-gravity witness. This is product correspondence, not a full-pair or runtime-parity claim.

## Accepted source inputs

- Transition report: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_revision_r3/coverage-r3.md` — SHA-256 `4681e1e4a918b0ed1f69dbfb2748828c817808558142ef8988db9bd7132016be`.
- Independent ACCEPT: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_r3_audit/verdict-r3.md` — SHA-256 `F4A8FCB61C3B3855CC51B97A5F152F91601399C4D71D75EB8D078DCB2EBFA738`.
- Ordinary map witness: `build/later-source-20261116/26.1.2--26.2/slime_bounce_ordinary_reachability/coverage.md` — SHA-256 `DA498DEAEE4D95A632DA8847A4972A63FCE712655181A54A5E7319D4BB0FC79A`.
- Witness ACCEPT: `build/later-source-20261116/26.1.2--26.2/slime_bounce_ordinary_reachability_audit/verdict.md` — SHA-256 `AABDD95CF20BD6A8F43AC936E82FF0417815458A07986061C6DC9E713D5EAEC0`.
- Pinned `SUPPRESSES_BOUNCE` payload reference: SHA-256 `e8ec85f81c3f0b187bdde06344e41b8be7b41cc7aee3745753f547d8248eaf0f`; independent ACCEPT: `1A755A6A5EE67E7B6879D84E55ECC93E4C46C9C056C2F8F198C64DEAB0DD2D89`. The pinned vanilla payload lists honey block only, not SlimeBlock.

All four report/audit files were rehashed during this readiness check and matched the identities above.

## Profile and provider resolution

The literal `ParkourVersion` aliases are `26.1`, `26.1.1`, and `26.1.2` → `V26_1` (ordinal 39). `26.2` → `CURRENT` (ordinal 40); the enum has no `V26_2`. `ChangeResolver` selects, per mechanic key, the lowest annotation ordinal that is greater than or equal to the selected profile; `CURRENT` resolves no historical changes.

An exhaustive implementation search for the three relevant interfaces found:

- `CollisionRestitution @V26_1`: `src/client/java/me/wolfii/legacyparkourcompat/change/v26_1/CollisionRestitution.java`.
- `BlockLanding @V26_1`: `src/client/java/me/wolfii/legacyparkourcompat/change/v26_1/BlockLanding.java`. Its factory creates keyed variants for registered SlimeBlock and BedBlock IDs.
- `BedBounce @V1_11_2`: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_11_2/BedBounce.java`; its only variant is `minecraft:red_bed`, so it does not resolve for SlimeBlock.

For selected `V26_1` and a SlimeBlock effect state, the selected collision and SlimeBlock landing providers implement the accepted old response. Slime has no `BlockBounceHook` variant; `BlockLanding` supplies its own default coefficient of 1.0. After the shared sneak suppression check, it reverses negative Y movement directly for a `LivingEntity`, without a gravity threshold or tag check.

`src/client/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java` redirects `Entity.move`'s `restituteMovementAfterCollisions` call. It looks up the block landing/bounce providers using the post-move effect block and dispatches the selected `CollisionRestitutionHook`. For the accepted ordinary player setup, the default entity bounciness is 0.0, so `shouldUseNativeRestitution` does not route this collision into the native branch. For `CURRENT`, historical lookups are empty and the redirect falls back to native 26.2 restitution, including its impact-speed-versus-gravity gate.

## Bounded behavior and limits

The accepted ordinary-map witness uses a non-flying, non-passenger, non-sneaking local player, default gravity/air-drag attributes, and a carpet edge adjacent to a SlimeBlock. The source-level tick/travel path reaches `Entity.move`; leaving the carpet gives downward speed approximately `0.0784`, below effective gravity `0.08`, and the next move clips at the SlimeBlock. The old selected provider bounces; native 26.2 selects zero restitution under its threshold gate. The pinned vanilla tag does not suppress SlimeBlock.

This disposition does not establish effective tag membership after datapack/resource merging or reload, arbitrary attribute modifiers, runtime trajectory/frequency, other bouncy blocks or impacts, or full pair/runtime parity. No product code or Git history was changed; no build, test, or runtime check was performed.
