# Independent source audit verdict: fall-flying before swimming pose

Verdict: **ACCEPTED WITH A CITATION CORRECTION** for the bounded source witness across exact stable releases 1.14.0–1.21.4. This supports a conditional local-player state path; it does not prove a specific world trajectory or network schedule.

## Inputs and authentication

- Frozen chain proof `c-pose-swim-priority-bound/1.14-1.21.4-exact-release-boundary-proof.md` SHA-256: `c0e841875de011092dfc036189245c60bb1ff2d9d16d2d2ffcaa71a828cc4f86`, matching the assignment.
- Each Markdown artifact in that bounded proof folder matches its adjacent `.sha256` sidecar. The accepted 1.21.4–1.21.5 finding `development/research/findings/1.21.4--1.21.5/swimming-fall-flying-pose-priority.md` has SHA-256 `4fa3a22dc75ef2c55e31190f26a89f0b02acf87326af349733c057e31dffab35`.
- Directly checked Yarn-only 1.14.1, 1.14.2, and 1.14.3 `Entity.java`, `LivingEntity.java`, and `PlayerEntity.java` against the exact file digests in the frozen proof; all nine match. Their nested `.decompile-provenance.json` records provide the exact input/source identity; no Mojmap source exists for those three aliases in this tree.
- Independently extracted and SHA-256 hashed the whitespace-normalized selector bodies in all 35 exact release folders. Mojmap bodies form stable groups across 1.15–1.17.1, 1.18–1.20.1, and 1.20.2–1.21.4; the 1.14.1/.2 Yarn bodies also match each other. The 1.14.0 Feather, 1.14.3 Yarn, and 1.14.4 Mojmap bodies are distinct hashes. Every inspected selector has the same fit/order behavior; the Yarn constants in 1.14.1/.2 are intermediary names, so the mapped enum identity comes from the exact nested mapping provenance. I also checked the frozen normalized fingerprints for the remaining gate methods and directly inspected selector, water/swimming, retained-flight, caller, and fit source at the listed transition anchors. Source differences at the stated gates match the proof’s account.

## Source result

Given a local player state that starts dry, is airborne, not riding, has a usable Elytra and valid retained fall-flying flag, has no Levitation from 1.16 onward, is sprinting when entering qualifying water, and is neither ability-flying nor sleeping/spectating, the source permits swimming state to become true without clearing fall-flying. Fall-flying continuation has no water rejection in the audited versions. Player swimming overrides suppress only ability flight. The desired-pose selector tests fall-flying before swimming throughout 1.14.0–1.21.4 and applies the selected pose only after its fit admission.

Relevant gates vary by release: entry to swimming requires sprint plus underwater water and not riding in 1.14–1.17.0; 1.17.1 onward additionally requires water at the entity block position. At 1.20.2 the player-fit helper changes, while the centered-width/feet-origin box and `1.0E-7` deflation preserve the gate described in the frozen proof. The preliminary swimming-fit gate and selected-pose fit/fallback both remain operative. For a bounded source witness, both fit checks may be stipulated as passing; that does not establish a collision-free physical route.

Tick order supports the witness: the entity water/swimming update occurs during the superclass tick; player pose selection follows the superclass tick; the local-player tick’s superclass tick precedes local input handling. Thus the pose selector can observe the newly set swimming flag alongside a retained fall-flying flag on the water-entry tick.

The fall-flying flag’s eligibility/continuation writer is server-side. It clears the flag only when its airborne/non-riding/glider eligibility check fails (and, from 1.16, when Levitation is present); water is not a failure condition. Local start uses its dry-start helper and sends `START_FALL_FLYING` only after a successful start check. A failed or duplicate `START_FALL_FLYING` request is a real server packet-path caveat: the handler can call `stopFallFlying()` on failure, including because a new start is invalid while wet or already flying. The ordinary water-entry tick does not itself send such a request. Server synchronization timing remains outside this source verdict.

The 1.21.4 and 1.21.5 accepted finding accurately describes the selector-order change and equal dimensions at those endpoints. At those endpoints FALL_FLYING and SWIMMING both have 0.6F × 0.6F dimensions and 0.4F eye height; the priority change alone therefore establishes a pose-state difference, not a body-box difference.

## Required citation correction

The frozen chain text labels the 1.14.1–1.14.3 Yarn selector as `PlayerEntity.updatePlayerPose`. In all three exact Yarn sources the member is named `PlayerEntity.updateSize`; each body contains the preliminary swimming-pose fit, fall-flying-before-sleeping-before-swimming choice, chosen-pose fit and fallback. The 1.14.1/.2 bodies use intermediary `EntityPose.field_18077`/`.field_18079` names; 1.14.3 uses mapped enum names. This is a member-name/citation error, not a behavioral disagreement; cite the actual method in each Yarn source.

## Boundaries

Accepted claim: conditional source-level state overlap and pose priority, including stated per-release water/flight/fit gates, for exact stable releases 1.14.0–1.21.4. Not established: a concrete world route, guaranteed local/server packet timing, fit in a particular world, runtime tick trace, or any conclusion outside this release interval. No unrelated movement area, product implementation, tests, build, game launch, or primary research file was examined or changed.
