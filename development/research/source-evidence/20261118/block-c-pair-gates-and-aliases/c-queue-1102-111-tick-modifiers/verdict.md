# Independent source-audit verdict — 1.10.2 to 1.11 Feather

Terminal: **COMPLETED**

## Immutable inputs

- Examined report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-queue-1102-111-tick-modifiers/report.md`
- Report SHA-256: `f64a5c3839732cef92345e4488e84263b66379a81dc72dd11aa4553caaa34eb8` (verified).
- Canonical exact sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.10.2/ornithe-feather/` and `.../1.11/ornithe-feather/`.
- Ready/source-manifest/provenance SHA-256 identities match the examined report: 1.10.2 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`, `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; 1.11 `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`, `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`, `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40`.

## Verdict by assigned slice

1. **Local input and pre-travel state — accepted, no movement finding in the stated ordinary-input slice.** `Input.java` is byte-identical (SHA-256 `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` on both sides). The loaded-chunk gate in `LocalClientPlayerEntity.tick()` and the compared `mobTick()` input snapshot, input tick, item-use scaling, autojump, sprint eligibility, flight input and mount-jump order match. The 1.11 Elytra-start branch omits the 1.10.2 nullable-stack guard; it sends a movement-action packet and is outside this bounded response slice, as the report says.

2. **Movement speed and effect modifier lifecycle — accepted for the compared movement operations, no finding.** `PlayerEntity.mobTick()` reads the movement-speed attribute and calls `setSpeed` in the same order, with the walk-speed base write gated to non-client worlds. Sprint modifier removal/re-addition, equipment modifier replacement, the byte-identical `ModifiableEntityAttributeInstance` evaluator, and `StatusEffect` speed/slowness UUIDs, magnitudes and operation 2 agree. Status-effect apply/upgrade/remove callbacks have the same client/server gates. The attribute packet handler sets base, clears modifiers, then adds received modifiers in the same order; status-effect receive construction and validity/thread gates also match. The receive-side effect removal handler calls `removeEffect` (rather than the callback-bearing `removeStatusEffect`) in both versions, so that detail is unchanged.

   One source difference omitted from the report is a `ConcurrentModificationException` catch around 1.11 `LivingEntity.tickStatusEffects()` iteration. The duration tick and movement-relevant modifier callbacks otherwise match. In the examined vanilla speed/slowness path this catch changes exceptional iteration handling, not the normal received-state or modifier response; no movement consequence is established by this difference. This verdict does not claim behavior under external mutation of the status-effect map.

3. **Ordinary default pose and eye height — accepted, no finding for ordinary and sneak transitions.** `PlayerEntity.updatePlayerPose()` and `getEyeHeight()` retain the same sizing precedence, collision gate and values. Ordinary/sneak width remains `0.6F`; the shrink-only `setSize` branch is not entered in this slice.

## Separate conditional `setSize` note

The reported width-shrink difference is real. In 1.10.2 `Entity.setSize()` rebuilds from the prior box minimum X/Z. In 1.11, when `width < oldWidth`, it instead rebuilds around the entity's current X/Z using `width / 2.0` and returns. `PlayerEntity.updatePlayerPose()` requests width `0.2F` for sleeping, subject to its existing collision gate. Therefore a call that accepts that width shrink takes the changed branch and can produce a different horizontal box. This audit does not determine sleep/bed-entry eligibility or redo collision internals; classify movement consequence separately if that conditional path is investigated.

## Boundaries

This is an independent audit of the report's four assigned slices and the conditional width-shrink observation, not a full tick-graph/pair freeze. Travel arithmetic, block/contact collision internals, bed-entry eligibility, packet movement responses, and other unassigned downstream operations were not audited. No product source, wiki lane, Git history or backlog was inspected; no code, tests, runtime, or source generation was performed.