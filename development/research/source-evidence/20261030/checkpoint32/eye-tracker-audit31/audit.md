# Independent source-only audit — eye tracker closure 31

## Verdict

**Pass for the bounded disposition:** the source claim that no additional movement delta is established beyond the eye-water producer/cache and its already-traced consumers is supported. I found no separate movement-dependent direct eye-fluid consumer in the assigned ordinary-player slice that requires another operation or an additional finding. The tracker/point-query behavior has movement-visible effects through swimming eligibility/state and local-player water movement predicates; the direct eye reads encountered outside that route serve water vision, turtle-helmet, mining, or other excluded effects.

This closes only the bounded eye-query consumer slice described in the audited disposition. It is not whole-pair coverage, does not prove runtime parity, and does not determine an exact change boundary between 1.13.2 and 1.14 or a later target.

## Bound evidence and identity

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

The source disposition reviewed is `.native-source/20261030/eye-tracker-closure31/source-disposition.md`, SHA-256 `6d068549dfa8de08da420b203cb6871f01eee3ba470d2aa406bf7dea7fadf1b4` (as supplied in the assignment). Its 1.13.2 and 26.2 `Entity.java` and player caller hashes agree with the independently recomputed hashes here and with the permitted paired-source review. I independently recomputed these additional consumer source hashes:

| Release / mapping | Source | SHA-256 |
| --- | --- | --- |
| 1.14 / Yarn | `entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` |
| 1.14 / Yarn | `entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| 1.14.1 / Yarn | `entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| 1.14.2 / Yarn | `entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| 1.14.3 / Yarn | `entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.4 / Feather | `entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 / Feather | `entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 / Feather | `client/entity/living/player/LocalClientPlayerEntity.java` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| 26.2 / unobfuscated | `world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.2 / unobfuscated | `client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

The 1.14.1–1.14.3 paired identities and family coverage, 1.14 client loaded-check override correction, and 1.13.2/26.2 call graph details rely on the explicitly permitted prior review records: `eye-query-114-closure/source-review.md`, `ordinary-pool/eye-query-114-client-gate-review/audit.md`, and `20261028/eye-fluid-query-closure/comparison-r2.md`. Those reviews are operation-level inputs only; none was treated as whole-pair coverage.

## Consumer trace and movement relevance

- In 1.13.2, `PlayerEntity.tick` refreshes `isSubmergedInWater` before `super.tick` (`PlayerEntity.java:206–208, 257–260`). `Entity.updateSwimming` consumes the cached predicate (`Entity.java:947–965`); `isUnderwater` combines the cache with body-in-water (`Entity.java:943–944`). `LocalClientPlayerEntity` sprint gating reads `isSubmergedInWater` and `isInWater` (`723–752`).
- The inspected 1.14 family preserves the player pre-super refresh and cached water-submersion route. In 1.14.4, refresh is at `PlayerEntity.java:234–235, 284–286`, the entity water-state path calls `updateSwimming` at `Entity.java:942–952`, and local-player sprint checks use cached `isSubmergedInWater` / `isInWater` at `LocalClientPlayerEntity.java:654–684`. The 1.14.0–.3 source review traces the corresponding aliases and method bodies. The query helper's `checkLoaded` branch does not reject ordinary local-client refresh because each inspected client-world override returns true.
- 26.2 `Player.tick` refreshes `wasUnderwater` before `super.tick` (`Player.java:259–260, 304–307`), reading the existing `EntityFluidInteraction` eye tracker. Entity base tick captures the previous tracker value before refreshing fluid interaction and then updates swimming (`Entity.java:530–532`); `isUnderWater` and swimming use the resulting cached fields (`1613–1641`). Local-player sprint predicates use `isUnderWater()` (`LocalPlayer.java:1143–1148`), which returns cached `wasUnderwater` (`1177–1179`).
- Direct local-player eye-water calls found in the inspected endpoints are vision timers/accessors (`1.13.2 LocalClientPlayerEntity.java:789–794, 982–986`; 1.14.4 lines `720–724, 967–969`; 26.2 `LocalPlayer.java:863–868, 1155–1168`). These are visual outputs and excluded. Player direct calls for turtle helmets and underwater mining, and 1.13.2/1.14 movement-related-stat exhaustion branches, are also outside the movement scope. The movement predicates do not call an independent point query at those sites.

The 1.14.4 player swimming override only suppresses swimming while flying and otherwise delegates to the entity cached-water path (`PlayerEntity.java:1412–1417`); its nearby direct query in `tickNonRidingMovementRelatedStats` distinguishes exhaustion/stat categories, not movement response. The analogous 1.13.2 and 1.14.0 direct query hits are in that excluded statistics/exhaustion routine. Thus they do not reveal an omitted movement consumer.

## Unsupported release gate correction

The source disposition lists `1.13.3–1.13.9` among uninspected releases. These are not valid Minecraft release identifiers in the assigned concrete release sequence; they must not be retained as required inspection gates. Record the actual bound accurately as `1.13.2` versus inspected `1.14` through `1.14.4`, with no claim about any real uninspected releases between those endpoints and no asserted exact introduction boundary. This correction does not affect the bounded 1.14-family consumer conclusion.

Likewise, do not impose a generic arbitrary-intermediate-release or whole-pair completion gate on this narrowly bounded, source-backed 1.14 family consumer contract. The family consumer path is corroborated by the inspected patch sources and the allowed review. Whole-pair movement inventory remains a separate requirement if someone claims pair-wide completion; it is not a blocker to closing this eye-query leftover.

## Final disposition

The audited bounded leftover closes **without new code or a separate direct-query finding**: the movement-relevant eye-fluid result is already represented by the producer/cache operation and the traced cached consumers. Correct the unsupported `1.13.3–1.13.9` gate in the source disposition/status record. No exact cutover should be inferred from endpoint comparisons.
