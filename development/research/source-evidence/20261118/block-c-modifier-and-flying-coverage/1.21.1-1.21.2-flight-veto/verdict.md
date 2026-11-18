# COMPLETED — independent flight-veto audit, 1.21.1→1.21.2

## Bound input

Pinned report: `c-queue-1211-1212-flight-veto-range/report.md`, SHA-256 `43135324cfe6719a5e9f4c37943a902675e8e4b82a51c03e5cb67382935eeea7` (verified against exact bytes). This verdict is an independent audit of its `canGlide` flight-veto and removed `LocalPlayer` caller-check claims, not a full-pair audit.

Canonical Mojmap source files examined:

- 1.21.1 `Player.java` SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `LocalPlayer.java` `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; `LivingEntity.java` `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`.
- 1.21.2 `Player.java` SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; `LocalPlayer.java` `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`; `LivingEntity.java` `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`.

The 1.21.2 nested `.decompile-provenance.json` lists the exact `Player.java` hash above; provenance file SHA-256 is `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. No corresponding nested provenance file exists in the 1.21.1 Mojmap tree, so that side is bound to its exact canonical file bytes. No product, Git, wiki, backlog, or implementation evidence was consulted.

## Verdict

**The new `Player.canGlide()` flying veto is real, but the report does not establish a logical-client movement-state delta for ordinary fall-flying initiation.** In 1.21.1, `LocalPlayer.aiStep` rejects `abilities.flying` and passengers before checking climbability, then requires an enabled chest Elytra and calls `tryToStartFallFlying`. In 1.21.2 it removes those caller checks and calls the method after the climbability guard; the new `Player.canGlide()` rejects `abilities.flying`, and inherited `LivingEntity.canGlide()` rejects ground, passenger, and levitation states while checking glide equipment. Thus, for ordinary Elytra initiation, the creative-flight veto was moved from the caller to the predicate. The client path's resulting start/no-start outcome for a flying player is unchanged; direct method behavior is not proof of a reachable client movement delta when its only client caller had already enforced that veto.

The 1.21.2 predicate does change gate order. `LocalPlayer.aiStep` now evaluates `onClimbable()` before entering `tryToStartFallFlying`, whereas the old caller checked flying and passenger first. `onClimbable()` is not pure: on a non-spectator standing in a state tagged `CLIMBABLE` (including an ordinary ladder state), both versions return true and write `lastClimbablePos`. Consequently, with jump input and flying or passenger state on a ladder, 1.21.2 may record the climbable position earlier in the tick where 1.21.1 short-circuited before that call. This is a real state-write/order difference, but the exact-source read found `getLastClimbablePos()` consumed by `FallLocation`; it does not establish a player movement-state delta within the assigned flight-initiation boundary. The climbability predicate body and ladder truth result are unchanged.

For a player already fall-flying, both client initiation paths reach `tryToStartFallFlying` only after the climbability check, and its `!isFallFlying()` gate prevents a second start. The new `canGlide()` override is also consulted by 1.21.2 `LivingEntity.updateFallFlying`, but that method's eligibility/stop branch is guarded by `!level().isClientSide`; it is server-side behavior and does not show the logical-client flight delta required here. The LocalPlayer client path does not call `canGlide()` for an already active glide in the examined call sequence.

The `LocalPlayer` checks for passenger and Elytra eligibility are likewise relocated into the shared predicate/method path for ordinary Elytra use. 1.21.2 generalizes equipment qualification through `LivingEntity.canGlideUsing` and the `GLIDER`/`EQUIPPABLE` components; that broader equipment rule is not accepted or rejected by this narrowly bounded flight-veto verdict.

## Coverage and limit

Compared exact `Player.tryToStartFallFlying`, `Player.canGlide`, inherited `LivingEntity.canGlide` and `onClimbable`, `LocalPlayer.aiStep` call/gate order, `updateFallFlying` side guard, and exact-source references to `getLastClimbablePos`. No full tick graph, broad equipment-semantic audit, or full-pair coverage is claimed. No implementation, tests, runtime launch, or source generation was performed.