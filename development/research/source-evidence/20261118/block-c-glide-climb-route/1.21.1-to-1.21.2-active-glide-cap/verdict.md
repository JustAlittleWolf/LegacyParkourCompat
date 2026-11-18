# Independent exact-source audit: 1.21.1–1.21.2 active glide at a dry climbable

## Verdict

**Supported for the assigned bounded path.** For an ordinary on-foot local client player with shared fall-flying flag 7 already true, no fluid, and feet in a vanilla climbable such as a ladder or vine, both versions reach the fall-flying travel branch. Neither checks climbability before its first glide move. Both run `checkSlowFallDistance()` before glide velocity calculation and the first glide move in this dry case. No player-movement delta is established for this bounded route.

The source restructuring is accurately described. In 1.21.1, `updateFallFlying()` runs unconditionally before travel, but the cap is inside the fall-flying travel branch, after water/lava routing. In 1.21.2, tick calls `updateFallFlying()` only while the flag is active; that method runs the same cap before its server-only glide-eligibility handling and before `travel` chooses fluid, glide or air movement. Thus 1.21.2 also caps in active-fluid cases where 1.21.1's fluid-first route does not reach the glide-branch cap. That semantic expansion is outside the stated dry route; it does not change the cap-before-first-glide-move result here.

## Trace and gates

- **1.21.1:** `LivingEntity.tick` calls `updateFallFlying()` before `travelRidden`/`travel` (`LivingEntity.java:2665–2679`). `updateFallFlying()` derives a local candidate from the existing shared flag, airborne/non-passenger/no-levitation gates and enabled chest Elytra (`2716–2736`); its `setSharedFlag(7, ...)` is guarded by `!level().isClientSide` (`2738–2739`). The stated client therefore retains an already-set flag. `travel` first requires `isControlledByLocalInstance()` (`2091–2092`); water and lava branches precede `isFallFlying()` (`2099–2151`). With no fluid and an active flag, the glide branch calls `checkSlowFallDistance()` (`2151–2153`), computes glide velocity, then first moves at `2176–2177`. The ordinary-air branch at `2191` reaches friction-based movement and the separate `handleOnClimbable()` helper (`2280–2295`); it is skipped. The water collision response checks climbability at `2121`, after its first move.
- **1.21.2:** Tick calls `updateFallFlying()` only under `if (isFallFlying())` (`LivingEntity.java:2758–2760`), before `travel` (`2768–2771`). `updateFallFlying()` calls `checkSlowFallDistance()` first (`2814–2816`); `canGlide()` and its possible flag clear occur only on the logical server (`2816–2819`). The active local client flag therefore remains set. `travel` requires local control and routes fluid before the fall-flying branch (`2178–2187`); under the dry precondition that branch calls `travelFallFlying()`, whose first move is at `2268–2272`. The ordinary-air path is `travelInAir()` (`2191–2214`), which reaches friction-based movement; its climbable helper is `handleOnClimbable()` (`2379–2394`). Separately, the fluid collision response checks climbability at `2240`, after its first move; neither path is selected before the first glide move.
- **Cap semantics:** both exact `Entity.checkSlowFallDistance()` bodies are identical: if vertical delta `y > -0.5` and `fallDistance > 1.0F`, assign `1.0F` (`1.21.1 Entity.java:2386–2390`; `1.21.2 Entity.java:2484–2488`). This is a fall-distance cap, not an early return from glide travel.
- **Climbability:** each `onClimbable()` reads the current block state and recognizes `BlockTags.CLIMBABLE` or an eligible trapdoor (`1.21.1 LivingEntity.java:1517–1533`; `1.21.2:1561–1577`). That predicate is not called along the active dry glide path before the first `move`.

## Source identity and limits

The frozen input report is byte-identical to its declared SHA-256. The 1.21.1 ready marker identifies release `1.21.1`, mapping `mojmap`, and its source manifest; the provenance records the exact requested batch and `mappingOption=mojmap`. Its LivingEntity and Entity bytes match both the source manifest and the reported hashes. For 1.21.2, the Mojmap-root provenance pins exact release metadata and client artifact hashes; its LivingEntity and Entity bytes match the provenance `sources` entries and reported hashes. The local source roots are explicitly `decompiled_minecraft/<version>/mojmap`.

Provenance qualification: the 1.21.2 `.decompile-provenance.json` schema has no explicit `versionId` or `namespace` field and its `inputs` entries do not separately list a client-mappings artifact URL/hash. The exact release metadata hash, client hash, Mojmap-root placement, and source hashes are verified, but the mapping artifact is not independently hash-pinned in that manifest. This does not undermine the traced source behavior; it narrows the strength of the mapping-input provenance claim.

No product code, wiki material, other reports, Git history, generated sources, tests, builds or runtime were used.

## Immutable identities

- Input report `c-glide-active-climb-1211-1212-exact-gate/report.md`: SHA-256 `c3148cfc94158e786ae6ddf85268df8a995c4ef5c268290633642c2a2664bdd6`.
- 1.21.1 source: `LivingEntity.java` `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Entity.java` `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; provenance `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`.
- 1.21.2 source: `LivingEntity.java` `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`; `Entity.java` `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; provenance `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`.


